package model;

import java.awt.Dimension;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import gui.VentanaBorrarUsuario;
import gui.VentanaCambiarContraseña;
import gui.VentanaCrearUsuario;
import gui.VentanaInicioSesion;
import gui.VentanaMenuUsuario;
import gui.VentanaVerUsuario;
import java.nio.file.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument.Iterator;

import java.io.*;

public class AplicacionUsuarios {

	private final String RUTA_FICHERO = "./usuarios.json";
	private VentanaInicioSesion ventanaInicioSesion;
	private VentanaCrearUsuario ventanaCrearUsuario;
	private VentanaMenuUsuario ventanaMenuUsuario;
	private VentanaVerUsuario ventanaVerUsuario;
	private VentanaCambiarContraseña ventanaCambiarContraseña;
	private VentanaBorrarUsuario ventanaBorrarUsuario;

	private JSONArray usuarios_registrados = new JSONArray();
	private JSONParser parser = new JSONParser();
	private JSONObject usuario;

	/*
	 * Crea el fichero donde se guardaran los registros de usuario
	 */
	private void crearFicheroJson() {
		Path fichero_json = Paths.get(RUTA_FICHERO);
		if (!Files.exists(fichero_json)) {
			try {
				Files.createFile(fichero_json);
			} catch (FileAlreadyExistsException ee) {
				System.out.println("El fichero ya existe " + ee.getMessage());
			} catch (IOException e) {
				System.out.println("Error al crear el archivo JSON :" + e.getMessage());
			}
		}
	}

	// Crear variable global para no acceder a disco todo el rato
	/*
	 * Cargar usuarios desde el archivo json
	 */
	private JSONArray obtenerUsuariosJson() {

		try {
			Object obj = parser.parse(new FileReader(RUTA_FICHERO));
			usuarios_registrados = (JSONArray) obj;
			for (Object object : usuarios_registrados) {
				System.out.println(object.toString());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return usuarios_registrados;
	}

	/**
	 * 
	 * @param nombreUsuario string a buscar
	 * @return Devuelve un jsonObject si recupera el usuario buscado,sino devuelve
	 *         null
	 */
	private JSONObject obtenerUsuarioJson(String nombreUsuario) {
		try {
			Object obj = parser.parse(new FileReader(RUTA_FICHERO));
			usuarios_registrados = (JSONArray) obj;
			for (Object object : usuarios_registrados) {
				usuario = (JSONObject) object;
				if (usuario.containsValue(nombreUsuario)) {
					return usuario;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * Inicia la aplicacion
	 */
	public void ejecutar() {
		crearFicheroJson();
		// mostrarVentana(ventanaInicioSesion);
		ventanaInicioSesion = new VentanaInicioSesion(this);
		ventanaInicioSesion.setVisible(true);
		ventanaInicioSesion.setSize(new Dimension(500, 400));
		ventanaInicioSesion.setLocationRelativeTo(null);
		//
		for (Object object : usuarios_registrados) {
			System.out.println(object.toString());
		}
	}

	/**
	 * 
	 * @param nombreUsuario
	 * @param contraseñaUsuario
	 */
	public void iniciarSesion(String nombreUsuario, String contraseñaUsuario) {
		if (comprobarContraseña(nombreUsuario, contraseñaUsuario)) {
			ventanaMenuUsuario = new VentanaMenuUsuario(this, nombreUsuario);
			ventanaMenuUsuario.setVisible(true);
			ventanaMenuUsuario.setSize(500, 400);
			ventanaInicioSesion.setLocationRelativeTo(null);
			ventanaInicioSesion.dispose();
		}

	}

	/*
	 * 
	 */
	public void cerrarSesion() {
		ventanaInicioSesion = new VentanaInicioSesion(this);
		ventanaInicioSesion.setVisible(true);
		ventanaInicioSesion.setSize(new Dimension(500, 400));
		ventanaInicioSesion.setLocationRelativeTo(null);
		ventanaMenuUsuario.dispose();
	}

	/**
	 * Si todos los campos son validos,escribe un nuevo JSONObject al fichero
	 * 
	 * @param nombre
	 * @param contrasena
	 * @param edad
	 * @param correo
	 */
	public void crearUsuario(String nombre, String contrasena, String edad, String correo) {

		if (comprobarCrearUsuario(edad, nombre, correo, contrasena)) {
			usuario = new JSONObject();
			usuario.put("nombre", nombre.trim());
			usuario.put("contrasena", contrasena.trim());
			usuario.put("edad", edad.trim());
			usuario.put("correo", correo.trim());
			usuarios_registrados.add(usuario);

			try (FileWriter f = new FileWriter(RUTA_FICHERO)) {
				f.write(usuarios_registrados.toString());
				f.flush();
				f.close();
			} catch (Exception e) {
				System.out.println("Error crear usuario : " + e.getMessage());
			}
			JOptionPane.showMessageDialog(null, "Usuario creado correctamente", "Usuario creado",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Recorre la lista de usuarios registrados y actualiza el fichero json
	 * 
	 * @param nombreUsuario
	 * @param nuevaContraseña
	 */
	public void cambiarContraseña(String nombreUsuario, String nuevaContraseña) {
		for (int i = 0; i < usuarios_registrados.size(); i++) {
			if (((JSONObject) usuarios_registrados.get(i)).get("nombre").equals(nombreUsuario)) {
				JSONObject cambiapass_usuario = (JSONObject) usuarios_registrados.get(i);
				cambiapass_usuario.replace("contrasena", nuevaContraseña.trim());
				try (FileWriter f = new FileWriter(RUTA_FICHERO)) {
					f.write(usuarios_registrados.toString());
					f.flush();
					f.close();
				} catch (Exception e) {
					System.out.println("Error crear usuario : " + e.getMessage());
				}
			}

		}

	}

	/**
	 * Elimina un usuario si existe en el fichero
	 * 
	 * @param nombreUsuario
	 */
	public void borrarUsuario(String nombreUsuario) {

		for (int i = 0; i < usuarios_registrados.size(); i++) {
			usuario = (JSONObject) usuarios_registrados.get(i);

			if (usuario.get("nombre").equals(nombreUsuario)) {

				usuarios_registrados.remove(usuario);

				try (FileWriter f = new FileWriter(RUTA_FICHERO)) {
					f.write(usuarios_registrados.toString());
					f.flush();

				} catch (Exception e) {
					System.out.println("Error crear usuario : " + e.getMessage());
				}
			}
		}
	}

	/**
	 * 
	 */
	public void mostrarVentanaCrearUsuario() {
		ventanaCrearUsuario = new VentanaCrearUsuario(this);
		mostrarVentana(ventanaCrearUsuario);
	}

	/**
	 * 
	 * @param nombreUsuario
	 */
	public void mostrarVentanaVerUsuario(String nombreUsuario) {
		usuario = obtenerUsuarioJson(nombreUsuario);
		if (usuario != null) {
			ventanaVerUsuario = new VentanaVerUsuario(this, usuario.get("nombre").toString(),
					usuario.get("edad").toString(), usuario.get("correo").toString());
			mostrarVentana(ventanaVerUsuario);
		} else {
			JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * 
	 * @param nombreUsuario
	 */
	public void mostrarVentanaCambiarContraseña(String nombreUsuario) {
		ventanaCambiarContraseña = new VentanaCambiarContraseña(this, nombreUsuario);
		mostrarVentana(ventanaCambiarContraseña);
	}

	/**
	 * 
	 * @param nombreUsuario
	 */
	public void mostrarVentanaBorrarUsuario(String nombreUsuario) {
		ventanaBorrarUsuario = new VentanaBorrarUsuario(this, nombreUsuario);
		mostrarVentana(ventanaBorrarUsuario);

	}

	/**
	 * 
	 * @param nombre
	 * @param contraseña
	 * @return
	 */
	public Boolean comprobarContraseña(String nombre, String contraseña) {
		if (obtenerUsuarioJson(nombre) != null) {
			usuario = obtenerUsuarioJson(nombre);
		} else {
			JOptionPane.showMessageDialog(null, "El usuario no existe ", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (usuario.get("nombre").equals(nombre)) {
			if (usuario.get("contrasena").equals(contraseña)) {
				return true;
			} else if (!usuario.get("contrasena").equals(contraseña)) {
				JOptionPane.showMessageDialog(null, "Contraseña incorrecta ", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "El usuario no existe ", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return false;

	}

	/**
	 * 
	 * @param ventana
	 */
	private void mostrarVentana(JFrame ventana) {
		ventana.setVisible(true);
		ventana.setSize(new Dimension(400, 400));
		ventana.setLocationRelativeTo(null);
		if (ventanaInicioSesion != null) {
			ventanaInicioSesion.dispose();
		}
		if (ventanaMenuUsuario != null) {
			ventanaMenuUsuario.dispose();
		}
	}

	/**
	 * 
	 * @param edadIn
	 * @param nombreIn
	 * @param correoIn
	 * @param contraseñaIn
	 * @return
	 */
	public Boolean comprobarCrearUsuario(String edadIn, String nombreIn, String correoIn, String contraseñaIn) {
		String mensaje = "";
		if (obtenerUsuarioJson(nombreIn) != null) {
			JOptionPane.showMessageDialog(null, "El usuario ya existe", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!correoIn.contains("@")) {
			JOptionPane.showMessageDialog(null, "El campo correo debe tener una @", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			int edad = Integer.parseInt(edadIn);
			if (edad <= 0) {
				JOptionPane.showMessageDialog(null, "El campo edad debe ser un numero positivo", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "El campo edad debe ser un campo numerico", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

}
