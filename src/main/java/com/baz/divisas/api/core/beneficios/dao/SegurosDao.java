package com.baz.divisas.api.core.beneficios.dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.core.beneficios.model.Seguro;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

/**
 * SegurosDao
 */
@Repository
public class SegurosDao {

	private MongoCollection<Seguro> coleccionSeguros;

	private static final String ESTATUS = "estatus";
	private static final String PREMIOSSEGUROS = "premiosSeguros";


	/**
	 * Constructor de la clase
	 */
	public SegurosDao() {
		super();

		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		FactoryMongoDB factoryDB = FactoryMongoDB.getInstance();
		MongoDatabase middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		coleccionSeguros = middlewareBD.getCollection(PropBD.getSeguros(), Seguro.class).withCodecRegistry(pojoCodecRegistry);

	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public SegurosDao(MongoCollection<Seguro> coleccion) {
		super();
		coleccionSeguros = coleccion;
	}
	
	/**
	 * Consulta de Seguros
	 *
	 * @return Seguros
	 */
	public List<Seguro> find(){		
		return coleccionSeguros.find().projection(exclude(PREMIOSSEGUROS))
				.sort(ascending("_id")).into(new ArrayList<Seguro>());
	}
	
	/**
	 * Consulta de Seguros por Estatus.
	 *
	 * @param estatus - Estatus
	 * @return Seguros
	 */
	public List<Seguro> findByEstatus(String estatus){		
		return coleccionSeguros.find(eq(ESTATUS, estatus)).projection(exclude(PREMIOSSEGUROS))
				.sort(ascending("_id")).into(new ArrayList<Seguro>());
	}	
	
	/**
	 * Consulta de Seguros por Id.
	 *
	 * @param id - Id Seguro
	 * @return Seguro
	 */
	public Seguro findById(String id){		
		return coleccionSeguros.find(eq("_id", new ObjectId(id))).first();
	}
	
	/**
	 * Creacion de Seguro.
	 *
	 * @param seguro - Seguro a insertar
	 * @return Resultado
	 */
	public InsertOneResult insert(Seguro seguro){		
		return coleccionSeguros.insertOne(seguro);
	}
	
	/**
	 * Actualizacion de Seguro.
	 *
	 * @param seguro - Seguro
	 * @return Seguro antes de actualizacion
	 */
	public Seguro update(Seguro seguro){		
		return coleccionSeguros.findOneAndUpdate(eq("_id", new ObjectId(seguro.getId())),
				combine(set("nombre", seguro.getNombre()),
						set(ESTATUS, seguro.getEstatus()),
						set(PREMIOSSEGUROS, seguro.getPremiosSeguros())));
	}

	/**
	 * Actualizacion Estatus de Seguro.
	 *
	 * @param idSeguro - Id seguro
	 * @param estatus - Estatus
	 * @return Seguro antes de actualizacion
	 */
	public Seguro updateEstatus(String idSeguro, String estatus){		
		return coleccionSeguros.findOneAndUpdate(eq("_id", new ObjectId(idSeguro)), set(ESTATUS, estatus));
	}
}
