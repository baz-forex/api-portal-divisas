package com.baz.divisas.api.usuarios.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Menu de accesos
 * 
 * Lista de modulos a los que el 
 * usuario tiene acceso
 * 
 * @author B813196
 * itoxtle
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {

	private List<Nodo> nodos = new ArrayList<>();
	
	/**
	 * Constructor de la clase
	 */
	public Menu() {
		super();
	}

	public List<Nodo> getNodos() {
		List<Nodo> tempList = new ArrayList<>(this.nodos);
		Collections.copy(tempList, this.nodos);
		tempList = this.nodos;
		return tempList;
	}

	public void setNodos(List<Nodo> nodos) {
		this.nodos = new ArrayList<>(nodos);
	}

}
