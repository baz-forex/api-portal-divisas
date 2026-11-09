package com.baz.divisas.api.tef.competencias.services;

import org.springframework.stereotype.Service;

import com.baz.divisas.general.dto.ResponseApi;


/**
 * The Interface ICargaCompetenciasService.
 *
 * @author 1023825
 */
@Service
public interface ICargaCompetenciasService {

	/**
	 * Consulta competencias fecha sucursal.
	 *
	 * @param competencias the competencias
	 * @return the response api
	 */
	ResponseApi consultaCompetenciasFecha(String competencias);

	/**
	 * Consulta competencias agrupadas.
	 *
	 * @return the response api
	 */
	ResponseApi consultaCompetenciasAgrupadas();
	
}
