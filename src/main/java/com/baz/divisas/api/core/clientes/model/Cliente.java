package com.baz.divisas.api.core.clientes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase cliente
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Cliente {

	@JsonProperty("idCliente")
	@JsonAlias("id_cliente")
	private String id;
	@JsonProperty("nombre")
	private String nombre;
	@JsonProperty("apellidoPaterno")
	@JsonAlias("apellido_paterno")
	private String apellidoPaterno;
	@JsonProperty("apellidoMaterno")
	@JsonAlias("apellido_materno")
	private String apellidoMaterno;
	@JsonProperty("fechaNacimiento")
	@JsonAlias("fecha_nacimiento")
	private String fechaNacimiento;
	@JsonProperty("calle")
	private String calle;
	@JsonProperty("numeroExterior")
	@JsonAlias("numero_exterior")
	private String numeroExterior;
	@JsonProperty("numeroInterior")
	@JsonAlias("numero_interior")
	private String numeroInterior;
	@JsonProperty("codigoPostal")
	@JsonAlias("codigo_postal")
	private String codigoPostal;
	@JsonProperty("colonia")
	private String colonia;
	@JsonProperty("poblacion")
	private String poblacion;
	@JsonProperty("estado")
	private String estado;
	@JsonProperty("idGenero")
	@JsonAlias("id_genero")
	private String idGenero;
	@JsonProperty("genero")
	private String genero;
	@JsonProperty("identificaciones")
	private List<Identificacion> identificaciones = new ArrayList<>();

	
	/**
	 * Constructor de la clase
	 */
	public Cliente() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumeroExterior() {
		return numeroExterior;
	}
	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}
	public String getNumeroInterior() {
		return numeroInterior;
	}
	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIdGenero() {
		return idGenero;
	}
	public void setIdGenero(String idGenero) {
		this.idGenero = idGenero;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public List<Identificacion> getIdentificaciones() {
		return Collections.unmodifiableList(identificaciones);
	}
	public void setIdentificaciones(List<Identificacion> identificaciones) {
		this.identificaciones = Collections.unmodifiableList(identificaciones);
	}
}
