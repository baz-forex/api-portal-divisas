package com.baz.divisas.api.tef.spots;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.SucursalTC;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.spots.dao.SpotsDao;
import com.baz.divisas.api.tef.spots.dto.ResSpotTesoreria;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.baz.divisas.api.tef.spots.model.Divisas;
import com.baz.divisas.api.tef.spots.model.TipoCambioSpotDivisa;
import com.baz.divisas.api.tef.spots.services.impl.SpotService;
import com.baz.divisas.api.tef.spots.services.impl.SpotsServiceImpl;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.model.TipoOperacion;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.InsertOneResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpotImplTest {
	
	@InjectMocks
	private SpotsServiceImpl spotServicesImpl;
	@Mock
	private SpotsDao spotDAO;
	@Mock
	private RegionesDao regionesDao;
	@Mock
	private SegmentosDao segmentosDao;
	@Mock
	private BitacoraServiceImpl bitacoraService;
	@Mock
	private SucursalesDao sucursalesDao;
	@Mock
	private PromoEspacioServiceImpl promoEspacioImpl;
	@Mock
	private SpotService spotService;
	@Mock
	private ParametriaDao parametria;
	@Test
	void testConsultaSpots() {

		ArrayList<Spot> listSpot = new ArrayList<>();

		when(spotDAO.findSpots()).thenReturn(listSpot);
		ResponseApi response = spotServicesImpl.consultaSpots();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		listSpot.add(new Spot());

		when(spotDAO.findSpots()).thenReturn(listSpot);
		response = spotServicesImpl.consultaSpots();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaSpotPorFecha() {

		ArrayList<Spot> listSpot = new ArrayList<>();

		when(spotDAO.findSpotsPorFecha(isA(String.class), isA(String.class), anyList())).thenReturn(listSpot);
		ResponseApi response = spotServicesImpl.consultaSpotPorFecha("", "", Collections.emptyList());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		listSpot.add(new Spot());

		when(spotDAO.findSpots()).thenReturn(listSpot);
		response = spotServicesImpl.consultaSpotPorFecha("", "", Collections.emptyList());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaSpotsVigentes() {

		ResponseApi response = spotServicesImpl.consultaSpotsVigentes( Collections.emptyList());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());


		when(spotDAO.findSpotVigenteDivisa(isA(String.class))).thenReturn(new Spot());
		response = spotServicesImpl.consultaSpotsVigentes(Arrays.asList(""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCreaSpot() {

		doNothing().when(spotDAO).insertSpots(any());
		List<Spot> spots = new ArrayList<Spot>();
		ResponseApi response = spotServicesImpl.creaSpots(spots);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		// ------------------------------------------
		
		SucursalTC sucu = new SucursalTC();
		
		TipoOperacion divisaCompra = new TipoOperacion("410", 18.20, 0.20);
		
		TipoOperacion divisaVenta = new TipoOperacion("411", 19.20, 0.20);
		sucu.setId("0172");
		sucu.setDivisaCompra(divisaCompra);
		sucu.setDivisaVenta(divisaVenta);		 
				
		doNothing().when(spotDAO).insertSpots(any());
		
		List<SucursalTC> sucursales = new ArrayList<>();
		sucursales.add(sucu);
		
		Spot req = new Spot();
		req.setIdDivisa("USD");
		req.setCompraEfectivo(18.15);
		req.setVentaEfectivo(19.10);
		spots.add(req);
		Spot cad = new Spot();
		cad.setIdDivisa("CAD");
		cad.setCompraEfectivo(18.15);
		cad.setVentaEfectivo(19.10);
		spots.add(cad);
		Spot eur = new Spot();
		eur.setIdDivisa("EUR");
		eur.setCompraEfectivo(18.15);
		eur.setVentaEfectivo(19.10);
		spots.add(eur);
		Spot gbp = new Spot();
		gbp.setIdDivisa("GBP");
		gbp.setCompraEfectivo(18.15);
		gbp.setVentaEfectivo(19.10);
		spots.add(gbp);
		when(sucursalesDao.consultaSucursalesTC(isA(String.class))).thenReturn(sucursales);
		when(spotDAO.updateTCSpotSucursales(isA(String.class),isA(String.class), isA(String.class),isA(Double.class))).thenReturn(null);
		doNothing().when(promoEspacioImpl).armaTCSucursal(any());
		doNothing().when(bitacoraService).registrar(isA(AccionBitacora.class),isA(String.class));
		when(spotDAO.updateIdBitacoraAlta(isA(String.class),isA(String.class))).thenReturn(null);
		Document param = new Document();
		when(parametria.consultar(isA(String.class))).thenReturn(param);

		response = spotServicesImpl.creaSpots(spots);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		
		param = new Document("activo",true).append("archivosSpot", true);
		when(parametria.consultar(isA(String.class))).thenReturn(param);

		response = spotServicesImpl.creaSpots(spots);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		// ------------------------------------------
		
	}
	
	@Test
	void testCreaSpotOLP() {

		InsertOneResult result = InsertOneResult.acknowledged(null);

		when(spotDAO.insertSpot(isA(Spot.class))).thenReturn(result);
		Document doc = new Document("spotMinimo",1000.0);
		when(parametria.consultar(any())).thenReturn(doc);
		Spot spot = new Spot();
		spot.setCompraEfectivo(1200.0);
		ResponseApi response = spotServicesImpl.creaSpotOlp(spot);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		// ------------------------------------------
		
		Spot req = new Spot();
		req.setIdDivisa("OLP");
		req.setCompraEfectivo(18.15);
		req.setVentaEfectivo(19.10);
		
		result = InsertOneResult.acknowledged(new BsonObjectId());
		
		when(spotDAO.insertSpot(isA(Spot.class))).thenReturn(result);
		when(sucursalesDao.consultaSucursalesOlp(isA(String.class))).thenReturn(new ArrayList<>());
		when(spotDAO.updateTCSpotSucursalesOLP(isA(String.class),isA(String.class), isA(Double.class))).thenReturn(null);
		doNothing().when(promoEspacioImpl).armaTCSucursal(any());
		doNothing().when(bitacoraService).registrar(isA(AccionBitacora.class),isA(String.class));
		when(spotDAO.updateIdBitacoraAlta(isA(String.class),isA(String.class))).thenReturn(null);
		spot = new Spot();
		spot.setCompraEfectivo(1200.0);
		response = spotServicesImpl.creaSpotOlp(spot);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		
		SucursalTC tcSuc = new SucursalTC();
		tcSuc.setId("OLP");
		tcSuc.setDivisaCompra(new TipoOperacion("410", 1200.0, 1300.0));
		tcSuc.setDivisaVenta(new TipoOperacion("411", 1200.0, 1300.0));
		List<SucursalTC> lista = new ArrayList<SucursalTC>();
		lista.add(tcSuc);
		when(spotDAO.insertSpot(isA(Spot.class))).thenReturn(result);
		when(sucursalesDao.consultaSucursalesOlp(isA(String.class))).thenReturn(lista);
		when(spotDAO.updateTCSpotSucursalesOLP(isA(String.class),isA(String.class), isA(Double.class))).thenReturn(null);
		doNothing().when(promoEspacioImpl).armaTCSucursal(any());
		doNothing().when(bitacoraService).registrar(isA(AccionBitacora.class),isA(String.class));
		when(spotDAO.updateIdBitacoraAlta(isA(String.class),isA(String.class))).thenReturn(null);
		spot = new Spot();
		spot.setCompraEfectivo(1200.0);
		response = spotServicesImpl.creaSpotOlp(spot);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	@Test
	public void testconsultaSpotsTesoreria() {

		ResSpotTesoreria respApi = new ResSpotTesoreria();
		
		TokenApigee token = new TokenApigee();
		token.setAccess_token(null);
		when(spotService.getTokenApi()).thenReturn(token);
		
		ResponseApi response = spotServicesImpl.consultaSpotsTesoreria();
		assertThat(response.getCodigo()).isNotNull();
		
		token.setAccess_token("123123123");
		
		when(spotService.getTokenApi()).thenReturn(token);
		when(spotService.consultaSpotTesoreria(any(), any())).thenReturn(respApi);
		response = spotServicesImpl.consultaSpotsTesoreria();
		assertThat(response.getCodigo()).isNotNull();
		
		TipoCambioSpotDivisa usd = new TipoCambioSpotDivisa();
		usd.setDivisa("USDMXN");
		
		TipoCambioSpotDivisa cad = new TipoCambioSpotDivisa();
		cad.setDivisa("CADMXN");
		
		TipoCambioSpotDivisa eur = new TipoCambioSpotDivisa();
		eur.setDivisa("EURMXN");
		
		TipoCambioSpotDivisa gbp = new TipoCambioSpotDivisa();
		gbp.setDivisa("GBPMXN");
		
		ArrayList<TipoCambioSpotDivisa> tiposCambio = new ArrayList<>();
		tiposCambio.add(usd);
		tiposCambio.add(cad);
		tiposCambio.add(eur);
		tiposCambio.add(gbp);
		Divisas divisas = new Divisas();
		divisas.setDivisas(tiposCambio);
		respApi.setResultado(divisas);
		respApi.toString();
		when(spotService.getTokenApi()).thenReturn(token);
		when(spotService.consultaSpotTesoreria(any(), any())).thenReturn(respApi);
		response = spotServicesImpl.consultaSpotsTesoreria();
		assertThat(response.getCodigo()).isNotNull();
	}

	@Test
	void testCreaSpotOlpMenorAlMinimo() {
		// spotMinimo > compraEfectivo -> ERROR_INTERNO, no inserta
		Document doc = new Document("spotMinimo", 2000.0);
		when(parametria.consultar(any())).thenReturn(doc);

		Spot spot = new Spot();
		spot.setCompraEfectivo(1000.0);

		ResponseApi response = spotServicesImpl.creaSpotOlp(spot);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@Test
	void testConsultaConfiguracionSpotOLP() {
		when(parametria.consultar(any())).thenReturn(new Document("acunacion", 20.0));

		ResponseApi response = spotServicesImpl.consultaConfiguracionSpotOLP();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testGuardaConfiguracionSpotOLP() {
		com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot req =
				new com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot();

		// Actualizacion exitosa
		when(parametria.updateParametriaConfiguracionSpotOLP(any())).thenReturn(true);
		ResponseApi response = spotServicesImpl.guardaConfiguracionSpotOLP(req);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

		// Sin actualizacion -> SIN_RESULTADOS
		when(parametria.updateParametriaConfiguracionSpotOLP(any())).thenReturn(false);
		response = spotServicesImpl.guardaConfiguracionSpotOLP(req);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void testConsultaSpotsVigentesOLP() {
		// Token sin acceso -> respuesta null -> igual arma la lista con el spot vigente
		TokenApigee tokenNulo = new TokenApigee();
		tokenNulo.setAccess_token(null);
		when(spotService.getTokenApi()).thenReturn(tokenNulo);
		when(spotDAO.findSpotVigenteDivisa(any())).thenReturn(new Spot());

		ResponseApi response = spotServicesImpl.consultaSpotsVigentesOLP();
		assertThat(response.getCodigo()).isNotNull();

		// Token valido + respuesta con divisas USD y XAG -> calcula precio Bloomberg
		TokenApigee token = new TokenApigee();
		token.setAccess_token("123123123");
		when(spotService.getTokenApi()).thenReturn(token);

		TipoCambioSpotDivisa usd = new TipoCambioSpotDivisa();
		usd.setDivisa("USDMXN");
		usd.setTipoCambio("18.50");

		TipoCambioSpotDivisa xag = new TipoCambioSpotDivisa();
		xag.setDivisa("XAG");
		xag.setTipoCambio("30.00");

		ArrayList<TipoCambioSpotDivisa> tiposCambio = new ArrayList<>();
		tiposCambio.add(usd);
		tiposCambio.add(xag);
		Divisas divisas = new Divisas();
		divisas.setDivisas(tiposCambio);
		ResSpotTesoreria respApi = new ResSpotTesoreria();
		respApi.setResultado(divisas);

		when(spotService.consultaSpotTesoreria(any(), any())).thenReturn(respApi);
		when(parametria.consultar(any()))
				.thenReturn(new Document("acunacion", 2.0).append("diferenciaCompraVenta", 5.0));
		when(spotDAO.findSpotVigenteDivisa(any())).thenReturn(new Spot());

		response = spotServicesImpl.consultaSpotsVigentesOLP();
		assertThat(response.getCodigo()).isNotNull();
	}
}

