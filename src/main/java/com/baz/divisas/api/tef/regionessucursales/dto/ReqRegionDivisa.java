package com.baz.divisas.api.tef.regionessucursales.dto;

import static com.baz.divisas.general.model.Constant.ID_REGION_INCORRECTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baz.divisas.general.annotation.ValidaObjectId;

/**
 * The Class ReqRegionDivisa.
 */
public class ReqRegionDivisa {

	/** The id region. */
	@ValidaObjectId(message = ID_REGION_INCORRECTO)
	@NotEmpty(message = "No puede ser vacío")
	@NotNull(message = "Requerido")
	private String idRegion;

	/** The id divisa. */
	@NotEmpty(message = "No puede ser vacío")
	@NotNull(message = "Requerida")
	private String idDivisa;

	/**
	 * Instantiates a new req region divisa.
	 */
	public ReqRegionDivisa() {
		super();
	}

	/**
	 * Gets the id region.
	 *
	 * @return the id region
	 */
	public String getIdRegion() {
		return idRegion;
	}

	/**
	 * Sets the id region.
	 *
	 * @param idRegion the new id region
	 */
	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

	/**
	 * Gets the id divisa.
	 *
	 * @return the id divisa
	 */
	public String getIdDivisa() {
		return idDivisa;
	}

	/**
	 * Sets the id divisa.
	 *
	 * @param idDivisa the new id divisa
	 */
	public void setIdDivisa(String idDivisa) {
		this.idDivisa = idDivisa;
	}
	
}
