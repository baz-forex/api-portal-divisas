package com.baz.divisas.api.tef.parametria.services;

import java.util.Arrays;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.apigee.dto.ResultadoApi;
import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.apigee.model.ApiLlaves;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaParametros;
import com.baz.divisas.api.tef.parametria.dto.ReqDetalleParametro;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminacionAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ResConsultaAsignaciones;
import com.baz.divisas.api.tef.parametria.dto.ResConsultaParametros;
import com.baz.divisas.api.tef.parametria.dto.AsignacionParametro;
import com.baz.divisas.api.tef.parametria.dto.Parametro;
import com.baz.divisas.api.tef.parametria.dto.ReqActAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqArchivo;
import com.baz.divisas.api.tef.parametria.dto.ReqAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaAsignaciones;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.AESUtils;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.RSAUtilsPagosDigitales;

/**
 * The Class ParametriaSucursalesImpl.
 */
@Service
public class ParametriaSucursalesImpl {

	/** The tarjeta service. */
	private ParametriaService parametriaService;

	/**
	 * Instantiates a new parametria sucursales impl.
	 */
	public ParametriaSucursalesImpl() {
		super();
		this.parametriaService = new ParametriaService();
	}

	/**
	 * Instantiates a new tarjeta regalo service impl.
	 *
	 * @param parametriaService the parametria service
	 */
	public ParametriaSucursalesImpl(ParametriaService parametriaService ) {
		super();
		this.parametriaService = parametriaService;
	}

	/**
	 * Consulta parametros.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi consultaParametros(ReqBusquedaParametros request) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());
			ApiLlaves llaves = parametriaService.getLlavesDivisas(token.getAccess_token());

			if(null !=  llaves && null != llaves.getResultado()) {
				LogApp.mensaje(llaves.toString());
				if(!request.getParametro().isEmpty()) {
					String parametroEncriptado = RSAUtilsPagosDigitales.EncryptOAE256(request.getParametro(), llaves.getResultado().getAccesoPublico());
					request.setParametro(parametroEncriptado);
				}

				LogApp.mensaje(PropApp.getUrlConsultaParametros() +" : Request: "+request.toString());

				ResConsultaParametros respuesta = parametriaService.consultaParametros(request,llaves.getResultado().getIdAcceso(),token.getAccess_token()); 

				if(null !=  respuesta && null != respuesta.getResultado()) {
					LogApp.mensaje(respuesta.toString());

					if(respuesta.getResultado().getParametros().size()>0) {
						for(Parametro parametro :respuesta.getResultado().getParametros()) {
							parametro.setParametro(RSAUtilsPagosDigitales.DecryptOAE256(parametro.getParametro(), llaves.getResultado().getAccesoPrivado()));
						}
					}

					resultado = new ResponseApi(EstatusResponse.OK, respuesta.getResultado());
				}
				else {
					if(null == respuesta) {
						resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("Sin resultados"));
					}
					else {
						resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
					}
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Llaves de Api de seguridad de divisas"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}

	/**
	 * Registra parametros.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response api
	 */
	public ResponseApi registraParametro(ReqDetalleParametro request,String usuario) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());
			ApiLlaves llaves = parametriaService.getLlavesDivisas(token.getAccess_token());

			if(null !=  llaves && null != llaves.getResultado()) {
				LogApp.mensaje(llaves.toString());

				String parametroEncriptado = RSAUtilsPagosDigitales.EncryptOAE256(request.getParametro(), llaves.getResultado().getAccesoPublico());
				request.setParametro(parametroEncriptado);

				LogApp.mensaje(PropApp.getUrlGestionParametros() +" : Request: "+request.toString() + ", idParametro :" + request.getIdParametro());

				ResultadoApi respuesta = parametriaService.registraParametros(request,llaves.getResultado().getIdAcceso(),token.getAccess_token(), usuario); 

				if(null !=  respuesta && !"ERROR".equals(respuesta.getCodigo())) {
					LogApp.mensaje(respuesta.toString());

					resultado = new ResponseApi(EstatusResponse.OK, respuesta.getMensaje());
				}
				else {			

					resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Llaves de Api de seguridad de divisas"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}


	/**
	 * Actualiza parametro.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response api
	 */
	public ResponseApi actualizaParametro(ReqDetalleParametro request,String usuario) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());
			ApiLlaves llaves = parametriaService.getLlavesDivisas(token.getAccess_token());

			if(null !=  llaves && null != llaves.getResultado()) {
				LogApp.mensaje(llaves.toString());

				String parametroEncriptado = RSAUtilsPagosDigitales.EncryptOAE256(request.getParametro(), llaves.getResultado().getAccesoPublico());
				request.setParametro(parametroEncriptado);

				LogApp.mensaje(PropApp.getUrlGestionParametros() +" : Request: "+request.toString() + ", idParametro :" + request.getIdParametro());

				ResultadoApi respuesta = parametriaService.actualizaParametros(request,llaves.getResultado().getIdAcceso(),token.getAccess_token(), usuario); 

				if(null !=  respuesta && !"ERROR".equals(respuesta.getCodigo())) {
					LogApp.mensaje(respuesta.toString());

					resultado = new ResponseApi(EstatusResponse.OK, respuesta.getMensaje());
				}
				else {			

					resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Llaves de Api de seguridad de divisas"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}

	/**
	 * Registra parametros masivos.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response api
	 */
	public ResponseApi registraParametrosMasivos(ReqArchivo request, String usuario) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());
			ApiLlaves llaves = parametriaService.getLlavesDivisas(token.getAccess_token());

			if(null !=  llaves && null != llaves.getResultado()) {
				LogApp.mensaje(llaves.toString());

				String archivoEncriptado = AESUtils.encryptAES(request.getArchivo(), llaves.getResultado().getAccesoSimetrico(),llaves.getResultado().getCodigoAutentificacionHash());
				request.setArchivo(archivoEncriptado);

				LogApp.mensaje(PropApp.getUrlGestionParametrosMasivos() +" : Request: "+request.toString());

				ResultadoApi respuesta = parametriaService.registraParametrosMasivos(request,llaves.getResultado().getIdAcceso(),token.getAccess_token(), usuario); 

				if(null !=  respuesta && !"ERROR".equals(respuesta.getCodigo())) {
					LogApp.mensaje(respuesta.toString());

					resultado = new ResponseApi(EstatusResponse.OK, respuesta.getMensaje());
				}
				else {			

					resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Llaves de Api de seguridad de divisas"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}

	/**
	 * Busca parametros asignados.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi buscaParametrosAsignados(ReqBusquedaAsignaciones request) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());
			ApiLlaves llaves = parametriaService.getLlavesDivisas(token.getAccess_token());

			if(null !=  llaves && null != llaves.getResultado()) {
				LogApp.mensaje(llaves.toString());
				
				if(!request.getParametro().isEmpty()) {
					String parametroEncriptado = RSAUtilsPagosDigitales.EncryptOAE256(request.getParametro(), llaves.getResultado().getAccesoPublico());
					request.setParametro(parametroEncriptado);
				}
				if(!request.getAsignacion().isEmpty()) {
					String asignacionEncriptado = RSAUtilsPagosDigitales.EncryptOAE256(request.getAsignacion(), llaves.getResultado().getAccesoPublico());
					request.setAsignacion(asignacionEncriptado);
				}
				LogApp.mensaje(PropApp.getUrlConsultaAsignacionesParametros() +" : Request: "+request.toString());

				ResConsultaAsignaciones respuesta = parametriaService.consultaAsignacionesParametros(request,llaves.getResultado().getIdAcceso(),token.getAccess_token()); 

				if(null !=  respuesta && !"ERROR".equals(respuesta.getCodigo())) {
					LogApp.mensaje(respuesta.toString());

					if(respuesta.getResultado().getAsignaciones().size()>0) {
						for(AsignacionParametro asignacion : respuesta.getResultado().getAsignaciones()) {
							asignacion.setParametro(RSAUtilsPagosDigitales.DecryptOAE256(asignacion.getParametro(), llaves.getResultado().getAccesoPrivado()));
							asignacion.setAsignacion(RSAUtilsPagosDigitales.DecryptOAE256(asignacion.getAsignacion(), llaves.getResultado().getAccesoPrivado()));
						}
					}

					resultado = new ResponseApi(EstatusResponse.OK, respuesta.getResultado());
				}
				else {			

					if(null == respuesta) {
						resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList("Sin resultados"));
					}
					else {
						resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
					}
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Llaves de Api de seguridad de divisas"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}

	/**
	 * Crear asignacion parametro.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response api
	 */
	public ResponseApi crearAsignacionParametro(ReqAsignacion request,String usuario) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());
			ApiLlaves llaves = parametriaService.getLlavesDivisas(token.getAccess_token());

			if(null !=  llaves && null != llaves.getResultado()) {
				LogApp.mensaje(llaves.toString());

				String parametroEncriptado = RSAUtilsPagosDigitales.EncryptOAE256(request.getParametro(), llaves.getResultado().getAccesoPublico());
				request.setParametro(parametroEncriptado);

				LogApp.mensaje(PropApp.getUrlGestionAsignaciones() +" : Request: "+request.toString());

				ResultadoApi respuesta = parametriaService.crearAsignacionParametro(request,llaves.getResultado().getIdAcceso(),token.getAccess_token(),usuario); 

				if(null !=  respuesta && !"ERROR".equals(respuesta.getCodigo())) {
					LogApp.mensaje(respuesta.toString());

					resultado = new ResponseApi(EstatusResponse.OK, respuesta.getMensaje());
				}
				else {			

					resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Llaves de Api de seguridad de divisas"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}
	
	/**
	 * Actualiza asignacion parametro.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response api
	 */
	public ResponseApi actualizaAsignacionParametro(ReqActAsignacion request,String usuario) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());
			ApiLlaves llaves = parametriaService.getLlavesDivisas(token.getAccess_token());

			if(null !=  llaves && null != llaves.getResultado()) {
				LogApp.mensaje(llaves.toString());

				String parametroEncriptado = RSAUtilsPagosDigitales.EncryptOAE256(request.getParametro(), llaves.getResultado().getAccesoPublico());
				request.setParametro(parametroEncriptado);

				LogApp.mensaje(PropApp.getUrlGestionAsignaciones() +" : Request: "+request.toString());

				ResultadoApi respuesta = parametriaService.actualizaAsignacionParametro(request,llaves.getResultado().getIdAcceso(),token.getAccess_token(),usuario); 

				if(null !=  respuesta && !"ERROR".equals(respuesta.getCodigo())) {
					LogApp.mensaje(respuesta.toString());

					resultado = new ResponseApi(EstatusResponse.OK, respuesta.getMensaje());
				}
				else {			

					resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Llaves de Api de seguridad de divisas"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}

	/**
	 * Elimina asignacion parametro.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response api
	 */
	public ResponseApi eliminaAsignacionParametro(ReqEliminacionAsignacion request, String usuario) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());

			LogApp.mensaje(PropApp.getUrlGestionAsignaciones() +" : Request: "+request.toString());

			ResultadoApi respuesta = parametriaService.eliminaAsignacionParametro(request,token.getAccess_token(),usuario); 

			if(null !=  respuesta && !"ERROR".equals(respuesta.getCodigo())) {
				LogApp.mensaje(respuesta.toString());

				resultado = new ResponseApi(EstatusResponse.OK, respuesta.getMensaje());
			}
			else {			

				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
			}

		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}
	
	
	/**
	 * Registra asignaciones parametros masivos.
	 *
	 * @param request the request
	 * @param usuario the usuario
	 * @return the response api
	 */
	public ResponseApi registraAsignacionesParametrosMasivos(ReqArchivo request, String usuario) {
		ResponseApi resultado;		

		TokenApigee token = parametriaService.getTokenApiAWSInterno();

		if(null !=  token && null != token.getAccess_token()) {
			LogApp.mensaje(token.toString());
			ApiLlaves llaves = parametriaService.getLlavesDivisas(token.getAccess_token());

			if(null !=  llaves && null != llaves.getResultado()) {
				LogApp.mensaje(llaves.toString());

				String archivoEncriptado = AESUtils.encryptAES(request.getArchivo(), llaves.getResultado().getAccesoSimetrico(),llaves.getResultado().getCodigoAutentificacionHash());
				request.setArchivo(archivoEncriptado);

				LogApp.mensaje(PropApp.getUrlGestionAsignacionesMasivas() +" : Request: "+request.toString());

				ResultadoApi respuesta = parametriaService.registraAsignacionesParametrosMasivos(request,llaves.getResultado().getIdAcceso(),token.getAccess_token(), usuario); 

				if(null !=  respuesta && !"ERROR".equals(respuesta.getCodigo())) {
					LogApp.mensaje(respuesta.toString());

					resultado = new ResponseApi(EstatusResponse.OK, respuesta.getMensaje());
				}
				else {			

					resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(respuesta.getMensaje()));
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Llaves de Api de seguridad de divisas"));
			}
		}
		else {
			resultado = new ResponseApi(EstatusResponse.NO_AUTORIZADO, Arrays.asList("Sin Token de Apigee"));
		}
		return resultado;
	}
}
