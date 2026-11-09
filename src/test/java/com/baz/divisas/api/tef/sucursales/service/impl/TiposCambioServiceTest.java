package com.baz.divisas.api.tef.sucursales.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.baz.divisas.api.apigee.dto.ResultadoApi;
import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.apigee.model.ApiLlaves;
import com.baz.divisas.api.tef.sucursales.model.TipoCambioDiv;
import com.baz.divisas.api.tef.sucursales.model.TiposCambioDiv;
import com.baz.divisas.api.tef.sucursales.service.TiposCambioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TiposCambioServiceTest {

	

	@InjectMocks
	private TiposCambioService tipoCambioService;
	
	private ObjectMapper MAPPER = new ObjectMapper();

    public static MockWebServer mockTipoCambioServiceServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockTipoCambioServiceServer = new MockWebServer();
        mockTipoCambioServiceServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockTipoCambioServiceServer.shutdown();
    }
    
    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockTipoCambioServiceServer.getPort());
        tipoCambioService = new TiposCambioService(WebClient.create(baseUrl));
        tipoCambioService.setClient(WebClient.create(baseUrl));
        tipoCambioService.getClient();
    }
   
    
    @Test
   	void testGetTokenApiAWSInterno() throws JsonProcessingException {
   				
       	TokenApigee respToken = new TokenApigee();
   		respToken.setAccess_token("ABCD");
   		
   		mockTipoCambioServiceServer.enqueue(new MockResponse()
   			      .setBody(MAPPER.writeValueAsString(respToken))
   			      .addHeader("Content-Type", "application/json"));
   		
   		TokenApigee response = tipoCambioService.getTokenApiAWSInterno();
   				
   		assertThat(response.getAccess_token()).isNotNull();
   		
   		mockTipoCambioServiceServer.enqueue(new MockResponse().setResponseCode(500)
                   .setHeader("content-type", "application/json")
                   .setBody("{}"));
   		
   		response = tipoCambioService.getTokenApiAWSInterno();
   		
   		assertThat(response.getAccess_token()).isNotNull();
   		
   	}
    
    @Test
   	void testGetLlavesDivisas() throws JsonProcessingException {
   				
    	ApiLlaves llaves = new ApiLlaves();
   		llaves.setCodigo("200");
   		   		
   		mockTipoCambioServiceServer.enqueue(new MockResponse()
   			      .setBody(MAPPER.writeValueAsString(llaves))
   			      .addHeader("Content-Type", "application/json"));
   		
   		ApiLlaves response = tipoCambioService.getLlavesDivisas("123123");
   				
   		assertThat(response.getCodigo()).isNull();
   		
   		mockTipoCambioServiceServer.enqueue(new MockResponse().setResponseCode(500)
                   .setHeader("content-type", "application/json")
                   .setBody("{}"));
   		
   		response = tipoCambioService.getLlavesDivisas("12312323");
   		
   		assertThat(response.getCodigo()).isNull();
   		
   	}
    
    @Test
    void testRegistraTiposCambioDivisas() throws JsonProcessingException {
    	ResultadoApi responseTc = new ResultadoApi();
    	responseTc.setCodigo("100");
		mockTipoCambioServiceServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(responseTc))
			      .addHeader("Content-Type", "application/json"));
		
		TiposCambioDiv tipos = new TiposCambioDiv();
		
		List<TipoCambioDiv> listaTC = new ArrayList<>();
		
		TipoCambioDiv tcUSD = new TipoCambioDiv();
		tcUSD.setId("FIX");
		tcUSD.setIdTipoOperacion("410");
		tcUSD.setCodigoDivisa("USD");
		tcUSD.setCodigoOrigen("1827");
		
		
		
		TipoCambioDiv tcMXN = new TipoCambioDiv();
		tcUSD.setId("FIX");
		tcUSD.setIdTipoOperacion("411");
		tcUSD.setCodigoDivisa("USD");
		tcUSD.setCodigoOrigen("1827");
		
		
		listaTC.add(tcUSD);
		listaTC.add(tcMXN);
		
		tipos.setTiposCambios(listaTC);
		tipos.getTiposCambios();
		
		ResultadoApi response = tipoCambioService.registraTiposCambioDivisas(tipos, null, null);
				
		assertThat(response.getCodigo()).isNull();
		
		mockTipoCambioServiceServer.enqueue(new MockResponse().setResponseCode(500)
                .setHeader("content-type", "application/json")
                .setBody("{}"));
		
		response = tipoCambioService.registraTiposCambioDivisas(tipos, null, null);
		
		assertThat(response.getCodigo()).isNull();
    }
}
