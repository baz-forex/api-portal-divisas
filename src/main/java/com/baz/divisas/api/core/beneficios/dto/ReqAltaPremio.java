package com.baz.divisas.api.core.beneficios.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.api.core.beneficios.model.BeanPremio;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author B86960
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReqAltaPremio {
    @BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
    @NotNull(message = "idCampania es requerido para registro")
    @NotBlank(message = "idCampania no tiene ninguna informacion")
    private String idCampania;
    private List<BeanPremio> premios;
    private BeanPremio premio;
    
    /**
     * Constructor.
     */
    public ReqAltaPremio() {
        super();
    }
    
    /**
     * Gets the id campania.
     *
     * @return the id campania
     */
    public String getIdCampania() {
        return idCampania;
    }
    
    /**
     * Sets the id campania.
     *
     * @param idCampania the new id campania
     */
    public void setIdCampania(String idCampania) {
        this.idCampania = idCampania;
    }

	/**
	 * @return the premios
	 */
	public List<BeanPremio> getPremios() {
		return premios;
	}

	/**
	 * @param premios the premios to set
	 */
	public void setPremios(List<BeanPremio> premios) {
		this.premios = premios;
	}

	/**
	 * @return the premio
	 */
	public BeanPremio getPremio() {
		return premio;
	}

	/**
	 * @param premio the premio to set
	 */
	public void setPremio(BeanPremio premio) {
		this.premio = premio;
	}
    
    
}
