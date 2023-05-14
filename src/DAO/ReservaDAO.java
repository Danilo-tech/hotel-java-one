package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Reserva;

public class ReservaDAO {

	final private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Reserva reserva) {
		
		try {
			final PreparedStatement statement = con.prepareStatement("INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_pago)"
					+ " VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				ejecutarRegistro(reserva, statement);
			} 
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	
	}

	private void ejecutarRegistro(Reserva reserva, PreparedStatement statement)
			throws SQLException {
		
		statement.setObject(1, reserva.getFechaEntrada());
		statement.setObject(2, reserva.getFechaSalida());
		statement.setString(3, reserva.getValor());
		statement.setString(4, reserva.getFormaPago());
		
		statement.executeUpdate();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		
		try (resultSet) {
			while(resultSet.next()) {
				reserva.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertada la reserva %s", reserva));
			}
		} catch (SQLException e) {
			throw new RuntimeException("animal" + e.getMessage(), e);
		}
		
	}
	
	public List<Reserva> listar() {
		
		List<Reserva> resultado = new ArrayList<>();
		
		try {
			String querySelect = "SELECT id, fecha_Entrada, fecha_Salida, valor, forma_Pago FROM reservas";
			System.out.println(querySelect);
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			
			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				
				try (resultSet) {
					while(resultSet.next()) {
						Integer id = resultSet.getInt("id");
						LocalDate fechaEntrada = resultSet.getDate("fecha_Entrada").toLocalDate().plusDays(1);
						LocalDate fechaSalida = resultSet.getDate("fecha_Salida").toLocalDate().plusDays(1);
						String valor = resultSet.getString("valor");
						String formaPago = resultSet.getString("forma_Pago");
						
						Reserva reserva = new Reserva(id, fechaEntrada, fechaSalida, valor, formaPago);
						resultado.add(reserva);
					}
				};
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return resultado;
	}
	
	public List<Reserva> listarPorId(String id) {
		
		List<Reserva> resultado = new ArrayList<>();
		
		try {
			String querySelect = "SELECT id, fecha_Entrada, fecha_Salida, valor, forma_Pago"
					+ " FROM reservas"
					+ " WHERE id = ?";
			System.out.println(querySelect);
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			
			try (statement) {
				
				statement.setString(1, id);
				statement.execute();
				
				final ResultSet resultSet = statement.executeQuery();
				
				try (resultSet) {
					while(resultSet.next()) {
						Integer Id = resultSet.getInt("id");
						LocalDate fechaEntrada = resultSet.getDate("fecha_Entrada").toLocalDate().plusDays(1);
						LocalDate fechaSalida = resultSet.getDate("fecha_Salida").toLocalDate().plusDays(1);
						String valor = resultSet.getString("valor");
						String formaPago = resultSet.getString("forma_Pago");
						
						Reserva reserva = new Reserva(Id, fechaEntrada, fechaSalida, valor, formaPago);
						resultado.add(reserva);
					}
				};
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
	
	public int modificar(LocalDate fechaEntrada, LocalDate fechaSalida, String valor, String formaPago, Integer id) {
		
		try {
			
			final PreparedStatement statement = con.prepareStatement("UPDATE reservas SET"
					+ " fecha_entrada = ?"
					+ ", fecha_salida = ?"
					+ ", valor = ?"
					+ ", forma_pago = ?"
					+ " WHERE id = ?");
			System.out.println(statement);

			try (statement) {
				
				statement.setObject(1, java.sql.Date.valueOf(fechaEntrada));
				statement.setObject(2, java.sql.Date.valueOf(fechaSalida));
				statement.setString(3, valor);
				statement.setString(4, formaPago);
				statement.setInt(5, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
				
			}
				
		} catch (SQLException e) {
				throw new RuntimeException(e);
		}
		
	}

	public int eliminar(Integer id) {
		
		try{
			Statement state = con.createStatement();
			state.execute("SET FOREIGN_KEY_CHECKS = 0");

			final PreparedStatement statement = con.prepareStatement("DELETE FROM reservas WHERE ID = ?");
			
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
