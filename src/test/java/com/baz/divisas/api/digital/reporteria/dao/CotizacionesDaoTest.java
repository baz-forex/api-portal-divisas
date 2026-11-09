package com.baz.divisas.api.digital.reporteria.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.Arrays;
import java.util.List;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CotizacionesDaoTest {
	
	@InjectMocks
	private CotizacionesDao cotizacionesDao;
	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	@Mock
	private FindIterable<Document> mockFindIterable;
	@Mock
	private BitacoraServiceImpl bitacora;
	
	@Test
	void testCountOperacionesApp() {

        when(mockCollection.countDocuments(isA(Bson.class))).thenReturn(1L);
                
        long resultado = cotizacionesDao.countOperacionesApp(new Document());        
        assertThat(resultado).isEqualTo(1);

	}
	
	@Test
	void testConsultaOperacionesApp() {

        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Document()));
                
        List<Document> resultado = cotizacionesDao.consultaOperacionesApp(new Document(), false);       
        assertThat(resultado.size()).isEqualTo(1);
        
        /////////////////////
        when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
        when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Document()));

        resultado = cotizacionesDao.consultaOperacionesApp(new Document(), true);       
        assertThat(resultado.size()).isEqualTo(1);
        
        /////////////////////
        when(mockCollection.aggregate(any())).thenThrow(new MongoException("Test Exception"));
        
        resultado = cotizacionesDao.consultaOperacionesApp(new Document(), true);       
        assertThat(resultado.size()).isEqualTo(0);

	}
	
	@Test
	void testConsultaIncidenciasApp() {

		Document incidencia = new Document("_id", new ObjectId());
		Document cu = new Document("pais","").append("canal","").append("sucursal","").append("folio","");
		incidencia.put("idClienteUnico", cu);
		
        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList(incidencia));
                
        List<Document> resultado = cotizacionesDao.consultaIncidenciasApp();     
        assertThat(resultado.size()).isEqualTo(1);
        
        
        /////////////////////
        when(mockCollection.find(isA(Bson.class))).thenThrow(new MongoException("Test Exception"));
        
        resultado = cotizacionesDao.consultaIncidenciasApp();     
        assertThat(resultado.size()).isEqualTo(0);

	}
	
	@Test
	void testActualizaEstatusCotizacion() {

		Document incidencia = new Document("_id", new ObjectId());
		Document cu = new Document("pais","").append("canal","").append("sucursal","").append("folio","");
		incidencia.put("idClienteUnico", cu);
		
		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
	    when(mockAggIterable.first()).thenReturn(new Document());
	    
	    UpdateResult updateResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		
        when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(updateResult);

                
        boolean resultado = cotizacionesDao.actualizaEstatusCotizacion(new ObjectId().toHexString(), "412", "");  
        assertThat(resultado).isFalse();
        
        
        /////////////////////        
	    updateResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		
        when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(updateResult);
        doNothing().when(bitacora).registrar(isA(AccionBitacora.class), isA(String.class));
                
        resultado = cotizacionesDao.actualizaEstatusCotizacion(new ObjectId().toHexString(), "412", "");  
        assertThat(resultado).isTrue();
        
        /////////////////////        

        when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenThrow(new MongoException("Test Exception"));
        
        resultado = cotizacionesDao.actualizaEstatusCotizacion(new ObjectId().toHexString(), "413", "");  
        assertThat(resultado).isFalse();

	}
	
}
