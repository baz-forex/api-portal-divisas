package com.baz.divisas.api.core.clientes.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

/**
 * ReqBloqueoClientes
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReqBloqueoClientes {

	/** The id. */
	@ApiModelProperty(hidden=true)
	private String id;
	
	/** The tipo. */
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(1|2|3)$", message="Tipo de bloqueo invalido")
	@ApiModelProperty(value="Tipo de Bloqueo", example="3", position=1, required=true)
	private String tipo;
	
	/** The cliente. */
	@Pattern(regexp="[0-9]{0,10}", message="El idCliente deben ser numérico de longitud máxima 10")
	@ApiModelProperty(value="Id de Cliente", example="1000", position=2)
	private String cliente;
	
	/** The tipo identificacion. */
	@Pattern(regexp="^(1|2|37|38)$", message="Tipo de identificacion invalido")
	@ApiModelProperty(value="Tipo de identificación", example="1", position=3)
	private String tipoIdentificacion;
	
	/** The folio identificacion. */
	@ApiModelProperty(value="Folio de identificación", example="123456", position=4)
	private String folioIdentificacion;
	
	
	/**
	 * Constructor de la clase
	 */
	public ReqBloqueoClientes() {
		super();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * Sets the tipo.
	 *
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Gets the cliente.
	 *
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}
	
	/**
	 * Sets the cliente.
	 *
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	/**
	 * Gets the tipo identificacion.
	 *
	 * @return the tipoIdentificacion
	 */
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	
	/**
	 * Sets the tipo identificacion.
	 *
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	/**
	 * Gets the folio identificacion.
	 *
	 * @return the folioIdentificacion
	 */
	public String getFolioIdentificacion() {
		return folioIdentificacion;
	}
	
	/**
	 * Sets the folio identificacion.
	 *
	 * @param folioIdentificacion the folioIdentificacion to set
	 */
	public void setFolioIdentificacion(String folioIdentificacion) {
		this.folioIdentificacion = folioIdentificacion;
	}

	@Override
	public String toString() {

		String str = "";
		switch (this.tipo) {
		case "1":
        str = "Cliente: " + cliente;
        break;
        
		case "2":
        str = "Tipo de Identificación: "	+ tipoIdentificacion +
				  "\nFolio de Identificación:" + folioIdentificacion;
        break;
		case "3":
        str = "Cliente: " + cliente + "\nTipo de Identificación: "
			+ tipoIdentificacion + "\nFolio de Identificación: " + folioIdentificacion;
        break;

		default:
        break;
		}
		
		return str;
	}
	
}
