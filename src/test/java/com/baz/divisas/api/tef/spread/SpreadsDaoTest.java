package com.baz.divisas.api.tef.spread;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.tef.spread.dao.SpreadsDao;
import com.baz.divisas.api.tef.spread.dto.Spread;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpreadsDaoTest {

	@InjectMocks 
	private SpreadsDao spreadsDao;

	@Mock 
	private MongoCollection<Spread> mockCollection;
	@Mock
	private FindIterable<Spread> mockFindIterable;
	@Mock
	private UpdateResult mockUpdateResult;

	
	@Test
	void testFindSpreads() {

        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
                
        List<Spread> resultado = spreadsDao.findSpreads();      
        assertThat(resultado.size()).isEqualTo(0);

	}
	
	@Test
	void testFindSpreadsDivisa() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(new Spread());
                
        Spread resultado = spreadsDao.findSpreadDivisa("");   
        assertThat(resultado).isNotNull();

	}
	
	@Test
	void testUpdateSpreadDivisa() {

        when(mockCollection.replaceOne(isA(Bson.class), isA(Spread.class))).thenReturn(mockUpdateResult);
                
        UpdateResult resultado = spreadsDao.updateSpreadDivisa(new Spread());  
        assertThat(resultado).isNotNull();

	}

	
}
