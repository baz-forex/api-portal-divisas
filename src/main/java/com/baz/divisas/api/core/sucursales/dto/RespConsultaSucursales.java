package com.baz.divisas.api.core.sucursales.dto;

import java.util.ArrayList;
import java.util.List;

import com.baz.divisas.api.core.sucursales.model.BeanSucursales;

/**
 * Clase RespConsultaSucursales
 * 
 * @author 1096526
 *
 */
public class RespConsultaSucursales {
	private int paginaActual;
	private long paginas;
	private int elementosPagina;
	private List<BeanSucursales> sucursales = new ArrayList<>();

	/**
	 * Constructor de la clase
	 */
	public RespConsultaSucursales() {
		super();
	}

	/**
	 * @return the paginaActual
	 */
	public int getPaginaActual() {
		return paginaActual;
	}

	/**
	 * @param paginaActual the paginaActual to set
	 */
	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	/**
	 * @return the paginas
	 */
	public long getPaginas() {
		return paginas;
	}

	/**
	 * @param paginas the paginas to set
	 */
	public void setPaginas(long paginas) {
		this.paginas = paginas;
	}

	/**
	 * @return the elementosPagina
	 */
	public int getElementosPagina() {
		return elementosPagina;
	}

	/**
	 * @param elementosPagina the elementosPagina to set
	 */
	public void setElementosPagina(int elementosPagina) {
		this.elementosPagina = elementosPagina;
	}

	/**
	 * @return the sucursales
	 */
	public List<BeanSucursales> getSucursales() {
		return new ArrayList<>(sucursales);
	}

	/**
	 * @param sucursales the sucursales to set
	 */
	public void setSucursales(List<BeanSucursales> sucursales) {
		this.sucursales = new ArrayList<>(sucursales);
	}

}
