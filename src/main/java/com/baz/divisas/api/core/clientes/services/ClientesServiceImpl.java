package com.baz.divisas.api.core.clientes.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bson.Document;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.core.clientes.dao.ClientesDao;
import com.baz.divisas.api.core.clientes.dto.ReqActualizacionCliente;
import com.baz.divisas.api.core.clientes.dto.ReqBloqueoClientes;
import com.baz.divisas.api.core.clientes.dto.ReqBusquedaClientes;
import com.baz.divisas.api.core.clientes.dto.ReqClientesAgrupados;
import com.baz.divisas.api.core.clientes.dto.RespBusquedaAgrupados;
import com.baz.divisas.api.core.clientes.dto.RespBusquedaCliente;
import com.baz.divisas.api.core.clientes.dto.RespBusquedaClientes;
import com.baz.divisas.api.core.clientes.dto.RespMiddleware;
import com.baz.divisas.api.core.clientes.dto.RespToken;
import com.baz.divisas.api.core.clientes.model.BitacoraActualizacion;
import com.baz.divisas.api.core.clientes.model.CampoModificado;
import com.baz.divisas.api.core.clientes.model.ClienteDetalle;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.config.PropClientes;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.model.EntCorreo;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.MailService;
import com.baz.divisas.general.util.SecurityUtils;

/**
 * Servicio de Clientes.
 *
 * @author B813196
 * itoxtle
 */
@Service
public class ClientesServiceImpl {

	/** The middleware service. */
	private MiddlewareService middlewareService;
	
	/** The security service. */
	private SecurityUtils securityService;
	
	/** The mail service. */
	private MailService mailService;

	/** The clientes dao. */
	private ClientesDao clientesDao;


	/**
	 * Constructor de la clase.
	 *
	 * @param middlewareService - Middleware Service
	 * @param securityService the security service
	 * @param mailService the mail service
	 * @param clientesDao - DAO Clientes
	 */
	@Autowired
	public ClientesServiceImpl(MiddlewareService middlewareService, SecurityUtils securityService, 
			MailService mailService, ClientesDao clientesDao) {
		super();
		this.middlewareService = middlewareService;
		this.securityService = securityService;
		this.mailService = mailService;
		this.clientesDao = clientesDao;
	}


	/**
	 * Metodo de busqueda de clientes
	 * 
	 * Realiza la busqueda de clientes por tipo:
	 * 
	 * - (1) Numero de cliente divisas
	 * - (2) Tipo y folio de identificacion
	 * - (3) Nombre y fecha de nacimiento .
	 *
	 * @param request - Request busqueda de clientes
	 * @return - ResponseApi
	 */
	public ResponseApi busquedaClientes(ReqBusquedaClientes request) {

		ResponseApi resultado;

		middlewareService.setClient(WebClient.builder()
				.baseUrl(PropClientes.getUrlMiddleware())
				.build());

		RespToken respToken = middlewareService.getToken();


		middlewareService.setClient(WebClient.builder()
				.baseUrl(PropClientes.getUrlMiddleware())
				.defaultHeader(HttpHeaders.AUTHORIZATION, Constant.BEARER + respToken.getToken())
				.build());

		RespBusquedaClientes respBusqClientes = middlewareService.find(request);

		if("1".equals(respBusqClientes.getResultado())) {

			if("1".equals(respBusqClientes.getCodigo()) && !respBusqClientes.getClientes().isEmpty()){
				resultado = new ResponseApi(EstatusResponse.OK, respBusqClientes.getClientes());
			}
			else {
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList(Constant.SIN_INFORMACION));
			}

		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Ocurrió un error al realizar la busqueda de clientes", respBusqClientes.getMensaje()));
		}

		return resultado;
	}


	/**
	 * Metodo de consulta de cliente por id.
	 *
	 * @param idCliente - Id de Cliente
	 * @return ResponseApi
	 */
	public ResponseApi busquedaClienteById(String idCliente) {

		ResponseApi resultado;

		middlewareService.setClient(WebClient.builder()
				.baseUrl(PropClientes.getUrlMiddleware())
				.build());

		RespToken respToken = middlewareService.getToken();


		middlewareService.setClient(WebClient.builder()
				.baseUrl(PropClientes.getUrlMiddleware())
				.defaultHeader(HttpHeaders.AUTHORIZATION, Constant.BEARER + respToken.getToken())
				.build());

		RespBusquedaCliente respBusqClientes = middlewareService.findById(idCliente);

		if("1".equals(respBusqClientes.getResultado())) {

			if("1".equals(respBusqClientes.getCodigo())){
				resultado = new ResponseApi(EstatusResponse.OK, respBusqClientes.getCliente());
			}
			else {
				resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
						Arrays.asList(Constant.SIN_INFORMACION));
			}

		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Ocurrió un error al realizar la consulta del cliente", respBusqClientes.getMensaje()));
		}

		return resultado;
	}


	/**
	 * Metodo de consulta de Bitacora de Actualizaciones del Cliente.
	 *
	 * @param idCliente - Id de Cliente
	 * @return Registros de la bitacora
	 */
	public ResponseApi consultaBitacoraCliente(String idCliente) {

		ResponseApi resultado;

		List<BitacoraActualizacion> bitacora = clientesDao.findBitacoraActCliente(idCliente);

		if(!bitacora.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, bitacora);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}


	/**
	 * Metodo de actualizacion de cliente.
	 *
	 * @param cliente - Cliente a actualizar
	 * @return - ResponseApi
	 */
	public ResponseApi actualizaCliente(ClienteDetalle cliente) {

		ResponseApi resultado;

		middlewareService.setClient(WebClient.builder()
				.baseUrl(PropClientes.getUrlMiddleware())
				.build());

		RespToken respToken = middlewareService.getToken();


		middlewareService.setClient(WebClient.builder()
				.baseUrl(PropClientes.getUrlMiddleware())
				.defaultHeader(HttpHeaders.AUTHORIZATION, Constant.BEARER + respToken.getToken())
				.build());

		RespBusquedaCliente respBusqClientes = middlewareService.findById(cliente.getId());

		if("1".equals(respBusqClientes.getResultado()) && "1".equals(respBusqClientes.getCodigo())) {

			ReqActualizacionCliente request = buidReqActualizaCliente(respBusqClientes.getCliente(), cliente);

			if(request != null) {

				RespMiddleware response = middlewareService.actualizaCliente(request);

				if("1".equals(response.getResultado())) {
					resultado = new ResponseApi(EstatusResponse.OK);
				}
				else {
					resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO, response.getMensaje());
				}				

			}
			else {

				resultado = new ResponseApi(EstatusResponse.OK, 
						"No se encontraron cambios en la información del cliente.");
			}

		}
		else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("El id de cliente no existe."));
		}

		return resultado;
	}


	/**
	 * Buid req actualiza cliente.
	 *
	 * @param clienteActual the cliente actual
	 * @param clienteActualizacion the cliente actualizacion
	 * @return the req actualizacion cliente
	 */
	private static ReqActualizacionCliente buidReqActualizaCliente(ClienteDetalle clienteActual, 
			ClienteDetalle clienteActualizacion) {

		if (clienteActual == null || clienteActualizacion == null) {
			return null;
		}

		ReqActualizacionCliente request = null;
		List<CampoModificado> camposModificados = new ArrayList<>();

		HashMap<String, String> mapCampos = new HashMap<>();
		mapCampos.put("nombre", "nombre");
		mapCampos.put("apellidoPaterno", "apellido_paterno");
		mapCampos.put("apellidoMaterno", "apellido_materno");
		mapCampos.put("fechaNacimiento", "fecha_nacimiento");
		mapCampos.put("calle", "calle");
		mapCampos.put("colonia", "colonia");
		mapCampos.put("codigoPostal", "codigo_postal");
		mapCampos.put("poblacion", "poblacion");
		mapCampos.put("estado", "estado");
		mapCampos.put("idNacionalidad", "id_nacionalidad");
		mapCampos.put("nacionalidad", "nacionalidad");

		BeanWrapper wrapperActual = new BeanWrapperImpl(clienteActual);
		BeanWrapper wrapperActualizacion = new BeanWrapperImpl(clienteActualizacion);

		for (Entry<String, String> entry : mapCampos.entrySet()) {

			try {

				String valorActual = (String) wrapperActual.getPropertyValue(entry.getKey());
				String valorActualizado = (String) wrapperActualizacion.getPropertyValue(entry.getKey());

				if(valorActual != null && !valorActual.equals(valorActualizado)) {
					CampoModificado campo = new CampoModificado();
					campo.setCampo(entry.getValue());
					campo.setValorAnterior(valorActual);
					campo.setValorNuevo(valorActualizado);
					camposModificados.add(campo);
				}

			} 
			catch (Exception e) {
				LogApp.excepcion(e, "Ocurrio un error al obtener datos modificados del cliente");
			}

		}

		if(!camposModificados.isEmpty()) {

			request = new ReqActualizacionCliente();
			request.setUsuario("000000");
			request.setIp("");
			request.setMotivoEdicion(clienteActualizacion.getMotivoEdicion());
			request.setIdCliente(clienteActualizacion.getId());
			request.setCamposModificados(camposModificados);			
		}

		return request;

	}


	/**
	 * Consulta agrupado clientes.
	 *
	 * @param idCliente - Id de cliente
	 * @param pagina - Número de página
	 * @param registrosPorPagina - Número de registros por página
	 * @return ResponseApi
	 */
	public ResponseApi consultaAgrupadoClientes(String idCliente, int pagina, int registrosPorPagina) {

		ResponseApi response;
		long totalRegistros = 0;

		RespBusquedaAgrupados responsePagina;
		
		List<Document> agrupadoClientes;

		if(idCliente.isEmpty()) {
			totalRegistros = clientesDao.countAgrupados();
		}
		else {
			totalRegistros = clientesDao.countAgrupadosCliente(idCliente);
		}
		
		agrupadoClientes = clientesDao.consultaClientesAgrupados(idCliente, pagina, registrosPorPagina);
		
		if (!agrupadoClientes.isEmpty()) {
			
			responsePagina = new RespBusquedaAgrupados(pagina, registrosPorPagina, totalRegistros, agrupadoClientes); 

			response = new ResponseApi(EstatusResponse.OK, responsePagina);
			
		} 
		else {
			response = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return response;

	}

	
	/**
	 * Registra agrupado clientes.
	 *
	 * @param request the request
	 * @return ResponseApi
	 */
	public ResponseApi registraAgrupadoClientes(ReqClientesAgrupados request) {

		ResponseApi response;

		Document clientesDuplicados = clientesDao.validaClientesAgrupados(request.getClientes());

		if (clientesDuplicados != null) {
			clientesDuplicados.put("detalle", "Los clientes ya pertenecen a un grupo.");
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, clientesDuplicados);
		} 
		else {

			boolean resultado = clientesDao.registraClientesAgrupados(request.getClientes());

			if (!resultado) {
				response = new ResponseApi(EstatusResponse.ERROR_INTERNO, 
						Arrays.asList("NO fue posible registrar el agrupado de clientes"));
			} 
			else {

				// Envio de Correo
				String mensajeCorreo = "Clientes: " + request.getClientes().toString();

				String usuario = securityService.getUserSesion().getIdUserSesion();

				EntCorreo correo = new EntCorreo(0, PropApp.getDestinatariosNotificaciones(),
						"Clientes Agrupados Registrados por el usuario: " + usuario, mensajeCorreo);

				new Thread(() ->  mailService.enviaMail(correo) ).start();

				response = new ResponseApi(EstatusResponse.OK);
			}

		}

		return response;

	}


	/**
	 * Actualiza agrupado clientes.
	 *
	 * @param request the request
	 * @return ResponseApi
	 */
	public ResponseApi actualizaAgrupadoClientes(ReqClientesAgrupados request) {

		ResponseApi response;

		Document clientesDuplicados = clientesDao.validaClientesAgrupados(request.getIdGrupo(), request.getClientes());

		if (clientesDuplicados != null) {
			clientesDuplicados.put("detalle", "Los clientes ya pertenecen a un grupo.");
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, clientesDuplicados);
		} 
		else {

			boolean resultado = clientesDao.actualizaClientesAgrupados(request.getIdGrupo(), request.getClientes());

			if (!resultado) {
				response = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible actualizar el agrupado de clientes"));
			} 
			else {

				// Envio de Correo
				String mensajeCorreo = "Clientes: " + request.getClientes().toString();

				String usuario = securityService.getUserSesion().getIdUserSesion();

				EntCorreo correo = new EntCorreo(0, PropApp.getDestinatariosNotificaciones(),
						"Clientes Agrupados Actualizados por el usuario: " + usuario, mensajeCorreo);

				new Thread(() ->  mailService.enviaMail(correo) ).start();

				response = new ResponseApi(EstatusResponse.OK);
			}
		}

		return response;

	}

	
	/**
	 * Elimina agrupado clientes.
	 *
	 * @param idGrupo - Id del grupo a eliminar
	 * @return ResponseApi
	 */
	public ResponseApi eliminaAgrupadoClientes(String idGrupo) {

		ResponseApi response;

		Document grupoEliminado = clientesDao.eliminaClientesAgrupados(idGrupo);

		if (grupoEliminado == null) {
			response = new  ResponseApi(EstatusResponse.ERROR_INTERNO, "No fue posible eliminar el agrupado de clientes");
		}
		else {
			
			// Envio de Correo
			String mensajeCorreo = "Grupo eliminado: " + grupoEliminado.get("clientes");

			String usuario = securityService.getUserSesion().getIdUserSesion();

			EntCorreo correo = new EntCorreo(0, PropApp.getDestinatariosNotificaciones(),
					"Clientes Agrupados Eliminados por el usuario: " + usuario, mensajeCorreo);

			new Thread(() ->  mailService.enviaMail(correo) ).start();

			response = new ResponseApi(EstatusResponse.OK);
		}

		return response;

	}

	
	/**
	 * Consulta bloqueo clientes.
	 *
	 * @param tipoBloqueo - Tipo de Bloqueo
	 * @return ResponseApi
	 */
	public ResponseApi consultaBloqueoClientes(String tipoBloqueo) {

		ResponseApi response;
		
		List<ReqBloqueoClientes> bloqueosClientes = clientesDao.consultaBloqueosOperativos(tipoBloqueo);
		
		if (!bloqueosClientes.isEmpty()) {

			response = new ResponseApi(EstatusResponse.OK, bloqueosClientes);
			
		} 
		else {
			response = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return response;
	}
	
	
	/**
	 * Registra bloqueo de clientes.
	 *
	 * @param request - ReqBloqueoClientes
	 * @return ResponseApi
	 */
	public ResponseApi registraBloqueoClientes(ReqBloqueoClientes request) {
		
		ResponseApi response = new ResponseApi(EstatusResponse.OK);
				
		Document datosBloqueo = new Document();

		String descTipoBloqueo = "";
		switch(request.getTipo()) {
		case "1":
        descTipoBloqueo = "por cliente";
	    
        datosBloqueo.append("cliente_divisas", request.getCliente());
        
        break;
		case "2":
        descTipoBloqueo = "por tipo y folio de identificación";

        datosBloqueo.append("tipo_identificacion", request.getTipoIdentificacion());
        datosBloqueo.append("folio_identificacion", request.getFolioIdentificacion());

        break;
		case "3":
        descTipoBloqueo = "por cliente, tipo y folio de identificación";
        
        datosBloqueo.append("cliente_divisas", request.getCliente());
        datosBloqueo.append("tipo_identificacion", request.getTipoIdentificacion());
        datosBloqueo.append("folio_identificacion", request.getFolioIdentificacion());
        		
        break;
        default:
        break;
		}

		Document bloqueo = clientesDao.buscaBloqueoOperativo(request.getTipo(), datosBloqueo);
		
		if(bloqueo == null) {
			
			String descBloqueo = "Bloqueo "+ descTipoBloqueo +" registrado";
			
			boolean resultado = clientesDao.registraBloqueoOperativo(request.getTipo(), datosBloqueo, descBloqueo);
			
			if(!resultado) {

				response = new  ResponseApi(EstatusResponse.ERROR_INTERNO, "No fue posible registrar el bloqueo.");
			}
			else {
				
				// Envio de Correo
				String usuario = securityService.getUserSesion().getIdUserSesion();

				EntCorreo correo = new EntCorreo(0, PropApp.getDestinatariosNotificaciones(),
						descBloqueo +" por el usuario: "+ usuario, "Datos Bloqueados:\n"+ request.toString());

				new Thread(() ->  mailService.enviaMail(correo) ).start();
			}
			
		}
		else {
			response = new  ResponseApi(EstatusResponse.PETICION_INCORRECTA, "Ya existe un bloqueo con los datos enviados.");
		}

		return response;
	
	}
	
	
	/**
	 * Elimina bloqueo clientes.
	 *
	 * @param idBloqueo - Id de Bloqueo
	 * @return ResponseApi
	 */
	public ResponseApi eliminaBloqueoClientes(String idBloqueo) {
		
		ResponseApi response = new ResponseApi(EstatusResponse.OK);

		ReqBloqueoClientes bloqueo = clientesDao.buscaBloqueoOperativoPorId(idBloqueo);
		
		if(bloqueo != null) {
			
			String descTipoBloqueo = "";
			switch(bloqueo.getTipo()) {
			case "1":
	        descTipoBloqueo = "por cliente";		    	       
	        break;
			case "2":
	        descTipoBloqueo = "por tipo y folio de identificación";
	        break;
			case "3":
	        descTipoBloqueo = "por cliente, tipo y folio de identificación";	        	
	        break;
	        default:
	        break;
			}
			
			String descBloqueo = "Bloqueo "+ descTipoBloqueo +" eliminado";
			
			boolean resultado = clientesDao.eliminaBloqueoOperativo(idBloqueo, descBloqueo);
			
			if(!resultado) {

				response = new  ResponseApi(EstatusResponse.ERROR_INTERNO, "No fue posible eliminar el bloqueo.");
			}
			else {
				
				// Envio de Correo
				String usuario = securityService.getUserSesion().getIdUserSesion();

				EntCorreo correo = new EntCorreo(0, PropApp.getDestinatariosNotificaciones(),
						descBloqueo +" por el usuario: "+ usuario, "Datos Bloqueados:\n"+ bloqueo.toString());

				new Thread(() ->  mailService.enviaMail(correo) ).start();
			}
			
		}
		else {
			response = new  ResponseApi(EstatusResponse.PETICION_INCORRECTA, 
					"No se encontro informacion con el id de bloqueo enviado.");
		}

		return response;
	
	}


	/**
	 * Consulta carga historica alto riesgo.
	 *
	 * @return the response api
	 */
	public ResponseApi consultaCargaHistoricaAltoRiesgo() {
		ResponseApi response;

		List<HistoricoAltoRiesgo> historico = clientesDao.consultaCargaHistoricaAltoRiesgo();

		if (!historico.isEmpty()) {

			response = new ResponseApi(EstatusResponse.OK, historico);

		} 
		else {
			response = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return response;
	}
	
}
