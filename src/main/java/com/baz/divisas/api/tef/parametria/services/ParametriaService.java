package com.baz.divisas.api.tef.parametria.services;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.apigee.dto.ResultadoApi;
import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.apigee.model.ApiLlaves;
import com.baz.divisas.api.tef.parametria.dto.ReqActAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqArchivo;
import com.baz.divisas.api.tef.parametria.dto.ReqAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaAsignaciones;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaParametros;
import com.baz.divisas.api.tef.parametria.dto.ReqDetalleParametro;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminacionAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ResConsultaAsignaciones;
import com.baz.divisas.api.tef.parametria.dto.ResConsultaParametros;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.util.LogApp;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

/**
 * The Class ParametriaService.
 */
public class ParametriaService {

	/** The client. */
	@Resource
	private WebClient client;
	
	/** The Constant AUTHORIZATION. */
	private static final String AUTHORIZATION = "Authorization";
	
	/**
	 * Constructor de la clase.
	 */
	public ParametriaService() {
		super();
		HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
		this.client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param client - WebClient
	 */
	public ParametriaService(WebClient client) {
		super();
		this.client = client;
	}
	
	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public WebClient getClient() {
		return client;
	}

	/**
	 * Sets the client.
	 *
	 * @param client the new client
	 */
	public void setClient(WebClient client) {
		this.client = client;
	}
	
	/**
	 * Gets the token api.
	 *
	 * @return the token api
	 */
	public TokenApigee getTokenApiAWSInterno() {
		TokenApigee response = new TokenApigee();
		
		String encoding = Base64
				.encodeBase64String((PropApp.getConsumerKeyParametros() + ":" + PropApp.getConsumerSecretParametros())
						.getBytes(StandardCharsets.UTF_8));		
		StringBuilder sb = new StringBuilder();
		sb.append("grant_type=" + PropApp.getGrantTypeApi());
		LogApp.mensaje(PropApp.getUrlTokenApiAWSINT() + " : Header :"+sb.toString());
		
		try {
			return client.post()
					.uri(PropApp.getUrlTokenApiAWSINT())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.header(AUTHORIZATION, "Basic " + encoding)
					.bodyValue(sb)
					.retrieve()
					.bodyToMono(TokenApigee.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al obtener el token de Apigee AWS INT");
			response.setMensaje(e.getMessage());
		}
				
		return response;
	}
	
	

	/**
	 * Gets the llaves divisas.
	 *
	 * @param accessToken the access token
	 * @return the llaves divisas
	 */
	public ApiLlaves getLlavesDivisas(String accessToken) {
		ApiLlaves response = new ApiLlaves();
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
 		LogApp.mensaje(PropApp.getUrlApiSeguridadDivisas() +" : Headers :"+ headers);
		
 		try {
			return client.get()
					.uri(PropApp.getUrlApiSeguridadDivisas())
					.headers(h -> h.addAll(headers))
					.retrieve()
					.bodyToMono(ApiLlaves.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al obtener las llaves de Divisas en AWS");
			response.setMensaje(e.getMessage());
		}

		return response;
	}

	/**
	 * Consulta parametros.
	 *
	 * @param request the request
	 * @param idAcceso the id acceso
	 * @param accessToken the access token
	 * @return the res consulta parametros
	 */
	public ResConsultaParametros consultaParametros(ReqBusquedaParametros request, String idAcceso,
			String accessToken) {
		ResConsultaParametros response = new ResConsultaParametros();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		
		try {
			return client.post()
					.uri(PropApp.getUrlConsultaParametros())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResConsultaParametros.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al consultar los parametros");
			response.setMensaje(e.getMessage());
		}
		
		return response;
	}

	/**
	 * Registra parametros.
	 *
	 * @param request the request
	 * @param idAcceso the id acceso
	 * @param accessToken the access token
	 * @param usuario the usuario
	 * @return the resultado api
	 */
	public ResultadoApi registraParametros(ReqDetalleParametro request, String idAcceso, String accessToken , String usuario) {
		ResultadoApi response = new ResultadoApi();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		headers.add("x-id-usuario",usuario);
		try {
			return client.post()
					.uri(PropApp.getUrlGestionParametros())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResultadoApi.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al registrar el parametro de sucursal");
			response.setMensaje(e.getMessage());
			response.setCodigo("ERROR");
		}
		
		return response;
	}
	
	
	/**
	 * Actualiza parametros.
	 *
	 * @param request the request
	 * @param idAcceso the id acceso
	 * @param accessToken the access token
	 * @param usuario the usuario
	 * @return the resultado api
	 */
	public ResultadoApi actualizaParametros(ReqDetalleParametro request, String idAcceso, String accessToken , String usuario) {
		ResultadoApi response = new ResultadoApi();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		headers.add("x-id-usuario",usuario);
		try {
			return client.put()
					.uri(PropApp.getUrlGestionParametros())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResultadoApi.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al actualizar el parametro de sucursal");
			response.setMensaje(e.getMessage());
			response.setCodigo("ERROR");
		}
		
		return response;
	}

	/**
	 * Registra parametros masivos.
	 *
	 * @param request the request
	 * @param idAcceso the id acceso
	 * @param accessToken the access token
	 * @param usuario the usuario
	 * @return the resultado api
	 */
	public ResultadoApi registraParametrosMasivos(ReqArchivo request, String idAcceso, String accessToken,
			String usuario) {
		ResultadoApi response = new ResultadoApi();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		headers.add("x-id-usuario",usuario);
		try {
			return client.post()
					.uri(PropApp.getUrlGestionParametrosMasivos())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResultadoApi.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al registrar los parametros de sucursal masivos");
			response.setMensaje(e.getMessage());
			response.setCodigo("ERROR");
		}
		
		return response;
	}

	/**
	 * Consulta asignaciones parametros.
	 *
	 * @param request the request
	 * @param idAcceso the id acceso
	 * @param accessToken the access token
	 * @return the res consulta asignaciones
	 */
	public ResConsultaAsignaciones consultaAsignacionesParametros(ReqBusquedaAsignaciones request, String idAcceso,
			String accessToken) {
		ResConsultaAsignaciones response = new ResConsultaAsignaciones();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		try {
			return client.post()
					.uri(PropApp.getUrlConsultaAsignacionesParametros())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResConsultaAsignaciones.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al consultar los parametros asignados de sucursal");
			response.setMensaje(e.getMessage());
			response.setCodigo("ERROR");
		}
		
		return response;
		
		
	}

	/**
	 * Crear asignacion parametro.
	 *
	 * @param request the request
	 * @param idAcceso the id acceso
	 * @param accessToken the access token
	 * @param usuario the usuario
	 * @return the res consulta asignaciones
	 */
	public ResultadoApi crearAsignacionParametro(ReqAsignacion request, String idAcceso,
			String accessToken,String usuario) {
		ResultadoApi response = new ResultadoApi();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		headers.add("x-id-usuario",usuario);
		try {
			return client.post()
					.uri(PropApp.getUrlGestionAsignaciones())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResultadoApi.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al registrar la asignación de los parametros");
			response.setMensaje(e.getMessage());
			response.setCodigo("ERROR");
		}
		
		return response;
		
		
		
	}

	/**
	 * Actualiza asignacion parametro.
	 *
	 * @param request the request
	 * @param idAcceso the id acceso
	 * @param accessToken the access token
	 * @param usuario the usuario
	 * @return the resultado api
	 */
	public ResultadoApi actualizaAsignacionParametro(ReqActAsignacion request, String idAcceso,
			String accessToken, String usuario) {
		ResultadoApi response = new ResultadoApi();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		headers.add("x-id-usuario",usuario);
		try {
			return client.put()
					.uri(PropApp.getUrlGestionAsignaciones())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResultadoApi.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al actualizar la asignación de los parametros");
			response.setMensaje(e.getMessage());
			response.setCodigo("ERROR");
		}
		
		return response;
	}

	/**
	 * Elimina asignacion parametro.
	 *
	 * @param request the request
	 * @param accessToken the access token
	 * @param usuario the usuario
	 * @return the resultado api
	 */
	public ResultadoApi eliminaAsignacionParametro(ReqEliminacionAsignacion request, String accessToken,
			String usuario) {
		ResultadoApi response = new ResultadoApi();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-usuario",usuario);
		try {
			return client.method(HttpMethod.DELETE)
					.uri(PropApp.getUrlGestionAsignaciones())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResultadoApi.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al eliminar la asignación de los parametros");
			response.setMensaje(e.getMessage());
			response.setCodigo("ERROR");
		}
		
		return response;
	}
	
	/**
	 * Registra asignaciones parametros masivos.
	 *
	 * @param request the request
	 * @param idAcceso the id acceso
	 * @param accessToken the access token
	 * @param usuario the usuario
	 * @return the resultado api
	 */
	public ResultadoApi registraAsignacionesParametrosMasivos(ReqArchivo request, String idAcceso, String accessToken,
			String usuario) {
		ResultadoApi response = new ResultadoApi();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		headers.add("x-id-usuario",usuario);
		try {
			return client.post()
					.uri(PropApp.getUrlGestionAsignacionesMasivas())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResultadoApi.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al registrar las asignaciones de los parámetros de sucursal masivos");
			response.setMensaje(e.getMessage());
			response.setCodigo("ERROR");
		}
		
		return response;
	}
}
