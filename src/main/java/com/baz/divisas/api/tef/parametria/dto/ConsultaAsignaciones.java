package com.baz.divisas.api.tef.parametria.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class ConsultaAsignaciones.
 */
public class ConsultaAsignaciones {
	/** The parametros. */
	private List<AsignacionParametro> asignaciones = new ArrayList<>();
	
	/** The paginacion. */
	private Paginacion paginacion;

	/**
	 * Gets the asignaciones.
	 *
	 * @return the asignaciones
	 */
	public List<AsignacionParametro> getAsignaciones() {
		return Collections.unmodifiableList(asignaciones);
	}

	/**
	 * Sets the asignaciones.
	 *
	 * @param asignaciones the new asignaciones
	 */
	public void setAsignaciones(List<AsignacionParametro> asignaciones) {
		this.asignaciones = Collections.unmodifiableList(asignaciones);
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
