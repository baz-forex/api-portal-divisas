package com.baz.divisas.api.usuarios.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * Clase Usuario
 * @author B813196
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Usuario extends UsuarioBase {
	
	private String correoElectronico;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;	
	
	@Valid
	private List<Modulo> modulos = new ArrayList<>();

		
	/**
	 * constructor de la clase
	 */
	public Usuario() {
		super();
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public List<Modulo> getModulos() {
		return Collections.unmodifiableList(modulos);
	}

	public void setModulos(@Valid List<Modulo> modulos) {
		this.modulos = Collections.unmodifiableList(modulos);
	}
	
}
