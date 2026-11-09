package com.baz.divisas.api.catalogos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.catalogos.model.CatRangoEdadesCampanias;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.general.model.CatalogoDivisas;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CatalogosDaoTest {

	@InjectMocks 
	private CatalogosDao catalogosDao;

	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private FindIterable<Document> mockFindIterable;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	
	@Test
	void testFindCatPaisNacionalidad() {

        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
                
        List<CatPaisNacionalidad> resultado = catalogosDao.findCatPaisNacionalidad();        
        assertThat(resultado.size()).isEqualTo(0);
        
        ///////////////////////////////

        when(mockFindIterable.into(any())).thenReturn(null);           
        resultado = catalogosDao.findCatPaisNacionalidad();        
        assertThat(resultado.size()).isEqualTo(0);
	}
	
	@Test
	void testFindCatEstados() {

        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
                
        List<Catalogo> resultado = catalogosDao.findCatEstados();      
        assertThat(resultado.size()).isEqualTo(0);
        
        ///////////////////////////////

        when(mockFindIterable.into(any())).thenReturn(null);           
        resultado = catalogosDao.findCatEstados();        
        assertThat(resultado.size()).isEqualTo(0);
	}
	
	@Test
	void testFindCatDivisas() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
                
        List<Catalogo> resultado = catalogosDao.findCatDivisas();        
        assertThat(resultado.size()).isEqualTo(0);
        
        ///////////////////////////////

        when(mockFindIterable.into(any())).thenReturn(null);           
        resultado = catalogosDao.findCatDivisas();        
        assertThat(resultado.size()).isEqualTo(0);
        
        catalogosDao = new CatalogosDao();
	}
	
	@Test
	public void testfindCatDivisasLimites() {
	
		 when(mockCollection.find()).thenReturn(mockFindIterable);
	     when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		 List<CatalogoDivisas> resultado = catalogosDao.findCatDivisasLimites(); 
		 assertThat(resultado.size()).isEqualTo(0);
	}
	
	@Test
	public void testfindCatDivisasId() {
	
		 when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
	     when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		 List<Catalogo> resultado = catalogosDao.findCatDivisasId("");
		 assertThat(resultado.size()).isEqualTo(0);
	}
	
	
	@Test
	void testActualizarEstatusCatDivisas() {

		UpdateResult updateResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());		

        when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(updateResult);
                
        boolean resultado = catalogosDao.actualizarEstatusCatDivisas("", true);       
        assertThat(resultado).isTrue();
        
        ///////////////////////////////
        
		updateResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());		

        when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(updateResult);
                
        resultado = catalogosDao.actualizarEstatusCatDivisas("", true);       
        assertThat(resultado).isFalse();

	}
	
	
	@Test
	public void findCatTipoOperacion() {
		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);

		List<Catalogo> resultado = catalogosDao.findCatTipoOperacion();
		assertThat(resultado.size()).isEqualTo(0);

		///////////////////////////////

		when(mockFindIterable.into(any())).thenReturn(null);
		resultado = catalogosDao.findCatTipoOperacion();
		assertThat(resultado.size()).isEqualTo(0);

	}

	
	@Test
	public void findCatTipoCliente() {
	
		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);

		List<Catalogo> resultado = catalogosDao.findCatTipoCliente();
		assertThat(resultado.size()).isEqualTo(0);

		///////////////////////////////

		when(mockFindIterable.into(any())).thenReturn(null);
		resultado = catalogosDao.findCatTipoCliente();
		assertThat(resultado.size()).isEqualTo(0);

	}
	
	
	@Test
	public void findfindCatPerfilClienteCampanias() {
	
		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);

		List<Catalogo> resultado = catalogosDao.findCatPerfilClienteCampanias();
		assertThat(resultado.size()).isEqualTo(0);

		///////////////////////////////

		when(mockFindIterable.into(any())).thenReturn(null);
		resultado = catalogosDao.findCatPerfilClienteCampanias();
		assertThat(resultado.size()).isEqualTo(0);

	}
	
	
	@Test
	public void testfindCatTipoLimiteCampanias() {
	
		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);

		List<Catalogo> resultado = catalogosDao.findCatTipoLimiteCampanias();
		assertThat(resultado.size()).isEqualTo(0);

		///////////////////////////////

		when(mockFindIterable.into(any())).thenReturn(null);
		resultado = catalogosDao.findCatTipoLimiteCampanias();
		assertThat(resultado.size()).isEqualTo(0);

	}
	
	
	@Test
	public void testfindCatTipoAcumuladoCampanias() {
	
		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);

		List<Catalogo> resultado = catalogosDao.findCatTipoAcumuladoCampanias();
		assertThat(resultado.size()).isEqualTo(0);

		///////////////////////////////

		when(mockFindIterable.into(any())).thenReturn(null);
		resultado = catalogosDao.findCatTipoAcumuladoCampanias();
		assertThat(resultado.size()).isEqualTo(0);

	}
	
	
	@Test
	public void testfindCatEstatusCampanias() {
	
		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);

		List<Catalogo> resultado = catalogosDao.findCatEstatusCampanias();
		assertThat(resultado.size()).isEqualTo(0);

		///////////////////////////////

		when(mockFindIterable.into(any())).thenReturn(null);
		resultado = catalogosDao.findCatEstatusCampanias();
		assertThat(resultado.size()).isEqualTo(0);

	}
	
	
	@Test
	public void testfindCatRangoEdadesCampanias() {
	
		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);

		List<CatRangoEdadesCampanias> resultado = catalogosDao.findCatRangoEdadesCampanias();
		assertThat(resultado.size()).isEqualTo(0);

		///////////////////////////////

		when(mockFindIterable.into(any())).thenReturn(null);
		resultado = catalogosDao.findCatRangoEdadesCampanias();
		assertThat(resultado.size()).isEqualTo(0);

	}
	
	@Test
	public void testfindCatIdentificaciones() {
		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(new ArrayList<Catalogo>());
		catalogosDao.findCatIdentificaciones();
		
	}
	
	@Test
	public void testfindCatEstatusCotizacion() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(new ArrayList<Catalogo>());
		catalogosDao.findCatEstatusCotizacion();

	}
	
	@Test
	public void testinsertDivisa() {
		catalogosDao.insertDivisa(new Document());
	}
	
	@Test
	public void testresetLimitesRegion() {
	
		catalogosDao.resetLimitesRegion("");
	}
}
