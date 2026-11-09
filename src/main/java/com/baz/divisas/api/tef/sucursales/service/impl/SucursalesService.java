package com.baz.divisas.api.tef.sucursales.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.general.model.CatalogoDivisas;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.general.model.TC;
import com.baz.divisas.api.tef.limites.dao.LimitesPorSucursalDao;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.spots.dao.SpotsDao;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.dto.ReqActSucursal;
import com.baz.divisas.api.tef.sucursales.model.Divisa;
import com.baz.divisas.api.tef.sucursales.model.Divisas;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.api.tef.sucursales.model.SucursalDivAux;
import com.baz.divisas.api.tef.sucursales.model.SucursalReporte;
import com.baz.divisas.api.tef.sucursales.service.ISucursales;
import com.baz.divisas.api.tef.sucursales.utils.ArchivosExcel;
import com.baz.divisas.api.tef.sucursales.utils.ReporteTCSucursales;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;


/**
 * The Class SucursalesService.
 */
@Service
public class SucursalesService implements ISucursales {

	/** Tolerancia para comparar valores de punto flotante contra cero. */
	private static final double EPSILON = 1e-9;
	/** The catalogos dao. */
	private CatalogosDao catalogosDao;
	
	/** The sucursales dao. */
	private SucursalesDao sucursalesDao;
	
	/** The spot dao. */
	private SpotsDao spotDao;

	/** The promo espacio impl. */
	private PromoEspacioServiceImpl promoEspacioImpl;
	/** The limites por sucursal dao. */
	private LimitesPorSucursalDao limitesPorSucursalDao;
	
	/** The Constant COMPRAEFECTIVO. */
	private static final String COMPRAEFECTIVO = "410";
	
	/** The Constant VENTAEFECTIVO. */
	private static final String VENTAEFECTIVO = "411";
	
	/** The bitacora. */
	private BitacoraServiceImpl bitacora;
	
	/**
	 * Constructor de la clase.
	 *
	 * @param catalogosDao - CatalogosDao
	 * @param sucursalesDao - SucursalesDao
	 * @param spotDao the spot dao
	 * @param promoEspacioImpl the promo espacio impl
	 * @param limitesPorSucursalDao the limites por sucursal dao
	 */
	@Autowired
	public SucursalesService(CatalogosDao catalogosDao,	SucursalesDao sucursalesDao,SpotsDao spotDao,
			PromoEspacioServiceImpl promoEspacioImpl,LimitesPorSucursalDao limitesPorSucursalDao) {
		super();
		this.catalogosDao = catalogosDao;
		this.sucursalesDao = sucursalesDao;
		this.spotDao = spotDao;
		this.promoEspacioImpl = promoEspacioImpl;
		this.limitesPorSucursalDao = limitesPorSucursalDao;
	}

	/**
	 * Gets the sucursales impl.
	 *
	 * @param asignadas the asignadas
	 * @return the sucursales impl
	 */
	@Override
	public ResponseApi getSucursalesImpl(boolean asignadas) {

		List<Sucursal> sucursales = sucursalesDao.findAsignadas(asignadas);
		List<CatalogoDivisas> divisas = catalogosDao.findCatDivisasLimites();
		
		for (Sucursal sucursal : sucursales) {

			// llena limites default
			sucursal.setLimites(validaArrayLimites(sucursal.getLimites(), divisas));
			
			recorreLimites(sucursal, divisas);
		}

		if (!sucursales.isEmpty()) {
			return new ResponseApi(EstatusResponse.OK, sucursales);
		} 
		else {
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("No existen sucursales."));
		}
	}

	/**
	 * Valida array limites.
	 *
	 * @param limiteList the limite list
	 * @param divisas the divisas
	 * @return the list
	 */
	private static List<Limites> validaArrayLimites(List<Limites> limiteList, List<CatalogoDivisas> divisas) {
		
		String[] tipoOperacion = { COMPRAEFECTIVO, VENTAEFECTIVO, "412", "413" };

		for (Catalogo divisa : divisas) {
			
			for (String tipoOper : tipoOperacion) {
				
				Limites limite = new Limites(tipoOper, divisa.getId(), new MontosLimite(), new MontosLimite(), false);

				if (limiteList == null) {
					limiteList = new ArrayList<>();
					limiteList.add(limite);
				} 
				else {
					
					Optional<Limites> existe = limiteList.stream()
							.filter(x -> (x.getCodigoDivisa().equals(divisa.getId())
									&& x.getIdTipoOperacion().equals(tipoOper)))
							.findFirst();
					
					if (!existe.isPresent()) {

						limiteList.add(limite);

					}

				}

			}
		}
		return limiteList;
	}
	
	/**
	 * Recorre limites.
	 *
	 * @param sucursal the sucursal
	 * @param divisas the divisas
	 */
	private static void recorreLimites(Sucursal sucursal, 
			List<CatalogoDivisas> divisas) {
		
		for (Limites limite : sucursal.getLimites()) {

			if (contieneCeroLimites(limite)) {

				// BUSCA EN DIVISA
				buscaEnDivisas(divisas, limite);
			}
		}
	}


	/**
	 * Busca en divisas.
	 *
	 * @param divisas the divisas
	 * @param limite the limite
	 */
	private static void buscaEnDivisas(List<CatalogoDivisas> divisas, Limites limite) {
		
		divisas.stream()
			   .filter(divisa -> (divisa.getId().equals(limite.getCodigoDivisa()) && divisa.isActivo()))
			   .forEach(divisa -> {			
				   		divisa.getLimites().stream()
						     			   .filter(limitesDivisa -> 
						     			    (limitesDivisa.getIdTipoOperacion().equals(limite.getIdTipoOperacion())) && limitesDivisa.isActivo())
							    		   .forEach(limitesDivisa -> setLimites(limitesDivisa, limite));
				});
	}
	
	/**
	 * Contiene cero limites.
	 *
	 * @param limite the limite
	 * @return true, if successful
	 */
	private static boolean contieneCeroLimites(Limites limite) {
		return (limite.getDiario().getCliente() <= 0 || limite.getDiario().getUsuario() <= 0
				|| limite.getMensual().getCliente() <= 0 || limite.getMensual().getUsuario() <= 0);		
	}
	
	/**
	 * Sets the limites.
	 *
	 * @param limites the limites
	 * @param limitesSucursal the limites sucursal
	 */
	private static void setLimites(Limites limites, Limites limitesSucursal) {
		if (limites.getDiario().getCliente() > 0 && limitesSucursal.getDiario().getCliente() <= 0) {
			limitesSucursal.getDiario().setCliente(limites.getDiario().getCliente());
		}
		if (limites.getDiario().getUsuario() > 0 && limitesSucursal.getDiario().getUsuario() <= 0) {
			limitesSucursal.getDiario().setUsuario(limites.getDiario().getUsuario());
		}
		if (limites.getMensual().getCliente() > 0 && limitesSucursal.getMensual().getCliente() <= 0) {
			limitesSucursal.getMensual().setCliente(limites.getMensual().getCliente());
		}
		if (limites.getMensual().getUsuario() > 0 && limitesSucursal.getMensual().getUsuario() <= 0) {
			limitesSucursal.getMensual().setUsuario(limites.getMensual().getUsuario());
		}
	}
	
	/**
	 * Consulta TC sucursales.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaTCSucursales() {
		ResponseApi resultado = null;
		List<Sucursal> tcSucursales = sucursalesDao.findTCSucursales();
		
		if (null != tcSucursales) {
			List<SucursalDivAux> sucursales = new ArrayList<>();

			for (Sucursal sucursal : tcSucursales) {
				SucursalDivAux sucu = new SucursalDivAux();
				sucu.setIdSucursal(sucursal.getId());
				sucu.setFechaModificacion(sucursal.getFechaModificacion());
				
				List<Divisa> listDivAux = new ArrayList<>();
				for (Divisas divisa : sucursal.getDivisas()) {
					Divisa divisaAux = new Divisa();
					divisaAux.setIdDivisa(divisa.getId());
					TC tipoCambio = new TC();
					TC spread = new TC();
					for (com.baz.divisas.api.tef.sucursales.model.TipoOperacion tipoOp : divisa.getTiposOperacion()) {
						if (COMPRAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
							tipoCambio.setCompra(tipoOp.getTipoCambioSpot());
							spread.setCompra(tipoOp.getTipoCambioSpread());
						}

						if (VENTAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
							tipoCambio.setVenta(tipoOp.getTipoCambioSpot());
							spread.setVenta(tipoOp.getTipoCambioSpread());
						}
					}
					divisaAux.setSpread(spread);
					divisaAux.setTc(tipoCambio);
					listDivAux.add(divisaAux);
				}
				sucu.setDivisas(listDivAux);
				sucursales.add(sucu);
			}

			resultado = new ResponseApi(EstatusResponse.OK, sucursales);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No se encontraron TC de Sucursales."));
		}

		return resultado;
	}

	/**
	 * Consulta TC sucursales.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaTCSucursalesOlp() {
		ResponseApi resultadoOLP = null;
		List<Sucursal> tcSucursalesOLP = sucursalesDao.findTCSucursalesOlp();

		if (null != tcSucursalesOLP) {
			List<SucursalDivAux> sucursalesOLP = new ArrayList<>();

			for (Sucursal sucursal : tcSucursalesOLP) {
				SucursalDivAux sucuOLP = new SucursalDivAux();
				sucuOLP.setIdSucursal(sucursal.getId());
				sucuOLP.setFechaModificacion(sucursal.getFechaModificacion());
				sucuOLP.setFechaUltimoCambioOLP(sucursal.getFechaUltimoCambioOLP());
				
				List<Divisa> listDivAuxOLP = new ArrayList<>();
				Divisa divisaAuxOLP = new Divisa();
				divisaAuxOLP.setIdDivisa(sucursal.getTcOlp().getId());
				TC tipoCambioOLP = new TC();
				TC spreadOLP = new TC();
				for (com.baz.divisas.api.tef.sucursales.model.TipoOperacion tipoOpOLP : sucursal.getTcOlp().getTiposOperacion()) {
					if (COMPRAEFECTIVO.equals(tipoOpOLP.getIdTipoOperacion())) {
						tipoCambioOLP.setCompra(tipoOpOLP.getTipoCambioSpot());
						spreadOLP.setCompra(tipoOpOLP.getTipoCambioSpread());
					}

					if (VENTAEFECTIVO.equals(tipoOpOLP.getIdTipoOperacion())) {
						tipoCambioOLP.setVenta(tipoOpOLP.getTipoCambioSpot());
						spreadOLP.setVenta(tipoOpOLP.getTipoCambioSpread());
					}
				}
				divisaAuxOLP.setSpread(spreadOLP);
				divisaAuxOLP.setTc(tipoCambioOLP);
				listDivAuxOLP.add(divisaAuxOLP);

				sucuOLP.setDivisas(listDivAuxOLP);
				sucursalesOLP.add(sucuOLP);
			}

			resultadoOLP = new ResponseApi(EstatusResponse.OK, sucursalesOLP);
		} 
		else {
			resultadoOLP = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No se encontraron TC de Sucursales."));
		}

		return resultadoOLP;
	}

	
	/**
	 * Actualiza TC sucursal.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaTCSucursal(@Valid ReqActSucursal request) {
		
		for(Divisa divisa : request.getDivisas()) {
			Spot spotDivisa = spotDao.findSpotVigenteDivisa(divisa.getIdDivisa());
			
			Double compra = spotDivisa.getCompraEfectivo();
			Double venta = spotDivisa.getVentaEfectivo();			
			
			if(Math.abs(divisa.getSpread().getCompra()) > EPSILON) {		
				compra += divisa.getSpread().getCompra();
			}
			
			if(Math.abs(divisa.getSpread().getVenta()) > EPSILON) {
				venta += divisa.getSpread().getVenta();
			}
			
			if(compra > venta) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,Arrays.asList("El TC de compra es mayor al TC de la venta de la divisa "));
			}
			else{
				if(Math.abs(divisa.getSpread().getCompra()) > EPSILON) {		
					sucursalesDao.updateSucursalTCSpreadSpot(request.getIdsucursal(), divisa.getIdDivisa(), COMPRAEFECTIVO,
					spotDivisa.getCompraEfectivo() + divisa.getSpread().getCompra(),
					divisa.getSpread().getCompra());
				}
				
				if(Math.abs(divisa.getSpread().getVenta()) > EPSILON) {
					sucursalesDao.updateSucursalTCSpreadSpot(request.getIdsucursal(), divisa.getIdDivisa(), VENTAEFECTIVO,
							venta, divisa.getSpread().getVenta());
				}
				
				if(PropApp.getSucursalMM().equals(request.getIdsucursal()) && "USD".equals(divisa.getIdDivisa())) {
					
					promoEspacioImpl.registraTCMM();
				}
			}
			
		}
		List<String> sucursalPromo = new ArrayList<>();
		if(!PropApp.getSucursalMM().equals(request.getIdsucursal())){
			sucursalPromo.add(request.getIdsucursal());
			promoEspacioImpl.armaTCSucursal(sucursalPromo);
		}
		
		return new ResponseApi(EstatusResponse.OK);
	}

	/**
	 * Carga archivo TC.
	 *
	 * @param archivo the archivo
	 * @return the response api
	 */
	@Override
	public ResponseApi cargaArchivoTC(MultipartFile archivo) {

		ArchivosExcel lectorExcel = new ArchivosExcel(sucursalesDao,promoEspacioImpl,limitesPorSucursalDao,bitacora);
		return lectorExcel.readDataSucursales(archivo);

	}
	
	/**
	 * Carga archivo TC.
	 *
	 * @param archivo the archivo
	 * @return the response api
	 */
	@Override
	public ResponseApi cargaArchivoTCOlp(MultipartFile archivo) {
		ArchivosExcel lectorExcel = new ArchivosExcel(sucursalesDao,promoEspacioImpl,limitesPorSucursalDao,bitacora);
		return lectorExcel.readDataSucursalesOlp(archivo);
	}

	/**
	 * Descarga TC sucursales.
	 *
	 * @return the byte array input stream
	 */
	@Override
	public ByteArrayInputStream descargaTCSucursales() {
		Iterable<Sucursal> tcSucursales = sucursalesDao.consulta();
		
		if (null != tcSucursales) {
			List<SucursalReporte> sucursales = new ArrayList<>();

			for (Sucursal sucursal : tcSucursales) {
				SucursalReporte sucu = new SucursalReporte();
				sucu.setId(sucursal.getId());
				sucu.setFechaUltimoCambio(sucursal.getFechaUltimoCambio());
				sucu.setCalleSucursalArchivo(sucursal.getCalleSucursalArchivo());
				sucu.setCpSucursalArchivo(sucursal.getCpSucursalArchivo());
				sucu.setEstadoSucursalArchivo(sucursal.getEstadoSucursalArchivo());
				sucu.setMunicipioSucursalArchivo(sucursal.getMunicipioSucursalArchivo());
				sucu.setNombreSucursalArchivo(sucursal.getNombreSucursalArchivo());
				sucu.setNumeroRegionArchivo(sucursal.getNumeroRegionArchivo());
				sucu.setNumeroSucursalArchivo(sucursal.getNumeroSucursalArchivo());
				sucu.setRegionSucursalArchivo(sucursal.getRegionSucursalArchivo());
				sucu.setTipoSucursalArchivo(sucursal.getTipoSucursalArchivo());

				for (Divisas divisa : sucursal.getDivisas()) {
					Divisa divisaAux = new Divisa();
					divisaAux.setIdDivisa(divisa.getId());
					if("USD".equals(divisaAux.getIdDivisa())) {
						divisaUSD(sucu,divisa);
					} 
					else if("CAD".equals(divisaAux.getIdDivisa())) {
						divisaCAD(sucu,divisa);
					}
					else if("EUR".equals(divisaAux.getIdDivisa())) {
						divisaEUR(sucu,divisa);
					}
					else if("GBP".equals(divisaAux.getIdDivisa())) {
						divisaGBP(sucu,divisa);
					}
				}
				sucursales.add(sucu);
			}

			return new ReporteTCSucursales().generaExcel(sucursales);
		} 
		return null;		
	}
	
	
	
	/**
	 * Descarga TC sucursales.
	 *
	 * @return the byte array input stream
	 */
	@Override
	public ByteArrayInputStream descargaTCSucursalesOlp() {
		Iterable<Sucursal> tcSucursales = sucursalesDao.consultaOlp();

		if (null != tcSucursales) {
			List<SucursalReporte> sucursales = new ArrayList<>();

			for (Sucursal sucursal : tcSucursales) {
				SucursalReporte sucu = new SucursalReporte();
				sucu.setId(sucursal.getId());
				sucu.setDivisaOlp(sucursal.getTcOlp().getId());

				for (com.baz.divisas.api.tef.sucursales.model.TipoOperacion tipoOp : sucursal.getTcOlp().getTiposOperacion()) {
					if (COMPRAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
						sucu.setEstrategiaCompraOlp(tipoOp.getTipoCambioSpread());
						sucu.setTcSucursalCompraOlp(tipoOp.getTipoCambioSpot());
					}

					if (VENTAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
						sucu.setEstrategiaVentaOlp(tipoOp.getTipoCambioSpread());
						sucu.setTcSucursalVentaOlp(tipoOp.getTipoCambioSpot());
					}
				}
				sucursales.add(sucu);
			}

			return new ReporteTCSucursales().generaExcelOLP(sucursales);
		} 
		return null;		
	}
	
	/**
	 * Divisa USD.
	 *
	 * @param sucu the sucu
	 * @param div the div
	 */
	private void divisaUSD(SucursalReporte sucu , Divisas div) {
	
		
		sucu.setDivisaUsd(div.getId());
		
		for (com.baz.divisas.api.tef.sucursales.model.TipoOperacion tipoOp : div.getTiposOperacion()) {
			if (COMPRAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
				sucu.setEstrategiaCompraUsd(tipoOp.getTipoCambioSpread());
				sucu.setTcSucursalCompraUsd(tipoOp.getTipoCambioSpot());
			}

			if (VENTAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
				sucu.setEstrategiaVentaUsd(tipoOp.getTipoCambioSpread());
				sucu.setTcSucursalVentaUsd(tipoOp.getTipoCambioSpot());
			}
		}
		
	}
	
	
	/**
	 * Divisa CAD.
	 *
	 * @param sucu the sucu
	 * @param div the div
	 */
	private void divisaCAD(SucursalReporte sucu, Divisas div) {

		sucu.setDivisaCad(div.getId());

		for (com.baz.divisas.api.tef.sucursales.model.TipoOperacion tipoOp : div.getTiposOperacion()) {
			if (COMPRAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {

				sucu.setEstrategiaCompraCad(tipoOp.getTipoCambioSpread());
				sucu.setTcSucursalCompraCad(tipoOp.getTipoCambioSpot());
			}

			if (VENTAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
				sucu.setEstrategiaVentaCad(tipoOp.getTipoCambioSpread());
				sucu.setTcSucursalVentaCad(tipoOp.getTipoCambioSpot());
			}
		}

	}


	/**
	 * Divisa EUR.
	 *
	 * @param sucu the sucu
	 * @param div the div
	 */
	private void divisaEUR(SucursalReporte sucu, Divisas div) {

		sucu.setDivisaEur(div.getId());

		for (com.baz.divisas.api.tef.sucursales.model.TipoOperacion tipoOp : div.getTiposOperacion()) {
			if (COMPRAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {

				sucu.setEstrategiaCompraEur(tipoOp.getTipoCambioSpread());
				sucu.setTcSucursalCompraEur(tipoOp.getTipoCambioSpot());
			}

			if (VENTAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
				sucu.setEstrategiaVentaEur(tipoOp.getTipoCambioSpread());
				sucu.setTcSucursalVentaEur(tipoOp.getTipoCambioSpot());
			}
		}
	
	}
	
	/**
	 * Divisa GBP.
	 *
	 * @param sucu the sucu
	 * @param div the div
	 */
	private void divisaGBP(SucursalReporte sucu, Divisas div) {
		sucu.setDivisaGbp(div.getId());

		for (com.baz.divisas.api.tef.sucursales.model.TipoOperacion tipoOp : div.getTiposOperacion()) {
			if (COMPRAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {

				sucu.setEstrategiaCompraGbp(tipoOp.getTipoCambioSpread());
				sucu.setTcSucursalCompraGbp(tipoOp.getTipoCambioSpot());
			}

			if (VENTAEFECTIVO.equals(tipoOp.getIdTipoOperacion())) {
				sucu.setEstrategiaVentaGbp(tipoOp.getTipoCambioSpread());
				sucu.setTcSucursalVentaGbp(tipoOp.getTipoCambioSpot());
			}
		}
	}


	/**
	 * Actualiza TC sucursal olp.
	 *
	 * @param request the request
	 * @return the response api
	 */
	@Override
	public ResponseApi actualizaTCSucursalOlp(@Valid ReqActSucursal request) {
		for(Divisa divisaOLP : request.getDivisas()) {
			Spot spotDivisaOLP = spotDao.findSpotVigenteDivisa(divisaOLP.getIdDivisa());
			
			Double compraOLP = spotDivisaOLP.getCompraEfectivo();
			Double ventaOLP = spotDivisaOLP.getVentaEfectivo();			
			
			if(Math.abs(divisaOLP.getSpread().getCompra()) > EPSILON) {		
				compraOLP += divisaOLP.getSpread().getCompra();
			}
			
			if(Math.abs(divisaOLP.getSpread().getVenta()) > EPSILON) {
				ventaOLP += divisaOLP.getSpread().getVenta();
			}
			
			if(compraOLP > ventaOLP) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,Arrays.asList("El TC de compra es mayor al TC de la venta de la divisa "));
			}
			else{
				
				if(Math.abs(divisaOLP.getSpread().getCompra()) > EPSILON) {		
					sucursalesDao.updateSucursalTCSpreadSpotOlp(request.getIdsucursal(), divisaOLP.getIdDivisa(), COMPRAEFECTIVO,
					spotDivisaOLP.getCompraEfectivo() + divisaOLP.getSpread().getCompra(),
					divisaOLP.getSpread().getCompra());
				}
				
				if(Math.abs(divisaOLP.getSpread().getVenta()) > EPSILON) {
					sucursalesDao.updateSucursalTCSpreadSpotOlp(request.getIdsucursal(), divisaOLP.getIdDivisa(), VENTAEFECTIVO,
							ventaOLP, divisaOLP.getSpread().getVenta());
				}
			}
			
		}
		List<String> sucursalPromo = new ArrayList<>();
		sucursalPromo.add(request.getIdsucursal());
		promoEspacioImpl.armaTCSucursal(sucursalPromo);
		return new ResponseApi(EstatusResponse.OK);
	}
}
