package com.baz.divisas.api.tef.spots;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.spots.controller.SpotsController;
import com.baz.divisas.api.tef.spots.dto.ReqSpots;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.baz.divisas.api.tef.spots.services.impl.SpotsServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class SpotControllerTest {
	
    @InjectMocks
    private SpotsController spotController;
    @Mock
    private SpotsServiceImpl spotServices;

    @Test
    void testGetSpots() {
    	
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.consultaSpots()).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = spotController.getSpots();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testGetSpotsPorFecha(){
    	
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.consultaSpotPorFecha(isA(String.class), isA(String.class), anyList())).thenReturn(respApi);
        
        ReqSpots request = new ReqSpots();
        request.setIdDivisas(Arrays.asList(""));
        request.getIdDivisas();
        request.setFechaInicio("");
        request.getFechaInicio();
        request.setFechaFin("");
        request.getFechaFin();
        
        ResponseEntity<ResponseApi> response = spotController.getSpotsPorFecha(request);
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    void testGetSpotsVigentes() {

        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.consultaSpotsVigentes(anyList())).thenReturn(respApi);
        
        ReqSpots request = new ReqSpots();
        request.setIdDivisas(Arrays.asList(""));
        
        ResponseEntity<ResponseApi> response = spotController.getSpotsVigentes(request);
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testRegistraSpot() {
        
        Spot spot = new Spot();
        spot.setId("");
        spot.getId();
        spot.setTipo("");
        spot.getTipo();
        spot.setIdDivisa("USD");
        spot.getIdDivisa();
        spot.setCompraEfectivo(31.1);
        spot.getCompraEfectivo();
        spot.setCompraAbono(41.1);
        spot.getCompraAbono();
        spot.setVentaEfectivo(31.1);
        spot.getVentaEfectivo();
        spot.setVentaCargo(41.1);
        spot.getVentaCargo();
        spot.setFechaRegistro("2022-12-03");
        spot.getFechaRegistro();
        spot.setHoraRegistro("12:56:00");
        spot.getHoraRegistro();
        spot.setIdBitacora("");
        spot.getIdBitacora();
        spot.toString();
        List<Spot> spots = new ArrayList<Spot>();
        spots.add(spot);
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.creaSpots(any())).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = spotController.registraSpot(spots);
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void testRegistraSpotOlp() {
        
        Spot spot = new Spot();
        spot.setId("");
        spot.getId();
        spot.setTipo("");
        spot.getTipo();
        spot.setIdDivisa("OLP");
        spot.getIdDivisa();
        spot.setCompraEfectivo(31.1);
        spot.getCompraEfectivo();
        spot.setCompraAbono(41.1);
        spot.getCompraAbono();
        spot.setVentaEfectivo(31.1);
        spot.getVentaEfectivo();
        spot.setVentaCargo(41.1);
        spot.getVentaCargo();
        spot.setFechaRegistro("2022-12-03");
        spot.getFechaRegistro();
        spot.setHoraRegistro("12:56:00");
        spot.getHoraRegistro();
        spot.setIdBitacora("");
        spot.getIdBitacora();
        spot.setTipo("Bloomberg");
        spot.getTipo();
        spot.toString();
        
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.creaSpotOlp(isA(Spot.class))).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = spotController.registraSpotOlp(spot);
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    public void testgetSpotsTesoreria() {
       
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.consultaSpotsTesoreria()).thenReturn(respApi);
        
        ResponseEntity<ResponseApi> response = spotController.getSpotsTesoreria();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    public void testGetSpotVigentesOLP() {

        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.consultaSpotsVigentesOLP()).thenReturn(respApi);

        ResponseEntity<ResponseApi> response = spotController.getSpotVigentesOLP();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetConfiguracionSpotOLP() {

        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.consultaConfiguracionSpotOLP()).thenReturn(respApi);

        ResponseEntity<ResponseApi> response = spotController.getConfiguracionSpotOLP();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testRegistraConfiguracionSpotOlp() {

        com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot req =
                new com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot();
        ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
        when(spotServices.guardaConfiguracionSpotOLP(isA(com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot.class)))
                .thenReturn(respApi);

        ResponseEntity<ResponseApi> response = spotController.registraConfiguracionSpotOlp(req);
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
}