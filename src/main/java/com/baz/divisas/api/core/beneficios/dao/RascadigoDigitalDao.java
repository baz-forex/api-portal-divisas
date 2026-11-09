package com.baz.divisas.api.core.beneficios.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.core.beneficios.dto.ResponseRascadito;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@Repository
public class RascadigoDigitalDao {
	
	
	private FactoryMongoDB factoryDB;
	private MongoDatabase middlewareBD;
	private MongoCollection<ResponseRascadito> coleccionParametriaRascadito;
	/**
	 * 
	 */
	public RascadigoDigitalDao() {
		
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		coleccionParametriaRascadito =  middlewareBD.getCollection(PropBD.getParametriaRascadito(),ResponseRascadito.class).withCodecRegistry(pojoCodecRegistry);
		
	}
	
	public ResponseRascadito findLandingPage(String id) {

		return coleccionParametriaRascadito.find(eq("_id", new ObjectId(id))).first();

	}
	
	public UpdateResult estatusParameria(String id, boolean isActivo) {

		return coleccionParametriaRascadito.updateOne(eq("_id", new ObjectId(id)), set("activo", isActivo));

	}
	
	
	public UpdateResult actualizaLandingPage(ResponseRascadito requestRascadigo) {

		return coleccionParametriaRascadito.updateOne(eq("_id", new ObjectId(requestRascadigo.getId())),
				combine(
						set("nombreCampania", requestRascadigo.getNombreCampania()),
						set("etiquetas", requestRascadigo.getEtiquetas()),
						set("imagenes", requestRascadigo.getImagenes())
						)
				, new UpdateOptions().upsert(true));
	}
	
	
	public InsertOneResult registraLandingPage(ResponseRascadito requestRascadigo) {
		
		
		return coleccionParametriaRascadito.insertOne(requestRascadigo);

	}
	
	public List<ResponseRascadito> consultaParametriaRascadito(){
		
		return coleccionParametriaRascadito.find().sort(descending("_id")).into(new ArrayList<ResponseRascadito>());
	}
	
}
