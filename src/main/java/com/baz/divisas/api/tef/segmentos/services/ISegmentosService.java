package com.baz.divisas.api.tef.segmentos.services;

import com.baz.divisas.general.dto.ResponseApi;

import org.springframework.stereotype.Service;

/**
 * Clase ISegmentosService.
 *
 * @author 164945 ogatica
 */
@Service
public interface ISegmentosService {

	/**
	 * Consulta segmentos.
	 *
	 * @return the response api
	 */
	ResponseApi consultaSegmentos();

	/**
	 * Crea segmentos.
	 *
	 * @param descripcion - Descripcion Segmento
	 * @return the response api
	 */
	ResponseApi creaSegmento(String descripcion);

	/**
	 * Actualiza descripcion del segmento.
	 *
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @return ResponseApi
	 */
	ResponseApi actualizaDescripcionSegmento(String id, String descripcion);

	/**
	 * Actualiza estatus segmentos.
	 *
	 * @param id the id
	 * @return the response api
	 */
	ResponseApi actualizaEstatusSegmento(String id);

	/**
	 * Elimina segmento.
	 *
	 * @param id - Id
	 * @return the response api
	 */
	ResponseApi eliminaSegmento(String id);

	//OLP
	
	/**
	 * Consulta segmentos olp.
	 *
	 * @return the response api
	 */
	ResponseApi consultaSegmentosOlp();

	/**
	 * Actualiza estatus segmento olp.
	 *
	 * @param id the id
	 * @return the response api
	 */
	ResponseApi actualizaEstatusSegmentoOlp(String id);

	/**
	 * Elimina segmento olp.
	 *
	 * @param id the id
	 * @return the response api
	 */
	ResponseApi eliminaSegmentoOlp(String id);

	/**
	 * Crea segmento olp.
	 *
	 * @param descripcion the descripcion
	 * @return the response api
	 */
	ResponseApi creaSegmentoOlp(String descripcion);

	/**
	 * Actualiza descripcion segmento olp.
	 *
	 * @param id the id
	 * @param descripcion the descripcion
	 * @return the response api
	 */
	ResponseApi actualizaDescripcionSegmentoOlp(String id, String descripcion);

}
