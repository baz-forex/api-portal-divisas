package com.baz.divisas.api.catalogos.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.catalogos.model.CatRangoEdadesCampanias;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.general.model.CatalogoDivisas;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropBDCatalogos;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;


/**
 * Clase CatalogosDao.
 *
 * @author B813196
 * itoxtle
 */
@Repository
public class CatalogosDao {

	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The catalogos divisas BD. */
	private MongoDatabase catalogosDivisasBD;
	
	/** The coleccion cat nacionalidades. */
	private MongoCollection<Document> coleccionCatNacionalidades;
	
	/** The coleccion cat estados. */
	private MongoCollection<Document> coleccionCatEstados;
	
	/** The coleccion cat monedas. */
	private MongoCollection<Document> coleccionCatMonedas;
	
	/** The coleccion cat identificaciones. */
	private MongoCollection<Document> coleccionCatIdentificaciones;
	
	/** The coleccion cat estatus cotizacion. */
	private MongoCollection<Document> coleccionCatEstatusCotizacion;
	
	/** The coleccion cat tipo operacion. */
	private MongoCollection<Document> coleccionCatTipoOperacion;
	
	/** The coleccion cat tipo cliente. */
	private MongoCollection<Document> coleccionCatTipoCliente;
	
	/** The coleccion cat perfil cliente campanias. */
	private MongoCollection<Document> coleccionCatPerfilClienteCampanias;
	
	/** The coleccion cat tipo limite campanias. */
	private MongoCollection<Document> coleccionCatTipoLimiteCampanias;
	
	/** The coleccion cat tipo acumulado campanias. */
	private MongoCollection<Document> coleccionCatTipoAcumuladoCampanias;
	
	/** The coleccion cat estatus campanias. */
	private MongoCollection<Document> coleccionCatEstatusCampanias;
	
	/** The coleccion cat rango edades campanias. */
	private MongoCollection<Document> coleccionCatRangoEdadesCampanias;

	/**
	 * Constructor de la clase 
	 * Usado para inyectar Mocks.
	 *
	 * @param coleccion - Mock Collection
	 */
	public CatalogosDao(MongoCollection<Document> coleccion) {
		super();
		this.coleccionCatNacionalidades = coleccion;
		this.coleccionCatEstados = coleccion;
		this.coleccionCatMonedas = coleccion;
		this.coleccionCatIdentificaciones = coleccion;
		this.coleccionCatEstatusCotizacion = coleccion;
		this.coleccionCatTipoOperacion = coleccion;
		this.coleccionCatTipoCliente = coleccion;
		this.coleccionCatPerfilClienteCampanias = coleccion;
		this.coleccionCatTipoLimiteCampanias = coleccion;
		this.coleccionCatTipoAcumuladoCampanias = coleccion;
		this.coleccionCatEstatusCampanias = coleccion;
		this.coleccionCatRangoEdadesCampanias = coleccion;
	}

	/**
	 * Constructor de la clase.
	 */
	public CatalogosDao() {
		super();		
		factoryDB = FactoryMongoDB.getInstance();
		catalogosDivisasBD = factoryDB.getMongoClient().getDatabase(PropBDCatalogos.getCatalogosBD());
		coleccionCatNacionalidades = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatNacionalidadesColeccion());		
		coleccionCatEstados = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatEstadosColeccion());
		coleccionCatMonedas = catalogosDivisasBD.getCollection(PropBD.getCatMonedasColeccion());
		coleccionCatIdentificaciones = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatIdentificaciones());
		coleccionCatEstatusCotizacion = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatEstatusCotizacion());


		coleccionCatTipoOperacion = catalogosDivisasBD.getCollection(PropBD.getCatTipoOperacionColeccion());
		coleccionCatTipoCliente = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatTipoCliente());
		coleccionCatPerfilClienteCampanias = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatPerfilClienteCampanias());
		coleccionCatTipoLimiteCampanias = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatTipoLimiteCampanias());
		coleccionCatTipoAcumuladoCampanias = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatTipoAcumuladoCampanias());
		coleccionCatEstatusCampanias = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatEstatusCampanias());
		coleccionCatRangoEdadesCampanias = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatRangoEdadesCampanias());
	}

	/**
	 * Consulta de catalogo Pais/Nacionalidad.
	 *
	 * @return Catalogo Pais/Nacionalidad
	 */
	public List<CatPaisNacionalidad> findCatPaisNacionalidad() {

		List<CatPaisNacionalidad> nacionalidades = new ArrayList<>();

		List<Document> resultado = coleccionCatNacionalidades.find().sort(Sorts.ascending("descripcion"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			nacionalidades = gson.fromJson(resultadoJson, new TypeToken<ArrayList<CatPaisNacionalidad>>(){}.getType());
		}

		return nacionalidades;
	}


	/**
	 * Consulta de catalogo Estados.
	 *
	 * @return Catalogo Estados
	 */
	public List<Catalogo> findCatEstados() {

		List<Catalogo> estados = new ArrayList<>();

		List<Document> resultado = coleccionCatEstados.find().sort(Sorts.ascending("descripcion"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			estados = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>(){}.getType());
		}

		return estados;
	}

	/**
	 * Metodo de consulta de Catalogo de Divisas.
	 *
	 * @return list Divisa
	 */
	public List<Catalogo> findCatDivisas() {

		List<Catalogo> divisas = new ArrayList<>();

		List<Document> resultado = coleccionCatMonedas.find(ne("_id","OLP")).sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			divisas = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return divisas;
	}

	/**
	 * Metodo de consulta de Catalogo de Divisas con limites.
	 *
	 * @return list Divisa
	 */
	public List<CatalogoDivisas> findCatDivisasLimites() {

		List<CatalogoDivisas> divisas = new ArrayList<>();

		List<Document> resultado = coleccionCatMonedas.find().sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			divisas = gson.fromJson(resultadoJson, new TypeToken<ArrayList<CatalogoDivisas>>() {}.getType());
		}

		return divisas;
	}

	/**
	 * Metodo de consulta de Catalogo de Divisas.
	 *
	 * @return list Divisa
	 */
	public List<Catalogo> findCatIdentificaciones() {

		List<Catalogo> divisas = new ArrayList<>();

		Document project = new Document();

		project.append("_id", "$id_cliente_amigo").append("descripcion", 1).append("activo", 1);

		List<Document> resultado = coleccionCatIdentificaciones.aggregate(
				Arrays.asList(
						match(eq("id_cliente_amigo", new Document("$exists",true))),
						project(project),
						sort(new Document("_id",-1))

						))
				.into(new ArrayList<Document>());

		if(resultado != null) {         
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			divisas = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return divisas;
	}

	/**
	 * Metodo de actualizacion del catalogo divisas.
	 *
	 * @param id     - Id de la divisa a actualizar
	 * @param estatus - Estatus de la divisa
	 * @return boolean
	 */
	public boolean actualizarEstatusCatDivisas(String id, boolean estatus) {

		UpdateResult result = coleccionCatMonedas.updateOne(eq("_id", id), set("activo", estatus));

		return result.getMatchedCount() > 0;
	}

	/**
	 * Metodo de consulta de Catalogo de Divisas.
	 *
	 * @param codigoDivisa the codigo divisa
	 * @return list Divisa
	 */
	public List<Catalogo> findCatDivisasId(String codigoDivisa) {

		List<Catalogo> divisas = new ArrayList<>();

		List<Document> resultado = coleccionCatMonedas.find(eq("_id",codigoDivisa))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			divisas = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return divisas;
	}

	/**
	 * Metodo de registro de limites por cliente.
	 *
	 * @param limites the limites
	 * @return InsertOneResult
	 */
	public InsertOneResult insertDivisa(Document limites) {

		return coleccionCatMonedas.insertOne(limites);

	}

	/**
	 * Metodo de reinicio de visualizaciones de una region.
	 *
	 * @param id the id
	 * @return DeleteResult
	 */
	public DeleteResult resetLimitesRegion(String id) {

		return coleccionCatMonedas.deleteMany(eq("_id", id));

	}


	/**
	 * Metodo de consulta de Catalogo Estatus Cotizacion.
	 *
	 * @return List<Catalogo>
	 */
	public List<Catalogo> findCatEstatusCotizacion() {

		List<Catalogo> estatus = new ArrayList<>();

		List<Document> resultado = coleccionCatEstatusCotizacion.aggregate(
				Arrays.asList(
						project(fields(computed("descripcion", "$compra.estatus_banca"),
								computed("activo", "true")))
						))
				.into(new ArrayList<Document>());

		if(resultado != null) {         
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			estatus = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return estatus;
	}
	
	/**
	 * Find cat tipo operacion.
	 *
	 * @return the list
	 */
	public List<Catalogo> findCatTipoOperacion() {
		List<Catalogo> tipoOperacion = new ArrayList<>();

		List<Document> resultado = coleccionCatTipoOperacion.find().sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			tipoOperacion = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return tipoOperacion;
	}

	/**
	 * Find cat tipo cliente.
	 *
	 * @return the list
	 */
	public List<Catalogo> findCatTipoCliente() {
		List<Catalogo> tipoCliente = new ArrayList<>();

		List<Document> resultado = coleccionCatTipoCliente.find().sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			tipoCliente = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return tipoCliente;
	}

	/**
	 * Find cat perfil cliente campanias.
	 *
	 * @return the list
	 */
	public List<Catalogo> findCatPerfilClienteCampanias() {
		List<Catalogo> perfilClienteCampanias = new ArrayList<>();

		List<Document> resultado = coleccionCatPerfilClienteCampanias.find().sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			perfilClienteCampanias = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return perfilClienteCampanias;
	}

	/**
	 * Find cat tipo limite campanias.
	 *
	 * @return the list
	 */
	public List<Catalogo> findCatTipoLimiteCampanias() {
		List<Catalogo> tipoLimiteCampanias = new ArrayList<>();

		List<Document> resultado = coleccionCatTipoLimiteCampanias.find().sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			tipoLimiteCampanias = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return tipoLimiteCampanias;
	}

	/**
	 * Find cat tipo acumulado campanias.
	 *
	 * @return the list
	 */
	public List<Catalogo> findCatTipoAcumuladoCampanias() {
		List<Catalogo> tipoAcumuladoCampanias = new ArrayList<>();

		List<Document> resultado = coleccionCatTipoAcumuladoCampanias.find().sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			tipoAcumuladoCampanias = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return tipoAcumuladoCampanias;
	}

	/**
	 * Find cat estatus campanias.
	 *
	 * @return the list
	 */
	public List<Catalogo> findCatEstatusCampanias() {
		List<Catalogo> estatusCampanias = new ArrayList<>();

		List<Document> resultado = coleccionCatEstatusCampanias.find().sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			estatusCampanias = gson.fromJson(resultadoJson, new TypeToken<ArrayList<Catalogo>>() {}.getType());
		}

		return estatusCampanias;
	}

	/**
	 * Find cat rango edades campanias.
	 *
	 * @return the list
	 */
	public List<CatRangoEdadesCampanias> findCatRangoEdadesCampanias() {
		List<CatRangoEdadesCampanias> rangoEdadesCampanias = new ArrayList<>();

		List<Document> resultado = coleccionCatRangoEdadesCampanias.find().sort(Sorts.ascending("_id"))
				.into(new ArrayList<Document>());

		if(resultado != null) {			
			Gson gson = new Gson();
			String resultadoJson = gson.toJson(resultado);
			rangoEdadesCampanias = gson.fromJson(resultadoJson, new TypeToken<ArrayList<CatRangoEdadesCampanias>>() {}.getType());
		}

		return rangoEdadesCampanias;
	}

}
