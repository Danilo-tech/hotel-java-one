package controller;

import java.time.LocalDate;
import java.util.List;

import DAO.ReservaDAO;
import factory.ConnectionFactory;
import modelo.Reserva;

public class ReservaController {

	private ReservaDAO reservaDAO;
	
	public ReservaController() {
		this.reservaDAO = new ReservaDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Reserva reserva) {
    	this.reservaDAO.guardar(reserva);
    }	
	
	public List<Reserva> listar() {
		return this.reservaDAO.listar();
	}
	
	public List<Reserva> listarPorId(String id) {
		return this.reservaDAO.listarPorId(id);
	}
	
	public int modificar(LocalDate fechaEntrada, LocalDate fechaSalida, String valor, String formaPago, Integer id) {
		return this.reservaDAO.modificar(fechaEntrada, fechaSalida, valor, formaPago, id);
	}
	
	public int eliminar(Integer id) {
        return this.reservaDAO.eliminar(id);
    }
	
}
