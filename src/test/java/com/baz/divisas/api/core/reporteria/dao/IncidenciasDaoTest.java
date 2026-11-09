package com.baz.divisas.api.core.reporteria.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.core.reporteria.dto.ReqBusquedaIncidencias;
import com.baz.divisas.api.core.reporteria.model.Incidencia;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class IncidenciasDaoTest {
	
	@InjectMocks
	private IncidenciasDao incidenciasDao;
	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	@Mock
	private FindIterable<Document> mockFindIterable;
	
	
	@Test
	void testConsulta() {

        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
        when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Document("_id", new ObjectId("631143f15876135747a5f9e3"))));
                
        List<Incidencia> resultado = incidenciasDao.consulta(new ReqBusquedaIncidencias(), false);       
        assertThat(resultado.size()).isEqualTo(1);
        
        /////////////////////
        resultado = incidenciasDao.consulta(new ReqBusquedaIncidencias(), true);       
        assertThat(resultado.size()).isEqualTo(1);

	}
	
	@Test
	void testConsultaPorId() {
                
        Incidencia resultado = incidenciasDao.consultaPorId("");       
        assertThat(resultado).isNull();
        
        /////////////////////
        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
        when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Document()));
                
        resultado = incidenciasDao.consultaPorId("631143f15876135747a5f9e3");       
        assertThat(resultado).isNotNull();

	}
}
