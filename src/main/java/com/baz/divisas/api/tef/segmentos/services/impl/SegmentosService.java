package com.baz.divisas.api.tef.segmentos.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.regiones.model.Estrategia;
import com.baz.divisas.api.tef.regiones.model.TipoOperacion;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.segmentos.services.ISegmentosService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * SegmentosService
 * 
 * @author 164945 ogatica
 */
@Service
public class SegmentosService implements ISegmentosService {

	private SegmentosDao segmentosDao;

	private BitacoraServiceImpl bitacora;

	private static final String SEGMENTO_NOEXISTE = "No existe la region con id: ";


	/**
	 * Constructor de la clase
	 *
	 * @param segmentosDao - Segmentos DAO
	 * @param bitacora - Servicio Bitacora
	 */
	@Autowired
	public SegmentosService(SegmentosDao segmentosDao, BitacoraServiceImpl bitacora) {
		super();
		this.segmentosDao = segmentosDao;
		this.bitacora = bitacora;
	}

	/**
	 * Metodo de consulta de segmentos
	 * 
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi consultaSegmentos() {
		
		ResponseApi resultado;

		List<Segmento> segmentos = segmentosDao.findAll();

		if (segmentos != null && !segmentos.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, segmentos);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen segmentos registrados."));
		}

		return resultado;

	}

	/**
	 * Metodo de creacion de Segmento
	 * 
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi creaSegmento(String descripcionSegmento) {
		
		ResponseApi resultado;

		Segmento segmento = segmentosDao.findByDescripcion(descripcionSegmento);

		if (segmento == null) {

			Limites limites = new Limites(false, new MontosLimite(0.0, 0.0,0.0, 0.0), new MontosLimite(0.0, 0.0,0.0, 0.0));
			Estrategia estrategia = new Estrategia(false, 0.0, DateUtils.getDateFormato(Constant.FORMATOFECHACOMPLETAEXT), "");

			TipoOperacion t410 = new TipoOperacion("410", "COMPRA EFECTIVO", 0.0,0.0, estrategia, limites, true);
			TipoOperacion t411 = new TipoOperacion("411", "VENTA EFECTIVO", 0.0,0.0, estrategia, limites, true);
			TipoOperacion t412 = new TipoOperacion("412", "COMPRA ABONO CUENTA", 0.0,0.0, estrategia, limites, true);
			TipoOperacion t413 = new TipoOperacion("413", "VENTA CARGO CUENTA", 0.0,0.0, estrategia, limites, true);

			Divisa usd = new Divisa("USD", "DÓLAR AMERICANO", Arrays.asList(t410, t411, t412, t413), true);
			Divisa eur = new Divisa("EUR", "EURO", Arrays.asList(t410, t411, t412, t413), true);
			Divisa cad = new Divisa("CAD", "DÓLAR CANADIENSE", Arrays.asList(t410, t411, t412, t413), true);
			Divisa gbp = new Divisa("GBP", "LIBRA ESTERLINA", Arrays.asList(t410, t411, t412, t413),true);
			
			segmento = new Segmento(descripcionSegmento, true, Arrays.asList(usd, eur, cad , gbp));

			InsertOneResult resultInsert = segmentosDao.insert(segmento);

			if (resultInsert.getInsertedId() != null) {

				resultado = new ResponseApi(EstatusResponse.OK);

				bitacora.registrar(AccionBitacora.REGISTRAR, "Registro del segmento: " + descripcionSegmento);

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible registrar el segmento."));
			}

		} 
		else {
			LogApp.mensaje("Ya existe un segmento con la descripcion: " + descripcionSegmento);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un segmento con esa descripcion"));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza la descripcion del segmento.
	 *
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaDescripcionSegmento(String id, String descripcion) {
		ResponseApi resultado;

		Segmento segmento = segmentosDao.findById(id);

		if (segmento != null) {

			UpdateResult resultUpdate = segmentosDao.updateDescripcion(id, descripcion);

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);

				if (resultUpdate.getModifiedCount() > 0) {
					bitacora.registrar(AccionBitacora.EDITAR, 
							"Actualización descripción segmento "+ segmento.getDescripcion() + ": " + descripcion);
				}

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible actualizar la descripcion del segmento"));
			}

		} 
		else {
			LogApp.mensaje(SEGMENTO_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(SEGMENTO_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza el estatus del segmento
	 * 
	 * @param id - Id
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaEstatusSegmento(String id) {
		
		ResponseApi resultado;
		Segmento segmento = segmentosDao.findById(id);

		if (segmento != null) {

			UpdateResult resultUpdate = segmentosDao.updateEstatus(id, !segmento.isActivo());

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);

				if (resultUpdate.getModifiedCount() > 0) {
					bitacora.registrarCambioEstatus(AccionBitacora.EDITAR, 
							"Actualización estatus del segmento: " + segmento.getDescripcion(),
							!segmento.isActivo());
				}

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible cambiar el estatus del segmento"));
			}

		} 
		else {
			LogApp.mensaje(SEGMENTO_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(SEGMENTO_NOEXISTE));
		}

		return resultado;
	}

	/**
	 * Metodo que elimina un segmento
	 * 
	 * @param id - Id
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi eliminaSegmento(String id) {

		ResponseApi resultado;

		Segmento segmento = segmentosDao.findById(id);

		if (segmento != null) {
			
			if (!segmento.isAsignado() && segmento.getSucursales().isEmpty()) {
				
				segmentosDao.delete(id);

				resultado = new ResponseApi(EstatusResponse.OK);

				bitacora.registrar(AccionBitacora.ELIMINAR, "Elimina segmento : " + segmento.getDescripcion());

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("El segmento no puede eliminarse, está asignado a un region o sucursal."));
			}

		} 
		else {
			LogApp.mensaje(SEGMENTO_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(SEGMENTO_NOEXISTE));
		}

		return resultado;
	}

	//OLP
	
	@Override
	public ResponseApi consultaSegmentosOlp() {
		ResponseApi resultado;

		List<Segmento> segmentos = segmentosDao.findAllOlp();

		if (segmentos != null && !segmentos.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, segmentos);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen segmentos registrados."));
		}

		return resultado;
	}

	@Override
	public ResponseApi actualizaEstatusSegmentoOlp(String id) {
		ResponseApi resultado;
		Segmento segmento = segmentosDao.findByIdOlp(id);

		if (segmento != null) {

			UpdateResult resultUpdate = segmentosDao.updateEstatusOlp(id, !segmento.isActivo());

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);

				if (resultUpdate.getModifiedCount() > 0) {
					bitacora.registrarCambioEstatus(AccionBitacora.EDITAR, 
							"Actualización estatus del segmento: " + segmento.getDescripcion(),
							!segmento.isActivo());
				}
				
			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible cambiar el estatus del segmento"));
			}

		} 
		else {
			LogApp.mensaje(SEGMENTO_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(SEGMENTO_NOEXISTE));
		}

		return resultado;
	}

	@Override
	public ResponseApi eliminaSegmentoOlp(String id) {
		ResponseApi resultado;

		Segmento segmento = segmentosDao.findByIdOlp(id);

		if (segmento != null) {
			
			if (!segmento.isAsignado() && segmento.getSucursales().isEmpty()) {
				
				segmentosDao.deleteOlp(id);

				resultado = new ResponseApi(EstatusResponse.OK);

				bitacora.registrar(AccionBitacora.ELIMINAR, "Elimina segmento : " + segmento.getDescripcion());

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("El segmento no puede eliminarse, está asignado a un region o sucursal."));
			}

		} 
		else {
			LogApp.mensaje(SEGMENTO_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(SEGMENTO_NOEXISTE));
		}

		return resultado;
	}

	@Override
	public ResponseApi creaSegmentoOlp(String descripcionSegmento) {
		try {
			ResponseApi resultado;

			Segmento segmento = segmentosDao.findByDescripcionOlp(descripcionSegmento);

			if (segmento == null) {

				Limites limites = new Limites(false, new MontosLimite(0.0, 0.0,0.0, 0.0), new MontosLimite(0.0, 0.0,0.0, 0.0));

				TipoOperacion t410 = new TipoOperacion("410", "COMPRA EFECTIVO", 0.0);
				TipoOperacion t411 = new TipoOperacion("411", "VENTA EFECTIVO", 0.0);

				t410.setLimites(limites);
				t410.setActivo(true);
				t411.setLimites(limites);
				t411.setActivo(true);

				Divisa olp = new Divisa("OLP", "ONZA DE PLATA", Arrays.asList(t410, t411), true);
				segmento = new Segmento(descripcionSegmento, true, Arrays.asList(olp));

				InsertOneResult resultInsert = segmentosDao.insertOlp(segmento);

				if (resultInsert != null && resultInsert.getInsertedId() != null) {

					resultado = new ResponseApi(EstatusResponse.OK);

					bitacora.registrar(AccionBitacora.REGISTRAR, "Registro del segmento: " + descripcionSegmento);

				}
				else {
					resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
							Arrays.asList("No fue posible registrar el segmento OLP: la base de datos no confirmó la inserción."));
				}

			} 
			else {
				LogApp.mensaje("Ya existe un segmento con la descripcion: " + descripcionSegmento);
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Ya existe un segmento con esa descripcion"));
			}

			return resultado;
		}
		catch (Exception e) {
			String detalle = obtenerDetalleError(e);
			LogApp.excepcion(e, "Error al registrar el segmento OLP con descripcion: " + descripcionSegmento);
			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible registrar el segmento OLP: " + detalle));
		}
	}

	/**
	 * Obtiene la causa más específica para informar un error accionable sin
	 * devolver el stack trace al consumidor.
	 */
	private String obtenerDetalleError(Exception exception) {
		Throwable causa = exception;
		while (causa.getCause() != null && causa.getCause() != causa) {
			causa = causa.getCause();
		}

		String mensaje = causa.getMessage();
		return mensaje == null || mensaje.trim().isEmpty()
				? causa.getClass().getSimpleName()
				: mensaje;
	}

	@Override
	public ResponseApi actualizaDescripcionSegmentoOlp(String id, String descripcion) {
		ResponseApi resultado;

		Segmento segmento = segmentosDao.findByIdOlp(id);

		if (segmento != null) {

			UpdateResult resultUpdate = segmentosDao.updateDescripcionOlp(id, descripcion);

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);
				
				if (resultUpdate.getModifiedCount() > 0) {
					bitacora.registrar(AccionBitacora.EDITAR, 
							"Actualización descripción segmento "+ segmento.getDescripcion() + ": " + descripcion);
				}

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible actualizar la descripcion del segmento"));
			}

		} 
		else {
			LogApp.mensaje(SEGMENTO_NOEXISTE + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(SEGMENTO_NOEXISTE));
		}

		return resultado;
	}

}
