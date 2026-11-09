package com.baz.divisas.api.tef.spots;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
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

import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.spots.dao.SpotsDao;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpotsDaoTest {

	@InjectMocks 
	private SpotsDao spotsDao;

	@Mock 
	private MongoCollection<Spot> mockCollection;
	@Mock
	private FindIterable<Spot> mockFindIterable;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private InsertOneResult mockInsertResult;
	@Mock
	PromoEspacioServiceImpl promoEspacioImpl;

	
	@Test
	void testFindSpots() {

        when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
                
        List<Spot> resultado = spotsDao.findSpots();      
        assertThat(resultado.size()).isEqualTo(0);

	}
	
	@Test
	void testFindSpotsPorFecha() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
                
        List<Spot> resultado = spotsDao.findSpotsPorFecha("", "", Arrays.asList(""));   
        assertThat(resultado.size()).isEqualTo(0);

	}
	
	@Test
	void testSpotVigenteDivisa() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
                
        Spot resultado = spotsDao.findSpotVigenteDivisa("");     
        assertThat(resultado).isNull();

	}
	
	@Test
	void testInsertSpots() {

        when(mockCollection.insertOne(isA(Spot.class))).thenReturn(mockInsertResult);
                
        spotsDao.insertSpots(Arrays.asList(new Spot()));    
	}	
	
	@Test
	void testInsertSpot() {

        when(mockCollection.insertOne(isA(Spot.class))).thenReturn(mockInsertResult);
                
        InsertOneResult resultado = spotsDao.insertSpot(new Spot());    
        assertThat(resultado).isNotNull();

	}
	
	@Test
	void testUpdateIdBitacoraAlta() {

        when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);
                
        UpdateResult resultado = spotsDao.updateIdBitacoraAlta(new ObjectId().toHexString(), "");  
        assertThat(resultado).isNotNull();

	}
	
	@Test
	void testUpdateTCSpotRegion() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = spotsDao.updateTCSpotRegion(ObjectId.get().toHexString(), "", "", 0.0);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateTCSpotRegiones() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = spotsDao.updateTCSpotRegiones(Arrays.asList(ObjectId.get().toHexString()), "", "", 0.0);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateTCSpotSegmento() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = spotsDao.updateTCSpotSegmento(ObjectId.get().toHexString(), "", "", 0.0);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateTCSpotSegmentos() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = spotsDao.updateTCSpotSegmentos(Arrays.asList(ObjectId.get().toHexString()), "", "", 0.0);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateTCSpotSucursales() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = spotsDao.updateTCSpotSucursales("","", "", 0.0);
		assertThat(resultado).isNull();
	}
	
	@Test
	void testUpdateTCSpotSucursalesOLP() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = spotsDao.updateTCSpotSucursalesOLP("","", 0.0);
		assertThat(resultado).isNull();
		
		spotsDao = new SpotsDao();
	}
	
}
