package com.baz.divisas.api.core.clientes;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.core.clientes.dto.ReqActualizacionCliente;
import com.baz.divisas.api.core.clientes.dto.ReqBusquedaClientes;
import com.baz.divisas.api.core.clientes.dto.ReqTipoCambio;
import com.baz.divisas.api.core.clientes.dto.RespBusquedaCliente;
import com.baz.divisas.api.core.clientes.dto.RespBusquedaClientes;
import com.baz.divisas.api.core.clientes.dto.RespMiddleware;
import com.baz.divisas.api.core.clientes.dto.RespToken;
import com.baz.divisas.api.core.clientes.services.MiddlewareService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MiddlewareServiceTest {
	
	@InjectMocks
	private MiddlewareService middlewareService;
	
	private ObjectMapper MAPPER = new ObjectMapper();

    public static MockWebServer mockMiddlewareServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockMiddlewareServer = new MockWebServer();
        mockMiddlewareServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockMiddlewareServer.shutdown();
    }
    
    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockMiddlewareServer.getPort());
        middlewareService = new MiddlewareService(WebClient.create(baseUrl));
        middlewareService.setClient(WebClient.create(baseUrl));
        middlewareService.getClient();
    }
    
	@Test
	void testGetToken() throws JsonProcessingException {
				
		RespToken respToken = new RespToken("1", "1", "OK");
		respToken.setToken("ABCD");
		
		mockMiddlewareServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(respToken))
			      .addHeader("Content-Type", "application/json"));
		
		RespToken response = middlewareService.getToken();
				
		assertThat(response.getResultado()).isEqualTo("1");
		
	}
	
	@Test
	void testGetTokenAdmin() throws JsonProcessingException {
				
		RespToken respToken = new RespToken("1", "1", "OK");
		respToken.setToken("ABCD");
		
		mockMiddlewareServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(respToken))
			      .addHeader("Content-Type", "application/json"));
		
		RespToken response = middlewareService.getTokenAdmin();
				
		assertThat(response.getResultado()).isEqualTo("1");
		
	}
	
	@Test
	void testGetTokenException() {
		
		mockMiddlewareServer.enqueue(new MockResponse().setResponseCode(500)
										                .setHeader("content-type", "application/json")
										                .setBody("{}"));
		
		RespToken response = middlewareService.getToken();
				
		assertThat(response.getResultado()).isEqualTo("0");
		
	}
    
	@Test
	void testBusquedaClientes() throws JsonProcessingException {
		
		RespBusquedaClientes respBusqCtes = new RespBusquedaClientes("1", "1", "Busqueda OK");
		
		mockMiddlewareServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(respBusqCtes))
			      .addHeader("Content-Type", "application/json"));
		
		RespBusquedaClientes response = middlewareService.find(new ReqBusquedaClientes());
				
		assertThat(response.getResultado()).isEqualTo("1");
		
	}
	
	@Test
	void testBusquedaClientesException() {
		
		mockMiddlewareServer.enqueue(new MockResponse().setResponseCode(500)
										                .setHeader("content-type", "application/json")
										                .setBody("{}"));
		
		RespBusquedaClientes response = middlewareService.find(new ReqBusquedaClientes());
				
		assertThat(response.getResultado()).isEqualTo("0");
		
	}
	
	@Test
	void testBusquedaClienteById() throws JsonProcessingException {

		RespBusquedaCliente respBusqCte = new RespBusquedaCliente("1", "1", "OK");
		
		mockMiddlewareServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(respBusqCte))
			      .addHeader("Content-Type", "application/json"));
		
		RespBusquedaCliente response = middlewareService.findById("");
				
		assertThat(response.getResultado()).isEqualTo("1");
	}
	
	@Test
	void testBusquedaClienteByIdException() {
		
		mockMiddlewareServer.enqueue(new MockResponse().setResponseCode(500)
										                .setHeader("content-type", "application/json")
										                .setBody("{}"));
		
		RespBusquedaCliente response = middlewareService.findById("");
				
		assertThat(response.getResultado()).isEqualTo("0");
		
	}
	
	@Test
	void testActualizaCliente() throws JsonProcessingException {

		RespMiddleware respActCte = new RespMiddleware("1", "1", "OK");
        
		mockMiddlewareServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(respActCte))
			      .addHeader("Content-Type", "application/json"));
		
		RespMiddleware response = middlewareService.actualizaCliente(new ReqActualizacionCliente());
				
		assertThat(response.getResultado()).isEqualTo("1");

	}
	
	@Test
	void testConsultaTipoCambio() throws JsonProcessingException {

		RespMiddleware respActCte = new RespMiddleware("1", "1", "OK");
        
		mockMiddlewareServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(respActCte))
			      .addHeader("Content-Type", "application/json"));
		
		RespMiddleware response = middlewareService.consultaTipoCambio(new ReqTipoCambio());
				
		assertThat(response.getResultado()).isEqualTo("1");

	}
	
	
	
	@Test
	void testActualizaClienteException() {
		
		mockMiddlewareServer.enqueue(new MockResponse().setResponseCode(500)
										                .setHeader("content-type", "application/json")
										                .setBody("{}"));
		
		RespMiddleware response = middlewareService.actualizaCliente(new ReqActualizacionCliente());
				
		assertThat(response.getResultado()).isEqualTo("0");
		
	}
	
	@Test
	void testConsultaTipoCambioException() {
		
		mockMiddlewareServer.enqueue(new MockResponse().setResponseCode(500)
										                .setHeader("content-type", "application/json")
										                .setBody("{}"));
		
		RespMiddleware response = middlewareService.consultaTipoCambio(new ReqTipoCambio());
				
		assertThat(response.getResultado()).isEqualTo("0");
		
	}

		
}
