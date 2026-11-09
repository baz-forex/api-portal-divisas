package com.baz.divisas.api.core.sucursales.service;

import static com.baz.divisas.api.core.sucursales.utils.Utileria.generarConsultaRespuestaSucursal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqAsignarSucursales;
import com.baz.divisas.api.core.sucursales.dto.RespActualizaEstatusCuestionario;
import com.baz.divisas.api.core.sucursales.dto.ReqRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionariosBitacora;
import com.baz.divisas.api.core.sucursales.model.BeanRespuestasCuestionario;
import com.baz.divisas.api.core.sucursales.model.BeanSucursales;
import com.baz.divisas.api.core.sucursales.model.ConsultaRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.utils.Validaciones;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.MailServiceCertificacion;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.result.UpdateResult;
import java.util.function.Predicate;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.baz.divisas.api.core.sucursales.utils.Utileria.procesarCertificacionSucursal;

/**
 * Clase CertificacionInsercionesImplTest
 * 
 * @author 1096526
 *
 */
@Service
public class CertificacionModificacionesImpl {
	private static final int CIEN = 100;
	private static final int DIEZMIL = 10000;
	private static final String ERRORCERTIFICACION = "No fue posible certificar la sucursal, no existe en la B.D.";
	private static final String ERRORCERTIFICACION1 = "Formato de sucursal no válido";
	private static final String ERRORCERTIFICACION2 = "No fue posible certificar la sucursal, "
			+ "no se han respondido todos los cuestionarios";
	private static final String ERRORCERTIFICACION3 = "No fue posible certificar la sucursal, "
			+ "no fue posible actualizar la B.D.";
	private CertificacionDao certificacionDao;

	private static MailServiceCertificacion mail = new MailServiceCertificacion();

	/**
	 * Constructor de la clase
	 * 
	 * @param certificacionDao objeto Dao
	 */
	public CertificacionModificacionesImpl(CertificacionDao certificacionDao) {
		super();
		this.certificacionDao = certificacionDao;
	}

	public static void setMail(MailServiceCertificacion mail) {
		CertificacionModificacionesImpl.mail = mail;
	}

	/**
	 * Metodo para actualizar un cuestionario
	 * 
	 * @param reqActualizaCuestionario - request con los datos a actualizar
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi actualizaCuestionario(BeanCuestionarios reqActualizaCuestionario) {
		ResponseApi resultado = null;
		Predicate<String> validacionId = Validaciones.validaID();
		Predicate<String> validacionCampo = Validaciones.validaCampo();
		LocalDateTime currentDateTime = LocalDateTime.now();

		if (validacionId.test(reqActualizaCuestionario.getId())
				&& validacionCampo.test(reqActualizaCuestionario.getTitulo().getTexto())) {

			reqActualizaCuestionario.setFechaRegistro(currentDateTime.toString());

			if (certificacionDao.actualizaCuestionario(reqActualizaCuestionario)
					.getModifiedCount() > 0) {

				BeanCuestionariosBitacora beanCuestionariosBitacora = new BeanCuestionariosBitacora();
				beanCuestionariosBitacora.setEstatus(reqActualizaCuestionario.isEstatus());
				beanCuestionariosBitacora
						.setFechaRegistro(reqActualizaCuestionario.getFechaRegistro());
				beanCuestionariosBitacora.setIdCuestionario(reqActualizaCuestionario.getId());
				beanCuestionariosBitacora.setPreguntas(reqActualizaCuestionario.getPreguntas());
				beanCuestionariosBitacora.setSucursales(reqActualizaCuestionario.getSucursales());
				beanCuestionariosBitacora.setTitulo(reqActualizaCuestionario.getTitulo());
				beanCuestionariosBitacora.setEvento("Actualiza Cuestionario");
				certificacionDao.nuevoCuestionarioBitacora(beanCuestionariosBitacora);

				resultado = new ResponseApi(EstatusResponse.OK);
			}
			else {
				LogApp.mensaje("No fue posible actualizar el cuestionario en la B.D. id "
						+ reqActualizaCuestionario.getId() + " no encontrado");
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList("No se actualizaron registros en la B.D."));
			}
		}
		else {
			LogApp.mensaje(
					"No fue posible actualizar el cuestionario, datos no válidos, favor de revisar la solicitud");
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA, Arrays.asList(
					"No fue posible actualizar el cuestionario, datos no válidos, favor de revisar la solicitud"));
		}

		return resultado;

	}

	/**
	 * Metodo para actualizar el estatus de un cuestionario
	 * 
	 * @param id - id del cuestionario
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi actualizaEstatusCuestionario(String id) {
		ResponseApi resultado = null;
		Predicate<String> validacionId = Validaciones.validaID();

		if (validacionId.test(id)) {
			BeanCuestionarios consultarCuestionarios = certificacionDao.consultaCuestionario(id);

			if (null != consultarCuestionarios
					&& !consultarCuestionarios.getPreguntas().isEmpty()) {

				RespActualizaEstatusCuestionario respActualizaEstatusCuestionario = new RespActualizaEstatusCuestionario();

				respActualizaEstatusCuestionario.setEstatus(!consultarCuestionarios.isEstatus());
				respActualizaEstatusCuestionario.setRespConsulta(certificacionDao
						.actualizaEstatusCuestionario(id, !consultarCuestionarios.isEstatus()));

				if (respActualizaEstatusCuestionario.getRespConsulta().wasAcknowledged()
						&& respActualizaEstatusCuestionario.getRespConsulta()
								.getModifiedCount() > 0) {

					BeanCuestionariosBitacora beanCuestionariosBitacora = new BeanCuestionariosBitacora();
					beanCuestionariosBitacora.setIdCuestionario(id);
					beanCuestionariosBitacora
							.setEstatus(respActualizaEstatusCuestionario.isEstatus());
					beanCuestionariosBitacora.setEvento("Actualiza Estatus Cuestionario");
					certificacionDao.nuevoCuestionarioBitacora(beanCuestionariosBitacora);
					Document respuestaEstatus = new Document();
					respuestaEstatus.append("estatus", beanCuestionariosBitacora.isEstatus());
					resultado = new ResponseApi(EstatusResponse.OK, respuestaEstatus);
				}
				else {
					LogApp.mensaje(
							"No fue posible actualizar el estatus del cuestionario en la B.D. id no encontrado");
					resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(
							"No fue posible actualizar el estatus del cuestionario en la B.D. id no encontrado"));
				}
			}
			else {
				LogApp.mensaje(
						"No fue posible actualizar el estatus del cuestionario en la B.D. no se encontró el id "
								+ id);
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(
						"No fue posible actualizar el estatus del cuestionario en la B.D. no se encontró el id "
								+ id));
			}
		}
		else {
			LogApp.mensaje(
					"No fue posible actualizar el estatus del cuestionario, datos no válidos, favor de revisar la solicitud");
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA, Arrays.asList(
					"No fue posible actualizar el estatus del cuestionario, datos no válidos, favor de revisar la solicitud"));
		}

		return resultado;
	}

	/**
	 * Metodo para asignar sucursales a un cuestionario
	 * 
	 * @param reqAsignarSucursales - request de la peticion
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	public ResponseApi asignarSucursales(ReqAsignarSucursales reqAsignarSucursales) {
		Predicate<String> validacionId = Validaciones.validaID();
		Predicate<List<String>> validacionSucursales = Validaciones.validaCampoSucursal();
		boolean success = true;
		List<String> errorMessages = new ArrayList<>();
		UpdateResult resp = null;

		if (!validacionId.test(reqAsignarSucursales.getId())
				|| !validacionSucursales.test(reqAsignarSucursales.getSucursales())) {
			LogApp.mensaje(
					"No fue posible asignar sucursales al cuestionario, datos no válidos, favor de revisar la solicitud");
			success = false;
			errorMessages.add(
					"No fue posible asignar sucursales al cuestionario, datos no válidos, favor de revisar la solicitud");
		}

		if (success) {
			resp = certificacionDao.asignarSucursales(reqAsignarSucursales);

			if ((resp == null || !resp.wasAcknowledged())) {
				LogApp.mensaje(
						"No fue posible asignar sucursales al cuestionario, id no encontrado");
				success = false;
				errorMessages
						.add("No fue posible asignar sucursales al cuestionario, id no encontrado");
			}
		}

		if (success && resp.getModifiedCount() == 0) {
			LogApp.mensaje("No se realizó ninguna modificación a la B.D.");
			success = false;
			errorMessages.add("No se realizó ninguna modificación a la B.D.");
		}

		if (success) {
			guardarCuestionarioBitacora(reqAsignarSucursales);
			eliminarRespuestasCuestionario(reqAsignarSucursales);
			guardarRespuestasCuestionario(reqAsignarSucursales);
			return new ResponseApi(EstatusResponse.OK);
		}
		else {
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, errorMessages);
		}
	}

	private void guardarCuestionarioBitacora(ReqAsignarSucursales reqAsignarSucursales) {
		BeanCuestionariosBitacora beanCuestionariosBitacora = new BeanCuestionariosBitacora();
		beanCuestionariosBitacora.setIdCuestionario(reqAsignarSucursales.getId());
		beanCuestionariosBitacora.setSucursales(reqAsignarSucursales.getSucursales());
		beanCuestionariosBitacora.setEvento("Asigna Cuestionario");
		certificacionDao.nuevoCuestionarioBitacora(beanCuestionariosBitacora);
	}

	private void eliminarRespuestasCuestionario(ReqAsignarSucursales reqAsignarSucursales) {
		certificacionDao.eliminaRespuestasCuestionario(reqAsignarSucursales.getId());
	}

	private void guardarRespuestasCuestionario(ReqAsignarSucursales reqAsignarSucursales) {
		for (String sucursal : reqAsignarSucursales.getSucursales()) {
			BeanRespuestasCuestionario beanRespuestasCuestionario = new BeanRespuestasCuestionario();
			beanRespuestasCuestionario
					.setIdCuestionario(new ObjectId(reqAsignarSucursales.getId()));
			beanRespuestasCuestionario.setSucursal(sucursal);
			certificacionDao.nuevoRespuestasCuestionario(beanRespuestasCuestionario);
		}
	}

	/**
	 * Metodo para certificar una sucursal
	 * 
	 * @param sucursal - sucursal a certificar
	 * 
	 * @return objeto ResponseApi con el resultado del servicio
	 */
	public ResponseApi certificaSucursal(String sucursal) {
		Predicate<List<String>> validaTexto = Validaciones.validaCampoSucursal();

		if (!validaTexto.test(Arrays.asList(sucursal))) {
			LogApp.mensaje(ERRORCERTIFICACION1);
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(ERRORCERTIFICACION1));
		}

		ResponseApi respuesta = null;
		ReqRespuestasSucursal reqRespuestasSucursal = new ReqRespuestasSucursal();
		reqRespuestasSucursal.setSucursal(sucursal);
		reqRespuestasSucursal.setElementosPagina(DIEZMIL);
		reqRespuestasSucursal.setPagina(1);
		boolean error = false;

		AggregateIterable<Document> result = certificacionDao
				.consultaRespuestasSucursal(reqRespuestasSucursal);
		ConsultaRespuestasSucursal respAvance = generarConsultaRespuestaSucursal(result);

		if (respAvance == null) {
			LogApp.mensaje(ERRORCERTIFICACION);
			respuesta = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(ERRORCERTIFICACION));
			error = true;
		}
		else if (respAvance.getAvanceTotal() != CIEN) {
			LogApp.mensaje(ERRORCERTIFICACION2);
			respuesta = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(ERRORCERTIFICACION2));
			error = true;
		}
		else {
			if (certificacionDao.certificaSucursal(sucursal).getModifiedCount() <= 0) {
				LogApp.mensaje(ERRORCERTIFICACION3);
				respuesta = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList(ERRORCERTIFICACION3));
				error = true;
			}
		}
		if (error) {
			return respuesta;
		}
		else {
			BeanSucursales datosSucursal = certificacionDao.consultaSucursal(sucursal);
			return procesarCertificacionSucursal(sucursal, respAvance, datosSucursal,
					certificacionDao, mail);
		}

	}

}
