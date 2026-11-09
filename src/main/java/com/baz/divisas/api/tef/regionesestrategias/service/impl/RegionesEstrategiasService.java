package com.baz.divisas.api.tef.regionesestrategias.service.impl;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.regionesestrategias.dao.RegionesEstrategiasDao;
import com.baz.divisas.api.tef.regionesestrategias.dto.RequestEstrategia;
import com.baz.divisas.api.tef.regionesestrategias.dto.ResponseEstrategia;
import com.baz.divisas.api.tef.regionesestrategias.service.IRegionesEstrategias;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase RegionesEstrategiasService
 * 
 */
@Service
public class RegionesEstrategiasService implements IRegionesEstrategias{

	private RegionesEstrategiasDao regionesEstrategiasDao;
	private CatalogosDao catalogosDao;
	private BitacoraServiceImpl bitacora;

	/**
	 * Cosntructor de la clase
	 *
	 * @param regionesEstrategiasDao - RegionesEstrategiasDao
	 * @param catalogosDao - CatalogosDao
	 * @param bitacora - BitacoraServiceImpl
	 */
	@Autowired
	public RegionesEstrategiasService(RegionesEstrategiasDao regionesEstrategiasDao, CatalogosDao catalogosDao,
			BitacoraServiceImpl bitacora) {
		super();
		this.regionesEstrategiasDao = regionesEstrategiasDao;
		this.catalogosDao = catalogosDao;
		this.bitacora = bitacora;
	}

	@Override
	public ResponseApi consultaEstrategiasRegiones() {

		ResponseApi resultado;

		List<ResponseEstrategia> estrategiasRegiones = regionesEstrategiasDao.findAll();

		if(estrategiasRegiones != null && !estrategiasRegiones.isEmpty()) {
			resultado =  new ResponseApi(EstatusResponse.OK, estrategiasRegiones);
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen estrategias en las regiones"));
		}

		return resultado;
	}

	@Override
	public ResponseApi asignarEstrategiaRegion(RequestEstrategia request) {

		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);

		List<ResponseEstrategia> estrategiasRegion = regionesEstrategiasDao.findByTipo(request);

		if (estrategiasRegion != null && !estrategiasRegion.isEmpty()) {

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
					"Registro de estrategia en la Region: " + request.getIdRegion(), null,
					request);

			regionesEstrategiasDao.updateEstrategiaRegion(request, idBitacora);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("La region no existe, esta inactiva o no contiene la estrategia"));
		}

		return resultado;
	}


	@Override
	public ResponseApi actualizarEstatusEstrategiaRegion(RequestEstrategia request) {
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		List<ResponseEstrategia> estrategiasRegion = regionesEstrategiasDao.findByTipo(request);

		if (estrategiasRegion != null && !estrategiasRegion.isEmpty()) {

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
					"Actualizacion de estatus de la estrategia en la Region: " + request.getIdRegion(), request.isActivoEstrategia());

			regionesEstrategiasDao.updateEstatusEstrategiaRegion(request, idBitacora);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
				 	Arrays.asList("La region no existe, esta inactiva o no contiene la estrategia"));
		}

		return resultado;
	}

}
