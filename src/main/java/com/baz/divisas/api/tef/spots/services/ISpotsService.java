/**
 * 
 */
package com.baz.divisas.api.tef.spots.services;

import java.util.List;


import com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.baz.divisas.general.dto.ResponseApi;

import org.springframework.stereotype.Service;

/**
 * Interface SpotService.
 *
 * @author 1077023
 */
@Service
public interface ISpotsService {
	
	/**
	 * Consulta Spots.
	 *
	 * @return Spots
	 */
	ResponseApi consultaSpots();
	
	/**
	 * Consulta Spots por fecha.
	 *
	 * @param fechaInicio - Fecha inicio
	 * @param fechaFin    - Fecha fin
	 * @param idsDivisas  - Ids divisas
	 * @return Spots
	 */
	ResponseApi consultaSpotPorFecha(String fechaInicio, String fechaFin, List<String> idsDivisas);
	
	/**
	 * Consulta Spots vigentes.
	 *
	 * @param idsDivisas - Ids Divisas
	 * @return Spots
	 */
	ResponseApi consultaSpotsVigentes(List<String> idsDivisas);
	
	/**
	 * Registro de Spot.
	 *
	 * @param spot - Nuevo Spot
	 * @return Resultado
	 */
	ResponseApi creaSpots(List<Spot> spot);

	/**
	 * Crea spot OLP.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi creaSpotOlp(Spot request);

	/**
	 * Consulta spots tesoreria.
	 *
	 * @return the response api
	 */
	ResponseApi consultaSpotsTesoreria();

	/**
	 * Consulta spots vigentes OLP.
	 *
	 * @return the response api
	 */
	ResponseApi consultaSpotsVigentesOLP();

	/**
	 * Consulta configuracion spot OLP.
	 *
	 * @return the response api
	 */
	ResponseApi consultaConfiguracionSpotOLP();

	/**
	 * Guarda configuracion spot OLP.
	 *
	 * @param spot the spot
	 * @return the response api
	 */
	ResponseApi guardaConfiguracionSpotOLP(ReqConfiguracionSpot spot);

}
