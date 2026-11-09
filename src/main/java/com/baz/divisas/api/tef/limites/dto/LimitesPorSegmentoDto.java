package com.baz.divisas.api.tef.limites.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.api.tef.general.LimitesOnUpsert;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.LimitesOnUpdateStatus;

/**
 * Clase LimitesPorSegmentoDto
 * 
 * @author 164945 ogatica
 */
public class LimitesPorSegmentoDto extends Limites {

	@NotBlank(message = "Requerido no vacios", groups= {LimitesOnUpdateStatus.class,LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idSegmento;
	
	@NotBlank(message = "Requerido no vacios", groups= {LimitesOnUpdateStatus.class,LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idRegion;

	/**
	 * Constructor de la clase
	 */
	public LimitesPorSegmentoDto() {
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

}
