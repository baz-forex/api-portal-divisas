package com.baz.divisas.api.bitacora;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.bitacora.dao.BitacoraDao;
import com.baz.divisas.api.bitacora.dto.BitacoraRequest;
import com.baz.divisas.api.bitacora.model.Bitacora;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BitacoraDaoTest {

	@InjectMocks 
	private BitacoraDao bitacoraDao;	
	@Mock 
	private MongoCollection<Bitacora> mockCollection;
	@Mock
	private AggregateIterable<Bitacora> mockAggIterable;
	@Mock
	private FindIterable<Bitacora> mockFindIterable;

	@Test
	void testInsertBitacora() {

        when(mockCollection.insertOne(any())).thenReturn(null);
        
        bitacoraDao.insertBitacora(new Bitacora());
        
        verify(mockCollection, times(1)).insertOne(any());
	}

	@Test
	void testFind() {

        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.into(any())).thenReturn(new ArrayList<Bitacora>());
        
        BitacoraRequest request = new BitacoraRequest();
		request.setIdUsuario("");
		request.setIdModulo("");
		request.setFechaInicio("");
		request.setFechaFin("");
       
        List<Bitacora> resultado = bitacoraDao.find(request);
                
        assertThat(resultado).isNotNull();
        
        ///////////////        
		request.setFechaInicio("1");
       
        resultado = bitacoraDao.find(request);
                
        assertThat(resultado).isNotNull();
        
        ///////////////        
		request.setFechaFin("1");
		request.setIdUsuario("1");
       
        resultado = bitacoraDao.find(request);
                
        assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindById() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(new Bitacora());
        
        Bitacora resultado = bitacoraDao.findById("");
        
        assertThat(resultado).isNotNull();
	}
	
}
