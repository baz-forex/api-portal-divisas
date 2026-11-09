package com.baz.divisas.api.core.sucursales.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Clase ConsultaRespuestasSucursal
 * 
 * @author 1096526
 *
 */
public class ConsultaRespuestasSucursal {
	private int paginaActual;
	private long paginas;
	private int elementosPagina;
	private int avanceTotal;
	private List<Cuestionario> cuestionarios = new ArrayList<>();
	@BsonIgnore
	@JsonIgnore
	private long totalRegistros;

	/**
	 * Constructor de la clase
	 * 
	 * @param preguntas - lista con los campos de las preguntas
	 */
	public ConsultaRespuestasSucursal() {
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
	 * @return the avanceTotal
	 */
	public int getAvanceTotal() {
		return avanceTotal;
	}

	/**
	 * @param avanceTotal the avanceTotal to set
	 */
	public void setAvanceTotal(int avanceTotal) {
		this.avanceTotal = avanceTotal;
	}

	/**
	 * @return the cuestionarios
	 */
	public List<Cuestionario> getCuestionarios() {
		return new ArrayList<>(cuestionarios);
	}

	/**
	 * @param cuestionarios the cuestionarios to set
	 */
	public void setCuestionarios(List<Cuestionario> cuestionarios) {
		this.cuestionarios = new ArrayList<>(cuestionarios);
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

}
