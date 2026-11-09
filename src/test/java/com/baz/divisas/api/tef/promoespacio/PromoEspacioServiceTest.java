package com.baz.divisas.api.tef.promoespacio;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.baz.divisas.api.tef.promoespacio.model.ResponsePromoEspacio;
import com.baz.divisas.api.tef.promoespacio.model.ResponseToken;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioService;

import reactor.core.publisher.Mono;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromoEspacioServiceTest {

	@InjectMocks
	private PromoEspacioService impl;
	
	@Mock
	private WebClient webClient;
	
	@Mock
	private RequestBodyUriSpec reqBody;
	
	@Mock
	private RequestBodySpec reqBodySpec;
	
	@Mock
	private RequestHeadersSpec<?> requestHeadersSpec;
	
	@SuppressWarnings("rawtypes")
	@Mock
	private RequestHeadersSpec requestHeadersSpe;
	
	@Mock
	private ResponseSpec responseSpec;
	
	@SuppressWarnings("rawtypes")
	@Mock
	private Mono mono;

	@SuppressWarnings("unchecked")
	@Test
	void testGetToken() {
		impl = new PromoEspacioService();
		impl = new PromoEspacioService(webClient);
		ResponseToken rpe = new ResponseToken();
		rpe.setCodigo(200);
		when(webClient.post()).thenReturn(reqBody);
		when(reqBody.uri(isA(String.class))).thenReturn(reqBodySpec);
		when(reqBodySpec.header(anyString(), anyString())).thenReturn(reqBodySpec);
		when(reqBodySpec.bodyValue(any())).thenReturn(requestHeadersSpe);
		when(requestHeadersSpe.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(any(Class.class))).thenReturn(mono);
		when(mono.block()).thenReturn(rpe);
		ResponseToken resultado = impl.getToken("","fasfdasdasdas");
		assertThat(resultado.getCodigo()).isEqualTo(200);
		
		when(webClient.post()).thenThrow(new RuntimeException("test"));
		resultado = impl.getToken("","asdadadasd");
		assertThat(resultado.getCodigo()).isNull();
	}
	@SuppressWarnings("unchecked")
	@Test
	void testPutSucursales() {
		impl = new PromoEspacioService(webClient);
		
		List<Document> ls = new ArrayList<>();
		Document suc = new Document();
		suc.append("idSucursal", 200);
		suc.append("dolarCompra", "19.05");
		suc.append("dolarVenta", "19.05");
		suc.append("dolarCompraApp", "19.05");
		suc.append("dolarVentaApp", "19.05");
		suc.append("canadienseCompra", "19.05");
		suc.append("canadienseVenta", "19.05");
		suc.append("euroCompra", "19.05");
		suc.append("euroVenta", "19.05");
		suc.append("plataCompra", "");
		suc.append("plataVenta", "");
		suc.append("regionStr", "Corporativas");
		suc.append("estrategia", "Spread 1.1");
		ls.add(suc);

		ResponsePromoEspacio rpe = new ResponsePromoEspacio();
		when(webClient.put()).thenReturn(reqBody);
		when(reqBody.uri(isA(String.class))).thenReturn(reqBodySpec);
		when(reqBodySpec.contentType(any())).thenReturn(reqBodySpec);
		when(reqBodySpec.header(anyString(), anyString())).thenReturn(reqBodySpec);
		when(reqBodySpec.body(isA(BodyInserter.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(any(Class.class))).thenReturn(mono);
		when(mono.block()).thenReturn(rpe);
		
		ResponsePromoEspacio resultado = impl.putSucursales("1", ls);
		assertThat(resultado).isNotNull();

		when(webClient.put()).thenThrow(new RuntimeException("test"));
		resultado = impl.putSucursales("1", ls);
		assertThat(resultado).isNotNull();

	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetTokenSinParametros() {
		impl = new PromoEspacioService(webClient);

		ResponseToken rpe = new ResponseToken();
		rpe.setCodigo(200);

		when(webClient.post()).thenReturn(reqBody);
		when(reqBody.uri(isA(String.class))).thenReturn(reqBodySpec);
		when(reqBodySpec.contentType(any())).thenReturn(reqBodySpec);
		when(reqBodySpec.body(isA(BodyInserter.class))).thenReturn(requestHeadersSpe);
		when(requestHeadersSpe.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(any(Class.class))).thenReturn(mono);
		when(mono.block()).thenReturn(rpe);

		ResponseToken resultado = impl.getToken();
		assertThat(resultado.getCodigo()).isEqualTo(200);

		// Rama de excepcion: setMensaje con el mensaje del error
		when(webClient.post()).thenThrow(new RuntimeException("test"));
		resultado = impl.getToken();
		assertThat(resultado.getMensaje()).isNotNull();
	}

	@SuppressWarnings("unchecked")
	@Test
	void testPutSucursalesAnterior() {
		impl = new PromoEspacioService(webClient);

		List<Document> ls = new ArrayList<>();
		Document suc = new Document();
		suc.append("idSucursal", 200);
		suc.append("dolarCompra", "19.05");
		suc.append("dolarVenta", "19.05");
		ls.add(suc);

		ResponsePromoEspacio rpe = new ResponsePromoEspacio();
		rpe.setCodigo(200);

		when(webClient.put()).thenReturn(reqBody);
		when(reqBody.uri(isA(String.class))).thenReturn(reqBodySpec);
		when(reqBodySpec.contentType(any())).thenReturn(reqBodySpec);
		when(reqBodySpec.header(anyString(), anyString())).thenReturn(reqBodySpec);
		when(reqBodySpec.body(isA(BodyInserter.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(any(Class.class))).thenReturn(mono);
		when(mono.block()).thenReturn(rpe);

		ResponsePromoEspacio resultado = impl.putSucursalesAnterior("1", ls);
		assertThat(resultado).isNotNull();

		// Rama de excepcion: setMensaje con el mensaje del error
		when(webClient.put()).thenThrow(new RuntimeException("test"));
		resultado = impl.putSucursalesAnterior("1", ls);
		assertThat(resultado.getMensaje()).isNotNull();
	}
}
