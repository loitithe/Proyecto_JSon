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

import javax.swing.JOptionPane;

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
	JSONObject usuario = new JSONObject();

	private void crearFicheroJson() {
		Path fichero_json = Paths.get(RUTA_FICHERO);
		if (!Files.exists(fichero_json)) {
			try {
				Files.createFile(fichero_json);
			} catch (FileAlreadyExistsException ee) {
				System.out.println("Crear fichero JSon" + ee.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// Crear variable global para no acceder a disco todo el rato
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

	private int obtenerPosicionUsuario(String nombreUsuario, JSONArray usuarios) {

	}

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

	public void ejecutar() {
		crearFicheroJson();
		ventanaInicioSesion = new VentanaInicioSesion(this);
		ventanaInicioSesion.setVisible(true);
		ventanaInicioSesion.setSize(new Dimension(500, 400));
		ventanaInicioSesion.setLocationRelativeTo(null);
		//

	}

	public void iniciarSesion(String nombreUsuario, String contraseñaUsuario) {
		if (obtenerUsuarioJson(nombreUsuario) != null &&
				comprobarContraseña(nombreUsuario, contraseñaUsuario)) {
			ventanaMenuUsuario = new VentanaMenuUsuario(this, nombreUsuario);
			ventanaMenuUsuario.setVisible(true);
			ventanaMenuUsuario.setSize(500, 400);
			ventanaInicioSesion.setLocationRelativeTo(null);
			ventanaInicioSesion.dispose();
		}

	}

	public void cerrarSesion() {
		ventanaInicioSesion = new VentanaInicioSesion(this);
		ventanaInicioSesion.setVisible(true);
		ventanaInicioSesion.setSize(new Dimension(500, 400));
		ventanaInicioSesion.setLocationRelativeTo(null);
		ventanaMenuUsuario.dispose();
	}

	public void crearUsuario(String nombre, String contrasena, String edad, String correo) {
		// obtenerUsuarioJson(nombre);
		if (obtenerUsuarioJson(nombre) == null) {
			JSONObject object_User = new JSONObject();
			object_User.put("nombre", nombre);
			object_User.put("contrasena", contrasena);
			object_User.put("edad", edad);
			object_User.put("correo", correo);

			usuarios_registrados.add(object_User);
			try (FileWriter f = new FileWriter(RUTA_FICHERO)) {
				f.write(usuarios_registrados.toString());
				f.flush();

			} catch (Exception e) {
				System.out.println("Error crear usuario : " + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No puede haber usuarios con el mismo nombre ", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		obtenerUsuariosJson();
	}

	public void cambiarContraseña(String nombreUsuario, String nuevaContraseña) {

	}

	// TODO
	public void borrarUsuario(String nombreUsuario) {
		for (Object object : usuarios_registrados) {
			JSONObject borra_usuario = (JSONObject) object;

		}
	}

	public void mostrarVentanaCrearUsuario() {
		ventanaCrearUsuario = new VentanaCrearUsuario(this);
		ventanaCrearUsuario.setVisible(true);
		ventanaCrearUsuario.setSize(new Dimension(500, 400));
		ventanaCrearUsuario.setLocationRelativeTo(null);
		ventanaInicioSesion.dispose();
	}

	public void mostrarVentanaVerUsuario(String nombreUsuario) {

	}

	public void mostrarVentanaCambiarContraseña(String nombreUsuario) {

	}

	public void mostrarVentanaBorrarUsuario(String nombreUsuario) {

	}

	public Boolean comprobarContraseña(String nombre, String contraseña) {
		JSONObject usuario = obtenerUsuarioJson(nombre);
		boolean valido = false;
		if (usuario != null) {
			if (usuario.get("nombre").equals(nombre)) {
				valido = true;
			} else if (!usuario.get("nombre").equals(nombre)) {
				JOptionPane.showMessageDialog(null, "Nombre incorrecto ", "Error",
						JOptionPane.ERROR_MESSAGE);
				valido = false;
			}

			if (usuario.get("contrasena").equals(contraseña)) {
				valido = true;
			} else if (!usuario.get("contrasena").equals(contraseña)) {
				JOptionPane.showMessageDialog(null, "Contraseña incorrecta ", "Error",
						JOptionPane.ERROR_MESSAGE);
				valido = false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "El usuario no existe ", "Error",
					JOptionPane.ERROR_MESSAGE);
			valido = false;
		}
		return valido;

	}

}
