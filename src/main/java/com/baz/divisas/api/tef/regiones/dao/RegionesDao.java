package com.baz.divisas.api.tef.regiones.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.general.model.RegionSegmentosSucursales;
import com.baz.divisas.api.tef.general.model.RegionSucursales;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase RegionesDao.
 *
 * @author 164945 ogatica
 */
@Repository
public class RegionesDao {

	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The terminal financiero BD. */
	private MongoDatabase terminalFinancieroBD;
	
	/** The coleccion regiones. */
	private MongoCollection<Region> coleccionRegiones;
	
	/** The coleccion regiones. */
	private MongoCollection<Region> coleccionRegionesOLP;
	
	/** The coleccion RSS. */
	private MongoCollection<RegionSegmentosSucursales> coleccionRSS;
	
	/** The coleccion RSS. */
	private MongoCollection<RegionSegmentosSucursales> coleccionOlpRSS;
	
	/** The coleccion regiones sucursales. */
	private MongoCollection<RegionSucursales> coleccionRegionesSucursales;
	
	/** The coleccion regiones sucursales. */
	private MongoCollection<RegionSucursales> coleccionRegionesOlpSucursales;

	/** The Constant ACTIVO. */
	private static final String ACTIVO = "activo";
	
	/** The Constant DESCRIPCION. */
	private static final String DESCRIPCION = "descripcion";
	
	/** The Constant ID. */
	private static final String ID = "_id";

	/**
	 * Constructor de la clase.
	 */
	public RegionesDao() {
		super();
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionRegiones = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccion(), Region.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionRSS = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccion(), RegionSegmentosSucursales.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionOlpRSS = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccionOlp(), RegionSegmentosSucursales.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionRegionesSucursales = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccion(),RegionSucursales.class).withCodecRegistry(pojoCodecRegistry);
		coleccionRegionesOLP = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccionOlp(), Region.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionRegionesOlpSucursales = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccionOlp(),RegionSucursales.class).withCodecRegistry(pojoCodecRegistry);
	}
		
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 * @param coleccionRSS the coleccion RSS
	 * @param coleccionRegionesSucursales the coleccion regiones sucursales
	 * @param coleccionRegionesOLP the coleccion regiones OLP
	 */
	public RegionesDao(MongoCollection<Region> coleccion, MongoCollection<RegionSegmentosSucursales> coleccionRSS,
			MongoCollection<RegionSucursales> coleccionRegionesSucursales,
			MongoCollection<Region> coleccionRegionesOLP,MongoCollection<RegionSegmentosSucursales> coleccionOlpRSS,MongoCollection<RegionSucursales> coleccionRegionesOlpSucursales) {
		super();
		this.coleccionRegiones = coleccion;
		this.coleccionRSS = coleccionRSS;
		this.coleccionRegionesSucursales = coleccionRegionesSucursales;
		this.coleccionRegionesOLP = coleccionRegionesOLP;
		this.coleccionOlpRSS = coleccionOlpRSS;
		this.coleccionRegionesOlpSucursales = coleccionRegionesOlpSucursales;
	}


	/**
	 * Metodo de consulta de todas las regiones activas.
	 *
	 * @return - Regiones Activas
	 */
	public List<Region> findAllActive() {

		return coleccionRegiones.find(eq(ACTIVO, true)).sort(Sorts.descending(ID)).into(new ArrayList<Region>());

	}
	
	/**
	 * Metodo de consulta de todas las regiones.
	 *
	 * @param fieldsExclude - Campos a excluir
	 * @return - Consulta de regiones
	 */
	public List<Region> findAllAndExcludeFields(List<String> fieldsExclude) {

		return coleccionRegiones.find().projection(exclude(fieldsExclude))
				.sort(Sorts.descending(ID)).into(new ArrayList<Region>());

	}

	/**
	 * Metodo de consulta de regiones por id.
	 *
	 * @param id - Id de Region
	 * @return - Region
	 */
	public Region findById(String id) {

		return coleccionRegiones.find(eq(ID, new ObjectId(id))).first();

	}

	/**
	 * Metodo de consulta de regiones por descripcion.
	 *
	 * @param descripcion - Descripcion
	 * @return - Region
	 */
	public Region findByDescripcion(String descripcion) {

		return coleccionRegiones.find(eq(DESCRIPCION, descripcion.trim())).first();

	}
	
	/**
	 * Metodo de consulta de regiones por Id de region y segmento.
	 *
	 * @param idRegion - Id de la Region
	 * @param idSegmento - Id del Segmento
	 * @return Region
	 */
	public Region findByRegionSegmento(String idRegion, String idSegmento) {

		return coleccionRegiones.find(and(eq("_id", new ObjectId(idRegion)), 
										  eq(ACTIVO, true),
									      eq("segmentos", new ObjectId(idSegmento)))).first();

	}
	
	/**
	 * Metodo de consulta de regiones por divisa.
	 *
	 * @param idDivisa - Id de Divisa
	 * @return Regiones
	 */
	public List<Region> findByDivisa(String idDivisa) {

		return coleccionRegiones.aggregate(Arrays.asList(
					match(eq(ACTIVO, true)),
					unwind("$divisas"),
					match(and(eq("divisas._id", idDivisa), eq("divisas.activo", true), eq("divisas.tiposOperacion.activo", true))),
					project(exclude("divisas"))
				)).allowDiskUse(true).into(new ArrayList<Region>());

	}

	/**
	 * Metodo de registro de regiones.
	 *
	 * @param region - Region a registrar
	 * @return InsertOneResult
	 */
	public InsertOneResult insert(Region region) {

		return coleccionRegiones.insertOne(region);

	}

	/**
	 * Metodo de actualización de la descripcion de la region.
	 *
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @return - UpdateResult
	 */
	public UpdateResult updateDescripcion(String id, String descripcion) {

		return coleccionRegiones.updateMany(eq(ID, new ObjectId(id)), 
											set(DESCRIPCION, descripcion));
	}

	/**
	 * Metodo de actualización de estatus de la region.
	 *
	 * @param id     - Id de la region
	 * @param activo - Estatus
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatus(String id, boolean activo) {

		return coleccionRegiones.updateOne(eq(ID, new ObjectId(id)), set(ACTIVO, activo));

	}

	/**
	 * Metodo de eliminacion de la region.
	 *
	 * @param id - Id de la region
	 * @return DeleteResult
	 */
	public DeleteResult delete(String id) {

		return coleccionRegiones.deleteMany(eq(ID, new ObjectId(id)));

	}

	/**
	 * Metodo de actualizacion de segmentos de la region.
	 *
	 * @param id - Id de la region
	 * @param segmentos - Segmentos
	 * @return UpdateResult
	 */
	public UpdateResult updateSegmentos(String id, List<ObjectId> segmentos) {

		return coleccionRegiones.updateOne(eq(ID, new ObjectId(id)), set("segmentos", segmentos));

	}

	/**
	 * Metodo de actualización de sucursales de la region.
	 *
	 * @param id - Id de la region
	 * @param sucursales - Sucursales
	 * @return - UpdateResult
	 */
	public UpdateResult updateSucursales(String id, List<String> sucursales) {

		return coleccionRegiones.updateOne(eq(ID, new ObjectId(id)), set("sucursales", sucursales));

	}

	/**
	 * Metodo que agrega una nueva divisa a todas las regiones.
	 *
	 * @param divisa - Divisa
	 * @return UpdateResult
	 */
	public UpdateResult addDivisa(Divisa divisa) {

		return coleccionRegiones.updateMany(and(eq(ID, new Document("$exists", true)), ne(ID, divisa.getId())),
				addToSet("divisas", divisa));

	}
	
	/**
	 * Metodo de actualización de estatus de la Divisa en la Region.
	 *
	 * @param idRegion - Id de la region
	 * @param idDivisa - Id de la divisa
	 * @param activo - Estatus
	 * @return - UpdateResult
	 */
	public boolean updateEstatusDivisaRegion(String idRegion, String idDivisa, boolean activo) {

		List<Bson> arrFilters = new ArrayList<>();
		arrFilters.add(new Document("div._id", idDivisa));
		FindOneAndUpdateOptions updateOptions = new FindOneAndUpdateOptions().arrayFilters(arrFilters);

		Region region = coleccionRegiones.findOneAndUpdate(eq(ID, new ObjectId(idRegion)), 
															 set("divisas.$[div].activo", activo),
															 updateOptions);

		return region != null;
	}

	/**
	 * List regiones segmentos sucursal.
	 *
	 * @return the list
	 */
	public List<RegionSegmentosSucursales> listRegionesSegmentosSucursal () {
		Document project = new Document();

        project.append("_id",1)
        .append(DESCRIPCION , 1)
        .append("sucursal",1)
        .append("segmentos",1);
        
        return coleccionRSS.aggregate(Arrays.asList(
                match(eq(ACTIVO, true)),
                lookup("sucursales", "sucursales", "_id", "sucursal"),
                project(project),
                unwind("$sucursal"),
                match(and(eq("sucursal.nomEstatus","ACTIVO"),eq("sucursal.idEstatus",1),eq("sucursal.divisas", new Document("$exists", true).append("$not", new Document("$size", 0))
						.append("$ne", null)))),
                group(new Document("_id","$_id").append("descripcion","$descripcion").append("segmentos","$segmentos"), Accumulators.addToSet("sucursales", "$sucursal")),
                project(new Document("_id",0).append("idRegion","$_id._id").append("descripcion","$_id.descripcion").append("sucursales","$sucursales._id").append("segmentosRegion","$_id.segmentos")),
                new Document("$sort", new Document("descripcion" , 1))
        		)).allowDiskUse(true).into(new ArrayList<RegionSegmentosSucursales>());
	}
	
	/**
	 * List TC regiones sucursales.
	 *
	 * @param idRegion the id region
	 * @param divisa the divisa
	 * @return the region sucursales
	 */
	public RegionSucursales listTCRegionesSucursales(String idRegion,String divisa) {
		Document project = new Document();
		project.append("_id",1)
		.append(DESCRIPCION , 1)
		.append("sucursal",1);

		Document projectFinal = new Document();
		projectFinal.append("sucursales._id",1)
		.append("sucursales.divisas",1)
		.append("descripcion","$_id.descripcion").append("idRegion","$_id._id");

		return  coleccionRegionesSucursales.aggregate(Arrays.asList(
				match(and(eq(ACTIVO, true),eq("_id",new ObjectId(idRegion)))),
				lookup("sucursales", "sucursales", "_id", "sucursal"),
				project(project),
				unwind("$sucursal"),
				unwind("$sucursal.divisas"),
				match(eq("sucursal.divisas._id",divisa)),
				group(new Document("_id","$_id").append("descripcion","$descripcion"), Accumulators.addToSet("sucursales", "$sucursal")),
				project(projectFinal),
				new Document("$sort", new Document("descripcion" , 1)))).allowDiskUse(true).first();              
	}
	
	/**
	 * List TC regiones sucursales OLP.
	 *
	 * @param idRegion the id region
	 * @return the region sucursales
	 */
	public RegionSucursales listTCRegionesSucursalesOlp(String idRegion) {
		Document project = new Document();
		project.append("_id",1)
		.append(DESCRIPCION , 1)
		.append("sucursal",1);

		Document projectFinal = new Document();
		projectFinal.append("sucursales._id",1)
		.append("sucursales.tcOlp",1)
		.append("descripcion","$_id.descripcion").append("idRegion","$_id._id");

		return  coleccionRegionesOlpSucursales.aggregate(Arrays.asList(
				match(and(eq(ACTIVO, true),eq("_id",new ObjectId(idRegion)))),
				lookup("sucursales", "sucursales", "_id", "sucursal"),
				project(project),
				unwind("$sucursal"),
				unwind("$sucursal.tcOlp"),
				group(new Document("_id","$_id").append("descripcion","$descripcion"), Accumulators.addToSet("sucursales", "$sucursal")),
				project(projectFinal),
				new Document("$sort", new Document("descripcion" , 1)))).allowDiskUse(true).first(); 
	}

	

	/**
	 * List regiones segmentos sucursal OLP.
	 *
	 * @return the list
	 */
	public List<RegionSegmentosSucursales> listRegionesSegmentosSucursalOlp() {
		Document project = new Document();

        project.append("_id",1)
        .append(DESCRIPCION , 1)
        .append("sucursal",1)
        .append("segmentos",1);
        
        return coleccionOlpRSS.aggregate(Arrays.asList(
                match(eq(ACTIVO, true)),
                lookup("sucursales", "sucursales", "_id", "sucursal"),
                project(project),
                unwind("$sucursal"),
                match(and(eq("sucursal.nomEstatus","ACTIVO"),eq("sucursal.idEstatus",1),eq("sucursal.tcOlp", new Document("$exists", true)))),
                group(new Document("_id","$_id").append("descripcion","$descripcion").append("segmentos","$segmentos"), Accumulators.addToSet("sucursales", "$sucursal")),
                project(new Document("_id",0).append("idRegion","$_id._id").append("descripcion","$_id.descripcion").append("sucursales","$sucursales._id").append("segmentosRegion","$_id.segmentos")),
                new Document("$sort", new Document("descripcion" , 1))
        		)).allowDiskUse(true).into(new ArrayList<RegionSegmentosSucursales>());
	}

	/**
	 * List regiones segmentos.
	 *
	 * @return the list
	 */
	public List<RegionSegmentosSucursales> listRegionesSegmentos() {
        
        return coleccionRSS.aggregate(Arrays.asList(
                match(and(eq(ACTIVO, true),eq("sucursales", new Document("$exists", true)),eq("segmentos", new Document("$exists", true).append("$not", new Document("$size", 0).append("$ne", null))))),
                lookup("catSegmentos", "segmentos", "_id", "segmentos"),
                unwind("$segmentos"),
                match(eq("segmentos.activo",true)),                
                lookup("sucursales", "segmentos.sucursales", "_id", "segmentos.sucursalSegmento"),
                unwind("$segmentos.sucursalSegmento"),
                match(and(eq("segmentos.sucursalSegmento.nomEstatus","ACTIVO"),eq("segmentos.sucursalSegmento.idEstatus",1),eq("segmentos.sucursalSegmento.divisas", new Document("$exists", true).append("$not", new Document("$size", 0))
						.append("$ne", null)))),
                group(new Document("_id","$_id").append("descripcion","$descripcion"), Accumulators.addToSet("segmentos", "$segmentos")),
                project(new Document("_id",0).append("idRegion","$_id._id").append("descripcion","$_id.descripcion").append("segmentosRegion","$segmentos._id")),                
                new Document("$sort", new Document("descripcion" , 1))
        		)).allowDiskUse(true).into(new ArrayList<RegionSegmentosSucursales>());
	}

	/**
	 * List regiones segmentos OLP.
	 *
	 * @return the list
	 */
	public List<RegionSegmentosSucursales> listRegionesSegmentosOlp() {
		return coleccionOlpRSS.aggregate(Arrays.asList(
                match(and(eq(ACTIVO, true),eq("sucursales", new Document("$exists", true)),eq("segmentos", new Document("$exists", true).append("$not", new Document("$size", 0).append("$ne", null))))),
                lookup("catSegmentosOlp", "segmentos", "_id", "segmentos"),
                unwind("$segmentos"),
                match(eq("segmentos.activo",true)),                
                lookup("sucursales", "segmentos.sucursales", "_id", "segmentos.sucursalSegmento"),
                unwind("$segmentos.sucursalSegmento"),
                match(and(eq("segmentos.sucursalSegmento.nomEstatus","ACTIVO"),eq("segmentos.sucursalSegmento.idEstatus",1),eq("segmentos.sucursalSegmento.tcOlp", new Document("$exists", true)))),
                group(new Document("_id","$_id").append("descripcion","$descripcion"), Accumulators.addToSet("segmentos", "$segmentos")),
                project(new Document("_id",0).append("idRegion","$_id._id").append("descripcion","$_id.descripcion").append("segmentosRegion","$segmentos._id")),                
                new Document("$sort", new Document("descripcion" , 1))
        		)).allowDiskUse(true).into(new ArrayList<RegionSegmentosSucursales>());
	}

	/**
	 * Find all and exclude fields olp.
	 *
	 * @param fieldsExclude the fields exclude
	 * @return the list
	 */
	public List<Region> findAllAndExcludeFieldsOlp(List<String> fieldsExclude) {

		return coleccionRegionesOLP.find().projection(exclude(fieldsExclude))
				.sort(Sorts.descending(ID)).into(new ArrayList<Region>());
	}

	/**
	 * Insert olp.
	 *
	 * @param region the region
	 * @return the insert one result
	 */
	public InsertOneResult insertOlp(Region region) {
		return coleccionRegionesOLP.insertOne(region);

	}

	/**
	 * Find by descripcion olp.
	 *
	 * @param descripcion the descripcion
	 * @return the region
	 */
	public Region findByDescripcionOlp(String descripcion) {
		return coleccionRegionesOLP.find(eq(DESCRIPCION, descripcion.trim())).first();
	}

	/**
	 * Find by id olp.
	 *
	 * @param id the id
	 * @return the region
	 */
	public Region findByIdOlp(String id) {
		return coleccionRegionesOLP.find(eq(ID, new ObjectId(id))).first();
	}

	/**
	 * Update descripcion olp.
	 *
	 * @param id the id
	 * @param descripcion the descripcion
	 * @return the update result
	 */
	public UpdateResult updateDescripcionOlp(String id, String descripcion) {
		return coleccionRegionesOLP.updateMany(eq(ID, new ObjectId(id)), 
				set(DESCRIPCION, descripcion));
	}

	/**
	 * Update estatus olp.
	 *
	 * @param id the id
	 * @param activo the activo
	 * @return the update result
	 */
	public UpdateResult updateEstatusOlp(String id, boolean activo) {

		return coleccionRegionesOLP.updateOne(eq(ID, new ObjectId(id)), set(ACTIVO, activo));
	}

	/**
	 * Delete olp.
	 *
	 * @param id the id
	 * @return the delete result
	 */
	public DeleteResult deleteOlp(String id) {
		return coleccionRegionesOLP.deleteMany(eq(ID, new ObjectId(id)));
		
	}

	/**
	 * Update estatus divisa region olp.
	 *
	 * @param idRegion the id region
	 * @param idDivisa the id divisa
	 * @param activo the activo
	 * @return true, if successful
	 */
	public boolean updateEstatusDivisaRegionOlp(String idRegion, String idDivisa, boolean activo) {

		List<Bson> arrFilters = new ArrayList<>();
		arrFilters.add(new Document("div._id", idDivisa));
		FindOneAndUpdateOptions updateOptions = new FindOneAndUpdateOptions().arrayFilters(arrFilters);

		Region region = coleccionRegionesOLP.findOneAndUpdate(eq(ID, new ObjectId(idRegion)), 
															 set("divisas.$[div].activo", activo),
															 updateOptions);

		return region != null;
	}

	public UpdateResult updateSegmentosOlp(String id, List<ObjectId> segmentos) {

		return coleccionRegionesOLP.updateOne(eq(ID, new ObjectId(id)), set("segmentos", segmentos));
		
	}

	/**
	 * Update sucursales olp.
	 *
	 * @param id the id
	 * @param sucursales the sucursales
	 * @return the update result
	 */
	public UpdateResult updateSucursalesOlp(String id, List<String> sucursales) {

		return coleccionRegionesOLP.updateOne(eq(ID, new ObjectId(id)), set("sucursales", sucursales));
		
	}

	public Region findByRegionSegmentoOlp(String idRegion, String idSegmento) {

		return coleccionRegionesOLP.find(and(eq("_id", new ObjectId(idRegion)), 
										  eq(ACTIVO, true),
									      eq("segmentos", new ObjectId(idSegmento)))).first();
	}

}
