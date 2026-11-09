/**
 * 
 */
package com.baz.divisas.api.tef.parametria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.parametria.dto.ReqActualizaCampoParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqAltaParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqConsultaParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminarCampoParametria;
import com.baz.divisas.api.tef.parametria.services.ParametriaServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller de parametria
 * @author B86960
 *
 */
@RestController
@RequestMapping("/api/tef/parametria")
@Api(tags = "Tef/Parametria")
@Validated
public class ParametriaServiceController {

	private ParametriaServiceImpl impl;
	
	/**
	 * Constructor
	 * @param impl implementacion
	 */
	@Autowired
	public ParametriaServiceController(ParametriaServiceImpl impl)
	{
		this.impl = impl;
	}
	
	
	/**
	 * Alta parametria.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PostMapping(value = "")
	@ApiOperation(value = "Alta de parametria general")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> altaParametria(@RequestBody ReqAltaParametria request) {
		ResponseApi resultado = impl.alta(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve las informacion del informe de promoespacio.
	 *
	 * @param request the request
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve documento de la parametria general")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> consultaParametria(@RequestBody ReqConsultaParametria request) {
		ResponseApi resultado = impl.consultar(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Consulta parametria.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "/all")
	@ApiOperation(value = "Devuelve todos los ids de parametria general")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> consultaParametria() {
		ResponseApi resultado = impl.consultar();
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Eliminar.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@DeleteMapping(value = "")
	@ApiOperation(value = "Elimina la parametria")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminar(@RequestBody ReqConsultaParametria request) {
		ResponseApi resultado = impl.eliminar(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Consulta activo.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@GetMapping(value = "/activo")
	@ApiOperation(value = "Devuelve si esta activa la parametria")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> consultaActivo(@RequestBody ReqConsultaParametria request) {
		ResponseApi resultado = impl.consultarActivo(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Activar.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PutMapping(value = "/activar")
	@ApiOperation(value = "Activa la parametria")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> activar(@RequestBody ReqConsultaParametria request) {
		ResponseApi resultado = impl.actualizarCampoActivo(request, true);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Desactivar.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PutMapping(value = "/desactivar")
	@ApiOperation(value = "Desactiva la parametria")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> desactivar(@RequestBody ReqConsultaParametria request) {
		ResponseApi resultado = impl.actualizarCampoActivo(request, false);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Actualizar campo.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PutMapping(value = "/campo")
	@ApiOperation(value = "Actualiza campo de la parametria")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizarCampo(@RequestBody ReqActualizaCampoParametria request) {
		ResponseApi resultado = impl.actualizarCampo(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Eliminar campo.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@DeleteMapping(value = "/campo")
	@ApiOperation(value = "Elimina campo de la parametria")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminarCampo(@RequestBody ReqEliminarCampoParametria request) {
		ResponseApi resultado = impl.eliminarCampo(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
