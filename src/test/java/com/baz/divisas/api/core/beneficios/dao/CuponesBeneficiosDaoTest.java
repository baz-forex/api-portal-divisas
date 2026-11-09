package com.baz.divisas.api.core.beneficios.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.core.beneficios.model.CuponAlianzas;
import com.baz.divisas.api.core.beneficios.model.CuponCRM;
import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.api.core.beneficios.model.CuponTA;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.InsertOneResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CuponesBeneficiosDaoTest {

	private CuponesBeneficiosDao dao;

	@Mock
	private MongoCollection<CuponTA> mockColCuponesTA;
	@Mock
	private MongoCollection<HistoricoAltoRiesgo> mockColHistoricaTA;
	@Mock
	private MongoCollection<CuponCRM> mockColCuponesCRM;
	@Mock
	private MongoCollection<HistoricoAltoRiesgo> mockColHistoricaCRM;
	@Mock
	private MongoCollection<CuponMasSalud> mockColCuponesMasSalud;
	@Mock
	private MongoCollection<HistoricoAltoRiesgo> mockColHistoricaMasSalud;
	@Mock
	private MongoCollection<CuponAlianzas> mockColCuponesAlianzas;
	@Mock
	private MongoCollection<HistoricoAltoRiesgo> mockColHistoricaAlianzas;
	@Mock
	private FindIterable<HistoricoAltoRiesgo> mockFindIterable;
	@Mock
	private InsertOneResult mockInsertResult;

	@BeforeEach
	void setUp() throws Exception {
		dao = new CuponesBeneficiosDao(mockColCuponesTA, mockColHistoricaTA,
				mockColCuponesCRM, mockColHistoricaCRM,
				mockColCuponesMasSalud, mockColHistoricaMasSalud);

		// Inyectar colecciones de Alianzas via reflexión
		Field fieldAlianzas = CuponesBeneficiosDao.class.getDeclaredField("coleccionCuponesAlianzas");
		fieldAlianzas.setAccessible(true);
		fieldAlianzas.set(dao, mockColCuponesAlianzas);

		Field fieldHistAlianzas = CuponesBeneficiosDao.class.getDeclaredField("coleccionHistoricaCuponesAlianzas");
		fieldHistAlianzas.setAccessible(true);
		fieldHistAlianzas.set(dao, mockColHistoricaAlianzas);
	}

	// ==================== Cupones TA ====================

	@Test
	void testConsultaCargaHistorica() {
		when(mockColHistoricaTA.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(new ArrayList<>());

		List<HistoricoAltoRiesgo> resultado = dao.consultaCargaHistorica();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testRegistraCuponTA() {
		when(mockColCuponesTA.insertOne(isA(CuponTA.class))).thenReturn(mockInsertResult);

		CuponTA cupon = new CuponTA();
		cupon.setId("ABC123");
		boolean resultado = dao.registraCuponTA(cupon);
		assertThat(resultado).isTrue();
	}

	@Test
	void testRegistraCuponTAException() {
		when(mockColCuponesTA.insertOne(isA(CuponTA.class))).thenThrow(new MongoException("Error"));

		CuponTA cupon = new CuponTA();
		cupon.setId("ABC123");
		boolean resultado = dao.registraCuponTA(cupon);
		assertThat(resultado).isFalse();
	}

	@Test
	void testRegistraBitacoraCuponesTA() {
		when(mockColHistoricaTA.insertOne(isA(HistoricoAltoRiesgo.class))).thenReturn(mockInsertResult);
		dao.registraBitacoraCuponesTA("nombre", "descripcion", 10);
	}

	@Test
	void testRegistraBitacoraCuponesTAException() {
		when(mockColHistoricaTA.insertOne(isA(HistoricoAltoRiesgo.class))).thenThrow(new MongoException("Error"));
		dao.registraBitacoraCuponesTA("nombre", "descripcion", 10);
	}

	// ==================== Cupones CRM ====================

	@Test
	void testRegistraCuponesCRM() {
		List<CuponCRM> cupones = new ArrayList<>();
		CuponCRM cupon = new CuponCRM();
		cupon.setId("ABCDE");
		cupon.setIdCliente("123");
		cupones.add(cupon);

		when(mockColCuponesCRM.insertMany(any(), isA(InsertManyOptions.class))).thenReturn(null);

		int resultado = dao.registraCuponesCRM(cupones);
		assertThat(resultado).isEqualTo(1);
	}

	@Test
	void testRegistraCuponesCRMException() {
		List<CuponCRM> cupones = new ArrayList<>();
		CuponCRM cupon = new CuponCRM();
		cupon.setId("ABCDE");
		cupones.add(cupon);

		when(mockColCuponesCRM.insertMany(any(), isA(InsertManyOptions.class))).thenThrow(new MongoException("Error"));

		int resultado = dao.registraCuponesCRM(cupones);
		assertThat(resultado).isZero();
	}

	@Test
	void testRegistraBitacoraCuponesCRM() {
		when(mockColHistoricaCRM.insertOne(isA(HistoricoAltoRiesgo.class))).thenReturn(mockInsertResult);
		dao.registraBitacoraCuponesCRM("nombre", "desc", 5, "2026-01-01", "2026-12-31");
	}

	@Test
	void testRegistraBitacoraCuponesCRMException() {
		when(mockColHistoricaCRM.insertOne(isA(HistoricoAltoRiesgo.class))).thenThrow(new MongoException("Error"));
		dao.registraBitacoraCuponesCRM("nombre", "desc", 5, "2026-01-01", "2026-12-31");
	}

	@Test
	void testConsultaCargaHistoricaCRM() {
		when(mockColHistoricaCRM.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(new ArrayList<>());

		List<HistoricoAltoRiesgo> resultado = dao.consultaCargaHistoricaCRM();
		assertThat(resultado).isNotNull();
	}

	// ==================== Cupones Mas Salud ====================

	@Test
	void testRegistraCuponesMasSaludBulk() {
		List<CuponMasSalud> cupones = new ArrayList<>();
		CuponMasSalud cupon = new CuponMasSalud();
		cupon.setId("CUPON1");
		cupon.setTipo("1");
		cupones.add(cupon);

		when(mockColCuponesMasSalud.insertMany(any(), isA(InsertManyOptions.class))).thenReturn(null);

		int resultado = dao.registraCuponesMasSaludBulk(cupones);
		assertThat(resultado).isEqualTo(1);
	}

	@Test
	void testRegistraCuponesMasSaludBulkException() {
		List<CuponMasSalud> cupones = new ArrayList<>();
		CuponMasSalud cupon = new CuponMasSalud();
		cupon.setId("CUPON1");
		cupones.add(cupon);

		when(mockColCuponesMasSalud.insertMany(any(), isA(InsertManyOptions.class))).thenThrow(new MongoException("Error"));

		int resultado = dao.registraCuponesMasSaludBulk(cupones);
		assertThat(resultado).isZero();
	}

	@Test
	void testRegistraBitacoraCuponesMasSalud() {
		when(mockColHistoricaMasSalud.insertOne(isA(HistoricoAltoRiesgo.class))).thenReturn(mockInsertResult);
		dao.registraBitacoraCuponesMasSalud("nombre", "desc", "1", 10);
	}

	@Test
	void testRegistraBitacoraCuponesMasSaludException() {
		when(mockColHistoricaMasSalud.insertOne(isA(HistoricoAltoRiesgo.class))).thenThrow(new MongoException("Error"));
		dao.registraBitacoraCuponesMasSalud("nombre", "desc", "1", 10);
	}

	@Test
	void testConsultaCargaHistoricaMasSalud() {
		when(mockColHistoricaMasSalud.find(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(new ArrayList<>());

		List<HistoricoAltoRiesgo> resultado = dao.consultaCargaHistoricaMasSalud("1");
		assertThat(resultado).isNotNull();
	}

	// ==================== Cupones Alianzas ====================

	@Test
	void testConsultaCargaHistoricaAlianzas() {
		when(mockColHistoricaAlianzas.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(new ArrayList<>());

		List<HistoricoAltoRiesgo> resultado = dao.consultaCargaHistoricaAlianzas();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testConsultaCargaHistoricaAlianzasConDatos() {
		List<HistoricoAltoRiesgo> historico = new ArrayList<>();
		HistoricoAltoRiesgo h = new HistoricoAltoRiesgo();
		h.setNombre("Carga Alianzas");
		h.setRegistros(15);
		historico.add(h);

		when(mockColHistoricaAlianzas.find()).thenReturn(mockFindIterable);
		when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
		when(mockFindIterable.into(any())).thenReturn(historico);

		List<HistoricoAltoRiesgo> resultado = dao.consultaCargaHistoricaAlianzas();
		assertThat(resultado).hasSize(1);
		assertThat(resultado.get(0).getNombre()).isEqualTo("Carga Alianzas");
	}

	@Test
	void testRegistraCuponesAlianzas() {
		List<CuponAlianzas> cupones = new ArrayList<>();
		CuponAlianzas cupon = new CuponAlianzas();
		cupon.setId("ABCDE");
		cupon.setAlianza("ALIANZA1");
		cupon.setFechaVigencia("2026-12-31");
		cupones.add(cupon);

		when(mockColCuponesAlianzas.insertMany(any(), isA(InsertManyOptions.class))).thenReturn(null);

		int resultado = dao.registraCuponesAlianzas(cupones);
		assertThat(resultado).isEqualTo(1);
	}

	@Test
	void testRegistraCuponesAlianzasMultiples() {
		List<CuponAlianzas> cupones = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			CuponAlianzas cupon = new CuponAlianzas();
			cupon.setId("CUP0" + i);
			cupon.setAlianza("ALI");
			cupones.add(cupon);
		}

		when(mockColCuponesAlianzas.insertMany(any(), isA(InsertManyOptions.class))).thenReturn(null);

		int resultado = dao.registraCuponesAlianzas(cupones);
		assertThat(resultado).isEqualTo(5);
	}

	@Test
	void testRegistraCuponesAlianzasExceptionMongo() {
		List<CuponAlianzas> cupones = new ArrayList<>();
		CuponAlianzas cupon = new CuponAlianzas();
		cupon.setId("ABCDE");
		cupones.add(cupon);

		when(mockColCuponesAlianzas.insertMany(any(), isA(InsertManyOptions.class))).thenThrow(new MongoException("Error"));

		int resultado = dao.registraCuponesAlianzas(cupones);
		assertThat(resultado).isZero();
	}

	@Test
	void testRegistraBitacoraCuponesAlianzas() {
		when(mockColHistoricaAlianzas.insertOne(isA(HistoricoAltoRiesgo.class))).thenReturn(mockInsertResult);
		dao.registraBitacoraCuponesAlianzas("nombre", "desc", 8, "2026-01-01", "2026-12-31");
	}

	@Test
	void testRegistraBitacoraCuponesAlianzasException() {
		when(mockColHistoricaAlianzas.insertOne(isA(HistoricoAltoRiesgo.class))).thenThrow(new MongoException("Error"));
		dao.registraBitacoraCuponesAlianzas("nombre", "desc", 8, "2026-01-01", "2026-12-31");
	}
}
