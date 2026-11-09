package com.baz.divisas.api.tef.regionesestrategias.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.tef.regionesestrategias.dto.RequestEstrategia;
import com.baz.divisas.api.tef.regionesestrategias.dto.ResponseEstrategia;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegionesEstrategiasDaoTest {

	@InjectMocks 
	private RegionesEstrategiasDao regionesEstrategiasDao;

	@Mock 
	private MongoCollection<ResponseEstrategia> mockCollection;
	@Mock
	private AggregateIterable<ResponseEstrategia> mockAggIterable;
	@Mock
	private UpdateResult mockUpdateResult;

	
	@Test
	void testFindAll() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new ResponseEstrategia()));

		List<ResponseEstrategia> resultado = regionesEstrategiasDao.findAll();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByTipo() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new ResponseEstrategia()));
		
		RequestEstrategia request = new RequestEstrategia();
		request.setIdRegion(ObjectId.get().toHexString());

		List<ResponseEstrategia> resultado = regionesEstrategiasDao.findByTipo(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstrategiaRegion() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		RequestEstrategia request = new RequestEstrategia();
		request.setIdRegion(ObjectId.get().toHexString());
		
		UpdateResult resultado = regionesEstrategiasDao.updateEstrategiaRegion(request, "");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusEstrategiaRegion() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		RequestEstrategia request = new RequestEstrategia();
		request.setIdRegion(ObjectId.get().toHexString());
		
		UpdateResult resultado = regionesEstrategiasDao.updateEstatusEstrategiaRegion(request, "");
		assertThat(resultado).isNotNull();
	}
}
