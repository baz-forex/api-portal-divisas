package com.baz.divisas.api.core.sucursales.model;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase RespuestaCuestionario
 * 
 * @author 1096526
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaCuestionario {
	private int idPregunta;
	private int idTipoRespuesta;
	private String respuesta;
	private List<Opcion> opciones;
	private String imagen;
	private String motivo;

	/**
	 * Constructor de la clase
	 */
	public RespuestaCuestionario() {
		super();
		this.opciones = new ArrayList<>();
	}

	/**
	 * @return the idPregunta
	 */
	public int getIdPregunta() {
		return idPregunta;
	}

	/**
	 * @param idPregunta the idPregunta to set
	 */
	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}

	/**
	 * @return the idTipoRespuesta
	 */
	public int getIdTipoRespuesta() {
		return idTipoRespuesta;
	}

	/**
	 * @param idTipoRespuesta the idTipoRespuesta to set
	 */
	public void setIdTipoRespuesta(int idTipoRespuesta) {
		this.idTipoRespuesta = idTipoRespuesta;
	}

	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

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

	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
