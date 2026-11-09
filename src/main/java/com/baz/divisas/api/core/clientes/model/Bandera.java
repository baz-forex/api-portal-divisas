package com.baz.divisas.api.core.clientes.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase Bandera
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bandera {

	private String valor;
	@JsonProperty("fechaActualizacion")
	@JsonAlias("fecha_actualizacion")
	private String fechaActualizacion;
	@JsonProperty("fechaPrimerAviso")
	@JsonAlias("fecha_primer_aviso")
	private String fechaPrimerAviso;

	/**
	 * Constructor de la clase
	 */
	public Bandera() {
		super();
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getFechaPrimerAviso() {
		return fechaPrimerAviso;
	}
	public void setFechaPrimerAviso(String fechaPrimerAviso) {
		this.fechaPrimerAviso = fechaPrimerAviso;
	}
}
