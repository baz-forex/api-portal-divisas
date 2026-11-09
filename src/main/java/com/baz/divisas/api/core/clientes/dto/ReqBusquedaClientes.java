package com.baz.divisas.api.core.clientes.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.baz.divisas.general.annotation.FormatoFechaCorta;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase ReqBusquedaClientes
 * @author B813196
 * itoxtle
 */
public class ReqBusquedaClientes {

	@JsonProperty("id_negocio")
	@ApiModelProperty(hidden=true)
	private String idNegocio = "DIV";
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(1|2|3)$", message="Tipo de Busqueda invalida") 
	@ApiModelProperty(value="Tipo Busqueda", example="3", position=1, required=true)
	private String tipoBusqueda;
	@ApiModelProperty(value="Id Cliente", example="15247222", position=2)
	private String idCliente;
	@ApiModelProperty(value="Tipo de Identificacion", example="1", position=3)
	private String tipoIdentificacion;
	@ApiModelProperty(value="Folio de Identificacion", example="1234567890001", position=4)
	private String folioIdentificacion;
	@ApiModelProperty(value="Nombre", example="MARIA ALEJANDRA", position=5)
	private String nombre;
	@ApiModelProperty(value="Apellido Paterno", example="SERRATO", position=6)
	private String apellidoPaterno;
	@ApiModelProperty(value="Apellido Materno", example="DE LA O", position=7)
	private String apellidoMaterno;
	@FormatoFechaCorta
	@ApiModelProperty(value="Fecha de Nacimiento", example="1969-04-24", position=8)
	private String fechaNacimiento;
	
	
	/**
	 * Constructor de la clase
	 */
	public ReqBusquedaClientes() {
		super();
	}

	public String getIdNegocio() {
		return idNegocio;
	}
	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}
	@JsonProperty(value = "tipo_busqueda", access = JsonProperty.Access.READ_ONLY)
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}
	@JsonProperty(value = "tipoBusqueda", access = JsonProperty.Access.WRITE_ONLY)
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	@JsonProperty(value = "id_cliente", access = JsonProperty.Access.READ_ONLY)
	public String getIdCliente() {
		return idCliente;
	}
	@JsonProperty(value = "idCliente", access = JsonProperty.Access.WRITE_ONLY)
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	@JsonProperty(value = "tipo_identificacion", access = JsonProperty.Access.READ_ONLY)
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	@JsonProperty(value = "tipoIdentificacion", access = JsonProperty.Access.WRITE_ONLY)
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	@JsonProperty(value = "folio_identificacion", access = JsonProperty.Access.READ_ONLY)
	public String getFolioIdentificacion() {
		return folioIdentificacion;
	}
	@JsonProperty(value = "folioIdentificacion", access = JsonProperty.Access.WRITE_ONLY)
	public void setFolioIdentificacion(String folioIdentificacion) {
		this.folioIdentificacion = folioIdentificacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@JsonProperty(value = "apellido_paterno", access = JsonProperty.Access.READ_ONLY)
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	@JsonProperty(value = "apellidoPaterno", access = JsonProperty.Access.WRITE_ONLY)
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	@JsonProperty(value = "apellido_materno", access = JsonProperty.Access.READ_ONLY)
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	@JsonProperty(value = "apellidoMaterno", access = JsonProperty.Access.WRITE_ONLY)
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	@JsonProperty(value = "fecha_nacimiento", access = JsonProperty.Access.READ_ONLY)
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	@JsonProperty(value = "fechaNacimiento", access = JsonProperty.Access.WRITE_ONLY)
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}
