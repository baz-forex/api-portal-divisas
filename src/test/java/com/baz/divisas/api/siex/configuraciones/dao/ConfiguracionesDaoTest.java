package com.baz.divisas.api.siex.configuraciones.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.siex.configuraciones.model.CampoRestringido;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ConfiguracionesDaoTest {
	
	@InjectMocks
	private ConfiguracionesDao configDao;
	@Mock 
	private MongoCollection<CampoRestringido> mockCollection;
	@Mock
	private FindIterable<CampoRestringido> mockFindIterable;
	@Mock
	private AggregateIterable<CampoRestringido> mockAggIterable;
	
	@Test
	void testFindCamposRestringidos() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new CampoRestringido()));

		List<CampoRestringido> resultado = configDao.findCamposRestringidos();
		assertThat(resultado).isNotNull();

	}
	
	@Test
	void testFindConfigCamposRestringidos() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new CampoRestringido()));

		List<CampoRestringido> resultado = configDao.findConfigCamposRestringidos();
		assertThat(resultado).isNotNull();

	}
	
	@Test
	void testFindCamposRestringidoById() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new CampoRestringido());

		CampoRestringido resultado = configDao.findCamposRestringidoById("", "");
		assertThat(resultado).isNotNull();

	}
	
	@Test
	void testAddRestriccionCampo() {

		UpdateResult updateResult = UpdateResult.acknowledged(1L, 1L, null);
		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(updateResult);

		UpdateResult resultado = configDao.addRestriccionCampo("", "", "");
		assertThat(resultado).isNotNull();

	}
	
	@Test
	void testRemoveRestriccionCampo() {

		UpdateResult updateResult = UpdateResult.acknowledged(1L, 1L, null);
		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(updateResult);

		UpdateResult resultado = configDao.removeRestriccionCampo("", "", "");
		assertThat(resultado).isNotNull();

	}

}
