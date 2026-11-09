package com.baz.divisas.api.tef.limites.divisas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.limites.controller.LimitesPorDivisaController;
import com.baz.divisas.api.tef.limites.services.impl.LimitesPorDivisaService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class LimitesPorDivisaControllerTest {

    @InjectMocks
    private LimitesPorDivisaController limitesController;

    @Mock
    private LimitesPorDivisaService limitesServices;

    @Test
    void testGetLimitesDivisa() {
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(limitesServices.consultaLimitesDivisa()).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = limitesController.getLimitesDivisa();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    void testRegistraLimiteDivisa() {
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(limitesServices.creaLimiteDivisa(isA(Limites.class))).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = limitesController.registraLimiteDivisa(new Limites());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaLimiteDivisa() {
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(limitesServices.actualizaLimiteDivisa(isA(Limites.class))).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = limitesController.actualizaLimiteDivisa(new Limites());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    void testActualizaEstatusLimiteDivisa() {
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(limitesServices.actualizaEstatusLimiteDivisa(isA(Limites.class))).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = limitesController.actualizaEstatusLimiteDivisa(new Limites());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testgetLimitesDivisaOLP() {
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(limitesServices.consultaLimitesDivisaOLP()).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = limitesController.getLimitesDivisaOLP();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
}