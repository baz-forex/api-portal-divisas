package com.baz.divisas.api.tef.competencias.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CargaCompetenciasDaoTest {

	@InjectMocks 
	private CargaCompetenciaDao cargaCompetenciasDao;

	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private AggregateIterable<Document> mockAggIterable;

	
	@Test
	void testBuscaCompetencias() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(new ArrayList<Document>());

		List<Document> resultado = cargaCompetenciasDao.buscaCompetencias("2000-01-01");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testBuscaSucursalesCeCo() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(new ArrayList<Document>());

		List<Document> resultado = cargaCompetenciasDao.buscaSucursalesCeCo(Arrays.asList(), "2000-01-01");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testBuscaSucursalesSinCeCo() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(new ArrayList<Document>());

		List<Document> resultado = cargaCompetenciasDao.buscaSucursalesSinCeCo(Arrays.asList(new Document("idSucursal", "1")), "2000-01-01");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testBuscaCompetenciaAgrupadas() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(new ArrayList<Document>());

		List<Document> resultado = cargaCompetenciasDao.buscaCompetenciaAgrupadas();
		assertThat(resultado).isNotNull();
	}
	
}
