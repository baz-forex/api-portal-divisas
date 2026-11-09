package com.baz.divisas.api.core.configuraciones.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespConsultaCatalogoRentLealVal {

	private List<BeanSegmentoLealtad> segmentoLealtad;
	private List<BeanSegmentoRentabilidad> segmentoRentabilidad;
	private List<BeanValoresRentabilidad> valoresRentabilidad;

	/**
	 * Constructor de la clase
	 */
	public RespConsultaCatalogoRentLealVal() {
		super();
	}

	public List<BeanSegmentoLealtad> getSegmentoLealtad() {
		return segmentoLealtad;
	}

	public void setSegmentoLealtad(List<BeanSegmentoLealtad> segmentoLealtad) {
		this.segmentoLealtad = segmentoLealtad;
	}

	public List<BeanSegmentoRentabilidad> getSegmentoRentabilidad() {
		return segmentoRentabilidad;
	}

	public void setSegmentoRentabilidad(List<BeanSegmentoRentabilidad> segmentoRentabilidad) {
		this.segmentoRentabilidad = segmentoRentabilidad;
	}

	public List<BeanValoresRentabilidad> getValoresRentabilidad() {
		return valoresRentabilidad;
	}

	public void setValoresRentabilidad(List<BeanValoresRentabilidad> valoresRentabilidad) {
		this.valoresRentabilidad = valoresRentabilidad;
	}

}
