/**
 * 
 */
package com.baz.divisas.api.core.beneficios.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Request de estatus campania
 * @author B86960
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReqEstatusCampania {
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
    @NotNull(message = "idCampania es requerido para registro")
    @NotBlank(message = "idCampania no tiene ninguna informacion")
    private String idCampania;
	@NotNull(message = "estatus es requerido para registro")
    @NotBlank(message = "estatus no tiene ninguna informacion")
	private String estatus;
	/**
	 * @return the idCampania
	 */
	public String getIdCampania() {
		return idCampania;
	}
	/**
	 * @param idCampania the idCampania to set
	 */
	public void setIdCampania(String idCampania) {
		this.idCampania = idCampania;
	}
	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
    
}
