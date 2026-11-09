package com.baz.divisas.api.tef.catalogos.service;

import org.springframework.stereotype.Service;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * The Interface IOperaciones.
 */
@Service
public interface ICatalogos {
	
	/**
	 * Gets Tipos de operacion.
	 *
	 * @return Tipos operacion
	 */
	ResponseApi getTiposOperacion();
	
	/**
	 * Actualiza estatus.
	 *
	 * @param tipoOperacion - Tipo de operacion
	 * @return ResponseApi
	 */
	ResponseApi actualizaEstatusTipoOperacion(Catalogo tipoOperacion);

}
