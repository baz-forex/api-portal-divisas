package com.baz.divisas.api.tef.regionsegmentosucursal.dto;

import static com.baz.divisas.general.model.Constant.ID_REGION_INCORRECTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baz.divisas.general.annotation.ValidaObjectId;

public class ReqSegmentoDivisa {

	/** The id region. */
	@ValidaObjectId(message = ID_REGION_INCORRECTO)
	@NotEmpty(message = "No puede ser vacío")
	@NotNull(message = "Requerido")
	private String idSegmento;

	/** The id divisa. */
	@NotEmpty(message = "No puede ser vacío")
	@NotNull(message = "Requerida")
	private String idDivisa;
	
	public ReqSegmentoDivisa() {
		super();
	}
	public String getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(String idSegmento) {
		this.idSegmento = idSegmento;
	}

	public String getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(String idDivisa) {
		this.idDivisa = idDivisa;
	}	
}
