package com.baz.divisas.api.tef.regionessegmento.dto;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.baz.divisas.general.annotation.ValidaObjectId;
import static com.baz.divisas.general.model.Constant.ID_REGION_INCORRECTO;

/**
 * ReqRegionSegmento.
 */
public class ReqRegionSegmentos {

	@ValidaObjectId(message = ID_REGION_INCORRECTO)
	@NotNull(message = "Requerido")
	private String idRegion;

	@NotNull(message = "Requerido")
	private List<String> idSegmento = Collections.emptyList();

	/**
	 * Constructor de la clase
	 */
	public ReqRegionSegmentos() {
		super();
	}

	/**
	 * Gets the id region.
	 *
	 * @return the idRegion
	 */
	public String getIdRegion() {
		return idRegion;
	}

	/**
	 * Sets the id region.
	 *
	 * @param idRegion the idRegion to set
	 */
	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

	/**
	 * Gets the id segmento.
	 *
	 * @return the idSegmento
	 */
	public List<String> getIdSegmento() {
		return new ArrayList<>(idSegmento);
	}

	/**
	 * Sets the id segmento.
	 *
	 * @param idSegmento the idSegmento to set
	 */
	public void setIdSegmento(List<String> idSegmento) {

		idSegmento = new ArrayList<>(idSegmento);
		this.idSegmento = Collections.unmodifiableList(idSegmento);
	}

}
