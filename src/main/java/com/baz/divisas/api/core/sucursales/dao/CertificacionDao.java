package com.baz.divisas.api.core.sucursales.dao;

import static org.bson.codecs.configuration.CodecRegistries.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonNull;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.*;

import com.baz.divisas.api.core.sucursales.dto.ReqAsignarSucursales;
import com.baz.divisas.api.core.sucursales.dto.ReqFiltroCuestionarios;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginadoBitacora;
import com.baz.divisas.api.core.sucursales.dto.ReqPregRespSucursal;
import com.baz.divisas.api.core.sucursales.dto.ReqRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.dto.RespConsultaPreguntas;
import com.baz.divisas.api.core.sucursales.model.BeanSucursales;
import com.baz.divisas.api.core.sucursales.model.BeanTopesCaja;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionariosBitacora;
import com.baz.divisas.api.core.sucursales.model.BeanRespuestasCuestionario;

/**
 * Clase CertificacionDao
 * 
 * @author 1096526
 *
 */
@Repository
public class CertificacionDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase middlewareBD;
	private MongoDatabase terminalFinancieroBD;
	private MongoCollection<BeanCuestionarios> coleccionCuestionarios;
	private MongoCollection<BeanCuestionariosBitacora> coleccionBitacoraCuestionarios;
	private MongoCollection<Document> coleccionCuestionariosDoc;
	private MongoCollection<BeanCuestionarios> coleccionCuestionariosHistorico;
	private MongoCollection<Document> coleccionCuestionariosHistoricoDoc;
	private MongoCollection<RespConsultaPreguntas> coleccionRespuestasCuestionarios;
	private MongoCollection<Document> coleccionRespuestasCuestionariosDoc;
	private MongoCollection<BeanTopesCaja> coleccionTopesCaja;
	private MongoCollection<Document> coleccionRespuestasCuestionario;
	private MongoCollection<BeanSucursales> coleccionSucursales;

	/**
	 * /** Constructor de la clase
	 */
	public CertificacionDao() {

		CodecRegistry pojoCodecRegistry = fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		terminalFinancieroBD = factoryDB.getMongoClient()
				.getDatabase(PropBD.getTerminalFinancieroBD());

		coleccionSucursales = terminalFinancieroBD
				.getCollection(PropBD.getSucursales(), BeanSucursales.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionTopesCaja = terminalFinancieroBD
				.getCollection(PropBD.getTopeCaja(), BeanTopesCaja.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionRespuestasCuestionarios = middlewareBD
				.getCollection(PropBD.getCuestionarios(), RespConsultaPreguntas.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionRespuestasCuestionariosDoc = middlewareBD
				.getCollection(PropBD.getRespuestasCuestionarios())
				.withCodecRegistry(pojoCodecRegistry);

		coleccionCuestionarios = middlewareBD
				.getCollection(PropBD.getCuestionarios(), BeanCuestionarios.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionBitacoraCuestionarios = middlewareBD
				.getCollection(PropBD.getBitacoraCuestionarios(), BeanCuestionariosBitacora.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionCuestionariosDoc = middlewareBD.getCollection(PropBD.getCuestionarios())
				.withCodecRegistry(pojoCodecRegistry);

		coleccionCuestionariosHistorico = middlewareBD
				.getCollection(PropBD.getCuestionariosHistorico(), BeanCuestionarios.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionCuestionariosHistoricoDoc = middlewareBD
				.getCollection(PropBD.getCuestionariosHistorico())
				.withCodecRegistry(pojoCodecRegistry);

		coleccionRespuestasCuestionario = middlewareBD
				.getCollection(PropBD.getRespuestasCuestionarios())
				.withCodecRegistry(pojoCodecRegistry);

	}

	/**
	 * Constructor para pruebas unitarias
	 * 
	 * @param coleccionCuestionarios              - coleccion
	 * @param coleccionCuestionariosDoc           - coleccion
	 * @param coleccionCuestionariosHistorico     - coleccion
	 * @param coleccionRespuestasCuestionarios    - coleccion
	 * @param coleccionRespuestasCuestionariosDoc - coleccion
	 * @param coleccionSucursales                 - coleccion
	 * @param coleccionCuestionariosBitacora      - coleccion
	 * @param coleccionTopesCaja                  - coleccion
	 * @param coleccionRespuestasCuestionario     - coleccion
	 * @param coleccionConsultaRespuestas         - coleccion
	 */
	public CertificacionDao(MongoCollection<BeanCuestionarios> coleccionCuestionarios,
			MongoCollection<Document> coleccionCuestionariosDoc,
			MongoCollection<BeanCuestionarios> coleccionCuestionariosHistorico,
			MongoCollection<RespConsultaPreguntas> coleccionRespuestasCuestionarios,
			MongoCollection<Document> coleccionRespuestasCuestionariosDoc,
			MongoCollection<BeanSucursales> coleccionSucursales,
			MongoCollection<BeanCuestionariosBitacora> coleccionCuestionariosBitacora,
			MongoCollection<BeanTopesCaja> coleccionTopesCaja,
			MongoCollection<Document> coleccionConsultaRespuestas) {
		super();
		this.coleccionCuestionarios = coleccionCuestionarios;
		this.coleccionCuestionariosDoc = coleccionCuestionariosDoc;
		this.coleccionCuestionariosHistorico = coleccionCuestionariosHistorico;
		this.coleccionRespuestasCuestionarios = coleccionRespuestasCuestionarios;
		this.coleccionRespuestasCuestionariosDoc = coleccionRespuestasCuestionariosDoc;
		this.coleccionSucursales = coleccionSucursales;
		this.coleccionBitacoraCuestionarios = coleccionCuestionariosBitacora;
		this.coleccionTopesCaja = coleccionTopesCaja;
		this.coleccionRespuestasCuestionario = coleccionConsultaRespuestas;
	}

	/**
	 * Metodo para consultar las sucursales del CeCo *
	 * 
	 * @param elementosPagina - registris mostrados por pagina
	 * @param reqPaginado     - request del paginado
	 * 
	 * @return sucursales CeCo
	 */
	public FindIterable<BeanSucursales> consultaSucursales(ReqPaginado reqPaginado) {

		// Devolver la lista de documentos y total de registros
		return coleccionSucursales.find(Filters.eq("nomEstatus", "ACTIVO"))
				.sort(Sorts.ascending(SUCURSAL))
				.skip((reqPaginado.getPagina() - 1) * reqPaginado.getElementosPagina())
				.limit(reqPaginado.getElementosPagina());

	}

	/**
	 * Metodo para obtener el total de registros de la coleccion sucursales
	 * 
	 * @return long con el total de sucursales
	 */
	public long conteoConsultaSucursales() {

		return coleccionSucursales.countDocuments(Filters.eq("nomEstatus", "ACTIVO"));
	}

	/**
	 * Metodo para insertar un nuevo cuestionario
	 * 
	 * @param reqNuevoCuestionario - request de la solicitud
	 * 
	 * @return respuesta de la insercion
	 */
	public InsertOneResult nuevoCuestionario(BeanCuestionarios reqNuevoCuestionario) {

		return coleccionCuestionarios.insertOne(reqNuevoCuestionario);

	}

	/**
	 * Metodo para insertar un nuevo cuestionario
	 * 
	 * @param reqNuevoCuestionario - request de la solicitud
	 * 
	 * @return respuesta de la insercion
	 */
	public InsertOneResult nuevoCuestionarioBitacora(
			BeanCuestionariosBitacora reqNuevoCuestionario) {

		return coleccionBitacoraCuestionarios.insertOne(reqNuevoCuestionario);

	}

	/**
	 * Metodo para crear las respuestas de un cuestionario
	 * 
	 * @param beanRespuestasCuestionario - Datos a insertar
	 * 
	 * @return objeto InsertOneResult con la respuesta de la consulta
	 */
	public Document nuevoRespuestasCuestionario(
			BeanRespuestasCuestionario beanRespuestasCuestionario) {

		Bson filtro = and(eq(IDCUESTIONARIO, beanRespuestasCuestionario.getIdCuestionario()),
				eq(SUCURSAL, beanRespuestasCuestionario.getSucursal()));

		Document datos = new Document("$set",
				new Document(SUCURSAL, beanRespuestasCuestionario.getSucursal())
						.append(IDCUESTIONARIO, beanRespuestasCuestionario.getIdCuestionario())
						.append(RESPUESTAS, beanRespuestasCuestionario.getRespuestas()));

		FindOneAndUpdateOptions opciones = new FindOneAndUpdateOptions().upsert(true);

		return coleccionRespuestasCuestionario.findOneAndUpdate(filtro, datos, opciones);
	}

	/**
	 * Metodo para eliminar registros que no coincidan con las sucursales asignadas
	 * 
	 * @param sucursal        - sucursal a validar
	 * @param idCiuestionario - id del cuestionario
	 * 
	 * @return objeto DeleteResult con la respuesta de la consulta
	 */
	public DeleteResult eliminaRespuestasCuestionario(String idCiuestionario) {

		return coleccionRespuestasCuestionario
				.deleteMany(eq(IDCUESTIONARIO, new ObjectId(idCiuestionario)));
	}

	/**
	 * Metodo para insertar datos desde un archivo
	 * 
	 * @param filtro - filtrado de la consulta
	 * @param datos  - datos de la consulta a actualizar
	 * 
	 * @return objeto UpdateResult para determinar si se proceso correctamente la
	 *         actualizacion
	 */
	public UpdateResult procesaCargaMasiva(Document filtro, Document datos) {

		return coleccionSucursales.updateMany(filtro, datos);

	}

	/**
	 * Metodo para consultar los cuestionarios
	 * 
	 * @param elementosPagina - registros mostrados por pagina
	 * @param pagina          - la pagina actual
	 * 
	 * @return objeto Document con los cuestionarios consultados
	 */
	public AggregateIterable<Document> consultaCuestionarios(int elementosPagina, int pagina) {

		return coleccionCuestionariosDoc.aggregate(Arrays.asList(new Document(PESOLOOKUP,
				new Document("from", RESPCUESTIONARIOS).append(LOCALFIELD, "_id")
						.append(FOREIGNFIELD, IDCUESTIONARIO).append("as", RESPCUESTIONARIOS)),
				new Document(PESOMATCH,
						new Document("respuestasCuestionarios.finalizaCuestionario",
								new Document("$ne", true))),
				new Document(PESOPROJECT, new Document("id", "$_id").append(TITULO, 1L)
						.append(PREGUNTAS, new Document(PESOSIZE, PESOPREGUNTAS))
						.append(FECHAREGISTRO, 1L).append(ESTATUS, 1L).append(SUCURSALES, 1L)),
				new Document("$skip", (pagina - 1) * elementosPagina),
				new Document("$limit", elementosPagina)));

	}

	/**
	 * Metodo para contar los cuestionarios
	 * 
	 * @return objeto Document con el resultado de la consulta
	 */
	public AggregateIterable<Document> conteoCuestionarios() {

		return coleccionCuestionariosDoc.aggregate(Arrays.asList(new Document(PESOLOOKUP,
				new Document("from", RESPCUESTIONARIOS).append(LOCALFIELD, "_id")
						.append(FOREIGNFIELD, IDCUESTIONARIO).append("as", RESPCUESTIONARIOS)),
				new Document(PESOGROUP,
						new Document("_id", "$_id").append(RESPCUESTIONARIOS,
								new Document("$first", "$respuestasCuestionarios"))),
				new Document(PESOMATCH, new Document("respuestasCuestionarios.finalizaCuestionario",
						new Document("$ne", true))),
				new Document("$count", TOTAL)));
	}

	/**
	 * Metodo para consultar la direccion de una Sucursal
	 * 
	 * @param sucursal - identificador de la Sucursal
	 * 
	 * @return objeto RespObtenerDireccionSucursal con los datos de la direccion
	 */
	public BeanSucursales obtenerDireccionSucursal(int sucursal) {

		return coleccionSucursales.find(Filters.eq(SUCURSAL, sucursal)).first();
	}

	/**
	 * Metodo para actualizar un cuestionario cuestionario
	 * 
	 * @param reqActualizaCuestionario - request con los datos a actualizar
	 * 
	 * @return objeto UpdateResult con la respuesta de la actualizacion
	 */
	public UpdateResult actualizaCuestionario(BeanCuestionarios reqActualizaCuestionario) {

		return coleccionCuestionarios.replaceOne(
				eq("_id", new ObjectId(reqActualizaCuestionario.getId())), reqActualizaCuestionario,
				new ReplaceOptions().upsert(false));

	}

	/**
	 * Metodo para consultar las preguntas de un cuestionario
	 * 
	 * @param id - identificador del cuestionario
	 * 
	 * @return objeto RespConsultaPreguntas con los datos de la respuesta del
	 *         servicio
	 */
	public RespConsultaPreguntas consultaPreguntas(String id) {

		return coleccionRespuestasCuestionarios.find(Filters.eq("_id", new ObjectId(id)))
				.projection(Projections.fields(Projections.include(TITULO, PREGUNTAS))).first();

	}

	/**
	 * Metodo para actualizar el estatus de un cuestionario
	 * 
	 * @param id      - request con id del cuestionario
	 * 
	 * @param estatus - estatus de la actualizacion
	 * 
	 * @return objeto UpdateResult con la respuesta de la actualizacion
	 */
	public UpdateResult actualizaEstatusCuestionario(String id, boolean estatus) {

		return coleccionCuestionarios.updateOne(eq("_id", new ObjectId(id)), set(ESTATUS, estatus));

	}

	/**
	 * Metodo para consultar un cuestionario
	 * 
	 * @param id - id del cuestionario
	 * 
	 * @return objeto BeanCuestionarios con la respuesta de la consulta
	 */
	public BeanCuestionarios consultaCuestionario(String id) {
		return coleccionCuestionarios.find(Filters.eq("_id", new ObjectId(id))).first();
	}

	/**
	 * Metodo para asignar sucursales a un cuestionario
	 * 
	 * @param reqAsignarSucursales - request de la solicitud
	 * 
	 * @return objeto UpdateResult con la respuesta de la actualizacion
	 */
	public UpdateResult asignarSucursales(ReqAsignarSucursales reqAsignarSucursales) {

		return coleccionCuestionarios.updateOne(
				eq("_id", new ObjectId(reqAsignarSucursales.getId())),
				set(SUCURSALES, reqAsignarSucursales.getSucursales()));

	}

	/**
	 * Metodo para consultar las respuestas de un cuestionario
	 * 
	 * @param reqPaginado - request del paginado
	 * 
	 * @return objeto Document con la respuesta del servicio
	 */
	public AggregateIterable<Document> consultaRespuestas(ReqPaginado reqPaginado) {

		return coleccionRespuestasCuestionariosDoc
				.aggregate(Arrays.asList(
						new Document(PESOPROJECT,
								new Document(SUCURSAL, 1L)
										.append(COMPLETADO, new Document(PESOCOND,
												Arrays.asList(
														new Document("$eq", Arrays.asList(
																"$finalizaCuestionario", true)),
														1L, 0L)))
										.append(PENDIENTE,
												new Document(PESOCOND, Arrays.asList(
														new Document("$eq", Arrays.asList(
																"$finalizaCuestionario", false)),
														1L, 0L)))),
						new Document(PESOGROUP,
								new Document("_id", "$sucursal")
										.append(COMPLETADO, new Document("$sum", PESOCOMPLETADO))
										.append(PENDIENTE, new Document("$sum", "$pendiente"))
										.append(TOTAL, new Document("$sum", 1L))),
						new Document(PESOPROJECT, new Document(COMPLETADO,
								new Document(PESOTOINT, PESOCOMPLETADO))
								.append(PENDIENTE, new Document(PESOTOINT, "$pendiente"))
								.append(TOTAL, new Document(PESOTOINT, "$total"))
								.append(AVANCE, new Document(PESOTOINT, new Document("$round",
										Arrays.asList(new Document("$multiply",
												Arrays.asList(new Document("$divide",
														Arrays.asList(PESOCOMPLETADO, "$total")),
														CIEN)),
												0L))))),
						new Document("$sort", new Document("_id", 1L)),
						new Document("$skip",
								(reqPaginado.getPagina() - 1) * reqPaginado.getElementosPagina()),
						new Document("$limit", reqPaginado.getElementosPagina())));
	}

	/**
	 * Metodo para obtener el conteo de registros
	 * 
	 * @return objeto Document con la respuesta de la consulta
	 */
	public AggregateIterable<Document> conteoConsultaRespuesta() {
		return coleccionRespuestasCuestionariosDoc
				.aggregate(Arrays.asList(new Document("$group", new Document("_id", "$sucursal")),
						new Document("$group", new Document("_id", new BsonNull()).append("total",
								new Document("$sum", 1L)))));
	}

	/**
	 * Metodo para obtener los datos de una sucursal
	 * 
	 * @param sucursal - sucursal a buscar
	 * 
	 * @return objeto BeanSucursales con la respuesta de la consulta
	 */
	public BeanSucursales datosSucursal(int sucursal) {
		return coleccionSucursales.find(Filters.eq(SUCURSAL, sucursal)).first();
	}

	/**
	 * Metodo para certificar una sucursal
	 * 
	 * @param sucursal - sucursal a certificar
	 * 
	 * @return objeto UpdateResult con con el resultado de la actualización
	 */
	public UpdateResult certificaSucursal(String sucursal) {
		BeanSucursales consultarCuestionarios = coleccionSucursales
				.find(Filters.eq(SUCURSAL, Integer.parseInt(sucursal))).first();

		return coleccionSucursales.updateOne(eq(SUCURSAL, Integer.parseInt(sucursal)),
				set(CERTIFICAR, !consultarCuestionarios.isCertificar()));

	}

	/**
	 * Metodo para consultar una sucursal
	 * 
	 * @param sucursal - sucursdal a consultar
	 * 
	 * @return objeto Document con el resultado de la consulta
	 */
	public BeanSucursales consultaSucursal(String sucursal) {
		return coleccionSucursales.find(Filters.eq(SUCURSAL, Integer.parseInt(sucursal))).first();

	}

	/**
	 * Metodo para consultar las respuestas de una sucursal
	 * 
	 * @param reqRespuestasSucursal - request de la solicitud
	 * 
	 * @return objeto RespPaginadoRespuestas con la respuesta de la consulta
	 */
	public AggregateIterable<Document> consultaRespuestasSucursal(
			ReqRespuestasSucursal reqRespuestasSucursal) {

		return coleccionRespuestasCuestionariosDoc
				.aggregate(Arrays.asList(new Document(PESOLOOKUP,
						new Document("from", CUESTIONARIOS).append(LOCALFIELD, IDCUESTIONARIO)
								.append(FOREIGNFIELD, "_id").append("as", "cuestionario_info")),
						new Document("$unwind", "$cuestionario_info"), new Document(PESOMATCH,
								new Document(SUCURSAL, reqRespuestasSucursal.getSucursal())),
						new Document(PESOADDFIELDS, new Document(PREGUNTASRESPONDIDAS,
								new Document(PESOSIZE,
										new Document("$ifNull",
												Arrays.asList("$respuestas", Arrays.asList()))))
								.append(PREGUNTAS, new Document(PESOSIZE,
										new Document(
												"$ifNull", Arrays
														.asList("$cuestionario_info.preguntas",
																Arrays.asList()))))),
						new Document(PESOADDFIELDS, new Document(AVANCE,
								new Document(PESOTOINT, new Document("$multiply",
										Arrays.asList(new Document("$divide",
												Arrays.asList("$preguntasRespondidas",
														new Document(PESOCOND, Arrays.asList(
																new Document("$eq",
																		Arrays.asList(PESOPREGUNTAS,
																				0L)),
																1L, PESOPREGUNTAS)))),
												CIEN))))),
						new Document(PESOGROUP,
								new Document("_id", new BsonNull())
										.append(TOTALREGISTROS, new Document("$sum", 1L))
										.append(AVANCETOTAL, new Document("$avg", "$avance"))
										.append(CUESTIONARIOS, new Document("$push",
												new Document("id", "$idCuestionario")
														.append(TITULO, "$cuestionario_info.titulo")
														.append(FECHAREGISTRO,
																"$cuestionario_info.fechaRegistro")
														.append(PREGUNTAS, PESOPREGUNTAS)
														.append(PREGUNTASRESPONDIDAS,
																"$preguntasRespondidas")
														.append(AVANCE, "$avance")))),
						new Document(PESOADDFIELDS, new Document(
								AVANCETOTAL,
								new Document(PESOTOINT,
										new Document("$avg", "$cuestionarios.avance")))
								.append(TOTALREGISTROS, "$totalRegistros")),
						new Document(PESOPROJECT, new Document("_id", 0L).append(TOTALREGISTROS, 1L)
								.append(AVANCETOTAL, 1L)
								.append(CUESTIONARIOS, new Document("$slice", Arrays.asList(
										"$cuestionarios",
										(reqRespuestasSucursal.getPagina() - 1)
												* reqRespuestasSucursal.getElementosPagina(),
										reqRespuestasSucursal.getElementosPagina()))))));
	}

	/**
	 * Metodo para insertar los cuestionarios en histórico
	 * 
	 * @param sucursal - sucursal a consultar
	 * 
	 * @return objeto MongoCursor con los datos de la consulta
	 */
	public MongoCursor<BeanCuestionarios> getCuestionarios(String sucursal) {

		return coleccionCuestionarios.find(Filters.in(SUCURSALES, sucursal)).iterator();
	}

	/**
	 * Metodo para actualizar un cuestionario en histórico
	 * 
	 * @param cuestionario - cuestionrio a actualizar
	 * @param id           - id del cuestioanrio
	 * 
	 * @return true/false
	 */
	public boolean actualizaCuestionarioHist(BeanCuestionarios cuestionario, ObjectId id) {
		return coleccionCuestionariosHistorico
				.replaceOne(Filters.eq("_id", id), cuestionario, new ReplaceOptions().upsert(true))
				.wasAcknowledged();
	}

	/**
	 * Metodo para consultar los cuestionarios del histórico
	 * 
	 * @param reqPaginado - request de la solicitud
	 * 
	 * @return objeto RespPaginadoCuestionarios con el resultado de la consulta
	 */
	public List<BeanCuestionarios> consultaCuestionariosHistorico(ReqPaginado reqPaginado) {

		Bson project = Aggregates.project(Projections
				.fields(Projections.include("_id", TITULO, FECHAREGISTRO, SUCURSALES, PREGUNTAS)));

		Bson skip = Aggregates
				.skip((reqPaginado.getPagina() - 1) * reqPaginado.getElementosPagina());
		Bson limit = Aggregates.limit(reqPaginado.getElementosPagina());

		return coleccionCuestionariosHistorico.aggregate(Arrays.asList(project, skip, limit))
				.into(new ArrayList<>());

	}

	/**
	 * Metodo para obtener el total de cuestionarios
	 * 
	 * @return total de cuestionarios
	 */
	public long conteoCuestionariosHist() {
		return coleccionCuestionariosHistorico.countDocuments();
	}

	/**
	 * Metodo para consultar las preguntas del histórico
	 * 
	 * @param id - id de la solicitud
	 * 
	 * @return objeto Document con la respuesta de la consulta
	 */
	public MongoCursor<BeanCuestionarios> consultaPregCuestionariosHist(String id) {

		Bson filter = Filters.eq("_id", new ObjectId(id));

		return coleccionCuestionariosHistorico.find(filter).iterator();

	}

	/**
	 * Metodo para consultar las sucursales asignadas a un cuestionario en histórico
	 * 
	 * @param id - id del cuestionario
	 * 
	 * @return objeto Document con la respuesta de la consulta
	 */
	public BeanCuestionarios consultaSucAsignadasHist(String id) {

		return coleccionCuestionariosHistorico.find(new Document("_id", new ObjectId(id)))
				.projection(new Document(SUCURSALES, 1).append("_id", 0)).first();

	}

	/**
	 * Metodo para consultar las preguntas y respuestas de un cuestionario por
	 * sucursal
	 * 
	 * @param reqPregRespSucursal - request de la solicitud
	 * 
	 * @return objeto Document con la respuesta del servicio
	 */
	public AggregateIterable<Document> consultaPreguntasSuc(
			ReqPregRespSucursal reqPregRespSucursal) {

		return coleccionCuestionariosDoc
				.aggregate(
						Arrays.asList(
								new Document("$match",
										new Document("sucursales",
												new Document("$in", Arrays.asList(
														"" + reqPregRespSucursal.getSucursal())))
												.append("_id",
														new ObjectId(reqPregRespSucursal.getId()))),
								new Document("$project",
										new Document("_id", 1L).append("preguntas", 1L))));

	}

	/**
	 * Metodo para obtener las respuestas de un cuestionario
	 * 
	 * @param reqPregRespSucursal - request de la solicitud
	 * 
	 * @return objeto Document con la respuesta de la consulta
	 */
	public AggregateIterable<Document> consultaRespuestasSuc(
			ReqPregRespSucursal reqPregRespSucursal) {
		return coleccionRespuestasCuestionariosDoc.aggregate(Arrays.asList(
				new Document("$match",
						new Document("sucursal", reqPregRespSucursal.getSucursal())
								.append(IDCUESTIONARIO, new ObjectId(reqPregRespSucursal.getId()))),
				new Document("$project", new Document("_id", 1L).append("respuestas", 1L))));
	}

	/**
	 * Metodo para consultar los cuestionarios del histórico
	 * 
	 * @param reqPaginadoBitacora - request de la solicitud
	 * 
	 * @return objeto RespPaginadoCuestionarios con el resultado de la consulta
	 */
	public List<BeanCuestionariosBitacora> consultaBitacoraCuestionario(
			ReqPaginadoBitacora reqPaginadoBitacora) {

		Bson match = Aggregates.match(Filters.eq(IDCUESTIONARIO, reqPaginadoBitacora.getId()));

		Bson project = Aggregates.project(Projections.fields(Projections.include("evento", ESTATUS,
				CERTIFICAR, TITULO, FECHAREGISTRO, SUCURSALES, PREGUNTAS)));

		// Orden ascendente por _id
		Bson sort = Aggregates.sort(Sorts.descending("_id"));

		Bson skip = Aggregates.skip(
				(reqPaginadoBitacora.getPagina() - 1) * reqPaginadoBitacora.getElementosPagina());
		Bson limit = Aggregates.limit(reqPaginadoBitacora.getElementosPagina());

		return coleccionBitacoraCuestionarios
				.aggregate(Arrays.asList(match, project, sort, skip, limit))
				.into(new ArrayList<>());

	}

	/**
	 * Metodo para obtener el total de cuestionarios
	 * 
	 * @param reqPaginadoBitacora - rquest de la solicitud
	 * 
	 * @return total de registros
	 */
	public long conteoBitacoraCuestionario(ReqPaginadoBitacora reqPaginadoBitacora) {
		Bson filter = Filters.eq(IDCUESTIONARIO, reqPaginadoBitacora.getId());

		return coleccionBitacoraCuestionarios.countDocuments(filter);
	}

	/**
	 * Metodo para obtener los topes de Caja
	 * 
	 * @param divisa   - divisa operada
	 * @param idCaja   - caja operada
	 * @param sucursal - sucursal operada
	 * 
	 * @return cursor Mongo con el resultado de la consulta
	 */
	public MongoCursor<BeanTopesCaja> consultaTopeCaja(List<Integer> divisa, List<Integer> idCaja,
			int sucursal) {
		return coleccionTopesCaja
				.find(Filters.and(Filters.in("idDivisa", divisa), Filters.in("idCaja", idCaja),
						Filters.eq("idSucursal", sucursal)))
				.sort(Sorts.ascending("idDivisa")).iterator();

	}

	/**
	 * Metodo para obtener los cuestionarios del histórico por mes y anio
	 * 
	 * @param reqFiltroCuestionarios - request de la solicitud
	 * 
	 * @return objeto AggregateIterable con el resultado de la consulta
	 */
	public AggregateIterable<Document> getCuestionariosFiltroHis(
			ReqFiltroCuestionarios reqFiltroCuestionarios) {

		return coleccionCuestionariosHistoricoDoc
				.aggregate(Arrays.asList(
						new Document("$addFields", new Document("year",
								new Document("$toInt",
										new Document("$substr",
												Arrays.asList("$fechaRegistro", 0L, 4L))))
								.append("month",
										new Document("$toInt",
												new Document("$substr",
														Arrays.asList("$fechaRegistro", 5L, 2L))))),
						new Document("$match",
								new Document("year", reqFiltroCuestionarios.getAnio())
										.append("month", reqFiltroCuestionarios.getMes())),
						new Document("$lookup",
								new Document("from", "respuestasCuestionarios")
										.append("localField", "_id")
										.append("foreignField", "idCuestionario")
										.append("as", "secondCollectionData")),
						new Document("$unwind", "$secondCollectionData"),
						new Document("$match", new Document("sucursales",
								new Document("$in",
										Arrays.asList(reqFiltroCuestionarios.getSucursal())))),
						new Document("$project",
								new Document("_id", 0L).append("titulo", 1L).append("preguntas", 1L)
										.append("respuestas", "$secondCollectionData.respuestas")
										.append("fechaRegistro", 1L))));
	}
}
