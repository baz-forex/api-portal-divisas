package com.baz.divisas.api.tef.catalogos;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.promoespacio.model.BeanDistanciaSucursales;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

/**
 * Pruebas unitarias CatalogosDao.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CatalogosDaoTest {

	private CatalogosDao catalogosDao;

	@Mock
	private MongoCollection<Document> coleccionCatTipoOperacion;
	@Mock
	private MongoCollection<Document> colleccionCatMonedas;
	@Mock
	private MongoCollection<Document> colleccionUbicacionSucursales;
	@Mock
	private FindIterable<Document> mockFindIterable;
	@Mock
	private ParametriaDao parametria;

	@BeforeEach
	void setUp() {
		// Se instancia sin invocar el constructor real para no abrir conexion a Mongo.
		catalogosDao = org.mockito.Mockito.mock(CatalogosDao.class,
				org.mockito.Mockito.CALLS_REAL_METHODS);
		ReflectionTestUtils.setField(catalogosDao, "coleccionCatTipoOperacion", coleccionCatTipoOperacion);
		ReflectionTestUtils.setField(catalogosDao, "colleccionCatMonedas", colleccionCatMonedas);
		ReflectionTestUtils.setField(catalogosDao, "colleccionUbicacionSucursales", colleccionUbicacionSucursales);
		ReflectionTestUtils.setField(catalogosDao, "parametria", parametria);
	}

	@Test
	void testFindTiposOperacion() {

		when(coleccionCatTipoOperacion.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(new ArrayList<Document>());

		List<Catalogo> resultado = catalogosDao.findTiposOperacion();
		assertThat(resultado).isEmpty();
	}

	@Test
	void testFindTiposOperacionConExcepcion() {

		// Fuerza la rama catch(MongoException)
		when(coleccionCatTipoOperacion.find()).thenThrow(new MongoException("error"));

		List<Catalogo> resultado = catalogosDao.findTiposOperacion();
		assertThat(resultado).isEmpty();
	}

	@Test
	void testFindTipoOperacionById() {

		Document doc = new Document("_id", "410").append("activo", true);
		when(coleccionCatTipoOperacion.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(doc);

		Document resultado = catalogosDao.findTipoOperacionById("410");
		assertThat(resultado).isEqualTo(doc);
	}

	@Test
	void testExisteTipoOperacion() {

		when(coleccionCatTipoOperacion.find(isA(Bson.class))).thenReturn(mockFindIterable);

		// Existe
		when(mockFindIterable.first()).thenReturn(new Document("_id", "410"));
		assertThat(catalogosDao.existeTipoOperacion("410")).isTrue();

		// No existe
		when(mockFindIterable.first()).thenReturn(null);
		assertThat(catalogosDao.existeTipoOperacion("999")).isFalse();
	}

	@Test
	void testUpdateEstatusTipoOperacion() {

		// Actualizacion exitosa (matchedCount > 0)
		UpdateResult ok = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(coleccionCatTipoOperacion.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(ok);
		assertThat(catalogosDao.updateEstatusTipoOperacion("410", true)).isTrue();

		// Sin coincidencias (matchedCount == 0)
		UpdateResult sinMatch = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(coleccionCatTipoOperacion.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(sinMatch);
		assertThat(catalogosDao.updateEstatusTipoOperacion("410", true)).isFalse();
	}

	@Test
	void testUpdateEstatusTipoOperacionConExcepcion() {

		when(coleccionCatTipoOperacion.updateOne(isA(Bson.class), isA(Bson.class)))
				.thenThrow(new MongoException("error"));

		assertThat(catalogosDao.updateEstatusTipoOperacion("410", true)).isFalse();
	}

	@Test
	void testFindDivisaById() {

		Document doc = new Document("_id", "USD").append("activo", true);
		when(colleccionCatMonedas.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(doc);

		Document resultado = catalogosDao.findDivisaById("USD");
		assertThat(resultado).isEqualTo(doc);
	}

	@Test
	void testConsultarCatUbicacionSucursales() {

		Document ubicacion = new Document("name", "0172");
		when(colleccionUbicacionSucursales.find(isA(Document.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.first()).thenReturn(ubicacion);

		BeanDistanciaSucursales resultado = catalogosDao.consultarCatUbicacionSucursales("0172");
		assertThat(resultado).isNotNull();
	}

	@Test
	void testConsultarSucursalesCercanas() {

		// La parametria "Geolocalizacion" aporta el radio (kilometros) y el limite de sucursales.
		Document parametros = new Document("kilometros", 5000.0).append("limiteSucursales", 30);
		when(parametria.consultar(isA(String.class))).thenReturn(parametros);

		when(colleccionUbicacionSucursales.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.limit(isA(Integer.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(new ArrayList<Document>());

		List<BeanDistanciaSucursales> resultado = catalogosDao.consultarSucursalesCercanas(-99.1, 19.4);
		assertThat(resultado).isEmpty();
		
		catalogosDao = new CatalogosDao();
	}
}
