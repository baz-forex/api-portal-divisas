package com.baz.divisas.api.core.clientes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase BitacoraCargaMasiva
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BitacoraCargaMasiva {

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idCarga;
	@BsonProperty("fecha_carga")
	private String fechaCarga;
	private String usuario;
	@BsonProperty("nombre_usuario")
	private String nombreUsuario;
	@BsonProperty("nombre_archivo")
	private String nombreArchivo;
	@BsonProperty("registros_totales")
	private int registrosTotales;
	@BsonProperty("registros_correctos")
	private int registrosCorrectos;
	@BsonProperty("registros_erroneos")
	private int registrosErroneos;
	private List<ErrorCargaMasiva> errores = new ArrayList<>();
	
	/**
	 * Constructor de la clase
	 */
	public BitacoraCargaMasiva() {
		super();
	}
	
	public String getIdCarga() {
		return idCarga;
	}
	public void setIdCarga(String idCarga) {
		this.idCarga = idCarga;
	}
	public String getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public int getRegistrosTotales() {
		return registrosTotales;
	}
	public void setRegistrosTotales(int registrosTotales) {
		this.registrosTotales = registrosTotales;
	}
	public int getRegistrosCorrectos() {
		return registrosCorrectos;
	}
	public void setRegistrosCorrectos(int registrosCorrectos) {
		this.registrosCorrectos = registrosCorrectos;
	}
	public int getRegistrosErroneos() {
		return registrosErroneos;
	}
	public void setRegistrosErroneos(int registrosErroneos) {
		this.registrosErroneos = registrosErroneos;
	}
	public List<ErrorCargaMasiva> getErrores() {
		List<ErrorCargaMasiva> tempList = new ArrayList<>(this.errores);
		Collections.copy(tempList, this.errores);
		tempList = this.errores;
		return tempList;
	}
	public void setErrores(List<ErrorCargaMasiva> errores) {
		this.errores = new ArrayList<>(errores);
	}
	
}
