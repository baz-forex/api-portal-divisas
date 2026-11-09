package com.baz.divisas.api.tef.regionessucursales.dto;

import static com.baz.divisas.general.model.Constant.ID_REGION_INCORRECTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baz.divisas.general.annotation.ValidaObjectId;

/**
 * ReqRegionSucursalDelete.
 */
public class ReqRegionSucursalDelete {

	@ValidaObjectId(message = ID_REGION_INCORRECTO)
	@NotEmpty(message = "No puede ser vacío")
	@NotNull(message = "Requerido")
	private String idRegion;

	@NotEmpty(message = "No puede ser vacío")
	@NotNull(message = "Requerida")
	private String idSucursal;

	/**
	 * Constructor de la clase
	 */
	public ReqRegionSucursalDelete() {
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
	 * @return the idSucursal
	 */
	public String getIdSucursal() {
		return idSucursal;
	}

	/**
	 * @param idSucursal the idSucursal to set
	 */
	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}

}
