package com.baz.divisas.api.usuarios.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.bson.codecs.pojo.annotations.BsonId;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase UsuarioBase
 * @author B813196
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UsuarioBase {

	@NotNull(message = "Requerido")
	@Size(min = 4, max = 10, message="Longitud de minimo {min}, máximo {max}")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Solo se permiten letras y numeros")
	@ApiModelProperty(value = "Id usuario", position = 1)
	@BsonId
	private String id;
	
	/**
	 * Constructor de la clase
	 * UsuarioBase
	 */
	public UsuarioBase() {
		super();
	}

	/**
	 * Constructor de la clase
	 * Usuario Base
	 * @param id - Identificador de usuario
	 */
	public UsuarioBase(String id) {
		super();
		this.id = id;
	}

	/**
	 * getId
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * setid
	 * @param id - Identificador de usuario
	 */
	public void setId(String id) {
		this.id = id;
	}
}
