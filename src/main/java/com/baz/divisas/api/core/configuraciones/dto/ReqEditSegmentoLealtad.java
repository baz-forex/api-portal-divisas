package com.baz.divisas.api.core.configuraciones.dto;

/**
 * Clase ReqEditSegmentoLealtad
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
public class ReqEditSegmentoLealtad {

	private BeanSegmentoLealtad segmentoLealtad = new BeanSegmentoLealtad();
	
	/**
	 * Constructor de la clase
	 */
	public ReqEditSegmentoLealtad() {
		super();
	}

	public BeanSegmentoLealtad getSegmentoLealtad() {
		return segmentoLealtad;
	}

	public void setSegmentoLealtad(BeanSegmentoLealtad segmentoLealtad) {
		this.segmentoLealtad = segmentoLealtad;
	}

}
