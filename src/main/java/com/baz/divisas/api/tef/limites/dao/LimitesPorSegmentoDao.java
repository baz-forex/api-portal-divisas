package com.baz.divisas.api.tef.limites.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.limites.dto.LimitesPorSegmentoDto;
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
 * Clase LimitesPorRegionSegmentoDao.
 *
 * @author 164945 ogatica
 */
@Repository
public class LimitesPorSegmentoDao {

	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The terminal financiero BD. */
	private MongoDatabase terminalFinancieroBD;
	
	/** The coleccion limites. */
	private MongoCollection<LimitesPorSegmentoDto> coleccionLimites;
	
	/** The coleccion limites olp. */
	private MongoCollection<LimitesPorSegmentoDto> coleccionLimitesOlp;
	
	/** The Constant ID. */
	private static final String ID = "_id";
	
	/** The Constant LIMITESACTIVO. */
	private static final String LIMITESACTIVO = "divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.activo";
	
	/** The Constant LIMITEDIARIO. */
	private static final String LIMITEDIARIO = "divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.diario";
	
	/** The Constant LIMITEMENSUAL. */
	private static final String LIMITEMENSUAL = "divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.mensual";
	
	/** The Constant LIMITEIDALTA. */
	private static final String LIMITEIDALTA = "divisas.$[divelem].tiposOperacion.$[tiposOperacionElem].limites.idBitacoraAlta";
	
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
	public LimitesPorSegmentoDao() {
		super();
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionLimites = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccion(), LimitesPorSegmentoDto.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionLimitesOlp = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccionOlp(), LimitesPorSegmentoDto.class)
				.withCodecRegistry(pojoCodecRegistry);
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 * @param coleccionLimitesOlp the coleccion limites olp
	 */
	public LimitesPorSegmentoDao(MongoCollection<LimitesPorSegmentoDto> coleccion,MongoCollection<LimitesPorSegmentoDto> coleccionLimitesOlp) {
		super();
		this.coleccionLimites = coleccion;
		this.coleccionLimitesOlp = coleccionLimitesOlp;
	}

	/**
	 * Metodo de consulta de limites por regionSegmento.
	 *
	 * @return - Consulta de limites por regionSegmento
	 */
	public List<LimitesPorSegmentoDto> findLimitesSegmento() {
		
		List<String> project = new ArrayList<>();

		project.add(ID);
		project.add(CODIGODIVISA);
		project.add(IDTIPOOPERACION);
		project.add(ACTIVO);
		project.add(MENSUAL);
		project.add(DIARIO);
		project.add(IDBITACORAALTA);
		project.add(IDBITACORAACTUALIZACION);

		return  coleccionLimites
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
				.into(new ArrayList<LimitesPorSegmentoDto>());
	}

	/**
	 * Metodo de consulta de limites por id de Segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @param activo - Activo
	 * @return - Registros de limitesRegionSegmento por id
	 */
	public List<LimitesPorSegmentoDto> findLimitesSegmentoId(LimitesPorSegmentoDto request, boolean activo) {

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
		
		return coleccionLimites
				.aggregate(Arrays.asList(match(eq(ID, new ObjectId(request.getIdSegmento()))),
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
				.allowDiskUse(true).into(new ArrayList<LimitesPorSegmentoDto>());


	}

	/**
	 * Metodo de registro de limites por Segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return InsertOneResult
	 */
	public UpdateResult insertLimitesSegmento(LimitesPorSegmentoDto request) {
		Document diario = new Document();
		Document mensual = new Document();

		diario.append(CLIENTE, request.getDiario().getCliente());
		diario.append(USUARIO, request.getDiario().getUsuario());
		mensual.append(CLIENTE, request.getMensual().getCliente());
		mensual.append(USUARIO, request.getMensual().getUsuario());

		return coleccionLimites.updateOne(eq(ID, new ObjectId(request.getIdSegmento())),
				combine(set(LIMITESACTIVO, true), set(LIMITEDIARIO, diario), set(LIMITEMENSUAL, mensual),
						set(LIMITEIDALTA, request.getIdBitacoraAlta())),
				new UpdateOptions().arrayFilters(Arrays.asList(eq(CODIGODIVISAELE, request.getCodigoDivisa()),
						eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));

	}

	/**
	 * Metodo de actualización de limites del segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return - UpdateResult
	 */
	public UpdateResult updateLimitesSegmento(LimitesPorSegmentoDto request) {

		Document diario = new Document();
		Document mensual = new Document();

		diario.append(CLIENTE, request.getDiario().getCliente());
		diario.append(USUARIO, request.getDiario().getUsuario());
		mensual.append(CLIENTE, request.getMensual().getCliente());
		mensual.append(USUARIO, request.getMensual().getUsuario());

		return coleccionLimites.updateOne(eq(ID, new ObjectId(request.getIdSegmento())),
				combine(set(LIMITEDIARIO, diario),
						set(LIMITEMENSUAL, mensual),
						set(BITACORAACTUALIZACION,
								request.getIdBitacoraActualizacion())),
				new UpdateOptions().arrayFilters(Arrays.asList(eq(CODIGODIVISAELE, request.getCodigoDivisa()),
						eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));

	}

	/**
	 * Metodo de actualización de estatus de la regionSegmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatusLimitesSegmento(LimitesPorSegmentoDto request) {

		return coleccionLimites.updateOne(eq(ID, new ObjectId(request.getIdSegmento())),
				combine(set(LIMITESACTIVO, request.isActivo()),
						set(BITACORAACTUALIZACION, request.getIdBitacoraActualizacion())),
				new UpdateOptions().arrayFilters(Arrays.asList(eq(CODIGODIVISAELE, request.getCodigoDivisa()),
						eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));

	}

	/**
	 * Find limites segmento olp.
	 *
	 * @return the list
	 */
	public List<LimitesPorSegmentoDto> findLimitesSegmentoOlp() {
		List<String> project = new ArrayList<>();

		project.add(ID);
		project.add(CODIGODIVISA);
		project.add(IDTIPOOPERACION);
		project.add(ACTIVO);
		project.add(MENSUAL);
		project.add(DIARIO);
		project.add(IDBITACORAALTA);
		project.add(IDBITACORAACTUALIZACION);

		return  coleccionLimitesOlp
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
				.into(new ArrayList<LimitesPorSegmentoDto>());
	}

	/**
	 * Find limites segmento id olp.
	 *
	 * @param request the request
	 * @param activo the activo
	 * @return the list
	 */
	public List<LimitesPorSegmentoDto> findLimitesSegmentoIdOlp(LimitesPorSegmentoDto request, boolean activo) {

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
		
		return coleccionLimitesOlp
				.aggregate(Arrays.asList(match(eq(ID, new ObjectId(request.getIdSegmento()))),
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
				.allowDiskUse(true).into(new ArrayList<LimitesPorSegmentoDto>());


	}

	/**
	 * Insert limites segmento olp.
	 *
	 * @param request the request
	 * @return the update result
	 */
	public UpdateResult insertLimitesSegmentoOlp(LimitesPorSegmentoDto request) {
		Document diario = new Document();
		Document mensual = new Document();

		diario.append(CLIENTE, request.getDiario().getCliente());
		diario.append(USUARIO, request.getDiario().getUsuario());
		mensual.append(CLIENTE, request.getMensual().getCliente());
		mensual.append(USUARIO, request.getMensual().getUsuario());

		return coleccionLimitesOlp.updateOne(eq(ID, new ObjectId(request.getIdSegmento())),
				combine(set(LIMITESACTIVO, true), set(LIMITEDIARIO, diario), set(LIMITEMENSUAL, mensual),
						set(LIMITEIDALTA, request.getIdBitacoraAlta())),
				new UpdateOptions().arrayFilters(Arrays.asList(eq(CODIGODIVISAELE, request.getCodigoDivisa()),
						eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));
	}

	/**
	 * Update limites segmento olp.
	 *
	 * @param request the request
	 * @return the update result
	 */
	public UpdateResult updateLimitesSegmentoOlp(LimitesPorSegmentoDto request) {

		Document diario = new Document();
		Document mensual = new Document();

		diario.append(CLIENTE, request.getDiario().getCliente());
		diario.append(USUARIO, request.getDiario().getUsuario());
		mensual.append(CLIENTE, request.getMensual().getCliente());
		mensual.append(USUARIO, request.getMensual().getUsuario());

		return coleccionLimitesOlp.updateOne(eq(ID, new ObjectId(request.getIdSegmento())),
				combine(set(LIMITEDIARIO, diario),
						set(LIMITEMENSUAL, mensual),
						set(BITACORAACTUALIZACION,
								request.getIdBitacoraActualizacion())),
				new UpdateOptions().arrayFilters(Arrays.asList(eq(CODIGODIVISAELE, request.getCodigoDivisa()),
						eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));
	}

	/**
	 * Update estatus limites segmento olp.
	 *
	 * @param request the request
	 * @return the update result
	 */
	public UpdateResult updateEstatusLimitesSegmentoOlp(LimitesPorSegmentoDto request) {

		return coleccionLimitesOlp.updateOne(eq(ID, new ObjectId(request.getIdSegmento())),
				combine(set(LIMITESACTIVO, request.isActivo()),
						set(BITACORAACTUALIZACION, request.getIdBitacoraActualizacion())),
				new UpdateOptions().arrayFilters(Arrays.asList(eq(CODIGODIVISAELE, request.getCodigoDivisa()),
						eq(IDTIPOOPERACIONELE, request.getIdTipoOperacion()))).upsert(true));
		
	}

}
