package com.baz.divisas.api.core.beneficios.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.beneficios.dao.CuponesBeneficiosDao;
import com.baz.divisas.api.core.beneficios.model.CuponAlianzas;
import com.baz.divisas.api.core.beneficios.model.CuponCRM;
import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.baz.divisas.api.core.clientes.model.Registros;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

/**
 * The Class CuponesTiempoAireImpl.
 */
@Service
public class CuponesBeneficiosImpl {

	/** The cupones dao. */
	private CuponesBeneficiosDao cuponesDao;	
	/**
	 * Instantiates a new cupones tiempo aire impl.
	 *
	 * @param cuponesDao the cupones dao
	 */
	@Autowired
	public CuponesBeneficiosImpl(CuponesBeneficiosDao cuponesDao) {
		super();
		this.cuponesDao = cuponesDao;
	}
	
	/**
	 * Consulta carga historica.
	 *
	 * @return the response api
	 */
	public ResponseApi consultaCargaHistorica() {
		ResponseApi response;

		List<HistoricoAltoRiesgo> historico = cuponesDao.consultaCargaHistorica();

		if (!historico.isEmpty()) {

			response = new ResponseApi(EstatusResponse.OK, historico);

		} 
		else {
			response = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return response;
	}

	/**
	 * Registra cupones TA.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @return the response api
	 */
	public ResponseApi registraCuponesTA(MultipartFile archivo, String nombre, String descripcion) {	
			return new ResponseApi(EstatusResponse.OK, Arrays.asList(""));
	}

	/**
	 * Registra cupones CRM.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @return the response api
	 */
	public ResponseApi registraCuponesCRM(MultipartFile archivo, String nombre, String descripcion, String fechaInicio, String fechaFin) {
		ResponseApi resp = null;			
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

			String linea;
			List<CuponCRM> cuponesValidos = new ArrayList<>();
			List<String> registrosErroneos = new ArrayList<>();
			while ((linea = br.readLine()) != null) {
				try {
					if (!linea.isBlank()) {
						String[] columnas = linea.split(",", -1);

						// Validar Reglas de carga del archivo
						CuponCRM cupon = new CuponCRM();						
						cupon.setIdCliente(columnas[0].toString().trim());
						cupon.setId(columnas[1].toString().toUpperCase().trim());
						cupon.setFechaInicio(fechaInicio);
						cupon.setFechaFin(fechaFin);
						if(validaFormatoCuponCRM(cupon)) {
							cuponesValidos.add(cupon);
						}
						else {
							registrosErroneos.add(sanearLinea(cupon.getId()));
						}						
					}
				} 
				catch (Exception e) {
					LogApp.excepcion(e, "Cupon incorrecto : " + linea);
					registrosErroneos.add(sanearLinea(linea));
				}
			}
		
			int cuponRegistrados = 0;
			if (!cuponesValidos.isEmpty()) {
				cuponRegistrados = cuponesDao.registraCuponesCRM(cuponesValidos);
			}
		
		
			if(cuponRegistrados > 0) {
			
				cuponesDao.registraBitacoraCuponesCRM(nombre, descripcion, cuponRegistrados, fechaInicio,fechaFin);
			}
			
			Registros registro = new Registros();
			registro.setRegistrosExitosos(cuponRegistrados);
			registro.setRegistrosErroneos(registrosErroneos);
			return new ResponseApi(EstatusResponse.OK, Arrays.asList(registro));

		} 
		catch (Exception  e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			resp = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
		}

		return resp;
	}

	/**
	 * Consulta carga historica CRM.
	 *
	 * @return the response api
	 */
	public ResponseApi consultaCargaHistoricaCRM() {
		ResponseApi response;

		List<HistoricoAltoRiesgo> historico = cuponesDao.consultaCargaHistoricaCRM();

		if (!historico.isEmpty()) {

			response = new ResponseApi(EstatusResponse.OK, historico);

		} 
		else {
			response = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return response;
	}


	/** Longitud maxima permitida para una linea reflejada como registro erroneo. */
	private static final int MAX_LONGITUD_LINEA_ERRONEA = 200;

	/**
	 * Sanea una linea del archivo de carga antes de reflejarla en la lista de
	 * registros erroneos de la respuesta: quita caracteres de control y acota
	 * su longitud. El contenido proviene del propio archivo subido por el
	 * usuario autenticado de la peticion (no es dato de un tercero), pero se
	 * sanea como defensa en profundidad antes de incluirlo en la respuesta.
	 *
	 * @param linea - Linea de entrada (puede ser null)
	 * @return Linea saneada, o cadena vacia si el valor de entrada era null
	 */
	private static String sanearLinea(String linea) {

		if (linea == null) {
			return "";
		}

		String limpia = linea.replaceAll("[\\x00-\\x1F\\x7F]", "");

		if (limpia.length() > MAX_LONGITUD_LINEA_ERRONEA) {
			limpia = limpia.substring(0, MAX_LONGITUD_LINEA_ERRONEA);
		}

		return limpia;
	}

	/**
	 * Valida formato cupon.
	 *
	 * @param cupon the cupon
	 * @return true, if successful
	 */
	private boolean validaFormatoCuponCRM(CuponCRM cupon) {
		boolean valido = false;
		
		String regexCupon = "^[a-zA-ZñÑ0-9]{5,5}$";
		if (cupon.getId().matches(regexCupon)) {
		
			valido = true;
		}
		return valido;
	}
	
	/**
	 * Valida formato cupon alianzas.
	 *
	 * @param cupon the cupon
	 * @return true, if successful
	 */
	private boolean validaFormatoCuponAlianzas(CuponAlianzas cupon) {
		boolean valido = false;
		
		String regexCupon = "^[a-zA-ZñÑ0-9]{5,5}$";
		if (cupon.getId().matches(regexCupon)) {
		
			valido = true;
		}
		return valido;
	}

	/**
	 * Registra cupones Mas Salud.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param tipo the tipo
	 * @return the response api
	 */
	public ResponseApi registraCuponesMasSalud(MultipartFile archivo, String nombre, String descripcion, String tipo) {
		ResponseApi resp = null;
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

			String linea;
			String regexCupon = "^[a-zA-ZñÑ0-9]{1,20}$";
			List<CuponMasSalud> cuponesValidos = new ArrayList<>();
			List<String> registrosErroneos = new ArrayList<>();
			
			while ((linea = br.readLine()) != null) {
				try {
					if (!linea.isBlank()) {
						String cuponId = linea.trim();
						// Validar formato del cupon
						if (cuponId.matches(regexCupon)) {
							CuponMasSalud cupon = new CuponMasSalud();
							cupon.setId(cuponId);
							cupon.setTipo(tipo);
							cuponesValidos.add(cupon);
						}
						else {
							registrosErroneos.add(sanearLinea(linea));
						}
					}
				} 
				catch (Exception e) {
					LogApp.excepcion(e, "Cupon Mas Salud incorrecto : " + linea);
					registrosErroneos.add(sanearLinea(linea));
				}
			}
			
			int cuponRegistrados = 0;
			if (!cuponesValidos.isEmpty()) {
				
				cuponRegistrados = cuponesDao.registraCuponesMasSaludBulk(cuponesValidos);
			}
		
			if(cuponRegistrados > 0) {
			
				cuponesDao.registraBitacoraCuponesMasSalud(nombre, descripcion, tipo, cuponRegistrados);
			}
			
			Registros registro = new Registros();
			registro.setRegistrosExitosos(cuponRegistrados);
			registro.setRegistrosErroneos(registrosErroneos);
			return new ResponseApi(EstatusResponse.OK, Arrays.asList(registro));
		} 
		catch (Exception  e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			resp = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
		}

		return resp;
	}

	/**
	 * Consulta carga historica Mas Salud.
	 *
	 * @return the response api
	 */
	public ResponseApi consultaCargaHistoricaMasSalud(String tipo) {
		ResponseApi response;

		List<HistoricoAltoRiesgo> historico = cuponesDao.consultaCargaHistoricaMasSalud(tipo);

		if (!historico.isEmpty()) {

			response = new ResponseApi(EstatusResponse.OK, historico);

		} 
		else {
			
			response = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return response;
	}

	/**
	 * Registra cupones alianzas.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @return the response api
	 */
	public ResponseApi registraCuponesAlianzas(MultipartFile archivo, String nombre, String descripcion,
			String fechaInicio, String fechaFin) {
		ResponseApi resp = null;			
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

			String linea;
			List<CuponAlianzas> cuponesValidos = new ArrayList<>();
			List<String> registrosErroneos = new ArrayList<>();
			while ((linea = br.readLine()) != null) {
				try {
					if (!linea.isBlank()) {
						String[] columnas = linea.split(",", -1);

						// Validar Reglas de carga del archivo
						CuponAlianzas cupon = new CuponAlianzas();						
						cupon.setAlianza(columnas[0].toString().trim());
						cupon.setId(columnas[1].toString().toUpperCase().trim());
						cupon.setFechaVigencia(fechaFin);
						if(validaFormatoCuponAlianzas(cupon)) {
							cuponesValidos.add(cupon);
						}
						else {
							registrosErroneos.add(sanearLinea(cupon.getId()));
						}						
					}
				} 
				catch (Exception e) {
					LogApp.excepcion(e, "Cupon incorrecto : " + linea);
					registrosErroneos.add(sanearLinea(linea));
				}
			}
		
			int cuponRegistrados = 0;
			if (!cuponesValidos.isEmpty()) {
				cuponRegistrados = cuponesDao.registraCuponesAlianzas(cuponesValidos);
			}
		
		
			if(cuponRegistrados > 0) {
			
				cuponesDao.registraBitacoraCuponesAlianzas(nombre, descripcion, cuponRegistrados, fechaInicio,fechaFin);
			}
			
			Registros registro = new Registros();
			registro.setRegistrosExitosos(cuponRegistrados);
			registro.setRegistrosErroneos(registrosErroneos);
			return new ResponseApi(EstatusResponse.OK, Arrays.asList(registro));

		} 
		catch (Exception  e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			resp = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
		}

		return resp;
	}

	/**
	 * Consulta carga historica alianzas.
	 *
	 * @return the response api
	 */
	public ResponseApi consultaCargaHistoricaAlianzas() {
		ResponseApi response;

		List<HistoricoAltoRiesgo> historico = cuponesDao.consultaCargaHistoricaAlianzas();

		if (!historico.isEmpty()) {

			response = new ResponseApi(EstatusResponse.OK, historico);

		} 
		else {
			response = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return response;
	}
}
