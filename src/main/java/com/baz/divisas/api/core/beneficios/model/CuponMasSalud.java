package com.baz.divisas.api.core.beneficios.model;

/**
 * The Class CuponMasSalud.
 */
public class CuponMasSalud {

	/** The id. */
	private String id;
	
	/** The tipo. */
	private String tipo;
	
	/** The estado. */
	private String estado = "disponible";
	
	/** The divisa. */
	private String divisa;
	
	/** The fecha entrega. */
	private String fechaEntrega;
	
	/** The hora entrega. */
	private String horaEntrega;
	
	/** The id cliente. */
	private String idCliente;
	
	/** The monto. */
	private String monto;
	
	/** The nombre cliente. */
	private String nombreCliente;
	
	/** The sucursal. */
	private String sucursal;

	/**
	 * Instantiates a new cupon Mas Salud.
	 */
	public CuponMasSalud() {
		super();
	}

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
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo the new tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Sets the estado.
	 *
	 * @param estado the new estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
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
	 * Gets the fecha entrega.
	 *
	 * @return the fecha entrega
	 */
	public String getFechaEntrega() {
		return fechaEntrega;
	}

	/**
	 * Sets the fecha entrega.
	 *
	 * @param fechaEntrega the new fecha entrega
	 */
	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	/**
	 * Gets the hora entrega.
	 *
	 * @return the hora entrega
	 */
	public String getHoraEntrega() {
		return horaEntrega;
	}

	/**
	 * Sets the hora entrega.
	 *
	 * @param horaEntrega the new hora entrega
	 */
	public void setHoraEntrega(String horaEntrega) {
		this.horaEntrega = horaEntrega;
	}

	/**
	 * Gets the id cliente.
	 *
	 * @return the id cliente
	 */
	public String getIdCliente() {
		return idCliente;
	}

	/**
	 * Sets the id cliente.
	 *
	 * @param idCliente the new id cliente
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Gets the monto.
	 *
	 * @return the monto
	 */
	public String getMonto() {
		return monto;
	}

	/**
	 * Sets the monto.
	 *
	 * @param monto the new monto
	 */
	public void setMonto(String monto) {
		this.monto = monto;
	}

	/**
	 * Gets the nombre cliente.
	 *
	 * @return the nombre cliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}

	/**
	 * Sets the nombre cliente.
	 *
	 * @param nombreCliente the new nombre cliente
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	/**
	 * Gets the sucursal.
	 *
	 * @return the sucursal
	 */
	public String getSucursal() {
		return sucursal;
	}

	/**
	 * Sets the sucursal.
	 *
	 * @param sucursal the new sucursal
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

}
