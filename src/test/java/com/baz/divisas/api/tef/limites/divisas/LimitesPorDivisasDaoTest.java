package com.baz.divisas.api.tef.limites.divisas;

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

import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.limites.dao.LimitesPorDivisaDao;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LimitesPorDivisasDaoTest {

	@InjectMocks 
	private LimitesPorDivisaDao limitesPorDivisaDao;

	@Mock 
	private MongoCollection<Limites> mockCollection;
	@Mock
	private FindIterable<Limites> mockFindIterable;
	@Mock
	private AggregateIterable<Limites> mockAggIterable;
	@Mock
	private InsertOneResult mockInsertOneResult;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private DeleteResult mockDeleteResult;

	
	@Test
	void testFindLimitesDivias() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Limites()));

		List<Limites> resultado = limitesPorDivisaDao.findLimitesDivias();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesDiviasId() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Limites()));

		List<Limites> resultado = limitesPorDivisaDao.findLimitesDiviasId(new Limites());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertLimitesDivias() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = limitesPorDivisaDao.insertLimitesDivias(new Limites());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateLimitesDivisas() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

    	Limites request = new Limites();
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorDivisaDao.updateLimitesDivisas(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusLimitesDivisas() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

    	Limites request = new Limites();
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));

		UpdateResult resultado = limitesPorDivisaDao.updateEstatusLimitesDivisas(request);
		assertThat(resultado).isNotNull();
	}
	
	
	@Test
	void testFindLimitesDivisasOLP() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Limites()));

		List<Limites> resultado = limitesPorDivisaDao.findLimitesDivisaOLP();
		assertThat(resultado).isNotNull();
		
		limitesPorDivisaDao = new LimitesPorDivisaDao();
	}
}
