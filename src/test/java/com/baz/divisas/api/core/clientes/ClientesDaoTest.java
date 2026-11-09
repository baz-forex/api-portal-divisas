package com.baz.divisas.api.core.clientes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
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
import com.baz.divisas.api.core.clientes.dao.ClientesDao;
import com.baz.divisas.api.core.clientes.dto.ReqBloqueoClientes;
import com.baz.divisas.api.core.clientes.model.BitacoraActualizacion;
import com.baz.divisas.api.core.clientes.model.BitacoraCargaMasiva;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientesDaoTest {

	@InjectMocks 
	private ClientesDao clientesDao;	
	@Mock
	private BitacoraServiceImpl bitacora;
	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	@Mock
	private FindIterable<Document> mockFindIterable;
	@Mock
	private InsertManyResult insertManyResult;
	@Mock
	private InsertOneResult insertOneResult;
	
	@Test
	void testFindBitacoraActCliente() {

        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new ArrayList<BitacoraActualizacion>()));
        
        List<BitacoraActualizacion> resultado = clientesDao.findBitacoraActCliente("");
                
        assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertBitacoraActClientes() {

        when(mockCollection.insertMany(any())).thenReturn(insertManyResult);
        
        clientesDao.insertBitacoraActClientes(Arrays.asList(new BitacoraActualizacion()));
        
        verify(mockCollection, times(1)).insertMany(any());
	}

	@Test
	void testInsertBitacoraCargaMasiva() {

        when(mockCollection.insertOne(any())).thenReturn(insertOneResult);
        
        clientesDao.insertBitacoraCargaMasiva(new BitacoraCargaMasiva());
        
        verify(mockCollection, times(1)).insertOne(any());
	}
	
	@Test
	void testFindBitacoraCargaMasiva() {

        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(new ArrayList<BitacoraCargaMasiva>());
        
        List<BitacoraCargaMasiva> resultado = clientesDao.findBitacoraCargaMasiva();
                
        assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindCargaMasivaById() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);
        
        BitacoraCargaMasiva resultado = clientesDao.findCargaMasivaById("642db981de21c87ee72bb147");
                
        assertThat(resultado).isNull();
	}
	
	@Test
	void testCountAgrupados() {

        when(mockCollection.countDocuments()).thenReturn(1L);
        
        long resultado = clientesDao.countAgrupados();
                
        assertThat(resultado).isEqualTo(1L);
	}
	
	@Test
	void testCountAgrupadosCliente() {

        when(mockCollection.countDocuments(isA(Bson.class))).thenReturn(1L);
        
        long resultado = clientesDao.countAgrupadosCliente("");
                
        assertThat(resultado).isEqualTo(1L);
	}
	
	@Test
	void testConsultaClientesAgrupados() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.skip(isA(int.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.limit(isA(int.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Document()));
        
        List<Document> resultado = clientesDao.consultaClientesAgrupados(null, 1, 1);
                
        assertThat(resultado.size()).isEqualTo(1);
        
        ////////////
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Document("_id", new ObjectId("644976e00c0fa1622784ab58"))));
        resultado = clientesDao.consultaClientesAgrupados("", 1, 1);
        
        assertThat(resultado.size()).isEqualTo(1);
        
        ////////////
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Document("_id", new ObjectId("644976e00c0fa1622784ab58"))));
        resultado = clientesDao.consultaClientesAgrupados("123", 1, 1);
        
        assertThat(resultado.size()).isEqualTo(1);
        
        ////////////
        when(mockCollection.find(isA(Bson.class))).thenThrow( new MongoException("Test Exception"));
        resultado = clientesDao.consultaClientesAgrupados("123", 1, 1);
        
        assertThat(resultado.size()).isEqualTo(0);
	}
	
	@Test
	void testValidaClientesAgrupados() {

        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.first()).thenReturn(new Document());

        
        Document resultado = clientesDao.validaClientesAgrupados(Arrays.asList());
                
        assertThat(resultado).isNotNull();        
	}
	
	@Test
	void testValidaClientesAgrupadosActualizacion() {

        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.first()).thenReturn(new Document());
        
        Document resultado = clientesDao.validaClientesAgrupados("644976e00c0fa1622784ab58", Arrays.asList());
                
        assertThat(resultado).isNotNull();        
	}
	
	@Test
	void testRegistraClientesAgrupados() {

        when(mockCollection.insertOne(isA(Document.class))).thenReturn(null);
        doNothing().when(bitacora).registrar(isA(AccionBitacora.class), isA(String.class));
        
        boolean resultado = clientesDao.registraClientesAgrupados(Arrays.asList());
                
        assertThat(resultado).isTrue();
        
        ////////////////        
        when(mockCollection.insertOne(isA(Document.class))).thenThrow(new MongoException("Test Exception"));
        
        resultado = clientesDao.registraClientesAgrupados(Arrays.asList());
                
        assertThat(resultado).isFalse();
	}
	
	@Test
	void testActualizaClientesAgrupados() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);
        
        boolean resultado = clientesDao.actualizaClientesAgrupados("644976e00c0fa1622784ab58", Arrays.asList());
                
        assertThat(resultado).isFalse(); 
        
        ////////////////        
        when(mockFindIterable.first()).thenReturn(new Document());
        when(mockCollection.findOneAndUpdate(isA(Bson.class), isA(Bson.class), isA(FindOneAndUpdateOptions.class))).thenReturn(new Document());
        doNothing().when(bitacora).registrar(isA(AccionBitacora.class), isA(String.class));
        
        resultado = clientesDao.actualizaClientesAgrupados("644976e00c0fa1622784ab58", Arrays.asList());
        
        assertThat(resultado).isTrue(); 
        
        /////// Exception /////////        
        when(mockFindIterable.first()).thenThrow(new MongoException("Test Exception"));

        resultado = clientesDao.actualizaClientesAgrupados("644976e00c0fa1622784ab58", Arrays.asList());
        
        assertThat(resultado).isFalse(); 
	}
	
	@Test
	void testEliminaClientesAgrupados() {

        when(mockCollection.findOneAndDelete(isA(Bson.class))).thenReturn(null);
        
        Document resultado = clientesDao.eliminaClientesAgrupados("644976e00c0fa1622784ab58");
                
        assertThat(resultado).isNull();
        
        ////////////////                
        when(mockCollection.findOneAndDelete(isA(Bson.class))).thenReturn(new Document());
        doNothing().when(bitacora).registrar(isA(AccionBitacora.class), isA(String.class));
        
        resultado = clientesDao.eliminaClientesAgrupados("644976e00c0fa1622784ab58");
                
        assertThat(resultado).isNotNull();
        
        /////// Exception /////////        
        when(mockCollection.findOneAndDelete(isA(Bson.class))).thenThrow(new MongoException("Test Exception"));
        
        resultado = clientesDao.eliminaClientesAgrupados("644976e00c0fa1622784ab58");
                
        assertThat(resultado).isNull();
	}
	
	@Test
	void testConsultaBloqueosOperativos() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Document("_id", new ObjectId())));

		List<ReqBloqueoClientes> resultado = clientesDao.consultaBloqueosOperativos("");

		assertThat(resultado.size()).isEqualTo(1);

        /////// Exception /////////        
		when(mockAggIterable.into(any())).thenThrow(new MongoException("Test Exception"));
		resultado = clientesDao.consultaBloqueosOperativos("");

		assertThat(resultado.size()).isEqualTo(0);
	}
		
	@Test
	void testBuscaBloqueoOperativoPorId() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(null);

		ReqBloqueoClientes resultado = clientesDao.buscaBloqueoOperativoPorId("620ae964880eb2a2f61f6fae");
		assertThat(resultado).isNull();

		////////////				
		when(mockFindIterable.first()).thenReturn(new Document("datos_bloqueo", new Document()));

		resultado = clientesDao.buscaBloqueoOperativoPorId("620ae964880eb2a2f61f6fae");
		assertThat(resultado).isNotNull();

	}
	
	@Test
	void testBuscaBloqueoOperativo() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);		
		when(mockFindIterable.first()).thenReturn(new Document("datos_bloqueo", new Document()));

		Document resultado = clientesDao.buscaBloqueoOperativo("", new Document());
		assertThat(resultado).isNotNull();

	}
	
	@Test
	void testRegistraBloqueoOperativo() {

		InsertOneResult insertResult = InsertOneResult.acknowledged(null);
		
		when(mockCollection.insertOne(isA(Document.class))).thenReturn(insertResult);		

		boolean resultado = clientesDao.registraBloqueoOperativo("", new Document(), "");
		assertThat(resultado).isFalse();
		
		////////////				
		insertResult = InsertOneResult.acknowledged(new BsonObjectId());

		when(mockCollection.insertOne(isA(Document.class))).thenReturn(insertResult);	
		when(bitacora.registrar(isA(AccionBitacora.class), isA(String.class), any(), isA(Document.class))).thenReturn("");

		resultado = clientesDao.registraBloqueoOperativo("", new Document(), "");
		assertThat(resultado).isTrue();
		
        /////// Exception /////////        
		when(mockCollection.insertOne(isA(Document.class))).thenThrow(new MongoException("Test Exception"));

		resultado = clientesDao.registraBloqueoOperativo("", new Document(), "");
		assertThat(resultado).isFalse();
	
	}
	
	@Test
	void testEliminaBloqueoOperativo() {

		when(mockCollection.findOneAndDelete(isA(Bson.class))).thenReturn(null);		

		boolean resultado = clientesDao.eliminaBloqueoOperativo("620ae964880eb2a2f61f6fae", "");
		assertThat(resultado).isFalse();
		
		////////////				
		when(mockCollection.findOneAndDelete(isA(Bson.class))).thenReturn(new Document());		
		when(bitacora.registrar(isA(AccionBitacora.class), isA(String.class), any(), any())).thenReturn("");

		resultado = clientesDao.eliminaBloqueoOperativo("620ae964880eb2a2f61f6fae", "");
		assertThat(resultado).isTrue();
		
        /////// Exception /////////        
		when(mockCollection.findOneAndDelete(isA(Bson.class))).thenThrow(new MongoException("Test Exception"));	

		resultado = clientesDao.eliminaBloqueoOperativo("620ae964880eb2a2f61f6fae", "");
		assertThat(resultado).isFalse();
	}
	
	
	@Test
	public void testRegistraClienteAltoRiesgo() {
		InsertOneResult insertResult = InsertOneResult.acknowledged(null);
		
		when(mockCollection.insertOne(isA(Document.class))).thenReturn(insertResult);		

		boolean resultado = clientesDao.registraClienteAltoRiesgo(null);
		assertThat(resultado).isTrue();
		
	}
	
	@Test
	public void testRegistraBitacoraClienteAltoRiesgo() {
		InsertOneResult insertResult = InsertOneResult.acknowledged(null);
		
		when(mockCollection.insertOne(isA(Document.class))).thenReturn(insertResult);		

		clientesDao.registraBitacoraClienteAltoRiesgo(null, null, 0); 
		
	}
	
	@Test
	void testConsultaCargaHistoricaAltoRiesgo() {

        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(any())).thenReturn(mockFindIterable);
        
        List<HistoricoAltoRiesgo> resultado = clientesDao.consultaCargaHistoricaAltoRiesgo();
                
        assertThat(resultado).isEmpty(); 
	}
}
