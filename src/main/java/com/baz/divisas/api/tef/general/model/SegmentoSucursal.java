package com.baz.divisas.api.tef.general.model;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * The Class SegmentoSucursal.
 */
public class SegmentoSucursal {
	
	/** The id segmento. */
	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idSegmento;
	

	/** The descripcion. */
	@NotNull(message = "Requerido")
	private String descripcion;
	
	/** The sucursales. */
	private List<String> sucursales = Collections.emptyList();

	public SegmentoSucursal() {
		super();
	}
	
	public SegmentoSucursal(String idSegmento,String descripcion,List<String> sucursales) {
		super();
		this.idSegmento = idSegmento;
		this.descripcion = descripcion;
		this.sucursales = sucursales;
	}
	
	

	/**
	 * Gets the id segmento.
	 *
	 * @return the id segmento
	 */
	public String getIdSegmento() {
		return idSegmento;
	}

	/**
	 * Sets the id segmento.
	 *
	 * @param idSegmento the new id segmento
	 */
	public void setIdSegmento(String idSegmento) {
		this.idSegmento = idSegmento;
	}

	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Gets the sucursales.
	 *
	 * @return the sucursales
	 */
	public List<String> getSucursales() {
		return sucursales;
	}

	/**
	 * Sets the sucursales.
	 *
	 * @param sucursales the new sucursales
	 */
	public void setSucursales(List<String> sucursales) {
		this.sucursales = sucursales;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentoSucursal sucursal = (SegmentoSucursal) o;
        return idSegmento == sucursal.idSegmento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSegmento);
    }


}
