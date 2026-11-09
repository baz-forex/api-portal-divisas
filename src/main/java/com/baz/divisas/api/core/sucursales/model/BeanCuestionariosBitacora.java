package com.baz.divisas.api.core.sucursales.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase BeanCuestionariosBitacora
 * 
 * @author 1096526
 *
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
public class BeanCuestionariosBitacora extends BeanCuestionarios {

	private String idCuestionario;
	private String evento;
	private boolean certificar;

	/**
	 * Constructor de la clase
	 */
	public BeanCuestionariosBitacora() {
		super();
	}

	/**
	 * @return the idCuestionario
	 */
	public String getIdCuestionario() {
		return idCuestionario;
	}

	/**
	 * @param idCuestionario the idCuestionario to set
	 */
	public void setIdCuestionario(String idCuestionario) {
		this.idCuestionario = idCuestionario;
	}

	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}

	/**
	 * @return the certificar
	 */
	public boolean isCertificar() {
		return certificar;
	}

	/**
	 * @param certificar the certificar to set
	 */
	public void setCertificar(boolean certificar) {
		this.certificar = certificar;
	}

}
