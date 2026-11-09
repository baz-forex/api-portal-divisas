package com.baz.divisas.api.core.beneficios.model;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Imagenes {
	

	@BsonProperty("id")
	private String id;
	private Binary valor;
	@BsonIgnore
	@JsonIgnore
	private MultipartFile imagenId;
	
	
	/**
	 * 
	 */
	public Imagenes() {
		super();
	}


	/**
	 * @param id
	 * @param valor
	 * @param imagenId
	 */
	public Imagenes(String id, Binary valor, MultipartFile imagenId) {
		super();
		this.id = id;
		this.valor = valor;
		this.imagenId = imagenId;
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
	 * @return the valor
	 */
	public Binary getValor() {
		return valor;
	}


	/**
	 * @param valor the valor to set
	 */
	public void setValor(Binary valor) {
		this.valor = valor;
	}


	/**
	 * @return the imagenId
	 */
	public MultipartFile getImagenId() {
		return imagenId;
	}


	/**
	 * @param imagenId the imagenId to set
	 */
	public void setImagenId(MultipartFile imagenId) {
		this.imagenId = imagenId;
	}

}
