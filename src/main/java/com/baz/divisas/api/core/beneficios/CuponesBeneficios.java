package com.baz.divisas.api.core.beneficios;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.beneficios.service.CuponesBeneficiosImpl;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * The Class CuponesBeneficios.
 */
@RestController
@RequestMapping("/api/core/beneficios/cupones")
@Api(tags = "Core/Beneficios/Cupones")
@Validated
public class CuponesBeneficios {
	
	/** The cupones tiempo aire. */
	@Autowired
	private CuponesBeneficiosImpl cuponesBeneficios;
	

	/**
	 * Carga cupones TA.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @return the response entity
	 */
	@PostMapping(value = "carga-cupones-ta/registro")
	@ApiOperation(value = "Carga cupones de tiempo aire")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> cargaCuponesTA(@RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam String descripcion) {
		
		ResponseApi resultado = cuponesBeneficios.registraCuponesTA(archivo , nombre , descripcion);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	


	/**
	 * Gets the cargas historica.
	 *
	 * @return the cargas historica
	 */
	@GetMapping(value = "carga-cupones-ta/consulta")
	@ApiOperation(value = "Devuelve cargas de historico de cupones tiempo aire")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCargasHistorica() {

		ResponseApi resultado = cuponesBeneficios.consultaCargaHistorica();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	
	/**
	 * Carga cupones CRM.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @return the response entity
	 */
	@PostMapping(value = "cupones-crm/registro")
	@ApiOperation(value = "Carga cupones de dolarmania crm")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> cargaCuponesCRM(@RequestParam MultipartFile archivo, @RequestParam String nombre,
			@RequestParam String descripcion, @RequestParam String fechaInicio, @RequestParam String fechaFin) {
		
		ResponseApi resultado = cuponesBeneficios.registraCuponesCRM(archivo , nombre , descripcion,fechaInicio,fechaFin);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Gets the cargas historica CRM.
	 *
	 * @return the cargas historica CRM
	 */
	@GetMapping(value = "cupones-crm/consulta")
	@ApiOperation(value = "Devuelve cargas de historico de cupones crm")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCargasHistoricaCRM() {

		ResponseApi resultado = cuponesBeneficios.consultaCargaHistoricaCRM();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Carga cupones Mas Salud.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param tipo the tipo de cupon
	 * @return the response entity
	 */
	@PostMapping(value = "cupones-mas-salud/registro")
	@ApiOperation(value = "Carga cupones de la campaña Mas Salud")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> cargaCuponesMasSalud(@RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam String tipo) {
		
		ResponseApi resultado = cuponesBeneficios.registraCuponesMasSalud(archivo , nombre , descripcion, tipo);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Gets the cargas historica Mas Salud.
	 *
	 * @return the cargas historica Mas Salud
	 */
	@GetMapping(value = "cupones-mas-salud/consulta-historico")
	@ApiOperation(value = "Devuelve cargas de historico de cupones Mas Salud")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCargasHistoricaMasSalud(@RequestParam 
			@Pattern(regexp="[1,2]", message="El tipo no es valido") String tipo) {

		ResponseApi resultado = cuponesBeneficios.consultaCargaHistoricaMasSalud(tipo);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	

	/**
	 * Carga cupones alianzas.
	 *
	 * @param archivo the archivo
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @return the response entity
	 */
	@PostMapping(value = "cupones-alianzas/registro")
	@ApiOperation(value = "Carga cupones de dolarmania crm")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> cargaCuponesAlianzas(@RequestParam MultipartFile archivo, @RequestParam String nombre,
			@RequestParam String descripcion, @RequestParam String fechaInicio, @RequestParam String fechaFin) {
		
		ResponseApi resultado = cuponesBeneficios.registraCuponesAlianzas(archivo , nombre , descripcion,fechaInicio,fechaFin);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	

	/**
	 * Gets the cargas historica alianzas.
	 *
	 * @return the cargas historica alianzas
	 */
	@GetMapping(value = "cupones-alianzas/consulta")
	@ApiOperation(value = "Devuelve cargas de historico de cupones crm")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCargasHistoricaAlianzas() {

		ResponseApi resultado = cuponesBeneficios.consultaCargaHistoricaAlianzas();
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
