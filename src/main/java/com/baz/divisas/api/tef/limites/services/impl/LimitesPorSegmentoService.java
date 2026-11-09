package com.baz.divisas.api.tef.limites.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.limites.dao.LimitesPorSegmentoDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSegmentoDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorSegmentoService;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase LimitesPorRegionSegmentoService.
 *
 * @author 164945 ogatica
 */
@Service
public class LimitesPorSegmentoService implements ILimitesPorSegmentoService {

	/** The limites por segmento dao. */
	private LimitesPorSegmentoDao limitesPorSegmentoDao;
	
	/** The regiones dao. */
	private RegionesDao regionesDao;
	
	/** The bitacora. */
	private BitacoraServiceImpl bitacora;

	/**
	 * Constructor de la clase.
	 *
	 * @param limitesPorSegmentoDao - LimitesPorSegmentoDao
	 * @param regionesDao - RegionesDao
	 * @param bitacora - BitacoraServiceImpl
	 */
	@Autowired
	public LimitesPorSegmentoService(LimitesPorSegmentoDao limitesPorSegmentoDao, RegionesDao regionesDao,
			BitacoraServiceImpl bitacora) {
		super();
		this.limitesPorSegmentoDao = limitesPorSegmentoDao;
		this.regionesDao = regionesDao;
		this.bitacora = bitacora;
	}

	/**
	 * Metodo de consulta de limite de segmentos.
	 *
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi consultaLimitesSegmento() {
		ResponseApi resultado;
		List<LimitesPorSegmentoDto> limites = limitesPorSegmentoDao.findLimitesSegmento();

		if (!limites.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, limites);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por segmento registrados."));
		}

		return resultado;

	}

	/**
	 * Metodo de registro de limite de Segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi creaLimiteSegmento(LimitesPorSegmentoDto request) {
		ResponseApi resultado;

		Region region = regionesDao.findByRegionSegmento(request.getIdRegion(), request.getIdSegmento());
		
		if(region != null) {
			
			List<LimitesPorSegmentoDto> limites = limitesPorSegmentoDao.findLimitesSegmentoId(request, true);
			
			if (limites.isEmpty()) {

				request.setIdBitacoraAlta(bitacora.registrar(AccionBitacora.REGISTRAR,
						"Registro de limite del segmento " + request.getIdSegmento(), null, request));

				limitesPorSegmentoDao.insertLimitesSegmento(request);

				resultado = new ResponseApi(EstatusResponse.OK);

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Ya existe un limite de Segmento con el mismo tipo e id de limite."));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("La region no existe, esta inactiva o no tiene asignado el segmento"));
		}

		return resultado;
	}

	/**
	 * Metodo de actualizacion de limite de Segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaLimiteSegmento(LimitesPorSegmentoDto request) {
		ResponseApi resultado;
		
		Region region = regionesDao.findByRegionSegmento(request.getIdRegion(), request.getIdSegmento());
		
		if(region != null) {
			
			List<LimitesPorSegmentoDto> limites = limitesPorSegmentoDao.findLimitesSegmentoId(request, true);
			
			if (!limites.isEmpty()) {

				request.setIdBitacoraActualizacion(bitacora.registrar(AccionBitacora.EDITAR,
						"Actualización de límite del segmento " + request.getIdSegmento(), limites, request));

				limitesPorSegmentoDao.updateLimitesSegmento(request);

				resultado = new ResponseApi(EstatusResponse.OK);

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("El segmento no existe o esta inactivo"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("La region no existe, esta inactiva o no tiene asignado el segmento"));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza el estatus de un límite de Segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaEstatusLimiteSegmento(LimitesPorSegmentoDto request) {
		ResponseApi resultado;

		List<LimitesPorSegmentoDto> limite = limitesPorSegmentoDao.findLimitesSegmentoId(request, !request.isActivo());

		if (!limite.isEmpty()) {

			request.setIdBitacoraActualizacion(bitacora.registrarCambioEstatus(AccionBitacora.EDITAR,
					"Actualización estatus de límite del segmento ", request.isActivo()));

			limitesPorSegmentoDao.updateEstatusLimitesSegmento(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("El límite del segmento no se encuentra configurado ó tiene el mismo estatus: "
					+ request.getIdSegmento());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("El límite del segmento no se encuentra configurado ó tiene el mismo estatus"));
		}

		return resultado;
	}

	/**
	 * Consulta limites segmento olp.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaLimitesSegmentoOlp() {
		ResponseApi resultado;
		List<LimitesPorSegmentoDto> limites = limitesPorSegmentoDao.findLimitesSegmentoOlp();

		if (!limites.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, limites);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por segmento registrados."));
		}

		return resultado;
	}

	/**
	 * Crea limite segmento olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi creaLimiteSegmentoOlp(LimitesPorSegmentoDto request) {
		ResponseApi resultado;

		Region region = regionesDao.findByRegionSegmentoOlp(request.getIdRegion(), request.getIdSegmento());
		
		if(region != null) {
			
			List<LimitesPorSegmentoDto> limites = limitesPorSegmentoDao.findLimitesSegmentoIdOlp(request, true);
			
			if (limites.isEmpty()) {

				request.setIdBitacoraAlta(bitacora.registrar(AccionBitacora.REGISTRAR,
						"Registro de limite del segmento " + request.getIdSegmento(), null, request));

				limitesPorSegmentoDao.insertLimitesSegmentoOlp(request);

				resultado = new ResponseApi(EstatusResponse.OK);

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Ya existe un limite de Segmento con el mismo tipo e id de limite."));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("La region no existe, esta inactiva o no tiene asignado el segmento"));
		}

		return resultado;
	}

	/**
	 * Actualiza limite segmento olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaLimiteSegmentoOlp(LimitesPorSegmentoDto request) {
		ResponseApi resultado;
		
		Region region = regionesDao.findByRegionSegmentoOlp(request.getIdRegion(), request.getIdSegmento());
		
		if(region != null) {
			
			List<LimitesPorSegmentoDto> limites = limitesPorSegmentoDao.findLimitesSegmentoIdOlp(request, true);
			
			if (!limites.isEmpty()) {

				request.setIdBitacoraActualizacion(bitacora.registrar(AccionBitacora.EDITAR,
						"Actualización de límite del segmento " + request.getIdSegmento(), limites, request));

				limitesPorSegmentoDao.updateLimitesSegmentoOlp(request);

				resultado = new ResponseApi(EstatusResponse.OK);

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("El segmento no existe o esta inactivo"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("La region no existe, esta inactiva o no tiene asignado el segmento"));
		}

		return resultado;
	}

	/**
	 * Actualiza estatus limite segmento olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaEstatusLimiteSegmentoOlp(LimitesPorSegmentoDto request) {
		ResponseApi resultado;

		List<LimitesPorSegmentoDto> limite = limitesPorSegmentoDao.findLimitesSegmentoIdOlp(request, !request.isActivo());

		if (!limite.isEmpty()) {

			request.setIdBitacoraActualizacion(bitacora.registrarCambioEstatus(AccionBitacora.EDITAR,
					"Actualización estatus de límite del segmento ", request.isActivo()));

			limitesPorSegmentoDao.updateEstatusLimitesSegmentoOlp(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("El límite del segmento no se encuentra configurado ó tiene el mismo estatus: "
					+ request.getIdSegmento());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("El límite del segmento no se encuentra configurado ó tiene el mismo estatus"));
		}

		return resultado;
	}

}
