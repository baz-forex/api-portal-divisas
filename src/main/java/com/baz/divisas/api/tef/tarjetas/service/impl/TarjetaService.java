package com.baz.divisas.api.tef.tarjetas.service.impl;

import com.baz.divisas.api.tef.tarjetas.dto.ReqTarjetasMasivas;
import com.baz.divisas.api.tef.tarjetas.dto.RespTarjetasMasivas;
import com.baz.divisas.api.tef.tarjetas.dto.RespTarjetasMasivasGeneral;
import com.baz.divisas.api.tef.tarjetas.service.ITarjetaService;
import com.baz.divisas.general.config.PropClientes;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.baz.divisas.general.model.Constant.INT_5;

/**
 * The Class TarjetaService.
 */
@Service
public class TarjetaService implements ITarjetaService {

  @Resource
  private WebClient client;

  /**
   * Constructor de la clase.
   */
  public TarjetaService() {
    super();
    HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    this.client = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }

  /**
   * Carga archivo Tarjetas.
   *
   * @param file the archivo
   * @return the response api
   */
  @Override
  public ResponseApi solicitudTarjetaMasiva(MultipartFile file) {

    //lista de las cadenas
    List<String> lineasProcesadas = new ArrayList<>();
    //Buffer lectura
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
      //Proceso para leer las lineas de los strings
      lineasProcesadas = reader.lines()
          .skip(1)
          .map((String linea) -> {
            String[] partes = linea.split(",");
            if (partes.length > INT_5) {
              String sucuGestora = partes[0];
              String[] restantes = Arrays.copyOfRange(partes, INT_5, partes.length);
              return String.join(",", restantes).concat(",").concat(sucuGestora);
            }
            return "";
          })
          //Filtro para las peticiones
          .filter(linea -> !linea.isEmpty())
          //listado de las peticiones
          .collect(Collectors.toList());


    }
    catch (Exception e) {
      //Error al procesar la info
      LogApp.excepcion(e,"No se pudo procesar la informacion del tarjeta.");
    }

    if (!lineasProcesadas.isEmpty()) {
      ReqTarjetasMasivas reqTarjetasMasivas = new ReqTarjetasMasivas();
      reqTarjetasMasivas.setTerminal("0001");
      reqTarjetasMasivas.setSucursal("9546");
      //set de cadenas
      reqTarjetasMasivas.setSolicitudes(lineasProcesadas);
      //envio de peticion
      RespTarjetasMasivasGeneral respTarjetasMasivas = sendPostWsbcdMPA7(reqTarjetasMasivas);

      return new ResponseApi(EstatusResponse.OK, respTarjetasMasivas.getResultado());
    }

    return new ResponseApi(EstatusResponse.SIN_RESULTADOS);
  }

  /**
   * Busqueda tarjeta regalo.
   *
   * @param request the request
   * @return the res busqueda tarjeta reg
   */
  public RespTarjetasMasivasGeneral sendPostWsbcdMPA7(ReqTarjetasMasivas request) {

    //URl del servicio
    String url = PropClientes.getUrlMiddleware() + "pagos/divisas/guardadito-go/v1/tarjetas/masivo";
    LogApp.mensaje(request.toString());
    RespTarjetasMasivasGeneral response = new RespTarjetasMasivasGeneral();
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    try {
      //Envio de la peticion
      return client.post()
          .uri(url)
          .contentType(MediaType.APPLICATION_JSON)
          .headers(h -> h.addAll(headers))
          .bodyValue(request)
          .retrieve()
          .bodyToMono(RespTarjetasMasivasGeneral.class).block();
    }
    catch (RuntimeException e) {
      // error controlado
      LogApp.excepcion(new Exception(e), "Ocurrió un error al solicitar tarjetas Masivas");
      response.setMensaje(e.getMessage());
    }

    return response;
  }
}


