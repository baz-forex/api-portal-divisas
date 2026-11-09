package com.baz.divisas.api.siex.configuraciones.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase ReqCampoRestringido
 * @author B813196
 * itoxtle
 */
public class ReqCampoRestringido {

	@NotEmpty(message = "Requerido")
	@ApiModelProperty(value="Ids de Campos", example = "[\"colonia\",\"direccion\"]", position=1, required=true)
	private List<String> idsCampos;
	
	@NotEmpty(message = "Requerido")
	@ApiModelProperty(value="Id Regla", example="empiezaCon", position=2, required=true)
	private String idRegla;
	
	@NotEmpty(message = "Requerido")
	@ApiModelProperty(value="Restricción", example="S/N", position=3, required=true)
	private String restriccion;

	/**
	 * Constructor de la clase
	 */
	public ReqCampoRestringido() {
		super();
	}
	
	/**
	 * @return the idsCampos
	 */
	public List<String> getIdsCampos() {
		return new ArrayList<>(idsCampos);
	}

	/**
	 * @param idsCampos the idsCampos to set
	 */
	public void setIdsCampos(List<String> idsCampos) {
		this.idsCampos = new ArrayList<>(idsCampos);
	}

	/**
	 * @return the idRegla
	 */
	public String getIdRegla() {
		return idRegla;
	}

	/**
	 * @param idRegla the idRegla to set
	 */
	public void setIdRegla(String idRegla) {
		this.idRegla = idRegla;
	}

	public String getRestriccion() {
		return restriccion;
	}
	public void setRestriccion(String restriccion) {
		this.restriccion = restriccion;
	}
	
}
