package com.baz.divisas.api.core.reporteria.services;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.api.core.reporteria.dao.OperacionesDao;
import com.baz.divisas.api.core.reporteria.dao.BeneficiosDao;
import com.baz.divisas.api.core.reporteria.dao.IncidenciasDao;
import com.baz.divisas.api.core.reporteria.dto.ReporteCostalSanta;
import com.baz.divisas.api.core.reporteria.dto.ReporteInventarioCostalSanta;
import com.baz.divisas.api.core.reporteria.dto.ReqBusquedaIncidencias;
import com.baz.divisas.api.core.reporteria.dto.ReqReporteOperaciones;
import com.baz.divisas.api.core.reporteria.dto.ReqReporteRascadito;
import com.baz.divisas.api.core.reporteria.model.GanadorCostalSanta;
import com.baz.divisas.api.core.reporteria.model.GanadorGolDeOro;
import com.baz.divisas.api.core.reporteria.model.GanadorPromoDigital;
import com.baz.divisas.api.core.reporteria.model.GanadorVeranoMundialista;
import com.baz.divisas.api.core.reporteria.model.Incidencia;
import com.baz.divisas.api.core.reporteria.model.InventarioCostalSanta;
import com.baz.divisas.api.core.reporteria.model.InventarioMundialVerde;
import com.baz.divisas.api.core.reporteria.model.InventarioVeranoMundialista;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.mongodb.client.AggregateIterable;

/**
 * Clase ReporteriaServiceImpl.
 *
 * @author itoxtle
 */

@Service
public class ReporteriaServiceImpl {

	/** The incidencias dao. */
	private IncidenciasDao incidenciasDao;
	
	/** The operaciones dao. */
	private OperacionesDao operacionesDao;
	
	/** The beneficios dao. */
	private BeneficiosDao beneficiosDao;

	/**
	 * Constructor de la clase.
	 *
	 * @param incidenciasDao - Reporteria Dao
	 * @param operacionesDao - Operaciones Dao
	 * @param beneficiosDao - Beneficios Dao
	 */
	@Autowired
	public ReporteriaServiceImpl(IncidenciasDao incidenciasDao, OperacionesDao operacionesDao, 
			BeneficiosDao beneficiosDao) {
		super();
		this.incidenciasDao = incidenciasDao;
		this.operacionesDao = operacionesDao;
		this.beneficiosDao = beneficiosDao;
	}

	/**
	 * Obtiene la fecha de registro de la primera incidencia en BD y la lista de
	 * incidencias en un rango de fechas de acuerdo al tipo.
	 *
	 * @param reqBusquedaIncidencias - Objeto con los parametros de busqueda
	 * @return Lista de incidencias
	 */
	public ResponseApi getIncidenciasImpl(ReqBusquedaIncidencias reqBusquedaIncidencias) {

		ResponseApi resultado;

		List<Incidencia> listaIncidencias = incidenciasDao.consulta(reqBusquedaIncidencias, false);

		if (!listaIncidencias.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, listaIncidencias);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}

	/**
	 * Obtiene el detalle de la incidencia por Id.
	 *
	 * @param id - Id de indicencia
	 * @return Detalle de incidencia
	 */
	public ResponseApi getIncidenciaImpl(String id) {
		ResponseApi resultado;

		Incidencia incidencia = incidenciasDao.consultaPorId(id);

		if (incidencia != null) {
			resultado = new ResponseApi(EstatusResponse.OK, incidencia);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}
	
	/**
	 * Obtiene la fecha de registro de la primera incidencia en BD y la lista de
	 * incidencias en un rango de fechas de acuerdo al tipo y lo descarga en xml.
	 *
	 * @param reqBusquedaIncidencias - Objeto con los parametros de b�squeda
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream descargaIncidenciasImpl(@Valid ReqBusquedaIncidencias reqBusquedaIncidencias) {
		List<Incidencia> listaIncidencias = incidenciasDao.consulta(reqBusquedaIncidencias, true);
		return new ReporteIncidenciasXls().generaExcel(listaIncidencias, reqBusquedaIncidencias.getTipoIncidencia());
	}
	
	/**
	 * Metodo de Consulta de Operaciones.
	 *
	 * @param request - Objeto con los parametros de busqueda
	 * @return Lista de Operaciones
	 */
	public ResponseApi getOperacionesImpl(@Valid ReqReporteOperaciones request) {
		
		ResponseApi resultado;
		
		Document filtro = getFiltrosConsulta(request);
		
		long totalOperaciones = operacionesDao.count(filtro);
		
		if(totalOperaciones > 0) {
			
			Iterable<Document> listOperaciones = operacionesDao.consulta(filtro, false);

			if (listOperaciones.iterator().hasNext()) {
				resultado = new ResponseApi(EstatusResponse.OK, 
							((AggregateIterable<Document>)listOperaciones).into(new ArrayList<Document>()));
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
	 * Metodo de descarga de Operaciones.
	 *
	 * @param request - Objeto con los parametros de busqueda
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream descargaOperacionesImpl(@Valid ReqReporteOperaciones request) {
		
		Document filtro = getFiltrosConsulta(request);

		Iterable<Document> listOperaciones = operacionesDao.consulta(filtro, true);

		return new ReporteOperacionesXls().generaExel(listOperaciones);
	}
	
	/**
	 * Metodo de Consulta Acumulado de Operaciones.
	 *
	 * @param request - Objeto con los parametros de busqueda
	 * @return Acumulado de Operaciones
	 */
	public ResponseApi getAcumuladoOperacionesImpl(@Valid ReqReporteOperaciones request) {
				
		Document filtro = getFiltrosConsulta(request);
		
		Document acumulado = operacionesDao.acumulado(filtro);
	
		return new ResponseApi(EstatusResponse.OK, acumulado);

	}
		
	/**
	 * Metodo de descarga de Ganadores Rascadito Digital.
	 *
	 * @param request - Objeto con los parametros de busqueda
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream descargaGanadoresRascaditoImpl(@Valid ReqReporteRascadito request) {
		
		Document filtro = new Document("fechaRegistro", 
									   new Document("$gte", request.getFechaInicial() + "T00:00:00.000")
									        .append("$lte", request.getFechaFinal()   + "T23:59:59.000"));

		List<GanadorPromoDigital> ganadores = beneficiosDao.consultaGanadoresPromoDigital(filtro);

		return new ReporteRascaditoXls().generaExel(ganadores);
	}
	
	/**
	 * Gets the filtros consulta.
	 *
	 * @param request the request
	 * @return the filtros consulta
	 */
	private static Document getFiltrosConsulta(ReqReporteOperaciones request) {
		
		Document filtro = new Document();
		
		// Rango de fechas
		if(isNotEmpty(request.getFechaInicial()) && isNotEmpty(request.getFechaFinal())) {		
			filtro.append("fecha_operacion", new Document("$gte", request.getFechaInicial() + "T00:00:00.000")
					   .append("$lte", request.getFechaFinal() + "T23:59:59.000"));
		}
		
		// Divisa
		if(isNotEmpty(request.getDivisa())) {
			filtro.append("divisa", request.getDivisa());
		}
		
		// Tipo de operacion		
		filtro.append("tipo_operacion", new Document("$in", request.getTipoOperacion()));
			
		// Sucursal
		if(isNotEmpty(request.getSucursal())) {
			filtro.append("sucursal", request.getSucursal());
		}
		
		// Id cliente
		if(isNotEmpty(request.getIdCliente())) {
			filtro.append("id_cliente", request.getIdCliente().trim());
		}
		
		// Origen
		if (isNotEmpty(request.getAplicacionOrigen())) {
			filtro.append("aplicacion_origen", request.getAplicacionOrigen());
		}
		
		// Estatus
		if (request.getEstatus() != null && !request.getEstatus().isEmpty()) {
			filtro.append("status", new Document("$in", request.getEstatus()));
		}

		return filtro;
	}
		
	/**
	 * Checks if is not empty.
	 *
	 * @param filtro the filtro
	 * @return true, if is not empty
	 */
	private static boolean isNotEmpty(String filtro) {
		
		return filtro != null && !filtro.isEmpty();
	}

	
	
	/**
	 * Descarga ganadores gol de oro impl.
	 *
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream descargaGanadoresGolDeOroImpl() {
		Document filtro = new Document();
		filtro.append("status", "EXITOSO");
		List<GanadorGolDeOro> ganadores = beneficiosDao.consultaGanadoresGolDeOro(filtro);
		List<String> bloqueos = beneficiosDao.consultaEmpleadosBloqueados();
		return new ReporteGolDeOro().generaExcel(ganadores,bloqueos);
	}

	/**
	 * Descarga inventario mundial verde impl.
	 *
	 * @return the input stream
	 */
	public ByteArrayInputStream descargaInventarioMundialVerdeImpl() {
		List<InventarioMundialVerde> inventarios = beneficiosDao.consultaInventarioMundialVerde();

		return new ReporteInventarioMundialVerde().generaExcel(inventarios);
	}

	/**
	 * Descarga ganadores verano mundialista.
	 *
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream descargaGanadoresVeranoMundialista() {
		Document filtro = new Document();
		filtro.append("status", "EXITOSO");
		List<GanadorVeranoMundialista> ganadores = beneficiosDao.consultaGanadoresVeranoMundialista(filtro);
		return new ReporteVeranoMundialista().generaExcel(ganadores);
	}
	
	/**
	 * Descarga inventario verano mundialista impl.
	 *
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream descargaInventarioVeranoMundialistaImpl() {
		List<InventarioVeranoMundialista> inventarios = beneficiosDao.consultaInventarioVeranoMundialista();

		return new ReporteInventarioVeranoMundialista().generaExcel(inventarios);
	}

	/**
	 * Descarga entregas cupones Mas Salud impl.
	 *
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream descargaCuponesMasSaludImpl() {
		List<CuponMasSalud> cupones = beneficiosDao.consultaCuponesMasSalud();
		return new ReporteCuponesMasSaludXls().generaExcel(cupones);
	}

	/**
	 * Descarga ganadores costal santa.
	 *
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream descargaGanadoresCostalSanta() {
		Document filtro = new Document();
		filtro.append("status", "EXITOSO");
		List<GanadorCostalSanta> ganadores = beneficiosDao.consultaGanadoresCostalSanta(filtro);
		return new ReporteCostalSanta().generaExcel(ganadores);
	}

	/**
	 * Descarga inventario costal santa.
	 *
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream descargaInventarioCostalSanta() {
		List<InventarioCostalSanta> inventarios = beneficiosDao.consultaInventarioCostalSanta();

		return new ReporteInventarioCostalSanta().generaExcel(inventarios);
	}
}
