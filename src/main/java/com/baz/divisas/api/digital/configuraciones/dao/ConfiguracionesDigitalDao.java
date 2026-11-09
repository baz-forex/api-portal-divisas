package com.baz.divisas.api.digital.configuraciones.dao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.springframework.stereotype.Repository;

/**
 * Clase ConfiguracionesDao
 * 
 * @author 1027346
 * ymartinezb
 *
 */
@Repository
public class ConfiguracionesDigitalDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase middlewareBD;
	private MongoCollection<Document> collectionParametriaMW;

	private static final String STATUS = "status";
	private static final String TIPO_OPERACION = "tipo_operacion";

	/**
	 * Constructor de la clase
	 */
	public ConfiguracionesDigitalDao() {
		super();

		factoryDB = FactoryMongoDB.getInstance();

		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		collectionParametriaMW = middlewareBD.getCollection(PropBD.getParametriaCotizacionesMW());
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public ConfiguracionesDigitalDao(MongoCollection<Document> coleccion) {
		super();
		collectionParametriaMW = coleccion;
	}

	/**
	 * Metodo de consulta de Parametria
	 *
	 * @param idParametria - Id de Parametria
	 * @param tipoOperacion - Tipo de Operacion
	 * 
	 * @return - Parametria
	 */
	public Document findParametria(String idParametria, String tipoOperacion) {

		Document doc = collectionParametriaMW.find(and(eq(STATUS, "1"), eq(TIPO_OPERACION, tipoOperacion))).first();

		if (doc != null && doc.get(idParametria) != null) {
			return (Document) doc.get(idParametria);
		}

		return null;
	}

	/**
	 * Metodo de update Parametria
	 *
	 * @param idParametria - Id de Parametria
	 * @param tipoOperacion - Tipo de Operacion
	 * @param parametria - Parametria
	 * 
	 * @return - Resultado
	 */
	public Document updateParametria(String idParametria, String tipoOperacion, Document parametria) {

		return collectionParametriaMW.findOneAndUpdate(and(eq(STATUS, "1"), eq(TIPO_OPERACION, tipoOperacion)), 
							 						   set(idParametria, parametria));

	}

}
