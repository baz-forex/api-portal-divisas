/**
 * 
 */
package com.baz.divisas.api.tef.spread.dao;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.spread.dto.Spread;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase SpreadsDao
 * @author 813196
 *
 */
@Repository
public class SpreadsDao {
	
	private FactoryMongoDB factoryDB;
	private MongoDatabase terminalFinancieroBD;
	private MongoCollection<Spread> coleccionCatSpreads;
	
	private static final String ID = "_id";
	
	/**
	 * Constructor de la clase 
	 * Usado para inyectar Mocks
	 * @param coleccion - Mock Collection
	 */
	public SpreadsDao(MongoCollection<Spread> coleccion) {
		super();
		this.coleccionCatSpreads = coleccion;
	}
	
	/**
	 * Constructor de la clase
	 */
	public SpreadsDao () {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionCatSpreads = terminalFinancieroBD.getCollection(PropBD.getCatSpreads(), Spread.class)
				.withCodecRegistry(pojoCodecRegistry);
	}
	
	/**
	 * Metodo de consulta de Spreads
	 * 
	 * @param
	 * @return - Consulta de TipoCambioSpread
	 */
	public List<Spread> findSpreads() {

		return coleccionCatSpreads.find().sort(Sorts.descending(ID)).into(new ArrayList<Spread>());

	}
	
	/**
	 * Metodo de consulta de Spreads por Divisa
	 * 
	 * @param idDivisa - Id Divisa
	 * @return - Spread
	 */
	public Spread findSpreadDivisa(String idDivisa) {
		
		return coleccionCatSpreads.find(eq(ID, idDivisa)).first();

	}
	
	/**
	 * Metodo de actualizacion de Spread por Divisa
	 * @param spread - Spread
	 * @return UpdateResult
	 */
	public UpdateResult updateSpreadDivisa(Spread spread) {
		
		return coleccionCatSpreads.replaceOne(eq(ID, spread.getIdDivisa()), spread);
	}


}
