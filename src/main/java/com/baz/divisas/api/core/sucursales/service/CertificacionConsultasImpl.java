package com.baz.divisas.api.core.sucursales.service;

import static com.baz.divisas.api.core.sucursales.utils.Utileria.*;
import static com.baz.divisas.api.core.sucursales.utils.Utileria2.*;
import static com.baz.divisas.api.core.sucursales.service.CertificacionInsercionesImpl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.ReqPregRespSucursal;
import com.baz.divisas.api.core.sucursales.dto.ReqRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.dto.RespConsultaPreguntas;
import com.baz.divisas.api.core.sucursales.dto.RespConsultaSucursales;
import com.baz.divisas.api.core.sucursales.dto.RespPaginadoRespuestas;
import com.baz.divisas.api.core.sucursales.dto.RespConsultaRespuestas;
import com.baz.divisas.api.core.sucursales.dto.RespPaginadoSucursales;
import com.baz.divisas.api.core.sucursales.dto.RespConsultaPregRespSucursal;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.model.BeanSucursales;
import com.baz.divisas.api.core.sucursales.model.BeanTopesCaja;
import com.baz.divisas.api.core.sucursales.model.ConsultaRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.utils.Validaciones;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import java.util.function.Predicate;
import org.bson.Document;

/**
 * Clase CertificacionControllerTest
 * 
 * @author 1096526
 *
 */
@Service
public class CertificacionConsultasImpl {
	private static final String DATOSNOVALIDOS = "Datos no válidos, favor de revisar la solicitud";
	private static final String ERRORSUCURSALES = "No fue posible obtener las sucursales";
	private static final String ERRORSUCURSAL = "Error en el formato de la sucursal";
	private static final String ERRORCUESTIONARIOS = "No fue posible obtener los cuestionarios, "
			+ "datos no válidos, favor de revisar la solicitud";

	private static final String REGISTROS = "registros";
	private static final String SUCURSALES = "sucursales";


	private static final int USD = 2;
	private static final int OLP = 3;
	private static final int CAD = 5;
	private static final int EUR = 7;

	private static final int ANCLADA = Integer.parseInt(PropBD.getCajaAnclada());
	private static final int PRINCIPAL = Integer.parseInt(PropBD.getCajaPrincipal());

	private final CertificacionDao certificacionDao;

	/**
	 * Constructor de la clase
	 * 
	 * @param certificacionDao    - dependencia Dao
	 * @param coleccionSucursales - dependencia Collection
	 */
	public CertificacionConsultasImpl(CertificacionDao certificacionDao) {
		this.certificacionDao = certificacionDao;
	}

	/**
	 * Metodo para consultar las sucursales del CeCo
	 * 
	 * @param reqPaginado - request de la solicitud
	 * 
	 * @return sucursales Ceco paginadas
	 */
	public ResponseApi consultaSucursales(ReqPaginado reqPaginado) {

		ResponseApi resultado = null;
		RespPaginadoSucursales respPaginadoSucursales = new RespPaginadoSucursales();
		long paginas;
		Predicate<String> validaPaginacion = Validaciones.validaPaginacion();

		if (validaPaginacion.test("" + reqPaginado.getElementosPagina())
				&& validaPaginacion.test("" + reqPaginado.getPagina())) {

			List<Integer> divisa = parseIntegerList(PropBD.getDivisa());
			List<Integer> tipoCaja = new ArrayList<>();

			tipoCaja.add(ANCLADA);
			tipoCaja.add(PRINCIPAL);

			FindIterable<BeanSucursales> respConsulta = certificacionDao
					.consultaSucursales(reqPaginado);

			// Obtener el total de registros
			long totalRegistros = certificacionDao.conteoConsultaSucursales();

			// Crear una lista vacía para almacenar los documentos
			List<BeanSucursales> sucursales = new ArrayList<>();

			// Iterar a través de los documentos y agregarlos a la lista
			for (BeanSucursales sucursal : respConsulta) {
				Document topesCajaDoc = obtenerTopesCaja(divisa, tipoCaja, sucursal.getSucursal());
				sucursal.setTopesCaja(topesCajaDoc);

				if (null == sucursal.getDireccion()) {
					sucursal.setDireccion(
							sucursal.getNomCalle() + "| " + sucursal.getNumeroExterior());
				}

				sucursales.add(sucursal);
			}

			respPaginadoSucursales.setRegistrosEnPagina(sucursales.size());
			respPaginadoSucursales.setRegistros(sucursales);
			respPaginadoSucursales.setTotalRegistros(totalRegistros);

			if (!respPaginadoSucursales.getRegistros().isEmpty()) {

				if (respPaginadoSucursales.getRegistrosEnPagina() == reqPaginado
						.getElementosPagina()) {
					paginas = (long) Math.ceil(
							Double.parseDouble("" + respPaginadoSucursales.getTotalRegistros())
									/ reqPaginado.getElementosPagina());
				}
				else {
					paginas = reqPaginado.getPagina();
				}

				RespConsultaSucursales respConsultaSucursales = new RespConsultaSucursales();
				respConsultaSucursales.setSucursales(respPaginadoSucursales.getRegistros());
				respConsultaSucursales
						.setElementosPagina(respPaginadoSucursales.getRegistrosEnPagina());
				respConsultaSucursales.setPaginaActual(reqPaginado.getPagina());
				respConsultaSucursales.setPaginas(paginas);
				resultado = new ResponseApi(EstatusResponse.OK, respConsultaSucursales);
			}
			else {
				LogApp.mensaje(ERRORSUCURSALES);
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList(ERRORSUCURSALES));
			}
		}
		else {
			LogApp.mensaje(DATOSNOVALIDOS);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(DATOSNOVALIDOS));
		}

		return resultado;

	}

	/**
	 * Metodo para consultar las sucursales del CeCo
	 * 
	 * @param reqPaginado - request de la solicitud
	 * 
	 * @return sucursales Ceco paginadas
	 */
	public ResponseApi consultaCuestionarios(ReqPaginado reqPaginado) {

		Predicate<String> combinedValidation = Validaciones.validaPaginacion();

		if (combinedValidation.test("" + reqPaginado.getElementosPagina())
				&& combinedValidation.test("" + reqPaginado.getPagina())) {

			long totalRegistros = getTotalRegistros(certificacionDao);
			List<Document> cuestionariosFiltrados = getCuestionariosFiltrados(reqPaginado,
					certificacionDao);

			Document respPaginadoCuestionarios = getRespPaginadoCuestionarios(
					cuestionariosFiltrados, totalRegistros);

			if (!respPaginadoCuestionarios.getList(REGISTROS, Document.class).isEmpty()) {

				long paginas = calculateTotalPages(respPaginadoCuestionarios, reqPaginado);
				return createResponse(EstatusResponse.OK, respPaginadoCuestionarios, paginas,
						reqPaginado.getPagina());
			}
			else {
				LogApp.mensaje("No fue posible obtener los cuestionarios, de la B.D.");
				return createErrorResponse("No fue posible obtener los cuestionarios de la B.D.");
			}
		}
		else {
			LogApp.mensaje(ERRORCUESTIONARIOS);
			return createErrorResponse(ERRORCUESTIONARIOS);
		}
	}

	/**
	 * Metodo para connsultar las preguntas de un cuestioanrio
	 * 
	 * @param id - id del cuestionario
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi consultaPreguntas(String id) {
		ResponseApi resultado = null;
		RespConsultaPreguntas respConsultaPreguntas = null;
		Predicate<String> validacionId = Validaciones.validaID();

		if (validacionId.test(id)) {

			respConsultaPreguntas = certificacionDao.consultaPreguntas(id);

			if (null != respConsultaPreguntas && !respConsultaPreguntas.getPreguntas().isEmpty()) {

				resultado = new ResponseApi(EstatusResponse.OK, respConsultaPreguntas);
			}
			else {
				LogApp.mensaje("No fue posible obtener las preguntas del cuestionario " + id);
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays
						.asList("No fue posible obtener las preguntas del cuestionario " + id));
			}
		}
		else {
			LogApp.mensaje("Id no válido, favor de revisar la solicitud");
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Id no válido, favor de revisar la solicitud"));
		}

		return resultado;
	}

	/**
	 * Metodo para consultar las respuestas de un cuestionario
	 * 
	 * @param reqPaginado - request con el paginado
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi consultaRespuestas(ReqPaginado reqPaginado) {
		Predicate<String> validaPaginacion = Validaciones.validaPaginacion();

		if (!validaPaginacion.test("" + reqPaginado.getElementosPagina())
				|| !validaPaginacion.test("" + reqPaginado.getPagina())) {
			LogApp.mensaje(DATOSNOVALIDOS);
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, Arrays.asList(DATOSNOVALIDOS));
		}
		AggregateIterable<Document> result = certificacionDao.consultaRespuestas(reqPaginado);
		if (result == null) {
			LogApp.mensaje(
					"No fue posible obtener las respuestas, no se encontraron registros en la B.D.");
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(
					"No fue posible obtener las respuestas, no se encontraron registros en la B.D."));
		}
		RespPaginadoRespuestas respPaginadoRespuestas = processResult(result, certificacionDao);
		long paginas = calculatePages(respPaginadoRespuestas, reqPaginado);
		RespConsultaRespuestas respConsultaRespuestas = buildResponse(respPaginadoRespuestas,
				reqPaginado, paginas);
		return new ResponseApi(EstatusResponse.OK, respConsultaRespuestas);
	}

	/**
	 * Metodo para consultar las respuestas por sucursal
	 * 
	 * @param reqRespuestasSucursal - request de la solicitud
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi consultaRespuestasSucursal(ReqRespuestasSucursal reqRespuestasSucursal) {
		Predicate<String> validaPaginacion = Validaciones.validaPaginacion();
		Predicate<List<String>> validaSucursal = Validaciones.validaCampoSucursal();
		ResponseApi respuesta = null;
		boolean error = false;

		if (!validaPaginacion.test("" + reqRespuestasSucursal.getElementosPagina())
				|| !validaPaginacion.test("" + reqRespuestasSucursal.getPagina())) {
			LogApp.mensaje(DATOSNOVALIDOS);
			respuesta = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(DATOSNOVALIDOS));
			error = true;
		}

		if (!validaSucursal.test(Arrays.asList(reqRespuestasSucursal.getSucursal()))) {
			LogApp.mensaje(ERRORSUCURSAL);
			respuesta = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(ERRORSUCURSAL));
			error = true;
		}

		if (error) {
			return respuesta;
		}

		AggregateIterable<Document> result = certificacionDao
				.consultaRespuestasSucursal(reqRespuestasSucursal);
		ConsultaRespuestasSucursal consultaRespuestasSucursal = generarConsultaRespuestaSucursal(
				result);

		if (null == consultaRespuestasSucursal
				|| consultaRespuestasSucursal.getElementosPagina() <= 0) {
			LogApp.mensaje(
					"No fue posible obtener las respuestas, no se encontró la sucursal o no se han asignado cuestionarios");
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(
					"No fue posible obtener las respuestas, no se encontró la sucursal o no se han asignado cuestionarios"));
		}

		long paginas = calcularPaginas(consultaRespuestasSucursal, reqRespuestasSucursal);
		consultaRespuestasSucursal.setPaginas(paginas);
		consultaRespuestasSucursal.setPaginaActual(reqRespuestasSucursal.getPagina());

		return new ResponseApi(EstatusResponse.OK, consultaRespuestasSucursal);
	}

	/**
	 * Metodo para consultar las sucursales de un cuestionario del histórico
	 * 
	 * @param id - id del cuestionario
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi sucursalesAsignadasHist(String id) {
		ResponseApi resultado = null;
		Predicate<String> validaId = Validaciones.validaID();
		BeanCuestionarios respuestaConsulta;

		if (validaId.test(id)) {
			respuestaConsulta = certificacionDao.consultaSucAsignadasHist(id);

			if (null != respuestaConsulta) {
				Document resp = new Document();
				resp.append(SUCURSALES, respuestaConsulta.getSucursales());
				resultado = new ResponseApi(EstatusResponse.OK, resp);
			}
			else {
				LogApp.mensaje("No fue posible obtener las sucursales del histórico");
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList("No fue posible obtener las sucursales del histórico"));
			}
		}
		else {
			LogApp.mensaje(
					"No fue posible obtener las sucursales del histórico, datos no válidos, favor de revisar la solicitud");
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA, Arrays.asList(
					"No fue posible obtener las sucursales del histórico, datos no válidos, favor de revisar la solicitud"));
		}

		return resultado;
	}

	/**
	 * Metodo para consultar las preguntas y respuestas de un cuestionario
	 * 
	 * @param reqPregRespSucursal - request de la solicitud
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi consultaPreguntasRespuestas(ReqPregRespSucursal reqPregRespSucursal) {
		ResponseApi resultado;

		if (isRequestValid(reqPregRespSucursal)) {
			RespConsultaPregRespSucursal respConsultaPregRespSucursal = createConsultaPregResp(
					reqPregRespSucursal, certificacionDao);

			if (respConsultaPregRespSucursal.getPreguntas().isEmpty()) {
				LogApp.mensaje(
						"No fue posible obtener las respuestas, no se encontró la sucursal o no se ha asignado el cuestionario");
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(
						"No fue posible obtener las respuestas, no se encontró la sucursal o no se ha asignado el cuestionario"));
			}
			else {
				resultado = new ResponseApi(EstatusResponse.OK, respConsultaPregRespSucursal);
			}
		}
		else {
			LogApp.mensaje(DATOSNOVALIDOS);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(ERRORCUESTIONARIOS));
		}

		return resultado;
	}

	private Document obtenerTopesCaja(List<Integer> divisa, List<Integer> tipoCaja, int sucursal) {
		Document topesCaja = new Document();
		Document datosTopesAnclada = new Document();
		Document datosTopesPrincipal = new Document();

		MongoCursor<BeanTopesCaja> topeCaja = certificacionDao.consultaTopeCaja(divisa, tipoCaja,
				sucursal);

		while (topeCaja.hasNext()) {
			BeanTopesCaja beanTopesCaja = topeCaja.next();

			if (ANCLADA == beanTopesCaja.getIdCaja()) {

				setTopesCajaValue(beanTopesCaja, datosTopesAnclada);
			}

			if (PRINCIPAL == beanTopesCaja.getIdCaja()) {

				setTopesCajaValue(beanTopesCaja, datosTopesPrincipal);
			}
		}

		topeCaja.close();

		assignTopesCajaValues(topesCaja, datosTopesAnclada, ANCLADA);
		assignTopesCajaValues(topesCaja, datosTopesPrincipal, PRINCIPAL);

		return topesCaja;
	}

	private static void setTopesCajaValue(BeanTopesCaja beanTopesCaja, Document datosTopes) {

		switch (beanTopesCaja.getIdDivisa()) {
			case USD :
				datosTopes.append("dolar", beanTopesCaja.getImporteTope());
				break;
			case CAD :
				datosTopes.append("canadiense", beanTopesCaja.getImporteTope());
				break;
			case EUR :
				datosTopes.append("euro", beanTopesCaja.getImporteTope());
				break;
			case OLP :
				datosTopes.append("olp", beanTopesCaja.getImporteTope());
				break;
			default :
				return;
		}

	}

	private static void assignTopesCajaValues(Document topesCaja, Document datosTopes,
			int tipoCaja) {

		if (tipoCaja == ANCLADA) {
			topesCaja.append("Anclada", datosTopes);
		}

		if (tipoCaja == PRINCIPAL) {
			topesCaja.append("Principal", datosTopes);
		}
	}

}
