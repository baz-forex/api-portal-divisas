package com.baz.divisas.api.tef.limites.segmentos;

import com.baz.divisas.api.tef.limites.controller.LimitesPorSegmentoController;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSegmentoDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorSegmentoService;
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
class LimitesPorSegmentoControllerTest {

    @InjectMocks
    private LimitesPorSegmentoController limitesPorSegmentoController;

    @Mock
    private ILimitesPorSegmentoService limitesPorSegmentoService;

    @Test
    void testGetLimitesRegion() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSegmentoService.consultaLimitesSegmento()).thenReturn(responseApi);
        ResponseEntity<ResponseApi> response = limitesPorSegmentoController.getLimitesSegmento();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testRegistraLimiteSegmento() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSegmentoService.creaLimiteSegmento(isA(LimitesPorSegmentoDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSegmentoController.registraLimiteSegmento(new LimitesPorSegmentoDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaLimiteSegmento() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSegmentoService.actualizaLimiteSegmento(isA(LimitesPorSegmentoDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSegmentoController.actualizaLimiteSegmento(new LimitesPorSegmentoDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaEstatusLimiteSegmento() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSegmentoService.actualizaEstatusLimiteSegmento(isA(LimitesPorSegmentoDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSegmentoController.actualizaEstatusLimiteSegmento(new LimitesPorSegmentoDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    //OLP
    
    @Test
    void testGetLimitesRegionOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSegmentoService.consultaLimitesSegmentoOlp()).thenReturn(responseApi);
        ResponseEntity<ResponseApi> response = limitesPorSegmentoController.getLimitesSegmentoOlp();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testRegistraLimiteSegmentoOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSegmentoService.creaLimiteSegmentoOlp(isA(LimitesPorSegmentoDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSegmentoController.registraLimiteSegmentoOlp(new LimitesPorSegmentoDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaLimiteSegmentoOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSegmentoService.actualizaLimiteSegmentoOlp(isA(LimitesPorSegmentoDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSegmentoController.actualizaLimiteSegmentoOlp(new LimitesPorSegmentoDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaEstatusLimiteSegmentoOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSegmentoService.actualizaEstatusLimiteSegmentoOlp(isA(LimitesPorSegmentoDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSegmentoController.actualizaEstatusLimiteSegmentoOlp(new LimitesPorSegmentoDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

}