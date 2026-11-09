package com.baz.divisas.api.core.sucursales.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase BeanRespuestasCuestionario
 * 
 * @author 1096526
 *
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
public class BeanRespuestasCuestionario {

	private ObjectId idCuestionario;
	private String sucursal;
	private boolean finalizaCuestionario;
	private List<RespuestaCuestionario> respuestas = new ArrayList<>();

	/**
	 * Constructor de la clase
	 */
	public BeanRespuestasCuestionario() {
		super();
	}

	/**
	 * @return the idCuestionario
	 */
	public ObjectId getIdCuestionario() {
		return idCuestionario;
	}

	/**
	 * @param idCuestionario the idCuestionario to set
	 */
	public void setIdCuestionario(ObjectId idCuestionario) {
		this.idCuestionario = idCuestionario;
	}

	/**
	 * @return the sucursales
	 */
	public String getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursales the sucursales to set
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	/**
	 * @return the finalizaCuestionario
	 */
	public boolean isFinalizaCuestionario() {
		return finalizaCuestionario;
	}

	/**
	 * @param finalizaCuestionario the finalizaCuestionario to set
	 */
	public void setFinalizaCuestionario(boolean finalizaCuestionario) {
		this.finalizaCuestionario = finalizaCuestionario;
	}

	/**
	 * @return the respuestas
	 */
	public List<RespuestaCuestionario> getRespuestas() {
		return new ArrayList<>(respuestas);
	}

	/**
	 * @param respuestas the respuestas to set
	 */
	public void setRespuestas(List<RespuestaCuestionario> respuestas) {
		this.respuestas = new ArrayList<>(respuestas);
	}

}
