package com.baz.divisas.api.tef.parametria.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

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
import com.baz.divisas.api.tef.parametria.dto.ResConsultaParametros;
import com.baz.divisas.api.tef.promoespacio.model.ResponseToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParametriaServiceTest {
	
	@InjectMocks
	private ParametriaService impl;
	
	@Mock
	private WebClient webClient;
	
	@Mock
	private RequestBodyUriSpec reqBody;
	
	@Mock
	private RequestBodySpec reqBodySpec;
	
	@Mock
	private RequestHeadersSpec requestHeadersSpec;
	
	private ObjectMapper MAPPER = new ObjectMapper();
	
	@Mock
	private ResponseSpec responseSpec;
	
	
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
	        impl = new ParametriaService(WebClient.create(baseUrl));
	        impl.setClient(WebClient.create(baseUrl));
	        impl.getClient();
	    }
	
	@Mock
	private Mono mono;
	
	@Test
	public void testGetTokenApiAWSInterno() {
		impl = new ParametriaService();
		impl = new ParametriaService(webClient);
		ResponseToken rpe = new ResponseToken();
		rpe.setCodigo(200);
		when(webClient.post()).thenReturn(reqBody);
		when(reqBody.uri(isA(String.class))).thenReturn(reqBodySpec);
		when(reqBodySpec.contentType(any())).thenReturn(reqBodySpec);
		when(reqBodySpec.header(any(), any())).thenReturn(reqBodySpec);
		when(reqBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
		when(reqBodySpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(any(Class.class))).thenReturn(mono);
		when(mono.block()).thenReturn(rpe);
		TokenApigee resultado = impl.getTokenApiAWSInterno();
		assertThat(resultado.getCodigo()).isNull();
		
		when(webClient.post()).thenThrow(new RuntimeException("test"));
		resultado = impl.getTokenApiAWSInterno();
		assertThat(resultado.getCodigo()).isNull();
	}
	
	@Test
	public void testGetLlavesDivisas() throws JsonProcessingException {
		ApiLlaves llaves = new ApiLlaves();
		llaves.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(llaves))
				.addHeader("Content-Type", "application/json"));

		ApiLlaves response = impl.getLlavesDivisas("123123123");

		assertThat(response.getResultado()).isNull();

	}
	
	@Test
	public void testConsultaParametros() throws JsonProcessingException {
		ResConsultaParametros parametros = new ResConsultaParametros();
		parametros.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(parametros))
				.addHeader("Content-Type", "application/json"));

		ResConsultaParametros response = impl.consultaParametros(new ReqBusquedaParametros(),"123123","123123");

		assertThat(response.getResultado()).isNull();

	}
	
	@Test
	public void testRegistraParametros() throws JsonProcessingException {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(result))
				.addHeader("Content-Type", "application/json"));

		ResultadoApi response = impl.registraParametros(new ReqDetalleParametro(),"1213","123123","12123");

		assertThat(response.getCodigo()).isEqualTo("ERROR");

	}
	
	@Test
	public void testActualizaParametros() throws JsonProcessingException {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(result))
				.addHeader("Content-Type", "application/json"));

		ResultadoApi response = impl.actualizaParametros(new ReqDetalleParametro(),"1213","123123","12123");

		assertThat(response.getCodigo()).isEqualTo("ERROR");

	}
	
	@Test
	public void testRegistraParametrosMasivos() throws JsonProcessingException {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(result))
				.addHeader("Content-Type", "application/json"));

		ResultadoApi response = impl.registraParametrosMasivos(new ReqArchivo(),"1213","123123","123123");

		assertThat(response.getCodigo()).isEqualTo("ERROR");

	}
	
	@Test
	public void testConsultaAsignacionesParametros() throws JsonProcessingException {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(result))
				.addHeader("Content-Type", "application/json"));

		ResultadoApi response = impl.consultaAsignacionesParametros(new ReqBusquedaAsignaciones(),"1213","123123");

		assertThat(response.getCodigo()).isEqualTo("ERROR");

	}
	
	
	@Test
	public void testCrearAsignacionParametro() throws JsonProcessingException {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(result))
				.addHeader("Content-Type", "application/json"));

		ResultadoApi response = impl.crearAsignacionParametro(new ReqAsignacion(), "1213","123123","12123");

		assertThat(response.getCodigo()).isEqualTo("ERROR");

	}
	
	@Test
	public void testActualizaAsignacionParametro() throws JsonProcessingException {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(result))
				.addHeader("Content-Type", "application/json"));

		ResultadoApi response = impl.actualizaAsignacionParametro(new ReqActAsignacion(),"1213","123123","12123");

		assertThat(response.getCodigo()).isEqualTo("ERROR");

	}
	
	@Test
	public void testEliminaAsignacionParametro() throws JsonProcessingException {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(result))
				.addHeader("Content-Type", "application/json"));

		ResultadoApi response = impl.eliminaAsignacionParametro(new ReqEliminacionAsignacion(),"1213","123123");

		assertThat(response.getCodigo()).isEqualTo("ERROR");

	}
	
	@Test
	public void testRegistraAsignacionesParametrosMasivos() throws JsonProcessingException {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("123123");

		mockMiddlewareServer.enqueue(new MockResponse()
				.setBody(MAPPER.writeValueAsString(result))
				.addHeader("Content-Type", "application/json"));

		ResultadoApi response = impl.registraAsignacionesParametrosMasivos(new ReqArchivo(),"1213","123123","12123");

		assertThat(response.getCodigo()).isEqualTo("ERROR");

	}
}
