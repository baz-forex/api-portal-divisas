package com.baz.divisas.api.tef.dashboard.services.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.dashboard.dao.DashboardRegionesDAO;
import com.baz.divisas.api.tef.dashboard.dto.RequestRegionTC;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.spots.dao.SpotsDao;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DashboardServiceImplTest {

	@InjectMocks
	DashboardServiceImpl dashboardService;

	@Mock
	DashboardRegionesDAO dashboardDao;
	@Mock
	RegionesDao regionesDao;
	@Mock
	SegmentosDao segmentosDao;
	@Mock
	CatalogosDao catalogosDao;
	@Mock
	SpotsDao spotsDao;

	@Test
	void testConsultaRegiones() {
		
		when(dashboardDao.findRegiones(isA(String.class))).thenReturn(Arrays.asList());

		ResponseApi response = dashboardService.consultaRegiones("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		Document region = new Document("_id", ObjectId.get() );
		region.put("segmentos", Arrays.asList(ObjectId.get()));

		when(dashboardDao.findRegiones(isA(String.class))).thenReturn(Arrays.asList(region));

		response = dashboardService.consultaRegiones("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testConsultaSegmentosRegion() {
		
		when(regionesDao.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = dashboardService.consultaSegmentosRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(regionesDao.findById(isA(String.class))).thenReturn(new Region());
		when(dashboardDao.findSegmentos(any(), isA(String.class))).thenReturn(Arrays.asList());

		response = dashboardService.consultaSegmentosRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(dashboardDao.findSegmentos(any(), isA(String.class))).thenReturn(Arrays.asList(new Document("_id", ObjectId.get())));

		response = dashboardService.consultaSegmentosRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testActualizarTCRegion() {
		
		RequestRegionTC request = new RequestRegionTC();
		request.setId("");
		request.setDivisa("");
		request.setTipoOperacionTC("");
		
		when(regionesDao.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = dashboardService.actualizarTCRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		when(regionesDao.findById(isA(String.class))).thenReturn(region);

		response = dashboardService.actualizarTCRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		region.setActivo(true);
		
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(null);
		
		response = dashboardService.actualizarTCRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
				
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(new Document());
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(null);
		
		response = dashboardService.actualizarTCRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------

		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(new Document());
		
		response = dashboardService.actualizarTCRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------

		region.setSucursales(Arrays.asList(""));
		
		response = dashboardService.actualizarTCRegion(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());		

	}
	
	@Test
	void testActualizarTCSegmento() {
		
		RequestRegionTC request = new RequestRegionTC();
		request.setId("");
		request.setDivisa("");
		request.setTipoOperacionTC("");
		
		when(segmentosDao.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = dashboardService.actualizarTCSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();
		when(segmentosDao.findById(isA(String.class))).thenReturn(segmento);

		response = dashboardService.actualizarTCSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		segmento.setActivo(true);
		
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(null);
		
		response = dashboardService.actualizarTCSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
				
		when(catalogosDao.findDivisaById(isA(String.class))).thenReturn(new Document());
		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(null);
		
		response = dashboardService.actualizarTCSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------

		when(catalogosDao.findTipoOperacionById(isA(String.class))).thenReturn(new Document());
		
		response = dashboardService.actualizarTCSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------

		segmento.setSucursales(Arrays.asList(""));
		
		response = dashboardService.actualizarTCSegmento(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());		

	}
}
