package com.baz.divisas.api.digital.reporteria.services;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.digital.reporteria.dao.CotizacionesDao;
import com.baz.divisas.api.digital.reporteria.dto.ReqActualizaEstatus;
import com.baz.divisas.api.digital.reporteria.dto.ReqBusqOperacionesApp;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;

/**
 * Clase ReporteriaServiceImpl
 * 
 * @author itoxtle
 *
 */

@Service("ReporteriaDigitalServiceImpl")
public class ReporteriaServiceImpl {

	private CotizacionesDao cotizacionesDao;

	/**
	 * Constructor de la clase
	 * 
	 * @param cotizacionesDao - Cotizaciones Dao
	 */
	@Autowired
	public ReporteriaServiceImpl(CotizacionesDao cotizacionesDao) {
		super();
		this.cotizacionesDao = cotizacionesDao;
	}


	/**
	 * Metodo de consulta de Operaciones por App
	 *
	 * @param request - Request
	 * @return ResponseApi
	 */
	public ResponseApi getOperacionesAppImpl(@Valid ReqBusqOperacionesApp request) {

		ResponseApi resultado;
		
		Document filtro = getFiltrosConsulta(request);
		
		long totalOperaciones = cotizacionesDao.countOperacionesApp(filtro);
		
		if(totalOperaciones > 0) {
			
			List<Document> listOperaciones = cotizacionesDao.consultaOperacionesApp(filtro, false);

			if (!listOperaciones.isEmpty()) {
				resultado = new ResponseApi(EstatusResponse.OK, listOperaciones);
				resultado.setMensaje("Total de operaciones: "+ totalOperaciones);
			} 
			else {
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}
	
	
	/**
	 * Metodo de descarga de Operaciones por App
	 *
	 * @param request - Request
	 * @return ResponseApi
	 */
	public ByteArrayInputStream descargaOperacionesAppImpl(@Valid ReqBusqOperacionesApp request) {
		
		Document filtro = getFiltrosConsulta(request);

		List<Document> listOperaciones = cotizacionesDao.consultaOperacionesApp(filtro, true);

		return new ReporteOperacionesAppXls(request.isEntregaDomicilio()).generaExel(listOperaciones);
	}
	

	private static Document getFiltrosConsulta(ReqBusqOperacionesApp request) {
		
		Document filtro = new Document();
		
		// Rango de fechas
		if(isNotEmpty(request.getFechaInicial()) && isNotEmpty(request.getFechaFinal())) {		
			filtro.append("fecha_cotizacion", new Document("$gte", request.getFechaInicial())
					   .append("$lte", request.getFechaFinal()));
		}
		else {
			// se coloca esta fecha porque en este año inicio el cambio y no hay registros antiguos
			filtro.append("fecha_cotizacion", new Document("$gte","2021-03-01"));
		}
		
		// Divisa
		if(isNotEmpty(request.getDivisa())) {
			filtro.append("divisa", request.getDivisa());
		}
		
		// Tipo de operacion
		if(request.isEntregaDomicilio()) {			
			filtro.append("tipo_operacion", new Document("$in", request.getTipoOperacion()))
			      .append("entrega", new Document("$exists",true));
		}
		else {
			filtro.append("tipo_operacion", new Document("$in",request.getTipoOperacion()))
				  .append("entrega", new Document("$exists",false));
		}
		
		// Sucursal
		if(isNotEmpty(request.getSucursal())) {
			filtro.append("sucursal", request.getSucursal());
		}
		
		// Id cliente
		if(isNotEmpty(request.getIdCliente())) {
			filtro.append("id_cliente", request.getIdCliente().trim());
		}
		
		// Estatus
		if (request.getEstatus() != null && !request.getEstatus().isEmpty()) {
			filtro.append("status", new Document("$in", request.getEstatus()));
		}
		
		// Origen
		if (isNotEmpty(request.getAplicacionOrigen())) {
			filtro.append("aplicacion_origen", request.getAplicacionOrigen());
		}

		return filtro;
	}
	
	private static boolean isNotEmpty(String filtro) {
		
		return filtro != null && !filtro.isEmpty();
	}

	/**
	 * Metodo de consulta de Incidencias App
	 *
	 * @param request - Request
	 * @return ResponseApi
	 */
	public ResponseApi getIncidenciasAppImpl() {

		ResponseApi resultado;
			
		List<Document> listaIncidencias = cotizacionesDao.consultaIncidenciasApp();

		if (!listaIncidencias.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, listaIncidencias);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}
	
	/**
	 * Metodo Actualizacion de Estatus de la Cotizacion
	 *
	 * @param request - Request
	 * @return ResponseApi
	 */
	public ResponseApi actualizaCotizacionImpl(ReqActualizaEstatus request) {

		ResponseApi resultado;
			
		boolean resultadoActualizacion = cotizacionesDao.actualizaEstatusCotizacion(request.getIdCotizacion(), 
																					request.getTipoOperacion(), 
																					request.getIdEstatus());

		if (resultadoActualizacion) {
			resultado = new ResponseApi(EstatusResponse.OK);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO);
		}

		return resultado;
	}
	
}
