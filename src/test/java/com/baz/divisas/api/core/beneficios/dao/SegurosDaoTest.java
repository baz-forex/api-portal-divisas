package com.baz.divisas.api.core.beneficios.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
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

import com.baz.divisas.api.core.beneficios.model.Seguro;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SegurosDaoTest {

	@InjectMocks 
	private SegurosDao segurosDao;	
	@Mock 
	private MongoCollection<Seguro> mockCollection;
	@Mock
	private FindIterable<Seguro> mockFindIterable;
	
	@Test
	void testFind() {

        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList());
        
        List<Seguro> resultado = segurosDao.find();
        
        assertThat(resultado).isNotNull();     
	}
	
	@Test
	void testFindByEstatus() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList());
        
        List<Seguro> resultado = segurosDao.findByEstatus("");
        
        assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindById() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(new Seguro());
        
        Seguro resultado = segurosDao.findById(ObjectId.get().toHexString());
        
        assertThat(resultado).isNotNull();
	}
		
	@Test
	void testInsert() {

        when(mockCollection.insertOne(isA(Seguro.class))).thenReturn(null);        
        InsertOneResult resultado = segurosDao.insert(new Seguro());
        
        assertThat(resultado).isNull();
	}
	
	@Test
	void testUpdate() {

        when(mockCollection.findOneAndUpdate(isA(Bson.class), isA(Bson.class))).thenReturn(new Seguro());
        
        Seguro seguro = new Seguro();
        seguro.setId(ObjectId.get().toHexString());
        Seguro resultado = segurosDao.update(seguro);
        
        assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatus() {

        when(mockCollection.findOneAndUpdate(isA(Bson.class), isA(Bson.class))).thenReturn(new Seguro());        
        Seguro resultado = segurosDao.updateEstatus(ObjectId.get().toHexString(), "");
        
        assertThat(resultado).isNotNull();
	}
	
}
