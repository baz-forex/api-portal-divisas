/**
 * 
 */
package com.baz.divisas.api.tef.dashboard.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.dashboard.dao.DashboardRegionesDAO;
import com.baz.divisas.api.tef.dashboard.dto.RequestRegionTC;
import com.baz.divisas.api.tef.dashboard.services.IDashboardService;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.spots.dao.SpotsDao;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * DashboardServiceImpl
 *
 */
@Service
public class DashboardServiceImpl implements IDashboardService {

	private DashboardRegionesDAO dashBoardDao;
	private RegionesDao regionesDao;
	private SegmentosDao segmentosDao;
	private CatalogosDao catalogosDao;
	private SpotsDao spotsDao;

	
	/**
	 * Instantiates a new dashboard service impl.
	 *
	 * @param dashBoardDao - DashboardRegionesDAO
	 * @param regionesDao - RegionesDao
	 * @param segmentosDao - SegmentosDao
	 * @param catalogosDao - CatalogosDao
	 * @param spotsDao - SpotsDao
	 */
	@Autowired
	public DashboardServiceImpl(DashboardRegionesDAO dashBoardDao, RegionesDao regionesDao, SegmentosDao segmentosDao,
			CatalogosDao catalogosDao, SpotsDao spotsDao) {
		super();
		this.dashBoardDao = dashBoardDao;
		this.regionesDao = regionesDao;
		this.segmentosDao = segmentosDao;
		this.catalogosDao = catalogosDao;
		this.spotsDao = spotsDao;
	}

	@Override
	public ResponseApi consultaRegiones(String idDivisa) {

		ResponseApi resultado;

		List<Document> regiones = dashBoardDao.findRegiones(idDivisa);
		
		if(!regiones.isEmpty()) {
			
			for (Document document : regiones) {

				document.put("_id", document.getObjectId("_id").toHexString());

				List<ObjectId> segmentosRegion = document.getList("segmentos", ObjectId.class);

				List<String> segmentos = new ArrayList<>();

				for (ObjectId segmento : segmentosRegion) {

					segmentos.add(segmento.toHexString());

				}

				document.put("segmentos", segmentos);
			}
			
			resultado = new ResponseApi(EstatusResponse.OK, regiones);
			
		}
		else {
			
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen regiones registradas"));
			
		}

		return resultado;

	}

	@Override
	public ResponseApi consultaSegmentosRegion(String idRegion, String idDivisa) {

		ResponseApi resultado;

		Region region = regionesDao.findById(idRegion);
		
		if(region != null) {
			
			List<Document> segmentosRegion = dashBoardDao.findSegmentos(region.getSegmentos(), idDivisa);
			
			if (!segmentosRegion.isEmpty()) {

				for (Document document : segmentosRegion) {

					document.put("_id", document.getObjectId("_id").toHexString());

				}
				
				resultado = new ResponseApi(EstatusResponse.OK, segmentosRegion);
			} 
			else {
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList("No existen segmentos asignados a la region."));
			}

		}
		else {
			LogApp.mensaje("No existe la region con id: " + idRegion);
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existe la region"));
		}

		return resultado;
	}

	@Override
	public ResponseApi actualizarTCRegion(RequestRegionTC request) {

		ResponseApi resultado;

		Region region = regionesDao.findById(request.getId());

		if (region != null && region.isActivo()) {

			Document divisa = catalogosDao.findDivisaById(request.getDivisa());			
			if (divisa == null) {
				LogApp.mensaje("No existe la divisa o esta inactiva: " + request.getDivisa());
				return new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList("No existe la divisa o esta inactiva"));
			}

			Document tipoOperacion = catalogosDao.findTipoOperacionById(request.getTipoOperacionTC());
			if (tipoOperacion == null) {
				LogApp.mensaje("No existe el tipo de operacion o esta inactivo: " + request.getTipoOperacionTC());
				return new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList("No existe el tipo de operacion o esta inactivo"));
			}
			
			// Actualiza TC Spot de las sucursales de la Region
			if (!region.getSucursales().isEmpty()) {

				spotsDao.updateTCSpotSucursales("",request.getDivisa(), 
						request.getTipoOperacionTC(), request.getMontoTC());
			}

			// Actualiza TC Spot de la Region
			spotsDao.updateTCSpotRegion(request.getId(), request.getDivisa(), 
					request.getTipoOperacionTC(), request.getMontoTC());

			resultado = new ResponseApi(EstatusResponse.OK);
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
										Arrays.asList("No existe la region o esta inactiva"));
		}

		return resultado;

	}

	@Override
	public ResponseApi actualizarTCSegmento(RequestRegionTC request) {
		
		ResponseApi resultado;

		Segmento segmento = segmentosDao.findById(request.getId());

		if (segmento != null && segmento.isActivo()) {

			Document divisa = catalogosDao.findDivisaById(request.getDivisa());
			if (divisa == null) {
				LogApp.mensaje("No existe la divisa o esta inactiva: " + request.getDivisa());
				return new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList("No existe la divisa o esta inactiva"));
			}

			Document tipoOperacion = catalogosDao.findTipoOperacionById(request.getTipoOperacionTC());
			if (tipoOperacion == null) {
				LogApp.mensaje("No existe el tipo de operacion o esta inactivo: " + request.getTipoOperacionTC());
				return new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList("No existe el tipo de operacion o esta inactivo"));
			}
			
			// Actualiza TC Spot de las sucursales del Segmento
			if (!segmento.getSucursales().isEmpty()) {

				spotsDao.updateTCSpotSucursales("",request.getDivisa(), 
						request.getTipoOperacionTC(), request.getMontoTC());
			}

			// Actualiza TC Spot del Segmento
			spotsDao.updateTCSpotSegmento(request.getId(), request.getDivisa(), 
					request.getTipoOperacionTC(), request.getMontoTC());

			resultado = new ResponseApi(EstatusResponse.OK);
						
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existe el segmento o esta inactivo"));
		}

		return resultado;
	}

}
