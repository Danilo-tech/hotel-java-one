package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class PruebaConexion {

	 public static void main(String[] args) throws SQLException {
		System.out.println("Abriendo la conexion");
	    Connection con = new ConnectionFactory().recuperaConexion();
	    	
	    System.out.println("Cerrando la conexión");

	    con.close();
	 }
	
}
