package com.baz.divisas.api.core.reporteria.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.bson.codecs.pojo.annotations.BsonIgnore;

import com.baz.divisas.general.annotation.FormatoFechaCorta;
import com.baz.divisas.general.model.Constant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase ReqBusquedaIncidencias.
 *
 * @author itoxtle
 */
public class ReqBusquedaIncidencias {

	/** Rango maximo de dias permitido para la busqueda de incidencias. */
	private static final int RANGO_MAXIMO_DIAS = 92;

	/**  fecha inicial. */
	@NotNull(message = "Requerido")
	@FormatoFechaCorta
	@ApiModelProperty(value="Fecha inicial", example="2023-04-01", position=1, required=true)
	private String fechaInicial;
	
	/**  fecha final. */
	@NotNull(message = "Requerido")
	@FormatoFechaCorta
	@ApiModelProperty(value="Fecha final", example="2023-04-30", position=2, required=true)
	private String fechaFinal;
	
	/**  tipoIncidencia. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Tipo de Incidencia", example="LISTA NEGRA", position=3, required=true)
	private String tipoIncidencia;

	/**
	 * Constructor de la clase.
	 */
	public ReqBusquedaIncidencias() {
		super();
	}

	/**
	 * Valida que el rango entre fechaInicial y fechaFinal no exceda el maximo
	 * permitido, evitando que un rango arbitrariamente grande provoque la
	 * carga y el procesamiento de un numero no acotado de incidencias.
	 *
	 * @return true si el rango es valido o si las fechas aun no son evaluables
	 */
	@AssertTrue(message = "El rango entre fechaInicial y fechaFinal no debe exceder " 
			+ RANGO_MAXIMO_DIAS + " dias")
	@BsonIgnore
	@JsonIgnore
	public boolean isRangoFechasValido() {

		if (fechaInicial == null || fechaFinal == null) {
			return true;
		}

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.FORMATOFECHACORTA);
			LocalDate inicio = LocalDate.parse(fechaInicial, formatter);
			LocalDate fin = LocalDate.parse(fechaFinal, formatter);

			long dias = Math.abs(java.time.temporal.ChronoUnit.DAYS.between(inicio, fin));

			return dias <= RANGO_MAXIMO_DIAS;
		}
		catch (DateTimeParseException e) {
			// El formato invalido ya es reportado por @FormatoFechaCorta
			return true;
		}
	}

	/**
	 * Gets the fecha inicial.
	 *
	 * @return the fecha inicial
	 */
	public String getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Sets the fecha inicial.
	 *
	 * @param fechaInicial the new fecha inicial
	 */
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	
	/**
	 * Gets the fecha final.
	 *
	 * @return the fecha final
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Sets the fecha final.
	 *
	 * @param fechaFinal the new fecha final
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * Gets the tipo incidencia.
	 *
	 * @return the tipo incidencia
	 */
	public String getTipoIncidencia() {
		return tipoIncidencia;
	}

	/**
	 * Sets the tipo incidencia.
	 *
	 * @param tipoIncidencia the new tipo incidencia
	 */
	public void setTipoIncidencia(String tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

}
