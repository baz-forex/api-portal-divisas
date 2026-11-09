package com.baz.divisas.api.tef.regionesestrategias.dao;

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

import com.baz.divisas.api.tef.regionesestrategias.dto.RequestEstrategia;
import com.baz.divisas.api.tef.regionesestrategias.dto.ResponseEstrategia;
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
 */
@Repository
public class RegionesEstrategiasDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase terminalFinancieroBD;
	private MongoCollection<ResponseEstrategia> coleccionRegiones;

	private static final String ID = "_id";
	private static final String ACTIVO = "activo";
	private static final String DIVISA = "divisa";
	private static final String TIPOOPERACION = "tipoOperacion";


	/**
	 * Constructor de la clase
	 */
	public  RegionesEstrategiasDao() {

		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())); 
		
		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionRegiones = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccion(),ResponseEstrategia.class)
				.withCodecRegistry(pojoCodecRegistry);
	}

	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public RegionesEstrategiasDao(MongoCollection<ResponseEstrategia> coleccion) {
		super();
		this.coleccionRegiones = coleccion;
	}
	
	/**
	 * Metodo para obtener las estrategias de las regiones
	 * 
	 * @return Estrategias de la Region
	 */
	public List<ResponseEstrategia> findAll() {

		Document project = new Document();

		project.append(DIVISA,"$divisas._id")
			   .append(TIPOOPERACION, "$divisas.tiposOperacion.idTipoOperacion")
			   .append("descripcion", "$descripcion")
			   .append("descripcionOperacion","$divisas.tiposOperacion.descripcion")
			   .append("estrategia","$divisas.tiposOperacion.estrategia");


		return coleccionRegiones.aggregate(Arrays.asList(
				match(eq(ACTIVO, true)),
				unwind("$divisas"),
				unwind("$divisas.tiposOperacion"),
				project(project)
				)).allowDiskUse(true).into(new ArrayList<ResponseEstrategia>());

	}

	/**
	 * Metodo de consulta de estrategia por tipo de operacion y divisa en la region
	 *
	 * @param request - RequestEstrategia
	 * @return Estrategias de la region
	 */
	public List<ResponseEstrategia> findByTipo(RequestEstrategia request) {

		Document project = new Document();

		project.append(DIVISA, "$divisas._id")
			   .append(TIPOOPERACION, "$divisas.tiposOperacion.idTipoOperacion")
			   .append("descripcionOperacion", "$divisas.tiposOperacion.descripcion")
			   .append("estrategia", "$divisas.tiposOperacion.estrategia");

		return coleccionRegiones.aggregate(Arrays.asList(
				match(and(eq("_id", new ObjectId(request.getIdRegion())), eq(ACTIVO, true) )),
				unwind("$divisas"),
				unwind("$divisas.tiposOperacion"),
				project(project),
				match(and(eq(TIPOOPERACION, request.getTipoOperacion()), eq(DIVISA, request.getIdDivisa())))
				)).allowDiskUse(true).into(new ArrayList<ResponseEstrategia>());

	}

	/**
	 * Update estrategia de la region.
	 *
	 * @param request - RequestEstrategia
	 * @param bitacora - bitacora
	 * @return UpdateResult
	 */
	public UpdateResult updateEstrategiaRegion(RequestEstrategia request, String bitacora) {

		return coleccionRegiones.updateOne(
					and(eq(ID,new ObjectId(request.getIdRegion())),
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
	 * Update estatus de la estrategia en la region.
	 *
	 * @param request - RequestEstrategia
	 * @param bitacora - bitacora
	 * @return UpdateResult
	 */
	public UpdateResult updateEstatusEstrategiaRegion(RequestEstrategia request, String bitacora) {

		return coleccionRegiones.updateOne(
				and(eq(ID,new ObjectId(request.getIdRegion())),
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
