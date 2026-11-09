package com.baz.divisas.api.apigee.dto;

/**
 * The Class Resultado.
 */
public class ResultadoLlaves {

	/** The id acceso. */
	private String idAcceso;
	
	/** The acceso publico. */
	private String accesoPublico;
	
	/** The acceso privado. */
	private String accesoPrivado;
	
	/** The acceso simetrico. */
	private String accesoSimetrico;
	
	/** The codigo autentificacion hash. */
	private String codigoAutentificacionHash;
	
	/**
	 * Instantiates a new resultado.
	 */
	public ResultadoLlaves() {
		super();
	}

	/**
	 * Gets the id acceso.
	 *
	 * @return the id acceso
	 */
	public String getIdAcceso() {
		return idAcceso;
	}

	/**
	 * Sets the id acceso.
	 *
	 * @param idAcceso the new id acceso
	 */
	public void setIdAcceso(String idAcceso) {
		this.idAcceso = idAcceso;
	}

	/**
	 * Gets the acceso publico.
	 *
	 * @return the acceso publico
	 */
	public String getAccesoPublico() {
		return accesoPublico;
	}

	/**
	 * Sets the acceso publico.
	 *
	 * @param accesoPublico the new acceso publico
	 */
	public void setAccesoPublico(String accesoPublico) {
		this.accesoPublico = accesoPublico;
	}

	/**
	 * Gets the acceso privado.
	 *
	 * @return the acceso privado
	 */
	public String getAccesoPrivado() {
		return accesoPrivado;
	}

	/**
	 * Sets the acceso privado.
	 *
	 * @param accesoPrivado the new acceso privado
	 */
	public void setAccesoPrivado(String accesoPrivado) {
		this.accesoPrivado = accesoPrivado;
	}

	/**
	 * Gets the acceso simetrico.
	 *
	 * @return the acceso simetrico
	 */
	public String getAccesoSimetrico() {
		return accesoSimetrico;
	}

	/**
	 * Sets the acceso simetrico.
	 *
	 * @param accesoSimetrico the new acceso simetrico
	 */
	public void setAccesoSimetrico(String accesoSimetrico) {
		this.accesoSimetrico = accesoSimetrico;
	}

	/**
	 * Gets the codigo autentificacion hash.
	 *
	 * @return the codigo autentificacion hash
	 */
	public String getCodigoAutentificacionHash() {
		return codigoAutentificacionHash;
	}

	/**
	 * Sets the codigo autentificacion hash.
	 *
	 * @param codigoAutentificacionHash the new codigo autentificacion hash
	 */
	public void setCodigoAutentificacionHash(String codigoAutentificacionHash) {
		this.codigoAutentificacionHash = codigoAutentificacionHash;
	}

	@Override
	public String toString() {
		return "ResultadoLlaves [idAcceso=" + idAcceso + ", accesoPublico=" + accesoPublico + ", accesoPrivado="
				+ accesoPrivado + ", accesoSimetrico=" + accesoSimetrico + ", codigoAutentificacionHash="
				+ codigoAutentificacionHash + "]";
	}
	
}
