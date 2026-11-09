package com.baz.divisas.api.tef.catalogos.dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.promoespacio.model.BeanDistanciaSucursales;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropBDCatalogos;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.util.LogApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.result.UpdateResult;

/**
 * DAO Operaciones.
 *
 * @author 863680
 */
@Repository("CatalogosTEFDao")
public class CatalogosDao {

	/** The Constant GEOLOCALIZACION. */
	private static final String GEOLOCALIZACION = "Geolocalizacion";
	
	/** The Constant ACTIVO. */
	private static final String ACTIVO = "activo";
	
	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The catalogo divisas BD. */
	private MongoDatabase catalogoDivisasBD;
	
	/** The coleccion cat tipo operacion. */
	private MongoCollection<Document> coleccionCatTipoOperacion;
	
	/** The colleccion cat monedas. */
	private MongoCollection<Document> colleccionCatMonedas;
	
	/** The colleccion ubicacion sucursales. */
	private MongoCollection<Document> colleccionUbicacionSucursales;
	/** The parametria. */
	private final ParametriaDao parametria;	

	/**
	 * Constructor de la clase.
	 */
	public CatalogosDao() {

		factoryDB = FactoryMongoDB.getInstance();
		catalogoDivisasBD = factoryDB.getMongoClient().getDatabase(PropBDCatalogos.getCatalogosBD());
		coleccionCatTipoOperacion = catalogoDivisasBD.getCollection(PropBD.getCatTipoOperacionColeccion());
		colleccionCatMonedas = catalogoDivisasBD.getCollection(PropBD.getCatMonedasColeccion());
		colleccionUbicacionSucursales = catalogoDivisasBD.getCollection(PropBD.getCatUbicacionSucursales());
		parametria = new ParametriaDao();
	}
	
	/**
	 *  Metodo de consulta de tipos de operacion.
	 *
	 * @return Tipos de operacion
	 */
	public List<Catalogo> findTiposOperacion(){

		List<Catalogo> tiposOperacion = new ArrayList<>();
		Gson gson = new Gson();

		try{

			ArrayList<Document> result = coleccionCatTipoOperacion.find()
					.sort(Sorts.ascending("_id")).into(new ArrayList<Document>());
			
			tiposOperacion = gson.fromJson(gson.toJson(result), new TypeToken<ArrayList<Catalogo>>(){}.getType());

		}
		catch(MongoException e) {
			LogApp.excepcion(e, "Ocurrio un error en la consulta Tipos de Operacion.");
		}

		return tiposOperacion;
	}
	
	/**
	 * Metodo de consulta de tipo de operacion por Id.
	 *
	 * @param id - Identificador de tipo de operacion
	 * @return Tipo de Operacion
	 */
	public Document findTipoOperacionById(String id){

		return coleccionCatTipoOperacion.find(and(eq("_id", id), eq(ACTIVO,true))).first();
	}

	/**
	 * Metodo de validacion del tipo de operacion.
	 *
	 * @param id - Identificador de tipo de operacion
	 * @return boolean
	 */
	public boolean existeTipoOperacion(String id) {

		Document resultado = coleccionCatTipoOperacion.find(eq(ACTIVO, id)).first();

		return resultado != null;
	}

	/**
	 * Metodo de actualizacion de estatus del Tipo de Operacion.
	 *
	 * @param id - Identificador de tipo de operacion
	 * @param activo - Estatus
	 * @return Resultado
	 */
	public boolean updateEstatusTipoOperacion(String id, boolean activo) {

		try {

			UpdateResult result = coleccionCatTipoOperacion.updateOne(eq("_id", id), set(ACTIVO, activo));

			if(result.getMatchedCount() == 0) {
				return false;
			}
		} 
		catch (MongoException e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar actualizar el estatus del tipo de Operacion.");
			return false;
		}

		return true;
	}

	/**
	 * Metodo de consulta de Divisa por Id.
	 *
	 * @param id - Identificador de tipo de operacion
	 * @return Tipo de Operacion
	 */
	public Document findDivisaById(String id){

		return colleccionCatMonedas.find(and(eq("_id", id), eq(ACTIVO,true))).first();
	}

	/**
	 * Consulta el catalogo de ubicacion geografica de cada sucursal.
	 *
	 * @param sucursal the sucursal
	 * @return the bean distancia sucursales
	 */
	public BeanDistanciaSucursales consultarCatUbicacionSucursales(String sucursal) {

		Gson gson = new Gson();
		Document ubicacionSucursal = colleccionUbicacionSucursales.find(new Document("name", sucursal)).first();
		return gson.fromJson(gson.toJson(ubicacionSucursal),BeanDistanciaSucursales.class);
	}
	
	/**
	 * Consulta las sucursales cercanas.
	 *
	 * @param longitud the longitud
	 * @param latitud the latitud
	 * @return the list
	 */
	public List<BeanDistanciaSucursales> consultarSucursalesCercanas(Double longitud, Double latitud) {

		Document doc = parametria.consultar(GEOLOCALIZACION);		
		
		Gson gson = new Gson();
		Position position = new Position(longitud,latitud);
		Point point = new Point(position);
		
		List<Document> documents = colleccionUbicacionSucursales
				.find(near("location", point, doc.getDouble("kilometros"), 0.0))
				.limit(doc.getInteger("limiteSucursales")).into(new ArrayList<>());

		return gson.fromJson(gson.toJson(documents),
				new TypeToken<List<BeanDistanciaSucursales>>() {
		}.getType());

	}

}
