package com.baz.divisas.api.tef.limites.sucursal;

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

import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.limites.dao.LimitesPorSucursalDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LimitesPorSucursalDaoTest {

	@InjectMocks 
	private LimitesPorSucursalDao limitesPorSucursalDao;

	@Mock 
	private MongoCollection<LimitesPorSucursalDto> mockCollection;
	@Mock
	private AggregateIterable<LimitesPorSucursalDto> mockAggIterable;
	@Mock
	private UpdateResult mockUpdateResult;


	
	@Test
	void testFindLimitesSucursal() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

		List<LimitesPorSucursalDto> resultado = limitesPorSucursalDao.findLimitesSucursal();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesSucursalId() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

		List<LimitesPorSucursalDto> resultado = limitesPorSucursalDao.findLimitesSucursalId(new LimitesPorSucursalDto());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertLimitesSucursal() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = limitesPorSucursalDao.insertLimitesSucursal(new LimitesPorSucursalDto());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateLimitesSucursal() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		LimitesPorSucursalDto request = new LimitesPorSucursalDto();
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorSucursalDao.updateLimitesSucursal(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusLimitesSucursal() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		LimitesPorSucursalDto request = new LimitesPorSucursalDto();
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
    	request.setDiario(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	request.setMensual(new MontosLimite(0.0, 0.0,0.0, 0.0));
    	
		UpdateResult resultado = limitesPorSucursalDao.updateEstatusLimitesSucursal(request);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindLimitesSucursalOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new LimitesPorRegionDto()));

		List<LimitesPorSucursalDto> resultado = limitesPorSucursalDao.findLimitesSucursalOlp();
		assertThat(resultado).isNotNull();
	}
	
}
