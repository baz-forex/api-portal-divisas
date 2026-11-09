package com.baz.divisas.api.core.sucursales.utils;

import static com.baz.divisas.api.core.sucursales.utils.Constantes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.bson.Document;

import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.ReqPregRespSucursal;
import com.baz.divisas.api.core.sucursales.dto.RespConsultaPregRespSucursal;
import com.baz.divisas.api.core.sucursales.dto.RespPaginadoRespuestas;
import com.baz.divisas.api.core.sucursales.model.ConsultaRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.model.Opcion;
import com.baz.divisas.api.core.sucursales.model.Pregunta;
import com.baz.divisas.api.core.sucursales.model.RespuestaCuestionario;
import com.baz.divisas.api.core.sucursales.model.TipoRespuesta;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.AggregateIterable;

/**
 * Clase Utileria2
 * 
 * @author 1096526
 *
 */
public final class Utileria2 {

	/**
	 * Constructor de la clase
	 */
	private Utileria2() {
		super();
	}

	/**
	 * Metodo para calcular el paginado
	 * 
	 * @param consultaRespuestasSucursal - respuesta de la consulta
	 * @param reqRespuestasSucursal      - request de la solicitud
	 * 
	 * @return numero de paginas
	 */
	public static long calcularPaginas(ConsultaRespuestasSucursal consultaRespuestasSucursal,
			ReqPaginado reqRespuestasSucursal) {
		if (consultaRespuestasSucursal.getElementosPagina() == reqRespuestasSucursal
				.getElementosPagina()) {
			return (long) Math
					.ceil(Double.parseDouble("" + consultaRespuestasSucursal.getTotalRegistros())
							/ reqRespuestasSucursal.getElementosPagina());
		}
		else {
			return reqRespuestasSucursal.getPagina();
		}
	}

	/**
	 * Metodo para extraer las preguntas de un cuestionaro
	 * 
	 * @param preguntasDoc - preguntas del cuestionario
	 * 
	 * @return objeto Pregunta con el resultado obtenido
	 */
	public static List<Pregunta> constructPreguntas(List<Document> preguntasDoc) {
		List<Pregunta> preguntas = new ArrayList<>();
		if (null != preguntasDoc) {
			for (Document preguntaDoc : preguntasDoc) {
				Pregunta pregunta = new Pregunta();
				pregunta.setId(preguntaDoc.getInteger("_id"));
				pregunta.setTexto(preguntaDoc.getString(TEXTO));
				pregunta.setImagen(preguntaDoc.getString(IMAGEN));

				Document docTipoRespuesta = preguntaDoc.get("tipoRespuesta", Document.class);
				TipoRespuesta tipoRespuesta = new TipoRespuesta();
				tipoRespuesta.setId(docTipoRespuesta.getString("_id"));
				tipoRespuesta.setDescripcion(docTipoRespuesta.getString("descripcion"));

				pregunta.setTipoRespuesta(tipoRespuesta);
				pregunta.setPermiteImagen(preguntaDoc.getBoolean("permiteImagen"));
				pregunta.setRespuestaMultiple(preguntaDoc.getBoolean("respuestaMultiple"));
				pregunta.setObligatoria(preguntaDoc.getBoolean("obligatoria"));
				pregunta.setOpciones(
						constructOpciones(preguntaDoc.getList("opciones", Document.class)));

				preguntas.add(pregunta);
			}
		}

		return preguntas;
	}

	private static List<Opcion> constructOpciones(List<Document> opcionesDoc) {
		List<Opcion> opciones = new ArrayList<>();
		if (null != opcionesDoc) {
			for (Document docOpcion : opcionesDoc) {
				Opcion opcion = new Opcion();
				opcion.setId(docOpcion.getInteger("_id"));
				opcion.setTexto(docOpcion.getString(TEXTO));

				opciones.add(opcion);
			}
		}

		return opciones;
	}

	/**
	 * Metodo para extraer las respuestas de un cuestionario
	 * 
	 * @param respuestasDoc - respuestas del cuestionario
	 * 
	 * @return objeto RespuestaCuestionario con la respuesta obtenida
	 */
	public static List<RespuestaCuestionario> constructRespuestas(List<Document> respuestasDoc) {
		List<RespuestaCuestionario> respuestas = new ArrayList<>();
		if (null != respuestasDoc) {
			for (Document docRespuesta : respuestasDoc) {
				RespuestaCuestionario respuesta = new RespuestaCuestionario();
				respuesta.setIdPregunta(docRespuesta.getInteger("idPregunta"));
				respuesta.setIdTipoRespuesta(docRespuesta.getInteger("idTipoRespuesta"));
				respuesta.setRespuesta(docRespuesta.getString("respuesta"));
				respuesta.setImagen(docRespuesta.getString(IMAGEN));
				respuesta.setOpciones(
						constructOpciones(docRespuesta.getList("opciones", Document.class)));

				if (respuesta.getIdTipoRespuesta() == 3) {
					respuesta.setMotivo(docRespuesta.getString("motivo"));
				}

				respuestas.add(respuesta);
			}

		}
		return respuestas;
	}

	/**
	 * Metodo para verificar los campos de la solicitud
	 * 
	 * @param reqPregRespSucursal - rquest de la solicitud
	 * 
	 * @return true/false
	 */
	public static boolean isRequestValid(ReqPregRespSucursal reqPregRespSucursal) {
		Predicate<String> validaId = Validaciones.validaID();
		Predicate<List<String>> validaSucursal = Validaciones.validaCampoSucursal();

		return validaSucursal.test(Arrays.asList(reqPregRespSucursal.getSucursal()))
				&& validaId.test(reqPregRespSucursal.getId());
	}

	/**
	 * Metodo para btener las respuestas del cuestionario
	 * 
	 * @param reqPregRespSucursal - request de la solicitud
	 * @param certificacionDao    - objeto Dao para consultas
	 * 
	 * @return objeto RespConsultaPregRespSucursal con la respuesta obtenida
	 */
	public static RespConsultaPregRespSucursal createConsultaPregResp(
			ReqPregRespSucursal reqPregRespSucursal, CertificacionDao certificacionDao) {
		RespConsultaPregRespSucursal respConsultaPregRespSucursal = new RespConsultaPregRespSucursal();

		AggregateIterable<Document> resultPreguntas = certificacionDao
				.consultaPreguntasSuc(reqPregRespSucursal);
		if (resultPreguntas != null) {
			for (Document res : resultPreguntas) {
				respConsultaPregRespSucursal
						.setPreguntas(constructPreguntas(res.getList(PREGUNTAS, Document.class)));
			}
		}

		AggregateIterable<Document> resultRespuestas = certificacionDao
				.consultaRespuestasSuc(reqPregRespSucursal);
		if (resultRespuestas != null) {
			for (Document res : resultRespuestas) {
				respConsultaPregRespSucursal.setRespuestas(
						constructRespuestas(res.getList(RESPUESTAS, Document.class)));
			}
		}

		return respConsultaPregRespSucursal;
	}

	/**
	 * Metodo para obtener el total de registros
	 * 
	 * @param certificacionDao - Objeto Dao
	 * 
	 * @return long con el total de registros
	 */
	public static long getTotalRegistros(CertificacionDao certificacionDao) {
		long totalRegistros = 0;
		AggregateIterable<Document> conteo = certificacionDao.conteoCuestionarios();

		for (Document total : conteo) {
			totalRegistros = total.getInteger("total");
		}

		return totalRegistros;
	}

	/**
	 * Metodo para obtener los cuestionarios ya filtrados
	 * 
	 * @param reqPaginado      - request
	 * @param certificacionDao - objeto Dao
	 * 
	 * @return Document con la respuesta obtenida
	 */
	public static List<Document> getCuestionariosFiltrados(ReqPaginado reqPaginado,
			CertificacionDao certificacionDao) {
		List<Document> cuestionariosFiltrados = new ArrayList<>();
		AggregateIterable<Document> cuestionarios = certificacionDao
				.consultaCuestionarios(reqPaginado.getElementosPagina(), reqPaginado.getPagina());

		for (Document cuestionario : cuestionarios) {
			Document respCuestionarios = new Document();

			respCuestionarios.append("id", cuestionario.getObjectId("id").toHexString());
			respCuestionarios.append("estatus", cuestionario.getBoolean("estatus"));
			respCuestionarios.append(FECHAREGISTRO, cuestionario.getString(FECHAREGISTRO));
			respCuestionarios.append(TITULO, cuestionario.get(TITULO, Document.class));
			respCuestionarios.append(SUCURSALES, cuestionario.getList(SUCURSALES, String.class));
			respCuestionarios.append(PREGUNTAS, cuestionario.getInteger(PREGUNTAS));

			cuestionariosFiltrados.add(respCuestionarios);
		}

		return cuestionariosFiltrados;
	}

	/**
	 * MEtodo para obtener el paginado
	 * 
	 * @param cuestionariosFiltrados - cuestioanrios obtenidos en la consulta
	 * @param totalRegistros         - total de registros en la respuesta
	 * 
	 * @return Document con la respuesta obtenida
	 */
	public static Document getRespPaginadoCuestionarios(List<Document> cuestionariosFiltrados,
			long totalRegistros) {
		Document respPaginadoCuestionarios = new Document();
		respPaginadoCuestionarios.append(REGISTROSENPAGINA, cuestionariosFiltrados.size());
		respPaginadoCuestionarios.append(REGISTROS, cuestionariosFiltrados);
		respPaginadoCuestionarios.append("totalRegistros", totalRegistros);
		return respPaginadoCuestionarios;
	}

	/**
	 * Metodo para calcular la paginacion
	 * 
	 * @param respPaginadoCuestionarios - respuesta
	 * @param reqPaginado               - request
	 * 
	 * @return long con el total de paginas
	 */
	public static long calculateTotalPages(Document respPaginadoCuestionarios,
			ReqPaginado reqPaginado) {
		if (respPaginadoCuestionarios.getInteger(REGISTROSENPAGINA) == reqPaginado
				.getElementosPagina()) {
			return (long) Math.ceil(
					Double.parseDouble("" + respPaginadoCuestionarios.getLong("totalRegistros"))
							/ reqPaginado.getElementosPagina());
		}
		else {
			return reqPaginado.getPagina();
		}
	}

	/**
	 * Metodo para crear la respuesta del servicio
	 * 
	 * @param status                    - estatus
	 * @param respPaginadoCuestionarios - respuesta
	 * @param paginas                   - nuemro de pagtinas
	 * @param paginaActual              - pagina actual
	 * 
	 * @return ResponseApi con la respuesta del servicio
	 */
	public static ResponseApi createResponse(EstatusResponse status,
			Document respPaginadoCuestionarios, long paginas, int paginaActual) {
		Document respConsultaCuestionarios = new Document();
		respConsultaCuestionarios.append(REGISTROSENPAGINA,
				respPaginadoCuestionarios.getInteger(REGISTROSENPAGINA));
		respConsultaCuestionarios.append(PAGINAACTUAL, paginaActual);
		respConsultaCuestionarios.append("paginas", paginas);
		respConsultaCuestionarios.append("cuestionarios",
				respPaginadoCuestionarios.getList(REGISTROS, Document.class));
		return new ResponseApi(status, respConsultaCuestionarios);
	}

	/**
	 * Metodo para generar Respuesta de error
	 * 
	 * @param message - mensaje
	 * 
	 * @return objeto ResponseApi con la respuesta generada
	 */
	public static ResponseApi createErrorResponse(String message) {
		return new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(message));
	}

	/**
	 * Metodo para calcular el paginado
	 * 
	 * @param respPaginadoRespuestas - respuesta del paginado
	 * @param reqPaginado            - rquest del paginado
	 * 
	 * @return long con el total de paginas
	 */
	public static long calculatePages(RespPaginadoRespuestas respPaginadoRespuestas,
			ReqPaginado reqPaginado) {
		if (respPaginadoRespuestas.getRegistrosEnPagina() == reqPaginado.getElementosPagina()) {
			return (long) Math
					.ceil(Double.parseDouble("" + respPaginadoRespuestas.getTotalRegistros())
							/ reqPaginado.getElementosPagina());
		}
		return reqPaginado.getPagina();
	}
}
