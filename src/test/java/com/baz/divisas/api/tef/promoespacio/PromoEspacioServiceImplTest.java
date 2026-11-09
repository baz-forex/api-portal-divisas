package com.baz.divisas.api.tef.promoespacio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.apigee.dto.ResultadoApi;
import com.baz.divisas.api.apigee.dto.ResultadoLlaves;
import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.apigee.model.ApiLlaves;
import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.general.model.SucursalTC;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.promoespacio.dao.PromoEspacioDao;
import com.baz.divisas.api.tef.promoespacio.model.Accesos;
import com.baz.divisas.api.tef.promoespacio.model.Auth;
import com.baz.divisas.api.tef.promoespacio.model.BeanDistanciaSucursales;
import com.baz.divisas.api.tef.promoespacio.model.BeanDistancias;
import com.baz.divisas.api.tef.promoespacio.model.DivisasBean;
import com.baz.divisas.api.tef.promoespacio.model.ResponsePromoEspacio;
import com.baz.divisas.api.tef.promoespacio.model.ResponseToken;
import com.baz.divisas.api.tef.promoespacio.model.Resultado;
import com.baz.divisas.api.tef.promoespacio.services.CryptoService;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioService;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.sucursales.model.TipoOperacion;
import com.baz.divisas.api.tef.sucursales.model.TiposCambioDiv;
import com.baz.divisas.api.tef.sucursales.service.TiposCambioService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromoEspacioServiceImplTest {

	@InjectMocks
	private PromoEspacioServiceImpl impl;

	@Mock
	private PromoEspacioService service;
	@Mock
	private PromoEspacioDao dao;
	@Mock
	private ParametriaDao parametria;
	@Mock
	private CatalogosDao catalogoDao;
	@Mock
	private TiposCambioService tiposCambio;
	@Mock
	private CryptoService cryptoService;

	private PromoEspacioServiceImpl nuevoImpl() {
		return new PromoEspacioServiceImpl(service, dao, parametria, catalogoDao, tiposCambio, cryptoService);
	}

	// ==================== Constructores ====================

	@Test
	void testConstructorDefault() {
		PromoEspacioServiceImpl def = new PromoEspacioServiceImpl();
		assertThat(def).isNotNull();
	}

	// ==================== armaTCSucursal ====================

	@Test
	void testArmaTCSucursalParametriaInactiva() {
		impl = nuevoImpl();

		// doc vacio
		when(parametria.consultar(isA(String.class))).thenReturn(new Document());
		impl.armaTCSucursal(Arrays.asList("0172"));

		// doc con activo=false
		when(parametria.consultar(isA(String.class))).thenReturn(new Document("activo", false));
		impl.armaTCSucursal(Arrays.asList("0172"));
	}

	@Test
	void testArmaTCSucursalFlujoV1() throws InterruptedException {
		impl = nuevoImpl();

		List<String> ld = new ArrayList<>();
		ld.add("0172");

		Document param = new Document();
		param.append("activo", true);
		param.append("tipoFlujo", "v1");
		param.append("divisas", Arrays.asList(new Document().append("divisa", "USD").append("compra", "dolarCompra").append("venta", "dolarVenta")));
		when(parametria.consultar(isA(String.class))).thenReturn(param);

		ld.add("9704");
		ld.add("4037");
		ld.add("5784");
		List<DivisasBean> lista = obtenerDivisasUSD(ld.toArray(String[]::new));
		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(lista);

		ResponseToken rt = new ResponseToken();
		rt.setCodigo(200);
		Resultado r = new Resultado();
		r.setToken("token1");
		rt.setResultado(r);
		when(service.getToken()).thenReturn(rt);

		ResponsePromoEspacio rpe = new ResponsePromoEspacio();
		rpe.setCodigo(200);
		when(service.putSucursalesAnterior(any(), anyList())).thenReturn(rpe);

		impl.armaTCSucursal(ld);
		Thread.sleep(150);

		// Rama: putSucursalesAnterior con codigo != 200 (error)
		rpe.setCodigo(500);
		when(service.putSucursalesAnterior(any(), anyList())).thenReturn(rpe);
		impl.armaTCSucursal(ld);
		Thread.sleep(150);

		// Rama: token v1 invalido (codigo != 200)
		rt.setCodigo(0);
		impl.armaTCSucursal(ld);
		Thread.sleep(150);

		// Rama: resultado null
		rt.setCodigo(200);
		rt.setResultado(null);
		impl.armaTCSucursal(ld);
		Thread.sleep(150);

		// Rama: token vacio
		r.setToken("");
		rt.setResultado(r);
		impl.armaTCSucursal(ld);
		Thread.sleep(150);
	}

	@Test
	void testArmaTCSucursalFlujoV2() throws InterruptedException {
		impl = nuevoImpl();

		List<String> ld = new ArrayList<>();
		ld.add("0172");

		Document param = new Document();
		param.append("activo", true);
		param.append("tipoFlujo", "v2");
		param.append("divisas", Arrays.asList(new Document().append("divisa", "USD").append("compra", "dolarCompra").append("venta", "dolarVenta")));
		when(parametria.consultar(isA(String.class))).thenReturn(param);

		ld.add("9704");
		ld.add("4037");
		ld.add("5784");
		List<DivisasBean> lista = obtenerDivisasUSD(ld.toArray(String[]::new));
		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(lista);

		when(cryptoService.encryptPassword()).thenReturn("passwordCifrado");
		when(cryptoService.getCipherRequestKey()).thenReturn("requestKey");
		when(cryptoService.decryptToken(any())).thenReturn("tokenDescifrado");

		// ResponseToken.getData() -> Auth.getAuth() -> Accesos.getAccess_token()
		ResponseToken rt = new ResponseToken();
		Auth data = new Auth();
		Accesos accesos = new Accesos();
		accesos.setAccess_token("accessTokenCifrado");
		data.setAuth(accesos);
		rt.setData(data);
		when(service.getToken(any(), any())).thenReturn(rt);

		ResponsePromoEspacio rpe = new ResponsePromoEspacio();
		rpe.setRequest_id("REQ-123");
		when(service.putSucursales(any(), anyList())).thenReturn(rpe);

		impl.armaTCSucursal(ld);
		Thread.sleep(150);

		// Rama: respuesta sin request_id (error)
		rpe.setRequest_id(null);
		when(service.putSucursales(any(), anyList())).thenReturn(rpe);
		impl.armaTCSucursal(ld);
		Thread.sleep(150);

		// Rama: token v2 null (detalle al obtener token)
		when(service.getToken(any(), any())).thenReturn(null);
		impl.armaTCSucursal(ld);
		Thread.sleep(150);

		// Rama: data null
		ResponseToken rtSinData = new ResponseToken();
		rtSinData.setData(null);
		when(service.getToken(any(), any())).thenReturn(rtSinData);
		impl.armaTCSucursal(ld);
		Thread.sleep(150);

		// Rama: auth null
		ResponseToken rtSinAuth = new ResponseToken();
		rtSinAuth.setData(new Auth());
		when(service.getToken(any(), any())).thenReturn(rtSinAuth);
		impl.armaTCSucursal(ld);
		Thread.sleep(150);
	}

	// ==================== obtenerMejortipoCambio ====================

	@Test
	void testObtenerMejortipoCambioCorporativa() {
		impl = nuevoImpl();

		Document sucuCorp = new Document();
		sucuCorp.append("idSucursal", "0172");
		sucuCorp.append("dolarCompra", 18.15);
		sucuCorp.append("dolarVenta", 19.25);
		List<Document> sucursales = new ArrayList<>();
		sucursales.add(sucuCorp);

		Document app = new Document();
		app.append("compra", 18.20);
		app.append("venta", 19.20);

		Document corpo = new Document();
		corpo.append("dolarCompra", 18.20);
		corpo.append("dolarVenta", 19.20);

		impl.obtenerMejortipoCambio(sucursales, app, corpo);
	}

	@Test
	void testObtenerMejortipoCambioSinUbicacion() {
		impl = nuevoImpl();

		Document sucu = new Document();
		sucu.append("idSucursal", "2244");
		sucu.append("dolarCompra", 18.15);
		sucu.append("dolarVenta", 19.25);
		List<Document> sucursales = new ArrayList<>();
		sucursales.add(sucu);

		Document app = new Document();
		app.append("compra", 18.20);
		app.append("venta", 19.20);

		Document corpo = new Document();
		corpo.append("dolarCompra", 18.20);
		corpo.append("dolarVenta", 19.20);

		// Sin ubicacion -> aplicarTCMejorado
		when(catalogoDao.consultarCatUbicacionSucursales(isA(String.class))).thenReturn(null);
		impl.obtenerMejortipoCambio(sucursales, app, corpo);
	}

	@Test
	void testObtenerMejortipoCambioConCercanas() {
		impl = nuevoImpl();

		Document sucu = new Document();
		sucu.append("idSucursal", "2244");
		sucu.append("dolarCompra", 18.15);
		sucu.append("dolarVenta", 19.25);
		List<Document> sucursales = new ArrayList<>();
		sucursales.add(sucu);

		Document app = new Document();
		app.append("compra", 18.20);
		app.append("venta", 19.20);

		Document corpo = new Document();
		corpo.append("dolarCompra", 18.20);
		corpo.append("dolarVenta", 19.20);

		BeanDistanciaSucursales ubicacion = new BeanDistanciaSucursales();
		BeanDistancias dis = new BeanDistancias();
		List<Double> geo = new ArrayList<>();
		geo.add(19.29);
		geo.add(-99.18);
		dis.setCoordinates(geo);
		ubicacion.setLocation(dis);

		BeanDistanciaSucursales cercana = new BeanDistanciaSucursales();
		cercana.setName("2244");
		List<BeanDistanciaSucursales> cercanas = new ArrayList<>();
		cercanas.add(cercana);

		DivisasBean db1 = new DivisasBean();
		db1.setDivisa("USD");
		db1.setId("2244");
		db1.setTipoOperacion("410");
		db1.setTipoCambio(18.10);
		DivisasBean db2 = new DivisasBean();
		db2.setDivisa("USD");
		db2.setId("2244");
		db2.setTipoOperacion("411");
		db2.setTipoCambio(19.30);
		List<DivisasBean> beans = new ArrayList<>();
		beans.add(db1);
		beans.add(db2);

		when(catalogoDao.consultarCatUbicacionSucursales(isA(String.class))).thenReturn(ubicacion);
		when(catalogoDao.consultarSucursalesCercanas(any(), any())).thenReturn(cercanas);
		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(beans);

		impl.obtenerMejortipoCambio(sucursales, app, corpo);

		// Rama: ubicacion con coords pero SIN sucursales cercanas -> aplicarTCMejorado
		when(catalogoDao.consultarSucursalesCercanas(any(), any())).thenReturn(new ArrayList<>());
		impl.obtenerMejortipoCambio(sucursales, app, corpo);
	}

	// ==================== sucursalesCercanas / mejorTC ====================

	@Test
	void testSucursalesCercanasYMejorTC() {
		impl = nuevoImpl();

		BeanDistanciaSucursales beanSucu = new BeanDistanciaSucursales();
		beanSucu.setName("0172");
		List<BeanDistanciaSucursales> beans = new ArrayList<>();
		beans.add(beanSucu);

		Document sucu = new Document();
		sucu.append("idSucursal", "0172");
		sucu.append("dolarCompra", 18.15);
		sucu.append("dolarVenta", 19.25);
		List<Document> sucursales = new ArrayList<>();
		sucursales.add(sucu);

		DivisasBean s1 = new DivisasBean();
		s1.setDivisa("USD");
		s1.setId("0172");
		s1.setTipoOperacion("410");
		s1.setTipoCambio(18.15);
		DivisasBean s2 = new DivisasBean();
		s2.setDivisa("USD");
		s2.setId("0172");
		s2.setTipoOperacion("411");
		s2.setTipoCambio(19.35);
		List<DivisasBean> sucursalesBean = new ArrayList<>();
		sucursalesBean.add(s1);
		sucursalesBean.add(s2);
		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(sucursalesBean);

		// Rama: app peor -> gana sucursal (comparacionCompra > 0, comparacionVenta > 0)
		Document appPeor = new Document();
		appPeor.append("compra", 1.00);
		appPeor.append("venta", 99.99);
		impl.sucursalesCercanas(new Document(), beans, sucursales, appPeor);

		// Rama: app mejor (comparacionCompra < 0, comparacionVenta < 0)
		Document appMejor = new Document();
		appMejor.append("compra", 99.99);
		appMejor.append("venta", 1.00);
		impl.sucursalesCercanas(new Document(), beans, sucursales, appMejor);

		// Rama: iguales (comparacionCompra == 0, comparacionVenta == 0)
		// dolarCompra 18.15 + 0.05 = 18.20 ; dolarVenta 19.35 - 0.01 = 19.34
		Document appIgual = new Document();
		appIgual.append("compra", 18.20);
		appIgual.append("venta", 19.34);
		impl.sucursalesCercanas(new Document(), beans, sucursales, appIgual);
	}

	// ==================== seteaTCSucursal ====================

	@Test
	void testSeteaTCSucursalVacio() {
		impl = nuevoImpl();
		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(new ArrayList<>());

		BeanDistanciaSucursales bean = new BeanDistanciaSucursales();
		bean.setName("9999");
		List<BeanDistanciaSucursales> beans = new ArrayList<>();
		beans.add(bean);

		List<Document> resultado = impl.seteaTCSucursal(beans);
		assertThat(resultado).isEmpty();
	}

	@Test
	void testSeteaTCSucursalConDatos() {
		impl = nuevoImpl();

		DivisasBean suc = new DivisasBean();
		suc.setDivisa("USD");
		suc.setId("0172");
		suc.setSucursal(172);
		suc.setTipoCambio(18.20);
		suc.setTipoOperacion("410");
		List<DivisasBean> sucursales = new ArrayList<>();
		sucursales.add(suc);

		DivisasBean suc2 = new DivisasBean();
		suc2.setDivisa("USD");
		suc2.setId("0172");
		suc2.setSucursal(172);
		suc2.setTipoCambio(19.35);
		suc2.setTipoOperacion("411");
		sucursales.add(suc2);

		BeanDistanciaSucursales beanSucu = new BeanDistanciaSucursales();
		beanSucu.setName("0172");
		List<BeanDistanciaSucursales> beans = new ArrayList<>();
		beans.add(beanSucu);

		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(sucursales);
		List<Document> resultado = impl.seteaTCSucursal(beans);
		assertThat(resultado).isNotEmpty();
	}

	// ==================== registraTCMM / envioDivisas ====================

	@Test
	void testRegistraTCMMExitoso() {
		impl = nuevoImpl();

		SucursalTC sucu = new SucursalTC();
		sucu.setId("1827");
		sucu.setDivisaCompra(new TipoOperacion("410", 19.38, 0.05));
		sucu.setDivisaVenta(new TipoOperacion("411", 19.68, -0.01));
		when(dao.consultaSucursalesTC(isA(String.class), isA(String.class))).thenReturn(sucu);

		TokenApigee token = new TokenApigee();
		token.setCodigo("1");
		token.setAccess_token("1231231");
		when(tiposCambio.getTokenApiAWSInterno()).thenReturn(token);

		ApiLlaves llaves = new ApiLlaves();
		llaves.setCodigo("200");
		ResultadoLlaves resultado = new ResultadoLlaves();
		resultado.setAccesoPublico("123123123");
		resultado.setIdAcceso("123123123");
		llaves.setResultado(resultado);
		when(tiposCambio.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		when(tiposCambio.registraTiposCambioDivisas(isA(TiposCambioDiv.class), isA(String.class), isA(String.class)))
				.thenReturn(new ResultadoApi());

		impl.registraTCMM();
	}

	@Test
	void testRegistraTCMMTokenNuloYVacio() {
		impl = nuevoImpl();

		SucursalTC sucu = new SucursalTC();
		sucu.setId("1827");
		sucu.setDivisaCompra(new TipoOperacion("410", 19.38, 0.05));
		sucu.setDivisaVenta(new TipoOperacion("411", 19.68, -0.01));
		when(dao.consultaSucursalesTC(isA(String.class), isA(String.class))).thenReturn(sucu);

		// Token null
		when(tiposCambio.getTokenApiAWSInterno()).thenReturn(null);
		impl.registraTCMM();

		// Token con access_token vacio
		TokenApigee tokenVacio = new TokenApigee();
		tokenVacio.setAccess_token("");
		when(tiposCambio.getTokenApiAWSInterno()).thenReturn(tokenVacio);
		impl.registraTCMM();
	}

	@Test
	void testRegistraTCMMSinLlaves() {
		impl = nuevoImpl();

		SucursalTC sucu = new SucursalTC();
		sucu.setId("1827");
		sucu.setDivisaCompra(new TipoOperacion("410", 19.38, 0.05));
		sucu.setDivisaVenta(new TipoOperacion("411", 19.68, -0.01));
		when(dao.consultaSucursalesTC(isA(String.class), isA(String.class))).thenReturn(sucu);

		TokenApigee token = new TokenApigee();
		token.setAccess_token("1231231");
		when(tiposCambio.getTokenApiAWSInterno()).thenReturn(token);

		// Llaves null
		when(tiposCambio.getLlavesDivisas(isA(String.class))).thenReturn(null);
		impl.registraTCMM();

		// Llaves sin resultado
		ApiLlaves llavesSinResultado = new ApiLlaves();
		llavesSinResultado.setResultado(null);
		when(tiposCambio.getLlavesDivisas(isA(String.class))).thenReturn(llavesSinResultado);
		impl.registraTCMM();
	}

	@Test
	void testRegistraTCMMException() {
		impl = nuevoImpl();

		// consultaSucursalesTC retorna null -> NPE en envioDivisas capturada por el try/catch
		when(dao.consultaSucursalesTC(isA(String.class), isA(String.class))).thenReturn(null);
		TokenApigee token = new TokenApigee();
		token.setAccess_token("1231231");
		when(tiposCambio.getTokenApiAWSInterno()).thenReturn(token);

		ApiLlaves llaves = new ApiLlaves();
		ResultadoLlaves resultado = new ResultadoLlaves();
		resultado.setAccesoPublico("123");
		resultado.setIdAcceso("123");
		llaves.setResultado(resultado);
		when(tiposCambio.getLlavesDivisas(isA(String.class))).thenReturn(llaves);

		impl.registraTCMM();
	}

	// ==================== Ramas adicionales para 100% ====================

	@Test
	void testArmaTCSucursalParticionCeroLanzaExcepcion() throws InterruptedException {
		impl = nuevoImpl();

		List<String> ld = new ArrayList<>();
		ld.add("0172");

		// particion = 0 -> partition() lanza IllegalArgumentException (capturada en hilo)
		Document param = new Document();
		param.append("activo", true);
		param.append("tipoFlujo", "v1");
		param.append("particion", 0);
		param.append("divisas", Arrays.asList(new Document().append("divisa", "USD").append("compra", "dolarCompra").append("venta", "dolarVenta")));
		when(parametria.consultar(isA(String.class))).thenReturn(param);

		ld.add("9704");
		ld.add("4037");
		ld.add("5784");
		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(obtenerDivisasUSD(ld.toArray(String[]::new)));

		ResponseToken rt = new ResponseToken();
		rt.setCodigo(200);
		Resultado r = new Resultado();
		r.setToken("token1");
		rt.setResultado(r);
		when(service.getToken()).thenReturn(rt);

		impl.armaTCSucursal(ld);
		Thread.sleep(150);
	}

	@Test
	void testObtenerMejortipoCambioSinAppDocs() {
		impl = nuevoImpl();

		// geolocalizarUSD se ejecuta con sucursales que NO incluyen 9704/4037/5784
		// -> compraAppMejor y ventaAppMejor son null (usdAPP queda sin compra/venta)
		// Llamamos directamente obtenerMejortipoCambio con usdAPP vacio para cubrir ramas
		Document sucuCorp = new Document();
		sucuCorp.append("idSucursal", "0172");
		sucuCorp.append("dolarCompra", 18.15);
		sucuCorp.append("dolarVenta", 19.25);
		List<Document> sucursales = new ArrayList<>();
		sucursales.add(sucuCorp);

		Document corpo = new Document();
		corpo.append("dolarCompra", 18.20);
		corpo.append("dolarVenta", 19.20);

		// usdAPP con solo compra (venta null) y luego solo venta
		Document appSoloCompra = new Document();
		appSoloCompra.append("compra", 18.20);
		appSoloCompra.append("venta", 19.20);

		impl.obtenerMejortipoCambio(sucursales, appSoloCompra, corpo);
	}

	@Test
	void testObtenerTipoCambioDivisaNoEncontrada() {
		impl = nuevoImpl();

		// TC con sucursal que coincide en id pero NO en divisa/tipoOperacion -> tipoCambio null
		DivisasBean otraDivisa = new DivisasBean();
		otraDivisa.setDivisa("EUR");
		otraDivisa.setId("0172");
		otraDivisa.setTipoOperacion("410");
		otraDivisa.setTipoCambio(20.5);
		List<DivisasBean> beans = new ArrayList<>();
		beans.add(otraDivisa);
		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(beans);

		BeanDistanciaSucursales bean = new BeanDistanciaSucursales();
		bean.setName("0172");
		List<BeanDistanciaSucursales> ld = new ArrayList<>();
		ld.add(bean);

		// obtenerTipoCambio USD no encontrado -> null -> se filtra en seteaTCSucursal
		List<Document> resultado = impl.seteaTCSucursal(ld);
		assertThat(resultado).isEmpty();
	}

	@Test
	void testGeolocalizarUSDViaArmaTCSucursalSinAppMejor() throws InterruptedException {
		impl = nuevoImpl();

		// Sin TC para 9704/4037/5784 -> dolarCompra/dolarVenta null en esas sucursales
		// -> compraAppMejor/ventaAppMejor con valores null en usdAPP
		List<String> ld = new ArrayList<>();
		ld.add("2244");

		Document param = new Document();
		param.append("activo", true);
		param.append("tipoFlujo", "v1");
		param.append("divisas", Arrays.asList(new Document().append("divisa", "USD").append("compra", "dolarCompra").append("venta", "dolarVenta")));
		when(parametria.consultar(isA(String.class))).thenReturn(param);

		// Solo 2244 tiene TC, las corporativas 9704/4037/5784 no
		List<DivisasBean> beans = obtenerDivisasUSD("2244");
		when(dao.obtenerTiposCambioSucursales(anyList())).thenReturn(beans);

		ResponseToken rt = new ResponseToken();
		rt.setCodigo(200);
		Resultado r = new Resultado();
		r.setToken("token1");
		rt.setResultado(r);
		when(service.getToken()).thenReturn(rt);
		ResponsePromoEspacio rpe = new ResponsePromoEspacio();
		rpe.setCodigo(200);
		when(service.putSucursalesAnterior(any(), anyList())).thenReturn(rpe);

		when(catalogoDao.consultarCatUbicacionSucursales(isA(String.class))).thenReturn(null);

		impl.armaTCSucursal(ld);
		Thread.sleep(200);
	}

	// ==================== Helpers ====================

	private List<DivisasBean> obtenerDivisasUSD(String... sucursales) {
		List<DivisasBean> lista = new ArrayList<>();
		for (String sucursal : sucursales) {
			lista.add(obtenerDivisasUSD(sucursal, true));
			lista.add(obtenerDivisasUSD(sucursal, false));
		}
		return lista;
	}

	private DivisasBean obtenerDivisasUSD(String sucursal, Boolean compra) {
		DivisasBean div = new DivisasBean();
		div.setDivisa("USD");
		div.setId(sucursal);
		div.setSucursal(Integer.parseInt(sucursal));
		if (compra) {
			div.setTipoOperacion("410");
			div.setTipoCambio(16.95);
		} else {
			div.setTipoOperacion("411");
			div.setTipoCambio(19.29);
		}
		return div;
	}
}
