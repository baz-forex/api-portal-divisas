package com.baz.divisas.api.tef.limites.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.limites.dao.LimitesPorRegionDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorRegionService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase LimitesPorRegionSegmentoService.
 *
 * @author 164945 ogatica
 */
@Service
public class LimitesPorRegionService implements ILimitesPorRegionService {

	/** The limites region dao. */
	private LimitesPorRegionDao limitesRegionDao;
	
	/** The bitacora. */
	private BitacoraServiceImpl bitacora;

	/**
	 * Constructor de la clase.
	 *
	 * @param limitesRegionDao - LimitesPorRegionDao
	 * @param bitacora - BitacoraServiceImpl
	 */
	@Autowired
	public LimitesPorRegionService(LimitesPorRegionDao limitesRegionDao, BitacoraServiceImpl bitacora) {
		super();
		this.limitesRegionDao = limitesRegionDao;
		this.bitacora = bitacora;
	}

	/**
	 * Metodo de consulta de limite de region.
	 *
	 * @return lista de limite de region
	 */
	@Override
	public ResponseApi consultaLimitesRegion() {
		ResponseApi resultado;
		List<LimitesPorRegionDto> limites = limitesRegionDao.findLimitesRegion();

		if (!limites.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, limites);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por región registrados."));
		}

		return resultado;

	}

	/**
	 * Metodo de actualizacion de limite de region.
	 *
	 * @param request the request
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi creaLimiteRegion(LimitesPorRegionDto request) {
		ResponseApi resultado;

		List<LimitesPorRegionDto> limite = limitesRegionDao.findLimitesRegionId(request, true);

		if (limite.isEmpty()) {
			
			request.setIdBitacoraAlta(bitacora.registrar(AccionBitacora.REGISTRAR,
					"Registro del límites de región " + request.getIdRegion(), null,
					request));
			
			limitesRegionDao.insertLimitesRegion(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un límite de región con el mismo tipo e id de límite."));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza el limite de region.
	 *
	 * @param request - LimitesPorRegionDto
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaLimiteRegion(LimitesPorRegionDto request) {
		ResponseApi resultado;

		List<LimitesPorRegionDto> limiteBD = limitesRegionDao.findLimitesRegionId(request, true);

		if (!limiteBD.isEmpty()) {

			request.setIdBitacoraActualizacion(bitacora.registrar(AccionBitacora.EDITAR,
					"Actualización de límites de región " + request.getIdRegion(), limiteBD, request));

			limitesRegionDao.updateLimitesRegion(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("No existe el límite de región o esta deshabilitado: " + request.getIdRegion());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe el límite de región o esta deshabilitado"));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza el estatus de un limite de region.
	 *
	 * @param req the req
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaEstatusLimiteRegion(LimitesPorRegionDto req) {
		ResponseApi resultado;

		List<LimitesPorRegionDto> limite = limitesRegionDao.findLimitesRegionId(req, !req.isActivo());

		if (!limite.isEmpty()) {

			req.setIdBitacoraActualizacion(bitacora.registrarCambioEstatus(AccionBitacora.EDITAR,
					"Actualización estatus de límites de divias ", req.isActivo()));

			limitesRegionDao.updateEstatusLimitesRegion(req);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("El límite de región no se encuentra configurado ó tiene el mismo estatus: " + req.getIdRegion());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("El límite de región no se encuentra configurado ó tiene el mismo estatus"));
		}

		return resultado;
	}

	/**
	 * Consulta limites region olp.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaLimitesRegionOlp() {
		ResponseApi resultado;
		List<LimitesPorRegionDto> limites = limitesRegionDao.findLimitesRegionOlp();

		if (!limites.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, limites);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por región registrados."));
		}

		return resultado;
	}

	/**
	 * Crea limite region olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi creaLimiteRegionOlp(LimitesPorRegionDto request) {
		ResponseApi resultado;

		List<LimitesPorRegionDto> limite = limitesRegionDao.findLimitesRegionIdOlp(request, true);

		if (limite.isEmpty()) {
			
			request.setIdBitacoraAlta(bitacora.registrar(AccionBitacora.REGISTRAR,
					"Registro del límites de región " + request.getIdRegion(), null,
					request));
			
			limitesRegionDao.insertLimitesRegionOlp(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un límite de región con el mismo tipo e id de límite."));
		}

		return resultado;
	}

	/**
	 * Actualiza limite region olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaLimiteRegionOlp(LimitesPorRegionDto request) {
		ResponseApi resultado;

		List<LimitesPorRegionDto> limiteBD = limitesRegionDao.findLimitesRegionIdOlp(request, true);

		if (!limiteBD.isEmpty()) {

			request.setIdBitacoraActualizacion(bitacora.registrar(AccionBitacora.EDITAR,
					"Actualización de límites de región " + request.getIdRegion(), limiteBD, request));

			limitesRegionDao.updateLimitesRegionOlp(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("No existe el límite de región o esta deshabilitado: " + request.getIdRegion());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe el límite de región o esta deshabilitado"));
		}

		return resultado;
	}

	/**
	 * Actualiza estatus limite region olp.
	 *
	 * @param req the req
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaEstatusLimiteRegionOlp(LimitesPorRegionDto req) {
		ResponseApi resultado;

		List<LimitesPorRegionDto> limite = limitesRegionDao.findLimitesRegionIdOlp(req, !req.isActivo());

		if (!limite.isEmpty()) {

			req.setIdBitacoraActualizacion(bitacora.registrarCambioEstatus(AccionBitacora.EDITAR,
					"Actualización estatus de límites de divias ", req.isActivo()));

			limitesRegionDao.updateEstatusLimitesRegionOlp(req);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("El límite de región no se encuentra configurado ó tiene el mismo estatus: " + req.getIdRegion());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("El límite de región no se encuentra configurado ó tiene el mismo estatus"));
		}

		return resultado;
	}

}
