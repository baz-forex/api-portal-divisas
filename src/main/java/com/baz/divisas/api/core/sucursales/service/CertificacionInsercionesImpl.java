package com.baz.divisas.api.core.sucursales.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionariosBitacora;
import com.baz.divisas.api.core.sucursales.model.Pregunta;
import com.baz.divisas.api.core.sucursales.utils.LectorArchivoExcel;
import com.baz.divisas.api.core.sucursales.utils.Validaciones;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

import java.util.function.Predicate;

/**
 * Clase CertificacionInsercionesImplTest
 * 
 * @author 1096526
 *
 */
@Service
public class CertificacionInsercionesImpl {

	private CertificacionDao certificacionDao;

	/**
	 * Constructor de la clase
	 * 
	 * @param certificacionDao - objeto dao
	 */
	@Autowired
	public CertificacionInsercionesImpl(CertificacionDao certificacionDao) {
		super();
		this.certificacionDao = certificacionDao;
	}

	/**
	 * Metodo para insertar un nuevo Cuestionario
	 * 
	 * @param reqNuevoCuestionario - request con los datos del nuevo cuestionario
	 * 
	 * @return boolean con el resultado de la insercion
	 */
	public ResponseApi creaCuestionario(BeanCuestionarios reqNuevoCuestionario) {
		ResponseApi resultado = null;
		LocalDateTime currentDateTime = LocalDateTime.now();
		Predicate<String> validaTexto = Validaciones.validaCampo();

		if (null != reqNuevoCuestionario.getPreguntas()
				&& !reqNuevoCuestionario.getPreguntas().isEmpty()) {

			for (Pregunta pregunta : reqNuevoCuestionario.getPreguntas()) {
				if (null == pregunta.getTexto() || !validaTexto.test(pregunta.getTexto())) {
					LogApp.mensaje(
							"Datos no válidos, favor de revisar el campo texto, de las preguntas");
					return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, Arrays.asList(
							"Datos no válidos, favor de revisar el campo texto, de las preguntas"));
				}
			}

			// Se setean los campos estatus y fechaRegistro
			reqNuevoCuestionario.setEstatus(false);
			reqNuevoCuestionario.setFechaRegistro(currentDateTime.toString());

			if (certificacionDao.nuevoCuestionario(reqNuevoCuestionario).wasAcknowledged()) {
				LogApp.mensaje(
						"Cuestionario insertado correctamente" + reqNuevoCuestionario.toString());

				BeanCuestionariosBitacora beanCuestionariosBitacora = new BeanCuestionariosBitacora();
				beanCuestionariosBitacora.setEstatus(reqNuevoCuestionario.isEstatus());
				beanCuestionariosBitacora.setFechaRegistro(reqNuevoCuestionario.getFechaRegistro());
				beanCuestionariosBitacora.setIdCuestionario(reqNuevoCuestionario.getId());
				beanCuestionariosBitacora.setPreguntas(reqNuevoCuestionario.getPreguntas());
				beanCuestionariosBitacora.setSucursales(reqNuevoCuestionario.getSucursales());
				beanCuestionariosBitacora.setTitulo(reqNuevoCuestionario.getTitulo());
				beanCuestionariosBitacora.setEvento("Nuevo Cuestionario");
				certificacionDao.nuevoCuestionarioBitacora(beanCuestionariosBitacora);

				resultado = new ResponseApi(EstatusResponse.OK);
			}
			else {
				LogApp.mensaje("No fue posible insertar el cuestionario a la B.D.");
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList("No fue posible insertar el cuestionario a la B.D."));
			}
		}
		else {
			LogApp.mensaje("No hay preguntas, favor de revisar la solicitud");
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No hay preguntas, favor de revisar la solicitud"));
		}

		return resultado;
	}

	/**
	 * Metodo de carga masiva con archivo xlsx
	 * 
	 * @param file - Archivo de actualizacion masiva con formato xlsx
	 * 
	 * @return ResponseApi
	 */
	public ResponseApi servicioCargaMasiva(MultipartFile file) {

		LectorArchivoExcel lectorExcel = new LectorArchivoExcel(certificacionDao);
		return lectorExcel.readDataFromColumns(file);

	}

	/**
	 * Metodo para obtener una lista de Enteros
	 * 
	 * @param input - Lista separada por comas
	 * 
	 * @return objeto tipo List con los enteros obtenidos
	 */
	public static List<Integer> parseIntegerList(String input) {
		List<Integer> result = new ArrayList<>();
		String[] strArray = input.split(",");
		for (String str : strArray) {
			result.add(Integer.parseInt(str));
		}
		return result;
	}

}
