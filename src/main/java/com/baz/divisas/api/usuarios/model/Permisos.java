package com.baz.divisas.api.usuarios.model;


/**
 * Permisos de accesos
 * @author B813196
 *
 */

public class Permisos {

	private boolean consultar;
	private boolean registrar;
	private boolean editar;
	private boolean borrar;
	
	/**
	 * Constructor de la clase
	 */
	public Permisos() {
		super();
	}

	public boolean isConsultar() {
		return consultar;
	}

	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public boolean isBorrar() {
		return borrar;
	}

	public void setBorrar(boolean borrar) {
		this.borrar = borrar;
	}
	
}
