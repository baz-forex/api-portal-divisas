package com.baz.divisas.api.digital.configuraciones;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.baz.divisas.api.digital.configuraciones.model.ParametriaChatbot;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaOperacion;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaRemesas;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaSeguimientoTC;
import com.baz.divisas.api.digital.configuraciones.services.ConfiguracionesDigitalServiceImpl;
import com.baz.divisas.api.tef.parametria.dto.ReqActAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqArchivo;
import com.baz.divisas.api.tef.parametria.dto.ReqAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaAsignaciones;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaParametros;
import com.baz.divisas.api.tef.parametria.dto.ReqDetalleParametro;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminacionAsignacion;
import com.baz.divisas.api.tef.parametria.services.ParametriaServiceImpl;
import com.baz.divisas.api.tef.parametria.services.ParametriaSucursalesImpl;
import com.baz.divisas.general.dto.ResponseApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Arrays;
import java.util.List;

/**
 * Controlador de configuraciones Digital.
 *
 * @author 1027346
 * ymartinezb
 */
@RestController
@RequestMapping("/api/digital/configuraciones")
@Api(tags = "Digital/Configuraciones")
@Validated
public class ConfiguracionesDigitalController {

	private static final List<String> PARAMETRIASDIVISAS = Arrays.asList("appeur","appcad","appgbp");

	/** Service Configuraciones. */
	private ConfiguracionesDigitalServiceImpl configService;
	
	/** The parametria chatbot. */
	private ParametriaServiceImpl parametriaChatbot;
	
	/** The parametria sucursal. */
	private ParametriaSucursalesImpl parametriaSucursal;

	/**
	 * Constructor de la clase.
	 *
	 * @param configService - Configuraciones Service
	 * @param parametriaChatbot the parametria chatbot
	 * @param parametriaSucursal the parametria sucursal
	 */
	@Autowired 
	public ConfiguracionesDigitalController(ConfiguracionesDigitalServiceImpl configService,ParametriaServiceImpl parametriaChatbot,ParametriaSucursalesImpl parametriaSucursal) {
		super();
		this.configService = configService;
		this.parametriaChatbot = parametriaChatbot;
		this.parametriaSucursal = parametriaSucursal;
	}

	/**
	 * Metodo que devuelve la Parametria de Operacion Digital.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * @param idAplicacion - Id de Aplicacion 	 *
	 * @return ResponseApi
	 */
	@GetMapping(value = "parametria/operacion/{tipoOperacion}/{idAplicacion}")
	@ApiOperation(value = "Obtiene la Parametría de Operación Digital", 
	notes="**tipoOperacion:** [412, 413] \n**idAplicacion:** [app, sapp, appeur, appcad, appgbp]")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getParametriaOperacion(
			@PathVariable 
			@Pattern(regexp="^(412|413)$", message="Tipo de operacion invalido") String tipoOperacion, 
			@PathVariable
			@Pattern(regexp="(?i)^(app|sapp|appeur|appcad|appgbp)$", message="Id de aplicacion invalido") String idAplicacion) {
		
		ResponseApi resultado;
		String aplicacion = idAplicacion.toLowerCase();

		String param = "parametria_operacion";
		if ("sapp".equals(aplicacion)) {
			param = "parametria_superapp";
		}

		if (PARAMETRIASDIVISAS.contains(aplicacion)) {
			param = aplicacion;
		}
		resultado = configService.getParametriaOperacion(param, tipoOperacion);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que actualiza la Parametria de Operacion Digital
	 *  
	 * @param tipoOperacion - Tipo de Operacion
	 * @param idAplicacion - Id de Aplicacion 
	 * @param parametria Parametria
	 * 
	 * @return ResponseApi
	 */
	@PutMapping(value = "parametria/operacion/{tipoOperacion}/{idAplicacion}")
	@ApiOperation(value = "Acualiza la Parametría de Operación Digital",
	notes="**tipoOperacion:** [412, 413] \n**idAplicacion:** [app, sapp, appeur, appcad, appgbp]")
	@ApiResponses(value = @ApiResponse(code = 401, message = "Unauthorized"))
	public ResponseEntity<ResponseApi> actualizaParametriaOperacion(
			@PathVariable 
			@Pattern(regexp="^(412|413)$", message="Tipo de operacion invalido") String tipoOperacion, 
			@PathVariable 
			@Pattern(regexp="(?i)^(app|sapp|appeur|appcad|appgbp)$", message="Id de aplicacion invalido") String idAplicacion,
			@Valid @RequestBody ParametriaOperacion parametria) {

		ResponseApi resultado;
		String aplicacion = idAplicacion.toLowerCase();
		String param = "parametria_operacion";
		if ("sapp".equals(aplicacion)) {
			param = "parametria_superapp";
		}

		if (PARAMETRIASDIVISAS.contains(aplicacion)) {
			param = aplicacion;
		}
		resultado = configService.updateParametriaOperacion(aplicacion, param, tipoOperacion, parametria);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve la Parametria de Seguimiento de Tipo de Cambio.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * @return ResponseApi
	 */
	@GetMapping(value = "parametria/seguimiento-tc/{tipoOperacion}")
	@ApiOperation(value = "Obtiene la Parametría de Seguimiento de Tipo de Cambio", 
	notes="**tipoOperacion:** [412, 413]")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getParametriaSeguimientoTC(
			@PathVariable 
			@Pattern(regexp="^(412|413)$", message="Tipo de operacion invalido") String tipoOperacion) {
		
		ResponseApi resultado = configService.getParametriaSeguimientoTC(tipoOperacion);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que actualiza la Parametria de Seguimiento de Tipo de Cambio
	 *  
	 * @param tipoOperacion - Tipo de Operacion
	 * @param parametria Parametria
	 * 
	 * @return ResponseApi
	 */
	@PutMapping(value = "parametria/seguimiento-tc/{tipoOperacion}")
	@ApiOperation(value = "Acualiza la Parametría de Seguimiento de Tipo de Cambio",
	notes="**tipoOperacion:** [412, 413]")
	@ApiResponses(value = @ApiResponse(code = 401, message = "Unauthorized"))
	public ResponseEntity<ResponseApi> actualizaParametriaSeguimientoTC(
			@PathVariable 
			@Pattern(regexp="^(412|413)$", message="Tipo de operacion invalido") String tipoOperacion, 
			@Valid @RequestBody ParametriaSeguimientoTC parametria) {

		ResponseApi resultado = configService.updateParametriaSeguimientoTC(tipoOperacion, parametria);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve la Parametria de Beneficio Remesas.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * @return ResponseApi
	 */
	@GetMapping(value = "parametria/remesas/{tipoOperacion}")
	@ApiOperation(value = "Obtiene la Parametría de Beneficio Remesas", 
	notes="**tipoOperacion:** [413]")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getParametriaRemesas(
			@PathVariable 
			@Pattern(regexp="^(413)$", message="Tipo de operacion invalido") String tipoOperacion) {
		
		ResponseApi resultado = configService.getParametriaRemesas(tipoOperacion);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que actualiza la Parametria de Beneficio Remesas.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * @param parametria Parametria
	 * @return ResponseApi
	 */
	@PutMapping(value = "parametria/remesas/{tipoOperacion}")
	@ApiOperation(value = "Acualiza la Parametría de Beneficio Remesas",
	notes="**tipoOperacion:** [413]")
	@ApiResponses(value = @ApiResponse(code = 401, message = "Unauthorized"))
	public ResponseEntity<ResponseApi> actualizaParametriaRemesas(
			@PathVariable 
			@Pattern(regexp="^(413)$", message="Tipo de operacion invalido") String tipoOperacion, 
			@Valid @RequestBody ParametriaRemesas parametria) {

		ResponseApi resultado = configService.updateParametriaRemesas(tipoOperacion, parametria);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	
	
	/**
	 * Metodo que devuelve la Parametria de Operacion Digital.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * @param divisa the divisa
	 * @return ResponseApi
	 */
	@GetMapping(value = "parametria/chatbot/{tipoOperacion}/{divisa}")
	@ApiOperation(value = "Obtiene la Parametría de ChatBot", 
	notes="**tipoOperacion:** [412, 413] \n**divisa:** [USD, EUR ,CAD]")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getParametriaChatBot(
			@PathVariable 
			@Pattern(regexp="^(412|413)$", message="Tipo de operacion invalido") String tipoOperacion, 
			@PathVariable 
			@Pattern(regexp="^(USD|EUR|CAD)$", message="Divisa invalida") String divisa) {
		
		ResponseApi resultado;
		
		if("412".equals(tipoOperacion)) {
			
			resultado = parametriaChatbot.getParametriaChatBot("Chatbot_Compra", divisa);
		}
		else {
			resultado = parametriaChatbot.getParametriaChatBot("Chatbot_Venta", divisa);
		}

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que actualiza la Parametria de Operacion Digital
	 *  
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * @param divisa the divisa
	 * @param parametria Parametria
	 * @return ResponseApi
	 */
	@PutMapping(value = "parametria/chatbot/{tipoOperacion}/{divisa}")
	@ApiOperation(value = "Acualiza la Parametría de ChatBot",
	notes="**tipoOperacion:** [412, 413] \n**divisa:** [USD, EUR, CAD]")
	@ApiResponses(value = @ApiResponse(code = 401, message = "Unauthorized"))
	public ResponseEntity<ResponseApi> actualizaParametriaChatBot(
			@PathVariable 
			@Pattern(regexp="^(412|413)$", message="Tipo de operacion invalido") String tipoOperacion, 
			@PathVariable 
			@Pattern(regexp="^(USD|EUR|CAD)$", message="Divisa invalida") String divisa,
			@Valid @RequestBody ParametriaChatbot parametria) {

		ResponseApi resultado;
		
		if("412".equals(tipoOperacion)) {
			
			resultado = parametriaChatbot.updateParametriaOperacion("Chatbot_Compra",divisa,  parametria);
		}
		else {
			resultado = parametriaChatbot.updateParametriaOperacion("Chatbot_Venta",divisa,  parametria);
		}

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	

	/**
	 * Consulta parametros sucursal.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PostMapping(value = "/parametros/busquedas")
	@ApiOperation(value = "Consulta de parámetros de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> consultaParametrosSucursal(@RequestBody ReqBusquedaParametros request) {

		ResponseApi	resultado = parametriaSucursal.consultaParametros(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	

	/**
	 * Registra parametro sucursal.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@PostMapping(value = "/parametros/crear-parametro")
	@ApiOperation(value = "crea parámetro de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraParametroSucursal(@RequestBody ReqDetalleParametro request, @RequestHeader String usuario) {

		ResponseApi	resultado = parametriaSucursal.registraParametro(request,usuario);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	

	/**
	 * Actualiza parametro sucursal.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@PutMapping(value = "/parametros/actualizar-parametro")
	@ApiOperation(value = "actualiza parámetro de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaParametroSucursal(@RequestBody ReqDetalleParametro request, @RequestHeader String usuario) {

		ResponseApi	resultado = parametriaSucursal.actualizaParametro(request, usuario);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Registra parametros masivos.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@PostMapping(value = "/parametros/masivo-parametros")
	@ApiOperation(value = "registra parámetros masivos de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraParametrosMasivos(@RequestBody ReqArchivo request, @RequestHeader String usuario) {

		ResponseApi	resultado = parametriaSucursal.registraParametrosMasivos(request,usuario);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Busca asignacione parametros.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PostMapping(value = "/parametros/busqueda-asignaciones")
	@ApiOperation(value = "búsqueda de asignaciones de parámetro de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> buscaAsignacioneParametros(@RequestBody ReqBusquedaAsignaciones request) {

		ResponseApi	resultado = parametriaSucursal.buscaParametrosAsignados(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Crear asignacion parametro.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@PostMapping(value = "/parametros/crear-asignacion")
	@ApiOperation(value = "creación de asignaciones de parámetro de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> crearAsignacionParametro(@RequestBody ReqAsignacion request, @RequestHeader String usuario) {

		ResponseApi	resultado = parametriaSucursal.crearAsignacionParametro(request,usuario);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Actualiza asignacion parametro.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@PutMapping(value = "/parametros/actualizar-asignacion")
	@ApiOperation(value = "actualización de asignación de parámetro de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaAsignacionParametro(@RequestBody ReqActAsignacion request, @RequestHeader String usuario) {

		ResponseApi	resultado = parametriaSucursal.actualizaAsignacionParametro(request, usuario);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Elimina asignacion parametro.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@DeleteMapping(value = "/parametros/eliminar-asignacion")
	@ApiOperation(value = "eliminación de asignación de parámetro de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaAsignacionParametro(@RequestBody ReqEliminacionAsignacion request, @RequestHeader String usuario) {

		ResponseApi	resultado = parametriaSucursal.eliminaAsignacionParametro(request, usuario);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Registra asignaciones parametros masivos.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response entity
	 */
	@PostMapping(value = "/parametros/masivo-asignacion")
	@ApiOperation(value = "asigna parámetros masivos de sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraAsignacionesParametrosMasivos(@RequestBody ReqArchivo request, @RequestHeader String usuario) {

		ResponseApi	resultado = parametriaSucursal.registraAsignacionesParametrosMasivos(request,usuario);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
