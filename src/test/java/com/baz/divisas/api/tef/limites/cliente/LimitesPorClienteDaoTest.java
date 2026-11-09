package com.baz.divisas.api.tef.limites.cliente;

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
import com.baz.divisas.api.tef.limites.dao.LimitesPorClienteDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorClienteDto;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LimitesPorClienteDaoTest {

	@InjectMocks 
	private LimitesPorClienteDao limitesPorClienteDao;

	@Mock 
	private MongoCollection<LimitesPorClienteDto> mockCollection;
	@Mock
	private FindIterable<LimitesPorClienteDto> mockFindIterable;
	@Mock
	private AggregateIterable<LimitesPorClienteDto> mockAggIterable;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private InsertOneResult mockInsertResult;


	
	@Test
	void testFindLimitesCliente() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorClienteDto()));

		List<LimitesPorClienteDto> resultado = limitesPorClienteDao.findLimitesCliente();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesClienteId() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new LimitesPorClienteDto());

		LimitesPorClienteDto resultado = limitesPorClienteDao.findLimitesClienteId(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesClienteId2() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new LimitesPorClienteDto());

		LimitesPorClienteDto resultado = limitesPorClienteDao.findLimitesClienteId(new LimitesPorClienteDto());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertLimitesCliente() {

		when(mockCollection.insertOne(isA(LimitesPorClienteDto.class))).thenReturn(mockInsertResult);

		InsertOneResult resultado = limitesPorClienteDao.insertLimitesCliente(new LimitesPorClienteDto());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateLimitesCliente() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		LimitesPorClienteDto request = new LimitesPorClienteDto();
		request.setId(ObjectId.get().toHexString());
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorClienteDao.updateLimitesCliente(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusLimitesCliente() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		LimitesPorSucursalDto request = new LimitesPorSucursalDto();
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorClienteDao.updateEstatusLimitesCliente(ObjectId.get().toHexString(), true, "");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesClienteOlp() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorClienteDto()));

		List<LimitesPorClienteDto> resultado = limitesPorClienteDao.findLimitesClienteOlp();
		assertThat(resultado).isNotNull();
	}
}
