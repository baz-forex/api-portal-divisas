package com.baz.divisas.api.tef.regionesestrategias.service;

import org.springframework.stereotype.Service;
import com.baz.divisas.api.tef.regionesestrategias.dto.RequestEstrategia;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * IRegionesEstrategias.
 *
 */
@Service
public interface IRegionesEstrategias {
	
	/**
	 * Consulta estrategias en las regiones
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaEstrategiasRegiones();
	
	/**
	 * Asignar estrategia a una region.
	 *
	 * @param request - RequestEstrategia
	 * @return ResponseApi
	 */
	ResponseApi asignarEstrategiaRegion(RequestEstrategia request);
	
	/**
	 * Actualizar estatus de la estrategia de una region.
	 *
	 * @param request - RequestEstrategia
	 * @return ResponseApi
	 */
	ResponseApi actualizarEstatusEstrategiaRegion(RequestEstrategia request);

}
