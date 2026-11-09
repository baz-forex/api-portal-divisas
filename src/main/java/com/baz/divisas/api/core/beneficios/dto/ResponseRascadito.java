package com.baz.divisas.api.core.beneficios.dto;

import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.api.core.beneficios.model.Etiquetas;
import com.baz.divisas.api.core.beneficios.model.Imagenes;

public class ResponseRascadito {
	
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;	
	private String nombreCampania;
	private List<Etiquetas> etiquetas;
	private List<Imagenes> imagenes;
	private boolean activo = false;
	/**
	 * 
	 */
	public ResponseRascadito() {
		super();
	}
	
	/**
	 * @param id
	 * @param nombreCampania
	 * @param etiquetas
	 * @param imagenes
	 * @param activo
	 */
	public ResponseRascadito(String id, String nombreCampania, List<Etiquetas> etiquetas, List<Imagenes> imagenes,
			boolean activo) {
		super();
		this.id = id;
		this.nombreCampania = nombreCampania;
		this.etiquetas = etiquetas;
		this.imagenes = imagenes;
		this.activo = activo;
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
	 * @return the nombreCampania
	 */
	public String getNombreCampania() {
		return nombreCampania;
	}
	/**
	 * @param nombreCampania the nombreCampania to set
	 */
	public void setNombreCampania(String nombreCampania) {
		this.nombreCampania = nombreCampania;
	}
	/**
	 * @return the etiquetas
	 */
	public List<Etiquetas> getEtiquetas() {
		return etiquetas;
	}
	/**
	 * @param etiquetas the etiquetas to set
	 */
	public void setEtiquetas(List<Etiquetas> etiquetas) {
		this.etiquetas = etiquetas;
	}
	/**
	 * @return the imagenes
	 */
	public List<Imagenes> getImagenes() {
		return imagenes;
	}
	/**
	 * @param imagenes the imagenes to set
	 */
	public void setImagenes(List<Imagenes> imagenes) {
		this.imagenes = imagenes;
	}
	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
		
}
