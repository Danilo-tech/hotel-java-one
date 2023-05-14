package controller;

import java.time.LocalDate;
import java.util.List;

import DAO.HuespedDAO;
import factory.ConnectionFactory;
import modelo.Huesped;

public class HuespedController {

	private HuespedDAO huespedDAO;
	
	public HuespedController() {
		this.huespedDAO = new HuespedDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Huesped huesped) {
    	this.huespedDAO.guardar(huesped);
	}
	
	public List<Huesped> listar() {
		return this.huespedDAO.listar();
	}
	
	public List<Huesped> listarPorId(String id) {
		return this.huespedDAO.listarPorId(id);
	}
	
	public int modificar(Integer id, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva) {
		return this.huespedDAO.modificar(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
	}

	public int eliminar(Integer id) {
		return this.huespedDAO.eliminar(id);
	}
	
}
