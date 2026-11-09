package com.baz.divisas.api.core.beneficios;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.core.beneficios.dto.ReqEstatusSeguro;
import com.baz.divisas.api.core.beneficios.model.Seguro;
import com.baz.divisas.api.core.beneficios.service.SegurosServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase SegurosController
 */
@RestController
@RequestMapping("/api/core/beneficios/seguros")
@Api(tags = "Core/Beneficios/Seguros")
@Validated
public class SegurosController {

	private SegurosServiceImpl segurosServiceImpl;

	/**
	 * Constructor
	 * @param segurosServiceImpl - Servicio Seguros
	 */
	@Autowired
	public SegurosController(SegurosServiceImpl segurosServiceImpl) {
		super();
		this.segurosServiceImpl = segurosServiceImpl;
	}

	/**
	 * Metodo para consultar seguros
	 * @return ResponseApi
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Consulta de seguros")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> consulta(){

		ResponseApi resultado = segurosServiceImpl.consultaSeguros();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo para consultar seguros.
	 *
	 * @param estatus - Estatus
	 * @return ResponseApi
	 */
	@GetMapping(value = "/{estatus:[a-zA-Z]+}")
	@ApiOperation(value = "Consulta de seguros por estatus", notes="**Estatus:** [Vigente, Inactivo, Eliminado]")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> consultaPorEstatus(@PathVariable 
			@Pattern(regexp="^(Vigente|Inactivo|Eliminado)$", message="Estatus invalido") String estatus){

		ResponseApi resultado = segurosServiceImpl.consultaSegurosPorEstatus(estatus);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo para consultar detalle del seguro.
	 *
	 * @param id - Id Seguro
	 * @return ResponseApi
	 */
	@GetMapping(value = "/{id:^[0-9a-f]{24}}")
	@ApiOperation(value = "Consulta de seguros por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> consultaPorId(@PathVariable 
			@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24") String id){

		ResponseApi resultado = segurosServiceImpl.consultaSegurosPorId(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo para creacion seguros.
	 *
	 * @param seguro - Seguro
	 * @return ResponseApi
	 */
	@PostMapping(value = "")
	@ApiOperation(value = "Creación de seguros")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> crear(@Validated(Registrar.class)
											 @RequestBody Seguro seguro){

		ResponseApi resultado = segurosServiceImpl.crear(seguro);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo para creacion seguros.
	 *
	 * @param seguro - Seguro
	 * @return ResponseApi
	 */
	@PutMapping(value = "")
	@ApiOperation(value = "Actualización de seguros")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> actualizar(@Validated(Actualizar.class) 
												  @RequestBody Seguro seguro){

		ResponseApi resultado = segurosServiceImpl.actualizar(seguro);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo para actualizar estatus del seguro.
	 *
	 * @param request - ReqEstatusSeguro
	 * @return ResponseApi
	 */
	@PutMapping(value = "/estatus")
	@ApiOperation(value = "Actualiza estatus del seguro", notes="**Estatus:** [Vigente, Inactivo, Eliminado]")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> actualizaEstatus(@Valid @RequestBody ReqEstatusSeguro request){

		ResponseApi resultado = segurosServiceImpl.actualizarEstatus(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
