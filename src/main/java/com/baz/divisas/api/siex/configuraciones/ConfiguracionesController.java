package com.baz.divisas.api.siex.configuraciones;

import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.siex.configuraciones.dto.ReqCampoRestringido;
import com.baz.divisas.api.siex.configuraciones.services.ConfiguracionesServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador de configuraciones SIEX
 * @author B813196
 * itoxtle
 *
 */
@RestController("ConfiguracionesSiexController")
@RequestMapping("/api/siex/configuraciones")
@Api(tags = "SIEX/Configuraciones")
@Validated
public class ConfiguracionesController {
	
  /**
   * Service Configuraciones
   */
  private ConfiguracionesServiceImpl configService;
  
  
  /**
   * Constructor de la clase
   * 
   * @param configService - Configuraciones Service
   * @param paisesRestringidosService - Paises Restringidos Service
   * @param clientesServiceImpl - Perfil De Clientes Service
   */
  @Autowired
  public ConfiguracionesController(ConfiguracionesServiceImpl configService) {
	  super();
	  this.configService = configService;
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
    
	  ResponseApi resultado = configService.getCamposRestringidosImpl();

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  /**
   * Metodo que descarga la configuracion de campos restringidos
   * 
   * @return Lista de campos restringidos
   */
  @GetMapping(value = "campos-restringidos/descarga")
  @ApiOperation(value = "Descarga configuración de los campos restringidos")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<Resource> descargaCamposRestringidos() {
    
	  InputStreamResource file = new InputStreamResource(configService.descargaCamposRestringidosImpl());
	  
	  return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CamposRestringidosSIEX.xlsx")
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
  }
  
  /**
   * Metodo que devuelve el campo restringido por id
   * 
   * @param idCampo - Identificador del campo restringido
   * @return Lista de campos restringidos
   */
  @GetMapping(value = { "campos-restringidos/reglas" })
  @ApiOperation(value = "Devuelve reglas de campos restringidos")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getReglasCampoRestringido() {
    
	  ResponseApi resultado = configService.getReglasCampoRestringidoImpl();

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  /**
   * Metodo que devuelve el campo restringido por id
   * 
   * @param idCampo - Identificador del campo restringido
   * @param idRegla - Identificador de la regla de restriccion
   * @return Lista de campos restringidos
   */
  @GetMapping(value = { "campos-restringidos/{idCampo}/{idRegla}" })
  @ApiOperation(value = "Devuelve un campo restringido por campo y regla ")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getCampoRestringido(@PathVariable String idCampo, 
		  												 @PathVariable @Pattern(regexp="^(?i)(empiezaCon|terminaCon|contiene|igualA)$",
		  												 message="Regla inválida") String idRegla) {
    
	  ResponseApi resultado = configService.getCampoRestringidoImpl(idCampo, idRegla);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que agrega una restriccion al campo
   * 
   * @param request - ReqCampoRestringido
   * @return ResponseApi
   */
  @PutMapping(value = "campos-restringidos")
  @ApiOperation(value = "Agrega una restricción a un campo")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> agregarRestriccionCampo(@Valid @RequestBody ReqCampoRestringido request) {
    
	  ResponseApi resultado = configService.agregarRestriccionCampoImpl(request);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  
  /**
   * Metodo que elimina una restriccion del campo
   * 
   * @param request - ReqCampoRestringido
   * @return ResponseApi
   */
  @DeleteMapping(value = "campos-restringidos")
  @ApiOperation(value = "Elimina una restriccion de un campo")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> eliminarRestriccionCampo(@Valid @RequestBody ReqCampoRestringido request) {
    
	  ResponseApi resultado = configService.eliminarRestriccionCampoImpl(request);

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }

  
}
