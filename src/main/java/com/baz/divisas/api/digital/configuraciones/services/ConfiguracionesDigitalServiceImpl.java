package com.baz.divisas.api.digital.configuraciones.services;

import java.util.Arrays;
import java.util.Locale;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.digital.configuraciones.dao.ConfiguracionesDigitalDao;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaOperacion;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaRemesas;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaSeguimientoTC;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.google.gson.Gson;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio de Configuraciones
 * @author 1027346
 * ymartinezb
 */
@Service
public class ConfiguracionesDigitalServiceImpl {

	private ConfiguracionesDigitalDao configDao;
	
	private BitacoraServiceImpl bitacora;
	
	private static final String SEGUIMIENTO_TC = "seguimiento_tipo_cambio";
	private static final String PARAM_REMESAS = "parametria_remesas";

	/**
	 * Constructor de la clase
	 * @param configDao - DAO Configuraciones
	 * @param bitacora - Servicio Bitacora
	 */
	@Autowired
	public ConfiguracionesDigitalServiceImpl(ConfiguracionesDigitalDao configDao, BitacoraServiceImpl bitacora) {
		super();
		this.configDao = configDao;
		this.bitacora = bitacora;
	}
	
	/**
	 * Metodo de consulta Parametria de Operacion Digital.
	 *
	 * @param idParametria - Id de Parametria
	 * @param tipoOperacion - Tipo de Operacion
	 * 
	 * @return - ResponseApi
	 */
	public ResponseApi getParametriaOperacion(String idParametria, String tipoOperacion) {
		
		ResponseApi resultado;
		
		Document docParametria = configDao.findParametria(idParametria, tipoOperacion);
		
		if (docParametria != null) {			
			Gson gson = new Gson();
			String docJson = gson.toJson(docParametria);
			ParametriaOperacion parametria = gson.fromJson(docJson, ParametriaOperacion.class);
			
			resultado = new ResponseApi(EstatusResponse.OK, parametria);					
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
	    								Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo de actualizacion Parametria de Operacion Digital.
	 *
	 * @param idAplicacion - Id de Aplicacion
	 * @param idParametria - Id de Parametria
	 * @param tipoOperacion - Tipo de Operacion
	 * @param parametria - ParametriaOperacion
	 * @return ResponseApi
	 */
	public ResponseApi updateParametriaOperacion(String idAplicacion, String idParametria, String tipoOperacion, 
			ParametriaOperacion parametria) {
		
		ResponseApi resultado;
		
		Document docParametriaNueva = Document.parse(new Gson().toJson(parametria));
		
		Document docParametriaAnterior = configDao.updateParametria(idParametria, tipoOperacion, docParametriaNueva);
		
		if (docParametriaAnterior != null) {

			resultado = new ResponseApi(EstatusResponse.OK);
			
			Gson gson = new Gson();
			String docJson = gson.toJson(docParametriaAnterior.get(idParametria));
			ParametriaOperacion parametriaAnterior = gson.fromJson(docJson, ParametriaOperacion.class);
			
			String descTipoOperacion = "";
			
			if(tipoOperacion.equals(Constant.COMPRA_ABONO)) {				
				descTipoOperacion = Constant.DESC_COMPRA;
			}
			else {
				descTipoOperacion = Constant.DESC_VENTA;
			}

			bitacora.registrar(AccionBitacora.EDITAR, "Actualización parametría de operación "+ descTipoOperacion + " "+ 
			idAplicacion.toUpperCase(Locale.getDefault()), parametriaAnterior, parametria);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible actualzar la parametria de operacion digital."));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo de consulta Parametria de Operacion Digital.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * 
	 * @return - ResponseApi
	 */
	public ResponseApi getParametriaSeguimientoTC(String tipoOperacion) {
		
		ResponseApi resultado;
		
		Document docParametria = configDao.findParametria(SEGUIMIENTO_TC, tipoOperacion);
		
		if (docParametria != null) {			
			Gson gson = new Gson();
			String docJson = gson.toJson(docParametria);
			ParametriaSeguimientoTC parametria = gson.fromJson(docJson, ParametriaSeguimientoTC.class);
			
			resultado = new ResponseApi(EstatusResponse.OK, parametria);					
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
	    								Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo de actualizacion Parametria de Operacion Digital.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * @param parametria - ParametriaSeguimientoTC
	 * @return ResponseApi
	 */
	public ResponseApi updateParametriaSeguimientoTC(String tipoOperacion, ParametriaSeguimientoTC parametria) {
		
		ResponseApi resultado;
		
		Document docParametriaNueva = Document.parse(new Gson().toJson(parametria));
		
		Document docParametriaAnterior = configDao.updateParametria(SEGUIMIENTO_TC, tipoOperacion, docParametriaNueva);
		
		if (docParametriaAnterior != null) {

			resultado = new ResponseApi(EstatusResponse.OK);
			
			Gson gson = new Gson();
			String docJson = gson.toJson(docParametriaAnterior.get(SEGUIMIENTO_TC));
			ParametriaSeguimientoTC parametriaAnterior = gson.fromJson(docJson, ParametriaSeguimientoTC.class);
			
			String descTipoOperacion = "";
			
			if(tipoOperacion.equals(Constant.COMPRA_ABONO)) {				
				descTipoOperacion = Constant.DESC_COMPRA;
			}
			else {
				descTipoOperacion = Constant.DESC_VENTA;
			}

			bitacora.registrar(AccionBitacora.EDITAR, "Actualización parametría de Seguimiento de Tipo de Cambio "+
					descTipoOperacion, parametriaAnterior, parametria);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible actualzar la parametria de Seguimiento de Tipo de Cambio."));
		}
		
		return resultado;
	}
		
	/**
	 * Metodo de consulta Parametria de Operacion Digital.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * 
	 * @return - ResponseApi
	 */
	public ResponseApi getParametriaRemesas(String tipoOperacion) {
		
		ResponseApi resultado;
		
		Document docParametria = configDao.findParametria(PARAM_REMESAS, tipoOperacion);
		
		if (docParametria != null) {			
			Gson gson = new Gson();
			String docJson = gson.toJson(docParametria);
			ParametriaRemesas parametria = gson.fromJson(docJson, ParametriaRemesas.class);
			
			resultado = new ResponseApi(EstatusResponse.OK, parametria);					
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
	    								Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo de actualizacion Parametria Remesas
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * @param parametria - ParametriaRemesas
	 * @return ResponseApi
	 */
	public ResponseApi updateParametriaRemesas(String tipoOperacion, ParametriaRemesas parametria) {
		
		ResponseApi resultado;
		
		Document docParametriaNueva = Document.parse(new Gson().toJson(parametria));
		
		Document docParametriaAnterior = configDao.updateParametria(PARAM_REMESAS, tipoOperacion, docParametriaNueva);
		
		if (docParametriaAnterior != null) {

			resultado = new ResponseApi(EstatusResponse.OK);
			
			Gson gson = new Gson();
			String docJson = gson.toJson(docParametriaAnterior.get(PARAM_REMESAS));
			ParametriaRemesas parametriaAnterior = gson.fromJson(docJson, ParametriaRemesas.class);
			
			String descTipoOperacion = "";
			
			if(tipoOperacion.equals(Constant.COMPRA_ABONO)) {				
				descTipoOperacion = Constant.DESC_COMPRA;
			}
			else {
				descTipoOperacion = Constant.DESC_VENTA;
			}

			bitacora.registrar(AccionBitacora.EDITAR, "Actualización parametría de Beneficio Remesas "+
					descTipoOperacion, parametriaAnterior, parametria);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible actualzar la parametria de Beneficio Remesas."));
		}
		
		return resultado;
	}
	
}
