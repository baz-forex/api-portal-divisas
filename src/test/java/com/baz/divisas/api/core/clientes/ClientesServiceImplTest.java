package com.baz.divisas.api.core.clientes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baz.divisas.api.auth.model.AuthClient;
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
import com.baz.divisas.api.core.clientes.model.Bandera;
import com.baz.divisas.api.core.clientes.model.BanderasControl;
import com.baz.divisas.api.core.clientes.model.BitacoraActualizacion;
import com.baz.divisas.api.core.clientes.model.CampoModificado;
import com.baz.divisas.api.core.clientes.model.Cliente;
import com.baz.divisas.api.core.clientes.model.ClienteDetalle;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.baz.divisas.api.core.clientes.model.Identificacion;
import com.baz.divisas.api.core.clientes.services.ClientesServiceImpl;
import com.baz.divisas.api.core.clientes.services.MiddlewareService;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.EntCorreo;
import com.baz.divisas.general.util.MailService;
import com.baz.divisas.general.util.SecurityUtils;
import com.baz.divisas.security.services.UserDetailsImpl;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientesServiceImplTest {

	@InjectMocks
	private ClientesServiceImpl clientesService;
	@Mock
	private MiddlewareService middlewareService;
	@Mock
	private SecurityUtils securityService;
	@Mock
	private MailService mailService;
	@Mock
	private ClientesDao clientesDao;
	@Mock
	private UserDetailsImpl user;
	@Mock
	private Authentication authentication;
	@Mock
	private SecurityContext securityContext;
	
	@Test
	void testBusquedaClientes() {
		
		
		when(middlewareService.getToken()).thenReturn(new RespToken("1","1","1"));
		when(middlewareService.find(isA(ReqBusquedaClientes.class))).thenReturn(new RespBusquedaClientes());
		ResponseApi response = clientesService.busquedaClientes(new ReqBusquedaClientes());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		RespBusquedaClientes respBusqueda = new RespBusquedaClientes("1","1","");
		respBusqueda.setResultado("1");
		respBusqueda.setMensaje("");
		respBusqueda.toString();


		when(middlewareService.find(isA(ReqBusquedaClientes.class))).thenReturn(respBusqueda);
		response = clientesService.busquedaClientes(new ReqBusquedaClientes());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		Identificacion identificacion = new Identificacion();
		identificacion.setFolio("");
		identificacion.getFolio();
		identificacion.setTipo("");
		identificacion.getTipo();
		
		Cliente cliente = new Cliente();
		cliente.setNombre("");
		cliente.getNombre();
		cliente.setApellidoMaterno("");
		cliente.getApellidoMaterno();
		cliente.setApellidoPaterno("");
		cliente.getApellidoPaterno();
		cliente.setFechaNacimiento("");
		cliente.getFechaNacimiento();
		cliente.setId("");
		cliente.getId();	
		cliente.setCalle("");
		cliente.getCalle();
		cliente.setCodigoPostal("");
		cliente.getCodigoPostal();
		cliente.setColonia("");
		cliente.getColonia();
		cliente.setPoblacion("");
		cliente.getPoblacion();
		cliente.setEstado("");
		cliente.getEstado();
		cliente.setGenero("");
		cliente.getGenero();
		cliente.setIdGenero("");
		cliente.getIdGenero();
		cliente.setNumeroExterior("");
		cliente.getNumeroExterior();
		cliente.setNumeroInterior("");
		cliente.getNumeroInterior();

		cliente.setIdentificaciones(Arrays.asList(identificacion));
		cliente.getIdentificaciones();
		
		respBusqueda.setClientes(Arrays.asList(cliente));
		
		when(middlewareService.find(isA(ReqBusquedaClientes.class))).thenReturn(respBusqueda);
		response = clientesService.busquedaClientes(new ReqBusquedaClientes());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		respBusqueda.setCodigo("0");

		when(middlewareService.find(isA(ReqBusquedaClientes.class))).thenReturn(respBusqueda);
		response = clientesService.busquedaClientes(new ReqBusquedaClientes());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	void testBusquedaClienteById() {
				
		Bandera bandera = new Bandera();
		bandera.setValor("");
		bandera.getValor();
		bandera.setFechaActualizacion("");
		bandera.getFechaActualizacion();
		bandera.setFechaPrimerAviso("");
		bandera.getFechaPrimerAviso();
		
		BanderasControl banderasControl = new BanderasControl();
		banderasControl.setCuestionarioPeps(bandera);
		banderasControl.getCuestionarioPeps();
		banderasControl.setCuestionarioPld(bandera);
		banderasControl.getCuestionarioPld();
		banderasControl.setDigitalizacion(bandera);
		banderasControl.getDigitalizacion();
		banderasControl.setExpedienteActualizado(bandera);
		banderasControl.getExpedienteActualizado();
		
		ClienteDetalle cliente = new ClienteDetalle();

		cliente.setBanderasControl(banderasControl);
		cliente.getBanderasControl();
		cliente.setIdGiro("");
		cliente.getIdGiro();
		cliente.setGiro("");
		cliente.getGiro();
		cliente.setIdActividad("");
		cliente.getIdActividad();
		cliente.setActividad("");
		cliente.getActividad();
		cliente.setIdNacionalidad("");
		cliente.getIdNacionalidad();
		cliente.setNacionalidad("");
		cliente.getNacionalidad();
		cliente.setIdPaisNacimiento("");
		cliente.getIdPaisNacimiento();
		cliente.setPaisNacimiento("");
		cliente.getPaisNacimiento();
		cliente.setFechaRegistro("");
		cliente.getFechaRegistro();
		cliente.setFechaActualizacion("");
		cliente.getFechaActualizacion();
		cliente.setSucursalRegistro("");
		cliente.getSucursalRegistro();
		cliente.setUsuarioRegistro("");
		cliente.getUsuarioRegistro();
		cliente.setMotivoEdicion("");
		cliente.getMotivoEdicion();
		
		when(middlewareService.getToken()).thenReturn(new RespToken("1","1","1"));
		when(middlewareService.findById(isA(String.class))).thenReturn(new RespBusquedaCliente());
		ResponseApi response = clientesService.busquedaClienteById("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		RespBusquedaCliente respBusqueda = new RespBusquedaCliente("1","1","");
		respBusqueda.setCliente(cliente);

		when(middlewareService.findById(isA(String.class))).thenReturn(respBusqueda);
		response = clientesService.busquedaClienteById("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		respBusqueda.setCodigo("0");
		
		when(middlewareService.findById(isA(String.class))).thenReturn(respBusqueda);
		response = clientesService.busquedaClienteById("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

	}
	
	@Test
	void testConsultaBitacoraCliente() {
				
		List<BitacoraActualizacion> listBitacora = new ArrayList<BitacoraActualizacion>();
		
		when(clientesDao.findBitacoraActCliente(isA(String.class))).thenReturn(listBitacora);
		ResponseApi response = clientesService.consultaBitacoraCliente("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		BitacoraActualizacion bitacora = new BitacoraActualizacion();
		bitacora.setIdCarga(new ObjectId());
		bitacora.getIdCarga();
		bitacora.setIdCliente("");
		bitacora.getIdCliente();
		bitacora.setCampoModificado("");
		bitacora.getCampoModificado();
		bitacora.setFechaRegistro("");
		bitacora.getFechaRegistro();
		bitacora.setMotivo("");
		bitacora.getMotivo();
		bitacora.setOrigen("");
		bitacora.getOrigen();
		bitacora.setUsuario("");
		bitacora.getUsuario();
		bitacora.setValorActual("");
		bitacora.getValorActual();
		bitacora.setValorAnterior("");
		bitacora.getValorAnterior();
		bitacora.setEstatus("");
		bitacora.getEstatus();
		CampoModificado campoModificado = new CampoModificado();
		bitacora.setCamposModificados(Arrays.asList(campoModificado));
		bitacora.getCamposModificados();
		
		listBitacora.add(new BitacoraActualizacion());
		
		when(clientesDao.findBitacoraActCliente(isA(String.class))).thenReturn(listBitacora);
		response = clientesService.consultaBitacoraCliente("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testActualizaCliente() {

		ClienteDetalle cliente = new ClienteDetalle();
		cliente.setId("");
		cliente.setNombre("");
		cliente.setApellidoMaterno("");
		cliente.setApellidoPaterno("");
		cliente.setFechaNacimiento("");
		cliente.setCalle("");
		cliente.setCodigoPostal("");
		cliente.setColonia("");
		cliente.setPoblacion("");
		cliente.setEstado("");
		cliente.setIdNacionalidad("");
		cliente.setNacionalidad("");
		cliente.setMotivoEdicion("");
		
		ClienteDetalle clienteAct = new ClienteDetalle();
		clienteAct.setId("");
		clienteAct.setNombre("otro");
		clienteAct.setApellidoMaterno("");
		clienteAct.setApellidoPaterno("");
		clienteAct.setFechaNacimiento("");
		clienteAct.setCalle("");
		clienteAct.setCodigoPostal("");
		clienteAct.setColonia("");
		clienteAct.setPoblacion("");
		clienteAct.setEstado("");
		clienteAct.setIdNacionalidad("");
		clienteAct.setNacionalidad("");
		clienteAct.setMotivoEdicion("");

		when(middlewareService.getToken()).thenReturn(new RespToken("1","1","1"));
		when(middlewareService.findById(isA(String.class))).thenReturn(new RespBusquedaCliente("0", "0", "") );
		ResponseApi response = clientesService.actualizaCliente(cliente);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		when(middlewareService.findById(isA(String.class))).thenReturn(new RespBusquedaCliente("1", "0", "") );
		response = clientesService.actualizaCliente(cliente);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		when(middlewareService.findById(isA(String.class))).thenReturn(new RespBusquedaCliente("1", "1", "") );
		response = clientesService.actualizaCliente(cliente);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		RespBusquedaCliente respBusqCliente = new RespBusquedaCliente("1", "1", "");
		respBusqCliente.setCliente(cliente);
		when(middlewareService.findById(isA(String.class))).thenReturn(respBusqCliente);
		when(middlewareService.actualizaCliente(isA(ReqActualizacionCliente.class))).thenReturn(new RespMiddleware("0", "0", "") );
		response = clientesService.actualizaCliente(cliente);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
		
		when(middlewareService.findById(isA(String.class))).thenReturn(respBusqCliente);
		when(middlewareService.actualizaCliente(isA(ReqActualizacionCliente.class))).thenReturn(new RespMiddleware("0", "0", "") );
		response = clientesService.actualizaCliente(clienteAct);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		CampoModificado campo = new CampoModificado();
		campo.setCampo("");
		campo.getCampo();
		campo.setValorAnterior("");
		campo.getValorAnterior();
		campo.setValorNuevo("");
		campo.getValorNuevo();
		
		ReqActualizacionCliente reqAct = new ReqActualizacionCliente();
		reqAct.setIdCliente("");
		reqAct.getIdCliente();
		reqAct.setIdNegocio("");
		reqAct.getIdNegocio();
		reqAct.setIp("");
		reqAct.getIp();
		reqAct.setMotivoEdicion("");
		reqAct.getMotivoEdicion();
		reqAct.setUsuario("");
		reqAct.getUsuario();
		reqAct.setCamposModificados(Arrays.asList(campo));
		reqAct.getCamposModificados();
		
		when(middlewareService.findById(isA(String.class))).thenReturn(respBusqCliente);
		when(middlewareService.actualizaCliente(isA(ReqActualizacionCliente.class))).thenReturn(new RespMiddleware("1", "1", "") );
		response = clientesService.actualizaCliente(clienteAct);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testConsultaAgrupadoClientes() {
				
		RespBusquedaAgrupados respBusqAgrupados = new RespBusquedaAgrupados(0, 0, 0, Arrays.asList());
		respBusqAgrupados.setAgrupados(Arrays.asList());
		respBusqAgrupados.getAgrupados();
	
		//////////////
		when(clientesDao.countAgrupados()).thenReturn(1L);
		when(clientesDao.consultaClientesAgrupados(isA(String.class), isA(int.class), isA(int.class))).thenReturn(Arrays.asList(new Document()));
		
		ResponseApi response = clientesService.consultaAgrupadoClientes("", 1, 1);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////
		
		when(clientesDao.countAgrupadosCliente(isA(String.class))).thenReturn(1L);
		when(clientesDao.consultaClientesAgrupados(isA(String.class), isA(int.class), isA(int.class))).thenReturn(Arrays.asList());
		
		response = clientesService.consultaAgrupadoClientes("123456", 1, 1);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	void testRegistraAgrupadoClientes() {
					
		when(clientesDao.validaClientesAgrupados(any())).thenReturn(new Document());
		
		ReqClientesAgrupados request = new ReqClientesAgrupados();
		request.setClientes(Arrays.asList(""));
		ResponseApi response = clientesService.registraAgrupadoClientes(request);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//////////////
		when(clientesDao.validaClientesAgrupados(any())).thenReturn(null);
		when(clientesDao.registraClientesAgrupados(any())).thenReturn(false);
		
		response = clientesService.registraAgrupadoClientes(request);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//////////////
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		user.setIdUserSesion("123");
					
		when(clientesDao.registraClientesAgrupados(any())).thenReturn(true);
		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(mailService).enviaMail(isA(EntCorreo.class));		
		
		response = clientesService.registraAgrupadoClientes(request);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaAgrupadoClientes() {
					
		when(clientesDao.validaClientesAgrupados(isA(String.class), any())).thenReturn(new Document());
		
		ReqClientesAgrupados request = new ReqClientesAgrupados();
		request.setIdGrupo("");
		request.setClientes(Arrays.asList(""));
		ResponseApi response = clientesService.actualizaAgrupadoClientes(request);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//////////////
		when(clientesDao.validaClientesAgrupados(isA(String.class), any())).thenReturn(null);
		when(clientesDao.actualizaClientesAgrupados(isA(String.class), any())).thenReturn(false);
		
		response = clientesService.actualizaAgrupadoClientes(request);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//////////////
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		user.setIdUserSesion("123");
					
		when(clientesDao.actualizaClientesAgrupados(isA(String.class), any())).thenReturn(true);
		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(mailService).enviaMail(isA(EntCorreo.class));		
		
		response = clientesService.actualizaAgrupadoClientes(request);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaAgrupadoClientes() {
					
		when(clientesDao.eliminaClientesAgrupados(isA(String.class))).thenReturn(null);
		
		ResponseApi response = clientesService.eliminaAgrupadoClientes("");
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//////////////		
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		user.setIdUserSesion("123");
					
		when(clientesDao.eliminaClientesAgrupados(isA(String.class))).thenReturn(new Document());
		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(mailService).enviaMail(isA(EntCorreo.class));		
		
		response = clientesService.eliminaAgrupadoClientes("");
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaBloqueoClientes() {
				
	
		when(clientesDao.consultaBloqueosOperativos(isA(String.class))).thenReturn(Arrays.asList());
		
		ResponseApi response = clientesService.consultaBloqueoClientes("");
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//////////////
		when(clientesDao.consultaBloqueosOperativos(isA(String.class))).thenReturn(Arrays.asList(new ReqBloqueoClientes()));
		
		response = clientesService.consultaBloqueoClientes("");
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		

	}
	
	@Test
	void testRegistraBloqueoClientes() {
					
		ReqBloqueoClientes request = new ReqBloqueoClientes();
		request.setTipo("");
		
		when(clientesDao.buscaBloqueoOperativo(isA(String.class), isA(Document.class))).thenReturn(new Document());
		
		ResponseApi response = clientesService.registraBloqueoClientes(request);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//////////////
		request.setTipo("1");
		response = clientesService.registraBloqueoClientes(request);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());

		//////////////
		request.setTipo("2");
		response = clientesService.registraBloqueoClientes(request);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//////////////
		request.setTipo("3");
		when(clientesDao.buscaBloqueoOperativo(isA(String.class), isA(Document.class))).thenReturn(null);
		when(clientesDao.registraBloqueoOperativo(isA(String.class), isA(Document.class), isA(String.class))).thenReturn(false);
		
		response = clientesService.registraBloqueoClientes(request);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//////////////
		when(clientesDao.registraBloqueoOperativo(isA(String.class), isA(Document.class), isA(String.class))).thenReturn(true);
		
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		user.setIdUserSesion("123");
					
		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(mailService).enviaMail(isA(EntCorreo.class));	
		
		response = clientesService.registraBloqueoClientes(request);		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
		
	@Test
	void testEliminaBloqueoClientes() {
					
		ReqBloqueoClientes request = new ReqBloqueoClientes();
		request.setTipo("");
		
		when(clientesDao.buscaBloqueoOperativoPorId(isA(String.class))).thenReturn(null);
		
		ResponseApi response = clientesService.eliminaBloqueoClientes("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//////////////		
		ReqBloqueoClientes bloqueo = new ReqBloqueoClientes();
		bloqueo.setTipo("");
		bloqueo.toString();
		
		when(clientesDao.buscaBloqueoOperativoPorId(isA(String.class))).thenReturn(bloqueo);
		when(clientesDao.eliminaBloqueoOperativo(isA(String.class), isA(String.class))).thenReturn(false);
		
		response = clientesService.eliminaBloqueoClientes("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//////////////		
		bloqueo.setTipo("1");
		bloqueo.toString();

		response = clientesService.eliminaBloqueoClientes("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//////////////		
		bloqueo.setTipo("2");
		bloqueo.toString();

		response = clientesService.eliminaBloqueoClientes("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//////////////		
		bloqueo.setTipo("3");
		bloqueo.toString();
		
		when(clientesDao.eliminaBloqueoOperativo(isA(String.class), isA(String.class))).thenReturn(true);
		
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		user.setIdUserSesion("123");
					
		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(mailService).enviaMail(isA(EntCorreo.class));	

		response = clientesService.eliminaBloqueoClientes("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testConsultaCargaHistoricaAltoRiesgo() {
				
		List<HistoricoAltoRiesgo> historico = new ArrayList<>(); 
		HistoricoAltoRiesgo hist = new HistoricoAltoRiesgo();
		hist.setDescripcion("Test");
		hist.setFecha("123123312312312312");
		hist.setId("21312313123123");
		hist.setNombre("123123123123");
		hist.setRegistros(12);
		historico.add(hist);
		
		when(clientesDao.consultaCargaHistoricaAltoRiesgo()).thenReturn(historico);
		
		ResponseApi response = clientesService.consultaCargaHistoricaAltoRiesgo();
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////
		
		historico = new ArrayList<>(); 
		
		when(clientesDao.consultaCargaHistoricaAltoRiesgo()).thenReturn(historico);
		
		response = clientesService.consultaCargaHistoricaAltoRiesgo();
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
}
