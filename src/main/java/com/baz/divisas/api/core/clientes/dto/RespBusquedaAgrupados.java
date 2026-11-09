package com.baz.divisas.api.core.clientes.dto;

import java.util.List;

import org.bson.Document;

import com.baz.divisas.general.dto.Paginacion;

/**
 * The Class PaginacionResponse.
 */
public class RespBusquedaAgrupados extends Paginacion {

	/** The registros. */
	private List<Document> agrupados;
	
	
	/**
	 * Instantiates a new paginacion response.
	 *
	 * @param pagina the pagina
	 * @param registrosPorPagina the registros por pagina
	 * @param totalRegistros the total registros
	 * @param agrupados the agrupados
	 */
	public RespBusquedaAgrupados(int pagina, int registrosPorPagina, long totalRegistros, List<Document> agrupados) {
		super(pagina, registrosPorPagina, totalRegistros);
		this.agrupados = agrupados;
	}


	/**
	 * Gets the agrupados.
	 *
	 * @return the agrupados
	 */
	public List<Document> getAgrupados() {
		return agrupados;
	}


	/**
	 * Sets the agrupados.
	 *
	 * @param agrupados the new agrupados
	 */
	public void setAgrupados(List<Document> agrupados) {
		this.agrupados = agrupados;
	}
	
}
