package com.baz.divisas.api.core.sucursales.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase BeanCuestionarios
 * 
 * @author 1096526
 *
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
public class BeanCuestionarios {

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private boolean estatus;
	private String fechaRegistro;
	private List<Pregunta> preguntas = new ArrayList<>();
	private List<String> sucursales = new ArrayList<>();
	private Titulo titulo;
	@BsonIgnore
	@JsonIgnore
	private long total;

	/**
	 * Constructor de la clase
	 */
	public BeanCuestionarios() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the estatus
	 */
	public boolean isEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the fechaRegistro
	 */
	public String getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the preguntas
	 */
	public List<Pregunta> getPreguntas() {
		return new ArrayList<>(preguntas);
	}

	/**
	 * @param preguntas the preguntas to set
	 */
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = new ArrayList<>(preguntas);
	}

	/**
	 * @return the sucursales
	 */
	public List<String> getSucursales() {
		return new ArrayList<>(sucursales);
	}

	/**
	 * @param sucursales the sucursales to set
	 */
	public void setSucursales(List<String> sucursales) {
		this.sucursales = new ArrayList<>(sucursales);
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @return the titulo
	 */
	public Titulo getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

}
