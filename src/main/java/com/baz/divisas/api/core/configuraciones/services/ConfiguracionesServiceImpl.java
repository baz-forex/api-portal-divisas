package com.baz.divisas.api.core.configuraciones.services;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.core.configuraciones.dao.ConfiguracionesDao;
import com.baz.divisas.api.core.configuraciones.dto.ReqCampoRestringido;
import com.baz.divisas.api.core.configuraciones.model.CampoRestringido;
import com.baz.divisas.api.core.configuraciones.model.Identificacion;
import com.baz.divisas.api.core.configuraciones.model.Notificacion;
import com.baz.divisas.api.core.configuraciones.model.ParametriaBusquedas;
import com.baz.divisas.api.core.configuraciones.model.RestriccionExclusion;
import com.baz.divisas.api.core.configuraciones.model.SegmentoSucursal;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Servicio de Configuraciones
 * @author B813196
 * itoxtle
 */
@Service
public class ConfiguracionesServiceImpl {
		
	private static final String SUCURSALES = "SUCURSALES";
	private static final String ESTADOS = "ESTADOS";
	private static final String ECOSISTEMAS = "ECOSISTEMAS";
	private static final List<String> tiposSegmentos = Arrays.asList(SUCURSALES,"REGION_NEGOCIO","REGION_GEOGRAFICA",
																	 ESTADOS,ECOSISTEMAS);	
	private static final String NO_EXISTE_SEGMENTO = "No existe el segmento: ";
	
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
	 * Metodo de consulta Parametria de Busquedas
	 * 
	 * Obtiene la configuracion de porcentajes que son utilizados para 
	 * realizar comparaciones en las busquedas de Cliente y Listas negras
	 * 
	 * @return - ResponseApi
	 */
	public ResponseApi getParametriaBusquedas() {
		
		ResponseApi resultado;
		
		// Consulta parametria de busquedas
		ParametriaBusquedas parametria = configDao.findParametriaBusquedas();
		
		if (parametria != null) {
			resultado = new ResponseApi(EstatusResponse.OK, parametria);					
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
	    								Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo de actualizacion Parametria de Busquedas
	 * 
	 * Actualiza la configuracion de porcentajes que son utilizados para 
	 * realizar comparaciones en las busquedas de Cliente y Listas negras
	 * 
	 * @param parametria - ParametriaBusquedas
	 * @return ResponseApi
	 */
	public ResponseApi actualizaParametriaBusquedas(ParametriaBusquedas parametria) {
		
		ResponseApi resultado;
		
		ParametriaBusquedas parametriaAnterior = configDao.findParametriaBusquedas();		
				
		if (configDao.updateParametriaBusquedas(parametria)) {

			resultado = new ResponseApi(EstatusResponse.OK);

			bitacora.registrar(AccionBitacora.EDITAR, "Actualización parametria de búsquedas", parametriaAnterior,
					parametria);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible actualzar la parametria de busquedas."));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo de consulta de campos restringidos
	 * @return Lista de campos restringidos
	 */
	public ResponseApi getCamposRestringidos(){
		
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
	 * Metodo de consulta de campo restringido por Id
	 * @param idCampo - Identificador de campo
	 * @return Campo restringido
	 */
	public ResponseApi getCamposRestringido(String idCampo){
		
		ResponseApi resultado;
		
		// Consulta campos restringidos
		CampoRestringido campoRestringido = configDao.findCamposRestringidoById(idCampo);
		
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
	 * Metodo ara agregar una cadena restringida al campo
	 * @param request - ReqCampoRestringido
	 * @return Campo restringido
	 */
	public ResponseApi agregarRestriccionCampo(ReqCampoRestringido request){
		
		ResponseApi resultado;
		
		UpdateResult resultUpdate = configDao.addRestriccionCampo(request.getIdCampo(), request.getRestriccion());
		
		if(resultUpdate.getMatchedCount() > 0 ) {
			
			if(resultUpdate.getModifiedCount() > 0) {
				
				resultado = new ResponseApi(EstatusResponse.OK);
				
				bitacora.registrar(AccionBitacora.REGISTRAR, 
						"Se agregó la restricción "+ request.getRestriccion() +" al campo "+ request.getIdCampo());
			}
			else {
				LogApp.mensaje("Ya existe la restriccion: " + request.getRestriccion() + " para el campo: " + request.getIdCampo());
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Ya existe la restriccion para ese campo"));
			}
			
		}
		else {
			LogApp.mensaje("No existe el campo: " + request.getIdCampo());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe el campo"));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo ara agregar una cadena restringida al campo
	 * @param request - ReqCampoRestringido
	 * @return Campo restringido
	 */
	public ResponseApi eliminarRestriccionCampo(ReqCampoRestringido request){
		
		ResponseApi resultado;
		
		UpdateResult resultUpdate = configDao.removeRestriccionCampo(request.getIdCampo(), request.getRestriccion());
		
		if(resultUpdate.getMatchedCount() > 0 ) {
			
			if(resultUpdate.getModifiedCount() > 0) {
				
				resultado = new ResponseApi(EstatusResponse.OK);
				
				bitacora.registrar(AccionBitacora.ELIMINAR, 
								   "Se eliminó la restricción "+ request.getRestriccion() +" del campo "+ request.getIdCampo());
			}
			else {
				LogApp.mensaje("No existe la restriccion: " + request.getRestriccion() + " para el campo: " + request.getIdCampo());
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("No existe la restriccion para ese campo"));
			}
			
		}
		else {
			LogApp.mensaje("No existe el campo: " + request.getIdCampo());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe el campo"));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo para editar un pais restringido o naciondalidad.
	 * @param listPaisNacionalidad - List<PaisesRestringido>
	 * @return Pais restringido
	 */
    public ResponseApi actualizaRestriccionPaisNacionalidad(Collection<CatPaisNacionalidad> listPaisNacionalidad) {

        ResponseApi resultado;
        CatPaisNacionalidad paisNacionalidadBitacora = new CatPaisNacionalidad();

        if (listPaisNacionalidad.isEmpty()) {
            return new ResponseApi(EstatusResponse.SIN_RESULTADOS,
                    Arrays.asList(Constant.SIN_INFORMACION));

        }

        for (CatPaisNacionalidad paisNacionalidad : listPaisNacionalidad) {

            if (paisNacionalidad.isRestriccionNacionalidad()) {
                
                paisNacionalidadBitacora  = configDao.buscaPaisRestriccion(paisNacionalidad);
                
                if ( !buscaIdentificacion(paisNacionalidad)) {
                    
                    
                    return new ResponseApi(EstatusResponse.SIN_RESULTADOS,
                            Arrays.asList(Constant.SIN_INFORMACION));
                }
                

            }
            else {
               Identificacion identificacion = new Identificacion("0", "0");
               RestriccionExclusion restriccion = new RestriccionExclusion(identificacion);              
               paisNacionalidad.setRestriccionExclusion(restriccion);
            }

            CatPaisNacionalidad paisNacionalidadAnterior = configDao
                    .updateRestriccionPaisNacionalidad(paisNacionalidad);
            
            registraBitacora(paisNacionalidadAnterior, paisNacionalidad, paisNacionalidadBitacora);

        }

        resultado = new ResponseApi(EstatusResponse.OK);

        return resultado;
    }
	
	
    public boolean buscaIdentificacion(CatPaisNacionalidad paisNacionalidad) {

        boolean resultado = false;
        
        if(paisNacionalidad.getRestriccionExclusion()== null) {
            return  false;
        }
        
        if(paisNacionalidad.getRestriccionExclusion().getIdentificacion().getPrimaria().equals("0") 
                &&  paisNacionalidad.getRestriccionExclusion().getIdentificacion().getAdicional().equals("0")) {
            
            return true;
            
        }
        
        Document buscaIdentificacionPrimaria = configDao
                .findIdIdentificacion(paisNacionalidad.getRestriccionExclusion().getIdentificacion().getPrimaria());
        Document buscaIdentificacionAdicional = configDao.findIdIdentificacion(
                paisNacionalidad.getRestriccionExclusion().getIdentificacion().getAdicional());

        if (buscaIdentificacionPrimaria != null && buscaIdentificacionAdicional != null) {

            if (buscaIdentificacionPrimaria.get("id_cliente_amigo")
                    .equals(buscaIdentificacionAdicional.get("id_cliente_amigo"))) {
                resultado = false;
            } else {

                resultado = true;
            }

        }

        return resultado;
    }
    
    public void registraBitacora(CatPaisNacionalidad paisNacionalidadAnterior, CatPaisNacionalidad paisNacionalidad,
            CatPaisNacionalidad paisNacionalidadBitacora) {
        if (paisNacionalidadAnterior != null &&
                paisNacionalidadAnterior.isRestriccionPais() != paisNacionalidad.isRestriccionPais()) {

            bitacora.registrar(AccionBitacora.EDITAR,
                    MessageFormat.format("Se {0} restricción al país {1}",
                            getDescAccionRestriccion(paisNacionalidad.isRestriccionPais()),
                            paisNacionalidad.getDescripcion()));
        }

        if (paisNacionalidadAnterior != null &&
                paisNacionalidadAnterior.isRestriccionNacionalidad() != paisNacionalidad
                        .isRestriccionNacionalidad()) {

            bitacora.registrar(AccionBitacora.EDITAR,
                    MessageFormat.format("Se {0} restricción a la nacionalidad {1}",
                            getDescAccionRestriccion(paisNacionalidad.isRestriccionNacionalidad()),
                            paisNacionalidad.getDescripcion()));
        }

        if (paisNacionalidadAnterior != null && paisNacionalidad.isRestriccionNacionalidad()
				&& null != paisNacionalidadBitacora
                && paisNacionalidadBitacora.getRestriccionExclusion().getIdentificacion().getPrimaria().equals("0") &&
                paisNacionalidadBitacora.getRestriccionExclusion().getIdentificacion().getAdicional().equals("0")) {
            bitacora.registrar(AccionBitacora.EDITAR,
                    "Se quita exclusion", paisNacionalidadAnterior.getRestriccionExclusion().getIdentificacion(),
                    paisNacionalidad.getRestriccionExclusion().getIdentificacion());

        }

        if (paisNacionalidadAnterior != null
                && !paisNacionalidad.getRestriccionExclusion().getIdentificacion().getPrimaria().equals("0")
                && !paisNacionalidad.getRestriccionExclusion().getIdentificacion().getAdicional().equals("0")) {

            bitacora.registrar(AccionBitacora.EDITAR,
                    "Se Agrega exclusion", paisNacionalidadAnterior.getRestriccionExclusion().getIdentificacion(),
                    paisNacionalidad.getRestriccionExclusion().getIdentificacion());

        }

    }
    	
	private static String getDescAccionRestriccion(boolean estatus) {
	
		String accion = "";
		
		if(estatus) {
			accion = "agregó";
		}
		else {
			accion = "eliminó";
		}
		
		return accion;
	}
	
	
	/**
	 * Metodo de consulta de Catalogo de Tipo de Segmento Sucursal
	 * @return Catalogo de Segmento Sucursal
	 */
	public ResponseApi getCatSegmentosSucursal(){
		
		List<Catalogo> catTipoSegmentoSucursal = Arrays.asList(new Catalogo(SUCURSALES,SUCURSALES),
				new Catalogo("REGION_NEGOCIO","REGION NEGOCIO"),new Catalogo("REGION_GEOGRAFICA","REGION GEOGRAFICA"),
				new Catalogo(ECOSISTEMAS,ECOSISTEMAS),new Catalogo(ESTADOS,ESTADOS));
		
		return new ResponseApi(EstatusResponse.OK, catTipoSegmentoSucursal);
		
	}
	
	
	/**
	 * Metodo de consulta de Segmentos Sucursal por Tipo
	 * @param tipoSegmento - Tipo de segmento de sucursales
	 * @return Lista de Segmentos
	 */
	public ResponseApi getSegmentosSucursal(String tipoSegmento) {
		
		ResponseApi resultado;
		
		List<SegmentoSucursal> segmentos = configDao.findSegmentosSucursalByTipo(tipoSegmento);
		
		if(!segmentos.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, segmentos);
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo de consulta de segmento por Id
	 * @param idSegmento - Identificador del segmento
	 * @return Segmento
	 */
	public ResponseApi getSegmentoSucursal(String idSegmento) {
		
		ResponseApi resultado;
		
		SegmentoSucursal segmento = configDao.findSegmentoSucursalById(idSegmento);
		
		if(segmento != null) {
			resultado = new ResponseApi(EstatusResponse.OK, segmento);
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo de registro de un nuevo segmento de sucursales
	 * @param segmento - Segmento a registrar
	 * @return ResponseApi
	 */
	public ResponseApi registraSegmentoSucursal(SegmentoSucursal segmento) {
		
		ResponseApi resultado;
		
		SegmentoSucursal resultSegmento = configDao.findSegmentoSucursalByIdRegion(segmento.getTipo(),segmento.getIdRegion());
		
		if(resultSegmento == null) {
			
			if(tiposSegmentos.contains(segmento.getTipo())) {
				
				InsertOneResult resultInsert = configDao.insertSegmentoSucursal(segmento);
				
				if(resultInsert.getInsertedId() != null) {
					
					resultado = new ResponseApi(EstatusResponse.OK);
					
					bitacora.registrar(AccionBitacora.REGISTRAR, 
									   "Registro del segmento "+segmento.getIdRegion()+"-"+segmento.getNombre(), null, segmento);
					
				}
				else {			
					resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
							Arrays.asList("No fue posible registrar el segmento de sucursales."));
				}
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("El tipo de segmento no es valido"));	
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un segmento de sucursales con el mismo tipo e id de region."));		
		}

		
		return resultado;
	}
	
	
	/**
	 * Metodo de actualizacion de un segmento de sucursales
	 * @param segmento - Segmento a actualizar
	 * @return ResponseApi
	 */
	public ResponseApi actualizarSegmentoSucursal(SegmentoSucursal segmento) {
		
		ResponseApi resultado;
		
		SegmentoSucursal resultSegmento = configDao.findSegmentoSucursalByIdRegion(segmento.getTipo(),segmento.getIdRegion());
		
		if(resultSegmento == null || resultSegmento.getId().equals(segmento.getId())) {
			
			if(tiposSegmentos.contains(segmento.getTipo())) {
				
				SegmentoSucursal segmentoActual = configDao.findSegmentoSucursalById(segmento.getId()); 
								
				UpdateResult resultUpdate = configDao.updateSegmentoSucursal(segmento);
				
				if(resultUpdate.getMatchedCount() > 0 ) {
						
					resultado = new ResponseApi(EstatusResponse.OK);
					
					bitacora.registrar(AccionBitacora.EDITAR, 
									   "Actualización del segmento "+segmentoActual.getIdRegion()+"-"+segmentoActual.getNombre(), 
									   segmentoActual, segmento, resultUpdate);
					
				}
				else {
					LogApp.mensaje(NO_EXISTE_SEGMENTO + segmento.getId());
					resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
							Arrays.asList(NO_EXISTE_SEGMENTO));
				}
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("El tipo de segmento no es valido"));	
			}

		}
		else {			
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un segmento de sucursales con el mismo tipo e id de region."));
		}

		return resultado;
	}
	
	
	/**
	 * Metodo de actualizacion del estatus un segmento de sucursales
	 * @param idSegmento - Id del Segmento a actualizar
	 * @return ResponseApi
	 */
	public ResponseApi actualizarEstatusSegmentoSucursal(String idSegmento) {
		
		ResponseApi resultado;
		
		SegmentoSucursal resultSegmento = configDao.findSegmentoSucursalById(idSegmento);
		
		if(resultSegmento != null) {
			
			UpdateResult resultUpdate = configDao.updateEstatusSegmentoSucursal(idSegmento, !resultSegmento.isActivo());
			
			if(resultUpdate.getMatchedCount() > 0 ) {
					
				resultado = new ResponseApi(EstatusResponse.OK);
				
				bitacora.registrarCambioEstatus(AccionBitacora.EDITAR, 
												"Actualización del segmento "+resultSegmento.getIdRegion()+"-"+resultSegmento.getNombre()+": ",
												!resultSegmento.isActivo());
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible cambiar el estatus del segmento de sucursales"));
			}
			
		}
		else {			
			LogApp.mensaje(NO_EXISTE_SEGMENTO + idSegmento);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(NO_EXISTE_SEGMENTO));
		}

		return resultado;
	}
	
	
	/**
	 * Metodo de eliminacion de un segmento de sucursales
	 * @param idSegmento - Identificador del segmento a eliminar
	 * @return ResponseApi
	 */
	public ResponseApi eliminarSegmentoSucursal(String idSegmento) {
		
		ResponseApi resultado;
				
		SegmentoSucursal segmento = configDao.removeSegmentoSucursal(idSegmento);
		
		if(segmento != null) {
				
			resultado = new ResponseApi(EstatusResponse.OK);
			
			bitacora.registrar(AccionBitacora.ELIMINAR, 
					"Eliminación del segmento "+segmento.getIdRegion()+"-"+segmento.getNombre(), segmento, null);
			
		}
		else {
			LogApp.mensaje(NO_EXISTE_SEGMENTO + idSegmento);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList(NO_EXISTE_SEGMENTO));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo de consulta de Catalogo de Estatus de Notificacion
	 * @return Catalogo de Estatus de Notificacion
	 */
	public ResponseApi getCatEstatusNotificacion(){
		
		List<Catalogo> catEstatusNotificacion = Arrays.asList(new Catalogo("VIGENTES", "Vigente"),
				new Catalogo("NO_VIGENTES","No Vigente"),new Catalogo("PROXIMAS","Próxima"));
		
		return new ResponseApi(EstatusResponse.OK, catEstatusNotificacion);
		
	}
	
	
	/**
	 * Metodo de consulta de Notificaciones por estatus
	 * @param estatus - Estatus de la Notificacion
	 * @param sucursal - Sucursal de la Notificacion
	 * @return ResponseApi
	 */
	public ResponseApi getNotificaciones(String estatus, String sucursal) {
		
		ResponseApi resultado;
		
		List<Notificacion> notificaciones = configDao.findNotificaciones(estatus, sucursal);
		
		if(!notificaciones.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, notificaciones);
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo de consulta de Notificacion por Id
	 * @param idNotificacion - Identificador de la Notificacion
	 * @return ResponseApi
	 */
	public ResponseApi getNotificacion(String idNotificacion) {
		
		ResponseApi resultado;
		
		Notificacion notificacion = configDao.findNotificacionById(idNotificacion, Collections.emptyList());
		
		if(notificacion != null) {
			resultado = new ResponseApi(EstatusResponse.OK, notificacion);
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo de regitro de notificacion
	 * @param notificacion - Notificacion
	 * @return ResponseApi
	 * @throws IOException - IOException
	 */
	public ResponseApi registraNotificacion(Notificacion notificacion) throws IOException {
		
		ResponseApi resultado;
		
		notificacion.setImagen(new Binary(notificacion.getFileImagen().getBytes()));
		notificacion.setFechaRegistro(LocalDateTime.now());
		
		if(notificacion.isGeneralSucursales()) {
			notificacion.setSucursales(Collections.emptyList());
		}
		
		InsertOneResult resultInsert = configDao.insertNotificacion(notificacion);
		
		if(resultInsert.getInsertedId() != null) {
			
			resultado = new ResponseApi(EstatusResponse.OK);
			
			bitacora.registrar(AccionBitacora.REGISTRAR, 
							   "Registro de notificación "+notificacion.getNombre(), null, notificacion);
			
		}
		else {			
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible registrar el segmento de sucursales."));
		}
		
		return resultado;
		
	}
	
	
	/**
	 * Metodo de actualizacion de notificacion
	 * @param notificacion - Notificacion
	 * @return ResponseApi
	 * @throws IOException - IOException
	 */
	public ResponseApi actualizaNotificacion(Notificacion notificacion) throws IOException {
		
		ResponseApi resultado;
		
		Notificacion notificacionActual = configDao.findNotificacionById(notificacion.getId(), 
																		 Arrays.asList("fechaRegistro","fechaActualizacion"));
		
		if(notificacionActual != null) {
						
			notificacion.setImagen(notificacionActual.getImagen());

			// Si se actualizó la imagen de la notificacion
			if(notificacion.getFileImagen() != null) {
				notificacion.setImagen(new Binary(notificacion.getFileImagen().getBytes()));
			}
					
			if(notificacion.isGeneralSucursales()) {
				notificacion.setSucursales(Collections.emptyList());
			}
					
			UpdateResult resultUpdate = configDao.updateNotificacion(notificacion);
				
			resultado = new ResponseApi(EstatusResponse.OK);
			
			if(notificacion.getFileImagen() == null) {
				notificacionActual.setImagen(null);
				notificacion.setImagen(null);
			}

			notificacion.setEstatusNotificacion();
			
			bitacora.registrar(AccionBitacora.EDITAR, 
							   "Actualización de la notificación "+ notificacionActual.getNombre(), 
							   notificacionActual, notificacion, resultUpdate);

		}
		else {
			LogApp.mensaje("No existe la notificacion: " + notificacion.getId());
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe la notificacion"));			
		}
						
		return resultado;
		
	}
	
	
	/**
	 * Metodo de actualizacion del estatus una notificacion
	 * @param idNotificacion - Id de la notificacion a actualizar
	 * @return ResponseApi
	 */
	public ResponseApi actualizarEstatusNotificacion(String idNotificacion) {
		
		ResponseApi resultado;
		
		Notificacion resultNotificacion = configDao.findNotificacionById(idNotificacion, Collections.emptyList());
		
		if(resultNotificacion != null) {
			
			UpdateResult resultUpdate = configDao.updateEstatusNotificacion(idNotificacion, !resultNotificacion.isActivo());
			
			if(resultUpdate.getMatchedCount() > 0 ) {
					
				resultado = new ResponseApi(EstatusResponse.OK);
				
				bitacora.registrarCambioEstatus(AccionBitacora.EDITAR, 
												"Actualización estatus de la notificación "+resultNotificacion.getNombre()+": ",
												!resultNotificacion.isActivo());
				
			}
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible cambiar el estatus de la notificación"));
			}
			
		}
		else {			
			LogApp.mensaje("No existe la notificación: " + idNotificacion);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe la notificación"));
		}

		return resultado;
	}
	
	
	/**
	 * Metodo de reinicio de visualizaciones de una notificacion
	 * @param idNotificacion - Id de la notificacion a actualizar
	 * @return ResponseApi
	 */
	public ResponseApi reiniciaNotificacion(String idNotificacion) {
		
		ResponseApi resultado;
		
		Notificacion resultNotificacion = configDao.findNotificacionById(idNotificacion, Collections.emptyList());
		
		if(resultNotificacion != null) {
			
			configDao.resetNotificacion(idNotificacion);
				
			resultado = new ResponseApi(EstatusResponse.OK);
			
			bitacora.registrar(AccionBitacora.EDITAR, "Reinicio de visualizaciones de la notificación: "+ 
							   resultNotificacion.getNombre());

		}
		else {			
			LogApp.mensaje("No existe la notificación: " + idNotificacion);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe la notificación"));
		}

		return resultado;
	}

}
