package com.baz.divisas.api.tef.limites.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.api.tef.general.LimitesOnUpdateStatus;
import com.baz.divisas.api.tef.general.LimitesOnUpsert;
import com.baz.divisas.api.tef.general.model.Limites;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase ReqAltaLimitesPorCliente
 * 
 * @author 164945 ogatica
 */
public class LimitesPorClienteDto extends Limites {

	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	@NotBlank(message = "Requerido no vacios", groups= {LimitesOnUpdateStatus.class,LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	@ApiModelProperty(value = "idClienteDivisas", position = 1)
	private String idClienteDivisas;

	/**
	 * Constructor de la clase
	 */
	public LimitesPorClienteDto() {
		super();
	}

	/**
	 * @return the idClienteDivisas
	 */
	public String getIdClienteDivisas() {
		return idClienteDivisas;
	}

	/**
	 * @param idClienteDivisas the idClienteDivisas to set
	 */
	public void setIdClienteDivisas(String idClienteDivisas) {
		this.idClienteDivisas = idClienteDivisas;
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

}
