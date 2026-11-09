package com.baz.divisas.api.tef.regionessucursales.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
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
import com.baz.divisas.api.tef.general.model.RegionSegmentosSucursales;
import com.baz.divisas.api.tef.general.model.RegionSucursales;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegionSucursalImplTest {
    
    @InjectMocks
    private RegionSucursalImpl regionSucursalService;
    
    @Mock
    RegionesDao regionDAO;
    @Mock
    SucursalesDao sucursalesDao;
    @Mock
    SegmentosDao segmentosDao;
    @Mock
    BitacoraServiceImpl bitacoraServiceImpl;
    
	@Test
	void testConsultaSucursalesRegiones() {
		
		when(regionDAO.findAllAndExcludeFields(any())).thenReturn(null);

		ResponseApi response = regionSucursalService.consultaSucursalesRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(regionDAO.findAllAndExcludeFields(any())).thenReturn(Arrays.asList());

		response = regionSucursalService.consultaSucursalesRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
				
		when(regionDAO.findAllAndExcludeFields(any())).thenReturn(Arrays.asList(new Region()));

		response = regionSucursalService.consultaSucursalesRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
    
	@Test
	void testAsignarSucursalesRegion() {

		List<String> reqSucursales = new ArrayList<>();
		
		when(regionDAO.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = regionSucursalService.asignarSucursalesRegion("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		
		when(regionDAO.findById(isA(String.class))).thenReturn(region);

		response = regionSucursalService.asignarSucursalesRegion("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		List<String> sucursalesRegion = new ArrayList<>();
		sucursalesRegion.add("2244");
		region.setSucursales(sucursalesRegion);
		region.setActivo(true);
		
		reqSucursales.add("2244");
		reqSucursales.add("0000");
		
		List<Sucursal> sucursales = new ArrayList<>();
		when(sucursalesDao.findList(any())).thenReturn(sucursales);
		
		response = regionSucursalService.asignarSucursalesRegion("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());		
		
		//--------------------------------------------------------------------
		Sucursal sucursal = new Sucursal();
		sucursal.setId("");
		
		sucursales.add(sucursal);
		
		response = regionSucursalService.asignarSucursalesRegion("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());		

	}
	
	@Test
	void testEliminarSucursalRegion() {
		
		when(regionDAO.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = regionSucursalService.eliminarSucursalRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		
		when(regionDAO.findById(isA(String.class))).thenReturn(region);

		response = regionSucursalService.eliminarSucursalRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		List<String> sucursalesRegion = new ArrayList<>();
		region.setSucursales(sucursalesRegion);
		region.setActivo(true);
		
		when(regionDAO.findById(isA(String.class))).thenReturn(region);
		
		response = regionSucursalService.eliminarSucursalRegion("", "2244");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		sucursalesRegion.add("2244");
		sucursalesRegion.add("0000");
		
		when(regionDAO.findById(isA(String.class))).thenReturn(region);
		
		response = regionSucursalService.eliminarSucursalRegion("", "2244");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	
	@Test
	void testConsultaRegionesSegmentosSucursales() {
		
		when(regionDAO.listRegionesSegmentosSucursal()).thenReturn(null);
		when(regionDAO.listRegionesSegmentos()).thenReturn(null);
		when(segmentosDao.listSegmentosSucursal()).thenReturn(null);
		ResponseApi response = regionSucursalService.consultaRegionesSegmentosSucursales();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		List<RegionSegmentosSucursales> listaRegion= new ArrayList<>();
		List<RegionSegmentosSucursales> listaSegmento= new ArrayList<>();
		List<Segmento> listaSeg= new ArrayList<>();
		
		
		when(regionDAO.listRegionesSegmentosSucursal()).thenReturn(listaRegion);
		when(regionDAO.listRegionesSegmentos()).thenReturn(listaSegmento);
		when(segmentosDao.listSegmentosSucursal()).thenReturn(listaSeg);
		
		response = regionSucursalService.consultaRegionesSegmentosSucursales();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		RegionSegmentosSucursales regionSegmento1 = new RegionSegmentosSucursales();	
		regionSegmento1.setIdRegion("1");
		regionSegmento1.setDescripcion("2");
		
		listaRegion.add(regionSegmento1);
		RegionSegmentosSucursales regionSegmento2 = new RegionSegmentosSucursales();	
		regionSegmento2.setIdRegion("1");
		regionSegmento2.setDescripcion("2");
		regionSegmento2.setSucursales(Arrays.asList("123"));
		regionSegmento2.setSegmentosRegion(Arrays.asList(new ObjectId("63fd51c6b39dd0317ac9d2c7")));
		listaSegmento.add(regionSegmento2);
		Segmento segmento = new Segmento();
		segmento.setId("63fd51c6b39dd0317ac9d2c7");
		listaSeg.add(segmento);
		when(regionDAO.listRegionesSegmentosSucursal()).thenReturn(listaRegion);
		when(regionDAO.listRegionesSegmentos()).thenReturn(listaSegmento);
		when(segmentosDao.listSegmentosSucursal()).thenReturn(listaSeg);
		
		response = regionSucursalService.consultaRegionesSegmentosSucursales();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	
	}
	
	@Test
	void testConsultaTCRegionesSucursales() {
		
		when(regionDAO.listTCRegionesSucursales(isA(String.class), isA(String.class))).thenReturn(null);	
		
		ResponseApi response = regionSucursalService.consultaTCRegionesSucursales("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		RegionSucursales regionSucursal = new RegionSucursales();
		regionSucursal.setIdRegion("");
		regionSucursal.setDescripcion("");
		regionSucursal.setSucursales(new ArrayList<>());
		
		when(regionDAO.listTCRegionesSucursales(isA(String.class), isA(String.class))).thenReturn(regionSucursal);
		
		response = regionSucursalService.consultaTCRegionesSucursales("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		regionSucursal = new RegionSucursales();
		regionSucursal.setIdRegion("");
		regionSucursal.setDescripcion("");
		com.baz.divisas.api.tef.general.model.Sucursal sucu = new com.baz.divisas.api.tef.general.model.Sucursal();
		sucu.setId("23423423");
		Divisa divisaUsd = new Divisa();
		divisaUsd.setId("USD");
		
		com.baz.divisas.api.tef.regiones.model.TipoOperacion tipo = new com.baz.divisas.api.tef.regiones.model.TipoOperacion();
		tipo.setIdTipoOperacion("410");
		tipo.setTipoCambioSpot(19.20);
		tipo.setTipoCambioSpread(-0.50);
		tipo.getTipoCambioSpread();
		
		com.baz.divisas.api.tef.regiones.model.TipoOperacion tipoVenta = new com.baz.divisas.api.tef.regiones.model.TipoOperacion();
		tipoVenta.setIdTipoOperacion("411");
		tipoVenta.setTipoCambioSpot(19.60);
		tipoVenta.setTipoCambioSpread(-0.50);
		
		List<com.baz.divisas.api.tef.regiones.model.TipoOperacion> tipos = new ArrayList<>();
		
		tipos.add(tipo);
		tipos.add(tipoVenta);
		
		divisaUsd.setTiposOperacion(tipos);
		
		sucu.setDivisas(divisaUsd);
		
		List<com.baz.divisas.api.tef.general.model.Sucursal> sucursales = new ArrayList<>();
		
		sucursales .add(sucu);
		
		regionSucursal.setSucursales(sucursales);
		
		when(regionDAO.listTCRegionesSucursales(isA(String.class), isA(String.class))).thenReturn(regionSucursal);
		
		response = regionSucursalService.consultaTCRegionesSucursales("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	//OLP
	
	@Test
	void testConsultaSucursalesRegionesOlp() {
		
		when(regionDAO.findAllAndExcludeFieldsOlp(any())).thenReturn(null);

		ResponseApi response = regionSucursalService.consultaSucursalesRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(regionDAO.findAllAndExcludeFieldsOlp(any())).thenReturn(Arrays.asList());

		response = regionSucursalService.consultaSucursalesRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
				
		when(regionDAO.findAllAndExcludeFieldsOlp(any())).thenReturn(Arrays.asList(new Region()));

		response = regionSucursalService.consultaSucursalesRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
    
	@Test
	void testAsignarSucursalesRegionOlp() {

		List<String> reqSucursales = new ArrayList<>();
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(null);

		ResponseApi response = regionSucursalService.asignarSucursalesRegionOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);

		response = regionSucursalService.asignarSucursalesRegionOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		List<String> sucursalesRegion = new ArrayList<>();
		sucursalesRegion.add("2244");
		region.setSucursales(sucursalesRegion);
		region.setActivo(true);
		
		reqSucursales.add("2244");
		reqSucursales.add("0000");
		
		List<Sucursal> sucursales = new ArrayList<>();
		when(sucursalesDao.findListOlp(any())).thenReturn(sucursales);
		
		response = regionSucursalService.asignarSucursalesRegionOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());		
		
		//--------------------------------------------------------------------
		Sucursal sucursal = new Sucursal();
		sucursal.setId("");
		
		sucursales.add(sucursal);
		
		response = regionSucursalService.asignarSucursalesRegionOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());		

	}
	
	@Test
	void testEliminarSucursalRegionOlp() {
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(null);

		ResponseApi response = regionSucursalService.eliminarSucursalRegionOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);

		response = regionSucursalService.eliminarSucursalRegionOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		List<String> sucursalesRegion = new ArrayList<>();
		region.setSucursales(sucursalesRegion);
		region.setActivo(true);
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);
		
		response = regionSucursalService.eliminarSucursalRegionOlp("", "2244");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		sucursalesRegion.add("2244");
		sucursalesRegion.add("0000");
		
		when(regionDAO.findByIdOlp(isA(String.class))).thenReturn(region);
		
		response = regionSucursalService.eliminarSucursalRegionOlp("", "2244");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	
	@Test
	void testConsultaRegionesSegmentosSucursalesOlp() {
		
		when(regionDAO.listRegionesSegmentosSucursalOlp()).thenReturn(null);
		when(regionDAO.listRegionesSegmentosOlp()).thenReturn(null);
		when(segmentosDao.listSegmentosSucursal()).thenReturn(null);
		ResponseApi response = regionSucursalService.consultaRegionesSegmentosSucursalesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		List<RegionSegmentosSucursales> listaRegion= new ArrayList<>();
		List<RegionSegmentosSucursales> listaSegmento= new ArrayList<>();
		List<Segmento> listaSeg= new ArrayList<>();
		
		
		when(regionDAO.listRegionesSegmentosSucursalOlp()).thenReturn(listaRegion);
		when(regionDAO.listRegionesSegmentosOlp()).thenReturn(listaSegmento);
		when(segmentosDao.listSegmentosSucursalOlp()).thenReturn(listaSeg);
		
		response = regionSucursalService.consultaRegionesSegmentosSucursalesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		RegionSegmentosSucursales regionSegmento1 = new RegionSegmentosSucursales();	
		regionSegmento1.setIdRegion("1");
		regionSegmento1.setDescripcion("2");
		
		listaRegion.add(regionSegmento1);
		RegionSegmentosSucursales regionSegmento2 = new RegionSegmentosSucursales();	
		regionSegmento2.setIdRegion("1");
		regionSegmento2.setDescripcion("2");
		regionSegmento2.setSucursales(Arrays.asList("123"));
		regionSegmento2.setSegmentosRegion(Arrays.asList(new ObjectId("63fd51c6b39dd0317ac9d2c7")));
		listaSegmento.add(regionSegmento2);
		Segmento segmento = new Segmento();
		segmento.setId("63fd51c6b39dd0317ac9d2c7");
		listaSeg.add(segmento);
		when(regionDAO.listRegionesSegmentosSucursalOlp()).thenReturn(listaRegion);
		when(regionDAO.listRegionesSegmentosOlp()).thenReturn(listaSegmento);
		when(segmentosDao.listSegmentosSucursalOlp()).thenReturn(listaSeg);
		
		response = regionSucursalService.consultaRegionesSegmentosSucursalesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	
	}
	
	@Test
	void testConsultaTCRegionesSucursalesOlp() {
		
		when(regionDAO.listTCRegionesSucursalesOlp(isA(String.class))).thenReturn(null);	
		
		ResponseApi response = regionSucursalService.consultaTCRegionesSucursalesOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		RegionSucursales regionSucursal = new RegionSucursales();
		regionSucursal.setIdRegion("");
		regionSucursal.setDescripcion("");
		regionSucursal.setSucursales(new ArrayList<>());
		
		when(regionDAO.listTCRegionesSucursalesOlp(isA(String.class))).thenReturn(regionSucursal);
		
		response = regionSucursalService.consultaTCRegionesSucursalesOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		regionSucursal = new RegionSucursales();
		regionSucursal.setIdRegion("");
		regionSucursal.setDescripcion("");
		com.baz.divisas.api.tef.general.model.Sucursal sucu = new com.baz.divisas.api.tef.general.model.Sucursal();
		sucu.setId("23423423");
		Divisa divisaUsd = new Divisa();
		divisaUsd.setId("OLP");
		
		com.baz.divisas.api.tef.regiones.model.TipoOperacion tipo = new com.baz.divisas.api.tef.regiones.model.TipoOperacion();
		tipo.setIdTipoOperacion("410");
		tipo.setTipoCambioSpot(19.20);
		tipo.setTipoCambioSpread(-0.50);
		tipo.getTipoCambioSpread();
		
		com.baz.divisas.api.tef.regiones.model.TipoOperacion tipoVenta = new com.baz.divisas.api.tef.regiones.model.TipoOperacion();
		tipoVenta.setIdTipoOperacion("411");
		tipoVenta.setTipoCambioSpot(19.60);
		tipoVenta.setTipoCambioSpread(-0.50);
		
		List<com.baz.divisas.api.tef.regiones.model.TipoOperacion> tipos = new ArrayList<>();
		
		tipos.add(tipo);
		tipos.add(tipoVenta);
		
		divisaUsd.setTiposOperacion(tipos);
		
		sucu.setTcOlp(divisaUsd);
		
		List<com.baz.divisas.api.tef.general.model.Sucursal> sucursales = new ArrayList<>();
		
		sucursales.add(sucu);
		
		regionSucursal.setSucursales(sucursales);
		
		when(regionDAO.listTCRegionesSucursalesOlp(isA(String.class))).thenReturn(regionSucursal);
		
		response = regionSucursalService.consultaTCRegionesSucursalesOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
}
