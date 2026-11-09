package com.baz.divisas.api.core.configuraciones.dao;

import static com.mongodb.client.model.Projections.include;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.baz.divisas.api.core.configuraciones.dto.BeanSegmentoLealtad;
import com.baz.divisas.api.core.configuraciones.dto.BeanSegmentoRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.BeanValoresRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.RespConsultaCatalogoRentLealVal;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropBDCatalogos;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.springframework.stereotype.Repository;

/**
 * Clase PerfilDeClientesDao
 * 
 * @author 1027346 Martinez Yonathan
 *
 */

@Repository
public class PerfilDeClientesDao {

	public static final String NUMBER1 = "lealtad";
	public static final String NUMBER2 = "rentabilidad";
	
	private static final String DESCRIP = "descripcion";
	private static final String ACTIVO = "activo";

	private FactoryMongoDB factoryDB;
	private MongoDatabase catalogosBD;

	private MongoCollection<Document> catSegmentoLealtad;
	private MongoCollection<Document> catSegmentoRentabilidad;
	private MongoCollection<Document> catValoresRentabilidad;

	private Gson gson = new Gson();

	/**
	 * Constructor de la clase
	 */
	public PerfilDeClientesDao() {
		super();
		factoryDB = FactoryMongoDB.getInstance();
		catalogosBD = factoryDB.getMongoClient().getDatabase(PropBDCatalogos.getCatalogosBD());

		catSegmentoLealtad = catalogosBD.getCollection(PropBD.getCatSegmentoLealtad());
		catSegmentoRentabilidad = catalogosBD.getCollection(PropBD.getCatSegmentoRentabilidad());
		catValoresRentabilidad = catalogosBD.getCollection(PropBD.getCatValoresRentabilidad());
	}

	/**
	 * Este método retorna el catalogo de divisas
	 * 
	 * @param tipoCatalogo - Identificador del segmento
	 * @return RespConsultaCatalogoDivisas
	 */
	public RespConsultaCatalogoRentLealVal consultaCatalogosRentLealValores(String tipoCatalogo) {

		RespConsultaCatalogoRentLealVal respConsultaCatalogoRentLealVal = new RespConsultaCatalogoRentLealVal();

		switch (tipoCatalogo) {
			case NUMBER1:

				if (segmentoLealtad() != null) {
					respConsultaCatalogoRentLealVal.setSegmentoLealtad(segmentoLealtad());
				}

				break;

			case NUMBER2:

				if (segmentorentavilidad() != null) {
					respConsultaCatalogoRentLealVal.setSegmentoRentabilidad(segmentorentavilidad());
				}

				break;

			default:

				break;
		}

		return respConsultaCatalogoRentLealVal;
	}

	/**
	 * Metodo de edicion de catalogos segmentacion lealtad
	 * 
	 * @param lealtad - documento para editar la lealtad
	 * @param id - id documento
	 * @return respuesta boleana del update
	 */
	public boolean editarCatSegmentoLealtad(Document lealtad, String id) {

		UpdateResult resultado = catSegmentoLealtad.updateMany(new BasicDBObject("_id", id),
				new BasicDBObject("$set", new Document(lealtad)), new UpdateOptions().upsert(false));

		return resultado.wasAcknowledged();
	}

	/**
	 * Metodo edita el catalogo de segmento de rentabilidad
	 * 
	 * @param rentabilidad - documento para editar la rentabilidad
	 * @return respuesta boleana del update
	 */
	public boolean editarCatSegmentoRentabilidad(Document rentabilidad) {

		UpdateResult resultado = catSegmentoRentabilidad.updateMany(
				new BasicDBObject("_id", rentabilidad.get("_id").toString()),
				new BasicDBObject("$set", new Document(rentabilidad)), new UpdateOptions().upsert(false));

		return resultado.wasAcknowledged();

	}

	/**
	 * Metodo edita el catalogo de valores rentabilidad
	 * 
	 * @param valoresRent - documento para editar la rentabilidad
	 * @return respuesta boleana del update
	 */
	public boolean editarCatValoresRentabilidad(Document valoresRent) {

		UpdateResult resultado = catValoresRentabilidad.updateMany(
				new BasicDBObject("_id", valoresRent.get("_id").toString()),
				new BasicDBObject("$set", new Document(valoresRent)), new UpdateOptions().upsert(false));

		return resultado.wasAcknowledged();
	}

	/**
	 * Metodo segmentoLealtad
	 * 
	 * @return BeanSegmentoLealtad
	 */
	public List<BeanSegmentoLealtad> segmentoLealtad() {

		List<BeanSegmentoLealtad> objetoSegmentoLealtad = null;

		// Se genera la estructura del bean segmento lealtad
		Type beanSegmentoLealtad = new TypeToken<ArrayList<BeanSegmentoLealtad>>() {
		}.getType();

		// se agregan los campos del catalogo segmento lealtad
		List<String> includeFieldsSegmentoLealtad = new ArrayList<>();
		includeFieldsSegmentoLealtad.add("_id");
		includeFieldsSegmentoLealtad.add("id_negocio");
		includeFieldsSegmentoLealtad.add(DESCRIP);
		includeFieldsSegmentoLealtad.add("limite_meses");
		includeFieldsSegmentoLealtad.add("transaccion_minimo");
		includeFieldsSegmentoLealtad.add("transaccion_maximo");
		includeFieldsSegmentoLealtad.add(ACTIVO);
		includeFieldsSegmentoLealtad.add("limites_rentabilidad");

		// Se obtiene el catalogo de segmento lealtad
		List<Document> resultCatSegmentoLealtad = catSegmentoLealtad.find()
				.projection(include(includeFieldsSegmentoLealtad)).into(new ArrayList<Document>());

		if (resultCatSegmentoLealtad != null && !resultCatSegmentoLealtad.isEmpty()) {
			objetoSegmentoLealtad = gson.fromJson(gson.toJson(resultCatSegmentoLealtad), beanSegmentoLealtad);
		}

		return objetoSegmentoLealtad;
	}

	/**
	 * Metodo segmentorentavilidad
	 * 
	 * @return BeanSegmentoRentabilidad
	 */
	public List<BeanSegmentoRentabilidad> segmentorentavilidad() {

		List<BeanSegmentoRentabilidad> objetoSegmentoRentabilidad = null;

		// Se genera la estructura del bean segmento rentabilidad
		Type beanSegmentoRentabilidad = new TypeToken<ArrayList<BeanSegmentoRentabilidad>>() {
		}.getType();

		// se agregan los campos del catalogo segmento rentabilidad
		List<String> includeFieldsRentabilidad = new ArrayList<>();
		includeFieldsRentabilidad.add("_id");
		includeFieldsRentabilidad.add(DESCRIP);
		includeFieldsRentabilidad.add("monto_minimo");
		includeFieldsRentabilidad.add("monto_maximo");
		includeFieldsRentabilidad.add("maximo_premios");
		includeFieldsRentabilidad.add(ACTIVO);

		// Se obtiene el catalogo de Tipo Operacion Cliente
		List<Document> resultCatSegmentoRentabilidad = catSegmentoRentabilidad.find()
				.projection(include(includeFieldsRentabilidad)).into(new ArrayList<Document>());

		if (resultCatSegmentoRentabilidad != null && !resultCatSegmentoRentabilidad.isEmpty()) {
			objetoSegmentoRentabilidad = gson.fromJson(gson.toJson(resultCatSegmentoRentabilidad),
					beanSegmentoRentabilidad);
		}

		return objetoSegmentoRentabilidad;
	}

	/**
	 * Metodo valoresRentabilidad
	 * 
	 * @return RespConsultaCatalogoRentLealVal
	 */
	public List<BeanValoresRentabilidad> valoresRentabilidad() {

		List<BeanValoresRentabilidad> objetoValoresRentabilidad = null;
		// Se genera la estructura del bean valores rentabilidad
		Type beanValoresRentabilidad = new TypeToken<ArrayList<BeanValoresRentabilidad>>() {
		}.getType();

		// se agregan los campos del catalogo valores rentabilidad
		List<String> includeFieldsValoresRentabilidad = new ArrayList<>();
		includeFieldsValoresRentabilidad.add("_id");
		includeFieldsValoresRentabilidad.add(DESCRIP);
		includeFieldsValoresRentabilidad.add("valor");
		includeFieldsValoresRentabilidad.add(ACTIVO);

		// Se obtiene el catalogo de monedas
		List<Document> resultCatValoresRentabilidad = catValoresRentabilidad.find()
				.projection(include(includeFieldsValoresRentabilidad)).into(new ArrayList<Document>());

		if (resultCatValoresRentabilidad != null && !resultCatValoresRentabilidad.isEmpty()) {
			objetoValoresRentabilidad = gson.fromJson(gson.toJson(resultCatValoresRentabilidad),
					beanValoresRentabilidad);
		}
		return objetoValoresRentabilidad;
	}
}
