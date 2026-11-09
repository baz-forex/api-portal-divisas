package com.baz.divisas.api.catalogos;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.catalogos.services.CatalogosServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/catalogos")
@Api(tags = "Catalogos")
@Validated
public class CatalogosController {

	/**
	 * Service Clientes
	 */
	private CatalogosServiceImpl catalogosService;


	/**
	 * Constructor de la clase
	 * 
	 * @param catalogosService - Catalogos Service
	 */
	@Autowired
	public CatalogosController(CatalogosServiceImpl catalogosService) {
		super();
		this.catalogosService = catalogosService;

	}


	/**
	 * Metodo que devuelve catalogo de paises y nacionalidades
	 * 
	 * @return ResponseApi
	 */
	@GetMapping(value = "paises")
	@ApiOperation(value = "Devuelve catalogo de Paises y Nacionalidades")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> getCatPaisNacionalidad() {

		ResponseApi resultado = catalogosService.getCatPaisNacionalidad();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}


	/**
	 * Metodo que devuelve catalogo de estados
	 * 
	 * @return ResponseApi
	 */
	@GetMapping(value = "estados")
	@ApiOperation(value = "Devuelve catalogo de Estados")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> getCatEstados() {

		ResponseApi resultado = catalogosService.getCatEstados();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que devuelve los catalogos de las divisas
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "divisas")
	@ApiOperation(value = "Devuelve catalogo de Divisas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCatDivisas() {

		ResponseApi resultado = catalogosService.getCatDivisas();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve los catalogos de las identificaciones
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "identificaciones")
	@ApiOperation(value = "Devuelve catalogo de las identificaciones")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCatIdentificaciones() {

		ResponseApi resultado = catalogosService.getCatIdentificaciones();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de actualizacion de divisas
	 * 
	 * @param requestDivisas - modelo de la divisa
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/divisas")
	@ApiOperation(value = "Actualizar estatus de una Divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaDivisa(@Valid @RequestBody Catalogo requestDivisas) {

		ResponseApi resultado = catalogosService.actualizaEstatusDivisa(requestDivisas);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de registro de divisas
	 * 
	 * @param requestDivisas - modelo de la divisa
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/divisas/registrar")
	@ApiOperation(value = "Registro de una Divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraDivisa(@Valid @RequestBody Catalogo requestDivisas) {

		ResponseApi resultado = catalogosService.creaDivisa(requestDivisas);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve el catalogo Estatus de Cotizacion
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "estatus-cotizacion")
	@ApiOperation(value = "Devuelve catalogo de estatus de cotizacion")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getCatEstatusCotizacion() {

		ResponseApi resultado = catalogosService.getCatEstatusCotizacion();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
    /**
     * Metodo que devuelve el catalogo de tipo de operacion
     * 
     * @return ResponseEntity
     */
    @GetMapping(value = "/TipoOperacion")
    @ApiOperation(value = "Devuelve catalogo de tipo de operacion")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found") })
    public ResponseEntity<ResponseApi> getCatTipoOperacion() {

        ResponseApi resultado = catalogosService.getCatTipoOperacion();

        return new ResponseEntity<>(resultado, resultado.getHttpStatus());
    }
	/**
     * Metodo que devuelve el catalogos de tipo cliente
     * 
     * @return ResponseEntity
     */
    @GetMapping(value = "/TipoCliente")
    @ApiOperation(value = "Devuelve catalogo de tipo cliente")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found") })
    public ResponseEntity<ResponseApi> getCatTipoCliente() {

        ResponseApi resultado = catalogosService.getCatTipoCliente();

        return new ResponseEntity<>(resultado, resultado.getHttpStatus());
    }
	/**
     * Metodo que devuelve el catalogo de perfil del cliente campanias
     * 
     * @return ResponseEntity
     */
    @GetMapping(value = "/campanias/PerfilCliente")
    @ApiOperation(value = "Devuelve catalogo de perfil del cliente campanias")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found") })
    public ResponseEntity<ResponseApi> getCatPerfilClienteCampanias() {

        ResponseApi resultado = catalogosService.getCatPerfilClienteCampanias();

        return new ResponseEntity<>(resultado, resultado.getHttpStatus());
    }
	/**
     * Metodo que devuelve el catalogo de tipo limite campanias
     * 
     * @return ResponseEntity
     */
    @GetMapping(value = "/campanias/TipoLimite")
    @ApiOperation(value = "Devuelve catalogo de tipo limite campanias")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found") })
    public ResponseEntity<ResponseApi> getCatTipoLimiteCampanias() {

        ResponseApi resultado = catalogosService.getCatTipoLimiteCampanias();

        return new ResponseEntity<>(resultado, resultado.getHttpStatus());
    }
	/**
     * Metodo que devuelve el catalogo de tipo acumulado campanias
     * 
     * @return ResponseEntity
     */
    @GetMapping(value = "/campanias/TipoAcumulado")
    @ApiOperation(value = "Devuelve catalogo de tipo acumulado campanias")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found") })
    public ResponseEntity<ResponseApi> getCatTipoAcumuladoCampanias() {

        ResponseApi resultado = catalogosService.getCatTipoAcumuladoCampanias();

        return new ResponseEntity<>(resultado, resultado.getHttpStatus());
    }
	/**
     * Metodo que devuelve el catalogos de los estatus de campanias
     * 
     * @return ResponseEntity
     */
    @GetMapping(value = "/campanias/Estatus")
    @ApiOperation(value = "Devuelve catalogo de Estatus de Campanias")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found") })
    public ResponseEntity<ResponseApi> getCatEstatusCampanias() {

        ResponseApi resultado = catalogosService.getCatEstatusCampanias();

        return new ResponseEntity<>(resultado, resultado.getHttpStatus());
    }
	/**
     * Metodo que devuelve el catalogo de rango de edades de campanias
     * 
     * @return ResponseEntity
     */
    @GetMapping(value = "/campanias/RangoEdades")
    @ApiOperation(value = "Devuelve catalogo de rango de edades de campanias")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found") })
    public ResponseEntity<ResponseApi> getCatRangoEdadesCampanias() {

        ResponseApi resultado = catalogosService.getCatRangoEdadesCampanias();

        return new ResponseEntity<>(resultado, resultado.getHttpStatus());
    }
}
