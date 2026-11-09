package com.baz.divisas.api.digital.configuraciones.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConfiguracionesDigitalDaoTest {

	@InjectMocks 
	private ConfiguracionesDigitalDao configDao;	
	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private FindIterable<Document> mockFindIterable;
	
	@Test
	void testFindParametriaOperacion() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);
        
        Document resultado = configDao.findParametria("", "");                
        assertThat(resultado).isNull();
        
        //////////////////
        when(mockFindIterable.first()).thenReturn(new Document());

        resultado = configDao.findParametria("", "");                
        assertThat(resultado).isNull();
        
        //////////////////
        when(mockFindIterable.first()).thenReturn(new Document("test", new Document()));

        resultado = configDao.findParametria("test", "");                
        assertThat(resultado).isNotNull();
	}
		
	@Test
	void testUpdateParametriaOperacion() {

        when(mockCollection.findOneAndUpdate(isA(Bson.class), isA(Bson.class))).thenReturn(new Document());
        
        Document resultado = configDao.updateParametria("", "", new Document());                
        assertThat(resultado).isNotNull();

	}
}
