package com.baz.divisas.api.core.sucursales.model;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Pregunta
 * 
 * @author 1096526
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pregunta {
	private int id;
	private String texto;
	private TipoRespuesta tipoRespuesta;
	private List<Opcion> opciones;
	private boolean permiteImagen;
	private boolean obligatoria;
	private boolean respuestaMultiple;
	private String imagen;

	/**
	 * Constructor de la clase
	 */
	public Pregunta() {
		super();
		this.opciones = new ArrayList<>();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * @return the tipoRespuesta
	 */
	public TipoRespuesta getTipoRespuesta() {
		return tipoRespuesta;
	}

	/**
	 * @param tipoRespuesta the tipoRespuesta to set
	 */
	public void setTipoRespuesta(TipoRespuesta tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}

	/**
	 * @return the opciones
	 */
	public List<Opcion> getOpciones() {
		return new ArrayList<>(opciones);
	}

	/**
	 * @param opciones the opciones to set
	 */
	public void setOpciones(List<Opcion> opciones) {
		this.opciones = new ArrayList<>(opciones);
	}

	/**
	 * @return the permiteImagen
	 */
	public boolean isPermiteImagen() {
		return permiteImagen;
	}

	/**
	 * @param permiteImagen the permiteImagen to set
	 */
	public void setPermiteImagen(boolean permiteImagen) {
		this.permiteImagen = permiteImagen;
	}

	/**
	 * @return the obligatoria
	 */
	public boolean isObligatoria() {
		return obligatoria;
	}

	/**
	 * @param obligatoria the obligatoria to set
	 */
	public void setObligatoria(boolean obligatoria) {
		this.obligatoria = obligatoria;
	}

	/**
	 * @return the respuestaMultiple
	 */
	public boolean isRespuestaMultiple() {
		return respuestaMultiple;
	}

	/**
	 * @param respuestaMultiple the respuestaMultiple to set
	 */
	public void setRespuestaMultiple(boolean respuestaMultiple) {
		this.respuestaMultiple = respuestaMultiple;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
