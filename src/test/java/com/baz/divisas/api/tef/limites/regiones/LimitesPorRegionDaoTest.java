package com.baz.divisas.api.tef.limites.regiones;

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

import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.limites.dao.LimitesPorRegionDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LimitesPorRegionDaoTest {

	@InjectMocks 
	private LimitesPorRegionDao limitesPorRegionDao;

	@Mock 
	private MongoCollection<LimitesPorRegionDto> mockCollection;
	@Mock
	private FindIterable<LimitesPorRegionDto> mockFindIterable;
	@Mock
	private AggregateIterable<LimitesPorRegionDto> mockAggIterable;
	@Mock
	private InsertOneResult mockInsertOneResult;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private DeleteResult mockDeleteResult;

	
	@Test
	void testFindLimitesRegion() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

		List<LimitesPorRegionDto> resultado = limitesPorRegionDao.findLimitesRegion();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesRegionId() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

    	LimitesPorRegionDto request = new LimitesPorRegionDto();
    	request.setIdRegion(ObjectId.get().toHexString());

		List<LimitesPorRegionDto> resultado = limitesPorRegionDao.findLimitesRegionId(request, true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertLimitesRegion() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

    	LimitesPorRegionDto request = new LimitesPorRegionDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));

		UpdateResult resultado = limitesPorRegionDao.insertLimitesRegion(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateLimitesRegion() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

    	LimitesPorRegionDto request = new LimitesPorRegionDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorRegionDao.updateLimitesRegion(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusLimitesRegion() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

    	LimitesPorRegionDto request = new LimitesPorRegionDto();
    	request.setIdRegion(ObjectId.get().toHexString());

		UpdateResult resultado = limitesPorRegionDao.updateEstatusLimitesRegion(request);
		assertThat(resultado).isNotNull();
	}
	
	//OLP
	
	@Test
	void testFindLimitesRegionOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

		List<LimitesPorRegionDto> resultado = limitesPorRegionDao.findLimitesRegionOlp();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesRegionIdOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

    	LimitesPorRegionDto request = new LimitesPorRegionDto();
    	request.setIdRegion(ObjectId.get().toHexString());

		List<LimitesPorRegionDto> resultado = limitesPorRegionDao.findLimitesRegionIdOlp(request, true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertLimitesRegionOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

    	LimitesPorRegionDto request = new LimitesPorRegionDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));

		UpdateResult resultado = limitesPorRegionDao.insertLimitesRegionOlp(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateLimitesRegionOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

    	LimitesPorRegionDto request = new LimitesPorRegionDto();
    	request.setIdRegion(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorRegionDao.updateLimitesRegionOlp(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusLimitesRegionOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

    	LimitesPorRegionDto request = new LimitesPorRegionDto();
    	request.setIdRegion(ObjectId.get().toHexString());

		UpdateResult resultado = limitesPorRegionDao.updateEstatusLimitesRegionOlp(request);
		assertThat(resultado).isNotNull();
	}
}
