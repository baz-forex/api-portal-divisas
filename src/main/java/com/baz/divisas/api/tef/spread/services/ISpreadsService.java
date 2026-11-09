/**
 * 
 */
package com.baz.divisas.api.tef.spread.services;


import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.spread.dto.Spread;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Interface SpreadsService
 * @author 813196
 *
 */
@Service
public interface ISpreadsService {
	
	/**
	 * Consulta Spreads
	 * @return ResponseApi
	 */
	ResponseApi consultaSpreads();

	/**
	 * Consulta Spreads por Divisa
	 * 
	 * @param idDivisa - Id Divisa
	 * @return ResponseApi
	 */
	ResponseApi consultaSpreadsDivisa(String idDivisa);
	
	
	/**
	 * Actualizacion de Spread por Divisa
	 * @param spread - Spread
	 * @return ResponseApi
	 */
	ResponseApi actualizaSpreadDivisa(Spread spread);

}
