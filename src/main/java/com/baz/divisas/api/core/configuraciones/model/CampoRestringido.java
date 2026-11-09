package com.baz.divisas.api.core.configuraciones.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase RestriccionCampo
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CampoRestringido {

	@BsonId
	private String id;
	private String descripcion;
	@BsonProperty("palabras_restringidas")
	private List<String> restricciones = new ArrayList<>();
	
	/**
	 * Constructor de la clase
	 */
	public CampoRestringido() {
		super();

	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<String> getRestricciones() {
		return Collections.unmodifiableList(restricciones);
	}
	public void setRestricciones(List<String> palabrasRestringidas) {
		this.restricciones = Collections.unmodifiableList(palabrasRestringidas);
	}
	
}
