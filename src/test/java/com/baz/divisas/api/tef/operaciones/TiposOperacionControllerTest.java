package com.baz.divisas.api.tef.operaciones;
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

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.catalogos.TiposOperacionController;
import com.baz.divisas.api.tef.catalogos.service.impl.CatalogosService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class TiposOperacionControllerTest {
	
    @InjectMocks
    private TiposOperacionController tiposOperacionController;
    @Mock
    private CatalogosService tiposOperacionService;
    
    @Test
    void testGet() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(tiposOperacionService.getTiposOperacion()).thenReturn(responseApi);
        ResponseEntity<ResponseApi> response = tiposOperacionController.get();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    void testActualizaEstatus() {
        ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
        when(tiposOperacionService.actualizaEstatusTipoOperacion(isA(Catalogo.class))).thenReturn(responseApi);
        ResponseEntity<ResponseApi> response = tiposOperacionController.actualizaEstatus(new Catalogo());
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

}