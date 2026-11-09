/**
 * 
 */
package com.baz.divisas.api.tef.parametria.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.digital.configuraciones.model.ParametriaChatbot;
import com.baz.divisas.api.tef.spots.dto.ReqConfiguracionSpot;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Parametria Dao.
 *
 * @author B86960
 */
@Repository
public class ParametriaDao {
	
	/** The Constant ID. */
	private static final String ID = "_id";
	
	/** The Constant ACTIVO. */
	private static final String ACTIVO = "activo";
	
	/** The col. */
	private MongoCollection<Document> col;
	
	/**
	 * Constructor.
	 */
	public ParametriaDao() {
		//Se obtiene la instancia de la conexion de mongo
		FactoryMongoDB factoryDB = FactoryMongoDB.getInstance();
		//Se obtiene la base
		MongoDatabase middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		//Se obtiene la coleccion
		col = middlewareBD.getCollection(PropBD.getParametriaGeneral());
	}
	
	/**
	 * Instantiates a new parametria dao.
	 *
	 * @param col the col
	 */
	public ParametriaDao(MongoCollection<Document> col) {
		//Se obtiene la coleccion
		super();
		this.col = col;
	}
	
	/**
	 * Metodo para consultar la parametria.
	 *
	 * @param parametria parametria
	 * @return Document
	 */
	public Document consultar(String parametria) {
		//Se obtiene el documento de la parametria
		Document doc = col.find(eq(ID,parametria)).first();
		//Se valida el documento
		if(null != doc && !doc.isEmpty())
		{
			//Se devuelve el estatus de la parametria
			return doc;
		}
		return new Document();
	}
	
	/**
	 * Metodo para obtener el estatus de la parametria.
	 *
	 * @param parametria the parametria
	 * @return estatus
	 */
	public boolean obtenerActivo(String parametria) {
		//Se obtiene el documento de la parametria
		Document doc = col.find(eq(ID,parametria)).first();
		//Se valida el documento
		if(null != doc && !doc.isEmpty())
		{
			//Se devuelve el estatus de la parametria
			return doc.getBoolean(ACTIVO);
		}
		return false;
	}
	
	/**
	 * Metodo de actualizacion del informe promo espacio.
	 *
	 * @param parametria the parametria
	 * @param estatus - Actualizacion de la parametria
	 * @return boolean
	 */
	public boolean actualizarCampoActivo(String parametria, boolean estatus) {
		//Se actualiza el estatus de la parametria
		UpdateResult result = col.updateOne(eq(ID, parametria), set(ACTIVO, estatus));
		//Se regresa si se actualizo la parametria
		return result.getMatchedCount() > 0;
	}
	
	/**
	 * Actualizar campo.
	 *
	 * @param <T> the generic type
	 * @param parametria the parametria
	 * @param campo the campo
	 * @param dato the dato
	 * @return true, if successful
	 */
	public <T> boolean actualizarCampo(String parametria, String campo, T dato) {
		//Se actualiza el estatus de la parametria
		UpdateResult result = col.updateOne(eq(ID, parametria), set(campo, dato));
		//Se regresa si se actualizo la parametria
		return result.getMatchedCount() > 0;
	}
	
	/**
	 * Eliminar campo.
	 *
	 * @param parametria the parametria
	 * @param campo the campo
	 * @return true, if successful
	 */
	public boolean eliminarCampo(String parametria, String campo) {
		//Se actualiza el estatus de la parametria
		UpdateResult result = col.updateOne(eq(ID, parametria), unset(campo));
		//Se regresa si se actualizo la parametria
		return result.getMatchedCount() > 0;
	}

	/**
	 * Eliminar.
	 *
	 * @param parametria the parametria
	 * @return true, if successful
	 */
	public boolean eliminar(String parametria) {
		//Se actualiza el estatus de la parametria
		DeleteResult result = col.deleteOne(eq(ID, parametria));
		//Se regresa si se actualizo la parametria
		return result.getDeletedCount() > 0;
	}

	/**
	 * Alta.
	 *
	 * @param document the document
	 * @return true, if successful
	 */
	public boolean alta(Document document) {
		//Se consulta si existe la parametria
		List<Document> doc = col.find(eq(ID,document.getString("parametria"))).into(new ArrayList<>());
		//Si el documento no esta vacio no se inserta
		if(null != doc && !doc.isEmpty()) {
			return false;
		}
		//Se inserta la parametria
		InsertOneResult result = col.insertOne(document);
		//Se regresa si se actualizo la parametria
		return !result.getInsertedId().isNull();
	}

	/**
	 * Consultar.
	 *
	 * @return the list
	 */
	public List<String> consultar() {
		//Lista de ids
		List<String> ids = new ArrayList<>();
		//Se consultan todas las parametrias
		List<Document> parametrias = col.find().into(new ArrayList<>());
		//Se valida que no este vacio
		if(null != parametrias && !parametrias.isEmpty()){
			parametrias.forEach((x) -> ids.add(x.getString(ID)));
		}
		return ids;
	}

	/**
	 * Find parametria.
	 *
	 * @param idParametria the id parametria
	 * @param divisa the divisa
	 * @return the document
	 */
	public Document findParametria(String idParametria, String divisa) {
		Document doc = col.aggregate(Arrays.asList(
				match(eq("_id",idParametria)),
				unwind("$monedas"),
				match(eq("monedas.idMoneda",divisa)),
				project(fields(
						computed("idMoneda", "$monedas.idMoneda"),
						computed("vigenciaEnDias", "$monedas.vigenciaEnDias"),
						computed("montos", "$monedas.montos"),
						computed("operacionesPermitidas", "$monedas.operacionesPermitidas"),
						computed("operacionesCanceladas", "$monedas.operacionesCanceladas"),
						computed("fluctuacion", "$monedas.fluctuacion")
						))
				)).first();

		return doc;
	}

	/**
	 * Update parametria.
	 *
	 * @param idParametria the id parametria
	 * @param divisa the divisa
	 * @param parametria the doc parametria nueva
	 * @return the document
	 */
	public Document updateParametria(String idParametria, String divisa, ParametriaChatbot parametria) {
		return col.findOneAndUpdate(and(eq("_id", idParametria), eq("monedas.idMoneda", divisa)), 
				   combine(
						   set("monedas.$.vigenciaEnDias", parametria.getVigenciaEnDias()),
						   set("monedas.$.montos.montoMinimo", parametria.getMontos().getMontoMinimo()),
						   set("monedas.$.montos.montoMaximo", parametria.getMontos().getMontoMaximo()),
						   set("monedas.$.operacionesPermitidas.operaciones", parametria.getOperacionesPermitidas().getOperaciones()),
						   set("monedas.$.operacionesPermitidas.plazo", parametria.getOperacionesPermitidas().getPlazo()),
						   set("monedas.$.operacionesCanceladas.operaciones", parametria.getOperacionesCanceladas().getOperaciones()),
						   set("monedas.$.operacionesCanceladas.plazoLimite",  parametria.getOperacionesCanceladas().getPlazoLimite()),
						   set("monedas.$.operacionesCanceladas.periodoLimite",  parametria.getOperacionesCanceladas().getPeriodoLimite()),
						   set("monedas.$.operacionesCanceladas.plazoBloqueo",  parametria.getOperacionesCanceladas().getPlazoBloqueo()),
						   set("monedas.$.operacionesCanceladas.periodoBloqueo",  parametria.getOperacionesCanceladas().getPeriodoBloqueo()),
						   set("monedas.$.fluctuacion.valor", parametria.getFluctuacion().getValor()),
						   set("monedas.$.fluctuacion.unidadMoneda", parametria.getFluctuacion().getUnidadMoneda()),
						   set("monedas.$.fluctuacion.valorPesos", parametria.getFluctuacion().getValorPesos())
						   ));
	}
	/**
	 * Update parametria configuracion spot OLP.
	 *
	 * @param req the req
	 * @return true, if successful
	 */
	public boolean updateParametriaConfiguracionSpotOLP(ReqConfiguracionSpot req) {
		UpdateResult result = col.updateOne(eq(ID, req.getId()),
				combine(
				   set("acunacion", req.getAcunacion()),
				   set("x1", req.getX1()),
				   set("x2", req.getX2()),
				   set("spotMinimo", req.getSpotMinimo()),
				   set("frecuencia", req.getFrecuencia()),
				   set("direccionActualizacion", req.getDireccionActualizacion()),
				   set("diferenciaCompraVenta",req.getDiferenciaCompraVenta())
						));
		
		return result.getMatchedCount() > 0;
	}
}
