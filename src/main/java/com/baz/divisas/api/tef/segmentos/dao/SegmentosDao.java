package com.baz.divisas.api.tef.segmentos.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.general.model.SegmentoSucursales;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase SegmentosDao.
 *
 * @author 164945 ogatica
 */
@Repository
public class SegmentosDao {

	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The terminal financiero BD. */
	private MongoDatabase terminalFinancieroBD;
	
	/** The coleccion segmentos. */
	private MongoCollection<Segmento> coleccionSegmentos;
	
	/** The coleccion segmentos. */
	private MongoCollection<Segmento> coleccionSegmentosOlp;
	
	/** The coleccion segmento sucursales. */
	private MongoCollection<SegmentoSucursales> coleccionSegmentoSucursales;
	
	/** The coleccion segmento sucursales. */
	private MongoCollection<SegmentoSucursales> coleccionSegmentoOLPSucursales;

	/** The Constant ACTIVO. */
	private static final String ACTIVO = "activo";
	
	/** The Constant DESCRIPCION. */
	private static final String DESCRIPCION = "descripcion";
	
	/** The Constant ID. */
	private static final String ID = "_id";
	
	/** The Constant ASIGNADO. */
	private static final String ASIGNADO = "asignado";
	
	/** The Constant IDREGION. */
	private static final String IDREGION = "idRegion"; 
	
	/** The Constant SUCURSALES. */
	private static final String SUCURSALES = "sucursales"; 


	/**
	 * Constructor de la clase.
	 */
	public SegmentosDao() {
		super();
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionSegmentos = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccion(), Segmento.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionSegmentosOlp = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccionOlp(), Segmento.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionSegmentoSucursales = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccion(), SegmentoSucursales.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionSegmentoOLPSucursales  = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccionOlp(), SegmentoSucursales.class)
				.withCodecRegistry(pojoCodecRegistry);
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 * @param coleccionSegmentoSucursales the coleccion segmento sucursales
	 * @param coleccionSegmentosOlp the coleccion segmentos olp
	 * @param coleccionSegmentoOLPSucursales the coleccion segmento OLP sucursales
	 */
	public SegmentosDao(MongoCollection<Segmento> coleccion,MongoCollection<SegmentoSucursales> coleccionSegmentoSucursales,MongoCollection<Segmento> coleccionSegmentosOlp,MongoCollection<SegmentoSucursales> coleccionSegmentoOLPSucursales) {
		super();
		this.coleccionSegmentos = coleccion;
		this.coleccionSegmentoSucursales = coleccionSegmentoSucursales;
		this.coleccionSegmentosOlp = coleccionSegmentosOlp;
		this.coleccionSegmentoOLPSucursales = coleccionSegmentoOLPSucursales;
	}


	/**
	 * Metodo de consulta de todos los segmentos.
	 *
	 * @return - Consulta de segmentos
	 */
	public List<Segmento> findAll() {

		return coleccionSegmentos.find().projection(exclude("divisas.tiposOperacion", SUCURSALES ))
				.sort(Sorts.descending(ID)).into(new ArrayList<Segmento>());
	}

	/**
	 * Metodo de consulta de todos los segmentos activos.
	 *
	 * @return - Segmentos Activos
	 */
	public List<Segmento> findAllActive() {

		return coleccionSegmentos.find(eq(ACTIVO, true)).sort(Sorts.descending(ID)).into(new ArrayList<Segmento>());

	}
	
	/**
	 * Metodo de consulta de segmentos por id.
	 *
	 * @param id - Id de Segmento
	 * @return - Segmento
	 */
	public Segmento findById(String id) {

		return coleccionSegmentos.find(eq(ID, new ObjectId(id))).first();

	}

	/**
	 * Metodo de consulta de segmentos por descripcion.
	 *
	 * @param descripcion - Descripcion del segmento
	 * @return - Segmento
	 */
	public Segmento findByDescripcion(String descripcion) {

		return coleccionSegmentos.find(eq(DESCRIPCION, descripcion.trim())).first();
	}

	/**
	 * Metodo de consulta de una lista de sementos.
	 *
	 * @param segmentos - Lista de segmentos
	 * @return Segmentos
	 */
	public List<Segmento> findList(Collection<String> segmentos) {

		List<ObjectId> idsSegmentos = segmentos.stream().map(ObjectId::new).collect(Collectors.toList());

		return coleccionSegmentos.find(and(in(ID, idsSegmentos), eq(ACTIVO, true), eq(ASIGNADO, false)))
				.into(new ArrayList<Segmento>());

	}
	
	/**
	 * Metodo de consulta de una lista de sementos por divisa.
	 *
	 * @param segmentos - Lista de segmentos
	 * @param idDivisa - Id de Divisa
	 * @return Segmentos
	 */
	public List<Segmento> findListByDivisa(Collection<String> segmentos, String idDivisa) {

		List<ObjectId> idsSegmentos = segmentos.stream().map(ObjectId::new).collect(Collectors.toList());

		return coleccionSegmentos.aggregate(Arrays.asList(
					match(and(in(ID, idsSegmentos), eq(ACTIVO, true))),
					unwind("$divisas"),
					match(and(eq("divisas._id", idDivisa), eq("divisas.activo", true), eq("divisas.tiposOperacion.activo", true))),
					project(exclude("divisas"))
				)).allowDiskUse(true).into(new ArrayList<Segmento>());

	}
	
	/**
	 * Metodo de registro de segmentos.
	 *
	 * @param segmento - Segmento a registrar
	 * @return InsertOneResult
	 */
	public InsertOneResult insert(Segmento segmento) {

		return coleccionSegmentos.insertOne(segmento);

	}

	/**
	 * Metodo de eliminacion del segmento.
	 *
	 * @param id - Id de la region
	 * @return DeleteResult
	 */
	public DeleteResult delete(String id) {

		return coleccionSegmentos.deleteMany(eq(ID, new ObjectId(id)));
	}	
	
	/**
	 * Metodo de actualización de la descripcion del segmento.
	 *
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @return - UpdateResult
	 */
	public UpdateResult updateDescripcion(String id, String descripcion) {

		return coleccionSegmentos.updateMany(eq(ID, new ObjectId(id)), set(DESCRIPCION, descripcion));

	}
	
	/**
	 * Metodo de actualización de estatus del segmento.
	 *
	 * @param id     - Id de la region
	 * @param activo - Estatus
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatus(String id, boolean activo) {

		return coleccionSegmentos.updateOne(eq(ID, new ObjectId(id)), set(ACTIVO, activo));

	}

	/**
	 * Metodo de actualización de sucursales del segmento.
	 *
	 * @param id - Id del segmento
	 * @param sucursales - sucursales
	 * @return UpdateResult
	 */
	public UpdateResult updateSucursales(String id, List<String> sucursales) {

		return coleccionSegmentos.updateOne(eq(ID, new ObjectId(id)), combine(set(SUCURSALES, sucursales)));
	}

	/**
	 * Metodo que agrega una nueva divisa a todas los segmentos.
	 *
	 * @param divisa - Divisa
	 * @return UpdateResult
	 */
	public UpdateResult addDivisa(Divisa divisa) {

		return coleccionSegmentos.updateMany(and(eq(ID, new Document("$exists", true)), ne(ID, divisa.getId())),
				addToSet("divisas", divisa));
	}

	/**
	 * Metodo de actualización de segmentos Asigando/Desasignado.
	 *
	 * @param segmentos - Lista de segmentos
	 * @param asignado - true/false
	 * @return UpdateResult
	 */
	public UpdateResult updateSegmentosAsignados(Collection<String> segmentos, boolean asignado) {
		
		List<ObjectId> idsSegmentos = segmentos.stream().map(ObjectId::new).collect(Collectors.toList());
		
		return coleccionSegmentos.updateMany(in(ID, idsSegmentos), set(ASIGNADO, asignado));
	}
	
	/**
	 * Metodo para obtener las sucursales de los segmentos.
	 *
	 * @return Segmentos
	 */
	public List<Segmento> findSucursalesSegmento() {

		return coleccionSegmentos.aggregate(Arrays.asList(
											match(and(eq(ACTIVO, true), eq(ASIGNADO, true))),
											lookup("catRegiones", ID, "segmentos", "regiones"),
											unwind("$regiones"),
											project(fields(include(ACTIVO, ASIGNADO, SUCURSALES),
														   computed(IDREGION, "$regiones._id"),
														   computed(DESCRIPCION, "$regiones.descripcion")))))
				.allowDiskUse(true).into(new ArrayList<Segmento>());
	}
	
	
	/**
	 * List TC segmento sucursales.
	 *
	 * @param idSegmento the id segmento
	 * @param divisa the divisa
	 * @return the segmento sucursales
	 */
	public SegmentoSucursales listTCSegmentoSucursales(String idSegmento,String divisa) {
		Document project = new Document();
		project.append("_id",1)
		.append(DESCRIPCION , 1)
		.append("sucursal",1);

		Document projectFinal = new Document();
		projectFinal.append("sucursales._id",1)
		.append("sucursales.divisas",1)
		.append("descripcion","$_id.descripcion").append("idSegmento","$_id._id");

		return  coleccionSegmentoSucursales.aggregate(Arrays.asList(
				match(and(eq(ACTIVO, true),eq("_id",new ObjectId(idSegmento)))),
				lookup("sucursales", "sucursales", "_id", "sucursal"),
				project(project),
				unwind("$sucursal"),
				unwind("$sucursal.divisas"),
				match(eq("sucursal.divisas._id",divisa)),
				group(new Document("_id","$_id").append("descripcion","$descripcion"), Accumulators.addToSet("sucursales", "$sucursal")),
				project(projectFinal),
				new Document("$sort", new Document("descripcion" , 1))
				)).allowDiskUse(true).first();              	 
	}
	
	/**
	 * List segmentos sucursal.
	 *
	 * @return the list
	 */
	public List<Segmento> listSegmentosSucursal() {

		Document project = new Document();

        project.append("_id",1)
        .append(DESCRIPCION , 1)
        .append("sucursal",1);
        
		return coleccionSegmentos
				.aggregate(Arrays.asList(match(eq(ACTIVO, true)), lookup("sucursales", "sucursales", "_id", "sucursal"),
						project(project), unwind("$sucursal"),
						match(and(eq("sucursal.nomEstatus", "ACTIVO"), eq("sucursal.idEstatus", 1),
								eq("sucursal.divisas",
										new Document("$exists", true).append("$not", new Document("$size", 0))
												.append("$ne", null)))),
						group(new Document("_id", "$_id").append("descripcion", "$descripcion"),
								Accumulators.addToSet("sucursales", "$sucursal")),
						project(new Document("_id", 0).append("_id", "$_id._id")
								.append("descripcion", "$_id.descripcion").append("sucursales", "$sucursales._id")),
						new Document("$sort", new Document("descripcion", 1))))
				.allowDiskUse(true).into(new ArrayList<Segmento>());

	}

	/**
	 * List segmentos sucursal olp.
	 *
	 * @return the list
	 */
	public List<Segmento> listSegmentosSucursalOlp() {
		Document project = new Document();

        project.append("_id",1)
        .append(DESCRIPCION , 1)
        .append("sucursal",1);
        
		return coleccionSegmentosOlp
				.aggregate(Arrays.asList(match(eq(ACTIVO, true)), lookup("sucursales", "sucursales", "_id", "sucursal"),
						project(project), unwind("$sucursal"),
						match(and(eq("sucursal.nomEstatus", "ACTIVO"), eq("sucursal.idEstatus", 1),
								eq("sucursal.tcOlp",
										new Document("$exists", true)))),
						group(new Document("_id", "$_id").append("descripcion", "$descripcion"),
								Accumulators.addToSet("sucursales", "$sucursal")),
						project(new Document("_id", 0).append("_id", "$_id._id")
								.append("descripcion", "$_id.descripcion").append("sucursales", "$sucursales._id")),
						new Document("$sort", new Document("descripcion", 1))))
				.allowDiskUse(true).into(new ArrayList<Segmento>());
	}

	/**
	 * List TC segmento sucursales olp.
	 *
	 * @param idSegmento the id segmento
	 * @return the segmento sucursales
	 */
	public SegmentoSucursales listTCSegmentoSucursalesOlp(String idSegmento) {
		Document project = new Document();
		project.append("_id",1)
		.append(DESCRIPCION , 1)
		.append("sucursal",1);

		Document projectFinal = new Document();
		projectFinal.append("sucursales._id",1)
		.append("sucursales.tcOlp",1)
		.append("descripcion","$_id.descripcion").append("idSegmento","$_id._id");

		return  coleccionSegmentoOLPSucursales.aggregate(Arrays.asList(
				match(and(eq(ACTIVO, true),eq("_id",new ObjectId(idSegmento)))),
				lookup("sucursales", "sucursales", "_id", "sucursal"),
				project(project),
				unwind("$sucursal"),
				unwind("$sucursal.tcOlp"),
				group(new Document("_id","$_id").append("descripcion","$descripcion"), Accumulators.addToSet("sucursales", "$sucursal")),
				project(projectFinal),
				new Document("$sort", new Document("descripcion" , 1))
				)).allowDiskUse(true).first();                   
	}

	/**
	 * Find all olp.
	 *
	 * @return the list
	 */
	public List<Segmento> findAllOlp() {
		return coleccionSegmentosOlp.find().projection(exclude("divisas.tiposOperacion", SUCURSALES ))
				.sort(Sorts.descending(ID)).into(new ArrayList<Segmento>());
	}

	/**
	 * Find by id olp.
	 *
	 * @param id the id
	 * @return the segmento
	 */
	public Segmento findByIdOlp(String id) {
		return coleccionSegmentosOlp.find(eq(ID, new ObjectId(id))).first();
	}

	/**
	 * Update estatus olp.
	 *
	 * @param id the id
	 * @param activo the activo
	 * @return the update result
	 */
	public UpdateResult updateEstatusOlp(String id, boolean activo) {

		return coleccionSegmentosOlp.updateOne(eq(ID, new ObjectId(id)), set(ACTIVO, activo));
	}

	/**
	 * Delete olp.
	 *
	 * @param id the id
	 * @return the delete result
	 */
	public DeleteResult deleteOlp(String id) {
		return coleccionSegmentosOlp.deleteMany(eq(ID, new ObjectId(id)));
		
	}

	/**
	 * Update descripcion olp.
	 *
	 * @param id the id
	 * @param descripcion the descripcion
	 * @return the update result
	 */
	public UpdateResult updateDescripcionOlp(String id, String descripcion) {

		return coleccionSegmentosOlp.updateMany(eq(ID, new ObjectId(id)), set(DESCRIPCION, descripcion));
	}

	/**
	 * Insert olp.
	 *
	 * @param segmento the segmento
	 * @return the insert one result
	 */
	public InsertOneResult insertOlp(Segmento segmento) {

		return coleccionSegmentosOlp.insertOne(segmento);
	}

	/**
	 * Find by descripcion olp.
	 *
	 * @param descripcion the descripcion
	 * @return the segmento
	 */
	public Segmento findByDescripcionOlp(String descripcion) {

		return coleccionSegmentosOlp.find(eq(DESCRIPCION, descripcion.trim())).first();
	}

	/**
	 * Update sucursales olp.
	 *
	 * @param id the id
	 * @param sucursales the sucursales
	 * @return the update result
	 */
	public UpdateResult updateSucursalesOlp(String id, List<String> sucursales) {

		return coleccionSegmentosOlp.updateOne(eq(ID, new ObjectId(id)), combine(set(SUCURSALES, sucursales)));
	}

	/**
	 * Find list olp.
	 *
	 * @param segmentos the segmentos
	 * @return the list
	 */
	public List<Segmento> findListOlp(List<String> segmentos) {
		List<ObjectId> idsSegmentos = segmentos.stream().map(ObjectId::new).collect(Collectors.toList());

		return coleccionSegmentosOlp.find(and(in(ID, idsSegmentos), eq(ACTIVO, true), eq(ASIGNADO, false)))
				.into(new ArrayList<Segmento>());
	}

	/**
	 * Update segmentos asignados olp.
	 *
	 * @param segmentos the segmentos
	 * @param asignado the asignado
	 * @return the update result
	 */
	public UpdateResult updateSegmentosAsignadosOlp(Collection<String> segmentos, boolean asignado) {
		
		List<ObjectId> idsSegmentos = segmentos.stream().map(ObjectId::new).collect(Collectors.toList());
		
		return coleccionSegmentosOlp.updateMany(in(ID, idsSegmentos), set(ASIGNADO, asignado));
		
	}

	public List<Segmento> findSucursalesSegmentoOlp() {
		return coleccionSegmentosOlp.aggregate(Arrays.asList(
				match(and(eq(ACTIVO, true), eq(ASIGNADO, true))),
				lookup("catRegionesOlp", ID, "segmentos", "regiones"),
				unwind("$regiones"),
				project(fields(include(ACTIVO, ASIGNADO, SUCURSALES),
						computed(IDREGION, "$regiones._id"),
						computed(DESCRIPCION, "$regiones.descripcion")))))
				.allowDiskUse(true).into(new ArrayList<Segmento>());
	}
	
}
