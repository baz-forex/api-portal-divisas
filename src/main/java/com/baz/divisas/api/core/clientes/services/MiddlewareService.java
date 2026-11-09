package com.baz.divisas.api.core.clientes.services;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.core.clientes.dto.ReqActualizacionCliente;
import com.baz.divisas.api.core.clientes.dto.ReqBusquedaClientes;
import com.baz.divisas.api.core.clientes.dto.ReqTipoCambio;
import com.baz.divisas.api.core.clientes.dto.RespBusquedaCliente;
import com.baz.divisas.api.core.clientes.dto.RespBusquedaClientes;
import com.baz.divisas.api.core.clientes.dto.RespMiddleware;
import com.baz.divisas.api.core.clientes.dto.RespTipoCambio;
import com.baz.divisas.api.core.clientes.dto.RespToken;
import com.baz.divisas.general.config.PropClientes;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

import reactor.core.publisher.Mono;


/**
 * Clase MiddlewareDao 
 * @author B813196, B86960
 * itoxtle, rvalencia
 */
@Service
public class MiddlewareService {


	private WebClient client;
	private String ClientId;
	private String usuario;
	private String clave;

	/**
	 * Constructor de la clase
	 * @param client - WebClient
	 */
	public MiddlewareService(WebClient client) {
		super();
		this.client = client;
	}

	public RespToken getToken() {
		ClientId = Constant.CLIENTID_MW;
		usuario = PropClientes.getAuthUsuario();
		clave = PropClientes.getAuthClave();
		return getTokenGeneric();
	}

	/**
	 * Busqueda Dinamica de Clientes Divisas
	 * @param request - Request de busqueda
	 * @return RespBusquedaClientes
	 */
	public RespBusquedaClientes find(ReqBusquedaClientes request) {

		RespBusquedaClientes response;

		try {

			response =  client.post()
					.uri(PropClientes.getUrlWsBusqDinamicaClientes())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(httpHeaders -> httpHeaders.set(Constant.CLIENTID, ClientId))										
					.body(Mono.just(request), ReqBusquedaClientes.class)
					.retrieve()
					.bodyToMono(RespBusquedaClientes.class).block();

		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al realizar la busqueda de clientes WS Middleware");
			response = new RespBusquedaClientes("0", "0", e.getMessage());
		}

		return response;

	}

	/**
	 * Busqueda por Id de Clientes Divisas
	 * @param idCliente - Id Cliente
	 * @return RespBusquedaClientes
	 */
	public RespBusquedaCliente findById(String idCliente) {

		RespBusquedaCliente response;

		try {

			response =  client.get()
					.uri(uriBuilder -> uriBuilder
							.path(PropClientes.getUrlWsBusqIdCliente())
							.queryParam("id_cliente", idCliente)
							.build())
					.headers(httpHeaders -> httpHeaders.set(Constant.CLIENTID, ClientId))										
					.retrieve()
					.bodyToMono(RespBusquedaCliente.class).block();

		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al realizar la consulta de cliente WS Middleware");
			response = new RespBusquedaCliente("0", "0", e.getMessage());
		}

		return response;

	}

	/**
	 * Actualizacion de cliente
	 * @param request - Request de Actualizacion
	 * @return RespMiddleware
	 */
	public RespMiddleware actualizaCliente(ReqActualizacionCliente request) {

		RespMiddleware response;

		try {

			response =  client.post()
					.uri(PropClientes.getUrlWsActualizaClientes())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(httpHeaders -> httpHeaders.set(Constant.CLIENTID, ClientId))										
					.body(Mono.just(request), ReqActualizacionCliente.class)
					.retrieve()
					.bodyToMono(RespMiddleware.class).block();

		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al actualizar al cliente WS Middleware");
			response = new RespToken("0", "0", e.getMessage());
		}

		return response;

	}

	public RespToken getTokenAdmin() {

		ClientId = Constant.CLIENTID_MWADMIN;
		usuario = PropClientes.getAuthUsuarioAdmin();
		clave = PropClientes.getAuthClaveAdmin();
		return getTokenGeneric();

	}
	
	private RespToken getTokenGeneric() {

		RespToken response;

		try {

			response = client.post()
					.uri(PropClientes.getUrlWsAuthToken())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.headers(httpHeaders -> httpHeaders.set(Constant.CLIENTID, ClientId))										
					.body(BodyInserters.fromFormData("usuario", usuario)
							.with("clave", clave))
					.retrieve()
					.bodyToMono(RespToken.class).block();

		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al obtener el token WS Middleware");
			response = new RespToken("0", "0", "");
		}

		return response;

	}

	/**
	 * Consulta tipo cambio.
	 *
	 * @param request the request
	 * @return the resp tipo cambio
	 */
	public RespTipoCambio consultaTipoCambio(ReqTipoCambio request) {

		RespTipoCambio response = new RespTipoCambio();

		try {

			response =  client.post()
					.uri(PropClientes.getUrlWsTipoCambio())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(httpHeaders -> httpHeaders.set(Constant.CLIENTID, ClientId))										
					.body(Mono.just(request), ReqTipoCambio.class)
					.retrieve()
					.bodyToMono(RespTipoCambio.class).block();

		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al actualizar al cliente WS Middleware");
			response.setCodigo("0");
			response.setResultado("0");
			response.setMensaje(e.getMessage());
		}

		return response;

	}

	public WebClient getClient() {
		return client;
	}

	public void setClient(WebClient client) {
		this.client = client;
	}

}
