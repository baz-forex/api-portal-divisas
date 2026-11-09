/**
 * 
 */
package com.baz.divisas.api.tef.regionsegmentosucursal.services;

import java.util.List;

import com.baz.divisas.general.dto.ResponseApi;

/**
 * The Interface IRegionSegmentoSucursalService.
 *
 * @author 1077023
 */
public interface IRegionSegmentoSucursalService {

	/**
	 * Consulta las sucursales en los segmentos.
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaSucursalesSegmento();
	
	/**
	 * Asigna sucursales a una region.
	 *
	 * @param idSegmento - Id del segmento
	 * @param idsSucursales - Ids de las sucursales
	 * @return ResponseApi
	 */
	ResponseApi asignarSucursalesSegmento(String idSegmento, List<String> idsSucursales);

	/**
	 * Elimina una sucursal del segmento.
	 *
	 * @param idSegmento - Id del segmento
	 * @param idSucursal - Id de la sucursal
	 * @return ResponseApi
	 */
	ResponseApi eliminarSucursalesSegmento(String idSegmento, String idSucursal);

	/**
	 * Consulta TC segmento sucursales.
	 *
	 * @param idSegmento the id segmento
	 * @param idDivisa the id divisa
	 * @return the response api
	 */
	ResponseApi consultaTCSegmentoSucursales(String idSegmento, String idDivisa);

	/**
	 * Consulta TC segmento sucursales OLP.
	 *
	 * @param idSegmento the id segmento
	 * @return the response api
	 */
	ResponseApi consultaTCSegmentoSucursalesOlp(String idSegmento);

	/**
	 * Consulta sucursales segmento olp.
	 *
	 * @return the response api
	 */
	ResponseApi consultaSucursalesSegmentoOlp();

	/**
	 * Eliminar sucursales segmento olp.
	 *
	 * @param idSegmento the id segmento
	 * @param idSucursal the id sucursal
	 * @return the response api
	 */
	ResponseApi eliminarSucursalesSegmentoOlp(String idSegmento, String idSucursal);

	/**
	 * Asignar sucursales segmento olp.
	 *
	 * @param idSegmento the id segmento
	 * @param sucursales the sucursales
	 * @return the response api
	 */
	ResponseApi asignarSucursalesSegmentoOlp(String idSegmento, List<String> sucursales);
	
}
