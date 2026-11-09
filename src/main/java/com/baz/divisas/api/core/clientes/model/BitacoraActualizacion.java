package com.baz.divisas.api.core.clientes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase BitacoraActualizacion
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BitacoraActualizacion {

	@BsonProperty("id_carga")
	private ObjectId idCarga;
	private String estatus;
	private String origen;
	private String usuario;
	@BsonProperty("id_cliente")
	private String idCliente;
	private String campoModificado;
	private String valorAnterior;
	private String valorActual;
	@BsonProperty("campos_modificados")
	private ArrayList<CampoModificado> camposModificados = new ArrayList<>();
	@BsonProperty("motivo_edicion")
	private String motivo;
	@BsonProperty("fecha_registro")
	private String fechaRegistro;
	
	/**
	 * Constructor de la clase
	 */
	public BitacoraActualizacion() {
		super();
	}

	public ObjectId getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(ObjectId idCarga) {
		this.idCarga = idCarga;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getCampoModificado() {
		return campoModificado;
	}

	public void setCampoModificado(String campoModificado) {
		this.campoModificado = campoModificado;
	}

	public String getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

	public String getValorActual() {
		return valorActual;
	}

	public void setValorActual(String valorActual) {
		this.valorActual = valorActual;
	}

	public List<CampoModificado> getCamposModificados() {		
		List<CampoModificado> tempList = new ArrayList<>(this.camposModificados);
		Collections.copy(tempList, this.camposModificados);
		tempList = this.camposModificados;
		return tempList;
	}

	public void setCamposModificados(List<CampoModificado> camposModificados) {
		this.camposModificados = new ArrayList<>(camposModificados);
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}
