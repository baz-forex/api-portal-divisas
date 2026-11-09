package com.baz.divisas.api.siex.configuraciones.dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.addToSet;
import static com.mongodb.client.model.Updates.pull;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.siex.configuraciones.model.CampoRestringido;
import com.baz.divisas.general.config.PropBDSiex;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;


/**
 * Clase ConfiguracionesDao
 * 
 * @author B813196
 * itoxtle
 *
 */
@Repository("ConfiguracionesSiexDao")
public class ConfiguracionesDao {
	
	private FactoryMongoDB factoryDB;
	private MongoDatabase siexBD;
	private MongoCollection<CampoRestringido> catCamposRestringidos;

	private CodecRegistry pojoCodecRegistry;
	
	private static final String PALABRAS = "palabras";
	private static final String ACTIVO = "activo";
	private static final String DESCRIPCION = "descripcion";
	
	/**
	 * Constructor de la clase
	 */
	public ConfiguracionesDao() {
		super();		
		pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
							CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		factoryDB = FactoryMongoDB.getInstance();
		
		siexBD = factoryDB.getMongoClient().getDatabase(PropBDSiex.getSiexBD());
		catCamposRestringidos =  siexBD.getCollection(PropBDSiex.getCatCamposRestringidos(),
											CampoRestringido.class).withCodecRegistry(pojoCodecRegistry);		
	}	
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public ConfiguracionesDao(MongoCollection<CampoRestringido> coleccion) {
		super();
		this.catCamposRestringidos = coleccion;
	}

	/**
	 * Metodo de consulta de Campos Restringidos
	 * @return Lista de campos restringidos
	 */
	public List<CampoRestringido> findCamposRestringidos(){
				
		return catCamposRestringidos.find(eq(ACTIVO, true))
								   .projection(include(DESCRIPCION))
								   .into(new ArrayList<CampoRestringido>());	
	}
	
	/**
	 * Metodo de consulta de Configuracion de Campos Restringidos
	 * @return Lista de campos restringidos
	 */
	public List<CampoRestringido> findConfigCamposRestringidos(){
				
		return catCamposRestringidos.find(eq(ACTIVO, true))
								   .into(new ArrayList<CampoRestringido>());	
	}

	/**
	 * Metodo de consulta de Campo Restringido por Id
	 * @param idCampo - Identificador de campo
	 * @param idRegla - Identificador de regla
	 * @return Campos restringido
	 */
	public CampoRestringido findCamposRestringidoById(String idCampo, String idRegla){
		
		return catCamposRestringidos.find(and(eq("_id", idCampo), 
										      eq(ACTIVO, true), 
											  eq(idRegla +"."+ ACTIVO, true)))
									.projection(fields( include(DESCRIPCION),
											    		computed("restricciones", "$"+idRegla +".palabras"))).first();		
	}
	
	/**
	 * Metodo para agregar una cadena restringida al campo
	 * @param idCampo - Identificador de campo
	 * @param idRegla - Identificador de regla
	 * @param restriccion - Cadena restringida
	 * @return Campos restringido
	 */
	public UpdateResult addRestriccionCampo(String idCampo, String idRegla, String restriccion){
				
		return catCamposRestringidos.updateOne(eq("_id", idCampo), 
											  addToSet(idRegla +"."+ PALABRAS, restriccion));

	}
	
	
	/**
	 * Metodo para agregar una cadena restringida al campo
	 * @param idCampo - Identificador de campo
	 * @param idRegla - Identificador de regla
	 * @param restriccion - Cadena restringida
	 * @return Campos restringido
	 */
	public UpdateResult removeRestriccionCampo(String idCampo, String idRegla, String restriccion){
				
		return catCamposRestringidos.updateOne(eq("_id", idCampo), 
											  pull(idRegla +"."+ PALABRAS, restriccion));

	}


}
