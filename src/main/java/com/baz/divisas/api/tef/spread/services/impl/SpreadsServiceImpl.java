/**
 * 
 */
package com.baz.divisas.api.tef.spread.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.spread.dao.SpreadsDao;
import com.baz.divisas.api.tef.spread.dto.Spread;
import com.baz.divisas.api.tef.spread.services.ISpreadsService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.UpdateResult;

/**
 * @author 1077023
 *
 */
@Service
public class SpreadsServiceImpl implements ISpreadsService {

	private SpreadsDao spreadsDao;
	
	private BitacoraServiceImpl bitacora;

	/**
	 * Constructor de la clase
	 * @param spreadsDao - SpreadDao
	 * @param bitacora   - BitacoraService
	 */
	@Autowired
	public SpreadsServiceImpl(SpreadsDao spreadsDao, BitacoraServiceImpl bitacora) {
		super();
		this.spreadsDao = spreadsDao;
		this.bitacora = bitacora;
	}

	@Override
	public ResponseApi consultaSpreads() {
		ResponseApi resultado;
		List<Spread> spreads = spreadsDao.findSpreads();
		
		if (!spreads.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, spreads);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen Spreads registrados."));
		}

		return resultado;
	}

	@Override
	public ResponseApi consultaSpreadsDivisa(String idDivisa) {
		ResponseApi resultado;
		Spread spread = spreadsDao.findSpreadDivisa(idDivisa);
		
		if (spread != null) {
			resultado = new ResponseApi(EstatusResponse.OK, spread);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen Spreads registrados."));
		}

		return resultado;
	}

	@Override
	public ResponseApi actualizaSpreadDivisa(Spread spread) {
		ResponseApi resultado;
		Spread spreadAnterior = spreadsDao.findSpreadDivisa(spread.getIdDivisa());
		
		UpdateResult updateResult = spreadsDao.updateSpreadDivisa(spread);
		
		if (updateResult.getMatchedCount() > 0) {
			
			resultado = new ResponseApi(EstatusResponse.OK);
			
			if(updateResult.getModifiedCount() > 0) {
				
				bitacora.registrar(AccionBitacora.EDITAR, "Actualización Spread "+ spread.getIdDivisa(), 
						spreadAnterior, spread);
			}
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No fue posible actualizar el Spread."));
		}

		return resultado;
	}


}
