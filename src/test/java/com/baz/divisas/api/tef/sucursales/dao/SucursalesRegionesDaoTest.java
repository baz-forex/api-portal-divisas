package com.baz.divisas.api.tef.sucursales.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
class SucursalesRegionesDaoTest {

	@InjectMocks 
	private SucursalesDao sucursalesDao;

	@Mock 
	private MongoCollection<Document> mockCollectionRegiones;
	@Mock
	private AggregateIterable<Document> mockAggIterableRegiones;	
	
	@Test
	void testFindSucursalesSegmentosRegion() {

		when(mockCollectionRegiones.aggregate(any())).thenReturn(mockAggIterableRegiones);
		when(mockAggIterableRegiones.allowDiskUse(true)).thenReturn(mockAggIterableRegiones);
		when(mockAggIterableRegiones.into(any())).thenReturn(new ArrayList<Document>());

		List<Document> resultado = sucursalesDao.findSucursalesSegmentosRegion();
		assertThat(resultado).isNotNull();
	}
	
}
