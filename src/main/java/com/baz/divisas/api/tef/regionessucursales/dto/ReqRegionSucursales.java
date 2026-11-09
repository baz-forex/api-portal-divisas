package com.baz.divisas.api.tef.regionessucursales.dto;

import static com.baz.divisas.general.model.Constant.ID_REGION_INCORRECTO;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.baz.divisas.general.annotation.ValidaObjectId;

/**
 * ReqRegionSucursales.
 */
public class ReqRegionSucursales {

	@ValidaObjectId(message = ID_REGION_INCORRECTO)
	@NotNull(message = "Requerido")
	private String idRegion;

	private List<String> idSucursal = Collections.emptyList();

	/**
	 * Constructor de la clase
	 */
	public ReqRegionSucursales() {
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
	public List<String> getIdSucursal() {
		return idSucursal;
	}

	/**
	 * @param idSucursal the idSucursal to set
	 */
	public void setIdSucursal(List<String> idSucursal) {
		this.idSucursal = idSucursal;
	}

}
