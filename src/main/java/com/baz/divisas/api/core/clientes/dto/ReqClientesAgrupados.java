package com.baz.divisas.api.core.clientes.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;

/**
 * The Class ReqClientesAgrupados.
 */
public class ReqClientesAgrupados {

	/** Id de Grupo de clientes. */
	@NotNull(message = "Requerido", groups= {Actualizar.class})
	@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24", 
	 		 groups=Actualizar.class)
	private String idGrupo;
	
	/** Grupo de clientes */
	@Size(min=2, message="Se requieren mínimo dos clientes en el grupo", groups={Registrar.class, Actualizar.class})
	private List<
	@Pattern(regexp="[0-9]{0,10}", message="Los clientes deben ser numéricos, máximo 10 dígitos", 
			 groups= {Registrar.class, Actualizar.class})
	 String> clientes;
	
	
	/**
	 * Instantiates a new req clientes agrupados.
	 */
	public ReqClientesAgrupados() {
		super();
	}

	/**
	 * Gets the id grupo.
	 *
	 * @return the idGrupo
	 */
	public String getIdGrupo() {
		return idGrupo;
	}
	
	/**
	 * Sets the id grupo.
	 *
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	/**
	 * Gets the clientes.
	 *
	 * @return the clientes
	 */
	public List<String> getClientes() {
		return clientes;
	}
	
	/**
	 * Sets the clientes.
	 *
	 * @param clientes the clientes to set
	 */
	public void setClientes(List<String> clientes) {
		this.clientes = clientes;
	}

}
