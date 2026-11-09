/**
 * 
 */
package com.baz.divisas.api.tef.dashboard.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

/**
 * DashboardRegionesDAO
 *
 */
@Repository
public class DashboardRegionesDAO {
	
	private FactoryMongoDB factoryDB;
	private MongoDatabase terminalFinancieroBD;
	private MongoCollection<Document> coleccionRegiones;
	private MongoCollection<Document> coleccionSegmentos;


	private static final String ID = "_id";
	private static final String ACTIVO = "activo";
	private static final String DESCRIPCION = "descripcion";
	private static final String DIVISAS_ID = "divisas._id";



	/**
	 * Constructor de la clase
	 */
	public DashboardRegionesDAO() {

		super();

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionRegiones = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccion());
		coleccionSegmentos = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccion());

	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 * @param promoEspacioImpl - Servicio Promoespacio
	 */
	public DashboardRegionesDAO(MongoCollection<Document> coleccion) {
		super();
		this.coleccionRegiones = coleccion;
		this.coleccionSegmentos = coleccion;
		
	}

	
	/**
	 * Metodo de consulta de Regiones
	 *
	 * @param idDivisa - Id de la divisa
	 * @return Regiones
	 */
	public List<Document> findRegiones(String idDivisa){

		return coleccionRegiones.aggregate(Arrays.asList(
				match(eq(ACTIVO, true)),
				unwind("$divisas"),
				match(and(eq(DIVISAS_ID, idDivisa), eq("divisas.activo", true), eq("divisas.tiposOperacion.activo", true))),
				project(fields(include("segmentos", DESCRIPCION, "idDivisa", DIVISAS_ID),
							   computed("regiones",
									   new Document("$filter",
											   new Document("input","$divisas.tiposOperacion")
												 .append("cond", new Document("$in", Arrays.asList("$$operacion.idTipoOperacion", 
							 							 							 Arrays.asList("410", "411"))))
													.append("as", "operacion")
											   	   )
									   )							   
				)))).allowDiskUse(true).into(new ArrayList<Document>());
	}

	/**
	 *  Metodo de consulta de Segmentos
	 *
	 * @param segmentos - Ids de Segmentos
	 * @param idDivisa - Id de la divisa
	 * @return Segmentos
	 */
	public List<Document> findSegmentos(List<ObjectId> segmentos, String idDivisa) {

		return coleccionSegmentos.aggregate(Arrays.asList(
				match(and(in("_id" , segmentos), eq(ACTIVO, true))),
				unwind("$divisas"),
				match(and(eq(DIVISAS_ID, idDivisa), eq("divisas.activo", true), eq("divisas.tiposOperacion.activo", true))),
				project(fields(include(DESCRIPCION, "idDivisa", DIVISAS_ID),
							   computed("segmentos", 
										new Document("$filter", 
												new Document("input", "$divisas.tiposOperacion")
													 .append("cond", new Document("$in", Arrays.asList("$$operacion.idTipoOperacion", 
															 							 Arrays.asList("410", "411"))))
													 .append("as", "operacion")
													)
										)
				)),
				sort(Sorts.descending(ID))
				)).allowDiskUse(true).into(new ArrayList<Document>());
	}

}
