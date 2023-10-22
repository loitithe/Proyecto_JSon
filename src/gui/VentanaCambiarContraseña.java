package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.AplicacionUsuarios;

public class VentanaCambiarContraseña extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel etiquetaNuevaContraseña;
	private JTextField textoNuevoContraseña;
	private JButton btnCambiarContraseña;
	private JButton btnCancelar;
	private AplicacionUsuarios app;
	private String nombreUsuario;

	public VentanaCambiarContraseña(AplicacionUsuarios app, String nombreUsuario) {
		this.app = app;
		this.nombreUsuario = nombreUsuario;
		setTitle("Aplicación usuarios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 188);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		etiquetaNuevaContraseña = new JLabel("Escribe tu nueva contraseña:");
		etiquetaNuevaContraseña.setFont(new Font("Tahoma", Font.BOLD, 12));
		etiquetaNuevaContraseña.setBounds(21, 22, 192, 14);
		contentPane.add(etiquetaNuevaContraseña);

		textoNuevoContraseña = new JTextField();
		textoNuevoContraseña.setBounds(21, 58, 179, 20);
		contentPane.add(textoNuevoContraseña);
		textoNuevoContraseña.setColumns(10);

		btnCambiarContraseña = new JButton("Cambiar");
		btnCambiarContraseña.setBounds(124, 111, 89, 23);
		btnCambiarContraseña.addActionListener(this);
		contentPane.add(btnCambiarContraseña);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(10, 111, 89, 23);
		btnCancelar.addActionListener(this);
		contentPane.add(btnCancelar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton boton_pulsado = (JButton) e.getSource();
		if (boton_pulsado == btnCambiarContraseña) {
			app.cambiarContraseña(nombreUsuario, textoNuevoContraseña.getText());
			JOptionPane.showMessageDialog(null, "Contrasena cambiada correctamente", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);

			VentanaMenuUsuario ventanaMenuUsuario = new VentanaMenuUsuario(app, nombreUsuario);
			ventanaMenuUsuario.setVisible(true);
			ventanaMenuUsuario.setSize(400, 400);
			ventanaMenuUsuario.setLocationRelativeTo(null);
			this.dispose();
		}
		if (boton_pulsado == btnCancelar) {
			VentanaMenuUsuario ventanaMenuUsuario = new VentanaMenuUsuario(app, nombreUsuario);
			ventanaMenuUsuario.setVisible(true);
			ventanaMenuUsuario.setSize(400, 400);
			ventanaMenuUsuario.setLocationRelativeTo(null);
			this.dispose();
		}
	}

}
