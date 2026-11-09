package com.baz.divisas.api.tef.regionessegmentoestrategia.dto;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.api.tef.regionesestrategias.dto.ResponseEstrategia;

/**
 * ResponseSegmentoEstrategia.
 */
public class ResponseSegmentoEstrategia extends ResponseEstrategia{

	private boolean asignacion;
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idRegion;
	private String regionDescripcion;

	
	/**
	 * Constructor de la clase
	 */
	public ResponseSegmentoEstrategia() {
		super();
	}

	/**
	 * @return the asignacion
	 */
	public boolean isAsignacion() {
		return asignacion;
	}

	/**
	 * @param asignacion the asignacion to set
	 */
	public void setAsignacion(boolean asignacion) {
		this.asignacion = asignacion;
	}

	/**
	 * @return the idRegion
	 */
	public String getIdRegion() {
		return idRegion;
	}

	/**
	 * @param idRegion the idRegion to set
	 */
	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

	/**
	 * @return the regionDescripcion
	 */
	public String getRegionDescripcion() {
		return regionDescripcion;
	}

	/**
	 * @param regionDescripcion the regionDescripcion to set
	 */
	public void setRegionDescripcion(String regionDescripcion) {
		this.regionDescripcion = regionDescripcion;
	}

}
