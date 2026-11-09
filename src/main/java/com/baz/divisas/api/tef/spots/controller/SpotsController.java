package com.baz.divisas.api.tef.spots.controller;

import java.util.List;

import javax.validation.Valid;

import com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot;
import com.baz.divisas.api.tef.spots.dto.ReqSpots;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.baz.divisas.api.tef.spots.services.ISpotsService;
import com.baz.divisas.general.dto.ResponseApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class SpotsController.
 *
 * @author 1077023
 */
@RestController
@RequestMapping("/api/tef/spots")
@Api(tags = "TEF/Spots")
@Validated
public class SpotsController {
	
	/** The spot service. */
	@Autowired
	private ISpotsService spotService;
	
	/**
	 * Metodo que devuelve los Spots.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve los spots")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSpots() {

		ResponseApi resultado = spotService.consultaSpots();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve los Spots.
	 *
	 * @param request - Request
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/fecha")
	@ApiOperation(value = "Devuelve los spots por fecha")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSpotsPorFecha(@RequestBody ReqSpots request) {

		ResponseApi	resultado = spotService.consultaSpotPorFecha(request.getFechaInicio(), request.getFechaFin(), 
																 request.getIdDivisas());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve los ultimos spots agregados al momento.
	 *
	 * @param request - Request
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/vigentes")
	@ApiOperation(value = "Devuelve los spots")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSpotsVigentes(@RequestBody ReqSpots request){

		ResponseApi resultado = spotService.consultaSpotsVigentes(request.getIdDivisas());
	
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de registro de Spots.
	 *
	 * @param spots the spots
	 * @return ResponseEntity
	 */
	@PostMapping(value = "")
	@ApiOperation(value = "Registrar una lista de Spots")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraSpot(@Valid @RequestBody List<Spot> spots) {
		
		ResponseApi resultado = spotService.creaSpots(spots);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	
	/**
	 * Metodo de registro de Spots.
	 *
	 * @param spot - Nuevo Spots
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/olp")
	@ApiOperation(value = "Registrar un Spot de OLP")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraSpotOlp(@Valid @RequestBody Spot spot) {
		
		ResponseApi resultado = spotService.creaSpotOlp(spot);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Gets the spots tesoreria.
	 *
	 * @return the spots tesoreria
	 */
	@GetMapping(value = "/tesoreria")
	@ApiOperation(value = "Devuelve los spots de tesoreria")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSpotsTesoreria() {

		ResponseApi resultado = spotService.consultaSpotsTesoreria();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve los ultimos spots agregados al momento.
	 *
	 * @param request - Request
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/vigentes/olp")
	@ApiOperation(value = "Devuelve los spots")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSpotVigentesOLP(){

		ResponseApi resultado = spotService.consultaSpotsVigentesOLP();
	
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve los ultimos spots agregados al momento.
	 *
	 * @param request - Request
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/configuracion-spot/olp")
	@ApiOperation(value = "Devuelve los spots")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getConfiguracionSpotOLP(){

		ResponseApi resultado = spotService.consultaConfiguracionSpotOLP();
	
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de registro de Spots.
	 *
	 * @param spot - Nuevo Spots
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/configuracion-spot/olp")
	@ApiOperation(value = "Registrar un Spot de OLP")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraConfiguracionSpotOlp(@Valid @RequestBody ReqConfiguracionSpot spot) {
		
		ResponseApi resultado = spotService.guardaConfiguracionSpotOLP(spot);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
