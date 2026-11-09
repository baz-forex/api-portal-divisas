package com.baz.divisas.api.tef.regionsegmentosucursal.dto;

import static com.baz.divisas.general.model.Constant.ID_SEGMENTO_INCORRECTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baz.divisas.general.annotation.ValidaObjectId;

/**
 * Clase ReqSegmentoSucursalDelete
 * 
 * @author 1027346 ymartinezb
 */
public class ReqSegmentoSucursalDelete {

	@ValidaObjectId(message = ID_SEGMENTO_INCORRECTO)
	@NotEmpty(message = "Requerido")
	@NotNull(message = "Requerido")
	private String idSegmento;

	@NotEmpty(message = "Requerido")
	@NotNull(message = "Requerido")
	private String idSucursal;


	/**
	 * Constructor de la clase
	 */
	public ReqSegmentoSucursalDelete() {
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
	 * @return the sucursal
	 */
	public String getIdSucursal() {
		return idSucursal;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}
}
