package com.baz.divisas.api.tef.regionessegmentoestrategia.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dao.RegionesSegmentosEstrategiasDao;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.RequestSegmentoEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.ResponseSegmentoEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.service.impl.RegionesSegEstrategiasService;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegionSegEstrategiasServiceTest {
    
    @InjectMocks
    private RegionesSegEstrategiasService regionesSegEstrategiasService;
    
    @Mock
    RegionesSegmentosEstrategiasDao segmentoEstrategiasDao;
    @Mock
    RegionesDao regionesDao;
    @Mock
    CatalogosDao catalogosDao;
    @Mock
    BitacoraServiceImpl bitacoraServiceImpl;
    
	@Test
	void testConsultaEstrategiasSegmentos() {
		
		when(segmentoEstrategiasDao.findAll()).thenReturn(null);

		ResponseApi response = regionesSegEstrategiasService.consultaEstrategiasSegmentos();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(segmentoEstrategiasDao.findAll()).thenReturn(Arrays.asList());

		response = regionesSegEstrategiasService.consultaEstrategiasSegmentos();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		ResponseSegmentoEstrategia respEstrategia = new ResponseSegmentoEstrategia();
		respEstrategia.setAsignacion(true);
		respEstrategia.isAsignacion();
		respEstrategia.setIdRegion("");
		respEstrategia.getIdRegion();
		respEstrategia.setRegionDescripcion("");
		respEstrategia.getRegionDescripcion();
		
		when(segmentoEstrategiasDao.findAll()).thenReturn(Arrays.asList(respEstrategia));

		response = regionesSegEstrategiasService.consultaEstrategiasSegmentos();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
 
	@Test
	void testAsignarEstrategiaSegmento() {
		
		when(regionesDao.findByRegionSegmento(isA(String.class), isA(String.class))).thenReturn(null);
		
		RequestSegmentoEstrategia request = new RequestSegmentoEstrategia();
		request.setActivo(true);
		request.isActivo();
		request.setActivoEstrategia(true);
		request.isActivoEstrategia();
		request.setIdDivisa("");
		request.getIdDivisa();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSegmento("");
		request.getIdSegmento();
		request.setMonto(0.0);
		request.getMonto();
		request.setTipoOperacion("");
		request.getTipoOperacion();
		
		ResponseApi response = regionesSegEstrategiasService.asignarEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(regionesDao.findByRegionSegmento(isA(String.class), isA(String.class))).thenReturn(new Region());
		when(segmentoEstrategiasDao.findByTipo(isA(RequestSegmentoEstrategia.class))).thenReturn(null);
		
		response = regionesSegEstrategiasService.asignarEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		when(segmentoEstrategiasDao.findByTipo(isA(RequestSegmentoEstrategia.class))).thenReturn(Arrays.asList());
		
		response = regionesSegEstrategiasService.asignarEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		request.setIdDivisa("");
		when(segmentoEstrategiasDao.findByTipo(isA(RequestSegmentoEstrategia.class))).thenReturn(Arrays.asList(new ResponseSegmentoEstrategia()));
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(null);

		response = regionesSegEstrategiasService.asignarEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		request.setTipoOperacion("");
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(new Document());
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(null);


		response = regionesSegEstrategiasService.asignarEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(new Document());

		response = regionesSegEstrategiasService.asignarEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarEstatusEstrategiaSegmento() {
		
		when(regionesDao.findByRegionSegmento(isA(String.class), isA(String.class))).thenReturn(null);
		
		RequestSegmentoEstrategia request = new RequestSegmentoEstrategia();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSegmento("");
		request.getIdSegmento();
		
		ResponseApi response = regionesSegEstrategiasService.actualizarEstatusEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(regionesDao.findByRegionSegmento(isA(String.class), isA(String.class))).thenReturn(new Region());
		when(segmentoEstrategiasDao.findByTipo(isA(RequestSegmentoEstrategia.class))).thenReturn(null);
		
		response = regionesSegEstrategiasService.actualizarEstatusEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		when(segmentoEstrategiasDao.findByTipo(isA(RequestSegmentoEstrategia.class))).thenReturn(Arrays.asList());
		
		response = regionesSegEstrategiasService.actualizarEstatusEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		request.setIdDivisa("");
		when(segmentoEstrategiasDao.findByTipo(isA(RequestSegmentoEstrategia.class))).thenReturn(Arrays.asList(new ResponseSegmentoEstrategia()));
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(null);

		response = regionesSegEstrategiasService.actualizarEstatusEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		request.setTipoOperacion("");
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(new Document());
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(null);


		response = regionesSegEstrategiasService.actualizarEstatusEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(new Document());

		response = regionesSegEstrategiasService.actualizarEstatusEstrategiaSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
   
}
