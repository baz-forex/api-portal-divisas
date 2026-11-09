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
 * Clase ReqAltaLimitesPorRegion
 * 
 * @author 164945 ogatica
 */
public class LimitesPorRegionDto extends Limites {

	@NotBlank(message = "Requerido no vacios", groups= {LimitesOnUpdateStatus.class,LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idRegion;

	/**
	 * Constructor de la clase
	 */
	public LimitesPorRegionDto() {
		super();
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
