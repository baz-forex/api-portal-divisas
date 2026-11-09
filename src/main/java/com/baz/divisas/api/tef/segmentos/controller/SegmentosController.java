package com.baz.divisas.api.tef.segmentos.controller;

import javax.validation.Valid;

import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.segmentos.services.ISegmentosService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador para Segmentos
 * 
 * @author 164945 ogatica
 */
@RestController
@RequestMapping("/api/tef/segmentos")
@Api(tags = "TEF/Segmentos")
@Validated
public class SegmentosController {

	@Autowired
	private ISegmentosService segmentosService;

	/**
	 * Constructor de la clase
	 */
	public SegmentosController() {
		super();
	}
	
	/**
	 * Metodo que devuelve todos los segmentos
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve todos los segmentos")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSegmentos() {

		ResponseApi resultado = segmentosService.consultaSegmentos();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de registro de un segmento
	 * 
	 * @param segmento - Segmento
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Registrar un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraSegmento(@Valid @RequestBody Segmento segmento) {

		ResponseApi resultado = segmentosService.creaSegmento(segmento.getDescripcion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de la descripcion del segmento
	 * 
	 * @param segmento - Segmento
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar")
	@ApiOperation(value = "Actualizar la descripción de un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaDescripcionSegmento(@Valid @RequestBody Segmento segmento) {

		ResponseApi resultado = segmentosService.actualizaDescripcionSegmento(segmento.getId(), segmento.getDescripcion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus del segmento
	 * 
	 * @param id - Id del segmento
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/{id}")
	@ApiOperation(value = "Actualizar estatus de un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusSegmento(@PathVariable String id) {

		ResponseApi resultado = segmentosService.actualizaEstatusSegmento(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de eliminacion de un segmento
	 * 
	 * @param id - id Segmento
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/eliminar/{id}")
	@ApiOperation(value = "Eliminar un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaSegmento(@PathVariable String id) {

		ResponseApi resultado = segmentosService.eliminaSegmento(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	//OLP
	
	/**
	 * Metodo que devuelve todos los segmentos
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve todos los segmentos")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSegmentosOlp() {

		ResponseApi resultado = segmentosService.consultaSegmentosOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de registro de un segmento
	 * 
	 * @param segmento - Segmento
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar/olp")
	@ApiOperation(value = "Registrar un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraSegmentoOlp(@Valid @RequestBody Segmento segmento) {

		ResponseApi resultado = segmentosService.creaSegmentoOlp(segmento.getDescripcion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de la descripcion del segmento
	 * 
	 * @param segmento - Segmento
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar/olp")
	@ApiOperation(value = "Actualizar la descripción de un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaDescripcionSegmentoOlp(@Valid @RequestBody Segmento segmento) {

		ResponseApi resultado = segmentosService.actualizaDescripcionSegmentoOlp(segmento.getId(), segmento.getDescripcion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus del segmento
	 * 
	 * @param id - Id del segmento
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/olp/{id}")
	@ApiOperation(value = "Actualizar estatus de un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusSegmentoOlp(@PathVariable String id) {

		ResponseApi resultado = segmentosService.actualizaEstatusSegmentoOlp(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de eliminacion de un segmento
	 * 
	 * @param id - id Segmento
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/eliminar/olp/{id}")
	@ApiOperation(value = "Eliminar un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaSegmentoOlp(@PathVariable String id) {

		ResponseApi resultado = segmentosService.eliminaSegmentoOlp(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
