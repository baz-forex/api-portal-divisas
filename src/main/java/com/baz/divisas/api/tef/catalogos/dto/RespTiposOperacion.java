package com.baz.divisas.api.tef.catalogos.dto;

import java.util.List;

import javax.validation.Valid;

import com.baz.divisas.api.catalogos.model.Catalogo;

/**
 * The Class RespTiposOperacion.
 */
public class RespTiposOperacion {
	
	/** The list operaciones. */
	@Valid
	private  List<Catalogo> listOperaciones;

	/**
	 * Constructor de la clase.
	 *
	 * @param listOperaciones - Tipos de operacion
	 */
	public RespTiposOperacion(List<Catalogo> listOperaciones) {
		super();
		this.listOperaciones = listOperaciones;
	}

	/**
	 * Gets the list operaciones.
	 *
	 * @return the list operaciones
	 */
	public List<Catalogo> getListOperaciones() {
		return listOperaciones;
	}

	/**
	 * Sets the list operaciones.
	 *
	 * @param listOperaciones the new list operaciones
	 */
	public void setListOperaciones(List<Catalogo> listOperaciones) {
		this.listOperaciones = listOperaciones;
	}
	
}
