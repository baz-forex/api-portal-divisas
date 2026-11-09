package com.baz.divisas.api.core.reporteria.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import org.bson.codecs.pojo.annotations.BsonId;

/**
 * Clase Incidencias
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Incidencia {

	@BsonId
	private String id;

	@SerializedName("tipo_incidencia")
	private String tipoIncidencia;
	private String sucursal;
	@SerializedName("datos_busqueda")
	private DatosBusqueda datosBusqueda;
	private List<ClienteIncidencia> resultados;
	@SerializedName("fecha_incidencia")
	private String fechaIncidencia;

	/**
	 * Constructor de la clase
	 */
	public Incidencia() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipoIncidencia() {
		return tipoIncidencia;
	}

	public void setTipoIncidencia(String tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public DatosBusqueda getDatosBusqueda() {
		return datosBusqueda;
	}

	public void setDatosBusqueda(DatosBusqueda datosBusqueda) {
		this.datosBusqueda = datosBusqueda;
	}

	public List<ClienteIncidencia> getResultados() {
		return resultados;
	}

	public void setResultados(List<ClienteIncidencia> resultados) {
		this.resultados = resultados;
	}

	public String getFechaIncidencia() {
		return fechaIncidencia;
	}

	public void setFechaIncidencia(String fechaIncidencia) {
		this.fechaIncidencia = fechaIncidencia;
	}
	
}
