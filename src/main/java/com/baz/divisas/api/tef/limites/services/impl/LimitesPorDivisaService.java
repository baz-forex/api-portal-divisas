package com.baz.divisas.api.tef.limites.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.limites.dao.LimitesPorDivisaDao;
import com.baz.divisas.api.tef.limites.services.ILimitesPorDivisaService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase LimitesPorDivisaService
 * 
 * @author 164945 ogatica
 */
@Service
public class LimitesPorDivisaService implements ILimitesPorDivisaService {

	private LimitesPorDivisaDao limitesDivisasDao;
	private BitacoraServiceImpl bitacora;

	/**
	 * Constructor de la clase
	 *
	 * @param limitesDivisasDao - LimitesPorDivisaDao
	 * @param bitacora - BitacoraServiceImpl
	 */
	@Autowired
	public LimitesPorDivisaService(LimitesPorDivisaDao limitesDivisasDao, BitacoraServiceImpl bitacora) {
		super();
		this.limitesDivisasDao = limitesDivisasDao;
		this.bitacora = bitacora;
	}

	/**
	 * Metodo de consulta de limite de divisa
	 * 
	 * @return lista de limite de divisa
	 */
	@Override
	public ResponseApi consultaLimitesDivisa() {
		ResponseApi resultado;
		List<Limites> limites = limitesDivisasDao.findLimitesDivias();

		if (!limites.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, limites);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por divisa registrados."));
		}

		return resultado;

	}

	/**
	 * Metodo de registro de limite por divisa
	 * 
	 * @param request - Limites
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi creaLimiteDivisa(Limites request) {
		ResponseApi resultado;

		List<Limites> limite = limitesDivisasDao.findLimitesDiviasId(request);

		if (limite.isEmpty()) {

			request.setIdBitacoraAlta(bitacora.registrar(AccionBitacora.REGISTRAR,
					"Registro del limites de divisas " + request.getCodigoDivisa(), null, request));

			request.setActivo(true);
			
			limitesDivisasDao.insertLimitesDivias(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un límite de divisa con el mismo tipo e id de límite."));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza un limite por divisa
	 * 
	 * @param request - Limites
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaLimiteDivisa(Limites request) {
		ResponseApi resultado;

		List<Limites> limiteBD = limitesDivisasDao.findLimitesDiviasId(request);

		if (!limiteBD.isEmpty()) {

			request.setIdBitacoraActualizacion(bitacora.registrar(AccionBitacora.EDITAR,
					"Actualización de límites de divias " + request.getCodigoDivisa(), limiteBD, request));

			limitesDivisasDao.updateLimitesDivisas(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("No existe el límite de divisa: " + request.getCodigoDivisa());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe el límite de divisa"));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza el estatus de un limite de divisa
	 * 
	 * @param request - Limites
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaEstatusLimiteDivisa(Limites request) {
		ResponseApi resultado;

		List<Limites> limite = limitesDivisasDao.findLimitesDiviasId(request);

		if (!limite.isEmpty()) {

			request.setIdBitacoraActualizacion(bitacora.registrarCambioEstatus(AccionBitacora.EDITAR,
					"Actualización estatus de límites de divias ", request.isActivo()));

			limitesDivisasDao.updateEstatusLimitesDivisas(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("No existe el límite de divisa: " + request.getCodigoDivisa());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe el límite de divisa"));
		}

		return resultado;
	}

	/**
	 * Metodo de consulta de limite de divisa
	 * 
	 * @return lista de limite de divisa
	 */
	@Override
	public ResponseApi consultaLimitesDivisaOLP() {
		ResponseApi resultado;
		List<Limites> limites = limitesDivisasDao.findLimitesDivisaOLP();

		if (!limites.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, limites);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por divisa registrados."));
		}

		return resultado;

	}
}
