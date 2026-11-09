package com.baz.divisas.api.tef.promoespacio.services;

import com.baz.divisas.api.apigee.dto.ResultadoApi;
import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.apigee.model.ApiLlaves;
import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.general.model.SucursalTC;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.promoespacio.dao.PromoEspacioDao;
import com.baz.divisas.api.tef.promoespacio.model.BeanDistanciaSucursales;
import com.baz.divisas.api.tef.promoespacio.model.DivisasBean;
import com.baz.divisas.api.tef.promoespacio.model.ResponsePromoEspacio;
import com.baz.divisas.api.tef.promoespacio.model.ResponseToken;
import com.baz.divisas.api.tef.sucursales.model.TipoCambioDiv;
import com.baz.divisas.api.tef.sucursales.model.TiposCambioDiv;
import com.baz.divisas.api.tef.sucursales.service.TiposCambioService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.RSAUtilsPagosDigitales;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Class PromoEspacioServiceImpl.
 *
 * @author B86960
 */
@Service
public class PromoEspacioServiceImpl {

	/** The Constant ID_SUCURSAL. */
	private static final String ID_SUCURSAL = "idSucursal";
	
	/** The Constant DOLARCOMPRA. */
	private static final String DOLARCOMPRA = "dolarCompra";
	
	/** The Constant DOLARVENTA. */
	private static final String DOLARVENTA = "dolarVenta";
	
	/** The Constant COMPRA. */
	private static final String COMPRA = "compra";
	
	/** The Constant VENTA. */
	private static final String VENTA = "venta";
	
	/** The Constant TIPOOPCOMPRA. */
	private static final String TIPOOPCOMPRA = "410";
	
	/** The Constant TIPOOPVENTA. */
	private static final String TIPOOPVENTA = "411";
	
	/** The Constant ACTIVO. */
	private static final String ACTIVO = "activo";
	
	/** The Constant PARAMETRIAPE. */
	private static final String PARAMETRIAPE = "InformePromoEspacio";
	
	/** The Constant USD. */
	private static final String USD = "USD";
	
	/** The Constant DOS. */
	private static final int DOS = 2;
	
	/** The Constant DOSCIENTOS. */
	private static final int DOSCIENTOS = 200;

	/** The Constant PARTICION_DEFAULT. */
	private static final int PARTICION_DEFAULT = 500;
	
	/** The Constant MONTO_MEJORADO_COMPRA. */
	private static final double MONTO_MEJORADO_COMPRA = 0.05D;
	
	/** The Constant MONTO_MEJORADO_VENTA. */
	private static final double MONTO_MEJORADO_VENTA = -0.01D;

	/** The service. */
	private final PromoEspacioService service;
	
	/** The dao. */
	private final PromoEspacioDao dao;
	
	/** The parametria. */
	private final ParametriaDao parametria;
	
	/** The catalogo dao. */
	private final CatalogosDao catalogoDao;
	
	/** The tipos cambio. */
	private final TiposCambioService tiposCambio;

	/** The lista sucursales. */
	private List<String> listaSucursales;
	
	/** The lista tipos cambios. */
	private List<DivisasBean> listaTiposCambios;

	/** Indice de tipos de cambio: clave = sucursal|divisa|tipoOperacion -> tipoCambio ya formateado. */
	private Map<String, String> indiceTiposCambios;

	private final CryptoService cryptoService;

	/**
	 * Instantiates a new promo espacio service impl.
	 */
	public PromoEspacioServiceImpl() {
		super();
		service = new PromoEspacioService();
		dao = new PromoEspacioDao();
		parametria = new ParametriaDao();
		catalogoDao = new CatalogosDao();
		tiposCambio = new TiposCambioService();
		cryptoService = new CryptoService();
	}

	/**
	 * Instantiates a new promo espacio service impl.
	 *
	 * @param service           the service
	 * @param dao               the dao
	 * @param parametria        the parametria
	 * @param middlewareService the middleware service
	 * @param catalogoDao       the catalogo dao
	 * @param tiposCambio       the tipos cambio
	 */
	public PromoEspacioServiceImpl(
					PromoEspacioService service,
					PromoEspacioDao dao,
					ParametriaDao parametria,
					CatalogosDao catalogoDao,
					TiposCambioService tiposCambio,CryptoService cryptoService) {
		super();
		this.service = service;
		this.dao = dao;
		this.parametria = parametria;
		this.catalogoDao = catalogoDao;
		this.tiposCambio = tiposCambio;
		this.cryptoService = cryptoService;
	}

	/**
	 * Obtener tcs.
	 *
	 * @return the list
	 */
	private List<DivisasBean> obtenerTcs() {
		return dao.obtenerTiposCambioSucursales(listaSucursales);
	}

	/**
	 * Arma TC sucursal.
	 *
	 * @param sucursales the sucursales
	 */
	public void armaTCSucursal(Collection<String> sucursales) {
		Document doc = parametria.consultar(PARAMETRIAPE);
		if (doc.isEmpty() || !doc.getBoolean(ACTIVO, false)) {
			LogApp.mensaje("Parametria inactiva - " + PARAMETRIAPE);
			return;
		}
		listaSucursales = new ArrayList<>(sucursales);
		listaSucursales.add("9704");
		listaSucursales.add("4037");
		listaSucursales.add("5784"); 
		LogApp.mensaje("Sucursales para PromoEspacio: " + listaSucursales);
		listaTiposCambios = obtenerTcs();
		construirIndiceTiposCambios();

		List<Document> divisasActivas = doc.getList("divisas", Document.class);

		List<Document> ld = listaSucursales.parallelStream().map(
						(String x) -> procesarSucursal(x, divisasActivas)).toList();
		int particion = doc.getInteger("particion", PARTICION_DEFAULT);
		new Thread(() -> procesaLista(ld, particion,doc)).start();
	}

	/**
	 * Procesa lista.
	 *
	 * @param ld the ld
	 * @param particion the particion
	 */
	private void procesaLista(List<Document> ld, int particion,Document parametria) {
		geolocalizarUSD(ld);
		informaPromo(ld, particion,parametria);
	}

	/**
	 * Procesar sucursal.
	 *
	 * @param sucursal the sucursal
	 * @param tcPlata the tc plata
	 * @param divisasActivas the divisas activas
	 * @return the document
	 */
	private Document procesarSucursal(String sucursal, List<Document> divisasActivas) {
		Document suc = new Document();
		suc.append(ID_SUCURSAL, sucursal);
		for (Document divisa : divisasActivas) {
			suc.append(divisa.get(COMPRA).toString(),
							obtenerTipoCambio(sucursal, divisa.get("divisa").toString(), true));
			suc.append(divisa.get(VENTA).toString(),
							obtenerTipoCambio(sucursal, divisa.get("divisa").toString(), false));
		}

		return suc;
	}

	/**
	 * Informa promo.
	 *
	 * @param tcSucursales the ld
	 * @param particion    the particion
	 */
	private void informaPromo(List<Document> tcSucursales, int particion,Document parametria) {
		if (!parametria.isEmpty() && parametria.getBoolean(ACTIVO, true)) {

			if (parametria.get("tipoFlujo").equals("v2")) {
				cryptoService.generateRandomKey();

				String password = cryptoService.encryptPassword();
				String requestKey = cryptoService.getCipherRequestKey();
				ResponseToken rt = service.getToken(requestKey, password);
				if (null != rt && null != rt.getData() && null != rt.getData().getAuth()
						&& null != rt.getData().getAuth().getAccess_token()) {
					// Descifrar el token con AES-GCMs
					String token = cryptoService.decryptToken(rt.getData().getAuth().getAccess_token());

					List<List<Document>> tcSucursalesPartidas = partition(tcSucursales, particion);
					for (List<Document> sucursalesPartida : tcSucursalesPartidas) {
						LogApp.mensaje("Datos de : " + sucursalesPartida.size() + " Sucursales para PromoEspacio: "
								+ sucursalesPartida);
						ResponsePromoEspacio response = service.putSucursales(token, sucursalesPartida);
						if (null != response.getRequest_id()) {
							LogApp.mensaje(response.toString());
						} 
						else {
							LogApp.mensaje(
									"Error al impactar las sucursales de promo espacios y del archivo de alnova");
						}
					}
				} 
				else {
					LogApp.mensaje("Detalle al obtener token de PromoEspacio");
					return;
				}
			} 
			else {

				ResponseToken rt = service.getToken();
				if (DOSCIENTOS != rt.getCodigo() || null == rt.getResultado()
						|| validaString(rt.getResultado().getToken())) {
					LogApp.mensaje("Detalle al obtener token de PromoEspacio");
					return;
				}
				List<List<Document>> tcSucursalesPartidas = partition(tcSucursales, particion);
				for (List<Document> sucursalesPartida : tcSucursalesPartidas) {
					LogApp.mensaje("Datos de : " + sucursalesPartida.size() + " Sucursales para PromoEspacio: "
							+ sucursalesPartida);
					ResponsePromoEspacio response = service.putSucursalesAnterior(rt.getResultado().getToken(),
							sucursalesPartida);
					if (DOSCIENTOS == response.getCodigo()) {
						LogApp.mensaje(response.toString());
					} 
					else {
						LogApp.mensaje("Error al impactar las sucursales de promo espacios y del archivo de alnova");
					}
				}

			}
		}
	}

	/**
	 * Valida string.
	 *
	 * @param token the token
	 * @return true, if successful
	 */
	private static boolean validaString(String token) {
		return (null == token || token.isEmpty());
	}

	/**
	 * Partition.
	 *
	 * @param <T> the generic type
	 * @param list the list
	 * @param size the size
	 * @return the list
	 */
	private static <T> List<List<T>> partition(List<T> list, int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("El tamaño debe ser mayor que 0");
		}
		List<List<T>> result = new ArrayList<>();
		for (int i = 0; i < list.size(); i += size) {
			int end = Math.min(i + size, list.size());
			result.add(list.subList(i, end));
		}
		return result;
	}

	/**
	 * Geolocalizar USD.
	 *
	 * @param sucursales the sucursales
	 */
	private void geolocalizarUSD(List<Document> sucursales) {

		Document compraAppMejor = sucursales.stream()
						.filter(element -> "9704".equals(element.get(ID_SUCURSAL))).findFirst().orElse(null);
		Document ventaAppMejor = sucursales.stream()
						.filter(element -> "4037".equals(element.get(ID_SUCURSAL))).findFirst().orElse(null);
		Document tcCorporativa = sucursales.stream()
						.filter(element -> "5784".equals(element.get(ID_SUCURSAL))).findFirst().orElse(null);

		Document usdAPP = new Document();
		if (null != compraAppMejor) {
			usdAPP.append(COMPRA, compraAppMejor.get(DOLARCOMPRA));
		}
		if (null != ventaAppMejor) {
			usdAPP.append(VENTA, ventaAppMejor.get(DOLARVENTA));
		}

		// Recorrido de las sucursales				
		obtenerMejortipoCambio(sucursales, usdAPP, tcCorporativa);
	}

	/**
	 * Obtener mejortipo cambio.
	 *
	 * @param sucursales    the sucursales
	 * @param usdAPP        the usd APP
	 * @param tcCorporativa the tc corporativa
	 */
	public void obtenerMejortipoCambio(List<Document> sucursales, Document usdAPP, Document tcCorporativa) {
		// Usamos Set para búsqueda O(1) en lugar de List.contains que es O(n)
		Set<String> corporativasIds = Set.of("0172", "0410", "2354", "2615", "2870", "9217", "5340", "9209");

		for (Document sucursal : sucursales) {
			String idSucursal = sucursal.get(ID_SUCURSAL).toString();

			// CASO 1: Sucursal Corporativa
			if (corporativasIds.contains(idSucursal)) {
				aplicarTCCorporativo(sucursal, tcCorporativa, usdAPP);
				continue;
			}

			// CASO 2: Sucursal Normal - Consultar ubicación
			BeanDistanciaSucursales ubicacion = catalogoDao.consultarCatUbicacionSucursales(idSucursal);

			if (ubicacion != null && ubicacion.getLocation() != null) {
				List<Double> coords = ubicacion.getLocation().getCoordinates();
				List<BeanDistanciaSucursales> cercanas = catalogoDao.consultarSucursalesCercanas(coords.get(0), coords.get(1));

				if (!cercanas.isEmpty()) {
					sucursalesCercanas(sucursal, cercanas, sucursales, usdAPP);
				}
				else {
					aplicarTCMejorado(sucursal, usdAPP);
				}
			}
			else {
				// Caso 3: Sin ubicación o no encontrada
				aplicarTCMejorado(sucursal, usdAPP);
			}
		}
	}

	/**
	 * Aplicar TC corporativo.
	 *
	 * @param sucursal the sucursal
	 * @param tcCorp the tc corp
	 * @param usdAPP the usd APP
	 */
	private static void aplicarTCCorporativo(Document sucursal, Document tcCorp, Document usdAPP) {
		double compra = Double.parseDouble(tcCorp.get(DOLARCOMPRA).toString());
		double venta = Double.parseDouble(tcCorp.get(DOLARVENTA).toString());
		mejorTC(sucursal, compra, venta, usdAPP);
	}

	/**
	 * Aplicar TC mejorado.
	 *
	 * @param sucursal the sucursal
	 * @param usdAPP the usd APP
	 */
	private static void aplicarTCMejorado(Document sucursal, Document usdAPP) {
		double compraBase = Double.parseDouble(sucursal.get(DOLARCOMPRA).toString());
		double ventaBase = Double.parseDouble(sucursal.get(DOLARVENTA).toString());

		double mejorCompra = Double.parseDouble(dosDecimales(compraBase + MONTO_MEJORADO_COMPRA));
		double mejorVenta = Double.parseDouble(dosDecimales(ventaBase + MONTO_MEJORADO_VENTA));

		mejorTC(sucursal, mejorCompra, mejorVenta, usdAPP);
	}

	/**
	 * Construye un indice O(1) de los tipos de cambio.
	 *
	 * Clave: sucursal|divisa|tipoOperacion  -> valor: tipo de cambio formateado a 2 decimales.
	 * Esto evita recorrer toda la lista en cada llamada a obtenerTipoCambio (de O(n) a O(1)).
	 */
	private void construirIndiceTiposCambios() {
		Map<String, String> indice = new HashMap<>();
		if (listaTiposCambios != null) {
			for (DivisasBean bean : listaTiposCambios) {
				if (bean.getId() == null || bean.getDivisa() == null
						|| bean.getTipoOperacion() == null || bean.getTipoCambio() == null) {
					continue;
				}
				String clave = claveTipoCambio(bean.getId(), bean.getDivisa(), bean.getTipoOperacion());
				// putIfAbsent respeta el "break" del algoritmo original (primera coincidencia gana)
				indice.putIfAbsent(clave, dosDecimales(Double.parseDouble(bean.getTipoCambio().toString())));
			}
		}
		this.indiceTiposCambios = indice;
	}

	/**
	 * Genera la clave compuesta del indice de tipos de cambio.
	 *
	 * @param sucursal the sucursal
	 * @param divisa the divisa
	 * @param tipoOperacion the tipo operacion
	 * @return the string
	 */
	private static String claveTipoCambio(String sucursal, String divisa, String tipoOperacion) {
		return sucursal + '|' + divisa + '|' + tipoOperacion;
	}

	/**
	 * Obtener tipo cambio.
	 *
	 * @param sucursal the sucursal
	 * @param divisa the divisa
	 * @param compra the compra
	 * @return the string
	 */
	private String obtenerTipoCambio(String sucursal, String divisa, boolean compra) {
		String tipoOperacion = compra ? TIPOOPCOMPRA : TIPOOPVENTA;
		// Lookup O(1) sobre el indice previamente construido
		return indiceTiposCambios.get(claveTipoCambio(sucursal, divisa, tipoOperacion));
	}


	/**
	 * Sucursales cercanas.
	 *
	 * @param sucursal           the sucursal
	 * @param sucursalesCercanas the sucursales cercanas
	 * @param sucursales         the sucursales archivo
	 * @param usdAPP             the usd APP
	 */
	public void sucursalesCercanas(Document sucursal,
	                               List<BeanDistanciaSucursales> sucursalesCercanas,
	                               Collection<Document> sucursales,
	                               Document usdAPP) {

		List<String> compra = new ArrayList<>();
		List<String> venta = new ArrayList<>();
		List<Document> sucursalesCerca = seteaTCSucursal(sucursalesCercanas);
		Map<String, Document> sucursalesIndexadas = Stream.concat(sucursales.stream(), sucursalesCerca.stream())
						.collect(Collectors.toMap(
										doc -> doc.get(ID_SUCURSAL).toString(),
										doc -> doc,
										(existente, nuevo) -> existente
						));

		for (BeanDistanciaSucursales cercana : sucursalesCercanas) {
			Document doc = sucursalesIndexadas.get(cercana.getName());
			if (doc != null) {
				compra.add(String.valueOf(doc.get(DOLARCOMPRA)));
				venta.add(String.valueOf(doc.get(DOLARVENTA)));
			}
		}

		compra.sort(Collections.reverseOrder());
		Collections.sort(venta);

		double mejorCompraSucursales = Double.parseDouble(compra.get(0));
		double mejorVentaSucursales = Double.parseDouble(venta.get(0));

		mejorCompraSucursales = Double.parseDouble(dosDecimales(mejorCompraSucursales + MONTO_MEJORADO_COMPRA));
		mejorVentaSucursales = Double.parseDouble(dosDecimales(mejorVentaSucursales + MONTO_MEJORADO_VENTA));

		mejorTC(sucursal, mejorCompraSucursales, mejorVentaSucursales, usdAPP);

	}

	/**
	 * Mejor TC.
	 *
	 * @param sucursal       the sucursal
	 * @param mejorCompraSuc the mejor compra suc
	 * @param mejorVentaSuc  the mejor venta suc
	 * @param usdAPP         the usd APP
	 */
	private static void mejorTC(Document sucursal, Double mejorCompraSuc, Double mejorVentaSuc, Document usdAPP) {
		// 1. Obtener valores de la App de forma segura
		double compraApp = Double.parseDouble(usdAPP.get(COMPRA).toString());
		double ventaApp = Double.parseDouble(usdAPP.get(VENTA).toString());

		int comparacionCompra = Double.compare(mejorCompraSuc, compraApp);
		int comparacionVenta = Double.compare(ventaApp, mejorVentaSuc);

		String mejorCompra = "";
		String mejorVenta = "";

		if (comparacionCompra == 0) {
			mejorCompra = String.valueOf(mejorCompraSuc);
		} 
		else if (comparacionCompra < 0) {
			mejorCompra = String.valueOf(compraApp);
		} 
		else {
			mejorCompra = String.valueOf(mejorCompraSuc);
		}

		if (comparacionVenta == 0) {
			mejorVenta = String.valueOf(mejorVentaSuc);
		} 
		else if (comparacionVenta < 0) {
			mejorVenta = String.valueOf(ventaApp);
		} 
		else {
			mejorVenta = String.valueOf(mejorVentaSuc);
		}
		
		sucursal.put("dolarCompraApp", formateaDoble(mejorCompra));
		sucursal.put("dolarVentaApp", formateaDoble(mejorVenta));
	}

	/**
	 * Dos decimales.
	 *
	 * @param monto the monto
	 * @return the string
	 */
	private static String dosDecimales(double monto) {
		DecimalFormat formatoDosDecimales = new DecimalFormat("0");
		formatoDosDecimales.setMinimumFractionDigits(DOS);
		formatoDosDecimales.setMaximumFractionDigits(DOS);
		return formatoDosDecimales.format(monto);
	}

	/**
	 * Formatea doble.
	 *
	 * @param monto the monto
	 * @return the string
	 */
	public static String formateaDoble(String monto) {

		DecimalFormat formatoDosDecimales = new DecimalFormat("0");
		formatoDosDecimales.setMinimumFractionDigits(DOS);
		formatoDosDecimales.setMaximumFractionDigits(DOS);
		Double tipoCambio = Double.valueOf(monto);			
		return formatoDosDecimales.format(tipoCambio);
	}

	/**
	 * Arma TC sucursal.
	 *
	 * @param sucursalesCercanas the sucursales
	 * @return the list
	 */
	public List<Document> seteaTCSucursal(Collection<BeanDistanciaSucursales> sucursalesCercanas) {
		listaSucursales = sucursalesCercanas.stream().map(BeanDistanciaSucursales::getName).collect(Collectors.toList());
		listaTiposCambios = obtenerTcs();
		construirIndiceTiposCambios();
		List<Document> ld = new ArrayList<>();
		for (String x : listaSucursales) {
			Document suc = new Document();
			suc.append(ID_SUCURSAL, x);
			suc.append(DOLARCOMPRA, obtenerTipoCambio(x, USD, true));
			suc.append(DOLARVENTA, obtenerTipoCambio(x, USD, false));
			if (null != suc.get(DOLARCOMPRA) && null != suc.get(DOLARVENTA)) {
				ld.add(suc);
			}
		}
		return ld;

	}

	/**
	 * Registra TCMM.
	 */
	public void registraTCMM() {
		SucursalTC sucursalMM = dao.consultaSucursalesTC("1827", "USD");
		try {
			envioDivisas(sucursalMM);
		}
		catch (Exception ex) {
			LogApp.excepcion(ex, "Detalle al asignar Tipo Cambio Nube Divisas, Sucursal: " + sucursalMM);
		}
	}

	/**
	 * Envio divisas.
	 *
	 * @param sucursalMM the sucursal MM
	 */
	private void envioDivisas(SucursalTC sucursalMM) {
		TokenApigee tokenApigeeDivisas = tiposCambio.getTokenApiAWSInterno();

		if (null == tokenApigeeDivisas || !StringUtils.hasText(tokenApigeeDivisas.getAccess_token())) {
			new ResponseApi(EstatusResponse.NO_AUTORIZADO, List.of("Sin Token de Apigee"));
			return;
		}
		LogApp.mensaje(tokenApigeeDivisas.toString());
		ApiLlaves llaves = tiposCambio.getLlavesDivisas(tokenApigeeDivisas.getAccess_token());
		if (null == llaves || null == llaves.getResultado()) {
			new ResponseApi(EstatusResponse.NO_AUTORIZADO, List.of("Sin Llaves de Divisas"));
			return;
		}

		TipoCambioDiv tcUSDCompra = new TipoCambioDiv();
		tcUSDCompra.setId("FIX");
		tcUSDCompra.setIdTipoOperacion("410");
		tcUSDCompra.setCodigoDivisa("USD");
		tcUSDCompra.setCodigoOrigen("1827");

		tcUSDCompra.setMontoDivisa(RSAUtilsPagosDigitales.EncryptOAE256(
						String.valueOf(sucursalMM.getDivisaCompra().getTipoCambioSpot()),
						llaves.getResultado().getAccesoPublico()));

		TipoCambioDiv tcUSDVenta = new TipoCambioDiv();
		tcUSDVenta.setId("FIX");
		tcUSDVenta.setIdTipoOperacion("411");
		tcUSDVenta.setCodigoDivisa("USD");
		tcUSDVenta.setCodigoOrigen("1827");
		tcUSDVenta.setMontoDivisa(RSAUtilsPagosDigitales.EncryptOAE256(
						String.valueOf(sucursalMM.getDivisaVenta().getTipoCambioSpot()),
						llaves.getResultado().getAccesoPublico()));

		List<TipoCambioDiv> listaTC = new ArrayList<>();
		listaTC.add(tcUSDCompra);
		listaTC.add(tcUSDVenta);

		TiposCambioDiv tipos = new TiposCambioDiv();
		tipos.setTiposCambios(listaTC);
		LogApp.mensaje("peticion : " + tipos);

		ResultadoApi resultado = tiposCambio.registraTiposCambioDivisas(tipos,
						tokenApigeeDivisas.getAccess_token(), llaves.getResultado().getIdAcceso());
		LogApp.mensaje(resultado.toString());
	}

}
