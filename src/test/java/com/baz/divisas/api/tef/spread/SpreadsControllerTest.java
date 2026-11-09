package com.baz.divisas.api.tef.spread;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.spread.controller.SpreadsController;
import com.baz.divisas.api.tef.spread.dto.Spread;
import com.baz.divisas.api.tef.spread.services.impl.SpreadsServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpreadsControllerTest {

    @InjectMocks
    private SpreadsController spreadsController;
    @Mock
    private SpreadsServiceImpl spreadsServices;
    

    @Test
    void testGetSpreads() {
    	
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spreadsServices.consultaSpreads()).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = spreadsController.getSpreads();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testGetSpreadsDivisa() {
    	
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spreadsServices.consultaSpreadsDivisa(isA(String.class))).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = spreadsController.getSpreadsDivisa("");
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testUpdateSpreadsDivisa() {
    	
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spreadsServices.actualizaSpreadDivisa(isA(Spread.class))).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = spreadsController.updateSpread(new Spread());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

}