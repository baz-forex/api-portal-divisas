/**
 * 
 */
package com.baz.divisas.api.core.beneficios;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.core.beneficios.dto.ReqAltaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEliminaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusCampania;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqTipoCampania;
import com.baz.divisas.api.core.beneficios.model.BeanCampania;
import com.baz.divisas.api.core.beneficios.service.CampaniasServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase de control para servicios de campañas
 * @author B86960
 * rvalencia
 */
@RestController
@RequestMapping("/api/core/beneficios/campanias")
@Api(tags = "Core/Beneficios/Campañas")
@Validated
public class CampaniasController {

	private CampaniasServiceImpl campaniasServiceImpl;
	/**
	 * Constructor
	 * @param campaniasServiceImpl implementacion sericio
	 */
	@Autowired
	public CampaniasController(CampaniasServiceImpl campaniasServiceImpl) {
		super();
		this.campaniasServiceImpl = campaniasServiceImpl;
	}
	/**
	 * Metodo para alta de campañas
	 * @param request request
	 * @return ResponseApi
	 */
	@PostMapping(value = "")
	  @ApiOperation(value = "Crear Campaña")
	  @ApiResponses(value = { 
			  @ApiResponse(code = 401, message = "Unauthorized"),
			  @ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> crear(@Valid @RequestBody BeanCampania request){
		ResponseApi resultado = campaniasServiceImpl.crear(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para consultar campañas
	 * @return ResponseApi
	 */
	@GetMapping(value = "")
	  @ApiOperation(value = "Obtener Campaña")
	  @ApiResponses(value = { 
			  @ApiResponse(code = 401, message = "Unauthorized"),
			  @ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> obtener(){
		ResponseApi resultado = campaniasServiceImpl.obtener();
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para actualizacion de campañas
	 * @param request request
	 * @return ResponseApi
	 */
	@PutMapping(value = "")
	  @ApiOperation(value = "Actualizacion de Campaña")
	  @ApiResponses(value = { 
			  @ApiResponse(code = 401, message = "Unauthorized"),
			  @ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> actualizar(@Valid @RequestBody BeanCampania request){
		ResponseApi resultado = campaniasServiceImpl.actualizar(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para eliminar de campaña
	 * @param idCampania idCampania
	 * @return ResponseApi
	 */
	@DeleteMapping(value = "{idCampania}")
	  @ApiOperation(value = "Elimina Campaña")
	  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
	          @ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminar(@PathVariable String idCampania){
		ResponseApi resultado = campaniasServiceImpl.eliminar(idCampania);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para actualizar tipo de campaña
	 * @param tipoCampania tipoCampania
	 * @return ResponseApi
	 */
	@PutMapping(value = "tipo")
	  @ApiOperation(value = "Actualizar Tipo Campaña")
	  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
	          @ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizarTipo(@Valid @RequestBody ReqTipoCampania tipoCampania){
		ResponseApi resultado = campaniasServiceImpl.actualizarTipo(tipoCampania);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para actualizar estatus de campaña
	 * @param request request
	 * @return ResponseApi
	 */
	@PutMapping(value = "estatus")
	  @ApiOperation(value = "Actualizar Estatus Campaña")
	  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
	          @ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizarEstatus(@Valid @RequestBody ReqEstatusCampania request){
		ResponseApi resultado = campaniasServiceImpl.actualizarEstatus(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para alta de premios de campania
	 * @param request request
	 * @return ResponseApi
	 */
	@PostMapping(value = "premio")
	  @ApiOperation(value = "Alta de Premio")
	  @ApiResponses(value = { 
			  @ApiResponse(code = 401, message = "Unauthorized"),
			  @ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> crearPremio(@Valid @RequestBody ReqAltaPremio request){
		ResponseApi resultado = campaniasServiceImpl.crearPremio(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para actualizacion de premio campañas
	 * @param request request
	 * @return ResponseApi
	 */
	@PutMapping(value = "premio")
	  @ApiOperation(value = "Actualizacion de Premio")
	  @ApiResponses(value = { 
			  @ApiResponse(code = 401, message = "Unauthorized"),
			  @ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> actualizarPremio(@Valid @RequestBody ReqAltaPremio request){
		ResponseApi resultado = campaniasServiceImpl.actualizarPremio(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para eliminar premio de campaña
	 * @param request request
	 * @return ResponseApi
	 */
	@DeleteMapping(value = "premio")
	  @ApiOperation(value = "Elimina Premio")
	  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
	          @ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminarPremio(@Valid @RequestBody ReqEliminaPremio request){
		ResponseApi resultado = campaniasServiceImpl.eliminarPremio(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	/**
	 * Metodo para eliminar premio de campaña
	 * @param request request
	 * @return ResponseApi
	 */
	@PutMapping(value = "premio/estatus")
	  @ApiOperation(value = "Actualiza Estatus del Premio")
	  @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
	          @ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusPremio(@Valid @RequestBody ReqEstatusPremio request){
		ResponseApi resultado = campaniasServiceImpl.actualizarEstatusPremio(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

}
