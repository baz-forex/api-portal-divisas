package com.baz.divisas.api.tef.regionsegmento.services.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regionessegmento.dto.ResRegionSegmento;
import com.baz.divisas.api.tef.regionessegmento.services.impl.RegionSegmentoImpl;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegionSegmentoImplTest {

	@InjectMocks
	RegionSegmentoImpl regionSegmentoService;

	@Mock
	private RegionesDao regionDAO;
	@Mock
	private SegmentosDao segmentoDAO;
	@Mock
	BitacoraServiceImpl bitacora;

	@Test
	void testConsultaSegmentosRegiones() {

		
		when(regionDAO.findAllAndExcludeFields(any())).thenReturn(null);

		ResponseApi response = regionSegmentoService.consultaSegmentosRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(regionDAO.findAllAndExcludeFields(any())).thenReturn(Arrays.asList());

		response = regionSegmentoService.consultaSegmentosRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		ResRegionSegmento resp = new ResRegionSegmento();
		resp = new ResRegionSegmento("", "", true, null);
		resp.setActivo(true);
		resp.isActivo();
		resp.setDescripcion("");
		resp.getDescripcion();
		resp.setId("");
		resp.getId();
		resp.setSegmentos(null);
		resp.getSegmentos();
		
		when(regionDAO.findAllAndExcludeFields(any())).thenReturn(Arrays.asList(new Region()));

		response = regionSegmentoService.consultaSegmentosRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testAsignarSegmentosRegion() {

		List<String> reqSegmentos = new ArrayList<>();
		
		when(regionDAO.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = regionSegmentoService.asignarSegmentosRegion("", reqSegmentos);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		
		when(regionDAO.findById(isA(String.class))).thenReturn(region);

		response = regionSegmentoService.asignarSegmentosRegion("", reqSegmentos);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		ObjectId isSegmento = ObjectId.get();
		List<ObjectId> segmentosRegion = new ArrayList<>();
		segmentosRegion.add(isSegmento);
		region.setSegmentos(segmentosRegion);
		region.setActivo(true);

		reqSegmentos.add(isSegmento.toHexString());
		reqSegmentos.add(ObjectId.get().toHexString());

		List<Segmento> segmentos = new ArrayList<>();
		when(regionDAO.findById(isA(String.class))).thenReturn(region);
		when(segmentoDAO.findList(any())).thenReturn(segmentos);

		response = regionSegmentoService.asignarSegmentosRegion("", reqSegmentos);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();
		segmento.setId(ObjectId.get().toHexString());
		segmentos.add(segmento);

		response = regionSegmentoService.asignarSegmentosRegion("", reqSegmentos);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void testEliminaSegmentoRegion() {
		
		when(regionDAO.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = regionSegmentoService.eliminaSegmentoRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		
		when(regionDAO.findById(isA(String.class))).thenReturn(region);

		response = regionSegmentoService.eliminaSegmentoRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		ObjectId isSegmento = ObjectId.get();
		List<ObjectId> segmentosRegion = new ArrayList<>();
		region.setSegmentos(segmentosRegion);
		region.setActivo(true);

		when(regionDAO.findById(isA(String.class))).thenReturn(region);

		response = regionSegmentoService.eliminaSegmentoRegion("", isSegmento.toHexString());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		segmentosRegion.add(isSegmento);
		segmentosRegion.add(ObjectId.get());

		Segmento segmento = new Segmento();
		when(regionDAO.findById(isA(String.class))).thenReturn(region);
		when(segmentoDAO.findById(isA(String.class))).thenReturn(segmento);

		response = regionSegmentoService.eliminaSegmentoRegion("", isSegmento.toHexString());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		region.setSucursales(new ArrayList<String>());
		segmento.setSucursales(Arrays.asList(""));

		response = regionSegmentoService.eliminaSegmentoRegion("", isSegmento.toHexString());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	//OLP
	@Test
	void testConsultaSegmentosRegionesOlp() {

		
		when(regionDAO.findAllAndExcludeFieldsOlp(any())).thenReturn(null);

		ResponseApi response = regionSegmentoService.consultaSegmentosRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(regionDAO.findAllAndExcludeFieldsOlp(any())).thenReturn(Arrays.asList());

		response = regionSegmentoService.consultaSegmentosRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		ResRegionSegmento resp = new ResRegionSegmento();
		resp = new ResRegionSegmento("", "", true, null);
		resp.setActivo(true);
		resp.isActivo();
		resp.setDescripcion("");
		resp.getDescripcion();
		resp.setId("");
		resp.getId();
		resp.setSegmentos(null);
		resp.getSegmentos();
		
		when(regionDAO.findAllAndExcludeFieldsOlp(any())).thenReturn(Arrays.asList(new Region()));

		response = regionSegmentoService.consultaSegmentosRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testAsignarSegmentosRegionOlp() {

		List<String> reqSegmentos = new ArrayList<>();
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(null);

		ResponseApi response = regionSegmentoService.asignarSegmentosRegionOlp("", reqSegmentos);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);

		response = regionSegmentoService.asignarSegmentosRegionOlp("", reqSegmentos);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		ObjectId isSegmento = ObjectId.get();
		List<ObjectId> segmentosRegion = new ArrayList<>();
		segmentosRegion.add(isSegmento);
		region.setSegmentos(segmentosRegion);
		region.setActivo(true);

		reqSegmentos.add(isSegmento.toHexString());
		reqSegmentos.add(ObjectId.get().toHexString());

		List<Segmento> segmentos = new ArrayList<>();
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);
		when(segmentoDAO.findListOlp(any())).thenReturn(segmentos);

		response = regionSegmentoService.asignarSegmentosRegionOlp("", reqSegmentos);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();
		segmento.setId(ObjectId.get().toHexString());
		segmentos.add(segmento);

		response = regionSegmentoService.asignarSegmentosRegionOlp("", reqSegmentos);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void testEliminaSegmentoRegionOlp() {
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(null);

		ResponseApi response = regionSegmentoService.eliminaSegmentoRegionOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);

		response = regionSegmentoService.eliminaSegmentoRegionOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		ObjectId isSegmento = ObjectId.get();
		List<ObjectId> segmentosRegion = new ArrayList<>();
		region.setSegmentos(segmentosRegion);
		region.setActivo(true);

		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);

		response = regionSegmentoService.eliminaSegmentoRegionOlp("", isSegmento.toHexString());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		segmentosRegion.add(isSegmento);
		segmentosRegion.add(ObjectId.get());

		Segmento segmento = new Segmento();
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);
		when(segmentoDAO.findByIdOlp(isA(String.class))).thenReturn(segmento);

		response = regionSegmentoService.eliminaSegmentoRegionOlp("", isSegmento.toHexString());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		region.setSucursales(new ArrayList<String>());
		segmento.setSucursales(Arrays.asList(""));

		response = regionSegmentoService.eliminaSegmentoRegionOlp("", isSegmento.toHexString());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
}
