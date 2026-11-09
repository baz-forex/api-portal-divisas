package com.baz.divisas.api.core.sucursales.model;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Cuestionario
 * 
 * @author 1096526
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cuestionario {
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private Titulo titulo;
	private String fechaRegistro;
	private int preguntas;
	private int preguntasRespondidas;
	private int avance;

	/**
	 * Constructor de la clase
	 */
	public Cuestionario() {
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
	public int getPreguntas() {
		return preguntas;
	}

	/**
	 * @param preguntas the preguntas to set
	 */
	public void setPreguntas(int preguntas) {
		this.preguntas = preguntas;
	}

	/**
	 * @return the preguntasRespondidas
	 */
	public int getPreguntasRespondidas() {
		return preguntasRespondidas;
	}

	/**
	 * @param preguntasRespondidas the preguntasRespondidas to set
	 */
	public void setPreguntasRespondidas(int preguntasRespondidas) {
		this.preguntasRespondidas = preguntasRespondidas;
	}

	/**
	 * @return the avance
	 */
	public int getAvance() {
		return avance;
	}

	/**
	 * @param avance the avance to set
	 */
	public void setAvance(int avance) {
		this.avance = avance;
	}

}
