package com.baz.divisas.api.core.beneficios.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CuponesTiempoAireDaoTest {

	@InjectMocks 
	private CuponesBeneficiosDao cuponesDao;	
	
	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	@Mock
	private FindIterable<Document> mockFindIterable;
	
	
	@Test
	public void testRegistraClienteAltoRiesgo() {
		InsertOneResult insertResult = InsertOneResult.acknowledged(null);
		
		when(mockCollection.insertOne(isA(Document.class))).thenReturn(insertResult);		

		boolean resultado = cuponesDao.registraCuponTA(null);
		assertThat(resultado).isTrue();
		
		when(mockCollection.insertOne(any())).thenThrow(new MongoException("Test Exception"));		

		resultado = cuponesDao.registraCuponTA(null);
		assertThat(resultado).isFalse();
	}
	
	@Test
	public void testRegistraBitacoraClienteAltoRiesgo() {
		InsertOneResult insertResult = InsertOneResult.acknowledged(null);
		
		when(mockCollection.insertOne(isA(Document.class))).thenReturn(insertResult);		

		cuponesDao.registraBitacoraCuponesTA(null, null, 0);
		
		/////// Exception /////////        
		when(mockCollection.insertOne(any())).thenThrow(new MongoException("Test Exception"));		

		cuponesDao.registraBitacoraCuponesTA(null, null, 0);
		
		cuponesDao = new CuponesBeneficiosDao();
	}
	
	@Test
	void testConsultaCargaHistoricaAltoRiesgo() {

        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(any())).thenReturn(mockFindIterable);
        
        List<HistoricoAltoRiesgo> resultado = cuponesDao.consultaCargaHistorica();
                
        assertThat(resultado).isEmpty(); 
	}
}
