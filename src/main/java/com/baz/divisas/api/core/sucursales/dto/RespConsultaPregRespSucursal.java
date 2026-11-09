package com.baz.divisas.api.core.sucursales.dto;

import java.util.ArrayList;
import java.util.List;

import com.baz.divisas.api.core.sucursales.model.Pregunta;
import com.baz.divisas.api.core.sucursales.model.RespuestaCuestionario;

/**
 * Clase RespConsultaRespSucursal
 * 
 * @author 1096526
 *
 */
public class RespConsultaPregRespSucursal {
	private List<Pregunta> preguntas = new ArrayList<>();
	private List<RespuestaCuestionario> respuestaCuestionarios = new ArrayList<>();

	/**
	 * Constructor de la clase
	 * 
	 * @param preguntas - lista con los campos de las preguntas
	 */
	public RespConsultaPregRespSucursal() {
		super();
	}

	/**
	 * @return the preguntas
	 */
	public List<Pregunta> getPreguntas() {
		return new ArrayList<>(preguntas);
	}

	/**
	 * @param preguntas the preguntas to set
	 */
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = new ArrayList<>(preguntas);
	}

	/**
	 * @return the respuestaCuestionarios
	 */
	public List<RespuestaCuestionario> getRespuestas() {
		return new ArrayList<>(respuestaCuestionarios);
	}

	/**
	 * @param respuestaCuestionarios the respuestaCuestionarios to set
	 */
	public void setRespuestas(List<RespuestaCuestionario> respuestaCuestionarios) {
		this.respuestaCuestionarios = new ArrayList<>(respuestaCuestionarios);
	}

}
