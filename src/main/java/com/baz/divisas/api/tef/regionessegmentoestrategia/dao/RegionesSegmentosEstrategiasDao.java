package com.baz.divisas.api.tef.regionessegmentoestrategia.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
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

import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.RequestSegmentoEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.ResponseSegmentoEstrategia;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase RegionesEstrategiasDao
 * 
 *
 */
@Repository
public class RegionesSegmentosEstrategiasDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase terminalFinancieroBD;
	private MongoCollection<ResponseSegmentoEstrategia> coleccionSegmeto;

	private static final String ACTIVO = "activo";
	private static final String ASIGNADO = "asignado";
	private static final String DIVISA = "divisa";
	private static final String TIPOOPERACION = "tipoOperacion";

	/**
	 * Constructor de la clase
	 */
	public RegionesSegmentosEstrategiasDao() {

		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionSegmeto = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccion(),ResponseSegmentoEstrategia.class)
				.withCodecRegistry(pojoCodecRegistry);
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public RegionesSegmentosEstrategiasDao(MongoCollection<ResponseSegmentoEstrategia> coleccion) {
		super();
		this.coleccionSegmeto = coleccion;
	}

	/**
	 * Metodo para obtener las estrategias de los segmentos
	 * 
	 * @return Estrategias del segmento
	 */
	public List<ResponseSegmentoEstrategia> findAll() {

		Document project = new Document();

		project.append("idRegion", "$region._id")
		.append("regionDescripcion" , "$region.descripcion")
		.append("asignacion", "$asignado")
		.append("descripcion", "$descripcion")
		.append(DIVISA, "$divisas._id")
		.append(TIPOOPERACION, "$divisas.tiposOperacion.idTipoOperacion")
		.append("descripcionOperacion", "$divisas.tiposOperacion.descripcion")
		.append("estrategia", "$divisas.tiposOperacion.estrategia");

		return coleccionSegmeto.aggregate(Arrays.asList(
				match(and(eq(ACTIVO, true), eq(ASIGNADO, true))),
				unwind("$divisas"),
				match(eq("divisas.activo", true)),
				unwind("$divisas.tiposOperacion"),
				match(eq("divisas.tiposOperacion.activo", true)),
				lookup("catRegiones", "_id", "segmentos", "region"),
				unwind("$region"),
				project(project))).allowDiskUse(true).into(new ArrayList<ResponseSegmentoEstrategia>());
	}

	/**
	 * Metodo de consulta de estrategia por tipo de operacion y divisa en el segmento
	 *
	 * @param request - RequestSegmentoEstrategia
	 * @return Estrategias del segmento
	 */
	public List<ResponseSegmentoEstrategia> findByTipo(RequestSegmentoEstrategia request) {

		Document project = new Document();

		project.append("asignacion", "$asignado")
		.append(DIVISA, "$divisas._id")
		.append(TIPOOPERACION, "$divisas.tiposOperacion.idTipoOperacion")
		.append("descripcionOperacion", "$divisas.tiposOperacion.descripcion")
		.append("estrategia", "$divisas.tiposOperacion.estrategia");

		return coleccionSegmeto.aggregate(Arrays.asList(
				match(and(eq("_id", new ObjectId(request.getIdSegmento())), eq(ACTIVO, true), eq(ASIGNADO, true))),
				unwind("$divisas"),
				match(eq("divisas.activo", true)),
				unwind("$divisas.tiposOperacion"),
				match(eq("divisas.tiposOperacion.activo", true)),
				project(project),
				match(and(eq(TIPOOPERACION, request.getTipoOperacion()), eq(DIVISA, request.getIdDivisa()) ))
				)).allowDiskUse(true).into(new ArrayList<ResponseSegmentoEstrategia>());

	}

	/**
	 * Update estrategia del segmento
	 *
	 * @param request - RequestSegmentoEstrategia
	 * @param bitacora - bitacora
	 * @return UpdateResult
	 */
	public UpdateResult updateEstrategiaSegmento(RequestSegmentoEstrategia request , String bitacora) {

		return coleccionSegmeto.updateOne(
					and(eq("_id",new ObjectId(request.getIdSegmento())),
						eq(ACTIVO,true),
						eq("divisas._id",request.getIdDivisa()),
						eq("divisas.tiposOperacion.idTipoOperacion" , request.getTipoOperacion())),

				combine(set("divisas.$[divisa].tiposOperacion.$[tiposOperaciones].estrategia.activo",true),
						set("divisas.$[divisa].tiposOperacion.$[tiposOperaciones].estrategia.monto",request.getMonto()),
						set("divisas.$[divisa].tiposOperacion.$[tiposOperaciones].estrategia.idBitacoraActualizacion",bitacora)
						)
				,
				new UpdateOptions().arrayFilters(Arrays.asList(
						eq("divisa._id",request.getIdDivisa()),eq("tiposOperaciones.idTipoOperacion",request.getTipoOperacion())))
				);   
	}

	/**
	 * Update estatus de la estrategia en el segmento
	 *
	 * @param request - RequestSegmentoEstrategia
	 * @param bitacora - bitacora
	 * @return UpdateResult
	 */
	public UpdateResult updateEstatusEstrategiaSegmento(RequestSegmentoEstrategia request , String bitacora) {

		return coleccionSegmeto.updateOne(
					and(eq("_id",new ObjectId(request.getIdSegmento())),
						eq(ACTIVO,true),
						eq("divisas._id",request.getIdDivisa()),
						eq("divisas.tiposOperacion.idTipoOperacion" , request.getTipoOperacion())),

				combine(set("divisas.$[divisa].tiposOperacion.$[tiposOperaciones].estrategia.activo",request.isActivoEstrategia()),
						set("divisas.$[divisa].tiposOperacion.$[tiposOperaciones].estrategia.idBitacoraActualizacion",bitacora)
						)
				,
				new UpdateOptions().arrayFilters(Arrays.asList(
						eq("divisa._id",request.getIdDivisa()),eq("tiposOperaciones.idTipoOperacion",request.getTipoOperacion())))
				);  

	}

}
