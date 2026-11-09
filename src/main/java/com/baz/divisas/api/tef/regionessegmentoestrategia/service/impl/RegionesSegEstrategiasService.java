package com.baz.divisas.api.tef.regionessegmentoestrategia.service.impl;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dao.RegionesSegmentosEstrategiasDao;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.RequestSegmentoEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.ResponseSegmentoEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.service.IRegionesSegmentosEstrategia;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase RegionesEstrategiasService
 * 
 */
@Service
public class RegionesSegEstrategiasService  implements IRegionesSegmentosEstrategia{

	private RegionesSegmentosEstrategiasDao segmentoEstrategiasDao;
	private RegionesDao regionesDao;
	private CatalogosDao catalogosDao;
	private BitacoraServiceImpl bitacora;


	/**
	 * Costructor de la clase
	 *
	 * @param segmetoEstrategiaDao - RegionesSegmentosEstrategiasDao
	 * @param regionesDao - RegionesDao
	 * @param catalogosDao - CatalogosDao
	 * @param bitacora - BitacoraServiceImpl
	 */
	@Autowired
	public RegionesSegEstrategiasService(RegionesSegmentosEstrategiasDao segmetoEstrategiaDao, RegionesDao regionesDao,
			CatalogosDao catalogosDao, BitacoraServiceImpl bitacora) {
		super();
		this.segmentoEstrategiasDao = segmetoEstrategiaDao;
		this.regionesDao = regionesDao;
		this.catalogosDao = catalogosDao;
		this.bitacora = bitacora;
	}

	@Override
	public ResponseApi consultaEstrategiasSegmentos() {
		ResponseApi resultado;

		List<ResponseSegmentoEstrategia> estrategiasSementos = segmentoEstrategiasDao.findAll();

		if(null != estrategiasSementos && !estrategiasSementos.isEmpty()) {

			resultado =  new ResponseApi(EstatusResponse.OK, estrategiasSementos);
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,Arrays.asList("No existen estrategias en los segmentos"));
		}

		return resultado;
	}

	@Override
	public ResponseApi asignarEstrategiaSegmento(RequestSegmentoEstrategia request) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);

		Region resultRegion = regionesDao.findByRegionSegmento(request.getIdRegion(), request.getIdSegmento());
		
		if (resultRegion != null) {
			
			List<ResponseSegmentoEstrategia> estrategiasSegmento = segmentoEstrategiasDao.findByTipo(request);

			if (estrategiasSegmento != null && !estrategiasSegmento.isEmpty()) {

				Document divisa = catalogosDao.findDivisaById(request.getIdDivisa());
				if (divisa == null) {
					LogApp.mensaje("No existe la divisa o esta inactiva: " + request.getIdDivisa());
					return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
							Arrays.asList("No existe la divisa o esta inactiva"));
				}
				
				Document tipoOperacion = catalogosDao.findTipoOperacionById(request.getTipoOperacion());
				if (tipoOperacion == null) {
					LogApp.mensaje("No existe el tipo de operacion o esta inactivo: " + request.getTipoOperacion());
					return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
							Arrays.asList("No existe el tipo de operacion o esta inactivo"));
				}
				
				String idBitacora = bitacora.registrar(AccionBitacora.REGISTRAR,
						"Registro de estrategia en el segmento: " + request.getIdSegmento(), null, request);

				segmentoEstrategiasDao.updateEstrategiaSegmento(request, idBitacora);

			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					 	Arrays.asList("El segmento no existe, esta inactivo o no contiene la estrategia"));
			}
			
		}
		else {
			
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("La region no existe, esta inactiva o no tiene asignado el segmento"));			
		}

		return resultado;

	}

	@Override
	public ResponseApi actualizarEstatusEstrategiaSegmento(RequestSegmentoEstrategia request) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);

		Region resultRegion = regionesDao.findByRegionSegmento(request.getIdRegion(), request.getIdSegmento());
		
		if (resultRegion != null) {
			
			List<ResponseSegmentoEstrategia> estrategiasSegmento = segmentoEstrategiasDao.findByTipo(request);

			if (estrategiasSegmento != null && !estrategiasSegmento.isEmpty()) {

				Document divisa = catalogosDao.findDivisaById(request.getIdDivisa());
				if (divisa == null) {
					LogApp.mensaje("No existe la divisa o esta inactiva: " + request.getIdDivisa());
					return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
							Arrays.asList("No existe la divisa o esta inactiva"));
				}
				
				Document tipoOperacion = catalogosDao.findTipoOperacionById(request.getTipoOperacion());
				if (tipoOperacion == null) {
					LogApp.mensaje("No existe el tipo de operacion o esta inactivo: " + request.getTipoOperacion());
					return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
							Arrays.asList("No existe el tipo de operacion o esta inactivo"));
				}

				String idBitacora = bitacora.registrarCambioEstatus(AccionBitacora.EDITAR, 
						"Actualizacion de estatus de la estrategia en el segmento: " 
					  + request.getIdSegmento(), request.isActivoEstrategia());
				
				segmentoEstrategiasDao.updateEstatusEstrategiaSegmento(request, idBitacora);

			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					 	Arrays.asList("El segmento no existe, esta inactivo o no contiene la estrategia"));
			}
			
		}
		else {
			
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("La region no existe, esta inactiva o no tiene asignado el segmento"));			
		}

		return resultado;

	}

}
