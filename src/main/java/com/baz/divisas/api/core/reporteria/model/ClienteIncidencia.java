package com.baz.divisas.api.core.reporteria.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteIncidencia {

	private String nombres;
	private String apellidos;
	@SerializedName("fecha_nacimiento")
	private String fechaNacimiento;
	@SerializedName("tipo_lista")
	private ListaNegra tipoLista;
	private String precision;
	@SerializedName("porcentaje_coincidencia")
	private String porcentajeCoincidencia;
	private String rutaVisor;

	/**
	 * Constructor de la clase
	 */
	public ClienteIncidencia() {
		super();
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public ListaNegra getTipoLista() {
		return tipoLista;
	}

	public void setTipoLista(ListaNegra tipoLista) {
		this.tipoLista = tipoLista;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getPorcentajeCoincidencia() {
		return porcentajeCoincidencia;
	}

	public void setPorcentajeCoincidencia(String porcentajeCoincidencia) {
		this.porcentajeCoincidencia = porcentajeCoincidencia;
	}

	public String getRutaVisor() {
		return rutaVisor;
	}

	public void setRutaVisor(String rutaVisor) {
		this.rutaVisor = rutaVisor;
	}
}
