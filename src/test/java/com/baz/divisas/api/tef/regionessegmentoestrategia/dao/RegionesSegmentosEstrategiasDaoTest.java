package com.baz.divisas.api.tef.regionessegmentoestrategia.dao;

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

import com.baz.divisas.api.tef.regionesestrategias.dto.ResponseEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.RequestSegmentoEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.ResponseSegmentoEstrategia;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegionesSegmentosEstrategiasDaoTest {

	@InjectMocks 
	private RegionesSegmentosEstrategiasDao regionesSegmentosEstrategiasDao;

	@Mock 
	private MongoCollection<ResponseSegmentoEstrategia> mockCollection;
	@Mock
	private AggregateIterable<ResponseSegmentoEstrategia> mockAggIterable;
	@Mock
	private UpdateResult mockUpdateResult;

	
	@Test
	void testFindAll() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new ResponseSegmentoEstrategia()));

		List<ResponseSegmentoEstrategia> resultado = regionesSegmentosEstrategiasDao.findAll();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByTipo() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new ResponseEstrategia()));
		
		RequestSegmentoEstrategia request = new RequestSegmentoEstrategia();
		request.setIdRegion(ObjectId.get().toHexString());
		request.setIdSegmento(ObjectId.get().toHexString());

		List<ResponseSegmentoEstrategia> resultado = regionesSegmentosEstrategiasDao.findByTipo(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstrategiaSegmento() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		RequestSegmentoEstrategia request = new RequestSegmentoEstrategia();
		request.setIdRegion(ObjectId.get().toHexString());
		request.setIdSegmento(ObjectId.get().toHexString());
		
		UpdateResult resultado = regionesSegmentosEstrategiasDao.updateEstrategiaSegmento(request, "");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusEstrategiaSegmento() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		RequestSegmentoEstrategia request = new RequestSegmentoEstrategia();
		request.setIdRegion(ObjectId.get().toHexString());
		request.setIdSegmento(ObjectId.get().toHexString());
		
		UpdateResult resultado = regionesSegmentosEstrategiasDao.updateEstatusEstrategiaSegmento(request, "");
		assertThat(resultado).isNotNull();
	}
}
