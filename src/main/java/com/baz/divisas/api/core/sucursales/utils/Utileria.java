package com.baz.divisas.api.core.sucursales.utils;

import static com.baz.divisas.api.core.sucursales.utils.Constantes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.RespConsultaRespuestas;
import com.baz.divisas.api.core.sucursales.dto.RespPaginadoRespuestas;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionariosBitacora;
import com.baz.divisas.api.core.sucursales.model.BeanSucursales;
import com.baz.divisas.api.core.sucursales.model.ConsultaRespuestas;
import com.baz.divisas.api.core.sucursales.model.ConsultaRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.model.Contacto;
import com.baz.divisas.api.core.sucursales.model.Cuestionario;
import com.baz.divisas.api.core.sucursales.model.Titulo;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.EntCorreo;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.MailServiceCertificacion;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;

/**
 * Clase Utileria
 * 
 * @author 1096526
 *
 */
public final class Utileria {
	private static final String CERTIFICAR = "certificar";
	private static final String ALERTACERTIFICACION = "Alerta de Certificación de Sucursal";

	/**
	 * Constructor de la clase
	 */
	private Utileria() {
		super();
	}

	/**
	 * Metodo para procesar la certificacion de una sucursal
	 * 
	 * @param sucursal         - sucursal a certificar
	 * @param respAvance       - avence de las respuestas
	 * @param datosSucursal    - datos de la sucursal
	 * @param certificacionDao - objeto dao
	 * @param mail             - objeto para enviar correo
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public static ResponseApi procesarCertificacionSucursal(String sucursal,
			ConsultaRespuestasSucursal respAvance, BeanSucursales datosSucursal,
			CertificacionDao certificacionDao, MailServiceCertificacion mail) {
		String mensaje = "";
		boolean enviarMensaje = false;

		if (datosSucursal != null && respAvance.getAvanceTotal() == CIEN) {

			MongoCursor<BeanCuestionarios> cuestionarios = certificacionDao
					.getCuestionarios("" + sucursal);

			List<BeanCuestionarios> cuestionariosAInsertar = new ArrayList<>();

			try {
				while (cuestionarios.hasNext()) {
					cuestionariosAInsertar.add(cuestionarios.next());
				}
			}
			finally {
				cuestionarios.close();
			}

			for (BeanCuestionarios cuestionario : cuestionariosAInsertar) {
				ObjectId id = new ObjectId(cuestionario.getId());
				cuestionario.setId(null);

				if (!certificacionDao.actualizaCuestionarioHist(cuestionario, id)) {

					return new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays
							.asList("No fue posible actualizar el cuestionario en histórico"));
				}

				BeanCuestionariosBitacora beanCuestionariosBitacora = new BeanCuestionariosBitacora();
				beanCuestionariosBitacora.setEstatus(cuestionario.isEstatus());
				beanCuestionariosBitacora.setFechaRegistro(cuestionario.getFechaRegistro());
				beanCuestionariosBitacora.setIdCuestionario(id.toHexString());
				beanCuestionariosBitacora.setPreguntas(cuestionario.getPreguntas());
				beanCuestionariosBitacora.setSucursales(cuestionario.getSucursales());
				beanCuestionariosBitacora.setTitulo(cuestionario.getTitulo());
				beanCuestionariosBitacora.setCertificar(datosSucursal.isCertificar());
				beanCuestionariosBitacora.setEvento("Certificación");
				certificacionDao.nuevoCuestionarioBitacora(beanCuestionariosBitacora);

			}

			if (datosSucursal.isCertificar()) {
				mensaje = "Se notifica por este medio la certificación de la sucursal " + sucursal
						+ " al haber respondido correctamente todos sus cuestionarios asignados.";
			}
			else {
				mensaje = "Se notifica por este medio que la sucursal " + sucursal
						+ " ya no se encuentra certificada, es necesario responder un nuevo cuestionario asignado.";
			}

			enviarMensaje = true;
		}
		else {
			LogApp.mensaje(
					"No fue posible certificar la sucursal, debido a que no ha finalizado todos sus cuestionarios");
		}

		if (enviarMensaje) {
			enviarCorreoCertificacion(sucursal, mensaje, mail);
			return new ResponseApi(EstatusResponse.OK,
					new Document().append(CERTIFICAR, datosSucursal.isCertificar()));
		}
		else {
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(
					"No fue posible certificar la sucursal, debido a que no ha finalizado todos sus cuestionarios"));
		}
	}

	private static void enviarCorreoCertificacion(String sucursal, String mensaje,
			MailServiceCertificacion mail) {
		EntCorreo correo = new EntCorreo(0,
				"g" + String.format("%04d", Integer.parseInt(sucursal)) + PropApp.getCorreoGerente()
						+ "; senen.ramirez@elektra.com.mx;",
				ALERTACERTIFICACION + " " + sucursal, mensaje);
		mail.enviaMail(correo);
	}

	/**
	 * Metodo para procesar el resultado de la consulta
	 * 
	 * @param result           - resultado de la consulta
	 * @param certificacionDao - objeto Dao para consultas
	 * 
	 * @return objeto RespPaginadoRespuestas con la respuesta de la consulta
	 */
	public static RespPaginadoRespuestas processResult(AggregateIterable<Document> result,
			CertificacionDao certificacionDao) {
		long totalRegistros = 0;

		List<ConsultaRespuestas> respuestas = new ArrayList<>();
		for (Document res : result) {
			ConsultaRespuestas respuesta = buildConsultaRespuesta(res, certificacionDao);
			respuestas.add(respuesta);
		}
		AggregateIterable<Document> conteo = certificacionDao.conteoConsultaRespuesta();

		for (Document total : conteo) {
			totalRegistros = total.getLong("total");
		}

		RespPaginadoRespuestas respPaginadoRespuestas = new RespPaginadoRespuestas();
		respPaginadoRespuestas.setRegistros(respuestas);
		respPaginadoRespuestas.setRegistrosEnPagina(respuestas.size());
		respPaginadoRespuestas.setTotalRegistros(totalRegistros);
		return respPaginadoRespuestas;
	}

	private static ConsultaRespuestas buildConsultaRespuesta(Document res,
			CertificacionDao certificacionDao) {
		ConsultaRespuestas respuesta = new ConsultaRespuestas();
		BeanSucursales datosSucursal = certificacionDao
				.datosSucursal(Integer.parseInt(res.getString("_id")));
		if (null != datosSucursal) {
			respuesta.setSucursal(res.getString("_id"));
			respuesta.setCuestionarios(res.getInteger(TOTAL));
			respuesta.setAvance(res.getInteger(AVANCE));
			respuesta.setGerente(datosSucursal.getGerente());
			respuesta.setContacto(buildContacto(datosSucursal));
			respuesta.setCertificar(datosSucursal.isCertificar());
			respuesta.setEstatus(
					determineEstatus(res.getInteger(AVANCE), datosSucursal.isCertificar()));
		}
		return respuesta;
	}

	private static Contacto buildContacto(BeanSucursales datosSucursal) {
		Contacto contacto = new Contacto();
		contacto.setTelefono(datosSucursal.getContactoSucursal());
		contacto.setCorreo(datosSucursal.getCorreoGerente());
		return contacto;
	}

	private static String determineEstatus(int avance, boolean isCertificar) {
		String mensaje = "Completada";

		if (avance == 0) {
			mensaje = "Sin Respuesta Cuestionario";
		}
		if (avance > 0 && avance < CIEN) {
			mensaje = "En proceso";
		}
		if (avance == CIEN && !isCertificar) {
			mensaje = "Pendiente de envío";
		}
		return mensaje;
	}

	/**
	 * Metodo para construir la respuesta
	 * 
	 * @param respPaginadoRespuestas - respuesta con el paginado
	 * @param reqPaginado            - request del paginado
	 * @param paginas                - total de paginas
	 * 
	 * @return objeto RespConsultaRespuestas con la respuesta del servicio
	 */
	public static RespConsultaRespuestas buildResponse(
			RespPaginadoRespuestas respPaginadoRespuestas, ReqPaginado reqPaginado, long paginas) {
		RespConsultaRespuestas respConsultaRespuestas = new RespConsultaRespuestas();
		respConsultaRespuestas.setRespuestas(respPaginadoRespuestas.getRegistros());
		respConsultaRespuestas.setElementosPagina(respPaginadoRespuestas.getRegistrosEnPagina());
		respConsultaRespuestas.setPaginaActual(reqPaginado.getPagina());
		respConsultaRespuestas.setPaginas(paginas);
		return respConsultaRespuestas;
	}

	/**
	 * Metodo para generar la respuesta de la consulta
	 * 
	 * @param result - resultado obtenido
	 * 
	 * @return objeto ConsultaRespuestasSucursal con el resultado de la consulta
	 */
	public static ConsultaRespuestasSucursal generarConsultaRespuestaSucursal(
			AggregateIterable<Document> result) {
		ConsultaRespuestasSucursal consultaRespuestasSucursal = null;
		List<Cuestionario> cuestionarios = new ArrayList<>();

		if (result != null) {
			for (Document res : result) {
				consultaRespuestasSucursal = new ConsultaRespuestasSucursal();
				List<Document> cuestionariosDocument = res.getList(CUESTIONARIOS, Document.class);
				cuestionarios.addAll(crearCuestionarios(cuestionariosDocument));

				consultaRespuestasSucursal.setElementosPagina(cuestionarios.size());
				consultaRespuestasSucursal.setAvanceTotal(res.getInteger(AVANCETOTAL));
				consultaRespuestasSucursal.setCuestionarios(cuestionarios);
				consultaRespuestasSucursal.setTotalRegistros(res.getLong(TOTALREGISTROS));
			}
		}

		return consultaRespuestasSucursal;
	}

	private static List<Cuestionario> crearCuestionarios(List<Document> cuestionariosDocument) {
		List<Cuestionario> cuestionarios = new ArrayList<>();
		for (Document cuestionarioDocument : cuestionariosDocument) {
			Cuestionario cuestionario = new Cuestionario();
			cuestionario.setAvance(cuestionarioDocument.getInteger(AVANCE));
			cuestionario.setFechaRegistro(cuestionarioDocument.getString(FECHAREGISTRO));
			cuestionario.setId(cuestionarioDocument.getObjectId("id").toHexString());
			cuestionario.setPreguntas(cuestionarioDocument.getInteger(PREGUNTAS));
			cuestionario
					.setPreguntasRespondidas(cuestionarioDocument.getInteger(PREGUNTASRESPONDIDAS));

			Document tituloDocument = cuestionarioDocument.get(TITULO, Document.class);
			Titulo titulo = new Titulo();
			titulo.setTexto(tituloDocument.getString("texto"));
			titulo.setImagen(tituloDocument.getString(IMAGEN));
			cuestionario.setTitulo(titulo);

			cuestionarios.add(cuestionario);
		}
		return cuestionarios;
	}
}
