package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Huesped;

public class HuespedDAO {

	final private Connection con;

	public HuespedDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Huesped huesped) {
		
		try {
			final PreparedStatement statement = con.prepareStatement("INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva)"
					+ " VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				ejecutarRegistro(huesped, statement);
			} 
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
		
	}
	
	private void ejecutarRegistro(Huesped huesped, PreparedStatement statement)
			throws SQLException {
		
		statement.setString(1, huesped.getNombre());
		statement.setString(2, huesped.getApellido());
		statement.setObject(3, huesped.getFechaNacimiento());
		statement.setString(4, huesped.getNacionalidad());
		statement.setString(5, huesped.getTelefono());
		statement.setInt(6, huesped.getIdReserva());
		
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		
		try (resultSet) {
			while(resultSet.next()) {
				huesped.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el huesped %s", huesped));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Huesped> listar() {
		
		List<Huesped> resultado = new ArrayList<Huesped>();
		
		try {
			String querySelect = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes";
			System.out.println(querySelect);
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			
			try (statement) {
				resultadoResultSet(resultado, statement);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
	
	public List<Huesped> listarPorId(String id) {
		
		List<Huesped> resultado = new ArrayList<Huesped>();
		
		try {
			String querySelect = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva"
					+ " FROM huespedes"
					+ " WHERE id = ?";
			System.out.println(querySelect);
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			
			try (statement) {
				statement.setString(1, id);
				statement.execute();
				resultadoResultSet(resultado, statement);
			} 
			return resultado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int modificar(Integer id, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
		Integer idReserva) {
		
		try {
			
			final PreparedStatement statement = con.prepareStatement("UPDATE huespedes SET"
					+ " nombre = ?"
					+ ", apellido = ?"
					+ ", fecha_nacimiento = ?"
					+ ", nacionalidad = ?"
					+ ", telefono = ?"
					+ ", id_reserva = ?"
					+ " WHERE id = ?");
			System.out.println(statement);

			try (statement) {
				
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setObject(3, fechaNacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setInt(6, idReserva);
				statement.setInt(7, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
				
			}
				
		} catch (SQLException e) {
				throw new RuntimeException(e);
		}
		
	}

	private void resultadoResultSet(List<Huesped> resultado, PreparedStatement statement) throws SQLException {
		
		final ResultSet resultSet = statement.executeQuery();
		
		try (resultSet) {
			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				String apellido = resultSet.getString("apellido");
				LocalDate fechaNacimiento = resultSet.getDate("fecha_nacimiento").toLocalDate().plusDays(1);
				String nacionalidad = resultSet.getString("nacionalidad");
				String telefono = resultSet.getString("telefono");
				Integer idReserva = resultSet.getInt("id_reserva");
				
				Huesped huesped = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
				resultado.add(huesped);
			}
		}
		
	}
	
	public int eliminar(Integer id) {
		
		try{
			Statement state = con.createStatement();
			state.execute("SET FOREIGN_KEY_CHECKS = 0");

			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE ID = ?");
			
			try (statement) {
				
				statement.setInt(1, id);
				statement.execute();
				state.execute("SET FOREIGN_KEY_CHECKS = 1");
				
				int updateCount = statement.getUpdateCount();
				return updateCount;
				
			}
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}

