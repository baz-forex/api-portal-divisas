package com.baz.divisas.api.tef.regionessegmentoestrategia.service;

import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.RequestSegmentoEstrategia;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Clase IRegionesSegmentosEstrategia
 * 
 */
@Service
public interface IRegionesSegmentosEstrategia {

	/**
	 * Consulta estrategias en los segmentos
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaEstrategiasSegmentos();
	
	/**
	 * Asignar estrategia a un segmento
	 *
	 * @param request - RequestSegmentoEstrategia
	 * @return ResponseApi
	 */
	ResponseApi asignarEstrategiaSegmento(RequestSegmentoEstrategia request);
	
	/**
	 * Actualizar estatus de la estrategia de un segmento.
	 *
	 * @param request - RequestSegmentoEstrategia
	 * @return ResponseApi
	 */
	ResponseApi actualizarEstatusEstrategiaSegmento(RequestSegmentoEstrategia request);
}
