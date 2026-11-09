package com.baz.divisas.api.bitacora.model;

import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Bitacora
 * @author B813196
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Bitacora {

	private String id;
	private String idModulo;
	private String idUsuario;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String tipoAccion;
	private String descripcionAccion;
	@BsonIgnore
	private boolean detalle;
	private Document datosAnteriores;
	private Document datosActuales;
	
	/**
	 * Constructor de la clase
	 */
	public Bitacora() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
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
	public String getTipoAccion() {
		return tipoAccion;
	}
	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
	public String getDescripcionAccion() {
		return descripcionAccion;
	}
	public void setDescripcionAccion(String descripcionAccion) {
		this.descripcionAccion = descripcionAccion;
	}
	public boolean isDetalle() {
		return detalle;
	}
	public void setDetalle(boolean detalle) {
		this.detalle = detalle;
	}
	public Document getDatosAnteriores() {
		return datosAnteriores;
	}
	public void setDatosAnteriores(Document datosAnteriores) {
		this.datosAnteriores = datosAnteriores;
	}
	public Document getDatosActuales() {
		return datosActuales;
	}
	public void setDatosActuales(Document datosActuales) {
		this.datosActuales = datosActuales;
	}

	@Override
	public String toString() {
		return "Bitacora [id=" + id + ", idModulo=" + idModulo + ", idUsuario=" + idUsuario + ", nombre=" + nombre
				+ ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", tipoAccion="
				+ tipoAccion + ", descripcionAccion=" + descripcionAccion + ", detalle=" + detalle
				+ ", datosAnteriores=" + datosAnteriores + ", datosActuales=" + datosActuales + "]";
	}

}
