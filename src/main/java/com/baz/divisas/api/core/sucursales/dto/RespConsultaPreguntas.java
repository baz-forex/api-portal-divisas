package com.baz.divisas.api.core.sucursales.dto;

import java.util.ArrayList;
import java.util.List;

import com.baz.divisas.api.core.sucursales.model.Pregunta;
import com.baz.divisas.api.core.sucursales.model.Titulo;

/**
 * Clase RespConsultaPreguntas
 * 
 * @author 1096526
 *
 */
public class RespConsultaPreguntas {
	private List<Pregunta> preguntas = new ArrayList<>();
	private Titulo titulo;

	/**
	 * Constructor de la clase
	 * 
	 * @param preguntas - lista con los campos de las preguntas
	 */
	public RespConsultaPreguntas() {
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
	 * @return the titulo
	 */
	public Titulo getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

}
