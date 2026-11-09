package com.baz.divisas.api.tef.parametria;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.digital.configuraciones.model.ParametriaChatbot;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.parametria.dto.ReqActualizaCampoParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqAltaParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqConsultaParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminarCampoParametria;
import com.baz.divisas.api.tef.parametria.services.ParametriaServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParametriaServiceImplTest {

	@InjectMocks
	private ParametriaServiceImpl impl;
	@Mock
	private ParametriaDao dao;

	@Test
	void testConsulta() {
		impl = new ParametriaServiceImpl(dao);
		ReqConsultaParametria req = new ReqConsultaParametria();
		req.setParametria(null);
		ResponseApi resultado = impl.consultar(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		req.setParametria("");
		resultado = impl.consultar(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		req.setParametria("Hola");
		when(dao.consultar(isA(String.class))).thenReturn(new Document());
		resultado = impl.consultar(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
		
		when(dao.consultar(isA(String.class))).thenReturn(new Document("_id","Hola"));
		resultado = impl.consultar(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testConsultaActivo() {
		impl = new ParametriaServiceImpl(dao);
		ReqConsultaParametria req = new ReqConsultaParametria();
		req.setParametria(null);
		ResponseApi resultado = impl.consultarActivo(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		req.setParametria("Hola");
		when(dao.obtenerActivo(isA(String.class))).thenReturn(true);
		resultado = impl.consultarActivo(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testActualizarCampoActivo() {
		impl = new ParametriaServiceImpl(dao);
		ReqConsultaParametria req = new ReqConsultaParametria();
		req.setParametria(null);
		ResponseApi resultado = impl.actualizarCampoActivo(req, true);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		req.setParametria("Hola");
		when(dao.actualizarCampoActivo(isA(String.class),isA(Boolean.class))).thenReturn(false);
		resultado = impl.actualizarCampoActivo(req, true);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
		
		when(dao.actualizarCampoActivo(isA(String.class),isA(Boolean.class))).thenReturn(true);
		resultado = impl.actualizarCampoActivo(req, true);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testActualizarCampo() {
		impl = new ParametriaServiceImpl(dao);
		ReqActualizaCampoParametria req = new ReqActualizaCampoParametria();
		req.setParametria(null);
		ResponseApi resultado = impl.actualizarCampo(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		req.setParametria("Hola");
		req.setCampo("campo");
		req.setValor("Hola");
		when(dao.actualizarCampo(isA(String.class),isA(String.class),isA(String.class))).thenReturn(false);
		resultado = impl.actualizarCampo(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
		
		when(dao.actualizarCampo(isA(String.class),isA(String.class),isA(String.class))).thenReturn(true);
		resultado = impl.actualizarCampo(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testEliminarCampo() {
		impl = new ParametriaServiceImpl(dao);
		ReqEliminarCampoParametria req = new ReqEliminarCampoParametria();
		req.setParametria(null);
		ResponseApi resultado = impl.eliminarCampo(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		req.setParametria("Hola");
		req.setCampo("campo");
		when(dao.eliminarCampo(isA(String.class),isA(String.class))).thenReturn(false);
		resultado = impl.eliminarCampo(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
		
		when(dao.eliminarCampo(isA(String.class),isA(String.class))).thenReturn(true);
		resultado = impl.eliminarCampo(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testEliminar() {
		impl = new ParametriaServiceImpl(dao);
		ReqConsultaParametria req = new ReqConsultaParametria();
		req.setParametria(null);
		ResponseApi resultado = impl.eliminar(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		req.setParametria("Hola");
		when(dao.eliminar(isA(String.class))).thenReturn(false);
		resultado = impl.eliminar(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
		
		when(dao.eliminar(isA(String.class))).thenReturn(true);
		resultado = impl.eliminar(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testAlta() {
		impl = new ParametriaServiceImpl(dao);
		ReqAltaParametria req = new ReqAltaParametria();
		req.setParametria(null);
		ResponseApi resultado = impl.alta(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		req.setParametria("Hola");
		Map <String,String> map = new HashMap<String,String>();
		map.put("a","b");
		req.setCampos(map);
		when(dao.alta(isA(Document.class))).thenReturn(false);
		resultado = impl.alta(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
		
		when(dao.alta(isA(Document.class))).thenReturn(true);
		resultado = impl.alta(req);
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testConsultar() {
		impl = new ParametriaServiceImpl(dao);
		ArrayList<String> lista = new ArrayList<>();
		when(dao.consultar()).thenReturn(lista);
		ResponseApi resultado = impl.consultar();
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);

		lista.add("Hola");
		resultado = impl.consultar();
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testGetParametriaChatBot() {
		impl = new ParametriaServiceImpl(dao);
		when(dao.findParametria(isA(String.class),isA(String.class))).thenReturn(new Document());
		ResponseApi resultado = impl.getParametriaChatBot("412","USD");
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
		
		when(dao.findParametria(isA(String.class),isA(String.class))).thenReturn(null);
		resultado = impl.getParametriaChatBot("412","USD");
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	
	@Test
	void testUpdateParametriaOperacion() {
		impl = new ParametriaServiceImpl(dao);
		when(dao.updateParametria(isA(String.class),isA(String.class),isA(ParametriaChatbot.class))).thenReturn(new Document());
		ResponseApi resultado = impl.updateParametriaOperacion("412", "USD", new ParametriaChatbot());
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.OK);
		
		when(dao.updateParametria(isA(String.class),isA(String.class),isA(ParametriaChatbot.class))).thenReturn(null);
		resultado = impl.updateParametriaOperacion("412", "USD", new ParametriaChatbot());
		assertThat(resultado.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
