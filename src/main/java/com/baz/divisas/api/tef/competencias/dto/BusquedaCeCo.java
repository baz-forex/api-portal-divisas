package com.baz.divisas.api.tef.competencias.dto;

import java.util.List;

import org.bson.Document;

public class BusquedaCeCo {

	List<CeCo> listaCeCo;
	List<Document> listaSinCeCo;
	/**
	 * @return the listaCeCo
	 */
	public List<CeCo> getListaCeCo() {
		return listaCeCo;
	}
	/**
	 * @param listaCeCo the listaCeCo to set
	 */
	public void setListaCeCo(List<CeCo> listaCeCo) {
		this.listaCeCo = listaCeCo;
	}
	/**
	 * @return the listaSinCeCo
	 */
	public List<Document> getListaSinCeCo() {
		return listaSinCeCo;
	}
	/**
	 * @param listaSinCeCo the listaSinCeCo to set
	 */
	public void setListaSinCeCo(List<Document> listaSinCeCo) {
		this.listaSinCeCo = listaSinCeCo;
	}	
	
}
