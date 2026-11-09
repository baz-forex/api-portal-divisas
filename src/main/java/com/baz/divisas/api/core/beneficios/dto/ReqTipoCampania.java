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
public class ReqTipoCampania {
    @BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
    @NotNull(message = "idCampania es requerido para registro")
    @NotBlank(message = "idCampania no tiene ninguna informacion")
    private String idCampania;
    private boolean permanente;
    private boolean unica;
    @NotNull(message = "motivo es requerido para registro")
    @NotBlank(message = "motivo no tiene ninguna informacion")
    private String motivo;
    /**
     * Constructor
     */
	public ReqTipoCampania() {
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
     * permanente
     */
    public boolean isPermanente() {
        return permanente;
    }
    /**
     * permanente
     * @param permanente
     */
    public void setPermanente(boolean permanente) {
        this.permanente = permanente;
    }
    /**
     * unica
     */
    public boolean isUnica() {
        return unica;
    }
    /**
     * unica
     * @param unica
     */
    public void setUnica(boolean unica) {
        this.unica = unica;
    }
    /**
     * motivo
     * @return
     */
    public String getMotivo() {
        return motivo;
    }
    /**
     * motivo
     * @param motivo
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
}
