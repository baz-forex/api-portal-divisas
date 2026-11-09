package com.baz.divisas.api.tef.regionessegmentoestrategia.dto;

import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.api.tef.regionesestrategias.dto.RequestEstrategia;
import com.baz.divisas.general.model.Registrar;

/**
 * RequestSegmentoEstrategia.
 */
public class RequestSegmentoEstrategia  extends RequestEstrategia{

	@BsonRepresentation(BsonType.OBJECT_ID)
	@NotNull(message = "Requerido", groups = {Registrar.class})
	private String idSegmento;

	/**
	 * Constructor de la clase
	 */
	public RequestSegmentoEstrategia() {
		super();
	}

	/**
	 * @return the idSegmento
	 */
	public String getIdSegmento() {
		return idSegmento;
	}

	/**
	 * @param idSegmento the idSegmento to set
	 */
	public void setIdSegmento(String idSegmento) {
		this.idSegmento = idSegmento;
	}

}


