package com.baz.divisas.api.core.reporteria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.core.reporteria.dto.ReqBusquedaIncidencias;
import com.baz.divisas.api.core.reporteria.dto.ReqReporteOperaciones;
import com.baz.divisas.api.core.reporteria.dto.ReqReporteRascadito;
import com.baz.divisas.api.core.reporteria.services.ReporteriaServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase ReporteriaController.
 *
 * @author 1027346 Martinez Yonathan
 */
@RestController
@RequestMapping("/api/core/reporteria")
@Api(tags = "Core/Reporteria")
@Validated
public class ReporteriaController {

	/** Service Reporteria. */
	@Autowired
	private ReporteriaServiceImpl reporteriaService;

	/**
	 * Constructor de la clase.
	 *
	 * @param reporteriaService - Reporteria Service
	 */
	public ReporteriaController(ReporteriaServiceImpl reporteriaService) {
		super();
		this.reporteriaService = reporteriaService;
	}

	/**
	 * Metodo que devuelve incidencias en listas negras
	 * 
	 * Obtiene todas las inicdencias registradas.
	 *
	 * @param request - incidencias
	 * @return lista de incidencias
	 */
	@PostMapping(value = "incidencias")
	@ApiOperation(value = "Obtiene el reporte de incidencias operativas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getIncidencias(@Valid @RequestBody ReqBusquedaIncidencias request) {

		ResponseApi resultado = reporteriaService.getIncidenciasImpl(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que devuelve incidencias en listas negras
	 * 
	 * Obtiene todas las inicdencias registradas.
	 *
	 * @param id - incidencias
	 * @return lista de incidencias
	 */
	@GetMapping(value = "incidencias/{id}")
	@ApiOperation(value = "Obtiene el detalle de la incidencia")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getIncidencia(@PathVariable		  
		  	 @Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
 			 String id) {

		ResponseApi resultado = reporteriaService.getIncidenciaImpl(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que descarga incidencias en archivo excel
	 * 
	 * Descarga incidencias.
	 *
	 * @param request - incidencias
	 * @return lista de incidencias
	 */
	@PostMapping(value = "incidencias/descarga")
	@ApiOperation(value = "Descarga el reporte de incidencias operativas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaIncidencias(@Valid @RequestBody ReqBusquedaIncidencias request) {

		InputStreamResource file = new InputStreamResource(reporteriaService.descargaIncidenciasImpl(request));
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.FORMATOFECHACORTA);
		String nombreReporte = "Reporte_Incidencias_" + dtf.format(LocalDateTime.now()) + ".xlsx";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

	}
	
	/**
	 * Metodo de Consulta de Operaciones.
	 *
	 * @param request - ReqReporteOperaciones
	 * @return Reporte de Operaciones
	 */
	@PostMapping(value = "operaciones")
	@ApiOperation(value = "Obtiene reportes de las operaciones de divisas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getOperaciones(@Valid @RequestBody ReqReporteOperaciones request) {

		ResponseApi resultado = reporteriaService.getOperacionesImpl(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	
	/**
	 * Metodo de Descarga de Operaciones.
	 *
	 * @param request - ReqReporteOperaciones
	 * @return Reporte de Operaciones
	 */
	@PostMapping(value = "operaciones/descarga")
	@ApiOperation(value = "Descarga las operaciones de divisas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaOperaciones(@Valid @RequestBody ReqReporteOperaciones request) {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaOperacionesImpl(request));
				
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ReporteOperaciones.xls")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	/**
	 * Metodo de Consulta de Acumulado Operaciones.
	 *
	 * @param request - ReqReporteOperaciones
	 * @return Reporte de Operaciones
	 */
	@PostMapping(value = "operaciones/acumulado")
	@ApiOperation(value = "Obtiene acumulado de las operaciones de divisas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getAcumuladoOperaciones(@Valid @RequestBody ReqReporteOperaciones request) {

		ResponseApi resultado = reporteriaService.getAcumuladoOperacionesImpl(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	
	/**
	 * Metodo de Descarga de Ganadores Rascadito Digital.
	 *
	 * @param request - ReqReporteOperaciones
	 * @return Reporte de Ganadores
	 */
	@PostMapping(value = "ganadores-rascadito/descarga")
	@ApiOperation(value = "Descarga ganadores de Rascadito Digital")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaGanadoresRascadito(@Valid @RequestBody ReqReporteRascadito request) {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaGanadoresRascaditoImpl(request));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ReporteRascadito.xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	/**
	 * Descarga gol de oro.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "gol-de-oro")
	@ApiOperation(value = "Descarga ganadores de Gol de Oro")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaGolDeOro() {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaGanadoresGolDeOroImpl());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=GolDeOro.csv")
				.contentType(MediaType.parseMediaType("text/csv")).body(file);
	}
	
	/**
	 * Descarga inventario mundial verde.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "inventario-mundial-verde")
	@ApiOperation(value = "Descarga inventario de mundial verde")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaInventarioMundialVerde() {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaInventarioMundialVerdeImpl());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.FORMATOFECHACORTA);
		String nombreReporte = "InventarioMundialVerde_" + dtf.format(LocalDateTime.now()) + ".xlsx";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+nombreReporte)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	

	/**
	 * Descarga operaciones mundial verde.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "verano-mundialista")
	@ApiOperation(value = "Descarga ganadores de Verano Mundialista")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaOperacionesMundialVerde() {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaGanadoresVeranoMundialista());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=VeranoMundialista.csv")
				.contentType(MediaType.parseMediaType("text/csv")).body(file);
	}
	

	/**
	 * Descarga inventario verano mundialista.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "inventario-verano-mundialista")
	@ApiOperation(value = "Descarga inventario de Verano Mundialista")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaInventarioVeranoMundialista() {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaInventarioVeranoMundialistaImpl());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.FORMATOFECHACORTA);
		String nombreReporte = "InventarioVeranoMundialista_" + dtf.format(LocalDateTime.now()) + ".csv";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+nombreReporte)
				.contentType(MediaType.parseMediaType("text/csv")).body(file);
	}
	
	/**
	 * Descarga entregas cupones Mas Salud.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "cupones-mas-salud/descarga")
	@ApiOperation(value = "Descarga entregas de cupones Mas Salud")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaCuponesMasSalud() {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaCuponesMasSaludImpl());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.FORMATOFECHACORTA);
		String nombreReporte = "CuponesMasSalud_" + dtf.format(LocalDateTime.now()) + ".xlsx";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	
	
	/**
	 * Descarga operaciones costalsanta.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "costal-santa")
	@ApiOperation(value = "Descarga ganadores de Costal de Santa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaOperacionesCostalsanta() {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaGanadoresCostalSanta());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CostalDeSanta.csv")
				.contentType(MediaType.parseMediaType("text/csv")).body(file);
	}
	

	
	/**
	 * Descarga inventario costal santa.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "inventario-costal-santa")
	@ApiOperation(value = "Descarga inventario de Verano Mundialista")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaInventarioCostalSanta() {
		
		InputStreamResource file = new InputStreamResource(reporteriaService.descargaInventarioCostalSanta());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.FORMATOFECHACORTA);
		String nombreReporte = "InventarioCostalDeSanta_" + dtf.format(LocalDateTime.now()) + ".csv";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+nombreReporte)
				.contentType(MediaType.parseMediaType("text/csv")).body(file);
	}
}
