package com.baz.divisas.api.tef.tarjetas.controller;

import com.baz.divisas.api.tef.tarjetas.service.impl.TarjetaService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarjetasMasivasControllerTest {

  @InjectMocks
  private TarjetasMasivasController tarjetaController;

  @Mock
  TarjetaService tarjetaService;

  @Test
  void solicitudTarjetaMAsiva(){
    // Arrange
    ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
    when(tarjetaService.solicitudTarjetaMasiva(any())).thenReturn(respApi);

    // Act
    ResponseEntity<ResponseApi> response = tarjetaController.solicitudTarjetaMAsiva(null);

    // Assert
    assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
  }
}