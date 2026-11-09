package com.baz.divisas.api.core.beneficios.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author B86960
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReqEstatusPremio {
    @BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
    @NotNull(message = "idCampania es requerido para registro")
    @NotBlank(message = "idCampania no tiene ninguna informacion")
    private String idCampania;
    @BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
    @NotNull(message = "idPremio es requerido para registro")
    @NotBlank(message = "idPremio no tiene ninguna informacion")
    private String idPremio;
    private boolean activo;
    /**
     * Constructor
     */
    public ReqEstatusPremio() {
        super();
    }
    /**
     * idCampania
     * @return
     */
    public String getIdCampania() {
        return idCampania;
    }
    /**
     * idCampania
     * @param idCampania
     */
    public void setIdCampania(String idCampania) {
        this.idCampania = idCampania;
    }
    /**
     * idPremio
     * @return
     */
    public String getIdPremio() {
        return idPremio;
    }
    /**
     * idPremio
     * @param idPremio
     */
    public void setIdPremio(String idPremio) {
        this.idPremio = idPremio;
    }
    /**
     * activo
     * @return
     */
    public boolean isActivo() {
        return activo;
    }
    /**
     * activo
     * @param activo
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
