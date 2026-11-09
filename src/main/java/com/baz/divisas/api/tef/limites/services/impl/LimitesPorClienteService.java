package com.baz.divisas.api.tef.limites.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.limites.dao.LimitesPorClienteDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorClienteDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorClienteService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase LimitesPorClienteService
 * 
 * @author 164945 ogatica
 */
@Service
public class LimitesPorClienteService implements ILimitesPorClienteService {

	private LimitesPorClienteDao limitesPorClienteDao;
	private BitacoraServiceImpl bitacora;

	/**
	 * Constructor de la clase
	 *
	 * @param limitesPorClienteDao - LimitesPorClienteDao
	 * @param bitacora - BitacoraServiceImpl
	 */
	@Autowired
	public LimitesPorClienteService(LimitesPorClienteDao limitesPorClienteDao, BitacoraServiceImpl bitacora) {
		super();
		this.limitesPorClienteDao = limitesPorClienteDao;
		this.bitacora = bitacora;
	}

	/**
	 * Metodo de consulta de limite de cliente
	 * 
	 * @return lista de limite de cliente
	 */
	@Override
	public ResponseApi consultaLimitesCliente() {
		ResponseApi resultado;
		List<LimitesPorClienteDto> limites = limitesPorClienteDao.findLimitesCliente();

		if (!limites.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, limites);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por cliente registrados."));
		}

		return resultado;

	}

	/**
	 * Metodo de actualizacion de limite de cliente
	 * 
	 * @param request - LimitesPorClienteDto
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi creaLimiteCliente(LimitesPorClienteDto request) {
		ResponseApi resultado;
		
		LimitesPorClienteDto limite = limitesPorClienteDao.findLimitesClienteId(request);
		
		if (limite == null) {

			request.setIdBitacoraAlta(bitacora.registrar(AccionBitacora.REGISTRAR,
					"Registro del limites de clientes " + request.getIdClienteDivisas(), null,
					request));

			request.setActivo(true);
			
			limitesPorClienteDao.insertLimitesCliente(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un limite de cliente con el mismo tipo e id de limite."));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza limite de cliente
	 * 
	 * @param idLimite - Identificador del limite de cliente
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaLimiteCliente(LimitesPorClienteDto req) {
		ResponseApi resultado;
		LimitesPorClienteDto limite = limitesPorClienteDao.findLimitesClienteId(req.getId());

		if (limite != null) {

			req.setIdBitacoraActualizacion(bitacora.registrar(AccionBitacora.EDITAR,
					"Actualización del limite por cliente: " + req.getIdClienteDivisas(), limite, req));

			limitesPorClienteDao.updateLimitesCliente(req);

			resultado = new ResponseApi(EstatusResponse.OK);

		}
		else {
			LogApp.mensaje("No existe la limite de cliente: " + req.getIdClienteDivisas());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe la limite de cliente"));
		}

		return resultado;
	}

	
	/**
	 * Metodo que actualiza el estatus de un limite de cliente
	 * 
	 * @param idCliente - Identificador del limite de cliente
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaEstatusLimiteCliente(String id) {
		ResponseApi resultado;
		LimitesPorClienteDto limite = limitesPorClienteDao.findLimitesClienteId(id);

		if (limite != null) {

			String idBitacora = bitacora.registrarCambioEstatus(AccionBitacora.EDITAR,
					"Actualización estatus del limite por cliente: " + limite.getIdClienteDivisas(),
					!limite.isActivo());

			limitesPorClienteDao.updateEstatusLimitesCliente(id, !limite.isActivo(), idBitacora);

			resultado = new ResponseApi(EstatusResponse.OK);

		}
		else {
			LogApp.mensaje("No existe limite del id: " + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe limite con ese id"));
		}

		return resultado;
	}

	@Override
	public ResponseApi consultaLimitesClienteOlp() {
		ResponseApi resultado;
		List<LimitesPorClienteDto> limites = limitesPorClienteDao.findLimitesClienteOlp();

		if (!limites.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, limites);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por cliente registrados."));
		}

		return resultado;
	}

}
