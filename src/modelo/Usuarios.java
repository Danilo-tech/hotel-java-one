package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class Usuarios {

	private String usuario;
	private String contraseña;
	
	public Usuarios(String usuario, String contraseña) {
		this.usuario = usuario;
		this.contraseña = contraseña;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getContraseña() {
		return contraseña;
	}
	
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	public static boolean validarUsuario(String nombre, String contraseña) {
	    Connection con = new ConnectionFactory().recuperaConexion();
	    PreparedStatement statement = null;
	    ResultSet result = null;
	    try {
	    	statement = con.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? AND contraseña = ?");
	    	statement.setString(1, nombre);
	    	statement.setString(2, contraseña);
	    	result = statement.executeQuery();
	    	return result.next();
	    } catch (SQLException e) {
	    	return false;
	    } 
	    
	}
	
}
