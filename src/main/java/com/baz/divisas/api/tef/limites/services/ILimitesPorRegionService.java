package com.baz.divisas.api.tef.limites.services;

import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Clase ILimitesPorRegionService.
 *
 * @author 164945
 * ogatica
 */
@Service
public interface ILimitesPorRegionService {

	/**
	 * Consulta limites region.
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaLimitesRegion();
	
	/**
	 * Crea limite region.
	 *
	 * @param request - LimitesPorRegionDto
	 * @return ResponseApi
	 */
	ResponseApi creaLimiteRegion(LimitesPorRegionDto request);
	
	/**
	 * Actualiza limite region.
	 *
	 * @param request - LimitesPorRegionDto
	 * @return ResponseApi
	 */
	ResponseApi actualizaLimiteRegion(LimitesPorRegionDto request);
	
	/**
	 * Actualiza estatus limite region.
	 *
	 * @param request - LimitesPorRegionDto
	 * @return ResponseApi
	 */
	ResponseApi actualizaEstatusLimiteRegion(LimitesPorRegionDto request);

	/**
	 * Consulta limites region olp.
	 *
	 * @return the response api
	 */
	ResponseApi consultaLimitesRegionOlp();

	/**
	 * Crea limite region olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi creaLimiteRegionOlp(LimitesPorRegionDto request);

	/**
	 * Actualiza limite region olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi actualizaLimiteRegionOlp(LimitesPorRegionDto request);

	/**
	 * Actualiza estatus limite region olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi actualizaEstatusLimiteRegionOlp(LimitesPorRegionDto request);
	
}
