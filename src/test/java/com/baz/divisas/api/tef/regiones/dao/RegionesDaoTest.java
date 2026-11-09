package com.baz.divisas.api.tef.regiones.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

import com.baz.divisas.api.tef.general.model.RegionSegmentosSucursales;
import com.baz.divisas.api.tef.general.model.RegionSucursales;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegionesDaoTest {

	@InjectMocks 
	private RegionesDao regionesDao;

	@Mock 
	private MongoCollection<Region> mockCollection;
	@Mock
	private FindIterable<Region> mockFindIterable;
	@Mock
	private AggregateIterable<Region> mockAggIterable;
	@Mock
	private InsertOneResult mockInsertOneResult;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private DeleteResult mockDeleteResult;

	
	@Test
	void testFindAllActive() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Region()));

		List<Region> resultado = regionesDao.findAllActive();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindAllAndExcludeFields() {

		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Region()));

		List<Region> resultado = regionesDao.findAllAndExcludeFields(Arrays.asList(""));
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindById() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Region());

		Region resultado = regionesDao.findById(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByDescripcion() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Region());

		Region resultado = regionesDao.findByDescripcion("");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByRegionSegmento() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Region());

		Region resultado = regionesDao.findByRegionSegmento(ObjectId.get().toHexString(), ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByDivisa() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Region()));

		List<Region> resultado = regionesDao.findByDivisa("");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsert() {

		when(mockCollection.insertOne(isA(Region.class))).thenReturn(mockInsertOneResult);

		InsertOneResult resultado = regionesDao.insert(new Region());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateDescripcion() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.updateDescripcion(ObjectId.get().toHexString(), "");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatus() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.updateEstatus(ObjectId.get().toHexString(), true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testDelete() {

		when(mockCollection.deleteMany(isA(Bson.class))).thenReturn(mockDeleteResult);

		DeleteResult resultado = regionesDao.delete(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateSegmentos() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.updateSegmentos(ObjectId.get().toHexString(), Arrays.asList());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateSucursales() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.updateSucursales(ObjectId.get().toHexString(), Arrays.asList());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testAddDivisa() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.addDivisa(new Divisa());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusDivisaRegion() {

		when(mockCollection.findOneAndUpdate(isA(Bson.class), isA(Bson.class), isA(FindOneAndUpdateOptions.class))).thenReturn(null);

		boolean resultado = regionesDao.updateEstatusDivisaRegion(ObjectId.get().toHexString(), "", true);
		assertThat(resultado).isFalse();
		
		when(mockCollection.findOneAndUpdate(isA(Bson.class), isA(Bson.class), isA(FindOneAndUpdateOptions.class))).thenReturn(new Region());

		resultado = regionesDao.updateEstatusDivisaRegion(ObjectId.get().toHexString(), "", true);
		assertThat(resultado).isTrue();
	}
	
	@Test
	void testListRegionesSegmentosSucursal() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new RegionSegmentosSucursales()));

		List<RegionSegmentosSucursales> resultado = regionesDao.listRegionesSegmentosSucursal();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testListTCRegionesSucursales() {
		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new RegionSucursales()));

		RegionSucursales resultado = regionesDao.listTCRegionesSucursales(ObjectId.get().toHexString(),"USD");
		assertThat(resultado).isNull();
	}
	
	@Test
	void testListRegionesSegmentos() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new RegionSegmentosSucursales()));

		List<RegionSegmentosSucursales> resultado = regionesDao.listRegionesSegmentos();
		assertThat(resultado).isNotNull();
	}
	
	//OLP
		
	@Test
	void testFindAllAndExcludeFieldsOlp() {

		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Region()));

		List<Region> resultado = regionesDao.findAllAndExcludeFieldsOlp(Arrays.asList(""));
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByIdOlp() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Region());

		Region resultado = regionesDao.findByIdOlp(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByDescripcionOlp() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Region());

		Region resultado = regionesDao.findByDescripcionOlp("");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByRegionSegmentoOlp() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Region());

		Region resultado = regionesDao.findByRegionSegmentoOlp(ObjectId.get().toHexString(), ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	
	@Test
	void testInsertOlp() {

		when(mockCollection.insertOne(isA(Region.class))).thenReturn(mockInsertOneResult);

		InsertOneResult resultado = regionesDao.insertOlp(new Region());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateDescripcionOlp() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.updateDescripcionOlp(ObjectId.get().toHexString(), "");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.updateEstatusOlp(ObjectId.get().toHexString(), true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testDeleteOlp() {

		when(mockCollection.deleteMany(isA(Bson.class))).thenReturn(mockDeleteResult);

		DeleteResult resultado = regionesDao.deleteOlp(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateSegmentosOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.updateSegmentosOlp(ObjectId.get().toHexString(), Arrays.asList());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateSucursalesOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = regionesDao.updateSucursalesOlp(ObjectId.get().toHexString(), Arrays.asList());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusDivisaRegionOlp() {

		when(mockCollection.findOneAndUpdate(isA(Bson.class), isA(Bson.class), isA(FindOneAndUpdateOptions.class))).thenReturn(null);

		boolean resultado = regionesDao.updateEstatusDivisaRegionOlp(ObjectId.get().toHexString(), "", true);
		assertThat(resultado).isFalse();
		
		when(mockCollection.findOneAndUpdate(isA(Bson.class), isA(Bson.class), isA(FindOneAndUpdateOptions.class))).thenReturn(new Region());

		resultado = regionesDao.updateEstatusDivisaRegionOlp(ObjectId.get().toHexString(), "", true);
		assertThat(resultado).isTrue();
	}
	
	@Test
	void testListRegionesSegmentosSucursalOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new RegionSegmentosSucursales()));

		List<RegionSegmentosSucursales> resultado = regionesDao.listRegionesSegmentosSucursalOlp();
		assertThat(resultado).isNotNull();
	}
	

	@Test
	void testListRegionesSegmentosOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new RegionSegmentosSucursales()));

		List<RegionSegmentosSucursales> resultado = regionesDao.listRegionesSegmentosOlp();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testListTCRegionesSucursalesOlp() {
		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new RegionSucursales()));

		RegionSucursales resultado = regionesDao.listTCRegionesSucursalesOlp(ObjectId.get().toHexString());
		assertThat(resultado).isNull();
	}
}
