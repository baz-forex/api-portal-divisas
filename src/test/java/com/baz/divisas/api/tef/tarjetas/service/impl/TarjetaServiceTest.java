package com.baz.divisas.api.tef.tarjetas.service.impl;

import com.baz.divisas.api.tef.tarjetas.dto.ReqTarjetasMasivas;
import com.baz.divisas.api.tef.tarjetas.dto.RespTarjetasMasivas;
import com.baz.divisas.api.tef.tarjetas.dto.RespTarjetasMasivasGeneral;
import com.baz.divisas.general.dto.ResponseApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarjetaServiceTest {

  @InjectMocks
  TarjetaService tarjetaService;


  @Mock
  private WebClient webClient;

  @Mock
  private WebClient.RequestBodyUriSpec requestBodyUriSpec;

  @Mock
  private WebClient.RequestHeadersSpec requestHeadersSpec;

  @Mock
  private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

  @Mock
  private WebClient.ResponseSpec responseSpec;


  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  void solicitudTarjetaMasiva() throws IOException {

    String contenido = "Header1,Header2,Header3,Header4,Header5,CampoExtra1,CampoExtra2\n"
        + "9546,A,B,C,D,E,F\n"
        + "9546,X,Y,Z,W,G,H\n";

    MultipartFile mockFile = mock(MultipartFile.class);

    when(mockFile.getInputStream()).thenReturn(
        new ByteArrayInputStream(contenido.getBytes(StandardCharsets.UTF_8))
    );

    ResponseApi respuesta = tarjetaService.solicitudTarjetaMasiva(mockFile);

    assertNotNull(respuesta);

  }

  @Test
  void solicitudTarjetaMasivaSinDatos() throws IOException {

    String contenido = "Header1,Header2\n"
        + "9546,A\n"
        + "9546,X\n";

    MultipartFile mockFile = mock(MultipartFile.class);

    when(mockFile.getInputStream()).thenReturn(
        new ByteArrayInputStream(contenido.getBytes(StandardCharsets.UTF_8))
    );

    ResponseApi respuesta = tarjetaService.solicitudTarjetaMasiva(mockFile);

    assertNotNull(respuesta);

    respuesta = tarjetaService.solicitudTarjetaMasiva(mockFile);

    assertNotNull(respuesta);


  }

  @Test
  void sendPostWsbcdMPA7() {


    ReqTarjetasMasivas request = new ReqTarjetasMasivas();
    request.setTerminal("0001");
    request.setSucursal("9546");
    request.setSolicitudes(Arrays.asList("A,B,C,D,E,9546"));
    request.getTerminal();
    request.getSucursal();
    RespTarjetasMasivas expectedResponse = new RespTarjetasMasivas();
    expectedResponse.setMensaje("OK");


    when(webClient.post()).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.headers(any())).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.bodyValue(request)).thenReturn(requestHeadersSpec);
    when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.bodyToMono(RespTarjetasMasivas.class)).thenReturn(Mono.just(expectedResponse));

    RespTarjetasMasivasGeneral actualResponse = tarjetaService.sendPostWsbcdMPA7(request);

    assertNotNull(actualResponse);
  }

  @Test
  void sendPostWsbcdMPA7Error() {

    ReqTarjetasMasivas request = new ReqTarjetasMasivas();

    when(webClient.post()).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.headers(any())).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.bodyValue(request)).thenReturn(requestHeadersSpec);
    when(requestHeadersSpec.retrieve()).thenThrow(new RuntimeException("Error de red"));

    RespTarjetasMasivasGeneral actualResponse = tarjetaService.sendPostWsbcdMPA7(request);
    actualResponse.getResultado();
    assertNotNull(actualResponse);
    assertEquals("Error de red", actualResponse.getMensaje());

  }

}