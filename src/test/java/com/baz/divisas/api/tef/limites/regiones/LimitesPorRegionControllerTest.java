package com.baz.divisas.api.tef.limites.regiones;

import com.baz.divisas.api.tef.limites.controller.LimitesPorRegionController;
import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorRegionService;
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
class LimitesPorRegionControllerTest {

    @InjectMocks
    private LimitesPorRegionController limitesPorRegionController;

    @Mock
    private ILimitesPorRegionService limitesPorRegionService;

    @Test
    void testGetLimitesRegion() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorRegionService.consultaLimitesRegion()).thenReturn(responseApi);
        ResponseEntity<ResponseApi> response = limitesPorRegionController.getLimitesRegion();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testRegistraLimiteRegion() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorRegionService.creaLimiteRegion(isA(LimitesPorRegionDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorRegionController.registraLimiteRegion(new LimitesPorRegionDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteRegion() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorRegionService.actualizaLimiteRegion(isA(LimitesPorRegionDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorRegionController.actualizaLimiteRegion(new LimitesPorRegionDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaEstatusLimiteRegion() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorRegionService.actualizaEstatusLimiteRegion(isA(LimitesPorRegionDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorRegionController.actualizaEstatusLimiteRegion(new LimitesPorRegionDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    //OLP
    @Test
    void testGetLimitesRegionOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorRegionService.consultaLimitesRegionOlp()).thenReturn(responseApi);
        ResponseEntity<ResponseApi> response = limitesPorRegionController.getLimitesRegionOlp();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testRegistraLimiteRegionOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorRegionService.creaLimiteRegionOlp(isA(LimitesPorRegionDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorRegionController.registraLimiteRegionOlp(new LimitesPorRegionDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteRegionOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorRegionService.actualizaLimiteRegionOlp(isA(LimitesPorRegionDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorRegionController.actualizaLimiteRegionOlp(new LimitesPorRegionDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaEstatusLimiteRegionOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorRegionService.actualizaEstatusLimiteRegionOlp(isA(LimitesPorRegionDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorRegionController.actualizaEstatusLimiteRegionOlp(new LimitesPorRegionDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

}