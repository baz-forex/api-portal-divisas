package com.baz.divisas.api.tef.regionsegmentosucursal.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.general.model.SegmentoSucursales;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegionSegmentoSucursalImplTest {
    
    @InjectMocks
    private RegionSegmentoSucursalImpl segmentoSucursalService;
    
    @Mock
    SegmentosDao segmentoDAO;
    @Mock
    SucursalesDao sucursalesDao;
    @Mock
    BitacoraServiceImpl bitacoraServiceImpl;
    
	@Test
	void testConsultaSucursalesRegiones() {
		
		when(segmentoDAO.findSucursalesSegmento()).thenReturn(null);

		ResponseApi response = segmentoSucursalService.consultaSucursalesSegmento();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(segmentoDAO.findSucursalesSegmento()).thenReturn(Arrays.asList());

		response = segmentoSucursalService.consultaSucursalesSegmento();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
				
		when(segmentoDAO.findSucursalesSegmento()).thenReturn(Arrays.asList(new Segmento()));

		response = segmentoSucursalService.consultaSucursalesSegmento();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
    
	@Test
	void testAsignarSucursalesRegion() {

		List<String> reqSucursales = new ArrayList<>();
		
		when(segmentoDAO.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = segmentoSucursalService.asignarSucursalesSegmento("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();
		segmento.setIdRegion("");
		segmento.getIdRegion();
		
		when(segmentoDAO.findById(isA(String.class))).thenReturn(segmento);

		response = segmentoSucursalService.asignarSucursalesSegmento("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		segmento.setActivo(true);
		when(segmentoDAO.findById(isA(String.class))).thenReturn(segmento);

		response = segmentoSucursalService.asignarSucursalesSegmento("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		List<String> sucursalesRegion = new ArrayList<>();
		sucursalesRegion.add("2244");
		segmento.setSucursales(sucursalesRegion);
		segmento.setAsignado(true);
		
		reqSucursales.add("2244");
		reqSucursales.add("0000");
		
		List<Sucursal> sucursales = new ArrayList<>();
		when(sucursalesDao.findList(any())).thenReturn(sucursales);
		
		response = segmentoSucursalService.asignarSucursalesSegmento("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());		
		
		//--------------------------------------------------------------------
		Sucursal sucursal = new Sucursal();
		sucursal.setId("");
		
		sucursales.add(sucursal);
		
		response = segmentoSucursalService.asignarSucursalesSegmento("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());		

	}
	
	@Test
	void testEliminarSucursalRegion() {
		
		when(segmentoDAO.findById(isA(String.class))).thenReturn(null);

		ResponseApi response = segmentoSucursalService.eliminarSucursalesSegmento("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();
		
		when(segmentoDAO.findById(isA(String.class))).thenReturn(segmento);

		response = segmentoSucursalService.eliminarSucursalesSegmento("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		List<String> sucursalesRegion = new ArrayList<>();
		segmento.setSucursales(sucursalesRegion);
		segmento.setActivo(true);
		
		when(segmentoDAO.findById(isA(String.class))).thenReturn(segmento);
		
		response = segmentoSucursalService.eliminarSucursalesSegmento("", "2244");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		sucursalesRegion.add("2244");
		sucursalesRegion.add("0000");
		
		when(segmentoDAO.findById(isA(String.class))).thenReturn(segmento);
		
		response = segmentoSucursalService.eliminarSucursalesSegmento("", "2244");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testConsultaTCSegmentoSucursales() {
		
		when(segmentoDAO.listTCSegmentoSucursales(isA(String.class), isA(String.class))).thenReturn(null);	
		
		ResponseApi response = segmentoSucursalService.consultaTCSegmentoSucursales("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		SegmentoSucursales segmentoSucursal = new SegmentoSucursales();
		segmentoSucursal.setIdSegmento("");
		segmentoSucursal.setDescripcion("");
		segmentoSucursal.setSucursales(new ArrayList<>());
		
		when(segmentoDAO.listTCSegmentoSucursales(isA(String.class), isA(String.class))).thenReturn(segmentoSucursal);
		
		response = segmentoSucursalService.consultaTCSegmentoSucursales("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		segmentoSucursal = new SegmentoSucursales();
		segmentoSucursal.setIdSegmento("");
		segmentoSucursal.setDescripcion("");
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
		
		segmentoSucursal.setSucursales(sucursales);
		
		when(segmentoDAO.listTCSegmentoSucursales(isA(String.class), isA(String.class))).thenReturn(segmentoSucursal);
		
		response = segmentoSucursalService.consultaTCSegmentoSucursales("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	//OLP
	@Test
	void testConsultaSucursalesRegionesOlp() {
		
		when(segmentoDAO.findSucursalesSegmentoOlp()).thenReturn(null);

		ResponseApi response = segmentoSucursalService.consultaSucursalesSegmentoOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		when(segmentoDAO.findSucursalesSegmentoOlp()).thenReturn(Arrays.asList());

		response = segmentoSucursalService.consultaSucursalesSegmentoOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
				
		when(segmentoDAO.findSucursalesSegmentoOlp()).thenReturn(Arrays.asList(new Segmento()));

		response = segmentoSucursalService.consultaSucursalesSegmentoOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
    
	@Test
	void testAsignarSucursalesRegionOlp() {

		List<String> reqSucursales = new ArrayList<>();
		
		when(segmentoDAO.findByIdOlp(isA(String.class))).thenReturn(null);

		ResponseApi response = segmentoSucursalService.asignarSucursalesSegmentoOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();
		segmento.setIdRegion("");
		segmento.getIdRegion();
		
		when(segmentoDAO.findByIdOlp(isA(String.class))).thenReturn(segmento);

		response = segmentoSucursalService.asignarSucursalesSegmentoOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		segmento.setActivo(true);
		when(segmentoDAO.findByIdOlp(isA(String.class))).thenReturn(segmento);

		response = segmentoSucursalService.asignarSucursalesSegmentoOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		List<String> sucursalesRegion = new ArrayList<>();
		sucursalesRegion.add("2244");
		segmento.setSucursales(sucursalesRegion);
		segmento.setAsignado(true);
		
		reqSucursales.add("2244");
		reqSucursales.add("0000");
		
		List<Sucursal> sucursales = new ArrayList<>();
		when(sucursalesDao.findListOlp(any())).thenReturn(sucursales);
		
		response = segmentoSucursalService.asignarSucursalesSegmentoOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());		
		
		//--------------------------------------------------------------------
		Sucursal sucursal = new Sucursal();
		sucursal.setId("");
		
		sucursales.add(sucursal);
		
		response = segmentoSucursalService.asignarSucursalesSegmentoOlp("", reqSucursales);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());		

	}
	
	@Test
	void testEliminarSucursalRegionOlp() {
		
		when(segmentoDAO.findByIdOlp(isA(String.class))).thenReturn(null);

		ResponseApi response = segmentoSucursalService.eliminarSucursalesSegmentoOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();
		
		when(segmentoDAO.findByIdOlp(isA(String.class))).thenReturn(segmento);

		response = segmentoSucursalService.eliminarSucursalesSegmentoOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		List<String> sucursalesRegion = new ArrayList<>();
		segmento.setSucursales(sucursalesRegion);
		segmento.setActivo(true);
		
		when(segmentoDAO.findByIdOlp(isA(String.class))).thenReturn(segmento);
		
		response = segmentoSucursalService.eliminarSucursalesSegmentoOlp("", "2244");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		sucursalesRegion.add("2244");
		sucursalesRegion.add("0000");
		
		when(segmentoDAO.findByIdOlp(isA(String.class))).thenReturn(segmento);
		
		response = segmentoSucursalService.eliminarSucursalesSegmentoOlp("", "2244");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testConsultaTCSegmentoSucursalesOlp() {
		
		when(segmentoDAO.listTCSegmentoSucursalesOlp(isA(String.class))).thenReturn(null);	
		
		ResponseApi response = segmentoSucursalService.consultaTCSegmentoSucursalesOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		SegmentoSucursales segmentoSucursal = new SegmentoSucursales();
		segmentoSucursal.setIdSegmento("");
		segmentoSucursal.setDescripcion("");
		segmentoSucursal.setSucursales(new ArrayList<>());
		
		when(segmentoDAO.listTCSegmentoSucursalesOlp(isA(String.class))).thenReturn(segmentoSucursal);
		
		response = segmentoSucursalService.consultaTCSegmentoSucursalesOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		segmentoSucursal = new SegmentoSucursales();
		segmentoSucursal.setIdSegmento("");
		segmentoSucursal.setDescripcion("");
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
		
		sucursales .add(sucu);
		
		segmentoSucursal.setSucursales(sucursales);
		
		when(segmentoDAO.listTCSegmentoSucursalesOlp(isA(String.class))).thenReturn(segmentoSucursal);
		
		response = segmentoSucursalService.consultaTCSegmentoSucursalesOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
}
