package com.baz.divisas.api.tef.regionesestrategias.services.impl;

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
import com.baz.divisas.api.tef.general.model.Estrategia;
import com.baz.divisas.api.tef.regionesestrategias.dao.RegionesEstrategiasDao;
import com.baz.divisas.api.tef.regionesestrategias.dto.RequestEstrategia;
import com.baz.divisas.api.tef.regionesestrategias.dto.ResponseEstrategia;
import com.baz.divisas.api.tef.regionesestrategias.service.impl.RegionesEstrategiasService;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegionEstrategiaServiceTest {
    
    @InjectMocks
    private RegionesEstrategiasService regionesEstrategiasService;
    
    @Mock
    RegionesEstrategiasDao regionesEstrategiasDao;
    @Mock
    CatalogosDao catalogosDao;
    @Mock
    BitacoraServiceImpl bitacoraServiceImpl;
    
	@Test
	void testConsultaEstrategiasRegiones() {
		
		when(regionesEstrategiasDao.findAll()).thenReturn(null);

		ResponseApi response = regionesEstrategiasService.consultaEstrategiasRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(regionesEstrategiasDao.findAll()).thenReturn(Arrays.asList());

		response = regionesEstrategiasService.consultaEstrategiasRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		Estrategia estrategia = new Estrategia();
		estrategia.setActivo(true);
		estrategia.isActivo();
		estrategia.setIdBitacoraActualizacion("");
		estrategia.getIdBitacoraActualizacion();
		estrategia.setIdBitacoraAlta("");
		estrategia.getIdBitacoraAlta();
		estrategia.setMonto(0.0);
		estrategia.getMonto();
		
		ResponseEstrategia respEstrategia = new ResponseEstrategia();
		respEstrategia.setDescripcion("");
		respEstrategia.getDescripcion();
		respEstrategia.setDescripcionOperacion("");
		respEstrategia.getDescripcionOperacion();
		respEstrategia.setDivisa("");
		respEstrategia.getDivisa();
		respEstrategia.setId("");
		respEstrategia.getId();
		respEstrategia.setTipoOperacion("");
		respEstrategia.getTipoOperacion();
		respEstrategia.setEstrategia(estrategia);
		respEstrategia.getEstrategia();		
		
		when(regionesEstrategiasDao.findAll()).thenReturn(Arrays.asList(respEstrategia));

		response = regionesEstrategiasService.consultaEstrategiasRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
 
	@Test
	void testAsignarEstrategiaRegion() {
		
		RequestEstrategia request = new RequestEstrategia();
		
		when(regionesEstrategiasDao.findByTipo(isA(RequestEstrategia.class))).thenReturn(null);

		ResponseApi response = regionesEstrategiasService.asignarEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(regionesEstrategiasDao.findByTipo(isA(RequestEstrategia.class))).thenReturn(Arrays.asList());

		response = regionesEstrategiasService.asignarEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		request.setIdDivisa("");
		when(regionesEstrategiasDao.findByTipo(isA(RequestEstrategia.class))).thenReturn(Arrays.asList(new ResponseEstrategia()));
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(null);

		response = regionesEstrategiasService.asignarEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		request.setTipoOperacion("");
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(new Document());
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(null);


		response = regionesEstrategiasService.asignarEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(new Document());

		response = regionesEstrategiasService.asignarEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarEstatusEstrategiaRegion() {
		
		RequestEstrategia request = new RequestEstrategia();
		
		when(regionesEstrategiasDao.findByTipo(isA(RequestEstrategia.class))).thenReturn(null);

		ResponseApi response = regionesEstrategiasService.actualizarEstatusEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(regionesEstrategiasDao.findByTipo(isA(RequestEstrategia.class))).thenReturn(Arrays.asList());

		response = regionesEstrategiasService.actualizarEstatusEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		request.setIdDivisa("");
		
		when(regionesEstrategiasDao.findByTipo(isA(RequestEstrategia.class))).thenReturn(Arrays.asList(new ResponseEstrategia()));
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(null);

		response = regionesEstrategiasService.actualizarEstatusEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		request.setTipoOperacion("");
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(new Document());
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(null);


		response = regionesEstrategiasService.actualizarEstatusEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(new Document());

		response = regionesEstrategiasService.actualizarEstatusEstrategiaRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
   
}
