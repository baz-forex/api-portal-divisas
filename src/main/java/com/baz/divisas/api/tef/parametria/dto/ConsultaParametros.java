package com.baz.divisas.api.tef.parametria.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * The Class ResConsultaParametros.
 */
public class ConsultaParametros{

	/** The parametros. */
	private List<Parametro> parametros = new ArrayList<>();
	
	/** The paginacion. */
	private Paginacion paginacion;
	
	/**
	 * Instantiates a new res consulta parametros.
	 */
	public ConsultaParametros() {
		super();
	}
	
	/**
	 * Gets the parametros.
	 *
	 * @return the parametros
	 */
	public List<Parametro> getParametros() {
		return Collections.unmodifiableList(parametros);
	}


	/**
	 * Sets the parametros.
	 *
	 * @param parametros the new parametros
	 */
	public void setParametros(List<Parametro> parametros) {
		this.parametros = Collections.unmodifiableList(parametros);
	}
	
	/**
	 * Gets the paginacion.
	 *
	 * @return the paginacion
	 */
	public Paginacion getPaginacion() {
		return paginacion;
	}
	
	/**
	 * Sets the paginacion.
	 *
	 * @param paginacion the new paginacion
	 */
	public void setPaginacion(Paginacion paginacion) {
		this.paginacion = paginacion;
	}
	
}
