package com.baz.divisas.api.tef.limites.services.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.limites.dao.LimitesPorSucursalDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorSucursalService;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.model.LimitesReporte;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.api.tef.sucursales.utils.ArchivosExcel;
import com.baz.divisas.api.tef.sucursales.utils.ReporteLimitesSucursales;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase LimitesPorSucursalService.
 *
 * @author 164945 ogatica
 */
@Service
public class LimitesPorSucursalService implements ILimitesPorSucursalService {

	/** The limites por sucursal dao. */
	private LimitesPorSucursalDao limitesPorSucursalDao;
	
	/** The bitacora. */
	private BitacoraServiceImpl bitacora;
	/** The sucursales dao. */
	private SucursalesDao sucursalesDao;
	
	/** The promo espacio impl. */
	private PromoEspacioServiceImpl promoEspacioImpl;
	
	/**
	 * Cosntructor de la clase.
	 *
	 * @param limitesSucursalDao - LimitesPorSucursalDao
	 * @param bitacora - BitacoraServiceImpl
	 * @param sucursalesDao the sucursales dao
	 * @param reporte the reporte
	 */
	@Autowired
	public LimitesPorSucursalService(LimitesPorSucursalDao limitesSucursalDao, BitacoraServiceImpl bitacora,SucursalesDao sucursalesDao) {
		super();
		this.limitesPorSucursalDao = limitesSucursalDao;
		this.bitacora = bitacora;
		this.sucursalesDao = sucursalesDao;
	}

	/**
	 * Metodo de consulta de limite de sucursal.
	 *
	 * @return lista de limite de sucursal
	 */
	@Override
	public ResponseApi consultaLimitesSucursal() {
		ResponseApi resultado;
		List<LimitesPorSucursalDto> limites = limitesPorSucursalDao.findLimitesSucursal();

		if (!limites.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, limites);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por sucursal registrados."));
		}

		return resultado;

	}

	/**
	 * Metodo de registro de limite por sucursal.
	 *
	 * @param request - LimitesPorSucursalDto
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi creaLimiteSucursal(LimitesPorSucursalDto request) {
		ResponseApi resultado;

		List<LimitesPorSucursalDto> limite = limitesPorSucursalDao.findLimitesSucursalId(request);

		if (limite.isEmpty()) {

			request.setIdBitacoraAlta(bitacora.registrar(AccionBitacora.REGISTRAR,
					"Registro del limites de sucursal " + request.getIdSucursal(), null,
					request));

			request.setActivo(true);
			
			limitesPorSucursalDao.insertLimitesSucursal(request);

			resultado = new ResponseApi(EstatusResponse.OK);


		} 
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un limite de sucursal con el mismo tipo e id de limite."));
		}

		return resultado;
	}

	/**
	 * Metodo de actualizacion de limite de sucursal.
	 *
	 * @param request - LimitesPorSucursalDto
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaLimiteSucursal(LimitesPorSucursalDto request) {
		ResponseApi resultado;

		List<LimitesPorSucursalDto> limiteBD = limitesPorSucursalDao.findLimitesSucursalId(request);

		if (!limiteBD.isEmpty()) {

			request.setIdBitacoraActualizacion(bitacora.registrar(AccionBitacora.EDITAR,
					"Actualización de limites de sucursal " + request.getIdSucursal(), limiteBD, request));

			limitesPorSucursalDao.updateLimitesSucursal(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("No existe el limite de sucursal: " + request.getIdSucursal());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe el limite de sucursal"));
		}

		return resultado;
	}

	/**
	 * Metodo que actualiza el estatus de un limite de sucursal.
	 *
	 * @param request - LimitesPorSucursalDto
	 * @return ResponseApi
	 */
	@Override
	public ResponseApi actualizaEstatusLimiteSucursal(LimitesPorSucursalDto request) {
		ResponseApi resultado;

		List<LimitesPorSucursalDto> limite = limitesPorSucursalDao.findLimitesSucursalId(request);

		if (!limite.isEmpty()) {

			request.setIdBitacoraActualizacion(bitacora.registrarCambioEstatus(AccionBitacora.EDITAR,
					"Actualización estatus de limites de divias ", request.isActivo()));

			limitesPorSucursalDao.updateEstatusLimitesSucursal(request);

			resultado = new ResponseApi(EstatusResponse.OK);

		} 
		else {
			LogApp.mensaje("No existe el limite de sucursal: " + request.getIdSucursal());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe el limite de sucursal"));
		}

		return resultado;
	}

	/**
	 * Consulta limites sucursal olp.
	 *
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaLimitesSucursalOlp() {
		ResponseApi resultado;
		List<LimitesPorSucursalDto> limites = limitesPorSucursalDao.findLimitesSucursalOlp();

		if (!limites.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, limites);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen limites por sucursal registrados."));
		}

		return resultado;
	}

	/**
	 * Carga archivo limites.
	 *
	 * @param archivo the archivo
	 * @return the response api
	 */
	@Override
	public ResponseApi cargaArchivoLimites(MultipartFile archivo) {
		ArchivosExcel lectorExcel = new ArchivosExcel(sucursalesDao,promoEspacioImpl,limitesPorSucursalDao,bitacora);
		return lectorExcel.lecturaLimites(archivo);
	}
	
	
	/**
	 * Descarga limites sucursales.
	 *
	 * @param divisa the divisa
	 * @return the byte array input stream
	 */
	@Override
	public ByteArrayInputStream descargaLimitesSucursales(String divisa) {

		Iterable<Sucursal> tcSucursales = sucursalesDao.consulta();

		if (null != tcSucursales) {
			List<LimitesReporte> sucursales = new ArrayList<>();

			for (Sucursal sucursal : tcSucursales) {				
				if(null != sucursal.getLimites() && sucursal.getLimites().size() > 0) {
					for (Limites limite : sucursal.getLimites()) {
						if("OLP".equals(divisa) && "OLP".equals(limite.getCodigoDivisa())){							
							sucursales.add(creaLimiteSucursal(sucursal,limite));
						}
						else {
							if(!"OLP".equals(divisa) && !"OLP".equals(limite.getCodigoDivisa())) {
								sucursales.add(creaLimiteSucursal(sucursal,limite));
							}
						}
					}	
				}
			}

			return new ReporteLimitesSucursales().generaExcel(sucursales);
			
		} 
		return null;		
	}
	
	/**
	 * Crea limite sucursal.
	 *
	 * @param sucursal the sucursal
	 * @param limite the limite
	 * @return the limites reporte
	 */
	private LimitesReporte creaLimiteSucursal(Sucursal sucursal,Limites limite) {
		LimitesReporte limiteSucursal = new LimitesReporte();
		limiteSucursal.setId(sucursal.getId());
		limiteSucursal.setDivisa(limite.getCodigoDivisa());
		limiteSucursal.setTipoOperacion(limite.getIdTipoOperacion());
		limiteSucursal.setClienteDiario(limite.getDiario().getCliente().toString());
		limiteSucursal.setUsuarioDiario(limite.getDiario().getUsuario().toString());

		limiteSucursal.setExtranjeroUsuarioDiario(limite.getDiario().getUsuarioExtranjero().toString());					
		limiteSucursal.setExtranjeroClienteDiario(limite.getDiario().getClienteExtranjero().toString());

		limiteSucursal.setClienteMensual(limite.getMensual().getCliente().toString());
		limiteSucursal.setUsuarioMensual(limite.getMensual().getUsuario().toString());

		limiteSucursal.setExtranjeroUsuarioMensual(limite.getMensual().getUsuarioExtranjero().toString());					
		limiteSucursal.setExtranjeroClienteMensual(limite.getMensual().getClienteExtranjero().toString());
		return limiteSucursal;
		
	}
}

