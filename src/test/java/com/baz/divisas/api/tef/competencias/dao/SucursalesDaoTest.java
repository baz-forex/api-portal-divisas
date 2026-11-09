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

import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SucursalesDaoTest {

	@InjectMocks 
	private CargaCompetenciaDao cargaCompetenciasDao;

	@Mock 
	private MongoCollection<Sucursal> mockCollectionSucursales;
	@Mock
	private FindIterable<Sucursal> mockFindIterableSucursales;
	@Mock
	private AggregateIterable<Sucursal> mockAggIterableSucursales;
	@Mock
	private UpdateResult mockUpdateResult;

	
	@Test
	void testBuscaSucursales() {

		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		List<Sucursal> resultado = cargaCompetenciasDao.buscaSucursales(Arrays.asList(new Document("resultado", new Document("idSucursal", "1"))));
		assertThat(resultado).isNotNull();

	}
	
}
