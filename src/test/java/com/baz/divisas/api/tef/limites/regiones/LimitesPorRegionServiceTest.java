package com.baz.divisas.api.tef.limites.regiones;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;

import com.baz.divisas.api.tef.limites.dao.LimitesPorRegionDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.baz.divisas.api.tef.limites.services.impl.LimitesPorRegionService;
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
class LimitesPorRegionServiceTest {
    @InjectMocks
    private LimitesPorRegionService limitesPorRegionService;

    @Mock
    private LimitesPorRegionDao limitesPorRegionDao;
    @Mock
    private BitacoraServiceImpl bitacoraService;

    @Test
    void testConsultaLimitesRegion() {

        when(limitesPorRegionDao.findLimitesRegion()).thenReturn(Arrays.asList());
        ResponseApi response = limitesPorRegionService.consultaLimitesRegion();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorRegionDao.findLimitesRegion()).thenReturn(Arrays.asList(new LimitesPorRegionDto()));
        response = limitesPorRegionService.consultaLimitesRegion();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());


    }

    @Test
    void testCreaLimiteRegion() {

        when(limitesPorRegionDao.findLimitesRegionId(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

        ResponseApi response = limitesPorRegionService.creaLimiteRegion(new LimitesPorRegionDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorRegionDao.findLimitesRegionId(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        response = limitesPorRegionService.creaLimiteRegion(new LimitesPorRegionDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteRegion() {

        when(limitesPorRegionDao.findLimitesRegionId(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorRegionService.actualizaLimiteRegion(new LimitesPorRegionDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorRegionDao.findLimitesRegionId(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

        response = limitesPorRegionService.actualizaLimiteRegion(new LimitesPorRegionDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testActualizaEstatusLimiteRegion() {

    	LimitesPorRegionDto limite = new LimitesPorRegionDto();
    	
        when(limitesPorRegionDao.findLimitesRegionId(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorRegionService.actualizaEstatusLimiteRegion(limite);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorRegionDao.findLimitesRegionId(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorRegionDto()));
    	limite.setActivo(true);

        response = limitesPorRegionService.actualizaEstatusLimiteRegion(limite);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    
    //OLP
    
    @Test
    void testConsultaLimitesRegionOlp() {

        when(limitesPorRegionDao.findLimitesRegionOlp()).thenReturn(Arrays.asList());
        ResponseApi response = limitesPorRegionService.consultaLimitesRegionOlp();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorRegionDao.findLimitesRegionOlp()).thenReturn(Arrays.asList(new LimitesPorRegionDto()));
        response = limitesPorRegionService.consultaLimitesRegionOlp();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());


    }

    @Test
    void testCreaLimiteRegionOlp() {

        when(limitesPorRegionDao.findLimitesRegionIdOlp(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

        ResponseApi response = limitesPorRegionService.creaLimiteRegionOlp(new LimitesPorRegionDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorRegionDao.findLimitesRegionIdOlp(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        response = limitesPorRegionService.creaLimiteRegionOlp(new LimitesPorRegionDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteRegionOlp() {

        when(limitesPorRegionDao.findLimitesRegionIdOlp(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorRegionService.actualizaLimiteRegionOlp(new LimitesPorRegionDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorRegionDao.findLimitesRegionIdOlp(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

        response = limitesPorRegionService.actualizaLimiteRegionOlp(new LimitesPorRegionDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testActualizaEstatusLimiteRegionOlp() {

    	LimitesPorRegionDto limite = new LimitesPorRegionDto();
    	
        when(limitesPorRegionDao.findLimitesRegionIdOlp(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorRegionService.actualizaEstatusLimiteRegionOlp(limite);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorRegionDao.findLimitesRegionIdOlp(isA(LimitesPorRegionDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorRegionDto()));
    	limite.setActivo(true);

        response = limitesPorRegionService.actualizaEstatusLimiteRegionOlp(limite);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
}