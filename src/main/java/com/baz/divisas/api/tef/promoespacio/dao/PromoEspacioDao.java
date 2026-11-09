/**
 * 
 */
package com.baz.divisas.api.tef.promoespacio.dao;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.fields;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.general.model.SucursalTC;
import com.baz.divisas.api.tef.promoespacio.model.DivisasBean;
import com.baz.divisas.api.tef.promoespacio.model.TcSpotBean;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Facet;

/**
 * The Class PromoEspacioDao.
 *
 * @author B86960
 */
@Repository
public class PromoEspacioDao {
	//Coleccion de sucursales
	private MongoCollection<DivisasBean> colSucursales;
	//Coleccion de Catalogo de Spot
	private MongoCollection<TcSpotBean> colCatSpot;
	/** The coleccion sucursales. */
	private MongoCollection<SucursalTC> coleccionSucursalesTC;

	/**
	 * Instantiates a new promo espacio dao.
	 */
	public PromoEspacioDao() {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		FactoryMongoDB factoryDB = FactoryMongoDB.getInstance();
		MongoDatabase terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		colSucursales= terminalFinancieroBD.getCollection(PropBD.getSucursales(), DivisasBean.class)
				.withCodecRegistry(pojoCodecRegistry);
		colCatSpot = terminalFinancieroBD.getCollection(PropBD.getCatSpot(), TcSpotBean.class)
				.withCodecRegistry(pojoCodecRegistry);
		coleccionSucursalesTC = terminalFinancieroBD.getCollection(PropBD.getSucursales(), SucursalTC.class)
				.withCodecRegistry(pojoCodecRegistry);
	}
	
	/**
	 * Instantiates a new promo espacio dao.
	 *
	 * @param colSucursales the col sucursales
	 * @param colCatSpot the col cat spot
	 */
	public PromoEspacioDao(MongoCollection<DivisasBean> colSucursales, MongoCollection<TcSpotBean> colCatSpot,MongoCollection<SucursalTC> coleccionSucursalesTC) {
		this.colSucursales = colSucursales;
		this.colCatSpot = colCatSpot;
		this.coleccionSucursalesTC = coleccionSucursalesTC;
	}
	
	/**
	 * Obtener tipos cambio sucursales.
	 *
	 * @param sucursales the sucursales
	 * @return the list
	 */
	public List<DivisasBean> obtenerTiposCambioSucursales(List<String> sucursales){
		return colSucursales.aggregate(Arrays.asList(
			    // 1. Filtro inicial común para reducir documentos desde el principio
			    Aggregates.match(Filters.in("_id", sucursales)),
			    
			    // 2. Ejecutar ambas consultas en paralelo mediante Facets
			    Aggregates.facet(
			        new Facet("resultadoTcolp",
			            Aggregates.project(Projections.fields(Projections.include("sucursal", "tcOlp"))),
			            Aggregates.unwind("$tcOlp.tiposOperacion"),
			            Aggregates.match(Filters.in("tcOlp.tiposOperacion.idTipoOperacion", Arrays.asList("410", "411"))),
			            Aggregates.project(Projections.fields(
			                Projections.computed("sucursalTexto", "$_id"),
			                Projections.include("sucursal"),
			                Projections.computed("divisa", "OLP"),
			                Projections.computed("tipoOperacion", "$tcOlp.tiposOperacion.idTipoOperacion"),
			                Projections.computed("tipoCambio", "$tcOlp.tiposOperacion.tipoCambioSpot")
			            ))
			        ),
			        new Facet("resultadoDivisas",
			            Aggregates.project(Projections.fields(Projections.include("sucursal", "divisas"))),
			            Aggregates.unwind("$divisas"),
			            Aggregates.unwind("$divisas.tiposOperacion"),
			            Aggregates.match(Filters.in("divisas.tiposOperacion.idTipoOperacion", Arrays.asList("410", "411"))),
			            Aggregates.project(Projections.fields(
			                Projections.computed("sucursalTexto", "$_id"),
			                Projections.include("sucursal"),
			                Projections.computed("divisa", "$divisas._id"),
			                Projections.computed("tipoOperacion", "$divisas.tiposOperacion.idTipoOperacion"),
			                Projections.computed("tipoCambio", "$divisas.tiposOperacion.tipoCambioSpot")
			            ))
			        )
			    ),
			    
			    // 3. Juntar los arrays de ambas búsquedas en una sola lista
			    Aggregates.project(Projections.computed("todosLosResultados", 
			        new Document("$concatArrays", Arrays.asList("$resultadoTcolp", "$resultadoDivisas"))
			    )),
			    
			    // 4. Romper el array contenedor para que MongoDB devuelva filas individuales mapeables a DivisasBean
			    Aggregates.unwind("$todosLosResultados"),
			    
			    // 5. Promover las propiedades al nivel raíz para que tu driver lo convierta directo a DivisasBean
			    Aggregates.project(Projections.fields(
			    	Projections.excludeId(),
			        Projections.computed("_id", "$todosLosResultados.sucursalTexto"),
			        Projections.computed("sucursal", "$todosLosResultados.sucursal"),
			        Projections.computed("divisa", "$todosLosResultados.divisa"),
			        Projections.computed("tipoOperacion", "$todosLosResultados.tipoOperacion"),
			        Projections.computed("tipoCambio", "$todosLosResultados.tipoCambio")
			    ))
			)).into(new ArrayList<DivisasBean>());
	}
	
	/**
	 * Obtener tipos cambio spot.
	 *
	 * @return the list
	 */
	public List<TcSpotBean> obtenerTiposCambioSpot(){
		return colCatSpot.aggregate(Arrays.asList(
				Aggregates.sort(Sorts.descending("idBitacora")),
				Aggregates.group("$idDivisa",
				Accumulators.first("compra", "$compraEfectivo"),
				Accumulators.first("venta", "$ventaEfectivo")),
				Aggregates.project(
						Projections.fields(
								Projections.excludeId(),
								Projections.include("compra","venta"),
								Projections.computed("divisa","$_id")
								)
						)
				)).into(new ArrayList<TcSpotBean>());
	}

	/**
	 * Consulta.
	 *
	 * @param idDivisa the id divisa
	 * @return the iterable
	 */
	public SucursalTC consultaSucursalesTC(String sucursal,String idDivisa) {
		return coleccionSucursalesTC
				.aggregate(
						Arrays.asList(
								match(eq("_id", sucursal)),
								unwind("$divisas"),
								match(eq("divisas._id",idDivisa)),
								project (
										fields(	
												computed("divisaCompra",new Document("$filter",
														new Document("input", "$divisas.tiposOperacion").append("as", "tipo").append("cond", new Document("$eq",Arrays.asList("$$tipo.idTipoOperacion" ,"410")))
														)											
														),
												computed("divisaVenta",new Document("$filter",
														new Document("input", "$divisas.tiposOperacion").append("as", "tipo").append("cond", new Document("$eq",Arrays.asList("$$tipo.idTipoOperacion" ,"411")))
														)
														))),
								unwind("$divisaCompra"),
								unwind("$divisaVenta")))
				.first();
	}	
}
