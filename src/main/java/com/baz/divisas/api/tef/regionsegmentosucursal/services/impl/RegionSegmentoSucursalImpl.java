/**
 * 
 */
package com.baz.divisas.api.tef.regionsegmentosucursal.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.general.model.SegmentoSucursales;
import com.baz.divisas.api.tef.general.model.SucursalAux;
import com.baz.divisas.api.tef.general.model.TC;
import com.baz.divisas.api.tef.regiones.model.TipoOperacion;
import com.baz.divisas.api.tef.regionsegmentosucursal.dto.SegmentoSucursal;
import com.baz.divisas.api.tef.regionsegmentosucursal.services.IRegionSegmentoSucursalService;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * RegionSegmentoSucursalImpl
 *
 */
@Service
public class RegionSegmentoSucursalImpl implements IRegionSegmentoSucursalService {

	private SegmentosDao segmentoDao;
	private SucursalesDao sucursalDao;
	private BitacoraServiceImpl bitacora;
		
	/**
	 * Constructor de la clase
	 *
	 * @param segmentoDAO the segmento DAO
	 * @param sucursalDAO the sucursal DAO
	 * @param bitacora the bitacora
	 */
	@Autowired
	public RegionSegmentoSucursalImpl(SegmentosDao segmentoDAO, SucursalesDao sucursalDAO,
			BitacoraServiceImpl bitacora) {
		super();
		this.segmentoDao = segmentoDAO;
		this.sucursalDao = sucursalDAO;
		this.bitacora = bitacora;
	}


	@Override
	public ResponseApi consultaSucursalesSegmento() {
		ResponseApi resultado;

		List<Segmento> segmentos = segmentoDao.findSucursalesSegmento();

		if (null != segmentos && !segmentos.isEmpty()) {
			
			resultado = new ResponseApi(EstatusResponse.OK, segmentos);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen regiones registradas"));
		}

		return resultado;
	}


	@Override
	public ResponseApi asignarSucursalesSegmento(String idSegmento, List<String> idsSucursales) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Segmento segmento = segmentoDao.findById(idSegmento);

		if (segmento != null && segmento.isActivo() && segmento.isAsignado()) {
			
			List<String> sucursalesNuevas = new ArrayList<>();
			
			for (String idSucursal : idsSucursales) {

				if (!segmento.getSucursales().contains(idSucursal)) {
					sucursalesNuevas.add(idSucursal);
				}
			}
			
			// Consulta de Sucursales a asignar
			List<Sucursal> sucursales = sucursalDao.findList(sucursalesNuevas);
			
			if(!sucursales.isEmpty()) {
				
				List<String> idsSucursalesAsignar = sucursales.stream().map(Sucursal::getId).collect(Collectors.toList());

				segmento.getSucursales().addAll(idsSucursalesAsignar);

				// Actualiza sucursales del Segmento
				segmentoDao.updateSucursales(idSegmento, segmento.getSucursales());
				
				sucursalDao.updateSucursalesAsignadas(idsSucursalesAsignar, true);
				String msjUpdate = "Asignación de sucursales " + idsSucursalesAsignar + " al segmento " + segmento.getDescripcion();
				bitacora.registrar(AccionBitacora.EDITAR, msjUpdate);				
				resultado.setDetalles(Arrays.asList(msjUpdate));
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Las sucursales no existen o ya están asignadas"));
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("El segmento no existe ó esta inactivo/desasignado"));
		}

		return resultado;
	}

	@Override
	public ResponseApi eliminarSucursalesSegmento(String idSegmento, String idSucursal) {

		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Segmento segmento = segmentoDao.findById(idSegmento);
		
		if (segmento != null && segmento.isActivo()) {
			
			if(segmento.getSucursales().contains(idSucursal)) {
				
				List<String> sucursales = segmento.getSucursales().stream().filter(x -> !x.equals(idSucursal))
						.collect(Collectors.toList());
				
				// Actualiza sucursales del segmento
				segmentoDao.updateSucursales(idSegmento, sucursales);
				
				sucursalDao.updateSucursalesAsignadas(Arrays.asList(idSucursal), false);
				String msjSucursalEliminada = "Sucursal " + idSucursal + " eliminada del Segmento " + idSegmento;
				bitacora.registrar(AccionBitacora.EDITAR, msjSucursalEliminada);
				LogApp.mensaje(msjSucursalEliminada);
				resultado.setDetalles(Arrays.asList("Sucursal eliminada del segmento correctamente"));
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					 	Arrays.asList("La sucursal no esta asignada al segmento"));
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("El segmento no existe ó esta inactivo"));
		}

		return resultado;
	}

	@Override
	public	ResponseApi consultaTCSegmentoSucursales(String idSegmento, String idDivisa) {
		ResponseApi resultado = null;
		SegmentoSucursales tcSegmentoSucursales = segmentoDao.listTCSegmentoSucursales(idSegmento, idDivisa);

		if (null != tcSegmentoSucursales) {
			SegmentoSucursal segmentoSucursal = new SegmentoSucursal();
			segmentoSucursal.setIdSegmento(tcSegmentoSucursales.getIdSegmento());
			segmentoSucursal.setDescripcion(tcSegmentoSucursales.getDescripcion());
			
			List<SucursalAux> sucursales = new ArrayList<>();
			
			for(com.baz.divisas.api.tef.general.model.Sucursal sucursal: tcSegmentoSucursales.getSucursales()) {
				SucursalAux sucu = new SucursalAux();
				sucu.setIdSucursal(sucursal.getId());
				TC tipoCambio = new TC();
				for (TipoOperacion tipoOp : sucursal.getDivisas().getTiposOperacion()) {					
					if("410".equals(tipoOp.getIdTipoOperacion())){
						tipoCambio.setCompra(tipoOp.getTipoCambioSpot());
					}
					
					if("411".equals(tipoOp.getIdTipoOperacion())){
						tipoCambio.setVenta(tipoOp.getTipoCambioSpot());
					}
				}
				sucu.setTc(tipoCambio);
				sucursales.add(sucu);
			}
			segmentoSucursal.setSucursales(sucursales);
			
			resultado = new ResponseApi(EstatusResponse.OK, segmentoSucursal);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No se encontraron TC de Segmentos - Sucursales."));
		}

		return resultado;
	}


	@Override
	public ResponseApi consultaTCSegmentoSucursalesOlp(String idSegmento) {
		ResponseApi resultado = null;
		SegmentoSucursales tcSegmentoSucursales = segmentoDao.listTCSegmentoSucursalesOlp(idSegmento);

		if (null != tcSegmentoSucursales) {
			SegmentoSucursal segmentoSucursal = new SegmentoSucursal();
			segmentoSucursal.setIdSegmento(tcSegmentoSucursales.getIdSegmento());
			segmentoSucursal.setDescripcion(tcSegmentoSucursales.getDescripcion());
			
			List<SucursalAux> sucursales = new ArrayList<>();
			
			for(com.baz.divisas.api.tef.general.model.Sucursal sucursal: tcSegmentoSucursales.getSucursales()) {
				SucursalAux sucu = new SucursalAux();
				sucu.setIdSucursal(sucursal.getId());
				TC tipoCambio = new TC();
				for (TipoOperacion tipoOp : sucursal.getTcOlp().getTiposOperacion()) {					
					if("410".equals(tipoOp.getIdTipoOperacion())){
						tipoCambio.setCompra(tipoOp.getTipoCambioSpot());
					}
					
					if("411".equals(tipoOp.getIdTipoOperacion())){
						tipoCambio.setVenta(tipoOp.getTipoCambioSpot());
					}
				}
				sucu.setTc(tipoCambio);
				sucursales.add(sucu);
			}
			segmentoSucursal.setSucursales(sucursales);
			
			resultado = new ResponseApi(EstatusResponse.OK, segmentoSucursal);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No se encontraron TC de Segmentos - Sucursales."));
		}

		return resultado;
	}


	@Override
	public ResponseApi consultaSucursalesSegmentoOlp() {
		ResponseApi resultado;

		List<Segmento> segmentos = segmentoDao.findSucursalesSegmentoOlp();

		if (null != segmentos && !segmentos.isEmpty()) {
			
			resultado = new ResponseApi(EstatusResponse.OK, segmentos);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen regiones registradas"));
		}

		return resultado;
	}


	@Override
	public ResponseApi eliminarSucursalesSegmentoOlp(String idSegmento, String idSucursal) {

		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Segmento segmento = segmentoDao.findByIdOlp(idSegmento);
		
		if (segmento != null && segmento.isActivo()) {
			
			if(segmento.getSucursales().contains(idSucursal)) {
				
				List<String> sucursales = segmento.getSucursales().stream().filter(x -> !x.equals(idSucursal))
						.collect(Collectors.toList());
				
				// Actualiza sucursales del segmento
				segmentoDao.updateSucursalesOlp(idSegmento, sucursales);
				
				sucursalDao.updateSucursalesAsignadasOlp(Arrays.asList(idSucursal), false);
				String msjSucursalEliminada = "Sucursal " + idSucursal + " eliminada del Segmento " + idSegmento;
				bitacora.registrar(AccionBitacora.EDITAR, msjSucursalEliminada);
				LogApp.mensaje(msjSucursalEliminada);
				resultado.setDetalles(Arrays.asList("Sucursal eliminada del segmento correctamente"));
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					 	Arrays.asList("La sucursal no esta asignada al segmento"));
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("El segmento no existe ó esta inactivo"));
		}

		return resultado;
	}


	@Override
	public ResponseApi asignarSucursalesSegmentoOlp(String idSegmento, List<String> idsSucursales) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Segmento segmento = segmentoDao.findByIdOlp(idSegmento);

		if (segmento != null && segmento.isActivo() && segmento.isAsignado()) {
			
			List<String> sucursalesNuevas = new ArrayList<>();
			
			for (String idSucursal : idsSucursales) {

				if (!segmento.getSucursales().contains(idSucursal)) {
					sucursalesNuevas.add(idSucursal);
				}
			}
			
			// Consulta de Sucursales a asignar
			List<Sucursal> sucursales = sucursalDao.findListOlp(sucursalesNuevas);
			
			if(!sucursales.isEmpty()) {
				
				List<String> idsSucursalesAsignar = sucursales.stream().map(Sucursal::getId).collect(Collectors.toList());

				segmento.getSucursales().addAll(idsSucursalesAsignar);

				// Actualiza sucursales del Segmento
				segmentoDao.updateSucursalesOlp(idSegmento, segmento.getSucursales());
				
				sucursalDao.updateSucursalesAsignadasOlp(idsSucursalesAsignar, true);
				String msjUpdate = "Asignación de sucursales " + idsSucursalesAsignar + " al segmento " + segmento.getDescripcion();
				bitacora.registrar(AccionBitacora.EDITAR, msjUpdate);				
				resultado.setDetalles(Arrays.asList(msjUpdate));
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Las sucursales no existen o ya están asignadas"));
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("El segmento no existe ó esta inactivo/desasignado"));
		}

		return resultado;
	}
}
