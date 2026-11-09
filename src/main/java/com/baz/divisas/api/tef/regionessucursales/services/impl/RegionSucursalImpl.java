package com.baz.divisas.api.tef.regionessucursales.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.RegionSegmentosSucursales;
import com.baz.divisas.api.tef.general.model.RegionSucursales;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.general.model.SegmentoSucursal;
import com.baz.divisas.api.tef.general.model.SucursalAux;
import com.baz.divisas.api.tef.general.model.TC;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regiones.model.TipoOperacion;
import com.baz.divisas.api.tef.regionessucursales.dto.RegionSucursal;
import com.baz.divisas.api.tef.regionessucursales.services.IRegionSucursalService;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * RegionSucursalImpl.
 */
@Service
public class RegionSucursalImpl implements IRegionSucursalService {

	/** The region dao. */
	private RegionesDao regionDao;
	
	/** The sucursal dao. */
	private SucursalesDao sucursalDao;	
	
	/** The bitacora. */
	private BitacoraServiceImpl bitacora;
	
	/** The segmentos dao. */
	private SegmentosDao segmentosDao;
	
	/**
	 * Constructor de la clase.
	 *
	 * @param regionDAO - RegionesDao
	 * @param sucursalDao - SucursalDao
	 * @param bitacora - BitacoraServiceImpl
	 * @param segmentosDao the segmentos dao
	 */
	@Autowired
	public RegionSucursalImpl(RegionesDao regionDAO, SucursalesDao sucursalDao, BitacoraServiceImpl bitacora,SegmentosDao segmentosDao) {
		super();
		this.regionDao = regionDAO;
		this.sucursalDao = sucursalDao;
		this.bitacora = bitacora;
		this.segmentosDao = segmentosDao;
	}

	/**
	 * Consulta sucursales regiones.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaSucursalesRegiones() {
		
		ResponseApi resultado;
		
		List<Region> regiones = regionDao.findAllAndExcludeFields(Arrays.asList("segmentos", "divisas"));
		
		if (regiones != null && !regiones.isEmpty()) {
						
			resultado = new ResponseApi(EstatusResponse.OK, regiones);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen regiones registradas"));
		}

		return resultado;
	}

	/**
	 * Asignar sucursales region.
	 *
	 * @param idRegion the id region
	 * @param idsSucursales the ids sucursales
	 * @return the response api
	 */
	@Override
	public ResponseApi asignarSucursalesRegion(String idRegion, List<String> idsSucursales) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Region region = regionDao.findById(idRegion);
		
		if (region != null && region.isActivo()) {
			
			List<String> sucursalesNuevas = new ArrayList<>();
			
			for (String idSucursal : idsSucursales) {

				if (!region.getSucursales().contains(idSucursal)) {
					sucursalesNuevas.add(idSucursal);
				}
			}
			
			// Consulta de Sucursales a asignar
			List<Sucursal> sucursales = sucursalDao.findList(sucursalesNuevas);

			if(!sucursales.isEmpty()) {
				
				List<String> idsSucursalesAsignar = sucursales.stream().map(Sucursal::getId).collect(Collectors.toList());
				
				region.getSucursales().addAll(idsSucursalesAsignar);
				
				// Actualiza sucursales de la Region
				regionDao.updateSucursales(idRegion, region.getSucursales());
				
				sucursalDao.updateSucursalesAsignadas(idsSucursalesAsignar, true);
				String msjUpdate = "Asignación de sucursales " + idsSucursalesAsignar + " a la región " + region.getDescripcion();
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
						 	Arrays.asList("La region no existe ó esta inactiva"));
		}

		return resultado;
	}

	/**
	 * Eliminar sucursal region.
	 *
	 * @param idRegion the id region
	 * @param idSucursal the id sucursal
	 * @return the response api
	 */
	@Override
	public ResponseApi eliminarSucursalRegion(String idRegion, String idSucursal) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Region region = regionDao.findById(idRegion);
		
		if (region != null && region.isActivo()) {
			
			if(region.getSucursales().contains(idSucursal)) {
				
				List<String> sucursales = region.getSucursales().stream().filter(x -> !x.equals(idSucursal))
																.collect(Collectors.toList());
				// Actualiza sucursales de la region
				regionDao.updateSucursales(idRegion, sucursales);
				
				sucursalDao.updateSucursalesAsignadas(Arrays.asList(idSucursal), false);
				String msjSucursalEliminada = "Sucursal " + idSucursal + " eliminada de la Region " + idRegion;
				bitacora.registrar(AccionBitacora.EDITAR, msjSucursalEliminada);
				LogApp.mensaje(msjSucursalEliminada);
				resultado.setDetalles(Arrays.asList("Sucursal eliminada de la región correctamente"));
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					 	Arrays.asList("La sucursal no esta asignada a la región"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						 	Arrays.asList("La región no existe ó esta inactiva"));
		}

		return resultado;
	}

	/**
	 * Consulta regiones segmentos sucursales.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaRegionesSegmentosSucursales() {
		ResponseApi resultado = null;
		List<RegionSegmentosSucursales> listRegionesSegmentosSucursal = regionDao.listRegionesSegmentosSucursal();
		List<RegionSegmentosSucursales> listRegionesSegmentos = regionDao.listRegionesSegmentos();
		List<Segmento> segmentos = segmentosDao.listSegmentosSucursal();
		
		if (null != listRegionesSegmentosSucursal && !listRegionesSegmentosSucursal.isEmpty()) {
			
			if(null != listRegionesSegmentos) {

				for(RegionSegmentosSucursales regionSegmento : listRegionesSegmentosSucursal ) {
					int x = 0;
					for(RegionSegmentosSucursales regionSucursal : listRegionesSegmentos) {


						if(regionSucursal.getIdRegion().equals(regionSegmento.getIdRegion())) {
							regionSucursal.setSucursales(regionSegmento.getSucursales());
							x+=1;
						}					
					}
					if(x==0) {
						listRegionesSegmentos.add(regionSegmento);
					}
				}

			}
			for(RegionSegmentosSucursales region : listRegionesSegmentos) {
				
				Set<SegmentoSucursal> segmentosSucu = new HashSet<>();
				
				for (ObjectId segmento : region.getSegmentosRegion()) {
					SegmentoSucursal segmSucursal = new SegmentoSucursal();
					for(Segmento seg :segmentos) {
						if(segmento.toHexString().equals(seg.getId())) {
							segmSucursal = new SegmentoSucursal(seg.getId(),seg.getDescripcion(),seg.getSucursales());
							segmentosSucu.add(segmSucursal);
						}
					}					
				}
				
				if(segmentosSucu.size() > 0) {
					Set<SegmentoSucursal> segmentoSuc = new HashSet<>(segmentosSucu);
					region.setSegmentos(segmentoSuc.stream().distinct().collect(Collectors.toList()));
				}				
			}
			
			resultado = new ResponseApi(EstatusResponse.OK, listRegionesSegmentos);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No se encontro el catálgo de Regiones - Segmentos - Sucursales."));
		}

		return resultado;
	}
	
	/**
	 * Consulta TC regiones sucursales.
	 *
	 * @param idRegion the id region
	 * @param idDivisa the id divisa
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaTCRegionesSucursales(String idRegion,String idDivisa) {
		ResponseApi resultado = null;
		RegionSucursales tcRegionesSucursales = regionDao.listTCRegionesSucursales(idRegion, idDivisa);

		if (null != tcRegionesSucursales) {
			RegionSucursal regionSucursal = new RegionSucursal();
			regionSucursal.setIdRegion(tcRegionesSucursales.getIdRegion());
			regionSucursal.setDescripcion(tcRegionesSucursales.getDescripcion());
			
			List<SucursalAux> sucursales = new ArrayList<>();
			
			for(com.baz.divisas.api.tef.general.model.Sucursal sucursal: tcRegionesSucursales.getSucursales()) {
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
			regionSucursal.setSucursales(sucursales);
			
			resultado = new ResponseApi(EstatusResponse.OK, regionSucursal);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No se encontraron TC de Regiones - Sucursales."));
		}

		return resultado;
	}

	@Override
	public ResponseApi consultaRegionesSegmentosSucursalesOlp() {
		ResponseApi resultado = null;
		List<RegionSegmentosSucursales> listRegionesSegmentosSucursal = regionDao.listRegionesSegmentosSucursalOlp();
		List<RegionSegmentosSucursales> listRegionesSegmentos = regionDao.listRegionesSegmentosOlp();
		List<Segmento> segmentos = segmentosDao.listSegmentosSucursalOlp();
		
		if ((null != listRegionesSegmentosSucursal && !listRegionesSegmentosSucursal.isEmpty())
				|| null != listRegionesSegmentos && !listRegionesSegmentos.isEmpty()) {
			
			if(null != listRegionesSegmentos) {

				for(RegionSegmentosSucursales regionSegmento : listRegionesSegmentosSucursal ) {
					int x = 0;
					for(RegionSegmentosSucursales regionSucursal : listRegionesSegmentos) {


						if(regionSucursal.getIdRegion().equals(regionSegmento.getIdRegion())) {
							regionSucursal.setSucursales(regionSegmento.getSucursales());
							x+=1;
						}					
					}
					if(x==0) {
						listRegionesSegmentos.add(regionSegmento);
					}
				}

			}
			for(RegionSegmentosSucursales region : listRegionesSegmentos) {
				
				Set<SegmentoSucursal> segmentosSucu = new HashSet<>();
				
				for (ObjectId segmento : region.getSegmentosRegion()) {
					SegmentoSucursal segmSucursal = new SegmentoSucursal();
					for(Segmento seg :segmentos) {
						if(segmento.toHexString().equals(seg.getId())) {
							segmSucursal = new SegmentoSucursal(seg.getId(),seg.getDescripcion(),seg.getSucursales());
							segmentosSucu.add(segmSucursal);
						}
					}					
				}
				
				if(segmentosSucu.size() > 0) {
					Set<SegmentoSucursal> segmentoSuc = new HashSet<>(segmentosSucu);
					region.setSegmentos(segmentoSuc.stream().distinct().collect(Collectors.toList()));
				}				
			}
			
			resultado = new ResponseApi(EstatusResponse.OK, listRegionesSegmentos);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No se encontro el catálgo de Regiones - Segmentos - Sucursales."));
		}

		return resultado;
	}

	@Override
	public ResponseApi consultaTCRegionesSucursalesOlp(String idRegion) {
		ResponseApi resultado = null;
		RegionSucursales tcRegionesSucursales = regionDao.listTCRegionesSucursalesOlp(idRegion);

		if (null != tcRegionesSucursales) {
			RegionSucursal regionSucursal = new RegionSucursal();
			regionSucursal.setIdRegion(tcRegionesSucursales.getIdRegion());
			regionSucursal.setDescripcion(tcRegionesSucursales.getDescripcion());
			
			List<SucursalAux> sucursales = new ArrayList<>();
			
			for(com.baz.divisas.api.tef.general.model.Sucursal sucursal: tcRegionesSucursales.getSucursales()) {
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
			regionSucursal.setSucursales(sucursales);
			
			resultado = new ResponseApi(EstatusResponse.OK, regionSucursal);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No se encontraron TC de Regiones - Sucursales."));
		}

		return resultado;
	}

	@Override
	public ResponseApi consultaSucursalesRegionesOlp() {
		ResponseApi resultado;
		
		List<Region> regiones = regionDao.findAllAndExcludeFieldsOlp(Arrays.asList("segmentos", "divisas"));
		
		if (regiones != null && !regiones.isEmpty()) {
						
			resultado = new ResponseApi(EstatusResponse.OK, regiones);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen regiones registradas"));
		}

		return resultado;
	}

	@Override
	public ResponseApi asignarSucursalesRegionOlp(String idRegion, List<String> idsSucursales) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Region region = regionDao.findByIdOlp(idRegion);
		
		if (region != null && region.isActivo()) {
			
			List<String> sucursalesNuevas = new ArrayList<>();
			
			for (String idSucursal : idsSucursales) {

				if (!region.getSucursales().contains(idSucursal)) {
					sucursalesNuevas.add(idSucursal);
				}
			}
			
			// Consulta de Sucursales a asignar
			List<Sucursal> sucursales = sucursalDao.findListOlp(sucursalesNuevas);

			if(!sucursales.isEmpty()) {
				
				List<String> idsSucursalesAsignar = sucursales.stream().map(Sucursal::getId).collect(Collectors.toList());
				
				region.getSucursales().addAll(idsSucursalesAsignar);
				
				// Actualiza sucursales de la Region
				regionDao.updateSucursalesOlp(idRegion, region.getSucursales());
				
				sucursalDao.updateSucursalesAsignadasOlp(idsSucursalesAsignar, true);
				String msjUpdate = "Asignación de sucursales " + idsSucursalesAsignar + " a la región " + region.getDescripcion();
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
						 	Arrays.asList("La región no existe ó esta inactiva"));
		}

		return resultado;
	}

	@Override
	public ResponseApi eliminarSucursalRegionOlp(String idRegion, String idSucursal) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Region region = regionDao.findByIdOlp(idRegion);
		
		if (region != null && region.isActivo()) {
			
			if(region.getSucursales().contains(idSucursal)) {
				
				List<String> sucursales = region.getSucursales().stream().filter(x -> !x.equals(idSucursal))
																.collect(Collectors.toList());
				// Actualiza sucursales de la region
				regionDao.updateSucursalesOlp(idRegion, sucursales);
				
				sucursalDao.updateSucursalesAsignadasOlp(Arrays.asList(idSucursal), false);
				String msjSucursalEliminada = "Sucursal " + idSucursal + " eliminada de la Región " + idRegion;
				bitacora.registrar(AccionBitacora.EDITAR, msjSucursalEliminada);
				LogApp.mensaje(msjSucursalEliminada);
				resultado.setDetalles(Arrays.asList("Sucursal eliminada de la región correctamente"));
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					 	Arrays.asList("La sucursal no esta asignada a la región"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						 	Arrays.asList("La región no existe ó esta inactiva"));
		}

		return resultado;
	}
}
