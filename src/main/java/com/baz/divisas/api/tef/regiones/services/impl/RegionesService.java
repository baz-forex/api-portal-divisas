package com.baz.divisas.api.tef.regiones.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.regiones.model.Estrategia;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regiones.model.TipoOperacion;
import com.baz.divisas.api.tef.regiones.services.IRegionesService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * RegionesService.
 *
 * @author 164945 ogatica
 */
@Service
public class RegionesService implements IRegionesService {
	
	/** The regiones dao. */
	private RegionesDao regionesDao;
	
	/** The Constant REGION_NOEXISTE. */
	private static final String REGION_NOEXISTE = "No existe la region con id: ";

	/**
	 * Constructor de la clase.
	 *
	 * @param regionesDao the regiones DAO
	 */
	@Autowired
	public RegionesService(RegionesDao regionesDao) {
		super();
		this.regionesDao = regionesDao;
	}

	/**
	 * Metodo de consulta de regiones.
	 *
	 * @return Regiones
	 */
	@Override
	public ResponseApi consultaRegiones() {
		
		ResponseApi resultado;
		
		List<Region> regiones = regionesDao.findAllAndExcludeFields(
				Arrays.asList("segmentos", "sucursales", "divisas.tiposOperacion"));

		if (null != regiones && !regiones.isEmpty()) {
			
			resultado = new ResponseApi(EstatusResponse.OK, regiones);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen regiones registradas."));
		}

		return resultado;

	}

	/**
	 * Metodo de creacion de Region.
	 *
	 * @param descripcionRegion - Descripcion de la Region
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi creaRegion(String descripcionRegion) {
		ResponseApi resultado;

		Region region = regionesDao.findByDescripcion(descripcionRegion);

		if (region == null) {

			Limites limites = new Limites(false, new MontosLimite(0.0, 0.0,0.0, 0.0), new MontosLimite(0.0, 0.0,0.0, 0.0));
			Estrategia estrategia = new Estrategia(false, 0.0, DateUtils.getDateFormato(Constant.FORMATOFECHACOMPLETAEXT), "");

			TipoOperacion t410 = new TipoOperacion("410", "COMPRA EFECTIVO", 0.0,0.0, estrategia, limites,true);
			TipoOperacion t411 = new TipoOperacion("411", "VENTA EFECTIVO", 0.0,0.0, estrategia, limites,true);
			TipoOperacion t412 = new TipoOperacion("412", "COMPRA ABONO CUENTA", 0.0,0.0, estrategia, limites,true);
			TipoOperacion t413 = new TipoOperacion("413", "VENTA CARGO CUENTA", 0.0,0.0, estrategia, limites,true);

			Divisa usd = new Divisa("USD", "DÓLAR AMERICANO", Arrays.asList(t410, t411, t412, t413),true);
			Divisa eur = new Divisa("EUR", "EURO", Arrays.asList(t410, t411, t412, t413),true);
			Divisa cad = new Divisa("CAD", "DÓLAR CANADIENSE", Arrays.asList(t410, t411, t412, t413),true);
			Divisa gbp = new Divisa("GBP", "LIBRA ESTERLINA", Arrays.asList(t410, t411, t412, t413),true);

			region = new Region(descripcionRegion, true, Arrays.asList(usd, eur, cad , gbp));

			InsertOneResult resultInsert = regionesDao.insert(region);

			if (resultInsert.getInsertedId() != null) {
				resultado = new ResponseApi(EstatusResponse.OK);
			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible registrar la region."));
			}
		}
		else {
			LogApp.mensaje("Ya existe una region con la descripcion: " + descripcionRegion);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe una region con esa descripcion"));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza la descripcion de la region.
	 *
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaDescripcionRegion(String id, String descripcion) {
		ResponseApi resultado;

		Region region = regionesDao.findById(id);

		if (region != null) {

			UpdateResult resultUpdate = regionesDao.updateDescripcion(id, descripcion);

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible actualizar la descripcion de la region"));
			}

		} 
		else {
			LogApp.mensaje(REGION_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(REGION_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza el estatus de la region.
	 *
	 * @param id - Id
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaEstatusRegion(String id) {
		ResponseApi resultado;
		
		Region region = regionesDao.findById(id);

		if (region != null) {

			UpdateResult resultUpdate = regionesDao.updateEstatus(id, !region.isActivo());

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);
				
			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible cambiar el estatus de la region"));
			}

		} 
		else {
			LogApp.mensaje(REGION_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(REGION_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Metodo que elimina una region.
	 *
	 * @param id - Id
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi eliminaRegion(String id) {

		ResponseApi resultado;

		Region region = regionesDao.findById(id);

		if (region != null) {

			regionesDao.delete(id);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje(REGION_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(REGION_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Metodo de actualizar estatus de la Divisa en la Region.
	 *
	 * @param idRegion - Id de la Region
	 * @param idDivisa - Id de la Divisa
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaEstatusDivisaRegion(String idRegion, String idDivisa) {
		ResponseApi resultado;

		Region region = regionesDao.findById(idRegion);

		if (region != null) {
			
			Optional<Divisa> divisa = region.getDivisas().stream().filter(x -> x.getId().equals(idDivisa)).findFirst();
			
			if(divisa.isPresent()) {
				
				boolean update = regionesDao.updateEstatusDivisaRegion(idRegion, idDivisa, !divisa.get().isActivo());
				
				if (update) {

					resultado = new ResponseApi(EstatusResponse.OK);

				} 
				else {
					LogApp.mensaje("No fue posible cambiar el estatus de la divisa " + idDivisa + " en la region " + idRegion);
					resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
							Arrays.asList("No fue posible cambiar el estatus de la divisa en la region"));
				}
			}
			else {
				LogApp.mensaje("No existe la divisa " + idDivisa + " en la region " + idRegion);
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No existe la divisa en la region"));
			}
		} 
		else {
			LogApp.mensaje(REGION_NOEXISTE + idRegion);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(REGION_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Consulta regiones olp.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaRegionesOlp() {
		ResponseApi resultado;

		List<Region> regiones = regionesDao.findAllAndExcludeFieldsOlp(
				Arrays.asList("segmentos", "sucursales", "divisas.tiposOperacion"));

		if (null != regiones && !regiones.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, regiones);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen regiones registradas."));
		}

		return resultado;
	}

	/**
	 * Crea region olp.
	 *
	 * @param descripcion the descripcion
	 * @return the response api
	 */
	@Override
	public ResponseApi creaRegionOlp(String descripcion) {
		ResponseApi resultado;

		Region region = regionesDao.findByDescripcionOlp(descripcion);

		if (region == null) {

			Limites limites = new Limites(false, new MontosLimite(0.0, 0.0,0.0, 0.0), new MontosLimite(0.0, 0.0,0.0, 0.0));

			TipoOperacion t410 = new TipoOperacion("410", "COMPRA EFECTIVO", 0.0);
			TipoOperacion t411 = new TipoOperacion("411", "VENTA EFECTIVO", 0.0);
			
			t410.setLimites(limites);
			t410.setActivo(true);
			t411.setLimites(limites);
			t411.setActivo(true);

			Divisa olp = new Divisa("OLP", "ONZA DE PLATA", Arrays.asList(t410, t411),true);			
			
			region = new Region(descripcion, true, Arrays.asList(olp));

			InsertOneResult resultInsert = regionesDao.insertOlp(region);

			if (resultInsert.getInsertedId() != null) {
				resultado = new ResponseApi(EstatusResponse.OK);
			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible registrar la region."));
			}
		}
		else {
			LogApp.mensaje("Ya existe una region de OLP con la descripcion: " + descripcion);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe una region de OLP con esa descripcion"));
		}

		return resultado;
	}

	/**
	 * Actualiza descripcion region olp.
	 *
	 * @param id the id
	 * @param descripcion the descripcion
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaDescripcionRegionOlp(String id, String descripcion) {
		ResponseApi resultado;

		Region region = regionesDao.findByIdOlp(id);

		if (region != null) {

			UpdateResult resultUpdate = regionesDao.updateDescripcionOlp(id, descripcion);

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible actualizar la descripcion de la region"));
			}

		} 
		else {
			LogApp.mensaje(REGION_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(REGION_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Actualiza estatus region olp.
	 *
	 * @param id the id
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaEstatusRegionOlp(String id) {
		ResponseApi resultado;
		
		Region region = regionesDao.findByIdOlp(id);

		if (region != null) {

			UpdateResult resultUpdate = regionesDao.updateEstatusOlp(id, !region.isActivo());

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);
				
			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible cambiar el estatus de la region"));
			}

		} 
		else {
			LogApp.mensaje(REGION_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(REGION_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Elimina region olp.
	 *
	 * @param id the id
	 * @return the response api
	 */
	@Override
	public ResponseApi eliminaRegionOlp(String id) {

		ResponseApi resultado;

		Region region = regionesDao.findByIdOlp(id);

		if (region != null) {

			regionesDao.deleteOlp(id);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje(REGION_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(REGION_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Actualiza estatus divisa region olp.
	 *
	 * @param idRegion the id region
	 * @param idDivisa the id divisa
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaEstatusDivisaRegionOlp(String idRegion, String idDivisa) {
		ResponseApi resultado;

		Region region = regionesDao.findByIdOlp(idRegion);

		if (region != null) {
			
			Optional<Divisa> divisa = region.getDivisas().stream().filter(x -> x.getId().equals(idDivisa)).findFirst();
			
			if(divisa.isPresent()) {
				
				boolean update = regionesDao.updateEstatusDivisaRegionOlp(idRegion, idDivisa, !divisa.get().isActivo());
				
				if (update) {

					resultado = new ResponseApi(EstatusResponse.OK);

				} 
				else {
					LogApp.mensaje("No fue posible cambiar el estatus de la divisa " + idDivisa + " en la region " + idRegion);
					resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
							Arrays.asList("No fue posible cambiar el estatus de la divisa en la region"));
				}
			}
			else {
				LogApp.mensaje("No existe la divisa " + idDivisa + " en la region " + idRegion);
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No existe la divisa en la region"));
			}
		} 
		else {
			LogApp.mensaje(REGION_NOEXISTE + idRegion);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(REGION_NOEXISTE));
		}

		return resultado;
	}

}
