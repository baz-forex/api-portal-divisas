package com.baz.divisas.api.core.reporteria.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OperacionesDaoTest {
	
	@InjectMocks
	private OperacionesDao operacionesDao;
	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	@Mock
	private FindIterable<Document> mockFindIterable;
	
	@Test
	void testCount() {

        when(mockCollection.countDocuments(isA(Bson.class))).thenReturn(1L);
                
        long resultado = operacionesDao.count(new Document());        
        assertThat(resultado).isEqualTo(1);

	}
	
	@Test
	void testAcumulado() {

        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
        when(mockAggIterable.into(any())).thenReturn(Arrays.asList());
                
        Document resultado = operacionesDao.acumulado(new Document());       
        assertThat(resultado).isNotNull();
        
        /////////////////////
        when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Document("operaciones",0).append("monto_divisa", 0D).append("monto_mxn", 0D)));
        
        resultado = operacionesDao.acumulado(new Document());       
        assertThat(resultado).isNotNull();

        /////////////////////
        when(mockCollection.aggregate(any())).thenThrow(new MongoException("Test Exception"));
        
        resultado = operacionesDao.acumulado(new Document());       
        assertThat(resultado).isNotNull();
	}
	
	@Test
	void testConsulta() {

        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
                
        Iterable<Document> resultado = operacionesDao.consulta(new Document(), false);       
        assertThat(resultado).isNotNull();
        
        /////////////////////
        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.allowDiskUse(true)).thenReturn(mockFindIterable);

        resultado = operacionesDao.consulta(new Document(), true);       
        assertThat(resultado).isNotNull();

	}
	
}
