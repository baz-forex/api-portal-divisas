/**
 * 
 */
package com.baz.divisas.api.tef.spots.dto;

import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase SpotDTO.
 *
 * @author 1077023
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Spot {

	/** The id. */
	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	
	/** The tipo. */
	private String tipo;
	
	/** The id divisa. */
	@NotNull(message = "Requerido")
	private String idDivisa;
	
	/** The compra efectivo. */
	@NotNull(message = "Requerido")
	private Double compraEfectivo;
	
	/** The compra abono. */
	@NotNull(message = "Requerido")
	private Double compraAbono;
	
	/** The venta efectivo. */
	@NotNull(message = "Requerido")
	private Double ventaEfectivo;
	
	/** The venta cargo. */
	@NotNull(message = "Requerido")
	private Double ventaCargo;
	
	/** The fecha registro. */
	private String fechaRegistro;
	
	/** The hora registro. */
	private String horaRegistro;
	
	/** The id bitacora. */
	private String idBitacora;
	
		/**
	 * Constructor de la clase.
	 */
	public Spot() {
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
	 * Gets the id divisa.
	 *
	 * @return the id divisa
	 */
	public String getIdDivisa() {
		return idDivisa;
	}

	/**
	 * Sets the id divisa.
	 *
	 * @param idDivisa the new id divisa
	 */
	public void setIdDivisa(String idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * Gets the compra efectivo.
	 *
	 * @return the compra efectivo
	 */
	public Double getCompraEfectivo() {
		return compraEfectivo;
	}

	/**
	 * Sets the compra efectivo.
	 *
	 * @param compraEfectivo the new compra efectivo
	 */
	public void setCompraEfectivo(Double compraEfectivo) {
		this.compraEfectivo = compraEfectivo;
	}

	/**
	 * Gets the compra abono.
	 *
	 * @return the compra abono
	 */
	public Double getCompraAbono() {
		return compraAbono;
	}

	/**
	 * Sets the compra abono.
	 *
	 * @param compraAbono the new compra abono
	 */
	public void setCompraAbono(Double compraAbono) {
		this.compraAbono = compraAbono;
	}

	/**
	 * Gets the venta efectivo.
	 *
	 * @return the venta efectivo
	 */
	public Double getVentaEfectivo() {
		return ventaEfectivo;
	}

	/**
	 * Sets the venta efectivo.
	 *
	 * @param ventaEfectivo the new venta efectivo
	 */
	public void setVentaEfectivo(Double ventaEfectivo) {
		this.ventaEfectivo = ventaEfectivo;
	}

	/**
	 * Gets the venta cargo.
	 *
	 * @return the venta cargo
	 */
	public Double getVentaCargo() {
		return ventaCargo;
	}

	/**
	 * Sets the venta cargo.
	 *
	 * @param ventaCargo the new venta cargo
	 */
	public void setVentaCargo(Double ventaCargo) {
		this.ventaCargo = ventaCargo;
	}

	/**
	 * Gets the fecha registro.
	 *
	 * @return the fecha registro
	 */
	public String getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Sets the fecha registro.
	 *
	 * @param fechaRegistro the new fecha registro
	 */
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Gets the hora registro.
	 *
	 * @return the hora registro
	 */
	public String getHoraRegistro() {
		return horaRegistro;
	}

	/**
	 * Sets the hora registro.
	 *
	 * @param horaRegistro the new hora registro
	 */
	public void setHoraRegistro(String horaRegistro) {
		this.horaRegistro = horaRegistro;
	}

	/**
	 * Gets the id bitacora.
	 *
	 * @return the id bitacora
	 */
	public String getIdBitacora() {
		return idBitacora;
	}

	/**
	 * Sets the id bitacora.
	 *
	 * @param idBitacora the new id bitacora
	 */
	public void setIdBitacora(String idBitacora) {
		this.idBitacora = idBitacora;
	}
}
