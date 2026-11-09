package com.baz.divisas.api.core.sucursales.dto;

import java.util.ArrayList;
import java.util.List;

import com.baz.divisas.api.core.sucursales.model.ConsultaRespuestas;

/**
 * Clase RespConsultaRespuestas
 * 
 * @author 1096526
 *
 */
public class RespConsultaRespuestas {
	private int paginaActual;
	private long paginas;
	private int elementosPagina;
	private List<ConsultaRespuestas> respuestas = new ArrayList<>();

	/**
	 * Constructor de la clase
	 */
	public RespConsultaRespuestas() {
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
	 * @return the respuestas
	 */
	public List<ConsultaRespuestas> getRespuestas() {
		return new ArrayList<>(respuestas);
	}

	/**
	 * @param respuestas the respuestas to set
	 */
	public void setRespuestas(List<ConsultaRespuestas> respuestas) {
		this.respuestas = new ArrayList<>(respuestas);
	}

}
