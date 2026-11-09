package com.baz.divisas.api.tef.limites.cliente;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.limites.dao.LimitesPorClienteDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorClienteDto;
import com.baz.divisas.api.tef.limites.services.impl.LimitesPorClienteService;
import com.baz.divisas.general.dto.ResponseApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;


@ExtendWith(MockitoExtension.class)
class LimitesPorClienteServiceTest {
    @InjectMocks
    private LimitesPorClienteService limitesPorClienteService;

    @Mock
    private LimitesPorClienteDao limitesPorClienteDao;
    @Mock
    private BitacoraServiceImpl bitacoraService;

    @Test
    void testConsultaLimitesCliente() {

        when(limitesPorClienteDao.findLimitesCliente()).thenReturn(Arrays.asList());
        
        ResponseApi response = limitesPorClienteService.consultaLimitesCliente();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorClienteDao.findLimitesCliente()).thenReturn(Arrays.asList(new LimitesPorClienteDto()));
        response = limitesPorClienteService.consultaLimitesCliente();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testCreaLimiteCliente() {

        when(limitesPorClienteDao.findLimitesClienteId(isA(LimitesPorClienteDto.class))).thenReturn(new LimitesPorClienteDto());

        ResponseApi response = limitesPorClienteService.creaLimiteCliente(new LimitesPorClienteDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorClienteDao.findLimitesClienteId(isA(LimitesPorClienteDto.class))).thenReturn(null);

        response = limitesPorClienteService.creaLimiteCliente(new LimitesPorClienteDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteCliente() {

    	when(limitesPorClienteDao.findLimitesClienteId(isA(String.class))).thenReturn(null);

    	LimitesPorClienteDto request = new LimitesPorClienteDto();
    	request.setId("");
    	request.setIdClienteDivisas("");
    	
        ResponseApi response = limitesPorClienteService.actualizaLimiteCliente(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        when(limitesPorClienteDao.findLimitesClienteId(isA(String.class))).thenReturn(new LimitesPorClienteDto());
        
        response = limitesPorClienteService.actualizaLimiteCliente(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testActualizaEstatusLimiteCliente() {

    	when(limitesPorClienteDao.findLimitesClienteId(isA(String.class))).thenReturn(null);

        ResponseApi response = limitesPorClienteService.actualizaEstatusLimiteCliente("");
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        LimitesPorClienteDto limite = new LimitesPorClienteDto();
        
        when(limitesPorClienteDao.findLimitesClienteId(isA(String.class))).thenReturn(limite);
        
        response = limitesPorClienteService.actualizaEstatusLimiteCliente("");
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
        
        // -------------------------------
        limite.setActivo(true);
        
        when(limitesPorClienteDao.findLimitesClienteId(isA(String.class))).thenReturn(limite);
        
        response = limitesPorClienteService.actualizaEstatusLimiteCliente("");
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
        
    }
    
    @Test
    void testConsultaLimitesClienteOlp() {

        when(limitesPorClienteDao.findLimitesClienteOlp()).thenReturn(Arrays.asList());
        
        ResponseApi response = limitesPorClienteService.consultaLimitesClienteOlp();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorClienteDao.findLimitesClienteOlp()).thenReturn(Arrays.asList(new LimitesPorClienteDto()));
        response = limitesPorClienteService.consultaLimitesClienteOlp();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

}