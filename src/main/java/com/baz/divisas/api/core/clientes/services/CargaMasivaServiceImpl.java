package com.baz.divisas.api.core.clientes.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.clientes.dao.ClientesDao;
import com.baz.divisas.api.core.clientes.model.BitacoraCargaMasiva;
import com.baz.divisas.api.core.clientes.util.ArchivoCargaMasiva;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;


/**
 * Servicio de Clientes
 * @author B813196
 * itoxtle
 */
@Service
public class CargaMasivaServiceImpl {
	
	private static final int MAX_ROWS_FILE = 50000;
		
	private ClientesDao clientesDao;
	private ArchivoCargaMasiva archivoCargaMasivaService;
	

	/**
	 * Constructor de la clase
	 * @param archivoCargaMasivaService - Carga Masiva Service
	 * @param clientesDao - DAO Clientes
	 */
	@Autowired
	public CargaMasivaServiceImpl(ClientesDao clientesDao, ArchivoCargaMasiva archivoCargaMasivaService) {
		super();
		this.clientesDao = clientesDao;
		this.archivoCargaMasivaService = archivoCargaMasivaService;
	}

	
	/**
	 * Metodo de carga archivo de actualizacion masiva
	 * @param file - Archivo de actualizacion masiva
	 * @return ResponseApi
	 */
	public ResponseApi cargaArchivoActualizacionMasiva(MultipartFile file) {

		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);

		try(InputStream is = file.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr)) {

			int totalLineas = 0;
			
			List<String> lineas = br.lines().collect(Collectors.toList());
			
			//Se obtienen las lineas en el archivo
			totalLineas = lineas.size();
			
			if(totalLineas > 1 && lineas.size() < MAX_ROWS_FILE) {
				
				// Se elimina el cabecero del archivo
				lineas.remove(0);

				// Se procesa el archivo cargado
				archivoCargaMasivaService.procesar(file.getOriginalFilename(), lineas);
								
			}
			else {
				
				if(lineas.isEmpty()) {
					resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
							Arrays.asList("El archivo no puede estar vacío"));
				}
				else {
					resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
							Arrays.asList("Se ha excedido la cantidad de registros permitidos: "+ MAX_ROWS_FILE));
				}
				
			}

		} 
		catch (IOException e) {
			LogApp.excepcion(e, "Ocurrio un error al leer el archivo");
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Ocurrio un error al leer el archivo", e));
		}

		return resultado;

	}
	
	
	/**
	 * Metodo de consulta de Bitacora de Actualizaciones del Cliente
	 * @param idCliente - Id de Cliente
	 * @return Registros de la bitacora
	 */
	public ResponseApi consultaBitacoraCargaMasiva() {
		
		ResponseApi resultado;
		
		List<BitacoraCargaMasiva> bitacora = clientesDao.findBitacoraCargaMasiva();
		
		if(!bitacora.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, bitacora);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo para generar reporte de errores de carga masiva
	 * @param idCarga - Identificador de carga
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream reporteErroresCargaMasiva(String idCarga) {

		BitacoraCargaMasiva bitacora = clientesDao.findCargaMasivaById(idCarga);

		return archivoCargaMasivaService.generaReporteExcelErrores(bitacora);

	}

}
