package com.baz.divisas.api.tef.general.model;

import java.util.ArrayList;
import java.util.List;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase CatalogoDivisas
 * @author B164945
 * ogatica
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CatalogoDivisas extends Catalogo {

	private List<Limites> limites = new ArrayList<>();
	
	/**
	 * Constructor de la clase
	 */
	public CatalogoDivisas() {
		super();
	}
	
	/**
	 * @return the limites
	 */
	public List<Limites> getLimites() {
		return limites;
	}

	/**
	 * @param limites the limites to set
	 */
	public void setLimites(List<Limites> limites) {
		this.limites = limites;
	}
	
}
