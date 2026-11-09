package com.baz.divisas.api.tef.segmentos.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.general.model.SegmentoSucursales;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SegmentosDaoTest {

	@InjectMocks 
	private SegmentosDao segmentosDao;

	@Mock 
	private MongoCollection<Segmento> mockCollection;
	@Mock
	private FindIterable<Segmento> mockFindIterable;
	@Mock
	private AggregateIterable<Segmento> mockAggIterable;
	@Mock
	private InsertOneResult mockInsertOneResult;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private DeleteResult mockDeleteResult;

	
	@Test
	void testFindAll() {

		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Segmento()));

		List<Segmento> resultado = segmentosDao.findAll();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindAllActive() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Segmento()));

		List<Segmento> resultado = segmentosDao.findAllActive();
		assertThat(resultado).isNotNull();
	}
	
	
	@Test
	void testFindById() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Segmento());

		Segmento resultado = segmentosDao.findById(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByDescripcion() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Segmento());

		Segmento resultado = segmentosDao.findByDescripcion("");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindList() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(new ArrayList<Segmento>());

		List<Segmento> resultado = segmentosDao.findList(Arrays.asList(ObjectId.get().toHexString()));
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindListByDivisa() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Segmento()));

		List<Segmento> resultado = segmentosDao.findListByDivisa(Arrays.asList(ObjectId.get().toHexString()), "");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsert() {

		when(mockCollection.insertOne(isA(Segmento.class))).thenReturn(mockInsertOneResult);

		InsertOneResult resultado = segmentosDao.insert(new Segmento());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateDescripcion() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.updateDescripcion(ObjectId.get().toHexString(), "");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatus() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.updateEstatus(ObjectId.get().toHexString(), true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testDelete() {

		when(mockCollection.deleteMany(isA(Bson.class))).thenReturn(mockDeleteResult);

		DeleteResult resultado = segmentosDao.delete(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}

	@Test
	void testUpdateSucursales() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.updateSucursales(ObjectId.get().toHexString(), Arrays.asList());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testAddDivisa() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.addDivisa(new Divisa());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateSegmentosAsignados() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.updateSegmentosAsignados(Arrays.asList(ObjectId.get().toHexString()), true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindSucursalesSegmento() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Segmento()));

		List<Segmento> resultado = segmentosDao.findSucursalesSegmento();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testListTCSegmentoSucursales() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new SegmentoSucursales()));

		SegmentoSucursales resultado = segmentosDao.listTCSegmentoSucursales("63fd51d4b39dd0317ac9d2c8", "USD");
		assertThat(resultado).isNull();
	}
	
	
	@Test
	void testListSegmentosSucursal() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Segmento()));

		List<Segmento> resultado = segmentosDao.listSegmentosSucursal();
		assertThat(resultado).isNotNull();
	}
	
	//OLP
	
	@Test
	void testListTCSegmentoSucursalesOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new SegmentoSucursales()));

		SegmentoSucursales resultado = segmentosDao.listTCSegmentoSucursalesOlp("63fd51d4b39dd0317ac9d2c8");
		assertThat(resultado).isNull();
	}
	
	
	@Test
	void testListSegmentosSucursalOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Segmento()));

		List<Segmento> resultado = segmentosDao.listSegmentosSucursalOlp();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindAllOlp() {

		when(mockCollection.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.projection(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(Arrays.asList(new Segmento()));

		List<Segmento> resultado = segmentosDao.findAllOlp();
		assertThat(resultado).isNotNull();
	}	
	
	@Test
	void testFindByIdOlp() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Segmento());

		Segmento resultado = segmentosDao.findByIdOlp(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindByDescripcionOlp() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(new Segmento());

		Segmento resultado = segmentosDao.findByDescripcionOlp("");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindListOlp() {

		when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(new ArrayList<Segmento>());

		List<Segmento> resultado = segmentosDao.findListOlp(Arrays.asList(ObjectId.get().toHexString()));
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testInsertOlp() {

		when(mockCollection.insertOne(isA(Segmento.class))).thenReturn(mockInsertOneResult);

		InsertOneResult resultado = segmentosDao.insertOlp(new Segmento());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateDescripcionOlp() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.updateDescripcionOlp(ObjectId.get().toHexString(), "");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateEstatusOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.updateEstatusOlp(ObjectId.get().toHexString(), true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testDeleteOlp() {

		when(mockCollection.deleteMany(isA(Bson.class))).thenReturn(mockDeleteResult);

		DeleteResult resultado = segmentosDao.deleteOlp(ObjectId.get().toHexString());
		assertThat(resultado).isNotNull();
	}

	@Test
	void testUpdateSucursalesOlp() {

		when(mockCollection.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.updateSucursalesOlp(ObjectId.get().toHexString(), Arrays.asList());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testUpdateSegmentosAsignadosOlp() {

		when(mockCollection.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);

		UpdateResult resultado = segmentosDao.updateSegmentosAsignadosOlp(Arrays.asList(ObjectId.get().toHexString()), true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	void testFindSucursalesSegmentoOlp() {

		when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
		when(mockAggIterable.allowDiskUse(true)).thenReturn(mockAggIterable);
		when(mockAggIterable.into(any())).thenReturn(Arrays.asList(new Segmento()));

		List<Segmento> resultado = segmentosDao.findSucursalesSegmentoOlp();
		assertThat(resultado).isNotNull();
	}
}
