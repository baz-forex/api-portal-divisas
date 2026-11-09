package com.baz.divisas.api.tef.limites.cliente;

import com.baz.divisas.api.tef.limites.controller.LimitesPorClienteController;
import com.baz.divisas.api.tef.limites.dto.LimitesPorClienteDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorClienteService;
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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LimitesPorClienteControllerTest {

    @InjectMocks
    private LimitesPorClienteController limitesPorClienteController;

    @Mock
    private ILimitesPorClienteService limitesPorClienteService;

    @Test
    void testGetLimitesCliente() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorClienteService.consultaLimitesCliente()).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorClienteController.getLimitesCliente();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testRegistraLimiteCliente() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorClienteService.creaLimiteCliente(isA(LimitesPorClienteDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorClienteController.registraLimiteCliente(new LimitesPorClienteDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteCliente() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorClienteService.actualizaLimiteCliente(isA(LimitesPorClienteDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorClienteController.actualizaLimiteCliente(new LimitesPorClienteDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaEstatusLimiteCliente() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorClienteService.actualizaEstatusLimiteCliente(isA(String.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorClienteController.actualizaEstatusLimiteCliente("");
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testGetLimitesClienteOLP() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorClienteService.consultaLimitesClienteOlp()).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorClienteController.getLimitesClienteOlp();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

}