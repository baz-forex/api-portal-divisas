package com.baz.divisas.api.tef.regionessegmento.dto;

import static com.baz.divisas.general.model.Constant.ID_REGION_INCORRECTO;
import static com.baz.divisas.general.model.Constant.ID_SEGMENTO_INCORRECTO;
import javax.validation.constraints.NotNull;

import com.baz.divisas.general.annotation.ValidaObjectId;

/**
 * Clase ReqRegionSegmentoDelete
 * 
 * @author 1027346 ymartinezb
 */
public class ReqRegionSegmentoDelete {

	@ValidaObjectId(message = ID_REGION_INCORRECTO)
	@NotNull(message = "Requerido")
	private String idRegion;

	@ValidaObjectId(message = ID_SEGMENTO_INCORRECTO)
	@NotNull(message = "Requerido")
	private String idSegmento;

	/**
	 * Constructor de la clase
	 */
	public ReqRegionSegmentoDelete() {
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
