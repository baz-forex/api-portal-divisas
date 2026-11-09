package com.baz.divisas.api.core.sucursales.dto;

import java.util.ArrayList;
import java.util.List;

import com.baz.divisas.api.core.sucursales.model.BeanSucursales;

/**
 * Clase RespPaginadoSucursales
 * 
 * @author 1096526
 *
 */
public class RespPaginadoSucursales {
	private List<BeanSucursales> registros;
	private long totalRegistros;
	private int registrosEnPagina;

	/**
	 * Constructor de la clase
	 */
	public RespPaginadoSucursales() {
		super();
	}

	/**
	 * @return the registros
	 */
	public List<BeanSucursales> getRegistros() {
		return new ArrayList<>(registros);
	}

	/**
	 * @param registros the registros to set
	 */
	public void setRegistros(List<BeanSucursales> registros) {
		this.registros = new ArrayList<>(registros);
	}

	/**
	 * @return the totalRegistros
	 */
	public long getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return the registrosEnPagina
	 */
	public int getRegistrosEnPagina() {
		return registrosEnPagina;
	}

	/**
	 * @param registrosEnPagina the registrosEnPagina to set
	 */
	public void setRegistrosEnPagina(int registrosEnPagina) {
		this.registrosEnPagina = registrosEnPagina;
	}

}
