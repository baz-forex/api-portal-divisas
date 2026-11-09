package com.baz.divisas.api.core.sucursales.dao;

import static com.baz.divisas.api.core.sucursales.utils.Constantes.IDCUESTIONARIO;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.RESPUESTAS;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.SUCURSAL;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.addToSet;
import static com.mongodb.client.model.Updates.set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginadoBitacora;
import com.baz.divisas.api.core.sucursales.dto.ReqRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.dto.RespConsultaPreguntas;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionariosBitacora;
import com.baz.divisas.api.core.sucursales.model.BeanRespuestasCuestionario;
import com.baz.divisas.api.core.sucursales.model.BeanSucursales;
import com.baz.divisas.api.core.sucursales.model.BeanTopesCaja;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.baz.divisas.api.core.sucursales.dto.ReqAsignarSucursales;
import com.baz.divisas.api.core.sucursales.dto.ReqFiltroCuestionarios;
import com.baz.divisas.api.core.sucursales.dto.ReqPregRespSucursal;
import java.util.List;
import java.util.ArrayList;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class CertificacionDaoTest {
	@InjectMocks
	private CertificacionDao dao;
	@Mock
	private MongoCollection<BeanSucursales> coleccionSucursales;
	@Mock
	private MongoCollection<BeanCuestionarios> coleccionCuestionarios;
	@Mock
	private MongoCollection<Document> coleccionCuestionariosDoc;
	@Mock
	private MongoCollection<RespConsultaPreguntas> coleccionRespuestasCuestionarios;
	@Mock
	private MongoCollection<Document> coleccionRespuestasCuestionariosDoc;
	@Mock
	private MongoCollection<BeanCuestionarios> coleccionCuestionariosHistorico;
	@Mock
	private MongoCollection<BeanCuestionariosBitacora> coleccionBitacoraCuestionarios;
	@Mock
	private MongoCollection<BeanTopesCaja> coleccionTopesCaja;
	@Mock
	MongoCollection<Document> coleccionRespuestasCuestionario;
	@Mock
	private MongoCollection<Document> coleccionCuestionariosHistoricoDoc;
	@Mock
	private MongoCursor<BeanCuestionarios> cursor;
	@Mock
	private MongoCursor<BeanTopesCaja> cursorTopesCaja;
	@Mock
	private FindIterable<BeanSucursales> iterable;
	@Mock
	private FindIterable<BeanCuestionarios> iterableCuestionarios;
	@Mock
	private FindIterable<RespConsultaPreguntas> iterableRespConsultaPreguntas;
	@Mock
	private FindIterable<BeanTopesCaja> iterableTopesCaja;
	@Mock
	private AggregateIterable<Document> aiterable;
	@Mock
	private AggregateIterable<BeanCuestionarios> aiterableCuestionarios;
	@Mock
	private AggregateIterable<BeanCuestionariosBitacora> aiterableCuestionariosBitacora;
	@Mock
	private UpdateResult updateResult;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testConsultaSucursales() {
		ReqPaginado reqPaginado = new ReqPaginado();
		reqPaginado.setElementosPagina(10);
		reqPaginado.setPagina(1);

		// Mocking the chain of method calls

		when(iterable.sort(any())).thenReturn(iterable);
		when(iterable.skip(anyInt())).thenReturn(iterable);
		when(iterable.limit(anyInt())).thenReturn(iterable);
		when(coleccionSucursales.find(Filters.eq("nomEstatus", "ACTIVO"))).thenReturn(iterable);

		FindIterable<BeanSucursales> res = dao.consultaSucursales(reqPaginado);

		assertThat(res).isNotNull();
	}

	@Test
	void testNuevoCuestionario() {
		BeanCuestionarios b = new BeanCuestionarios();

		// Call the method under test
		InsertOneResult result = dao.nuevoCuestionario(b);

		// Assert the result
		assertNull(result);
	}

	@Test
	void testProcesaCargaMasiva() {
		// Configura los Documentos de prueba
		Document filtro = new Document("filtro", "valor");
		Document datos = new Document("datos", "valor");

		// Verifica que los resultados son los esperados
		assertNull(dao.procesaCargaMasiva(filtro, datos));
	}

	@Test
	void testConsultaCuestionarios() {
		int elementosPagina = 10;
		int pagina = 1;

		// Assert the result
		assertNull(dao.consultaCuestionarios(elementosPagina, pagina));
	}

	@Test
	void testConteoCuestionarios() {

		// Assert the result
		assertNull(dao.conteoCuestionarios());
	}

	@Test
	 void testObtenerDireccionSucursal() {
	 when(coleccionSucursales.find(Filters.eq("sucursal",
	 1))).thenReturn(iterable);
	 when(iterable.first()).thenReturn(null);
	
	 // Assert
	 assertNull(dao.obtenerDireccionSucursal(1));
	 }

	@Test
	void testActualizaCuestionario() {
		BeanCuestionarios reqActualizaCuestionario = new BeanCuestionarios();
		reqActualizaCuestionario.setId("7865e5d98b0973103a8d34ac");
		assertNull(dao.actualizaCuestionario(reqActualizaCuestionario));
	}

	@Test
	 void testConsultaPreguntas() {
	 when(iterableRespConsultaPreguntas.projection(any())).thenReturn(iterableRespConsultaPreguntas);
	 Bson filter = Filters.eq("_id", new ObjectId("7865e5d98b0973103a8d34ac"));
	 when(coleccionRespuestasCuestionarios.find(filter)).thenReturn(iterableRespConsultaPreguntas);
	 RespConsultaPreguntas res =
	 dao.consultaPreguntas("7865e5d98b0973103a8d34ac");
	
	 assertThat(res).isNull();
	 }

	@Test
	void testActualizaEstatusCuestionario() {

		// Call the method under test
		UpdateResult result = dao.actualizaEstatusCuestionario("7865e5d98b0973103a8d34ac", false);

		// Assert the result
		assertNull(result);
	}

	@Test
	void testConsultaCuestionario() {
		Bson filter = Filters.eq("_id", new ObjectId("7865e5d98b0973103a8d34ac"));
		when(coleccionCuestionarios.find(filter)).thenReturn(iterableCuestionarios);
		BeanCuestionarios res = dao.consultaCuestionario("7865e5d98b0973103a8d34ac");

		assertThat(res).isNull();
	}

	@Test
	void testAsignarSucursales() {
		List<String> sucursales = new ArrayList<>();
		sucursales.add("Sucursal1");
		sucursales.add("Sucursal2");
		sucursales.add("Sucursal3");

		ReqAsignarSucursales req = new ReqAsignarSucursales();
		req.setId("6438547dcf82e103a0287bc9");
		req.setSucursales(sucursales);

		when(coleccionCuestionarios.updateOne(eq("_id", new ObjectId(req.getId())),
				addToSet("sucursales", new BasicDBObject("$each", req.getSucursales()))))
				.thenReturn(null);

		assertThat(dao.asignarSucursales(req)).isNull();
	}

	@Test
	void testConsultaRespuestas() {
		ReqPaginado reqPaginado = new ReqPaginado();
		reqPaginado.setElementosPagina(10);
		reqPaginado.setPagina(1);
		when(coleccionRespuestasCuestionariosDoc.aggregate(any())).thenReturn(null);
		AggregateIterable<Document> res = dao.consultaRespuestas(reqPaginado);

		assertThat(res).isNull();
	}

	@Test
	 void testDatosSucursal() {
	 when(coleccionSucursales.find(Filters.eq("sucursal",
	 107))).thenReturn(iterable);
	 when(iterable.first()).thenReturn(null);
	 BeanSucursales res = dao.datosSucursal(107);
	
	 assertThat(res).isNull();
	 }

	@Test
	void testCertificaSucursal() {
		BeanSucursales consultarCuestionarios = new BeanSucursales();
		consultarCuestionarios.setCertificar(true);

		when(coleccionSucursales.find(Filters.eq("sucursal", 107))).thenReturn(iterable);
		when(iterable.first()).thenReturn(consultarCuestionarios);
		when(coleccionSucursales.updateOne(eq("sucursal", 107), set("certificar", false)))
				.thenReturn(null);

		UpdateResult res = dao.certificaSucursal("0107");

		assertThat(res).isNull();
	}

	@Test
	 void testConsultaSucursal() {
	 when(coleccionSucursales.find(Filters.eq("sucursal",
	 107))).thenReturn(iterable);
	 when(iterable.first()).thenReturn(null);
	 BeanSucursales res = dao.consultaSucursal("0107");
	
	 assertThat(res).isNull();
	 }

	@Test
	 void testConsultaRespuestasSucursal() {
	 when(coleccionRespuestasCuestionariosDoc.aggregate(any())).thenReturn(null);
	 AggregateIterable<Document> res = dao.consultaRespuestasSucursal(new
	 ReqRespuestasSucursal());
	
	 assertThat(res).isNull();
	 }

	@Test
	 void testGetCuestionarios() {
	
	 when(coleccionCuestionarios.find(Filters.in("sucursales",
	 "107"))).thenReturn(iterableCuestionarios);
	 when(iterableCuestionarios.iterator()).thenReturn(cursor);
	
	 MongoCursor<BeanCuestionarios> res = dao.getCuestionarios("107");
	
	 assertThat(res).isNotNull();
	 }

	@Test
	void testActualizaCuestionarioHist() {
		ObjectId id = new ObjectId();
		BeanCuestionarios cuestionario = new BeanCuestionarios();

		when(coleccionCuestionariosHistorico.replaceOne(any(Bson.class),
				any(BeanCuestionarios.class), any(ReplaceOptions.class))).thenReturn(updateResult);

		boolean res = dao.actualizaCuestionarioHist(cuestionario, id);

		assertThat(res).isFalse();
	}

	@Test
	void testConsultaCuestionariosHistorico() {
		ReqPaginado req = new ReqPaginado();
		when(coleccionCuestionariosHistorico.aggregate(any())).thenReturn(aiterableCuestionarios);
		List<BeanCuestionarios> res = dao.consultaCuestionariosHistorico(req);

		assertThat(res).isNotNull();
	}

	@Test
	 void testConteoCuestionariosHist() {
	 when(coleccionCuestionariosHistorico.countDocuments()).thenReturn(1L);
	 long res = dao.conteoCuestionariosHist();
	
	 assertThat(res).isNotNull();
	 }

	@Test
	void testConsultaPregCuestionariosHist() {
		String id = "647648510693b02e1fac65e7";
		when(coleccionCuestionariosHistorico.find(Filters.eq("_id", new ObjectId(id))))
				.thenReturn(iterableCuestionarios);
		when(iterableCuestionarios.iterator()).thenReturn(null);

		MongoCursor<BeanCuestionarios> result = dao.consultaPregCuestionariosHist(id);

		// Assert the result
		assertNull(result);

	}

	@Test
	void testConsultaSucAsignadasHist() {
		String id = "647648510693b02e1fac65e7";
		when(coleccionCuestionariosHistorico.find(new Document("_id", new ObjectId(id))))
				.thenReturn(iterableCuestionarios);
		when(iterableCuestionarios.projection(new Document("sucursales", 1).append("_id", 0)))
				.thenReturn(iterableCuestionarios);
		when(iterableCuestionarios.first()).thenReturn(null);

		BeanCuestionarios result = dao.consultaSucAsignadasHist(id);

		// Assert the result
		assertNull(result);

	}

	@Test
	void testConsultaPreguntasSuc() {
		ReqPregRespSucursal req = new ReqPregRespSucursal();
		req.setId("647648510693b02e1fac65e7");
		req.setSucursal("0107");
		AggregateIterable<Document> res = dao.consultaPreguntasSuc(req);

		assertThat(res).isNull();
	}

	@Test
	void testConsultaRespuestasSuc() {
		ReqPregRespSucursal req = new ReqPregRespSucursal();
		req.setId("647648510693b02e1fac65e7");
		req.setSucursal("0107");
		AggregateIterable<Document> res = dao.consultaRespuestasSuc(req);

		new CertificacionDao();

		assertThat(res).isNull();
	}

	@Test
	void testConteoConsultaRespuesta() {
		when(coleccionRespuestasCuestionariosDoc.aggregate(any())).thenReturn(null);
		AggregateIterable<Document> res = dao.conteoConsultaRespuesta();
		
		assertThat(res).isNull();
	}

	@Test
	void testConsultaBitacoraCuestionario() {
		ReqPaginadoBitacora reqPaginadoBitacora = new ReqPaginadoBitacora();
		reqPaginadoBitacora.setElementosPagina(10);
		reqPaginadoBitacora.setPagina(1);

		when(coleccionBitacoraCuestionarios.aggregate(any()))
				.thenReturn(aiterableCuestionariosBitacora);
		List<BeanCuestionariosBitacora> res = dao.consultaBitacoraCuestionario(reqPaginadoBitacora);

		assertThat(res).isNotNull();
	}

	@Test
	void testConteoConsultaSucursales() {
		when(coleccionSucursales.countDocuments(Filters.eq("nomEstatus", "ACTIVO"))).thenReturn(1L);
		long res = dao.conteoConsultaSucursales();
		
		assertThat(res).isNotNull();
	}

	@Test
	void testNuevoCuestionarioBitacora() {
		BeanCuestionariosBitacora reqNuevoCuestionario = new BeanCuestionariosBitacora();
		InsertOneResult result = dao.nuevoCuestionarioBitacora(reqNuevoCuestionario);

		assertNull(result);
	}

	@Test
	void testConteoBitacoraCuestionario() {
		ReqPaginadoBitacora reqPaginadoBitacora = new ReqPaginadoBitacora();

		when(coleccionBitacoraCuestionarios
				.countDocuments(Filters.eq(IDCUESTIONARIO, reqPaginadoBitacora.getId())))
				.thenReturn(1L);
		long res = dao.conteoBitacoraCuestionario(reqPaginadoBitacora);

		assertThat(res).isNotNull();
	}

	@Test
	void testNuevoRespuestasCuestionario() {
		BeanRespuestasCuestionario beanRespuestasCuestionario = new BeanRespuestasCuestionario();
		Bson filtro = and(eq(IDCUESTIONARIO, beanRespuestasCuestionario.getIdCuestionario()),
				eq(SUCURSAL, beanRespuestasCuestionario.getSucursal()));
		Document datos = new Document("$set",
				new Document(SUCURSAL, beanRespuestasCuestionario.getSucursal())
						.append(IDCUESTIONARIO, beanRespuestasCuestionario.getIdCuestionario())
						.append(RESPUESTAS, beanRespuestasCuestionario.getRespuestas()));
		FindOneAndUpdateOptions opciones = new FindOneAndUpdateOptions().upsert(true);

		when(coleccionRespuestasCuestionario.findOneAndUpdate(filtro, datos, opciones))
				.thenReturn(new Document());
		Document res = dao.nuevoRespuestasCuestionario(beanRespuestasCuestionario);

		assertThat(res).isNull();
	}

	@Test
	void testEliminaRespuestasCuestionario() {
		when(coleccionRespuestasCuestionario.deleteMany(eq("idCuestionario", new ObjectId("649cc915fbee1a1af5ed68c2"))))
		.thenReturn(null);
		DeleteResult res = dao.eliminaRespuestasCuestionario("649cc915fbee1a1af5ed68c2");

		assertThat(res).isNull();
	}

	@Test
	void testConsultaTopeCaja() {
		List<Integer> divisa = new ArrayList<>();
		List<Integer> tipoCaja = new ArrayList<>();
		int sucursal = 107;

		when(iterableTopesCaja.sort(Sorts.ascending("idDivisa"))).thenReturn(iterableTopesCaja);
		when(coleccionTopesCaja.find(Filters.and(Filters.in("idDivisa", divisa),
				Filters.in("idCaja", tipoCaja), Filters.eq("idSucursal", sucursal))))
				.thenReturn(iterableTopesCaja);
		MongoCursor<BeanTopesCaja> res = dao.consultaTopeCaja(divisa, tipoCaja, sucursal);

		assertThat(res).isNull();
	}

	@Test
	void testGetCuestionariosFiltroHis() {
		ReqFiltroCuestionarios reqFiltroCuestionarios = new ReqFiltroCuestionarios();
		AggregateIterable<Document> result = dao.getCuestionariosFiltroHis(reqFiltroCuestionarios);

		assertNull(result);
	}

}
