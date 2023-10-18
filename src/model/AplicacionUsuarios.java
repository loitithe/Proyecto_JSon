package model;

import java.awt.Dimension;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import gui.VentanaBorrarUsuario;
import gui.VentanaCambiarContraseña;
import gui.VentanaCrearUsuario;
import gui.VentanaInicioSesion;
import gui.VentanaMenuUsuario;
import gui.VentanaVerUsuario;
import java.nio.file.*;
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

	private JSONArray obtenerUsuariosJson() {
		JSONParser parser = new JSONParser();
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
		ventanaMenuUsuario = new VentanaMenuUsuario(this, nombreUsuario);

		ventanaMenuUsuario.setVisible(true);
		ventanaMenuUsuario.setSize(500, 400);
		ventanaInicioSesion.setLocationRelativeTo(null);
		ventanaInicioSesion.dispose();
	}

	public void cerrarSesion() {
		ventanaInicioSesion = new VentanaInicioSesion(this);
		ventanaInicioSesion.setVisible(true);
		ventanaInicioSesion.setSize(new Dimension(500, 400));
		ventanaInicioSesion.setLocationRelativeTo(null);
		ventanaMenuUsuario.dispose();
	}

	public void crearUsuario(String nombre, String contrasena, String edad, String correo) {

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
		obtenerUsuariosJson();
	}

	public void cambiarContraseña(String nombreUsuario, String nuevaContraseña) {

	}

	public void borrarUsuario(String nombreUsuario) {

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

}
