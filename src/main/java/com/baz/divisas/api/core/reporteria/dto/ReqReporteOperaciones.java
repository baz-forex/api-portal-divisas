package com.baz.divisas.api.core.reporteria.dto;

import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.bson.codecs.pojo.annotations.BsonIgnore;

import com.baz.divisas.general.annotation.FormatoFechaCorta;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ReqReporteOperaciones.
 */
public class ReqReporteOperaciones {

	/** fecha inicial. */
	@NotNull(message = "Requerido")
	@FormatoFechaCorta
	@ApiModelProperty(value="Fecha inicial", example="2023-04-01", position=1)
	private String fechaInicial;
	
	/** fecha final. */
	@NotNull(message = "Requerido")
	@FormatoFechaCorta
	@ApiModelProperty(value="Fecha final", example="2023-04-30", position=2)
	private String fechaFinal;
	
	/** divisa. */
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(USD|CAD|EUR|GBP|OLP)$", message="Divisa invalida") 
	@ApiModelProperty(value="Divisa", example="USD", position=3, required=true)
	private String divisa;
	
	/** tipo operacion. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Tipo de operacion", example = "[\"412\",\"413\"]", 
			          position=4, required=true)
	private List<@Pattern(regexp="^(410|411|412|413)$", message="Tipo de operacion invalido") 
				 String> tipoOperacion;
	
	/** estatus. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Estatus", example = "[\"EXITOSO\"]", position=5, required=true)
	private List<String> estatus;
	
	/** sucursal. */
	@Pattern(regexp="[0-9]{0,4}", message="La sucursal debe ser numérica de longitud 4")
	@ApiModelProperty(value="Sucursal", example="\"2244\"", position=6)
	private String sucursal;
	
	/** id cliente. */
	@Pattern(regexp="[0-9]{0,10}", message="El idCliente deben ser numérico de longitud máxima 10")
	@ApiModelProperty(value="Id de cliente", example="\"1000\"", position=7)
	private String idCliente;
	
	/** The aplicacion origen. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Origen de la operación", example="Banca Digital", position=8, required=true)
	private String aplicacionOrigen;

	
	
	/**
	 * Constructor de la clase.
	 */
	public ReqReporteOperaciones() {
		super();
	}
	
	/**
	 * Checks if is fechas requeridas.
	 *
	 * @return true, if is fechas requeridas
	 */
	@AssertTrue(message="fechaInicial y fechaFinal requeridas")
	@BsonIgnore
	@JsonIgnore
	public boolean isFechasRequeridas() {
		
		if(idCliente == null || idCliente.isEmpty()){
			
			return fechaInicial != null && !fechaInicial.isEmpty() &&
				   fechaFinal   != null && !fechaFinal.isEmpty();
		}
		else {
			
			return true;
		}

	}

	/**
	 * Gets the fecha inicial.
	 *
	 * @return the fecha inicial
	 */
	public String getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Sets the fecha inicial.
	 *
	 * @param fechaInicial the new fecha inicial
	 */
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Gets the fecha final.
	 *
	 * @return the fecha final
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Sets the fecha final.
	 *
	 * @param fechaFinal the new fecha final
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
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
	public List<String> getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Sets the tipo operacion.
	 *
	 * @param tipoOperacion the new tipo operacion
	 */
	public void setTipoOperacion(List<String> tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Gets the estatus.
	 *
	 * @return the estatus
	 */
	public List<String> getEstatus() {
		return estatus;
	}

	/**
	 * Sets the estatus.
	 *
	 * @param estatus the new estatus
	 */
	public void setEstatus(List<String> estatus) {
		this.estatus = estatus;
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
	 * Gets the aplicacion origen.
	 *
	 * @return the aplicacion origen
	 */
	public String getAplicacionOrigen() {
		return aplicacionOrigen;
	}

	/**
	 * Sets the aplicacion origen.
	 *
	 * @param aplicacionOrigen the new aplicacion origen
	 */
	public void setAplicacionOrigen(String aplicacionOrigen) {
		this.aplicacionOrigen = aplicacionOrigen;
	}
	
}
