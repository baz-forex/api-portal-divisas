package com.baz.divisas.api.digital.reporteria;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.digital.reporteria.dto.ReqActualizaEstatus;
import com.baz.divisas.api.digital.reporteria.dto.ReqBusqOperacionesApp;
import com.baz.divisas.api.digital.reporteria.services.ReporteriaServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase ReporteriaController
 * 
 * @author itoxtle
 *
 */
@RestController("ReporteriaDigitalController")
@RequestMapping("/api/digital/reporteria")
@Api(tags = "Digital/Reporteria")
@Validated
public class ReporteriaController {

	/**
	 * Service PerfilDeClientes
	 */
	@Autowired
	private ReporteriaServiceImpl reporteriaService;

	/**
	 * Constructor de la clase
	 * 
	 * @param reporteriaService - Reporteria Service
	 */
	public ReporteriaController(ReporteriaServiceImpl reporteriaService) {
		super();
		this.reporteriaService = reporteriaService;
	}

	/**
	 * Metodo de consulta de Operaciones App
	 * 
	 * @param request - ReqBusqOperacionesApp
	 * @return Lista de Operaciones
	 */
	@PostMapping(value = "operaciones")
	@ApiOperation(value = "Obtiene las operaciones realizadas desde App")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getOperacionesApp(@Valid @RequestBody ReqBusqOperacionesApp request) {

		ResponseApi resultado = reporteriaService.getOperacionesAppImpl(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	

	/**
	 * Metodo de descarga de Operaciones App
	 * 
	 * @param request - ReqBusqOperacionesApp
	 * @return Reporte de Operaciones
	 */
	@PostMapping(value = "operaciones/descarga")
	@ApiOperation(value = "Descarga las operaciones realizadas desde App")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaOperacionesApp(@Valid @RequestBody ReqBusqOperacionesApp request) {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaOperacionesAppImpl(request));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=OperacionesApp.xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	
	/**
	 * Metodo de consulta de Operaciones App
	 * 
	 * @return Lista de Operaciones
	 */
	@GetMapping(value = "operaciones/incidencias")
	@ApiOperation(value = "Obtiene las operaciones con incidencia en App")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getIncidenciasApp() {

		ResponseApi resultado = reporteriaService.getIncidenciasAppImpl();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Metodo de Actualizacion de Estatus de la Cotizacion.
	 *
	 * @param request - ReqActualizaEstatus
	 * @return ResponseApi
	 */
	@PutMapping(value = "operaciones/incidencias/cambia-estatus")
	@ApiOperation(value = "Actualiza estatus de una operacion")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaCotizacion(@Valid @RequestBody ReqActualizaEstatus request) {

		ResponseApi resultado = reporteriaService.actualizaCotizacionImpl(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

}
