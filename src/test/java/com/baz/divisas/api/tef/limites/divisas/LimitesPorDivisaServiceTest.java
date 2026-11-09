package com.baz.divisas.api.tef.limites.divisas;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.limites.dao.LimitesPorDivisaDao;
import com.baz.divisas.api.tef.limites.services.impl.LimitesPorDivisaService;
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
class LimitesPorDivisaServiceTest {
	
    @InjectMocks
    private LimitesPorDivisaService limitesPorDivisaService;

    @Mock
    private LimitesPorDivisaDao limitesPorDivisaDao;
    @Mock
    private BitacoraServiceImpl bitacoraService;

    @Test
    void testConsultaLimitesDivisa() {

        when(limitesPorDivisaDao.findLimitesDivias()).thenReturn(Arrays.asList());
        ResponseApi response = limitesPorDivisaService.consultaLimitesDivisa();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorDivisaDao.findLimitesDivias()).thenReturn(Arrays.asList(new Limites()));
        response = limitesPorDivisaService.consultaLimitesDivisa();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testCreaLimiteDivisa() {

        when(limitesPorDivisaDao.findLimitesDiviasId(isA(Limites.class))).thenReturn(Arrays.asList(new Limites()));

        ResponseApi response = limitesPorDivisaService.creaLimiteDivisa(new Limites());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorDivisaDao.findLimitesDiviasId(isA(Limites.class))).thenReturn(Arrays.asList());

        response = limitesPorDivisaService.creaLimiteDivisa(new Limites());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testActualizaLimiteDivisa() {

        when(limitesPorDivisaDao.findLimitesDiviasId(isA(Limites.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorDivisaService.actualizaLimiteDivisa(new Limites());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorDivisaDao.findLimitesDiviasId(isA(Limites.class))).thenReturn(Arrays.asList(new Limites()));

        response = limitesPorDivisaService.actualizaLimiteDivisa(new Limites());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testActualizaEstatusLimiteDivisa() {

        when(limitesPorDivisaDao.findLimitesDiviasId(isA(Limites.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorDivisaService.actualizaEstatusLimiteDivisa(new Limites());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        when(limitesPorDivisaDao.findLimitesDiviasId(isA(Limites.class))).thenReturn(Arrays.asList(new Limites()));

        response = limitesPorDivisaService.actualizaEstatusLimiteDivisa(new Limites());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testConsultaLimitesDivisaOLP() {

        when(limitesPorDivisaDao.findLimitesDivisaOLP()).thenReturn(Arrays.asList());
        ResponseApi response = limitesPorDivisaService.consultaLimitesDivisaOLP();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorDivisaDao.findLimitesDivisaOLP()).thenReturn(Arrays.asList(new Limites()));
        response = limitesPorDivisaService.consultaLimitesDivisaOLP();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
}