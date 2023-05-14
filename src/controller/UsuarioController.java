package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.Usuarios;
import views.Login;
import views.MenuUsuario;

public class UsuarioController implements ActionListener {

	private Login views;
	
	public UsuarioController(Login views) {
		this.views = views;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String nombre = views.getNombre();
		String contraseña = views.getContraseña();
		
		if (Usuarios.validarUsuario(nombre, contraseña)) {
			MenuUsuario menu = new MenuUsuario();
			menu.setVisible(true);
			views.dispose();
		} else {
            JOptionPane.showMessageDialog(views, "Usuario o Contraseña no válidos");
		}
	}
	
}
