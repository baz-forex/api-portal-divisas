package com.baz.divisas.api.tef.limites.services;

import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Clase ILimitesPorClienteService
 * 
 * @author 164945
 * ogatica
 */
@Service
public interface ILimitesPorDivisaService {

	public ResponseApi consultaLimitesDivisa();
	public ResponseApi creaLimiteDivisa(Limites req);
	public ResponseApi actualizaLimiteDivisa(Limites req);
	public ResponseApi actualizaEstatusLimiteDivisa(Limites req);
	public ResponseApi consultaLimitesDivisaOLP();
	
}
