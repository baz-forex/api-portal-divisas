package com.baz.divisas.api.tef.tarjetas.controller;

import com.baz.divisas.api.tef.tarjetas.service.impl.TarjetaService;
import com.baz.divisas.general.dto.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Controlador Tarjetas.
 *
 * @author 1027346
 */
@RestController
@RequestMapping("api/tef/ggo/tarjetas")
@Api(tags = "TEF/GGO/tarjetas")
@Validated
public class TarjetasMasivasController {

  /**
   * The tarjetas service.
   */
  @Autowired
  private TarjetaService tarjetaService;

  /**
   * Solicita Tarjetas masivas.
   *
   * @param file the request
   * @return the response entity
   */
  @PostMapping(value = "/solicitud/masiva")
  @ApiOperation(value = "Solicita Tarjetas masivas para guardadito go")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
      @ApiResponse(code = 204, message = "Not Found")})
  public ResponseEntity<ResponseApi> solicitudTarjetaMAsiva(@Valid @RequestParam MultipartFile file) {

    //servicio de solicitud de tarjeta
    ResponseApi resultado = tarjetaService.solicitudTarjetaMasiva(file);

    //response del servicios
    return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
}
