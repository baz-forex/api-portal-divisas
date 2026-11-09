package com.baz.divisas.api.tef.regionesestrategias.dto;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.api.tef.general.model.Estrategia;

/**
 * ResponseEstrategia.
 */
public class ResponseEstrategia {

	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private String divisa;
	private String descripcion;
	private String tipoOperacion;
	private String descripcionOperacion;
	private Estrategia estrategia;

	/**
	 * Constructor de la clase
	 */
	public ResponseEstrategia() {
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
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}
	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	/**
	 * @return the descripcionOperacion
	 */
	public String getDescripcionOperacion() {
		return descripcionOperacion;
	}
	/**
	 * @param descripcionOperacion the descripcionOperacion to set
	 */
	public void setDescripcionOperacion(String descripcionOperacion) {
		this.descripcionOperacion = descripcionOperacion;
	}
	/**
	 * @return the estrategia
	 */
	public Estrategia getEstrategia() {
		return estrategia;
	}
	/**
	 * @param estrategia the estrategia to set
	 */
	public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}

}
