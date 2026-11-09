package com.baz.divisas.api.core.reporteria.model;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;


/**
 * The Class GanadorPromoDigital.
 */
public class GanadorPromoDigital {

	/** The id. */
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	
	/** The nombre campania. */
	private String nombreCampania;
	
	/** The origen. */
	private String origen;
	
	/** The codigo. */
	private String codigo;
	
	/** The beneficio. */
	private double beneficio;
	
	/** The monto minimo operacion. */
	private int montoMinimoOperacion;
	
	/** The codigo divisa. */
	private String codigoDivisa;
	
	/** The estatus. */
	private String estatus;
	
	/** The fecha registro. */
	private String fechaRegistro;
	
	/** The vigencia. */
	private String vigencia;
	
	/** The fecha actualizacion. */
	private String fechaActualizacion;
	
	/** The cliente. */
	private ClienteGanador cliente;
	
	
	/**
	 * Instantiates a new ganador promo digital.
	 */
	public GanadorPromoDigital() {
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
	 * Gets the nombre campania.
	 *
	 * @return the nombre campania
	 */
	public String getNombreCampania() {
		return nombreCampania;
	}
	
	/**
	 * Sets the nombre campania.
	 *
	 * @param nombreCampania the new nombre campania
	 */
	public void setNombreCampania(String nombreCampania) {
		this.nombreCampania = nombreCampania;
	}
	
	/**
	 * Gets the origen.
	 *
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}
	
	/**
	 * Sets the origen.
	 *
	 * @param origen the new origen
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	
	/**
	 * Sets the codigo.
	 *
	 * @param codigo the new codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Gets the beneficio.
	 *
	 * @return the beneficio
	 */
	public double getBeneficio() {
		return beneficio;
	}
	
	/**
	 * Sets the beneficio.
	 *
	 * @param beneficio the new beneficio
	 */
	public void setBeneficio(double beneficio) {
		this.beneficio = beneficio;
	}
	
	/**
	 * Gets the monto minimo operacion.
	 *
	 * @return the monto minimo operacion
	 */
	public int getMontoMinimoOperacion() {
		return montoMinimoOperacion;
	}
	
	/**
	 * Sets the monto minimo operacion.
	 *
	 * @param montoMinimoOperacion the new monto minimo operacion
	 */
	public void setMontoMinimoOperacion(int montoMinimoOperacion) {
		this.montoMinimoOperacion = montoMinimoOperacion;
	}
	
	/**
	 * Gets the codigo divisa.
	 *
	 * @return the codigo divisa
	 */
	public String getCodigoDivisa() {
		return codigoDivisa;
	}
	
	/**
	 * Sets the codigo divisa.
	 *
	 * @param codigoDivisa the new codigo divisa
	 */
	public void setCodigoDivisa(String codigoDivisa) {
		this.codigoDivisa = codigoDivisa;
	}
	
	/**
	 * Gets the estatus.
	 *
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	
	/**
	 * Sets the estatus.
	 *
	 * @param estatus the new estatus
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
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
	 * Gets the vigencia.
	 *
	 * @return the vigencia
	 */
	public String getVigencia() {
		return vigencia;
	}
	
	/**
	 * Sets the vigencia.
	 *
	 * @param vigencia the new vigencia
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	
	/**
	 * Gets the fecha actualizacion.
	 *
	 * @return the fecha actualizacion
	 */
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	
	/**
	 * Sets the fecha actualizacion.
	 *
	 * @param fechaActualizacion the new fecha actualizacion
	 */
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	/**
	 * Gets the cliente.
	 *
	 * @return the cliente
	 */
	public ClienteGanador getCliente() {
		return cliente;
	}
	
	/**
	 * Sets the cliente.
	 *
	 * @param cliente the new cliente
	 */
	public void setCliente(ClienteGanador cliente) {
		this.cliente = cliente;
	}		
	
}
