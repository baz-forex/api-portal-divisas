package com.baz.divisas.api.tef.general.model;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

public class SegmentoSucursales {

	
	/** The id region. */
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idSegmento;
	
	/** The descripcion. */
	@NotNull(message = "Requerido")
	private String descripcion;
    
    /** The sucursales. */
    private List<Sucursal> sucursales = Collections.emptyList();
    
    public SegmentoSucursales() {
    	super();
    }

	public String getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(String idSegmento) {
		this.idSegmento = idSegmento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}
    
    
}
