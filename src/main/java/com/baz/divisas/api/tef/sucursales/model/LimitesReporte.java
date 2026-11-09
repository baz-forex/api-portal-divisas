package com.baz.divisas.api.tef.sucursales.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class LimitesReporte.
 */
public class LimitesReporte {
	/** The id. */
	@SerializedName("_id")
	private String id;
	
	/** The divisa. */
	private String divisa;
	
	/** The tipo operacion. */
	private String tipoOperacion;
	
	/** The cliente diario. */
	private String clienteDiario;
	
	/** The usuario diario. */
	private String usuarioDiario;
	
	/** The cliente mensual. */
	private String clienteMensual;
	
	/** The usuario mensual. */
	private String usuarioMensual;
	
	/** The extranjero usuario diario. */
	private String extranjeroUsuarioDiario;
	
	/** The extranjero cliente diario. */
	private String extranjeroClienteDiario;
	
	/** The extranjero usuario mensual. */
	private String extranjeroUsuarioMensual;	
	
	/** The extranjero cliente mensual. */
	private String extranjeroClienteMensual;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the divisa.
	 *
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * Sets the divisa.
	 *
	 * @param divisa the new divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * Gets the tipo operacion.
	 *
	 * @return the tipo operacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Sets the tipo operacion.
	 *
	 * @param tipoOperacion the new tipo operacion
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Gets the cliente diario.
	 *
	 * @return the cliente diario
	 */
	public String getClienteDiario() {
		return clienteDiario;
	}

	/**
	 * Sets the cliente diario.
	 *
	 * @param clienteDiario the new cliente diario
	 */
	public void setClienteDiario(String clienteDiario) {
		this.clienteDiario = clienteDiario;
	}

	/**
	 * Gets the usuario diario.
	 *
	 * @return the usuario diario
	 */
	public String getUsuarioDiario() {
		return usuarioDiario;
	}

	/**
	 * Sets the usuario diario.
	 *
	 * @param usuarioDiario the new usuario diario
	 */
	public void setUsuarioDiario(String usuarioDiario) {
		this.usuarioDiario = usuarioDiario;
	}

	/**
	 * Gets the cliente mensual.
	 *
	 * @return the cliente mensual
	 */
	public String getClienteMensual() {
		return clienteMensual;
	}

	/**
	 * Sets the cliente mensual.
	 *
	 * @param clienteMensual the new cliente mensual
	 */
	public void setClienteMensual(String clienteMensual) {
		this.clienteMensual = clienteMensual;
	}

	/**
	 * Gets the usuario mensual.
	 *
	 * @return the usuario mensual
	 */
	public String getUsuarioMensual() {
		return usuarioMensual;
	}

	/**
	 * Sets the usuario mensual.
	 *
	 * @param usuarioMensual the new usuario mensual
	 */
	public void setUsuarioMensual(String usuarioMensual) {
		this.usuarioMensual = usuarioMensual;
	}

	/**
	 * Gets the extranjero usuario diario.
	 *
	 * @return the extranjero usuario diario
	 */
	public String getExtranjeroUsuarioDiario() {
		return extranjeroUsuarioDiario;
	}

	/**
	 * Sets the extranjero usuario diario.
	 *
	 * @param extranjeroUsuarioDiario the new extranjero usuario diario
	 */
	public void setExtranjeroUsuarioDiario(String extranjeroUsuarioDiario) {
		this.extranjeroUsuarioDiario = extranjeroUsuarioDiario;
	}

	/**
	 * Gets the extranjero cliente diario.
	 *
	 * @return the extranjero cliente diario
	 */
	public String getExtranjeroClienteDiario() {
		return extranjeroClienteDiario;
	}

	/**
	 * Sets the extranjero cliente diario.
	 *
	 * @param extranjeroClienteDiario the new extranjero cliente diario
	 */
	public void setExtranjeroClienteDiario(String extranjeroClienteDiario) {
		this.extranjeroClienteDiario = extranjeroClienteDiario;
	}

	/**
	 * Gets the extranjero usuario mensual.
	 *
	 * @return the extranjero usuario mensual
	 */
	public String getExtranjeroUsuarioMensual() {
		return extranjeroUsuarioMensual;
	}

	/**
	 * Sets the extranjero usuario mensual.
	 *
	 * @param extranjeroUsuarioMensual the new extranjero usuario mensual
	 */
	public void setExtranjeroUsuarioMensual(String extranjeroUsuarioMensual) {
		this.extranjeroUsuarioMensual = extranjeroUsuarioMensual;
	}

	/**
	 * Gets the extranjero cliente mensual.
	 *
	 * @return the extranjero cliente mensual
	 */
	public String getExtranjeroClienteMensual() {
		return extranjeroClienteMensual;
	}

	/**
	 * Sets the extranjero cliente mensual.
	 *
	 * @param extranjeroClienteMensual the new extranjero cliente mensual
	 */
	public void setExtranjeroClienteMensual(String extranjeroClienteMensual) {
		this.extranjeroClienteMensual = extranjeroClienteMensual;
	}	
	
}
