package com.baz.divisas.api.tef.limites.dao;

import static com.mongodb.client.model.Aggregates.addFields;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase LimitesPorRegionDao.
 *
 * @author 164945 ogatica
 */
@Repository
public class LimitesPorRegionDao {

	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The terminal financiero BD. */
	private MongoDatabase terminalFinancieroBD;
	
	/** The coleccion regiones. */
	private MongoCollection<LimitesPorRegionDto> coleccionRegiones;
	
	/** The coleccion regiones OLP. */
	private MongoCollection<LimitesPorRegionDto> coleccionRegionesOLP;

	/** The Constant ID. */
	private static final String ID = "_id";
	
	/** The Constant LIMITESACTIVO. */
	private static final String LIMITESACTIVO = "divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.activo";
	
	/** The Constant LIMITEDIARIO. */
	private static final String LIMITEDIARIO = "divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.diario";
	
	/** The Constant LIMITEMENSUAL. */
	private static final String LIMITEMENSUAL = "divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.mensual";
	
	/** The Constant LIMITEIDALTA. */
	private static final String LIMITEIDALTA = 	"divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.idBitacoraAlta";
	
	/** The Constant BITACORAACTUALIZACION. */
	private static final String BITACORAACTUALIZACION = "divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.idBitacoraActualizacion";
	
	/** The Constant CODIGODIVISAELE. */
	private static final String CODIGODIVISAELE = "divelem._id";
	
	/** The Constant IDTIPOOPERACIONELE. */
	private static final String IDTIPOOPERACIONELE = "tiposOperacionElem.idTipoOperacion";
	
	/** The Constant IDBITACORAALTAELE. */
	private static final String IDBITACORAALTAELE = "$divisas.tiposOperacion.limites.idBitacoraAlta";
	
	/** The Constant CODIGODIVISA. */
	private static final String CODIGODIVISA = "codigoDivisa";
	
	/** The Constant CODIGODIVISAACTIVO. */
	private static final String CODIGODIVISAACTIVO = "codigoDivisaActivo";
	
	/** The Constant IDTIPOOPERACION. */
	private static final String IDTIPOOPERACION = "idTipoOperacion";
	
	/** The Constant IDTIPOOPERACIONACTIVO. */
	private static final String IDTIPOOPERACIONACTIVO = "idTipoOperacionActivo";
	
	/** The Constant ACTIVO. */
	private static final String ACTIVO = "activo";
	
	/** The Constant MENSUAL. */
	private static final String MENSUAL = "mensual";
	
	/** The Constant DIARIO. */
	private static final String DIARIO = "diario";
	
	/** The Constant IDBITACORAALTA. */
	private static final String IDBITACORAALTA = "idBitacoraAlta";
	
	/** The Constant IDBITACORAACTUALIZACION. */
	private static final String IDBITACORAACTUALIZACION = "idBitacoraActualizacion";
	
	/** The Constant CLIENTE. */
	private static final String CLIENTE = "cliente";
	
	/** The Constant USUARIO. */
	private static final String USUARIO = "usuario";


	/**
	 * Constructor de la clase.
	 */
	public LimitesPorRegionDao() {
		super();
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionRegiones = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccion(), LimitesPorRegionDto.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionRegionesOLP = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccionOlp(), LimitesPorRegionDto.class)
				.withCodecRegistry(pojoCodecRegistry);
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 * @param coleccionRegionesOLP the coleccion regiones OLP
	 */
	public LimitesPorRegionDao(MongoCollection<LimitesPorRegionDto> coleccion,MongoCollection<LimitesPorRegionDto> coleccionRegionesOLP) {
		super();
		this.coleccionRegiones = coleccion;
		this.coleccionRegionesOLP = coleccionRegionesOLP;
	}

	/**
	 * Metodo de consulta de limites por region.
	 *
	 * @return - Consulta de limites por region
	 */
	public List<LimitesPorRegionDto> findLimitesRegion() {

		List<String> project = new ArrayList<>();

		project.add(ID);
		project.add(CODIGODIVISA);
		project.add(IDTIPOOPERACION);
		project.add(ACTIVO);
		project.add(MENSUAL);
		project.add(DIARIO);
		project.add(IDBITACORAALTA);
		project.add(IDBITACORAACTUALIZACION);

		return  coleccionRegiones
				.aggregate(Arrays.asList(
						match(eq(ID, new Document("$exists", true))),
						unwind("$divisas"),
						unwind("$divisas.tiposOperacion"),
						addFields(
								
								new Field<>(CODIGODIVISA, "$divisas._id"),
								new Field<>(IDTIPOOPERACION, "$divisas.tiposOperacion.idTipoOperacion"),
								new Field<>(ACTIVO, "$divisas.tiposOperacion.limites.activo"),
								new Field<>(MENSUAL, "$divisas.tiposOperacion.limites.mensual"),
								new Field<>(DIARIO, "$divisas.tiposOperacion.limites.diario"),
								new Field<>(IDBITACORAALTA, IDBITACORAALTAELE),
								new Field<>(IDBITACORAACTUALIZACION,
										"$divisas.tiposOperacion.limites.idBitacoraActualizacion")),
						project(fields(include(project))), 
						match(eq(ID, new Document("$exists", true))),
						sort(Sorts.descending(IDBITACORAALTA)
								)))
				.allowDiskUse(true)
				.into(new ArrayList<LimitesPorRegionDto>());

	}

	/**
	 * Metodo de consulta de limitesRegion por req.
	 *
	 * @param request - LimitesPorRegionDto
	 * @param activo the activo
	 * @return - Registros de limitesRegion por req
	 */
	public List<LimitesPorRegionDto> findLimitesRegionId(LimitesPorRegionDto request, boolean activo) {
		
		List<String> project = new ArrayList<>();

		project.add(ID);
		project.add(CODIGODIVISA);
		project.add(CODIGODIVISAACTIVO);
		project.add(IDTIPOOPERACION);
		project.add(IDTIPOOPERACIONACTIVO);
		project.add(ACTIVO);
		project.add(MENSUAL);
		project.add(DIARIO);
		project.add(IDBITACORAALTA);
		project.add(IDBITACORAACTUALIZACION);
		
		return coleccionRegiones
				.aggregate(Arrays.asList(
						match(eq(ID, new ObjectId(request.getIdRegion()))),
						unwind("$divisas"),
						unwind("$divisas.tiposOperacion"),
						addFields(
								new Field<>(CODIGODIVISA, "$divisas._id"),
								new Field<>(CODIGODIVISAACTIVO, "$divisas.activo"),
								new Field<>(IDTIPOOPERACION, "$divisas.tiposOperacion.idTipoOperacion"),
								new Field<>(IDTIPOOPERACIONACTIVO, "$divisas.tiposOperacion.activo"),
								new Field<>(ACTIVO, "$divisas.tiposOperacion.limites.activo"),
								new Field<>(MENSUAL, "$divisas.tiposOperacion.limites.mensual"),
								new Field<>(DIARIO, "$divisas.tiposOperacion.limites.diario"),
								new Field<>(IDBITACORAALTA, IDBITACORAALTAELE),
								new Field<>(IDBITACORAACTUALIZACION,
										"$divisas.tiposOperacion.limites.idBitacoraActualizacion")),
						project(fields(include(project))), 
						match(and(eq(CODIGODIVISA, request.getCodigoDivisa()),
								eq(CODIGODIVISAACTIVO, true),
								eq(IDTIPOOPERACION, request.getIdTipoOperacion()),
								eq(IDTIPOOPERACIONACTIVO, true),
								eq(ACTIVO, activo)
								)),
						sort(Sorts.descending(IDBITACORAALTA))))
				.allowDiskUse(true).into(new ArrayList<LimitesPorRegionDto>());

	}

	/**
	 * Metodo de registro de limites por region.
	 *
	 * @param request - LimitesPorRegionDto
	 * @return InsertOneResult
	 */
	public UpdateResult insertLimitesRegion(LimitesPorRegionDto request) {
		Document diario = new Document();
		Document mensual = new Document();

		diario.append(CLIENTE, request.getDiario().getCliente());
		diario.append(USUARIO, request.getDiario().getUsuario());
		mensual.append(CLIENTE, request.getMensual().getCliente());
		mensual.append(USUARIO, request.getMensual().getUsuario());

		return coleccionRegiones.updateOne(eq(ID, new ObjectId(request.getIdRegion())),
									  combine(set(LIMITESACTIVO, true), 
											  set(LIMITEDIARIO, diario), set(LIMITEMENSUAL, mensual),
											  set(LIMITEIDALTA, request.getIdBitacoraAlta())),
									new UpdateOptions().arrayFilters(Arrays.asList(
											eq(CODIGODIVISAELE, request.getCodigoDivisa()),
											eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));

	}
	
	/**
	 * Metodo de actualización de estatus del limite en la region.
	 *
	 * @param request - LimitesPorRegionDto
	 * @return - UpdateResult
	 */
	public UpdateResult updateLimitesRegion(LimitesPorRegionDto request) {

		Document diario = new Document();
		Document mensual = new Document();

		diario.append(CLIENTE, request.getDiario().getCliente());
		diario.append(USUARIO, request.getDiario().getUsuario());
		mensual.append(CLIENTE, request.getMensual().getCliente());
		mensual.append(USUARIO, request.getMensual().getUsuario());

		return coleccionRegiones.updateOne(eq(ID, new ObjectId(request.getIdRegion())),
									  combine(set(LIMITEDIARIO, diario),
										      set(LIMITEMENSUAL, mensual),
											  set(BITACORAACTUALIZACION, request.getIdBitacoraActualizacion())),
									new UpdateOptions().arrayFilters(Arrays.asList(
											eq(CODIGODIVISAELE, request.getCodigoDivisa()),
											eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));

	}

	/**
	 * Metodo de actualización de estatus de la region.
	 *
	 * @param request - LimitesPorRegionDto
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatusLimitesRegion(LimitesPorRegionDto request) {

		return coleccionRegiones.updateOne(eq(ID, new ObjectId(request.getIdRegion())),
								      combine(set(LIMITESACTIVO, request.isActivo()),
										  set(BITACORAACTUALIZACION, request.getIdBitacoraActualizacion())),
									new UpdateOptions().arrayFilters(Arrays.asList(
											eq(CODIGODIVISAELE, request.getCodigoDivisa()),
											eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));

	}

	/**
	 * Find limites region olp.
	 *
	 * @return the list
	 */
	public List<LimitesPorRegionDto> findLimitesRegionOlp() {
		List<String> project = new ArrayList<>();

		project.add(ID);
		project.add(CODIGODIVISA);
		project.add(IDTIPOOPERACION);
		project.add(ACTIVO);
		project.add(MENSUAL);
		project.add(DIARIO);
		project.add(IDBITACORAALTA);
		project.add(IDBITACORAACTUALIZACION);

		return  coleccionRegionesOLP
				.aggregate(Arrays.asList(
						match(eq(ID, new Document("$exists", true))),
						unwind("$divisas"),
						unwind("$divisas.tiposOperacion"),
						addFields(
								
								new Field<>(CODIGODIVISA, "$divisas._id"),
								new Field<>(IDTIPOOPERACION, "$divisas.tiposOperacion.idTipoOperacion"),
								new Field<>(ACTIVO, "$divisas.tiposOperacion.limites.activo"),
								new Field<>(MENSUAL, "$divisas.tiposOperacion.limites.mensual"),
								new Field<>(DIARIO, "$divisas.tiposOperacion.limites.diario"),
								new Field<>(IDBITACORAALTA, IDBITACORAALTAELE),
								new Field<>(IDBITACORAACTUALIZACION,
										"$divisas.tiposOperacion.limites.idBitacoraActualizacion")),
						project(fields(include(project))), 
						match(eq(ID, new Document("$exists", true))),
						sort(Sorts.descending(IDBITACORAALTA)
								)))
				.allowDiskUse(true)
				.into(new ArrayList<LimitesPorRegionDto>());
	}

	/**
	 * Find limites region id olp.
	 *
	 * @param request the request
	 * @param activo the activo
	 * @return the list
	 */
	public List<LimitesPorRegionDto> findLimitesRegionIdOlp(LimitesPorRegionDto request, boolean activo) {
		
		List<String> project = new ArrayList<>();

		project.add(ID);
		project.add(CODIGODIVISA);
		project.add(CODIGODIVISAACTIVO);
		project.add(IDTIPOOPERACION);
		project.add(IDTIPOOPERACIONACTIVO);
		project.add(ACTIVO);
		project.add(MENSUAL);
		project.add(DIARIO);
		project.add(IDBITACORAALTA);
		project.add(IDBITACORAACTUALIZACION);
		
		return coleccionRegionesOLP
				.aggregate(Arrays.asList(
						match(eq(ID, new ObjectId(request.getIdRegion()))),
						unwind("$divisas"),
						unwind("$divisas.tiposOperacion"),
						addFields(
								new Field<>(CODIGODIVISA, "$divisas._id"),
								new Field<>(CODIGODIVISAACTIVO, "$divisas.activo"),
								new Field<>(IDTIPOOPERACION, "$divisas.tiposOperacion.idTipoOperacion"),
								new Field<>(IDTIPOOPERACIONACTIVO, "$divisas.tiposOperacion.activo"),
								new Field<>(ACTIVO, "$divisas.tiposOperacion.limites.activo"),
								new Field<>(MENSUAL, "$divisas.tiposOperacion.limites.mensual"),
								new Field<>(DIARIO, "$divisas.tiposOperacion.limites.diario"),
								new Field<>(IDBITACORAALTA, IDBITACORAALTAELE),
								new Field<>(IDBITACORAACTUALIZACION,
										"$divisas.tiposOperacion.limites.idBitacoraActualizacion")),
						project(fields(include(project))), 
						match(and(eq(CODIGODIVISA, request.getCodigoDivisa()),
								eq(CODIGODIVISAACTIVO, true),
								eq(IDTIPOOPERACION, request.getIdTipoOperacion()),
								eq(IDTIPOOPERACIONACTIVO, true),
								eq(ACTIVO, activo)
								)),
						sort(Sorts.descending(IDBITACORAALTA))))
				.allowDiskUse(true).into(new ArrayList<LimitesPorRegionDto>());

	}

	/**
	 * Insert limites region olp.
	 *
	 * @param request the request
	 * @return the update result
	 */
	public UpdateResult insertLimitesRegionOlp(LimitesPorRegionDto request) {
		Document diario = new Document();
		Document mensual = new Document();

		diario.append(CLIENTE, request.getDiario().getCliente());
		diario.append(USUARIO, request.getDiario().getUsuario());
		mensual.append(CLIENTE, request.getMensual().getCliente());
		mensual.append(USUARIO, request.getMensual().getUsuario());

		return coleccionRegionesOLP.updateOne(eq(ID, new ObjectId(request.getIdRegion())),
									  combine(set(LIMITESACTIVO, true), 
											  set(LIMITEDIARIO, diario), set(LIMITEMENSUAL, mensual),
											  set(LIMITEIDALTA, request.getIdBitacoraAlta())),
									new UpdateOptions().arrayFilters(Arrays.asList(
											eq(CODIGODIVISAELE, request.getCodigoDivisa()),
											eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));

		
	}

	/**
	 * Update limites region olp.
	 *
	 * @param request the request
	 * @return the update result
	 */
	public UpdateResult updateLimitesRegionOlp(LimitesPorRegionDto request) {

		Document diario = new Document();
		Document mensual = new Document();

		diario.append(CLIENTE, request.getDiario().getCliente());
		diario.append(USUARIO, request.getDiario().getUsuario());
		mensual.append(CLIENTE, request.getMensual().getCliente());
		mensual.append(USUARIO, request.getMensual().getUsuario());

		return coleccionRegionesOLP.updateOne(eq(ID, new ObjectId(request.getIdRegion())),
									  combine(set(LIMITEDIARIO, diario),
										      set(LIMITEMENSUAL, mensual),
											  set(BITACORAACTUALIZACION, request.getIdBitacoraActualizacion())),
									new UpdateOptions().arrayFilters(Arrays.asList(
											eq(CODIGODIVISAELE, request.getCodigoDivisa()),
											eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));
	}

	/**
	 * Update estatus limites region olp.
	 *
	 * @param request the request
	 * @return the update result
	 */
	public UpdateResult updateEstatusLimitesRegionOlp(LimitesPorRegionDto request) {

		return coleccionRegionesOLP.updateOne(eq(ID, new ObjectId(request.getIdRegion())),
								      combine(set(LIMITESACTIVO, request.isActivo()),
										  set(BITACORAACTUALIZACION, request.getIdBitacoraActualizacion())),
									new UpdateOptions().arrayFilters(Arrays.asList(
											eq(CODIGODIVISAELE, request.getCodigoDivisa()),
											eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));

	}	

}
