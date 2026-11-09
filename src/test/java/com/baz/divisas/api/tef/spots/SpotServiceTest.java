package com.baz.divisas.api.tef.spots;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.tef.spots.dto.ReqSpotsTesoreria;
import com.baz.divisas.api.tef.spots.dto.ResSpotTesoreria;
import com.baz.divisas.api.tef.spots.model.Divisas;
import com.baz.divisas.api.tef.spots.model.TipoCambioSpotDivisa;
import com.baz.divisas.api.tef.spots.services.impl.SpotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class SpotServiceTest {

	@InjectMocks
	private SpotService spotService;
	
	private ObjectMapper MAPPER = new ObjectMapper();

    public static MockWebServer mockSpotServiceServer;

    @BeforeAll
    static void setUp() throws IOException {
    	mockSpotServiceServer = new MockWebServer();
    	mockSpotServiceServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
    	mockSpotServiceServer.shutdown();
    }
    
    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockSpotServiceServer.getPort());
        spotService = new SpotService(WebClient.create(baseUrl));
        spotService.setClient(WebClient.create(baseUrl));
        spotService.getClient();
    }
    
    @Test
	void testGetTokenApi() throws JsonProcessingException {
				
    	TokenApigee respToken = new TokenApigee();
		respToken.setAccess_token("ABCD");
		
		mockSpotServiceServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(respToken))
			      .addHeader("Content-Type", "application/json"));
		respToken.toString();
		TokenApigee response = spotService.getTokenApi();
		
		mockSpotServiceServer.enqueue(new MockResponse()
			    .setResponseCode(500)
			    .setBody("Internal Server Error"));
		 response = spotService.getTokenApi();
				
		assertThat(response.getAccess_token()).isNull();
		
		spotService = new SpotService();
	}
    
    @Test
	public void testBusquedaTarjetaRegalo() throws JsonProcessingException {
				
    	ResSpotTesoreria resp = new ResSpotTesoreria();
		resp.setCodigo("ABCD");
		
		ReqSpotsTesoreria request = new ReqSpotsTesoreria();
		request.setClaveNegocio("12313123");
		ArrayList<String> divisas = new ArrayList<String>();
		divisas.add("USD");
		request.setDivisas(divisas);
		
		String accessToken="1";
		
		mockSpotServiceServer.enqueue(new MockResponse()
			      .setBody(MAPPER.writeValueAsString(resp))
			      .addHeader("Content-Type", "application/json"));
		
		ResSpotTesoreria response = spotService.consultaSpotTesoreria(null, null);
				
		assertThat(response.getCodigo()).isNull();
		
		response = spotService.consultaSpotTesoreria(request,accessToken);
		
		assertThat(response.getCodigo()).isNotNull();
		
	}
    
    @Test
    public void test() {
    	ResSpotTesoreria x = new ResSpotTesoreria();
    	Divisas div = new Divisas();
    	ArrayList<TipoCambioSpotDivisa> listaDiv = new ArrayList<TipoCambioSpotDivisa>();
    	TipoCambioSpotDivisa tipoCambio = new TipoCambioSpotDivisa();
    	tipoCambio.setDivisa("USD");
    	tipoCambio.setFechaRecuperacion("1231213123");
    	tipoCambio.setTipoCambio("121.12");
    	tipoCambio.getDivisa();
    	tipoCambio.getFechaRecuperacion();
    	tipoCambio.getTipoCambio();
    	listaDiv.add(tipoCambio);
    	div.setDivisas(listaDiv);
    	div.getDivisas();
    	div.toString();
    	x.setResultado(div);
    	x.getResultado();
    	x.toString();
    }
}
