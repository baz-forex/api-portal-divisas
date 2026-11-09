package com.baz.divisas.api.tef.sucursales.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.general.model.CatalogoDivisas;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.general.model.TC;
import com.baz.divisas.api.tef.limites.dao.LimitesPorSucursalDao;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regiones.model.TipoOperacion;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.spots.dao.SpotsDao;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.dto.ReqActSucursal;
import com.baz.divisas.api.tef.sucursales.model.Divisas;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.api.tef.sucursales.utils.ArchivosExcel;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SucursalesServiceTest {

	@InjectMocks
	SucursalesService sucursalesService;

	@Mock
	SucursalesDao sucursalesDao;	
	@Mock
	SegmentosDao segmentosDao;
	@Mock
	RegionesDao regionesDao;
	@Mock
	CatalogosDao catalogosDao;
	@Mock
	SpotsDao spotDao;
	@Mock
	PromoEspacioServiceImpl promoEspacioImpl;
	
	@Mock
	private LimitesPorSucursalDao limitesPorSucursalDao;
	
	@Mock
	private BitacoraServiceImpl bitacora;

	private MultipartFile fileMock;
	ArchivosExcel lectorExcel;

	@BeforeEach
	public void setUp() {
		sucursalesService = new SucursalesService(catalogosDao, sucursalesDao, spotDao, promoEspacioImpl,limitesPorSucursalDao);
		lectorExcel = new ArchivosExcel(sucursalesDao, promoEspacioImpl,limitesPorSucursalDao,bitacora);
	}
	
	@Test
	public void testConsultaSegmentos() {

		List<Sucursal> sucursales = new ArrayList<>();
		when(sucursalesDao.findAsignadas(isA(boolean.class))).thenReturn(sucursales);

		ResponseApi response = sucursalesService.getSucursalesImpl(true);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		Sucursal sucursal = new Sucursal();
		sucursal.setId("1234");
		sucursal.getId();
		sucursal.setAsignada(true);
		sucursal.isAsignada();
		
		sucursal.setCodigoPostal("");
		sucursal.getCodigoPostal();
		sucursal.setDescripcionRegion("");
		sucursal.getDescripcionRegion();
		sucursal.setDivisas(Arrays.asList());
		sucursal.getDivisas();
		sucursal.setIdEntidad(1);
		sucursal.getIdEntidad();
		sucursal.setIdEstatus(1);
		sucursal.getIdEstatus();
		sucursal.setLatitud("");
		sucursal.getLatitud();
		sucursal.setLongitud("");
		sucursal.getLongitud();
		sucursal.setNomCalle("");
		sucursal.getNomCalle();
		sucursal.setNomCanal("");
		sucursal.getNomCanal();
		sucursal.setNomCC("");
		sucursal.getNomCC();
		sucursal.setNomColonia("");
		sucursal.getNomColonia();
		sucursal.setNomEntidad("");
		sucursal.getNomEntidad();
		sucursal.setNomEstado("");
		sucursal.getNomEstado();
		sucursal.setNomEstatus("");
		sucursal.getNomEstatus();
		sucursal.setNomLocalidad("");
		sucursal.getNomLocalidad();
		sucursal.setNomMunicipio("");
		sucursal.getNomMunicipio();
		sucursal.setNomPais("");
		sucursal.getNomPais();
		sucursal.setNomTipoOperacion("");
		sucursal.getNomTipoOperacion();
		sucursal.setSucursal(1);
		sucursal.getSucursal();
		sucursal.setTipoCanal("");
		sucursal.getTipoCanal();
		sucursal.setFechaModificacion("");
		sucursal.getFechaModificacion();
		sucursal.setFechaUltimoCambio("");
		sucursal.getFechaUltimoCambio();
		sucursal.setDescripcion("");
		sucursal.getDescripcion();
		sucursal.setRegionSucursalArchivo("");
		sucursal.getRegionSucursalArchivo();
		sucursal.setNombreSucursalArchivo("");
		sucursal.getNombreSucursalArchivo();
		sucursal.setTipoSucursalArchivo("");
		sucursal.getTipoSucursalArchivo();
		sucursal.setMunicipioSucursalArchivo("");
		sucursal.getMunicipioSucursalArchivo();
		sucursal.setEstadoSucursalArchivo("");
		sucursal.getEstadoSucursalArchivo();
		sucursal.setCalleSucursalArchivo("");
		sucursal.getCalleSucursalArchivo();
		sucursal.setNumeroSucursalArchivo("");
		sucursal.getNumeroSucursalArchivo();
		sucursal.setCpSucursalArchivo("");
		sucursal.getCpSucursalArchivo();
		sucursal.setNumeroRegionArchivo("");
		sucursal.getNumeroRegionArchivo();
		sucursales.add(sucursal);
		
		// *********************
		List<Document> sucursalesSegmentoRegion = new ArrayList<>();
		sucursalesSegmentoRegion.add(new Document("sucursalesSegmentos", Arrays.asList("0000", "1234")));
		sucursalesSegmentoRegion.add(new Document("sucursalesSegmentos", Arrays.asList("9999")));
		
		// *********************
		List<Region> regiones = new ArrayList<>();
		Region region = new Region();
		region.setId("");
		region.getId();
		region.setSucursales(Arrays.asList("0000", "1234"));
		regiones.add(region);
		
		Region region2 = new Region();
		region2.setSucursales(Arrays.asList("9999"));		
		regiones.add(region2);

		
		// *********************
		List<Segmento> segmentos = new ArrayList<>();
		
		// *********************
		List<CatalogoDivisas> divisas = new ArrayList<>();
		CatalogoDivisas divisa = new CatalogoDivisas();
		divisa.setId("USD");
		divisa.getId();
		divisa.setActivo(true);
		divisa.isActivo();
		divisa.setDescripcion("");
		divisa.getDescripcion();
		divisa.setLimites(Arrays.asList());
		divisa.getLimites();
		divisas.add(divisa);
		
		
		when(sucursalesDao.findAsignadas(isA(boolean.class))).thenReturn(sucursales);
		when(sucursalesDao.findSucursalesSegmentosRegion()).thenReturn(sucursalesSegmentoRegion);
		when(regionesDao.findAllActive()).thenReturn(regiones);
		when(segmentosDao.findAllActive()).thenReturn(segmentos);
		when(catalogosDao.findCatDivisasLimites()).thenReturn(divisas);

		response = sucursalesService.getSucursalesImpl(true);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

		//--------------------------------------------------------------------

		CatalogoDivisas divisa2 = new CatalogoDivisas();
		divisa2.setId("CAD");
		divisas.add(divisa2);
		
		List<Limites> limites = new ArrayList<>();
		Limites lim1 = new Limites();
		lim1.setCodigoDivisa("USD");
		lim1.setIdTipoOperacion("410");
		lim1.setDiario(new MontosLimite());
		lim1.getDiario();
		lim1.setMensual(new MontosLimite());
		lim1.setIdBitacoraActualizacion("");
		lim1.getIdBitacoraActualizacion();
		lim1.setIdBitacoraAlta("");
		lim1.getIdBitacoraAlta();
		
		Limites lim2 = new Limites();
		lim2.setCodigoDivisa("CAD");
		lim2.setIdTipoOperacion("100");
		lim2.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
		lim2.getDiario();
		lim2.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
				
		limites.add(lim1);
		limites.add(lim2);
		
		sucursal.setLimites(limites);
		
		Sucursal sucursal2 = new Sucursal();
		sucursal2.setId("2");
		
		Limites lim3 = new Limites();
		lim3.setActivo(true);
		lim3.setCodigoDivisa("CAD");
		lim3.setIdTipoOperacion("100");
		lim3.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
		lim3.getDiario();
		lim3.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
		
		List<Limites> limites2 = new ArrayList<>();
		limites2.add(lim3);
		sucursal2.setLimites(limites2);

		sucursales.add(sucursal2);
		
		response = sucursalesService.getSucursalesImpl(true);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		Limites limDivisa = new Limites();
		limDivisa.setCodigoDivisa("USD");
		limDivisa.setIdTipoOperacion("410");
		limDivisa.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
		limDivisa.getDiario();
		limDivisa.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
		
		divisa.setLimites(Arrays.asList(limDivisa));
		
		response = sucursalesService.getSucursalesImpl(true);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		limDivisa.setActivo(true);;
		
		response = sucursalesService.getSucursalesImpl(true);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		Divisa divRegion = new Divisa();
		divRegion.setId("USD");
		divRegion.setActivo(true);
		
		TipoOperacion tipoOperacion = new TipoOperacion();
		tipoOperacion.setActivo(true);
		tipoOperacion.setIdTipoOperacion("410");
		tipoOperacion.setLimites(new Limites(true, new MontosLimite(), new MontosLimite()));
		
		divRegion.setTiposOperacion(Arrays.asList(tipoOperacion));
		
		region.setActivo(true);
		region.setDivisas(Arrays.asList(divRegion));
		
		response = sucursalesService.getSucursalesImpl(true);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		Divisa divSegmento = new Divisa();
		divSegmento.setId("USD");
		divSegmento.getId();
		
		Segmento segmento = new Segmento();
		segmento.setSucursales(Arrays.asList("1234"));
		segmento.getSucursales();
		segmento.setDivisas(Arrays.asList(divSegmento));
		segmento.getDivisas();
		
		segmentos.add(segmento);
		
		response = sucursalesService.getSucursalesImpl(true);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		tipoOperacion.setLimites(new Limites(true, new MontosLimite(1.0, 1.0 , 1.0 , 1.0), new MontosLimite(1.0, 1.0, 1.0 , 1.0)));
		 
		divSegmento.setActivo(true);
		divSegmento.isActivo();
		divSegmento.setTiposOperacion(Arrays.asList(tipoOperacion));
		divSegmento.getTiposOperacion();
		
		response = sucursalesService.getSucursalesImpl(true);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		

	}
	
	@Test
	public void testConsultaTCSucursales() {

		List<Sucursal> sucursales = new ArrayList<>();
		when(sucursalesDao.findTCSucursales()).thenReturn(null);

		ResponseApi response = sucursalesService.consultaTCSucursales();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		sucursales = new ArrayList<>();
		when(sucursalesDao.findTCSucursales()).thenReturn(sucursales);

		response = sucursalesService.consultaTCSucursales();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		Sucursal sucu= new Sucursal();
		sucu.setFechaModificacion("2024-08-27");
		sucu.setId("0172");
		
		Divisas usd = new Divisas();
		usd.setId("USD");
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion compra = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		compra.setIdTipoOperacion("410");
		compra.setTipoCambioSpot(12.2);
		compra.setTipoCambioSpread(0.15);
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion venta = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		venta.setIdTipoOperacion("411");
		venta.setTipoCambioSpot(12.2);
		venta.setTipoCambioSpread(0.15);
		
		List<com.baz.divisas.api.tef.sucursales.model.TipoOperacion> tipos = new ArrayList<com.baz.divisas.api.tef.sucursales.model.TipoOperacion>();
		tipos.add(compra);
		tipos.add(venta);
		
		usd.setTiposOperacion(tipos);
		
		sucu.setDivisas(Arrays.asList(usd));
		
		sucursales.add(sucu);
		
		when(sucursalesDao.findTCSucursales()).thenReturn(sucursales);		
		response = sucursalesService.consultaTCSucursales();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	
	@Test
	public void testConsultaTCSucursalesOlp() {

		List<Sucursal> sucursales = new ArrayList<>();
		when(sucursalesDao.findTCSucursalesOlp()).thenReturn(null);

		ResponseApi response = sucursalesService.consultaTCSucursalesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		sucursales = new ArrayList<>();
		when(sucursalesDao.findTCSucursalesOlp()).thenReturn(sucursales);

		response = sucursalesService.consultaTCSucursalesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		Sucursal sucu= new Sucursal();
		sucu.setFechaModificacion("2024-08-27");
		sucu.setId("0172");
		
		Divisas usd = new Divisas();
		usd.setId("OLP");
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion compra = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		compra.setIdTipoOperacion("410");
		compra.setTipoCambioSpot(12.2);
		compra.setTipoCambioSpread(0.15);
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion venta = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		venta.setIdTipoOperacion("411");
		venta.setTipoCambioSpot(12.2);
		venta.setTipoCambioSpread(0.15);
		
		List<com.baz.divisas.api.tef.sucursales.model.TipoOperacion> tipos = new ArrayList<com.baz.divisas.api.tef.sucursales.model.TipoOperacion>();
		tipos.add(compra);
		tipos.add(venta);
		
		usd.setTiposOperacion(tipos);
		
		sucu.setTcOlp(usd);
		
		sucursales.add(sucu);
		
		when(sucursalesDao.findTCSucursalesOlp()).thenReturn(sucursales);		
		response = sucursalesService.consultaTCSucursalesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void testActualizaTCSucursal() {
		Spot spot = new Spot();
		spot.setCompraEfectivo(12.45);
		spot.setVentaEfectivo(18.30);
		
		when(spotDao.findSpotVigenteDivisa(isA(String.class))).thenReturn(spot);
		
		ReqActSucursal req = new ReqActSucursal();
		req.setIdsucursal("0172");
		
		com.baz.divisas.api.tef.sucursales.model.Divisa usd= new com.baz.divisas.api.tef.sucursales.model.Divisa();
		usd.setIdDivisa("USD");
		usd.setSpread(new TC());
		usd.setTc(new TC());
		
		List<com.baz.divisas.api.tef.sucursales.model.Divisa> divisas = new ArrayList<com.baz.divisas.api.tef.sucursales.model.Divisa>();
		divisas.add(usd);		
		req.setDivisas(divisas);
		
		List<String> ld = new ArrayList<>();
		ld.add("0172");
		
		promoEspacioImpl.armaTCSucursal(any());
		
		ResponseApi response = sucursalesService.actualizaTCSucursal(req);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		////////
		spot.setCompraEfectivo(18.45);
		spot.setVentaEfectivo(18.30);
		
		when(spotDao.findSpotVigenteDivisa(isA(String.class))).thenReturn(spot);
		response = sucursalesService.actualizaTCSucursal(req);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		
		spot.setCompraEfectivo(18.45);
		spot.setVentaEfectivo(18.30);
		
		TC spread = new TC();
		spread.setCompra(-0.20);
		spread.setVenta(0.25);
		usd.setSpread(spread);
		
		divisas = new ArrayList<com.baz.divisas.api.tef.sucursales.model.Divisa>();
		divisas.add(usd);		
		req.setDivisas(divisas);
		
		when(spotDao.findSpotVigenteDivisa(isA(String.class))).thenReturn(spot);
		response = sucursalesService.actualizaTCSucursal(req);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	@Test
	public void testDescargaTCSucursales() {
		when(sucursalesDao.consulta()).thenReturn(null);
		ByteArrayInputStream response = sucursalesService.descargaTCSucursales();
		assertThat(response).isNull();
		
		List<Sucursal> sucursales = new ArrayList<Sucursal>();
		Sucursal sucursal = new Sucursal();
		sucursal.setId("0172");
		sucursal.setFechaUltimoCambio("2024-05-18");
		sucursal.setCalleSucursalArchivo("123123");
		sucursal.setCpSucursalArchivo("1223");
		sucursal.setEstadoSucursalArchivo("CDMX");
		sucursal.setMunicipioSucursalArchivo("Tlalpan");
		sucursal.setNombreSucursalArchivo("Torres Esmeralda");
		sucursal.setNumeroRegionArchivo("12");
		sucursal.setNumeroSucursalArchivo("213123");
		sucursal.setRegionSucursalArchivo("Norte");
		sucursal.setTipoSucursalArchivo("1");		
		
		com.baz.divisas.api.tef.sucursales.model.Divisas usd = new Divisas();
		usd.setId("USD");
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion compra = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		compra.setIdTipoOperacion("410");
		compra.setTipoCambioSpot(12.2);
		compra.setTipoCambioSpread(0.15);
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion venta = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		venta.setIdTipoOperacion("411");
		venta.setTipoCambioSpot(12.2);
		venta.setTipoCambioSpread(0.15);
		
		List<com.baz.divisas.api.tef.sucursales.model.TipoOperacion> tipos = new ArrayList<com.baz.divisas.api.tef.sucursales.model.TipoOperacion>();
		tipos.add(compra);
		tipos.add(venta);
		
		usd.setTiposOperacion(tipos);

		//CAD
		com.baz.divisas.api.tef.sucursales.model.Divisas cad = new Divisas();
		cad.setId("CAD");
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion compraCad = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		compraCad.setIdTipoOperacion("410");
		compraCad.setTipoCambioSpot(12.2);
		compraCad.setTipoCambioSpread(0.15);
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion ventaCad = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		ventaCad.setIdTipoOperacion("411");
		ventaCad.setTipoCambioSpot(12.2);
		ventaCad.setTipoCambioSpread(0.15);
		
		List<com.baz.divisas.api.tef.sucursales.model.TipoOperacion> tiposCad = new ArrayList<com.baz.divisas.api.tef.sucursales.model.TipoOperacion>();
		tiposCad.add(compraCad);
		tiposCad.add(ventaCad);
		
		cad.setTiposOperacion(tiposCad);
		
		
		//EUR
		com.baz.divisas.api.tef.sucursales.model.Divisas eur = new Divisas();
		eur.setId("EUR");
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion compraEur = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		compraEur.setIdTipoOperacion("410");
		compraEur.setTipoCambioSpot(12.2);
		compraEur.setTipoCambioSpread(0.15);
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion ventaEur = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		ventaEur.setIdTipoOperacion("411");
		ventaEur.setTipoCambioSpot(12.2);
		ventaEur.setTipoCambioSpread(0.15);
		
		List<com.baz.divisas.api.tef.sucursales.model.TipoOperacion> tiposEur = new ArrayList<com.baz.divisas.api.tef.sucursales.model.TipoOperacion>();
		tiposEur.add(compraEur);
		tiposEur.add(ventaEur);
		
		eur.setTiposOperacion(tiposEur);
		
		//EUR
		com.baz.divisas.api.tef.sucursales.model.Divisas gbp = new Divisas();
		gbp.setId("GBP");

		com.baz.divisas.api.tef.sucursales.model.TipoOperacion compraGbp = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		compraGbp.setIdTipoOperacion("410");
		compraGbp.setTipoCambioSpot(12.2);
		compraGbp.setTipoCambioSpread(0.15);

		com.baz.divisas.api.tef.sucursales.model.TipoOperacion ventaGbp = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		ventaGbp.setIdTipoOperacion("411");
		ventaGbp.setTipoCambioSpot(12.2);
		ventaGbp.setTipoCambioSpread(0.15);

		List<com.baz.divisas.api.tef.sucursales.model.TipoOperacion> tiposGbp = new ArrayList<com.baz.divisas.api.tef.sucursales.model.TipoOperacion>();
		tiposGbp.add(compraGbp);
		tiposGbp.add(ventaGbp);

		gbp.setTiposOperacion(tiposGbp);
		
		
		sucursal.setDivisas(Arrays.asList(usd,cad,eur,gbp));
		
		sucursales.add(sucursal);
		when(sucursalesDao.consulta()).thenReturn(sucursales);
		response = sucursalesService.descargaTCSucursales();
		assertThat(response).isNotNull();
	}
	
	@Test
	public void testDescargaTCSucursalesOlp() {
		when(sucursalesDao.consultaOlp()).thenReturn(null);
		ByteArrayInputStream response = sucursalesService.descargaTCSucursalesOlp();
		assertThat(response).isNull();
		
		List<Sucursal> sucursales = new ArrayList<Sucursal>();
		Sucursal sucursal = new Sucursal();
		sucursal.setId("0172");
		sucursal.setFechaUltimoCambio("2024-05-18");
		sucursal.setCalleSucursalArchivo("123123");
		sucursal.setCpSucursalArchivo("1223");
		sucursal.setEstadoSucursalArchivo("CDMX");
		sucursal.setMunicipioSucursalArchivo("Tlalpan");
		sucursal.setNombreSucursalArchivo("Torres Esmeralda");
		sucursal.setNumeroRegionArchivo("12");
		sucursal.setNumeroSucursalArchivo("213123");
		sucursal.setRegionSucursalArchivo("Norte");
		sucursal.setTipoSucursalArchivo("1");		
		
		com.baz.divisas.api.tef.sucursales.model.Divisas olp = new Divisas();
		olp.setId("OLP");
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion compra = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		compra.setIdTipoOperacion("410");
		compra.setTipoCambioSpot(12.2);
		compra.setTipoCambioSpread(0.15);
		
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion venta = new com.baz.divisas.api.tef.sucursales.model.TipoOperacion();
		venta.setIdTipoOperacion("411");
		venta.setTipoCambioSpot(12.2);
		venta.setTipoCambioSpread(0.15);
		
		List<com.baz.divisas.api.tef.sucursales.model.TipoOperacion> tipos = new ArrayList<com.baz.divisas.api.tef.sucursales.model.TipoOperacion>();
		tipos.add(compra);
		tipos.add(venta);
		
		olp.setTiposOperacion(tipos);

		sucursal.setTcOlp(olp);
		
		sucursales.add(sucursal);
		when(sucursalesDao.consultaOlp()).thenReturn(sucursales);
		response = sucursalesService.descargaTCSucursalesOlp();
		assertThat(response).isNotNull();
	}
	
	@Test
	public void testActualizaTCSucursalOlp() {
		Spot spot = new Spot();
		spot.setCompraEfectivo(12.45);
		spot.setVentaEfectivo(18.30);
		
		when(spotDao.findSpotVigenteDivisa(isA(String.class))).thenReturn(spot);
		
		ReqActSucursal req = new ReqActSucursal();
		req.setIdsucursal("0172");
		
		com.baz.divisas.api.tef.sucursales.model.Divisa olp= new com.baz.divisas.api.tef.sucursales.model.Divisa();
		olp.setIdDivisa("OLP");
		olp.setSpread(new TC());
		olp.setTc(new TC());
		
		List<com.baz.divisas.api.tef.sucursales.model.Divisa> divisas = new ArrayList<com.baz.divisas.api.tef.sucursales.model.Divisa>();
		divisas.add(olp);		
		req.setDivisas(divisas);
		
		List<String> ld = new ArrayList<>();
		ld.add("0172");
		
		promoEspacioImpl.armaTCSucursal(any());
		
		ResponseApi response = sucursalesService.actualizaTCSucursalOlp(req);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		////////
		spot.setCompraEfectivo(18.45);
		spot.setVentaEfectivo(18.30);
		
		when(spotDao.findSpotVigenteDivisa(isA(String.class))).thenReturn(spot);
		response = sucursalesService.actualizaTCSucursalOlp(req);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		
		spot.setCompraEfectivo(18.45);
		spot.setVentaEfectivo(18.30);
		
		TC spread = new TC();
		spread.setCompra(-0.20);
		spread.setVenta(0.25);
		olp.setSpread(spread);
		
		divisas = new ArrayList<com.baz.divisas.api.tef.sucursales.model.Divisa>();
		divisas.add(olp);		
		req.setTcOlp(olp);
		
		when(spotDao.findSpotVigenteDivisa(isA(String.class))).thenReturn(spot);
		response = sucursalesService.actualizaTCSucursalOlp(req);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testCargaArchivoTC() {
		
		byte[] excelFileContent = null;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("SampleSheet");

			// Create headers
			Row headerRow = sheet.createRow(0);
			String[] headers = { "x", "sucursal", "regíon", "divisa usd", "tc sucursal compra usd",
					"estrategia compra usd", "tc sucursal venta usd", "estrategia venta usd", "divisa eur",
					"tc sucursal compra eur", "estrategia compra eur", "tc sucursal venta eur", "estrategia venta eur",
					"divisa cad", "tc sucursal compra cad", "estrategia compra cad", "tc sucursal venta cad",
					"estrategia venta cad", "divisa gbp", "tc sucursal compra gbp", "estrategia compra gbp", 
					"tc sucursal venta gbp","estrategia venta gbp",
					"nombre", "tipo", "municipio", "estado", "calle", "número", "cp",
					"número de región"
			};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Add sample data
			String[][] sampleData = {{"404", "0172", "regíon", "USD", "18.50",
				"-0.50", "18.50", "1.20", "EUR",
				"15.20", "-0.20", "15.50", "1.0",
				"CAD", "12.20", "-0.02", "13.10",
				"0.20","GBP", "12.20", "-0.02", "13.10",
				"0.20", "nombre", "tipo", "municipio", "estado", "calle", "número", "cp",
				"número de región"
					}, {"404", "2244", "regíon", "USD", "18.50",
						"-0.50", "18.50", "1.20", "EUR",
						"15.20", "-0.20", "15.50", "1.0",
						"CAD", "12.20", "-0.02", "13.10",
						"0.20", "GBP", "12.20", "-0.02", "13.10",
						"0.20","nombre", "tipo", "", "estado", "calle", "número", "cp",
						"número de región"
					}
			};

			for (int rowIdx = 0; rowIdx < sampleData.length; rowIdx++) {
				Row dataRow = sheet.createRow(rowIdx + 1);
				for (int colIdx = 0; colIdx < sampleData[rowIdx].length; colIdx++) {
					Cell cell = dataRow.createCell(colIdx);
					cell.setCellValue(sampleData[rowIdx][colIdx]);
				}
			}

			// Write the workbook content to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			excelFileContent = outputStream.toByteArray();
		}
		catch (IOException e) {

		}

		fileMock = new MockMultipartFile("prueba", "prueba.xlsx", "text/plain", excelFileContent);
		ResponseApi result = sucursalesService.cargaArchivoTC(fileMock);

		assertNotNull(result);
		
		excelFileContent = null;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("SampleSheet");

			// Create headers
			Row headerRow = sheet.createRow(0);
			String[] headers = { "sucursal", "regíon", "divisa usd", "tc sucursal compra usd",
					"estrategia compra usd", "tc sucursal venta usd", "estrategia venta usd", "divisa eur",
					"tc sucursal compra eur", "estrategia compra eur", "tc sucursal venta eur", "estrategia venta eur",
					"divisa cad", "tc sucursal compra cad", "estrategia compra cad", "tc sucursal venta cad",
					"estrategia venta cad", "divisa gbp", "tc sucursal compra gbp", "estrategia compra gbp", 
					"tc sucursal venta gbp","estrategia venta gbp",	"nombre", "tipo", "municipio", "estado",
					"calle", "número", "cp","número de región"
			};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Add sample data
			String[][] sampleData = {{"404", "0172", "regíon", "USD", "18.50",
				"-0.50", "18.50", "1.20",
				"15.20", "-0.20", "15.50", "1.0",
				"CAD", "12.20", "-0.02", "13.10",
				"0.20","GBP", "12.20", "-0.02", "13.10",
				"0.20", "nombre", "tipo", "municipio", "estado", "calle", "número", "cp",
				"número de región"
					}, {"404",  "regíon", "USD", "18.50",
						"-0.50", "18.50", "1.20", "EUR",
						"15.20", "-0.20", "15.50", "1.0",
						"CAD", "12.20", "-0.02", "13.10",
						"0.20","GBP", "12.20", "-0.02", "13.10",
						"0.20", "nombre", "tipo", "", "estado", "calle", "número", "cp",
						"número de región"
					}
			};

			for (int rowIdx = 0; rowIdx < sampleData.length; rowIdx++) {
				Row dataRow = sheet.createRow(rowIdx + 1);
				for (int colIdx = 0; colIdx < sampleData[rowIdx].length; colIdx++) {
					Cell cell = dataRow.createCell(colIdx);
					cell.setCellValue(sampleData[rowIdx][colIdx]);
				}
			}

			// Write the workbook content to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			excelFileContent = outputStream.toByteArray();
		}
		catch (IOException e) {

		}

		fileMock = new MockMultipartFile("prueba", "prueba.xlsx", "text/plain", excelFileContent);
		result = sucursalesService.cargaArchivoTC(fileMock);

		assertNotNull(result);
	}
	
	@Test
	public void testCargaArchivoTCOLP() {

		
		byte[] excelFileContent = null;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("SampleSheet");

			// Create headers
			Row headerRow = sheet.createRow(0);
			String[] headers = { "sucursal", "divisa", "tc sucursal compra",
					"estrategia compra", "tc sucursal venta", "estrategia venta"
			};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Add sample data
			String[][] sampleData = {{"0172", "OLP", "18.50",
				"-0.50", "18.50", "1.20"
					}, {"2244", "OLP", "18.50",
						"-0.50", "18.50", "1.20"
					}
			};

			for (int rowIdx = 0; rowIdx < sampleData.length; rowIdx++) {
				Row dataRow = sheet.createRow(rowIdx + 1);
				for (int colIdx = 0; colIdx < sampleData[rowIdx].length; colIdx++) {
					Cell cell = dataRow.createCell(colIdx);
					cell.setCellValue(sampleData[rowIdx][colIdx]);
				}
			}

			// Write the workbook content to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			excelFileContent = outputStream.toByteArray();
		}
		catch (IOException e) {

		}

		fileMock = new MockMultipartFile("prueba", "prueba.xlsx", "text/plain", excelFileContent);
		ResponseApi result = sucursalesService.cargaArchivoTCOlp(fileMock);

		assertNotNull(result);
		
		excelFileContent = null;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("SampleSheet");

			// Create headers
			Row headerRow = sheet.createRow(0);
			String[] headers = { "divisa", "tc sucursal compra",
					"estrategia compra", "tc sucursal venta", "estrategia venta"
			};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Add sample data
			String[][] sampleData = {{"0172", "OLP", "18.50",
				"-0.50", "18.50", 
					}, {"2244", "OLP", 
						"-0.50", "18.50", "1.20"
					}
			};

			for (int rowIdx = 0; rowIdx < sampleData.length; rowIdx++) {
				Row dataRow = sheet.createRow(rowIdx + 1);
				for (int colIdx = 0; colIdx < sampleData[rowIdx].length; colIdx++) {
					Cell cell = dataRow.createCell(colIdx);
					cell.setCellValue(sampleData[rowIdx][colIdx]);
				}
			}

			// Write the workbook content to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			excelFileContent = outputStream.toByteArray();
		}
		catch (IOException e) {

		}

		fileMock = new MockMultipartFile("prueba", "prueba.xlsx", "text/plain", excelFileContent);
		result = sucursalesService.cargaArchivoTCOlp(fileMock);

		assertNotNull(result);
	}
}
