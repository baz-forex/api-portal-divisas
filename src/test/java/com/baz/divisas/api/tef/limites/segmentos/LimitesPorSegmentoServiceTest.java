package com.baz.divisas.api.tef.limites.segmentos;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;

import com.baz.divisas.api.tef.limites.dao.LimitesPorSegmentoDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSegmentoDto;
import com.baz.divisas.api.tef.limites.services.impl.LimitesPorSegmentoService;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.general.dto.ResponseApi;

import org.bson.types.ObjectId;
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
class LimitesPorSegmentoServiceTest {
    @InjectMocks
    private LimitesPorSegmentoService limitesPorSegmentoService;

    @Mock
    private LimitesPorSegmentoDao limitesPorSegmentoDao;
    @Mock
    private RegionesDao regionesDao;
    @Mock
    private BitacoraServiceImpl bitacoraService;

    @Test
    void testConsultaLimitesSegmento() {

        when(limitesPorSegmentoDao.findLimitesSegmento()).thenReturn(Arrays.asList());
        ResponseApi response = limitesPorSegmentoService.consultaLimitesSegmento();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorSegmentoDao.findLimitesSegmento()).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));
        response = limitesPorSegmentoService.consultaLimitesSegmento();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testCreaLimiteRegion() {

    	LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setIdSegmento(ObjectId.get().toHexString());
    	    	
        when(regionesDao.findByRegionSegmento(isA(String.class), isA(String.class))).thenReturn(null);

        ResponseApi response = limitesPorSegmentoService.creaLimiteSegmento(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        // -------------------------------
        
        when(regionesDao.findByRegionSegmento(isA(String.class), isA(String.class))).thenReturn(new Region());
        when(limitesPorSegmentoDao.findLimitesSegmentoId(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));

        response = limitesPorSegmentoService.creaLimiteSegmento(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        when(limitesPorSegmentoDao.findLimitesSegmentoId(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        response = limitesPorSegmentoService.creaLimiteSegmento(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteSegmento() {

    	LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setIdSegmento(ObjectId.get().toHexString());
    	    	
        when(regionesDao.findByRegionSegmento(isA(String.class), isA(String.class))).thenReturn(null);

        ResponseApi response = limitesPorSegmentoService.actualizaLimiteSegmento(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        // -------------------------------
        
        when(regionesDao.findByRegionSegmento(isA(String.class), isA(String.class))).thenReturn(new Region());
        when(limitesPorSegmentoDao.findLimitesSegmentoId(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        response = limitesPorSegmentoService.actualizaLimiteSegmento(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        when(limitesPorSegmentoDao.findLimitesSegmentoId(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));

        response = limitesPorSegmentoService.actualizaLimiteSegmento(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testActualizaEstatusLimiteSegmento() {

    	LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setIdSegmento(ObjectId.get().toHexString());

        when(limitesPorSegmentoDao.findLimitesSegmentoId(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorSegmentoService.actualizaEstatusLimiteSegmento(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        request.setActivo(true);
        when(limitesPorSegmentoDao.findLimitesSegmentoId(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));

        response = limitesPorSegmentoService.actualizaEstatusLimiteSegmento(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    //OLP
    
    @Test
    void testConsultaLimitesSegmentoOlp() {

        when(limitesPorSegmentoDao.findLimitesSegmentoOlp()).thenReturn(Arrays.asList());
        ResponseApi response = limitesPorSegmentoService.consultaLimitesSegmentoOlp();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorSegmentoDao.findLimitesSegmentoOlp()).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));
        response = limitesPorSegmentoService.consultaLimitesSegmentoOlp();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testCreaLimiteRegionOlp() {

    	LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setIdSegmento(ObjectId.get().toHexString());
    	    	
        when(regionesDao.findByRegionSegmentoOlp(isA(String.class), isA(String.class))).thenReturn(null);

        ResponseApi response = limitesPorSegmentoService.creaLimiteSegmentoOlp(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        // -------------------------------
        
        when(regionesDao.findByRegionSegmentoOlp(isA(String.class), isA(String.class))).thenReturn(new Region());
        when(limitesPorSegmentoDao.findLimitesSegmentoIdOlp(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));

        response = limitesPorSegmentoService.creaLimiteSegmentoOlp(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        when(limitesPorSegmentoDao.findLimitesSegmentoIdOlp(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        response = limitesPorSegmentoService.creaLimiteSegmentoOlp(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteSegmentoOlp() {

    	LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setIdSegmento(ObjectId.get().toHexString());
    	    	
        when(regionesDao.findByRegionSegmentoOlp(isA(String.class), isA(String.class))).thenReturn(null);

        ResponseApi response = limitesPorSegmentoService.actualizaLimiteSegmentoOlp(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        // -------------------------------
        
        when(regionesDao.findByRegionSegmentoOlp(isA(String.class), isA(String.class))).thenReturn(new Region());
        when(limitesPorSegmentoDao.findLimitesSegmentoIdOlp(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        response = limitesPorSegmentoService.actualizaLimiteSegmentoOlp(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        when(limitesPorSegmentoDao.findLimitesSegmentoIdOlp(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));

        response = limitesPorSegmentoService.actualizaLimiteSegmentoOlp(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testActualizaEstatusLimiteSegmentoOlp() {

    	LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setIdSegmento(ObjectId.get().toHexString());

        when(limitesPorSegmentoDao.findLimitesSegmentoIdOlp(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorSegmentoService.actualizaEstatusLimiteSegmentoOlp(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        request.setActivo(true);
        when(limitesPorSegmentoDao.findLimitesSegmentoIdOlp(isA(LimitesPorSegmentoDto.class), isA(boolean.class))).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));

        response = limitesPorSegmentoService.actualizaEstatusLimiteSegmentoOlp(request);
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

}