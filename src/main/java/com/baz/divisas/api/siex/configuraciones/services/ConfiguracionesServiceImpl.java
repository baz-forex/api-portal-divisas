package com.baz.divisas.api.siex.configuraciones.services;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.siex.configuraciones.dao.ConfiguracionesDao;
import com.baz.divisas.api.siex.configuraciones.dto.ReqCampoRestringido;
import com.baz.divisas.api.siex.configuraciones.model.CampoRestringido;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.mongodb.client.result.UpdateResult;

/**
 * Servicio de Configuraciones
 * @author B813196
 * itoxtle
 */
@Service("ConfiguracionesSiexServiceImpl")
public class ConfiguracionesServiceImpl {
		
	private ConfiguracionesDao configDao;
	
	private BitacoraServiceImpl bitacora;

	
	/**
	 * Constructor de la clase
	 * @param configDao - DAO Configuraciones
	 * @param bitacora - Servicio Bitacora
	 */
	@Autowired
	public ConfiguracionesServiceImpl(ConfiguracionesDao configDao, BitacoraServiceImpl bitacora) {
		super();
		this.configDao = configDao;
		this.bitacora = bitacora;
	}

	
	/**
	 * Metodo de consulta de campos restringidos
	 * @return Lista de campos restringidos
	 */
	public ResponseApi getCamposRestringidosImpl(){
		
		ResponseApi resultado;
		
		// Consulta campos restringidos
		List<CampoRestringido> camposRestringidos = configDao.findCamposRestringidos();
		
		if(!camposRestringidos.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, camposRestringidos);
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo de descarga configuracion de campos restringidos
	 * @return Lista de campos restringidos
	 */
	public ByteArrayInputStream descargaCamposRestringidosImpl(){
				
		// Consulta configuracion de campos restringidos
		List<CampoRestringido> camposRestringidos = configDao.findConfigCamposRestringidos();
		
		return new CamposRestringidosXls().generaExel(camposRestringidos);
	}
	
	
	/**
	 * Metodo de consulta de campos restringidos
	 * @return Lista de campos restringidos
	 */
	public ResponseApi getReglasCampoRestringidoImpl(){
		
		List<Catalogo> catEstatusNotificacion = Arrays.asList(new Catalogo("empiezaCon", "Empieza con"),
				new Catalogo("terminaCon","Termina con"), new Catalogo("contiene","Contiene"), new Catalogo("igualA","Es igual"));
		
		return new ResponseApi(EstatusResponse.OK, catEstatusNotificacion);
	}
	
	
	/**
	 * Metodo de consulta de campo restringido por Id
	 * @param idCampo - Identificador de campo
	 * @param idRegla - Identificador de regla
	 * @return Campo restringido
	 */
	public ResponseApi getCampoRestringidoImpl(String idCampo, String idRegla){
		
		ResponseApi resultado;
		
		// Consulta campos restringidos
		CampoRestringido campoRestringido = configDao.findCamposRestringidoById(idCampo, idRegla);
		
		if(campoRestringido != null) {
			resultado = new ResponseApi(EstatusResponse.OK, campoRestringido);
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo para agregar una cadena restringida al campo
	 * @param request - ReqCampoRestringido
	 * @return Campo restringido
	 */
	public ResponseApi agregarRestriccionCampoImpl(@Valid ReqCampoRestringido request){
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		List<String> errores = new ArrayList<>();
		
		for (String idCampo : request.getIdsCampos()) {
			
			UpdateResult resultUpdate = configDao.addRestriccionCampo(idCampo, request.getIdRegla(), request.getRestriccion());
			
			if(resultUpdate.getMatchedCount() > 0 ) {
				
				if(resultUpdate.getModifiedCount() > 0) {
									
					bitacora.registrar(AccionBitacora.REGISTRAR, 
							"Se agregó la restricción '"+ request.getIdRegla() +"' '"+ request.getRestriccion() +"' al campo "+ idCampo);
				}
				else {					
					errores.add(idCampo + ": Ya existe la restricción '"+ request.getIdRegla() +"' '"+ request.getRestriccion() +"'");
				}
				
			}
			else {		
				errores.add(idCampo + ": No existe el campo");
			}
		}
		
		
		if(!errores.isEmpty()) {
			errores.add(0, "No fue posible actualizar algunos campos:");
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA, errores);
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo para eliminar una cadena restringida del campo
	 * @param request - ReqCampoRestringido
	 * @return Campo restringido
	 */
	public ResponseApi eliminarRestriccionCampoImpl(@Valid ReqCampoRestringido request){
		
		
		ResponseApi resultado = new ResponseApi(EstatusResponse.OK);
		
		List<String> errores = new ArrayList<>();
		
		for (String idCampo : request.getIdsCampos()) {
			
			UpdateResult resultUpdate = configDao.removeRestriccionCampo(idCampo, request.getIdRegla(),request.getRestriccion());
			
			if(resultUpdate.getMatchedCount() > 0 ) {
				
				if(resultUpdate.getModifiedCount() > 0) {
									
					bitacora.registrar(AccionBitacora.ELIMINAR, 
							"Se eliminó la restricción '"+ request.getIdRegla() +"' '"+ request.getRestriccion() +"' del campo "+ idCampo);
				}
				else {					
					errores.add(idCampo + ": No existe la restricción '"+ request.getIdRegla() +"' '"+ request.getRestriccion() +"'");
				}
				
			}
			else {		
				errores.add(idCampo + ": No existe el campo");
			}
		}
		
		
		if(!errores.isEmpty()) {
			errores.add(0, "No fue posible actualizar algunos campos:");
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA, errores);
		}
		
		return resultado;
	}	

}
