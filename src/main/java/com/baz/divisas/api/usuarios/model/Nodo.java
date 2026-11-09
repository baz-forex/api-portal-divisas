package com.baz.divisas.api.usuarios.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/**
 * Nodo de accesos
 * @author B813196
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Nodo {

	@SerializedName("_id")
	private String id;
	@JsonIgnore
	@SerializedName("parent_id")
	private String parentId;
	private String tipo;
	private String descripcion;
	private List<Nodo> nodos = new ArrayList<>();
	private Permisos permisos;
	private String icono;
	
	/**
	 * Constructor de la clase
	 */
	public Nodo() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public Permisos getPermisos() {
		return permisos;
	}
	public void setPermisos(Permisos permisos) {
		this.permisos = permisos;
	}
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	
	
	
		
}
