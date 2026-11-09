package com.baz.divisas.api.tef.limites.sucursal;

import com.baz.divisas.api.tef.limites.controller.LimitesPorSucursalController;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorSucursalService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;


@ExtendWith(MockitoExtension.class)
class LimitesPorSucursalControllerTest {

    @InjectMocks
    private LimitesPorSucursalController limitesPorSucursalController;

    @Mock
    private ILimitesPorSucursalService limitesPorSucursalService;

    @Test
    void testGetLimitesSucursal() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSucursalService.consultaLimitesSucursal()).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSucursalController.getLimitesSucursal();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testRegistraLimiteSucursal() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSucursalService.creaLimiteSucursal(isA(LimitesPorSucursalDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSucursalController.registraLimiteSucursal(new LimitesPorSucursalDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteSucursal() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSucursalService.actualizaLimiteSucursal(isA(LimitesPorSucursalDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSucursalController.actualizaLimiteSucursal(new LimitesPorSucursalDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testActualizaEstatusLimiteSucursal() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSucursalService.actualizaEstatusLimiteSucursal(isA(LimitesPorSucursalDto.class))).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSucursalController.actualizaEstatusLimiteSucursal(new LimitesPorSucursalDto());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    //OLP
    
    @Test
    void testGetLimitesSucursalOlp() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(limitesPorSucursalService.consultaLimitesSucursalOlp()).thenReturn(responseApi);
        
        ResponseEntity<ResponseApi> response = limitesPorSucursalController.getLimitesSucursalOlp();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    void testCargaArchivoLimites() {
    	ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(limitesPorSucursalService.cargaArchivoLimites(any())).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = limitesPorSucursalController.cargaArchivoLimites(null);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
	public void testDescargaLimitesOLP() throws UnsupportedEncodingException {
		String message = "test";
		ByteArrayInputStream respApi = new ByteArrayInputStream(message.getBytes("UTF-8"));
		when(limitesPorSucursalService.descargaLimitesSucursales(any())).thenReturn(respApi);			
		ResponseEntity<Resource> response = limitesPorSucursalController.descargaLimitesOLP("OLP");		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
    
    @Test
   public void testDescargaLimites() throws UnsupportedEncodingException {
   		String message = "test";
   		ByteArrayInputStream respApi = new ByteArrayInputStream(message.getBytes("UTF-8"));
   		when(limitesPorSucursalService.descargaLimitesSucursales(any())).thenReturn(respApi);	
   		
   		ResponseEntity<Resource> response = limitesPorSucursalController.descargaLimites();
   		
   		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
   	}
}
