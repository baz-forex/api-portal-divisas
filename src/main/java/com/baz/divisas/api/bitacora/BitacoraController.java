package com.baz.divisas.api.bitacora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.bitacora.dto.BitacoraRequest;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador de bitacora
 * @author B813196
 * itoxtle
 *
 */
@RestController
@RequestMapping("/api/bitacora")
@Api(tags = "Bitacora")
@Validated
public class BitacoraController {

	@Autowired
	private BitacoraServiceImpl bitacoraService;

	/**
	 * Constructor de la clase
	 */
	public BitacoraController() {
		super();
	}


	/**
	 * Metodo que devuelve los registros de la bitacora por rango de fechas
	 *
	 * @param idBitacora - Id Bitacora
	 * @return ResponseApi
	 */
	@GetMapping(value = "/{idBitacora}")
	@ApiOperation(value = "Devuelve un registro de la bitacora por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> getBitacoraPorId(@PathVariable String idBitacora) {

		ResponseApi resultado = bitacoraService.getBitacoraPorId(idBitacora);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve los registros de la bitacora por rango de fechas
	 *
	 * @param request - Bitacora request
	 * @return ResponseApi
	 */
	@PostMapping(value = "")
	@ApiOperation(value = "Devuelve los registros de la bitacora")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> getBitacoraPorFecha(@RequestBody BitacoraRequest request) {

		ResponseApi resultado = bitacoraService.getBitacora(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

}
