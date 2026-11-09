package com.baz.divisas.api.core.configuraciones.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase ParametriaBusquedas
 * @author B813196
 * itoxtle
 *
 */
public class ParametriaBusquedas {

	@NotNull(message = "Requerido")
	@ApiModelProperty(value = "Parametria Búsqueda Clientes", position = 1)
	@Valid
	private ConfigBusquedas busquedaClientes;
	
	@NotNull(message = "Requerido")
	@ApiModelProperty(value = "Parametria Búsqueda Lista Negra", position = 2)
	@Valid
	private ConfigBusquedas busquedaListaNegra;
	
	@NotNull(message = "Requerido")
	@ApiModelProperty(value = "Penalizaciones", position = 3)
	@Valid
	private ConfigPenalizaciones penalizaciones;
	
	/**
	 * Constructor de la clase
	 */
	public ParametriaBusquedas() {
		super();

	}
	/**
	 * getBusquedaClientes
	 * @return ConfigBusquedas
	 */
	public ConfigBusquedas getBusquedaClientes() {
		return busquedaClientes;
	}
	/**
	 * setBusquedaClientes
	 * @param busquedaClientes - Parametria para busqueda de clientes
	 */
	public void setBusquedaClientes(ConfigBusquedas busquedaClientes) {
		this.busquedaClientes = busquedaClientes;
	}
	/**
	 * getBusquedaListaNegra
	 * @return ConfigBusquedas
	 */
	public ConfigBusquedas getBusquedaListaNegra() {
		return busquedaListaNegra;
	}
	/**
	 * setBusquedaListaNegra
	 * @param busquedaListaNegra - Parametria para busquedas en lista negra
	 */
	public void setBusquedaListaNegra(ConfigBusquedas busquedaListaNegra) {
		this.busquedaListaNegra = busquedaListaNegra;
	}
	/**
	 * getPenalizaciones
	 * @return ConfigPenalizaciones
	 */
	public ConfigPenalizaciones getPenalizaciones() {
		return penalizaciones;
	}
	/**
	 * setPenalizaciones
	 * @param penalizaciones - Penalizaciones al porcentaje de coincidencia
	 */
	public void setPenalizaciones(ConfigPenalizaciones penalizaciones) {
		this.penalizaciones = penalizaciones;
	}
	
}
