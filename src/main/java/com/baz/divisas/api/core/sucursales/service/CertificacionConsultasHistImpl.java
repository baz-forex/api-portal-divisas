package com.baz.divisas.api.core.sucursales.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginadoBitacora;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionariosBitacora;
import com.baz.divisas.api.core.sucursales.utils.Validaciones;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.client.MongoCursor;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bson.Document;

/**
 * Clase CertificacionControllerTest
 * 
 * @author 1096526
 *
 */
@Service
public class CertificacionConsultasHistImpl {
	private static final String ERRORCUESTIONARIOS = "No fue posible obtener los cuestionarios, "
			+ "datos no válidos, favor de revisar la solicitud";
	private static final String PAGINAACTUAL = "paginaActual";
	private static final String FECHAREGISTRO = "fechaRegistro";
	private static final String TITULO = "titulo";
	private static final String PREGUNTAS = "preguntas";
	private static final String SUCURSALES = "sucursales";
	private static final String ESTATUS = "estatus";
	private static final String CERTIFICAR = "certificar";

	private final CertificacionDao certificacionDao;

	/**
	 * Constructor de la clase
	 * 
	 * @param certificacionDao - dependencia Dao
	 */
	public CertificacionConsultasHistImpl(CertificacionDao certificacionDao) {
		this.certificacionDao = certificacionDao;
	}

	/**
	 * Metodo para consultar los cuestionarios del histórico
	 * 
	 * @param reqPaginado - request de la solicitud
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi consultaCuestionariosHistorico(ReqPaginado reqPaginado) {
		Predicate<String> combinedValidation = Validaciones.validaPaginacion();
		String elementosPagina = "" + reqPaginado.getElementosPagina();
		String numeroPagina = "" + reqPaginado.getPagina();
		ResponseApi resultado;
		List<BeanCuestionarios> registros;

		if (combinedValidation.test(elementosPagina) && combinedValidation.test(numeroPagina)) {
			try {
				registros = certificacionDao.consultaCuestionariosHistorico(reqPaginado);

				if (registros.isEmpty()) {
					LogApp.mensaje(ERRORCUESTIONARIOS);
					resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
							Arrays.asList(ERRORCUESTIONARIOS));
				}
				else {
					long totalRegistros = certificacionDao.conteoCuestionariosHist();
					long paginas = calcularPaginas(registros.size(), totalRegistros,
							reqPaginado.getElementosPagina(), reqPaginado.getPagina());

					Document respuesta = crearRespuestaDocument(reqPaginado, paginas, registros);

					resultado = new ResponseApi(EstatusResponse.OK, respuesta);
				}
			}
			catch (IllegalArgumentException e) {
				LogApp.mensaje(ERRORCUESTIONARIOS);
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO, Arrays
						.asList("Error al intentar recuperar los cuestioanrios de la B.D. " + e));
			}

		}
		else {
			LogApp.mensaje(ERRORCUESTIONARIOS);
			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList(ERRORCUESTIONARIOS));
		}

		return resultado;
	}

	private static long calcularPaginas(int registrosEnPagina, long totalRegistros,
			int elementosPagina, int paginaActual) {

		if (registrosEnPagina == elementosPagina) {
			return (long) Math.ceil((double) totalRegistros / elementosPagina);
		}
		else {
			return paginaActual;
		}

	}

	private static Document crearRespuestaDocument(ReqPaginado reqPaginado, long paginas,
			List<BeanCuestionarios> registros) {

		List<Document> cuestionarios = registros.stream()
				.map(CertificacionConsultasHistImpl::crearCuestionarioDocument)
				.collect(Collectors.toList());

		return new Document().append(PAGINAACTUAL, reqPaginado.getPagina())
				.append("paginas", paginas).append("elementosPagina", registros.size())
				.append("cuestionarios", cuestionarios);
	}

	private static Document crearCuestionarioDocument(BeanCuestionarios cuestionario) {

		return new Document().append("id", cuestionario.getId())
				.append(TITULO, cuestionario.getTitulo().getTexto())
				.append(PREGUNTAS, cuestionario.getPreguntas().size())
				.append(FECHAREGISTRO, cuestionario.getFechaRegistro());
	}

	/**
	 * Metodo para obtener las respuestas del histórico
	 * 
	 * @param id - id de la solicitud
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi consultaPregCuestionariosHist(String id) {
		Predicate<String> validaId = Validaciones.validaID();

		if (!validaId.test(id)) {
			LogApp.mensaje(ERRORCUESTIONARIOS);
			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList(ERRORCUESTIONARIOS));
		}

		ResponseApi resultado;

		// Consulta los cuestionarios del historico
		try (MongoCursor<BeanCuestionarios> preguntasCuestionariosHist = certificacionDao
				.consultaPregCuestionariosHist(id)) {

			if (!preguntasCuestionariosHist.hasNext()) {
				LogApp.mensaje("No fue posible obtener las preguntas del histórico");
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible obtener las preguntas del histórico"));
			}
			else {
				BeanCuestionarios consultaCuestionarios = null;
				while (preguntasCuestionariosHist.hasNext()) {
					consultaCuestionarios = preguntasCuestionariosHist.next();
				}

				resultado = new ResponseApi(EstatusResponse.OK, consultaCuestionarios);
			}
		}
		catch (IllegalArgumentException e) {
			LogApp.mensaje(ERRORCUESTIONARIOS + " " + e);
			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList(ERRORCUESTIONARIOS));
		}

		return resultado;
	}

	/**
	 * Metodo para consultar los cuestionarios del histórico
	 * 
	 * @param reqPaginadoBitacora - request de la solicitud
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi consultaBitacoraCuestionario(ReqPaginadoBitacora reqPaginadoBitacora) {
		Predicate<String> combinedValidation = Validaciones.validaPaginacion();
		String elementosPagina = "" + reqPaginadoBitacora.getElementosPagina();
		String numeroPagina = "" + reqPaginadoBitacora.getPagina();
		ResponseApi resultado;
		List<BeanCuestionariosBitacora> registros;

		if (combinedValidation.test(elementosPagina) && combinedValidation.test(numeroPagina)) {
			try {
				// Obtiene el registro de la bitacora
				registros = certificacionDao.consultaBitacoraCuestionario(reqPaginadoBitacora);

				if (registros.isEmpty()) {
					LogApp.mensaje("No fue posible obtener datos de la bitácora");
					resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
							Arrays.asList("No fue posible obtener datos de la bitácora"));
				}
				else {
					long totalRegistros = certificacionDao
							.conteoBitacoraCuestionario(reqPaginadoBitacora);
					long paginas = calcularPaginas(registros.size(), totalRegistros,
							reqPaginadoBitacora.getElementosPagina(),
							reqPaginadoBitacora.getPagina());

					Document respuesta = crearRespuestaBitacora(reqPaginadoBitacora, paginas,
							registros);

					resultado = new ResponseApi(EstatusResponse.OK, respuesta);
				}
			}
			catch (IllegalArgumentException e) {
				LogApp.mensaje(ERRORCUESTIONARIOS);
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO, Arrays.asList(
						"Error al intentar recuperar los datos de la bitácora en la B.D. " + e));
			}

		}
		else {
			LogApp.mensaje(ERRORCUESTIONARIOS);
			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList(ERRORCUESTIONARIOS));
		}

		return resultado;
	}

	private static Document crearRespuestaBitacora(ReqPaginadoBitacora reqPaginadoBitacora,
			long paginas, List<BeanCuestionariosBitacora> registros) {

		List<Document> datosBitacora = registros.stream()
				.map(CertificacionConsultasHistImpl::crearCuestionarioBitacora)
				.collect(Collectors.toList());

		return new Document().append(PAGINAACTUAL, reqPaginadoBitacora.getPagina())
				.append("paginas", paginas).append("elementosPagina", registros.size())
				.append("bitacora", datosBitacora);
	}

	private static Document crearCuestionarioBitacora(
			BeanCuestionariosBitacora beanCuestionariosBitacora) {

		Document resp = new Document("evento", beanCuestionariosBitacora.getEvento());

		if ("Nuevo Cuestionario".equals(beanCuestionariosBitacora.getEvento())
				|| "Actualiza Cuestionario".equals(beanCuestionariosBitacora.getEvento())) {
			resp.append(TITULO, beanCuestionariosBitacora.getTitulo().getTexto())
					.append(PREGUNTAS, beanCuestionariosBitacora.getPreguntas().size())
					.append(FECHAREGISTRO, beanCuestionariosBitacora.getFechaRegistro());
		}
		else if ("Asigna Cuestionario".equals(beanCuestionariosBitacora.getEvento())) {
			resp.append(SUCURSALES, beanCuestionariosBitacora.getSucursales());
		}
		else if ("Actualiza Estatus Cuestionario".equals(beanCuestionariosBitacora.getEvento())) {
			resp.append(ESTATUS, beanCuestionariosBitacora.isEstatus());
		}
		else {
			resp.append(CERTIFICAR, beanCuestionariosBitacora.isCertificar());
		}

		return resp;
	}
}
