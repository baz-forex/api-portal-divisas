package com.baz.divisas.api.tef.dashboard.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DashboardDaoTest {

	@InjectMocks 
	private DashboardRegionesDAO dashboardDao;

	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private FindIterable<Document> mockFindIterable;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	PromoEspacioServiceImpl promoEspacioImpl;

	
	@Test
	void testFindRegiones() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Document()));

		List<Document> resultado = dashboardDao.findRegiones("");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindSegmentos() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Document()));

		List<Document> resultado = dashboardDao.findSegmentos(Arrays.asList(), "");
		assertThat(resultado).isNotNull();
	}

}
