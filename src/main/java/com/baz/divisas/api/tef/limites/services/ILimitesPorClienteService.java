package com.baz.divisas.api.tef.limites.services;

import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.limites.dto.LimitesPorClienteDto;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Clase ILimitesPorClienteService.
 *
 * @author 164945
 * ogatica
 */
@Service
public interface ILimitesPorClienteService {

	/**
	 * Consulta limites cliente.
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaLimitesCliente();
	
	/**
	 * Crea limite cliente.
	 *
	 * @param request the request
	 * @return ResponseApi
	 */
	ResponseApi creaLimiteCliente(LimitesPorClienteDto request);
	
	/**
	 * Actualiza limite cliente.
	 *
	 * @param request the request
	 * @return ResponseApi
	 */
	ResponseApi actualizaLimiteCliente(LimitesPorClienteDto request);
	
	/**
	 * Actualiza estatus limite cliente.
	 *
	 * @param id - Id Limite
	 * @return ResponseApi
	 */
	ResponseApi actualizaEstatusLimiteCliente(String id);

	ResponseApi consultaLimitesClienteOlp();
	
}
