package com.baz.divisas.api.tef.limites.segmentos;

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
import com.baz.divisas.api.tef.limites.dao.LimitesPorSegmentoDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSegmentoDto;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LimitesPorSegmentoDaoTest {

	@InjectMocks 
	private LimitesPorSegmentoDao limitesPorSegmentoDao;

	@Mock 
	private MongoCollection<LimitesPorSegmentoDto> mockCollection;
	@Mock
	private FindIterable<LimitesPorSegmentoDto> mockFindIterable;
	@Mock
	private AggregateIterable<LimitesPorSegmentoDto> mockAggIterable;
	@Mock
	private InsertOneResult mockInsertOneResult;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private DeleteResult mockDeleteResult;

	
	@Test
	void testFindLimitesSegmento() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));

		List<LimitesPorSegmentoDto> resultado = limitesPorSegmentoDao.findLimitesSegmento();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesSegmentoId() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

		LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdSegmento(ObjectId.get().toHexString());

		List<LimitesPorSegmentoDto> resultado = limitesPorSegmentoDao.findLimitesSegmentoId(request, true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertLimitesSegmento() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdSegmento(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));

		UpdateResult resultado = limitesPorSegmentoDao.insertLimitesSegmento(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateLimitesSegmento() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdSegmento(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorSegmentoDao.updateLimitesSegmento(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusLimitesSegmento() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdSegmento(ObjectId.get().toHexString());

		UpdateResult resultado = limitesPorSegmentoDao.updateEstatusLimitesSegmento(request);
		assertThat(resultado).isNotNull();
	}
	
	//OLP
	@Test
	void testFindLimitesSegmentoOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorSegmentoDto()));

		List<LimitesPorSegmentoDto> resultado = limitesPorSegmentoDao.findLimitesSegmentoOlp();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesSegmentoIdOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

		LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdSegmento(ObjectId.get().toHexString());

		List<LimitesPorSegmentoDto> resultado = limitesPorSegmentoDao.findLimitesSegmentoIdOlp(request, true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertLimitesSegmentoOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdSegmento(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));

		UpdateResult resultado = limitesPorSegmentoDao.insertLimitesSegmentoOlp(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateLimitesSegmentoOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdSegmento(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorSegmentoDao.updateLimitesSegmentoOlp(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusLimitesSegmentoOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		LimitesPorSegmentoDto request = new LimitesPorSegmentoDto();
    	request.setIdSegmento(ObjectId.get().toHexString());

		UpdateResult resultado = limitesPorSegmentoDao.updateEstatusLimitesSegmentoOlp(request);
		assertThat(resultado).isNotNull();
	}
	
}
