package com.baz.divisas.api.tef.regionessegmento.services;

import java.util.List;


import org.springframework.stereotype.Service;

import com.baz.divisas.general.dto.ResponseApi;

/**
 * The Interface IRegionSegmentoService.
 *
 * @author 1077023
 */
@Service
public interface IRegionSegmentoService {
	
	/**
	 * Consulta los segmentos en las regiones.
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaSegmentosRegiones();
	
	/**
	 * Asigna segmentos a una region.
	 *
	 * @param idRegion - Id de la region
	 * @param idsSegmentos - Ids de los segmentos
	 * @return ResponseApi
	 */
	ResponseApi asignarSegmentosRegion(String idRegion, List<String> idsSegmentos);
	
	/**
	 * Elimina un segmento de la region.
	 *
	 * @param idRegion - Id de la region
	 * @param idSegmento - Id del segmento
	 * @return ResponseApi
	 */
	ResponseApi eliminaSegmentoRegion(String idRegion, String idSegmento);

	/**
	 * Consulta segmentos regiones olp.
	 *
	 * @return the response api
	 */
	ResponseApi consultaSegmentosRegionesOlp();

	/**
	 * Asignar segmentos region olp.
	 *
	 * @param idRegion the id region
	 * @param idSegmento the id segmento
	 * @return the response api
	 */
	ResponseApi asignarSegmentosRegionOlp(String idRegion, List<String> idSegmento);

	/**
	 * Elimina segmento region olp.
	 *
	 * @param idRegion the id region
	 * @param idSegmento the id segmento
	 * @return the response api
	 */
	ResponseApi eliminaSegmentoRegionOlp(String idRegion, String idSegmento);

}
