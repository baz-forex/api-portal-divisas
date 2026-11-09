package com.baz.divisas.api.tef.limites.services;

import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.limites.dto.LimitesPorSegmentoDto;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Clase ILimitesPorRegionSegmentoService.
 *
 * @author 164945
 * ogatica
 */
@Service
public interface ILimitesPorSegmentoService {

	/**
	 * Consulta limites segmento.
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaLimitesSegmento();
	
	/**
	 * Crea limite segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseApi
	 */
	ResponseApi creaLimiteSegmento(LimitesPorSegmentoDto request);
	
	/**
	 * Actualiza limite segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseApi
	 */
	ResponseApi actualizaLimiteSegmento(LimitesPorSegmentoDto request);
	
	/**
	 * Actualiza estatus limite segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseApi
	 */
	ResponseApi actualizaEstatusLimiteSegmento(LimitesPorSegmentoDto request);

	/**
	 * Consulta limites segmento olp.
	 *
	 * @return the response api
	 */
	ResponseApi consultaLimitesSegmentoOlp();

	/**
	 * Crea limite segmento olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi creaLimiteSegmentoOlp(LimitesPorSegmentoDto request);

	/**
	 * Actualiza limite segmento olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi actualizaLimiteSegmentoOlp(LimitesPorSegmentoDto request);

	/**
	 * Actualiza estatus limite segmento olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi actualizaEstatusLimiteSegmentoOlp(LimitesPorSegmentoDto request);
	
}
