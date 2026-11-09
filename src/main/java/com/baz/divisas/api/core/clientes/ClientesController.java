package com.baz.divisas.api.core.clientes;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.core.clientes.dto.ReqBloqueoClientes;
import com.baz.divisas.api.core.clientes.dto.ReqBusquedaClientes;
import com.baz.divisas.api.core.clientes.dto.ReqClientesAgrupados;
import com.baz.divisas.api.core.clientes.model.ClienteDetalle;
import com.baz.divisas.api.core.clientes.services.CargaMasivaServiceAltoRiesgo;
import com.baz.divisas.api.core.clientes.services.CargaMasivaServiceImpl;
import com.baz.divisas.api.core.clientes.services.ClientesServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador de Clientes.
 *
 * @author B813196
 * itoxtle
 */
@RestController
@RequestMapping("/api/core/clientes")
@Api(tags = "Core/Clientes")
@Validated
public class ClientesController {

	/** Service Clientes. */
	private ClientesServiceImpl clientesService;

	/** The carga masiva service. */
	private CargaMasivaServiceImpl cargaMasivaService;

	/** The carga masiva al riesgo. */
	private CargaMasivaServiceAltoRiesgo cargaMasivaAlRiesgo;

	/**
	 * Constructor de la clase.
	 *
	 * @param clientesService - Clientes Service
	 * @param cargaMasivaService - Carga Masiva Service
	 * @param cargaMasivaAlRiesgo the carga masiva al riesgo
	 */
	@Autowired
	public ClientesController(ClientesServiceImpl clientesService, CargaMasivaServiceImpl cargaMasivaService,CargaMasivaServiceAltoRiesgo cargaMasivaAlRiesgo) {
		super();
		this.clientesService = clientesService;
		this.cargaMasivaService = cargaMasivaService;
		this.cargaMasivaAlRiesgo = cargaMasivaAlRiesgo;
	}


	/**
	 * Metodo de busqueda de clientes.
	 *
	 * @param request - Parametros de busqueda
	 * @return ResponseApi
	 */
	@PostMapping(value = "busqueda")
	@ApiOperation(value = "Busqueda de clientes")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> busquedaClientes(@Valid @RequestBody ReqBusquedaClientes request) {

		ResponseApi resultado = clientesService.busquedaClientes(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de consulta de cliente por id.
	 *
	 * @param idCliente - Id de Cliente
	 * @return ResponseApi
	 */
	@GetMapping(value = "/{idCliente}")
	@ApiOperation(value = "Busqueda de cliente por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> busquedaCliente(@PathVariable 
			@Pattern(regexp="[0-9]{0,10}", message="El idCliente deben ser numérico de longitud máxima 10")
			String idCliente) {

		ResponseApi resultado = clientesService.busquedaClienteById(idCliente);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}


	/**
	 * Metodo de consulta de cliente por id.
	 *
	 * @param idCliente - Id de Cliente
	 * @return ResponseApi
	 */
	@GetMapping(value = "/{idCliente}/bitacora-actualizacion")
	@ApiOperation(value = "Bitacora de actualizaciones del cliente")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> consultaBitacoraActCliente(@PathVariable 
			@Pattern(regexp="[0-9]{0,10}", message="El idCliente deben ser numérico de longitud máxima 10")
			String idCliente) {

		ResponseApi resultado = clientesService.consultaBitacoraCliente(idCliente);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}


	/**
	 * Metodo de actualizacion de clientes.
	 *
	 * @param cliente - Cliente a actualizar
	 * @return ResponseApi
	 */
	@PutMapping(value = "")
	@ApiOperation(value = "Actualizacion de cliente")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> actualizaCliente(@RequestBody ClienteDetalle cliente) {

		ResponseApi resultado = clientesService.actualizaCliente(cliente);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}


	/**
	 * Metodo de carga archivo de actualizacion masiva.
	 *
	 * @param file - Archivo de actualizacion masiva
	 * @return ResponseApi
	 */
	@PostMapping(value = "actualizacion-masiva")
	@ApiOperation(value = "Carga archivo para actualizacion masiva de clientes")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> cargaArchivoActualizacionMasiva(@RequestParam MultipartFile file) {

		ResponseApi resultado = cargaMasivaService.cargaArchivoActualizacionMasiva(file);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}

	
	/**
	 * Metodo de consulta de cliente por id.
	 *
	 * @return ResponseApi
	 */
	@GetMapping(value = "actualizacion-masiva/bitacora")
	@ApiOperation(value = "Bitacora de carga masiva para actualizacion de clientes")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> consultaBitacoraCargaMasiva() {

		ResponseApi resultado = cargaMasivaService.consultaBitacoraCargaMasiva();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	
	/**
	 * Metodo de reporte de errores de carga masiva.
	 *
	 * @param idCarga - Identificador de la carga masiva
	 * @return ResponseEntity<Resource>
	 */
	@GetMapping(value = "actualizacion-masiva/{idCarga}/errores")
	@ApiOperation(value = "Reporte de errores de carga masiva")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> reporteErroresCargaMasiva(@PathVariable String idCarga) {

		InputStreamResource file = new InputStreamResource(cargaMasivaService.reporteErroresCargaMasiva(idCarga));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Reporte_Errores.xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

	}


	/**
	 * Consulta agrupado clientes.
	 *
	 * @param pagina the pagina
	 * @param registrosPorPagina the registros por pagina
	 * @param idCliente the id cliente
	 * @return the response entity
	 */
	@GetMapping(value = "agrupados")
	@ApiOperation(value = "Consulta agrupados de clientes")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> consultaAgrupadoClientes(
			
			@RequestParam(required = true, defaultValue="1") 
			@Min(value=1, message="Mínimo {value}") int pagina, 
			
			@RequestParam(required = true, defaultValue="50")
			@Max(value=50, message="Máximo {value} registros por página")
			@Min(value=1, message="Mínimo {value} registros por página") int registrosPorPagina,
			
			@RequestParam(required = false, defaultValue="")
			@Pattern(regexp="^[0-9]{0,10}", message="Debe ser un valor numérico, máximo 10 dígitos") String idCliente) {

		ResponseApi resultado = clientesService.consultaAgrupadoClientes(idCliente, pagina, registrosPorPagina);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	
	/**
	 * Metodo de agrupado de clientes.
	 *
	 * @param request - Agrupado de clientes
	 * @return ResponseApi
	 */
	@PostMapping(value = "/agrupados")
	@ApiOperation(value = "Registra Agrupado de Clientes")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> registraAgrupadoClientes(@Validated(Registrar.class)
																@RequestBody ReqClientesAgrupados request) {

		ResponseApi resultado = clientesService.registraAgrupadoClientes(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Actualiza agrupado clientes.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PutMapping(value = "/agrupados")
	@ApiOperation(value = "Actualiza Agrupado de Clientes")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> actualizaAgrupadoClientes(@Validated(Actualizar.class)
																 @RequestBody ReqClientesAgrupados request) {

		ResponseApi resultado = clientesService.actualizaAgrupadoClientes(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}


	/**
	 * Metodo que elimina el agrupado de los clientes.
	 *
	 * @param idGrupo - Id de grupo de clientes a eliminar
	 * @return ResponseApi
	 */
	@DeleteMapping(value = "/agrupados/{idGrupo}")
	@ApiOperation(value = "Elimina agrupados de clientes")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaAgrupadoClientes(@PathVariable 
			@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24") String idGrupo) {

		ResponseApi resultado = clientesService.eliminaAgrupadoClientes(idGrupo);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	
	/**
	 * Consulta bloqueo de clientes.
	 *
	 * @param tipoBloqueo - Tipo de Bloqueo
	 * @return ResponseApi
	 */
	@GetMapping(value = "/bloqueos/{tipoBloqueo}")
	@ApiOperation(value = "Obtiene los bloqueos operativos de clientes")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> consultaBloqueoClientes(@PathVariable 
			@Pattern(regexp="^(1|2|3)$", message="Tipo de Bloqueo invalido")  String tipoBloqueo) {

		ResponseApi resultado = clientesService.consultaBloqueoClientes(tipoBloqueo);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	

	/**
	 * Registra bloqueo de clientes.
	 *
	 * @param request - ReqBloqueoClientes
	 * @return ResponseApi
	 */
	@PostMapping(value = "/bloqueos")
	@ApiOperation(value = "Registra Bloqueo de Clientes")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> registraBloqueoClientes(@Valid @RequestBody ReqBloqueoClientes request) {

		ResponseApi resultado = clientesService.registraBloqueoClientes(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Elimina bloqueo de clientes.
	 *
	 * @param idBloqueo - Id de Bloqueo
	 * @return ResponseApi
	 */
	@DeleteMapping(value = "/bloqueos/{idBloqueo}")
	@ApiOperation(value = "Elimina agrupados de clientes")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaBloqueoClientes(@PathVariable 
			@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24") String idBloqueo) {

		ResponseApi resultado = clientesService.eliminaBloqueoClientes(idBloqueo);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que devuelve el catalogo Tipo de Bloqueo de Cliente.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping(value = "tipos-bloqueo")
	@ApiOperation(value = "Devuelve catalogo de tipos de bloqueo de clientes")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCatTiposBloqueoClientes() {
		
		List<Catalogo> catTiposBloqueo = Arrays.asList(new Catalogo("1", "Cliente"),
				new Catalogo("2","Tipo y folio de identificación"),new Catalogo("3","Compuesto"));
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK, catTiposBloqueo);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	


	/**
	 * Carga clientes alto riesgo.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @return the response entity
	 */
	@PostMapping(value = "carga-clientes/registro")
	@ApiOperation(value = "Carga clientes de alto riesgo")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> cargaClientesAltoRiesgo(@RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam String descripcion) {
		
		ResponseApi resultado = cargaMasivaAlRiesgo.registraClientesAltoRiesgo(archivo , nombre , descripcion);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	

	
	/**
	 * Gets the cargas historicas clientes.
	 *
	 * @return the cargas historicas clientes
	 */
	@GetMapping(value = "carga-clientes/consulta")
	@ApiOperation(value = "Devuelve cargas de tipos de clientes")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCargasHistoricasClientes() {

		ResponseApi resultado = clientesService.consultaCargaHistoricaAltoRiesgo();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
