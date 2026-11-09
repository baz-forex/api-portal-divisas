/**
 * 
 */
package com.baz.divisas.api.tef.regionessegmento.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regionessegmento.dto.ResRegionSegmento;
import com.baz.divisas.api.tef.regionessegmento.services.IRegionSegmentoService;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.ObjectMapperUtils;

/**
 * Clase RegionSegmentoImpl
 *
 */
@Service
public class RegionSegmentoImpl implements IRegionSegmentoService {

	private SegmentosDao segmentoDAO;
	private RegionesDao regionDAO;
	private BitacoraServiceImpl bitacora;

	/**
	 * Constructor de la clase
	 *
	 * @param segmentoDAO - SegmentosDao
	 * @param regionDAO - RegionesDao
	 * @param bitacora - BitacoraServiceImpl
	 */
	@Autowired
	public RegionSegmentoImpl(SegmentosDao segmentoDAO, RegionesDao regionDAO, BitacoraServiceImpl bitacora) {
		super();
		this.segmentoDAO = segmentoDAO;
		this.regionDAO = regionDAO;
		this.bitacora = bitacora;
	}

	@Override
	public ResponseApi consultaSegmentosRegiones() {
		
		ResponseApi resultado;

		List<Region> regiones = regionDAO.findAllAndExcludeFields(Arrays.asList("sucursales", "divisas"));

		if (null != regiones && !regiones.isEmpty()) {

			List<ResRegionSegmento> respRegionSegmento = ObjectMapperUtils.mapAll(regiones, ResRegionSegmento.class);

			resultado = new ResponseApi(EstatusResponse.OK, respRegionSegmento);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen regiones registradas"));
		}

		return resultado;
	}

	@Override
	public ResponseApi asignarSegmentosRegion(String idRegion, List<String> idsSegmentos) {

		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Region region = regionDAO.findById(idRegion);
		
		if (region != null && region.isActivo()) {

			List<String> segmentosNuevos = new ArrayList<>();

			for (String idSegmento : idsSegmentos) {
				
				if (!region.getSegmentos().contains(new ObjectId(idSegmento))) {
					segmentosNuevos.add(idSegmento);
				}
			}

			// Consulta de Segmentos a asignar
			List<Segmento> segmentos = segmentoDAO.findList(segmentosNuevos);
			
			if(!segmentos.isEmpty() ) {
				
				List<ObjectId> idsSegmentosAsignar = segmentos.stream().map(x -> new ObjectId(x.getId()))
																.collect(Collectors.toList());

				region.getSegmentos().addAll(idsSegmentosAsignar);
				
				// Actualiza segmentos de la Region
				regionDAO.updateSegmentos(region.getId(), region.getSegmentos());

				List<String> idsSegmentosAsigandos = segmentos.stream().map(Segmento::getId)
														      .collect(Collectors.toList());
				
				// Actualiza estatus de los Segmentos asignados a la Region
				segmentoDAO.updateSegmentosAsignados(idsSegmentosAsigandos, true);

				String msjUpdate = "Asignación de segmentos " + idsSegmentosAsigandos + " a la región " + region.getDescripcion();
				bitacora.registrar(AccionBitacora.EDITAR, msjUpdate);				
				resultado.setDetalles(Arrays.asList(msjUpdate));
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
								Arrays.asList("Los segmentos no existen o ya están asignados"));
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						 	Arrays.asList("La región no existe ó esta inactiva"));
		}

		return resultado;
	}

	@Override
	public ResponseApi eliminaSegmentoRegion(String idRegion, String idSegmento) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Region region = regionDAO.findById(idRegion);
		
		if(region != null && region.isActivo()) {
			
			if(region.getSegmentos().contains(new ObjectId(idSegmento))) {
				
				List<ObjectId> segmentos = region.getSegmentos().stream()
						.filter(x -> !x.equals(new ObjectId(idSegmento))).collect(Collectors.toList());
				
				// Actualiza segmentos de la Region
				regionDAO.updateSegmentos(idRegion, segmentos);
				
				// Actualiza estatus del Segmento eliminado de la Region
				segmentoDAO.updateSegmentosAsignados(Arrays.asList(idSegmento), false);
				
				String msjSegmentoEliminado = "Segmento " + idSegmento + " eliminado de la Región " + idRegion;
				
				// Asigna sucursales del Segmento a la Region
				Segmento segmento = segmentoDAO.findById(idSegmento);
				
				if(!segmento.getSucursales().isEmpty()) {

					List<String> sucursalesRegion = region.getSucursales();
					sucursalesRegion.addAll(segmento.getSucursales());

					// Actualiza sucursales de la region
					regionDAO.updateSucursales(idRegion, sucursalesRegion);

					// Actualiza sucursales del segmento
					segmentoDAO.updateSucursales(idSegmento, Arrays.asList());

					resultado.setDetalles(Arrays.asList(msjSegmentoEliminado 
							+ ", Sucursales asignadas a la región: " + segmento.getSucursales()));
				} 
				else {
					resultado.setDetalles(Arrays.asList(msjSegmentoEliminado));
				}

				bitacora.registrar(AccionBitacora.EDITAR, msjSegmentoEliminado);
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					 	Arrays.asList("El segmento no esta asignado a la region"));
			}
		}
		else {			
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("La región no existe ó esta inactiva"));
		}

		return resultado;
	}

	@Override
	public ResponseApi consultaSegmentosRegionesOlp() {
		ResponseApi resultado;

		List<Region> regiones = regionDAO.findAllAndExcludeFieldsOlp(Arrays.asList("sucursales", "divisas"));

		if (null != regiones && !regiones.isEmpty()) {

			List<ResRegionSegmento> respRegionSegmento = ObjectMapperUtils.mapAll(regiones, ResRegionSegmento.class);

			resultado = new ResponseApi(EstatusResponse.OK, respRegionSegmento);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen regiones registradas"));
		}

		return resultado;
	}

	@Override
	public ResponseApi asignarSegmentosRegionOlp(String idRegion, List<String> idsSegmentos) {
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Region region = regionDAO.findByIdOlp(idRegion);
		
		if (region != null && region.isActivo()) {

			List<String> segmentosNuevos = new ArrayList<>();

			for (String idSegmento : idsSegmentos) {
				
				if (!region.getSegmentos().contains(new ObjectId(idSegmento))) {
					segmentosNuevos.add(idSegmento);
				}
			}

			// Consulta de Segmentos a asignar
			List<Segmento> segmentos = segmentoDAO.findListOlp(segmentosNuevos);
			
			if(!segmentos.isEmpty() ) {
				
				List<ObjectId> idsSegmentosAsignar = segmentos.stream().map(x -> new ObjectId(x.getId()))
																.collect(Collectors.toList());

				region.getSegmentos().addAll(idsSegmentosAsignar);
				
				// Actualiza segmentos de la Region
				regionDAO.updateSegmentosOlp(region.getId(), region.getSegmentos());

				List<String> idsSegmentosAsigandos = segmentos.stream().map(Segmento::getId)
														      .collect(Collectors.toList());
				
				// Actualiza estatus de los Segmentos asignados a la Region
				segmentoDAO.updateSegmentosAsignadosOlp(idsSegmentosAsigandos, true);

				String msjUpdate = "Asignación de segmentos " + idsSegmentosAsigandos + " a la región " + region.getDescripcion();
				bitacora.registrar(AccionBitacora.EDITAR, msjUpdate);				
				resultado.setDetalles(Arrays.asList(msjUpdate));
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
								Arrays.asList("Los segmentos no existen o ya están asignados"));
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						 	Arrays.asList("La región no existe ó esta inactiva"));
		}

		return resultado;
	}

	@Override
	public ResponseApi eliminaSegmentoRegionOlp(String idRegion, String idSegmento) {
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		Region region = regionDAO.findByIdOlp(idRegion);
		
		if(region != null && region.isActivo()) {
			
			if(region.getSegmentos().contains(new ObjectId(idSegmento))) {
				
				List<ObjectId> segmentos = region.getSegmentos().stream()
						.filter(x -> !x.equals(new ObjectId(idSegmento))).collect(Collectors.toList());
				
				// Actualiza segmentos de la Region
				regionDAO.updateSegmentosOlp(idRegion, segmentos);
				
				// Actualiza estatus del Segmento eliminado de la Region
				segmentoDAO.updateSegmentosAsignadosOlp(Arrays.asList(idSegmento), false);
				
				String msjSegmentoEliminado = "Segmento " + idSegmento + " eliminado de la Región " + idRegion;
				
				// Asigna sucursales del Segmento a la Region
				Segmento segmento = segmentoDAO.findByIdOlp(idSegmento);
				
				if(!segmento.getSucursales().isEmpty()) {

					List<String> sucursalesRegion = region.getSucursales();
					sucursalesRegion.addAll(segmento.getSucursales());

					// Actualiza sucursales de la region
					regionDAO.updateSucursalesOlp(idRegion, sucursalesRegion);

					// Actualiza sucursales del segmento
					segmentoDAO.updateSucursalesOlp(idSegmento, Arrays.asList());

					resultado.setDetalles(Arrays.asList(msjSegmentoEliminado 
							+ ", Sucursales asignadas a la región: " + segmento.getSucursales()));
				} 
				else {
					resultado.setDetalles(Arrays.asList(msjSegmentoEliminado));
				}

				bitacora.registrar(AccionBitacora.EDITAR, msjSegmentoEliminado);
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					 	Arrays.asList("El segmento no esta asignado a la region"));
			}
		}
		else {			
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("La región no existe ó esta inactiva"));
		}

		return resultado;
	}

}
