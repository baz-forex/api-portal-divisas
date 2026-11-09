package com.baz.divisas.api.core.configuraciones;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.core.configuraciones.dto.ReqCampoRestringido;
import com.baz.divisas.api.core.configuraciones.dto.ReqEditSegmentoLealtad;
import com.baz.divisas.api.core.configuraciones.dto.ReqEditSegmentoRent;
import com.baz.divisas.api.core.configuraciones.dto.ReqEditValoresRentabilidad;
import com.baz.divisas.api.core.configuraciones.model.Notificacion;
import com.baz.divisas.api.core.configuraciones.model.ParametriaBusquedas;
import com.baz.divisas.api.core.configuraciones.model.SegmentoSucursal;
import com.baz.divisas.api.core.configuraciones.services.ConfiguracionesServiceImpl;
import com.baz.divisas.api.core.configuraciones.services.PerfilDeClientesServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador de configuraciones
 * @author B813196
 * itoxtle
 *
 */
@RestController
@RequestMapping("/api/core/configuraciones")
@Api(tags = "Core/Configuraciones")
@Validated
public class ConfiguracionesController {
	
  /**
   * Service Configuraciones
   */
  private ConfiguracionesServiceImpl configService;
  
  /**
   * Service PerfilDeClientes
   */
  private PerfilDeClientesServiceImpl clientesServiceImpl;
  
  
  /**
   * Constructor de la clase
   * 
   * @param configService - Configuraciones Service
   * @param paisesRestringidosService - Paises Restringidos Service
   * @param clientesServiceImpl - Perfil De Clientes Service
   */
  @Autowired
  public ConfiguracionesController(ConfiguracionesServiceImpl configService, 
		  PerfilDeClientesServiceImpl clientesServiceImpl) {
	  super();
	  this.configService = configService;
	  this.clientesServiceImpl = clientesServiceImpl;
  }

  
  /**
   * Metodo que devuelve la parametria de busquedas
   * 
   * Obtiene la configuracion de porcentajes que son utilizados para 
   * realizar comparaciones en las busquedas de Cliente y Listas Negras
   * 
   * @return ParametriaBusquedas
   * 			ConfigBusquedas
   * 			ConfigBusquedasLN
   * 			ConfigPenalizaciones
   */
  @GetMapping(value = "parametria-busquedas")
  @ApiOperation(value = "Devuelve la parametria de busquedas")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getParametriaBusquedas() {
    
	  ResponseApi resultado = configService.getParametriaBusquedas();

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  

/**
   * Metodo que actualiza la parametria de busquedas
   * 
   * Actualiza la configuracion de porcentajes que son utilizados para 
   * realizar comparaciones en las busquedas de Cliente y Listas Negras
   * 
   * @param parametria 
   * 		ParametriaBusquedas
   * 			ConfigBusquedas
   * 			ConfigBusquedasLN
   * 			ConfigPenalizaciones
   * 
   * @return ResponseApi
   */
  @PutMapping(value = "parametria-busquedas")
  @ApiOperation(value = "Acualiza la parametria de busquedas")
  @ApiResponses(value = @ApiResponse(code = 401, message = "Unauthorized"))
  public ResponseEntity<ResponseApi> actualizaParametriaBusquedas(@Valid @RequestBody ParametriaBusquedas parametria) {
    
	  ResponseApi resultado = configService.actualizaParametriaBusquedas(parametria);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que devuelve los campos restringidos
   * 
   * @return Lista de campos restringidos
   */
  @GetMapping(value = "campos-restringidos")
  @ApiOperation(value = "Devuelve los campos restringidos")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getCamposRestringidos() {
    
	  ResponseApi resultado = configService.getCamposRestringidos();

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que devuelve el campo restringido por id
   * 
   * @param idCampo - Identificador del campo restringido
   * @return Lista de campos restringidos
   */
  @GetMapping(value = "campos-restringidos/{idCampo}")
  @ApiOperation(value = "Devuelve un campo restringido por Id")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getCampoRestringido(@PathVariable String idCampo) {
    
	  ResponseApi resultado = configService.getCamposRestringido(idCampo);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que agrega una restriccion al campo
   * 
   * @param request - ReqCampoRestringido
   * @return ResponseApi
   */
  @PutMapping(value = "campos-restringidos")
  @ApiOperation(value = "Agrega una restriccion a un campo restringido")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> agregarRestriccionCampo(@RequestBody ReqCampoRestringido request) {
    
	  ResponseApi resultado = configService.agregarRestriccionCampo(request);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que elimina una restriccion al campo
   * 
   * @param request - ReqCampoRestringido
   * @return ResponseApi
   */
  @DeleteMapping(value = "campos-restringidos")
  @ApiOperation(value = "Elimina una restriccion a un campo restringido")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> eliminarRestriccionCampo(@RequestBody ReqCampoRestringido request) {
    
	  ResponseApi resultado = configService.eliminarRestriccionCampo(request);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }


  /**
   * Metodo que agrega una restriccion DE PAIS O NACIONALIDAD
   * 
   * @param listPaisNacionalidad - List<PaisesRestringidos>
   * @return ResponseApi
   */
  @PutMapping(value = "paises-restringidos")
  @ApiOperation(value = "Agrega una restriccion a un pais o nacionalidad")
  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found") })
  public ResponseEntity<ResponseApi> updatePaisesRestringidos(@RequestBody 
		  													  List<CatPaisNacionalidad> listPaisNacionalidad) {

	  ResponseApi resultado = configService.actualizaRestriccionPaisNacionalidad(listPaisNacionalidad);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }

  
  /**
   * Metodo que devuelve el catalogo de segmentos sucursal
   * 
   * @return Catalogo Tipo Segmento Sucursal
   */
  @GetMapping(value = "segmentos-sucursal")
  @ApiOperation(value = "Devuelve catalogo de segmentos sucursal")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getCatSegmentoSucursal() {

	  ResponseApi resultado = configService.getCatSegmentosSucursal();

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que devuelve segmentos sucursal por tipo
   * 
   * @param tipoSegmento - Tipo de Segmento de ListaSucursales
   * @return Segmentos Sucursal
   */
  @GetMapping(value = "segmentos-sucursal/{tipoSegmento}")
  @ApiOperation(value = "Devuelve segmentos de sucursales por tipo")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getSegmentosSucursal(@PathVariable String tipoSegmento) {

	  ResponseApi resultado = configService.getSegmentosSucursal(tipoSegmento);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que devuelve un segmento sucursal por id
   * 
   * @param idSegmento - Identificador del Segmento de ListaSucursales
   * @return Segmentos Sucursal
   */
  @GetMapping(value = "segmento-sucursal/{idSegmento}")
  @ApiOperation(value = "Devuelve un segmento de sucursales por id")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getSegmentoSucursal(@PathVariable
														 @Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
														 String idSegmento) {

	  ResponseApi resultado = configService.getSegmentoSucursal(idSegmento);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que registra un segmento de sucursales
   * 
   * @param request - SegmentoSucursal
   * @return ResponseApi
   */
  @PostMapping(value = "segmento-sucursal")
  @ApiOperation(value = "Registra un segmento de sucursales")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> registraSegmentoSucursal(@Validated(Registrar.class) 
  															  @RequestBody SegmentoSucursal request) {
    
	  ResponseApi resultado = configService.registraSegmentoSucursal(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
  
  /**
   * Metodo que actualiza un segmento de sucursales
   * 
   * @param request - SegmentoSucursal
   * @return ResponseApi
   */
  @PutMapping(value = "segmento-sucursal")
  @ApiOperation(value = "Actualiza un segmento de sucursales")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> actualizaSegmentoSucursal(@Validated(Actualizar.class) 
  @RequestBody SegmentoSucursal request) {

	  ResponseApi resultado = configService.actualizarSegmentoSucursal(request);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que actualiza el estatus de un segmento de sucursales
   * 
   * @param idSegmento - Identificador del segmento
   * @return ResponseApi
   */
  @PutMapping(value = "segmento-sucursal/actualizar-estatus/{idSegmento}")
  @ApiOperation(value = "Actualiza el estatus de un segmento de sucursales")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> actualizaEstatusSegmentoSucursal(@PathVariable 
																	  @Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
																	  String idSegmento) {
    
	  ResponseApi resultado = configService.actualizarEstatusSegmentoSucursal(idSegmento);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que elimina un segmento de sucursales
   * 
   * @param idSegmento - Identificador del segmento
   * @return ResponseApi
   */
  @DeleteMapping(value = "segmento-sucursal/{idSegmento}")
  @ApiOperation(value = "Elimina un segmento de sucursales")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> eliminaSegmentoSucursal(@PathVariable 
		  													 @Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
  															 String idSegmento) {
    
	  ResponseApi resultado = configService.eliminarSegmentoSucursal(idSegmento);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }

  
  /**
   * Metodo que devuelve un segmento sucursal por id
   * 
   * @param idSegmento - Identificador del Segmento de ListaSucursales
   * @return Segmentos Sucursal
   */
  @GetMapping(value = "perfil-cliente/{idSegmento}")
  @ApiOperation(value = "Devuelve perfil del cliente")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> consultaCatalogosSegmentosLealRentVal(@PathVariable String idSegmento) {

	  ResponseApi resultado = clientesServiceImpl.getPerfilesDelCliente(idSegmento);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }

  
  /**
   * Metodo que devuelve un segmento valor rentabilidad
   * 
   * @return Segmentos de valor de la rentavilidad
   */
  @GetMapping(value = "parametria-rentabilidad")
  @ApiOperation(value = "Devuelve los valores de la rentavilidad")
  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found") })
  public ResponseEntity<ResponseApi> consultaCatalogosSegmentosValorRent() {

	  ResponseApi resultado = clientesServiceImpl.getValoresRentabilida();

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }

  
  /**
   * Metodo que edita el catalogo sel perfil del cliente el segmento de lealtad
   * 
   * @param request - ReqEditSegmentoLealtad
   * @return estatus del update
   */
  @PutMapping(value = "perfil-cliente/lealtad")
  @ApiOperation(value = "Edita la lealtad de el perfl de cliente")
  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found") })
  public ResponseEntity<ResponseApi> editarCatSegmentoLealtad(@RequestBody ReqEditSegmentoLealtad request) {

	  ResponseApi resultado = clientesServiceImpl.editarCatSegmentoLealtad(request.getSegmentoLealtad());

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }

  
  /**
   * Metodo para editar el catalogo de segmento rentabilidad
   * 
   * @param request - ReqEditSegmentoLealtad
   * @return estatus del update
   */
  @PutMapping(value = "perfil-cliente/rentabilidad")
  @ApiOperation(value = "Edita la rentavilidad de el perfl de cliente")
  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found") })
  public ResponseEntity<ResponseApi> editarCatSegmentoRentabilidad(@RequestBody ReqEditSegmentoRent request) {

	  ResponseApi resultado = clientesServiceImpl.editarCatSegmentoRentabilidad(request.getSegmentoRentabilidad());

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }

  
  /**
   * Servicio para editar el catalogo de valores rentabilidad
   * 
   * @param request - ReqEditValoresRentabilidad
   * @return estatus del update
   */
  @PutMapping(value = "parametria-rentabilidad")
  @ApiOperation(value = "Edita el valor de rentabilidad")
  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found") })
  public ResponseEntity<ResponseApi> editarCatValoresRentabilidad(@RequestBody ReqEditValoresRentabilidad request) {

	  ResponseApi resultado = clientesServiceImpl.editarCatValoresRentabilidad(request.getValoresRentabilidad());

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }

  
  /**
   * Metodo que devuelve el catalogo de estatus de notificacion
   * 
   * @return Catalogo Estatus de Notificacion
   */
  @GetMapping(value = "estatus-notificacion")
  @ApiOperation(value = "Devuelve catalogo de estatus de notificacion")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getCatEstatusNotificacion() {

	  ResponseApi resultado = configService.getCatEstatusNotificacion();

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo de consulta de notificaciones
   * @param estatus - Estatus de la notificacion
   * @param sucursal - Sucursal de la notificacion
   * @return Notificaciones
   */
  @GetMapping(value = "notificaciones/{estatus}")
  @ApiOperation(value = "Devuelve notificaciones por estatus y sucursal")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getNotificaciones(@PathVariable 
													   @Pattern(regexp="^(?i)(VIGENTES|NO_VIGENTES|PROXIMAS)$",
													   			message="Solo se permite VIGENTES|NO_VIGENTES|PROXIMAS")
											  		   String estatus) {

	  ResponseApi resultado = configService.getNotificaciones(estatus, "");

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo de consulta de notificaciones
   * @param estatus - Estatus de la notificacion
   * @param sucursal - Sucursal de la notificacion
   * @return Notificaciones
   */
  @GetMapping(value = "notificaciones/{estatus}/{sucursal}")
  @ApiOperation(value = "Devuelve notificaciones por estatus y sucursal")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getNotificaciones(@PathVariable 
													   @Pattern(regexp="^(?i)(VIGENTES|NO_VIGENTES|PROXIMAS)$",
													   			message="Solo se permite VIGENTES|NO_VIGENTES|PROXIMAS")
											  		   String estatus, 
		  											   @PathVariable 		  											   
		  											   @Pattern(regexp="^[0-9]{4}", message="Debe ser numérico de longitud 4") 
  													   String sucursal) {

	  ResponseApi resultado = configService.getNotificaciones(estatus, sucursal);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo de consulta de notificacion por id
   * @param idNotificacion - Identificador de notificacion
   * @return Notificacion
   */
  @GetMapping(value = "notificacion/{idNotificacion}")
  @ApiOperation(value = "Devuelve una notificacion por id")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getNotificacion(@PathVariable		  
												  	 @Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
										  			 String idNotificacion) {

	  ResponseApi resultado = configService.getNotificacion(idNotificacion);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo de registro de notificaciones
   * @param notificacion - Notificacion
   * @return ResponseApi
   * @throws IOException - IOException
   */
  @PostMapping(value = "notificaciones", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @ApiOperation(value = "Registra una notificacion por id")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> registrarNotificacion(@Validated(Registrar.class)
  														  @ModelAttribute Notificacion notificacion) throws IOException {

	  ResponseApi resultado = configService.registraNotificacion(notificacion);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo de actualizacion de notificaciones
   * @param notificacion - Notificacion
   * @return ResponseApi
   * @throws IOException - IOException
   */
  @PutMapping(value = "notificaciones", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @ApiOperation(value = "Actualiza una notificacion por id")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> actualizarNotificacion(@Validated(Actualizar.class)
  														    @ModelAttribute Notificacion notificacion) throws IOException {

	  ResponseApi resultado = configService.actualizaNotificacion(notificacion);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que actualiza el estatus de un segmento de sucursales
   * 
   * @param idNotificacion - Identificador de la notificacion
   * @return ResponseApi
   */
  @PutMapping(value = "notificaciones/actualizar-estatus/{idNotificacion}")
  @ApiOperation(value = "Actualiza el estatus de una notificación")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> actualizaEstatusNotificacion(@PathVariable 
																  @Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
																  String idNotificacion) {
    
	  ResponseApi resultado = configService.actualizarEstatusNotificacion(idNotificacion);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que actualiza el estatus de un segmento de sucursales
   * 
   * @param idNotificacion - Identificador de la notificacion
   * @return ResponseApi
   */
  @PutMapping(value = "notificaciones/reiniciar/{idNotificacion}")
  @ApiOperation(value = "Actualiza el estatus de una notificación")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> reiniciarNotificacion(@PathVariable 
														   @Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
														   String idNotificacion) {
    
	  ResponseApi resultado = configService.reiniciaNotificacion(idNotificacion);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
}
