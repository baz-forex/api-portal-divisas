package com.baz.divisas.api.core.reporteria.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;

/**
 * Clase OperacionesDao
 * 
 * @author itoxtlem
 *
 */
@Repository
public class OperacionesDao {

	private static final String MONTO_DIVISA = "monto_divisa";
	private static final String TIPO_IDENTIFICACION = "tipo_identificacion";
	private static final String FECHA_OPERACION = "fecha_operacion";
	private static final String TIPO_OPERACION = "tipo_operacion";
	private static final String ID_CLIENTE = "id_cliente";
	
	private static final String OPERACIONES = "operaciones";
	private static final String MONTO_MXN = "monto_mxn";

	private FactoryMongoDB factoryDB;
	private MongoDatabase middlewareBD;
	private MongoCollection<Document> collectionOperaciones;
	
	private static final List<String> FIELDS_CONSULTA = Arrays.asList(ID_CLIENTE,"nombre","apellido_paterno",
			"apellido_materno","sucursal","divisa",MONTO_DIVISA,FECHA_OPERACION,"folio_identificacion");
	

	/**
	 * Constructor de la clase
	 */
	public OperacionesDao() {
		super();
		factoryDB = FactoryMongoDB.getInstance();
		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		collectionOperaciones = middlewareBD.getCollection(PropBD.getOperacionesMW());

	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public OperacionesDao(MongoCollection<Document> coleccion) {
		super();
		collectionOperaciones = coleccion;
	}

	/**
	 * Count Operaciones
	 *
	 * @param filtro - Filtro de consulta
	 * @return Numero de operaciones
	 */
	public long count(Document filtro) {
		
		return collectionOperaciones.countDocuments(filtro);
	}
	
	/**
	 * Count Operaciones
	 *
	 * @param filtro - Filtro de consulta
	 * @return Numero de operaciones
	 */
	public Document acumulado(Document filtro) {
		
		Document acumulado = new Document(OPERACIONES, 0).append(MONTO_DIVISA, 0).append(MONTO_MXN, 0);
		
		try {
						
			List<Document> acumuladoOperaciones = collectionOperaciones.aggregate(Arrays.asList(
																		 match(filtro),
																		 group("", Accumulators.sum(OPERACIONES, 1), Accumulators.sum(MONTO_DIVISA, "$monto_divisa"), Accumulators.sum(MONTO_MXN, "$monto_mxn"))))
															  .allowDiskUse(true).into(new ArrayList<Document>());
			
			if(!acumuladoOperaciones.isEmpty()) {
				acumulado.put(OPERACIONES, acumuladoOperaciones.get(0).getInteger(OPERACIONES));
				acumulado.put(MONTO_DIVISA, acumuladoOperaciones.get(0).getDouble(MONTO_DIVISA).longValue());
				acumulado.put(MONTO_MXN, acumuladoOperaciones.get(0).getDouble(MONTO_MXN).longValue());
			}
			
		} 
		catch (MongoException e) {	
			LogApp.excepcion(e, "Ocurrió un error al consultar acumulado de Operaciones");
		}

		return acumulado;
	}
	
	/**
	 * Consulta Operaciones App.
	 *
	 * @param isDescarga - Bandera que indica si es descarga de informacion
	 * @param filtro - Filtro de consulta
	 * @return Lista de operaciones
	 */
	public Iterable<Document> consulta(Document filtro, boolean isDescarga) {
		
		if (!isDescarga) {

			return collectionOperaciones
					.aggregate(Arrays.asList(
					 match(filtro),
					 sort(descending(FECHA_OPERACION)),
					 project(fields(excludeId(), 
							 		include(FIELDS_CONSULTA), 
							 		computed(TIPO_OPERACION, new Document("$switch", 
									new Document("branches", Arrays.asList(
											new Document("case", new Document("$eq", Arrays.asList("$"+TIPO_OPERACION, Constant.COMPRA_EFECTIVO)))
												 .append("then", Constant.DESC_COMPRA_EFECTIVO),
											new Document("case", new Document("$eq", Arrays.asList("$"+TIPO_OPERACION, Constant.COMPRA_ABONO)))
												 .append("then", Constant.DESC_COMPRA_ABONO),
											new Document("case", new Document("$eq", Arrays.asList("$"+TIPO_OPERACION, Constant.VENTA_EFECTIVO)))
												 .append("then", Constant.DESC_VENTA_EFECTIVO),
											new Document("case", new Document("$eq", Arrays.asList("$"+TIPO_OPERACION, Constant.VENTA_CARGO)))
												 .append("then", Constant.DESC_VENTA_CARGO)))
											     .append("default", ""))),
							 		computed(TIPO_IDENTIFICACION, new Document("$switch", 
										new Document("branches", Arrays.asList(
												new Document("case", new Document("$eq", Arrays.asList("$"+TIPO_IDENTIFICACION, Constant.INE)))
													 .append("then", Constant.DESC_INE),
												new Document("case", new Document("$eq", Arrays.asList("$"+TIPO_IDENTIFICACION, Constant.PASAPORTE)))
													 .append("then", Constant.DESC_PASAPORTE),
												new Document("case", new Document("$eq", Arrays.asList("$"+TIPO_IDENTIFICACION, Constant.PASSPORT_CARD)))
													 .append("then", Constant.DESC_PASSPORT_CARD),
												new Document("case", new Document("$eq",Arrays.asList("$"+TIPO_IDENTIFICACION, Constant.FORMA_MIGRATORIA)))
													 .append("then", Constant.DESC_FORMA_MIGRATORIA)))
												     .append("default", ""))),
							 		computed("estatus","$status")
							 		)),
					 limit(Constant.INT_50))).allowDiskUse(true);				
		} 
		else {
			return collectionOperaciones.find(filtro).sort(ascending(FECHA_OPERACION))
											   .allowDiskUse(true);
		}

	}

}
