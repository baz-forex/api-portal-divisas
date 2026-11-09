package com.baz.divisas.api.core.beneficios.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.core.beneficios.model.CuponCRM;
import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.api.core.beneficios.model.CuponTA;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoException;
import com.mongodb.bulk.BulkWriteError;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CuponesMasSaludDaoTest {

	private CuponesBeneficiosDao cuponesDao;
	
	@Mock 
	private MongoCollection<CuponTA> coleccionCuponesTA;
	@Mock 
	private MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesTA;
	@Mock 
	private MongoCollection<CuponCRM> coleccionCuponesCRM;
	@Mock 
	private MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesCRM;
	@Mock 
	private MongoCollection<CuponMasSalud> coleccionCuponesMasSalud;
	@Mock 
	private MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesMasSalud;
	@Mock
	private FindIterable<HistoricoAltoRiesgo> mockFindIterable;
	
	@BeforeEach
	void setUp() {
		cuponesDao = new CuponesBeneficiosDao(coleccionCuponesTA, coleccionHistoricaCuponesTA,
				coleccionCuponesCRM, coleccionHistoricaCuponesCRM,
				coleccionCuponesMasSalud, coleccionHistoricaCuponesMasSalud);
	}
	
	@Test
	public void testRegistraCuponesMasSaludBulk() {
		
		CuponMasSalud cupon1 = new CuponMasSalud();
		cupon1.setId("ABC123");
		cupon1.setTipo("1");
		
		CuponMasSalud cupon2 = new CuponMasSalud();
		cupon2.setId("DEF456");
		cupon2.setTipo("1");
		
		List<CuponMasSalud> cupones = Arrays.asList(cupon1, cupon2);
		
		// Caso exitoso
		int resultado = cuponesDao.registraCuponesMasSaludBulk(cupones);
		assertThat(resultado).isEqualTo(2);
		
		/////// MongoException /////////
		doThrow(new MongoException("Test Exception")).when(coleccionCuponesMasSalud).insertMany(any(List.class), any());

		resultado = cuponesDao.registraCuponesMasSaludBulk(cupones);
		assertThat(resultado).isEqualTo(0);
	}
	
	@Test
	public void testRegistraCuponesMasSaludBulkDuplicados() {
		
		CuponMasSalud cupon1 = new CuponMasSalud();
		cupon1.setId("ABC123");
		cupon1.setTipo("1");
		
		CuponMasSalud cupon2 = new CuponMasSalud();
		cupon2.setId("DEF456");
		cupon2.setTipo("1");
		
		List<CuponMasSalud> cupones = Arrays.asList(cupon1, cupon2);
		
		// Simular MongoBulkWriteException con 1 duplicado (error code 11000)
		BulkWriteError duplicateError = new BulkWriteError(11000, "E11000 duplicate key error", new BsonDocument(), 0);
		List<BulkWriteError> writeErrors = Arrays.asList(duplicateError);
		
		MongoBulkWriteException bulkException = org.mockito.Mockito.mock(MongoBulkWriteException.class);
		when(bulkException.getWriteErrors()).thenReturn(writeErrors);
		
		doThrow(bulkException).when(coleccionCuponesMasSalud).insertMany(any(List.class), any());
		
		int resultado = cuponesDao.registraCuponesMasSaludBulk(cupones);
		// 2 cupones - 1 duplicado = 1 registrado
		assertThat(resultado).isEqualTo(1);
	}
	
	@Test
	public void testRegistraBitacoraCuponesMasSalud() {
		
		cuponesDao.registraBitacoraCuponesMasSalud("carga1", "descripcion", "1", 10);
		
		/////// Exception /////////        
		when(coleccionHistoricaCuponesMasSalud.insertOne(any(HistoricoAltoRiesgo.class))).thenThrow(new MongoException("Test Exception"));		

		cuponesDao.registraBitacoraCuponesMasSalud("carga1", "descripcion", "2", 10);
	}
	
	@Test
	void testConsultaCargaHistoricaMasSalud() {

        when(coleccionHistoricaCuponesMasSalud.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(any())).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(new ArrayList<>());
        
        List<HistoricoAltoRiesgo> resultado = cuponesDao.consultaCargaHistoricaMasSalud("1");
                
        assertThat(resultado).isEmpty(); 
	}
}
