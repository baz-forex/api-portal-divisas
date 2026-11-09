/**
 * 
 */
package com.baz.divisas.api.tef.spots.services.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.SucursalTC;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.spots.dao.SpotsDao;
import com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot;
import com.baz.divisas.api.tef.spots.dto.ReqSpotsTesoreria;
import com.baz.divisas.api.tef.spots.dto.ResSpotTesoreria;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.baz.divisas.api.tef.spots.model.TipoCambioSpotDivisa;
import com.baz.divisas.api.tef.spots.services.ISpotsService;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.TransferenciaArchivo;
import com.mongodb.client.result.InsertOneResult;

/**
 * Clase SpotServiceImpl.
 *
 * @author 1077023
 */
@Service
public class SpotsServiceImpl implements ISpotsService{

	/** The Constant NOEXISTEN. */
	private static final String NOEXISTEN = "No existen los TipoCambioSpot registrados.";
	
	/** The Constant COMPRAEFECTIVO. */
	private static final String COMPRAEFECTIVO = "410";
	
	/** The Constant VENTAEFECTIVO. */
	private static final String VENTAEFECTIVO = "411";

	/** The spot dao. */
	private SpotsDao spotDao;
	
	/** The bitacora. */
	private BitacoraServiceImpl bitacora;
	
	/** The promo espacio impl. */
	private PromoEspacioServiceImpl promoEspacioImpl;
	
	/** The sucursales dao. */
	private SucursalesDao sucursalesDao;
	
	/** The spot service. */
	private SpotService spotService;

	/** The transfer archivo. */
	private TransferenciaArchivo transferArchivo;
	/** The parametria. */
	private ParametriaDao parametria;
	
	/** The Constant ACTIVO. */
	private static final String ACTIVO = "activo";
	
	/** The Constant PARAMETRIAPE. */
	private static final String PARAMETRIAPE = "InformePromoEspacio";
	
	/**
	 * Constructor de la clase.
	 *
	 * @param spotDao - SpotsDao
	 * @param bitacora - BitacoraServiceImpl
	 * @param promoEspacioImpl the promo espacio impl
	 * @param sucursalesDao the sucursales dao
	 * @param spotService the spot service
	 * @param transferArchivo the transfer archivo
	 * @param parametria the parametria
	 */
	@Autowired
	public SpotsServiceImpl(SpotsDao spotDao,
			BitacoraServiceImpl bitacora, PromoEspacioServiceImpl promoEspacioImpl, SucursalesDao sucursalesDao,
			SpotService spotService,TransferenciaArchivo transferArchivo,ParametriaDao parametria) {
		super();
		this.spotDao = spotDao;
		this.bitacora = bitacora;
		this.promoEspacioImpl = promoEspacioImpl;
		this.sucursalesDao = sucursalesDao;
		this.spotService = spotService;
		this.transferArchivo = transferArchivo;
		this.parametria = parametria;
	}


	/**
	 * Consulta spots.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaSpots() {

		ResponseApi resultado;
		List<Spot> spots = spotDao.findSpots();

		if (!spots.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, spots);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(NOEXISTEN));
		}

		return resultado;
	}

	/**
	 * Consulta spot por fecha.
	 *
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @param idsDivisas the ids divisas
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaSpotPorFecha(String fechaInicio, String fechaFin, List<String> idsDivisas) {

		ResponseApi resultado;
		List<Spot> spots = spotDao.findSpotsPorFecha(fechaInicio, fechaFin, idsDivisas);

		if (!spots.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, spots);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(NOEXISTEN));
		}

		return resultado;
	}

	/**
	 * Consulta spots vigentes.
	 *
	 * @param idsDivisas the ids divisas
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaSpotsVigentes(List<String> idsDivisas) {

		ResponseApi resultado;
		List<Spot> spots = new ArrayList<>();

		for (int i = 0; i < idsDivisas.size(); i++) {
			String idDivisa = idsDivisas.get(i);
			Spot spotDivisa = spotDao.findSpotVigenteDivisa(idDivisa);
			spots.add(spotDivisa);
		}

		if (!spots.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, spots);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(NOEXISTEN));
		}

		return resultado;
	}

	/**
	 * Crea spot.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi creaSpots(List<Spot> request) {
		
		spotDao.insertSpots(request);

		if (request.isEmpty()) {

			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible registrar el Spot."));

		}
		
		List<String> sucursalesPromo = Collections.synchronizedList(new ArrayList<>());
		
		//Obtener sucursales con el arreglo divisas
		request.parallelStream().forEach((Spot spot ) -> { 
			List<SucursalTC> sucursales= sucursalesDao.consultaSucursalesTC(spot.getIdDivisa());
			if(null != sucursales && sucursales.size() >0) {
				sucursales.parallelStream().forEach((SucursalTC sucursal ) -> { 

					spotDao.updateTCSpotSucursales(sucursal.getId(),spot.getIdDivisa(), COMPRAEFECTIVO,
							spot.getCompraEfectivo() + sucursal.getDivisaCompra().getTipoCambioSpread());
					spotDao.updateTCSpotSucursales(sucursal.getId(),spot.getIdDivisa(), VENTAEFECTIVO,
							spot.getVentaEfectivo() + sucursal.getDivisaVenta().getTipoCambioSpread());
					sucursalesPromo.add(sucursal.getId());				
				});				
			}
		});
		promoEspacioImpl.armaTCSucursal(sucursalesPromo.stream().distinct().collect(Collectors.toList()));
		
		Document doc = parametria.consultar(PARAMETRIAPE);
		if (doc.isEmpty() || !doc.getBoolean(ACTIVO, false)) {
			LogApp.mensaje("Parametria inactiva - " + PARAMETRIAPE);
			return new ResponseApi(EstatusResponse.OK);
		}
		
		if (null != doc.get("archivosSpot") && doc.getBoolean("archivosSpot", true)) {
			new Thread(() -> {
				InputStream reporte = generaArchivosTCSpotAlnova(request);
				InputStream reporte2 = generaArchivosTCSpotAlnova(request);
				if (reporte != null) {

					String nombreReporteOperaciones = PropApp.getReporteSpotOperacionAlnova()
							+ DateUtils.getDateFormat("yyMMdd") + ".TXT";

					transferArchivo.setArchivoSMB(reporte, PropApp.getReporteriaOperacionAlnova(),
							nombreReporteOperaciones);

					String nombreReporteRegulatorios = PropApp.getReporteSpotRegulatoriosAlnova()
							+ DateUtils.getDateFormat("yyMMdd") + "R1.TXT";

					transferArchivo.setArchivoSMB(reporte2, PropApp.getReporteriaRegulatoriosAlnova(),
							nombreReporteRegulatorios);
				}

			}).start();
		}
		return new ResponseApi(EstatusResponse.OK);

	}
	
	/**
	 * Genera archivos TC spot alnova.
	 *
	 * @param request the request
	 * @return the input stream
	 */
	public InputStream generaArchivosTCSpotAlnova(List<Spot> request) {
				StringBuilder reporte = new StringBuilder();
			
		for (Spot tcSpotDivisa : request) {
			String divisa = "";
			if ("USD".equals(tcSpotDivisa.getIdDivisa())) {
				divisa = "USDB";
			} 
			else if ("CAD".equals(tcSpotDivisa.getIdDivisa())) {
				divisa = "CADB";
			} 
			else if ("EUR".equals(tcSpotDivisa.getIdDivisa())) {
				divisa = "EURB";
			} 
			else if ("GBP".equals(tcSpotDivisa.getIdDivisa())) {
				divisa = "GBPB";
			}

			String[] compra = String.valueOf(tcSpotDivisa.getCompraEfectivo()).split("\\.");
			
			String[] venta = String.valueOf(tcSpotDivisa.getVentaEfectivo()).split("\\.");
			
			String linea = String.join("", divisa,
					String.format("%06d",Integer.valueOf(compra[0])),
					String.format("%-6s",Integer.valueOf(compra[1])).replace(' ', '0'),
					String.format("%06d",Integer.valueOf(venta[0])),
					String.format("%-6s",Integer.valueOf(venta[1])).replace(' ', '0')
					);

			reporte.append(linea + "\r\n");

		}
		
		byte[] bytes = reporte.toString().getBytes(StandardCharsets.UTF_8);

		return new ByteArrayInputStream(bytes);

	}
	
	/**
	 * Crea spot.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi creaSpotOlp(Spot request) {
		
		//Consulta de la parametria
		//tengo que obtener el spot minimo
		//En dado caso de que el spot que se esta recibiendo esta por debajo del minimo, no se actualiza y se tiene que mostrar el mensaje de que el spot a guardar es menor al minimo configurado
		Document doc = parametria.consultar("Bloomberg");
		double spotMinimo = Double.valueOf(doc.get("spotMinimo").toString());
		
		if(spotMinimo > request.getCompraEfectivo()) {
			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("El tipo de cambio spot a guardar es menor al tipo de cambio mínimo configurado"));
		}
		
		InsertOneResult resultInsert = spotDao.insertSpot(request);

		if (null == resultInsert.getInsertedId()) {

			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible registrar el Spot."));

		}
		//Obtener sucursales con el arreglo divisas
		List<SucursalTC> sucursales= sucursalesDao.consultaSucursalesOlp(request.getIdDivisa());
		List<String> sucursalPromo = new ArrayList<>();
		if(null != sucursales && sucursales.size() >0) {
			sucursales.parallelStream().forEach((SucursalTC sucursal ) -> { 
				
				spotDao.updateTCSpotSucursalesOLP(sucursal.getId(), COMPRAEFECTIVO,
						request.getCompraEfectivo() + sucursal.getDivisaCompra().getTipoCambioSpread());
				spotDao.updateTCSpotSucursalesOLP(sucursal.getId(), VENTAEFECTIVO,
						request.getVentaEfectivo() + sucursal.getDivisaVenta().getTipoCambioSpread());
				sucursalPromo.add(sucursal.getId());				
			});
			//promoEspacioImpl.informarSucursalesPromoOLP(sucursalPromo);
			promoEspacioImpl.armaTCSucursal(sucursalPromo);

		}
		String idBitacora = bitacora.registrar(AccionBitacora.REGISTRAR,
				"Registro de Tipo de Cambio Spot olp", null, request);

		spotDao.updateIdBitacoraAlta(request.getId(), idBitacora);

		return new ResponseApi(EstatusResponse.OK);

	}


	/**
	 * Consulta spots tesoreria.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaSpotsTesoreria() {
		ResponseApi resultado;
		ArrayList<String> divisas = new ArrayList<String>();
		divisas.add("USDMXN");
		divisas.add("CADMXN");
		divisas.add("EURMXN");
		divisas.add("GBPMXN");

		ResSpotTesoreria respuesta = consultaTesoreriaSpot(divisas);

		if (null != respuesta && null != respuesta.getResultado()) {
			LogApp.mensaje(respuesta.toString());

			for (TipoCambioSpotDivisa tipoCambio : respuesta.getResultado().getDivisas()) {

				if ("USDMXN".equals(tipoCambio.getDivisa())) {
					tipoCambio.setDivisa("USD");
				} else if ("CADMXN".equals(tipoCambio.getDivisa())) {
					tipoCambio.setDivisa("CAD");
				} else if ("EURMXN".equals(tipoCambio.getDivisa())) {
					tipoCambio.setDivisa("EUR");
				} else if ("GBPMXN".equals(tipoCambio.getDivisa())) {
					tipoCambio.setDivisa("GBP");
				}
			}

			resultado = new ResponseApi(EstatusResponse.OK, respuesta.getResultado());
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("\"Atención!!\r\n" + "Error al consultar los precios con Bloomberg.\""));
		}

	
	return resultado;
	}
	
	/**
	 * Consulta tesoreria spot.
	 *
	 * @param divisas the divisas
	 * @return the res spot tesoreria
	 */
	private ResSpotTesoreria consultaTesoreriaSpot(ArrayList<String> divisas) {

		spotService.setClient(WebClient.builder().baseUrl(PropApp.getUrlApigeeOnPrem()).build());

		TokenApigee token = spotService.getTokenApi();

		if (null != token && null != token.getAccess_token()) {
			ReqSpotsTesoreria req = new ReqSpotsTesoreria();
			req.setClaveNegocio(PropApp.getClaveNegocio());
			req.setDivisas(divisas);
			LogApp.mensaje(PropApp.getUrlTipoCambioTesoreria() + " : Request: " + req.toString());
			return spotService.consultaSpotTesoreria(req, token.getAccess_token());
		}
		else {
			return null;
		}

	}
	/**
	 * Consulta spots vigentes OLP.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaSpotsVigentesOLP() {
		ResponseApi resultado;
		List<Object> spots = new ArrayList<>();

		Spot spotDivisa = spotDao.findSpotVigenteDivisa("OLP");
		spots.add(spotDivisa);
		
		ArrayList<String> divisas = new ArrayList<String>();
		divisas.add("XAG");
		divisas.add("USDMXN");
		ResSpotTesoreria respuesta = consultaTesoreriaSpot(divisas);

		if (null != respuesta && null != respuesta.getResultado()) {
		
			LogApp.mensaje(respuesta.toString());
			Spot spotUsd = new Spot();
			Spot spotXag = new Spot();

			for (TipoCambioSpotDivisa tipoCambio : respuesta.getResultado().getDivisas()) {

				if ("USDMXN".equals(tipoCambio.getDivisa())) {
					spotUsd.setId("USD");
					spotUsd.setCompraEfectivo(Double.parseDouble(tipoCambio.getTipoCambio()));
				} 
				else if ("XAG".equals(tipoCambio.getDivisa())) {
					spotXag.setId("OLP");
					spotXag.setCompraEfectivo(Double.parseDouble(tipoCambio.getTipoCambio()));
				}
			}
			
			Document doc = parametria.consultar("Bloomberg");			
			double acunacion = Double.parseDouble(doc.get("acunacion").toString());
			Document spotBloomberg = calculaPrecioBloomberg(spotUsd,spotXag,doc);
			spots.add(spotBloomberg);
			
			Document configuracion = new Document("acunacion",acunacion);
			spots.add(configuracion);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("\"Atención!!\r\n" + "Error al consultar los precios con Bloomberg.\""));
		}
		
		
		if (!spots.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, spots);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(NOEXISTEN));
		}

		return resultado;
	}
	
	/**
	 * Calcula precio bloomberg.
	 *
	 * @param spotUsd the spot usd
	 * @param spotXag the spot xag
	 * @param acunacion the acunacion
	 * @return the document
	 */
	private Document calculaPrecioBloomberg(Spot spotUsd, Spot spotXag, Document parametria) {
		
		BigDecimal xagBloomberg = BigDecimal.valueOf(spotXag.getCompraEfectivo());
	    BigDecimal acunacion = BigDecimal.valueOf(Double.parseDouble(parametria.get("acunacion").toString()));
	    BigDecimal spotUsdBloomberg = BigDecimal.valueOf(spotUsd.getCompraEfectivo());

	    BigDecimal resultadoXAG = xagBloomberg.add(acunacion).multiply(spotUsdBloomberg);
	        
	    double compraOlp = Double.valueOf(resultadoXAG.intValue());
		double ventaOlp = compraOlp + Double.valueOf(parametria.get("diferenciaCompraVenta").toString());
		Document spot = new Document();
		spot.append("idDivisa","OLP");
		spot.append("tipo","Bloomberg");
		spot.append("compra",compraOlp);
		spot.append("venta",ventaOlp);
		
		return spot;
	}


	/**
	 * Consulta configuracion spot OLP.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaConfiguracionSpotOLP() {
		
		return new ResponseApi(EstatusResponse.OK, parametria.consultar("Bloomberg"));
	}


	/**
	 * Guarda configuracion spot OLP.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi guardaConfiguracionSpotOLP(ReqConfiguracionSpot request) {
		
		if(parametria.updateParametriaConfiguracionSpotOLP(request)){
			return new ResponseApi(EstatusResponse.OK, true);
		}
		else {
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existe la parametria"));
		}
		
	}
}
