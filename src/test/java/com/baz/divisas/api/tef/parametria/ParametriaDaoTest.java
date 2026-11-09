package com.baz.divisas.api.tef.parametria;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.digital.configuraciones.model.Monto;
import com.baz.divisas.api.digital.configuraciones.model.OperacionesCanceladas;
import com.baz.divisas.api.digital.configuraciones.model.OperacionesPermitidas;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaChatbot;
import com.baz.divisas.api.digital.configuraciones.model.RangoMontos;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParametriaDaoTest {

	@InjectMocks 
	private ParametriaDao dao;
	@Mock 
	private MongoCollection<Document> mockCol;
	@Mock
	private FindIterable<Document> mockFindIterable;	
	@Mock
	private AggregateIterable<Document> mockAggregateIterable;	
	@Mock
	private UpdateResult mockUpdate;
	@Mock
	private DeleteResult mockDelete;
	@Mock
	private InsertOneResult mockInsert;
	@Mock
	private BsonValue mockBson;
	
	@Test
	void testConsultar() {
		dao = new ParametriaDao(mockCol);
		
		when(mockCol.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(null);
		Document resultado = dao.consultar("InformePromoEspacio");
		assertThat(resultado).isEmpty();
		
		Document doc = new Document();
		when(mockFindIterable.first()).thenReturn(doc);
		resultado = dao.consultar("InformePromoEspacio");
		assertThat(resultado).isEmpty();
		
		doc.append("activo", false);
		resultado = dao.consultar("InformePromoEspacio");
		assertThat(resultado).isNotEmpty();
		
		//dao = new ParametriaDao();
		//resultado = dao.consultar("InformePromoEspacio");
	}
	
	@Test
	void testObtenerActivo() {
		dao = new ParametriaDao(mockCol);
		
		when(mockCol.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(null);
		boolean resultado = dao.obtenerActivo("InformePromoEspacio");
		assertThat(resultado).isFalse();
		
		Document doc = new Document();
		when(mockFindIterable.first()).thenReturn(doc);
		resultado = dao.obtenerActivo("InformePromoEspacio");
		assertThat(resultado).isFalse();
		
		doc.append("activo", false);
		resultado = dao.obtenerActivo("InformePromoEspacio");
		assertThat(resultado).isFalse();
		
		doc.clear();
		doc.append("activo", true);
		resultado = dao.obtenerActivo("InformePromoEspacio");
		assertThat(resultado).isTrue();
		
		//dao = new ParametriaDao();
		//resultado = dao.obtenerActivo("InformePromoEspacio");
	}
	
	@Test
	void testActualizarCampoActivo() {
		dao = new ParametriaDao(mockCol);
		when(mockCol.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdate);
		when(mockUpdate.getMatchedCount()).thenReturn(0L);
		
		boolean resultado = dao.actualizarCampoActivo("InformePromoEspacio",false);
		assertThat(resultado).isFalse();
		
		when(mockUpdate.getMatchedCount()).thenReturn(1L);
		resultado = dao.actualizarCampoActivo("InformePromoEspacio",false);
		assertThat(resultado).isTrue();
		
		//dao = new ParametriaDao();
		//resultado = dao.actualizarCampoActivo("InformePromoEspacio",false);
	}
	
	@Test
	void testActualizarCampo() {
		dao = new ParametriaDao(mockCol);
		when(mockCol.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdate);
		when(mockUpdate.getMatchedCount()).thenReturn(0L);

		boolean resultado = dao.actualizarCampo("InformePromoEspacio", "Activo", false);
		assertThat(resultado).isFalse();

		when(mockUpdate.getMatchedCount()).thenReturn(1L);
		resultado = dao.actualizarCampo("InformePromoEspacio","Activo", false);
		assertThat(resultado).isTrue();

		//dao = new ParametriaDao();
		//resultado = dao.actualizarCampo("InformePromoEspacio", "sucursales", Arrays.asList(7676,2024,172,2244));
	}
	
	@Test
	void testEliminarCampo() {
		dao = new ParametriaDao(mockCol);
		when(mockCol.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdate);
		when(mockUpdate.getMatchedCount()).thenReturn(0L);

		boolean resultado = dao.eliminarCampo("InformePromoEspacio", "Activo");
		assertThat(resultado).isFalse();

		when(mockUpdate.getMatchedCount()).thenReturn(1L);
		resultado = dao.eliminarCampo("InformePromoEspacio","Activo");
		assertThat(resultado).isTrue();

		//dao = new ParametriaDao();
		//resultado = dao.actualizarCampo("InformePromoEspacio", "sucursales", Arrays.asList(7676,2024,172,2244));
	}
	
	@Test
	void testEliminar() {
		dao = new ParametriaDao(mockCol);
		when(mockCol.deleteOne(isA(Bson.class))).thenReturn(mockDelete);
		when(mockDelete.getDeletedCount()).thenReturn(0L);

		boolean resultado = dao.eliminar("InformePromoEspacio");
		assertThat(resultado).isFalse();

		when(mockDelete.getDeletedCount()).thenReturn(1L);
		resultado = dao.eliminar("InformePromoEspacio");
		assertThat(resultado).isTrue();
	}
	
	@Test
	void testAlta() {
		dao = new ParametriaDao(mockCol);
		Document doc = new Document();
		ArrayList<Document> docs = new ArrayList<>();
		docs.add(doc);
		when(mockCol.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(docs);
		
		boolean resultado = dao.alta(doc);
		assertThat(resultado).isFalse();
		
		docs.clear();
		when(mockCol.insertOne(isA(Document.class))).thenReturn(mockInsert);
		when(mockInsert.getInsertedId()).thenReturn(mockBson);
		when(mockBson.isNull()).thenReturn(true);
		
		resultado = dao.alta(doc);
		assertThat(resultado).isFalse();
		
		when(mockFindIterable.into(any())).thenReturn(null);
		when(mockBson.isNull()).thenReturn(false);
		
		resultado = dao.alta(doc);
		assertThat(resultado).isTrue();
	}
	
	@Test
	void testConsultar2() {
		dao = new ParametriaDao(mockCol);
		
		when(mockCol.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(null);
		List<String> resultado = dao.consultar();
		assertThat(resultado).isEmpty();
		
		List<Document> docs = new ArrayList<>();
		when(mockFindIterable.into(any())).thenReturn(docs);
		resultado = dao.consultar();
		assertThat(resultado).isEmpty();
		
		
		Document doc = new Document();
		doc.append("_id", "Hola");
		docs.add(doc);
		resultado = dao.consultar();
		assertThat(resultado).isNotEmpty();

	}
	
	@Test
	void testFindParametria() {
		dao = new ParametriaDao(mockCol);
		
		when(mockCol.aggregate(any())).thenReturn(mockAggregateIterable);
		when(mockAggregateIterable.first()).thenReturn(null);
		Document resultado = dao.findParametria("ChatBot_Compra","USD");
		assertThat(resultado).isNull();
	}
	
	@Test
	void testUpdateParametria() {
		ParametriaChatbot par= new ParametriaChatbot();
		par.setIdMoneda("USD");
		par.setVigenciaEnDias("12");
		
		RangoMontos monto = new RangoMontos();
		monto.setMontoMaximo("21");
		monto.setMontoMinimo("12");
		par.setMontos(monto);
		
		Monto fluctuacion= new Monto();
		fluctuacion.setValorPesos("12");
		fluctuacion.setValor("100");
		par.setFluctuacion(fluctuacion);
		
		OperacionesCanceladas canceladas= new OperacionesCanceladas();
		canceladas.setOperaciones("12");
		canceladas.setPeriodoBloqueo("Dia");
		canceladas.setPeriodoLimite("Dia");
		canceladas.setPlazoBloqueo("Mes");
		canceladas.setPlazoLimite("1");
		par.setOperacionesCanceladas(canceladas);
		
		OperacionesPermitidas permitidas = new OperacionesPermitidas();
		permitidas.setOperaciones("12");
		permitidas.setPlazo("Dia");
		par.setOperacionesPermitidas(permitidas);
		
		when(mockCol.findOneAndUpdate(isA(Bson.class), isA(Bson.class), isA(FindOneAndUpdateOptions.class))).thenReturn(null);
		Document resultado = dao.updateParametria("ChatBot_Compra","USD", par);
		assertThat(resultado).isNull();
	}
	
	@Test
	public void testupdateParametriaConfiguracionSpotOLP() {
		
		ReqConfiguracionSpot req = new ReqConfiguracionSpot();
		req.setAcunacion(2.0);
		req.setDiferenciaCompraVenta(200.0);
		req.setDireccionActualizacion("5");
		req.setFrecuencia("1");
		req.setId("12312312");
		req.setSpotMinimo(2.0);
		req.setX1(3.0);
		req.setX2(5.0);
		dao = new ParametriaDao(mockCol);
		when(mockCol.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdate);
		when(mockUpdate.getMatchedCount()).thenReturn(0L);

		boolean resultado = dao.updateParametriaConfiguracionSpotOLP(req);
		assertThat(resultado).isFalse();

		when(mockUpdate.getMatchedCount()).thenReturn(1L);
		resultado = dao.updateParametriaConfiguracionSpotOLP(req);
		assertThat(resultado).isTrue();
		
		dao = new ParametriaDao();
	}
}
