package com.baz.divisas.api.tef.dashboard.services;

import com.baz.divisas.api.tef.dashboard.dto.RequestRegionTC;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * IDashboardService.
 */
public interface IDashboardService {
	
	/**
	 * Consulta regiones.
	 *
	 * @param idDivisa the id divisa
	 * @return the response api
	 */
	ResponseApi consultaRegiones(String idDivisa);
	
	/**
	 * Consulta segemntos.
	 *
	 * @param idRegion the id region
	 * @param idDivisa the id divisa
	 * @return the response api
	 */
	ResponseApi consultaSegmentosRegion(String idRegion, String idDivisa);
	
	/**
	 * Actualizar TC region.
	 *
	 * @param requestRegionTC the request region TC
	 * @return the response api
	 */
	ResponseApi actualizarTCRegion(RequestRegionTC requestRegionTC);
	
	/**
	 * Actualizar TC segemento.
	 *
	 * @param requestRegionTC the request region TC
	 * @return the response api
	 */
	ResponseApi actualizarTCSegmento(RequestRegionTC requestRegionTC); 
	
}
