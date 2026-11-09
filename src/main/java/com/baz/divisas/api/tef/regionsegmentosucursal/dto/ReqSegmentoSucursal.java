package com.baz.divisas.api.tef.regionsegmentosucursal.dto;

import static com.baz.divisas.general.model.Constant.ID_SEGMENTO_INCORRECTO;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baz.divisas.general.annotation.ValidaObjectId;

/**
 * Clase ReqRegionSegmentoSucursal
 * 
 * @author 1027346 ymartinezb
 */
public class ReqSegmentoSucursal {


	@ValidaObjectId(message = ID_SEGMENTO_INCORRECTO)
	@NotEmpty(message = "Requerido")
	@NotNull(message = "Requerido")
	private String idSegmento;

	@NotEmpty(message = "Requerido")
	@NotNull(message = "Requerido")
	private List<String> sucursales = Collections.emptyList();

	
	/**
	 * Constructor de la clase
	 */
	public ReqSegmentoSucursal() {
		super();
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

	/**
	 * @return the sucursales
	 */
	public List<String> getSucursales() {
		return sucursales;
	}

	/**
	 * @param sucursales the sucursales to set
	 */
	public void setSucursales(List<String> sucursales) {
		this.sucursales = sucursales;
	}

}
