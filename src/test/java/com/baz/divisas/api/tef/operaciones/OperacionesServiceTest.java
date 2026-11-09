package com.baz.divisas.api.tef.operaciones;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.catalogos.dto.RespTiposOperacion;
import com.baz.divisas.api.tef.catalogos.service.impl.CatalogosService;
import com.baz.divisas.general.dto.ResponseApi;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OperacionesServiceTest {
	
    @InjectMocks
    private CatalogosService tiposOperacionService;
    @Mock
    private CatalogosDao tiposOperacionDao;
    
    @Test
    void testGet() {
        
        List<Catalogo> tiposOperacion = new ArrayList<>();
        tiposOperacion.add(new Catalogo());
        
        RespTiposOperacion operacionResponse = new RespTiposOperacion(tiposOperacion);
        operacionResponse.getListOperaciones();
        operacionResponse.setListOperaciones(tiposOperacion);
        
        when(tiposOperacionDao.findTiposOperacion()).thenReturn(Arrays.asList());
        ResponseApi response = tiposOperacionService.getTiposOperacion();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
                
        when(tiposOperacionDao.findTiposOperacion()).thenReturn(tiposOperacion);
        response = tiposOperacionService.getTiposOperacion();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    void testActualizaEstatus() {
    	
    	Catalogo tipoOperacion = new Catalogo();
    	tipoOperacion.setId("");
    	tipoOperacion.setActivo(true);
    	
        when(tiposOperacionDao.existeTipoOperacion(isA(String.class))).thenReturn(false);
        ResponseApi response = tiposOperacionService.actualizaEstatusTipoOperacion(tipoOperacion);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        
        when(tiposOperacionDao.existeTipoOperacion(isA(String.class))).thenReturn(true);
        when(tiposOperacionDao.updateEstatusTipoOperacion(isA(String.class), isA(boolean.class))).thenReturn(true);
        response = tiposOperacionService.actualizaEstatusTipoOperacion(tipoOperacion);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

}