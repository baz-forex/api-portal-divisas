package com.baz.divisas.api.digital.reporteria.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropBDCatalogos;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase CotizacionesDao
 * 
 * @author itoxtle
 *
 */

@Repository
public class CotizacionesDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase middlewareBD;
	private MongoCollection<Document> coleccionCotizaciones;
	
	private MongoDatabase catalogosDivisasBD;
	private MongoCollection<Document> coleccionCatEstatusCotizacion;
	
	private static final String COND = "$cond";
	private static final String IFNULL = "$ifNull";
	private static final String NOMBRE = "nombre";
	private static final String PATERNO = "apellido_paterno";
	private static final String MATERNO = "apellido_materno";
	private static final String STATUS = "status";
	private static final String STATUS_BANCA = "estatus_banca";
	private static final String FECHA_COTIZACION = "fecha_cotizacion";
	private static final String FECHA_LIMITE = "fecha_limite";
	private static final String TIPO_OPERACION = "tipo_operacion";
	private static final String MONTO_DIVISA = "monto_divisa";
	private static final String SUCURSAL = "sucursal";
	private static final String ESTATUS_BANCA = "$estatus_banca";
	private static final String CONDUCTOR = "$entrega.conductor.";
	private static final String ID_CU = "idClienteUnico";
	
	public static final String ID_PRODUCTO = "id_producto";
	public static final String DESCRIPCION_MOVIMIENTO = "descripcion_movimiento";
	public static final String CONCEPTO = "concepto";
	public static final String DESCRIPCION_OPERACION = "descripcion_operacion";
	public static final String DESCRIPCION_OPERACION2 = "descripcion_operacion2";
	public static final String BANDERA_LIBRE = "bandera_libre";
	public static final String FECHA_ACTUALIZACION = "fecha_actualizacion";

	private static final List<String> FIELDS_CONSULTA = Arrays.asList(FECHA_COTIZACION,NOMBRE,PATERNO,
			MATERNO, "folio_identificacion", SUCURSAL,"divisa",MONTO_DIVISA,"id_cliente",STATUS_BANCA);
	
	private static final List<String> FIELDS_DESCARGA = Arrays.asList("icu", "id_cliente_alnova", FECHA_COTIZACION,
			NOMBRE, PATERNO, MATERNO, "folio_identificacion", "genero", 
			"numero_operacion", SUCURSAL, "numero_cuenta", "alias_cuenta", STATUS_BANCA, 
			TIPO_OPERACION,"divisa", MONTO_DIVISA,"monto_mxn","tipo_cambio", FECHA_LIMITE, "hora_cotizacion","vigencia", 
			"tipoBeneficioRemesas", "montoBeneficioRemesas");

	
	private BitacoraServiceImpl bitacora;
	
	/**
	 * Constructor de la clase.
	 *
	 * @param bitacora - Servicio Bitacora
	 */
	@Autowired
	public CotizacionesDao(BitacoraServiceImpl bitacora) {
		super();
		factoryDB = FactoryMongoDB.getInstance();
		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		coleccionCotizaciones = middlewareBD.getCollection(PropBD.getCotizacionesApp());
		
		catalogosDivisasBD = factoryDB.getMongoClient().getDatabase(PropBDCatalogos.getCatalogosBD());
		coleccionCatEstatusCotizacion = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatEstatusCotizacion());
		this.bitacora = bitacora;
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 * @param bitacora - Servicio Bitacora
	 */
	public CotizacionesDao(MongoCollection<Document> coleccion, BitacoraServiceImpl bitacora) {
		super();

		this.coleccionCotizaciones = coleccion;
		this.coleccionCatEstatusCotizacion = coleccion;
		this.bitacora = bitacora;
	}
	
	
	/**
	 * Count Operaciones App.
	 *
	 * @param filtro - Filtro de consulta
	 * @return Numero de operaciones
	 */
	public long countOperacionesApp(Document filtro) {
		
		return coleccionCotizaciones.countDocuments(filtro);
	}
	
	
	/**
	 * Consulta Operaciones App.
	 *
	 * @param isDescarga - Bandera que indica si es descarga de informacion
	 * @param filtro - Filtro de consulta
	 * @return Lista de operaciones
	 */
	public List<Document> consultaOperacionesApp(Document filtro, boolean isDescarga) {

		List<Document> operaciones = new ArrayList<>();
		
		try {
					
			if (!isDescarga) {

				operaciones = coleccionCotizaciones
						.aggregate(Arrays.asList(
												 match(filtro),
												 sort(descending(FECHA_COTIZACION)),
												 project(fields(excludeId(), 
														 		include(FIELDS_CONSULTA), 
														 		computed(TIPO_OPERACION, new Document(COND,
														 								   new Document("if", new Document("$eq", Arrays.asList("$tipo_operacion", "412")))
														 								   	    .append("then", "COMPRA ABONO CUENTA").append("else", "VENTA CARGO CUENTA"))))),
												 limit(Constant.INT_50))).into(new ArrayList<Document>());				
			} 
			else {
				
				operaciones = coleccionCotizaciones.aggregate(Arrays.asList(
						match(filtro),
						sort(ascending(FECHA_COTIZACION)),
						project(fields(excludeId(), 
								include(FIELDS_DESCARGA),
								computed("id_cliente_unico", new Document("$concat",
																			Arrays.asList("$id_cliente_unico.pais", "-", "$id_cliente_unico.canal", "-",
																						  "$id_cliente_unico.sucursal", "-", "$id_cliente_unico.folio"))),
								computed("numero_retencion", new Document(IFNULL, Arrays.asList("$numero_retencion", "  "))),
								computed("latitud", new Document(IFNULL, Arrays.asList("$latitud", "0"))),
								computed("longitud", new Document(IFNULL, Arrays.asList("$longitud", "0"))),
								computed("fecha_redencion", new Document(COND,
																		 new Document("if", new Document("$eq", Arrays.asList(ESTATUS_BANCA, "Entrega Exitosa")))
																			  .append("then", "$fecha_actualizacion").append("else", ""))),
								computed(FECHA_ACTUALIZACION, new Document(COND,
																			 new Document("if", new Document("$eq", Arrays.asList(ESTATUS_BANCA, "Vigente")))
																			 	  .append("then", "$fecha_cotizacion").append("else", "$fecha_actualizacion"))),
								computed("programa", new Document("$switch", 
																new Document("branches", Arrays.asList(
																			new Document("case", new Document("$eq", Arrays.asList(ESTATUS_BANCA, "Entrega Exitosa")))
																				 .append("then", "GA10"),
																			new Document("case", new Document("$eq", Arrays.asList(ESTATUS_BANCA, "Vigente")))
																				 .append("then", "MDW"),
																			new Document("case", new Document("$eq", Arrays.asList(ESTATUS_BANCA, "Cancelada")))
																				 .append("then", "MDW"),
																			new Document("case", new Document("$eq", Arrays.asList(ESTATUS_BANCA, "Vencida")))
																				 .append("then", "BATCH")))
																			     .append("default", "MDW"))),
								computed("isEntregaDomicilio", new Document(COND, 
															   new Document("if", new Document("$not", Arrays.asList("$entrega")))
															        .append("then", false).append("else", true))),
								computed("conductor.nombre", new Document(IFNULL, Arrays.asList("$entrega.conductor.nombre", " "))),
								computed("conductor.apellidoPaterno", new Document(IFNULL, Arrays.asList(CONDUCTOR+"apellidoPaterno", " "))),
								computed("conductor.apellidoMaterno", new Document(IFNULL, Arrays.asList(CONDUCTOR+"apellidoMaterno", " "))),
								computed("conductor.fechaNacimiento", new Document(IFNULL, Arrays.asList(CONDUCTOR+"fechaNacimiento", " "))),
								computed("conductor.numeroEmpleado", new Document(IFNULL, Arrays.asList(CONDUCTOR+"numeroEmpleado", " "))),
								computed("conductor.correoElectronico",new Document(IFNULL,Arrays.asList(CONDUCTOR+"correoElectronico", " "))),
								computed("conductor.numeroTelefono", new Document(IFNULL, Arrays.asList(CONDUCTOR+"numeroTelefono", " "))),
								computed("entrega.calle", new Document(IFNULL, Arrays.asList("$entrega.calle", " "))),
								computed("entrega.numeroExterior", new Document(IFNULL, Arrays.asList("$entrega.numeroExterior", " "))),
								computed("entrega.numeroInterior", new Document(IFNULL, Arrays.asList("$entrega.numeroInterior", " "))),
								computed("entrega.colonia", new Document(IFNULL, Arrays.asList("$entrega.colonia", " "))),
								computed("entrega.codigoPostal", new Document(IFNULL, Arrays.asList("$entrega.codigoPostal", " "))),
								computed("entrega.latitud", new Document(IFNULL, Arrays.asList("$entrega.latitud", " "))),
								computed("entrega.longitud", new Document(IFNULL, Arrays.asList("$entrega.longitud", " "))),
								computed("entrega.descripcionEstatus", new Document(IFNULL, 
																	   Arrays.asList("$entrega.descripcionEstatus", " "))),								
								computed("isRecompra", new Document(COND, 
															   new Document("if", new Document("$not", Arrays.asList("$recompra")))
															        .append("then", false).append("else", true))),
								computed("recompra", new Document(IFNULL, Arrays.asList("$recompra", " ")))
						
						)))).allowDiskUse(true).into(new ArrayList<Document>());
			}

		} 
		catch (MongoException e) {
			LogApp.excepcion(e, "Ocurrió un error al consultar Operaciones por App");
		}
		
		return operaciones;
	}
	
	
	/**
	 * Consulta Incidencias App.
	 *
	 * @return Lista de incidencias
	 */
	public List<Document> consultaIncidenciasApp() {

		List<Document> reporteIncidencias = new ArrayList<>();
		
		try {
			
			reporteIncidencias = coleccionCotizaciones.find(and( eq(STATUS, "PENDIENTE"), 
															      lt(FECHA_LIMITE, DateUtils.getDateFormat(Constant.FORMATOFECHACORTA))))					
													   .sort(ascending(FECHA_COTIZACION))
													   .projection(fields(computed("icu", "$icu"), computed("idCliente", "$id_cliente"), 
															   computed("idClienteAlnova", "$id_cliente_alnova"), computed(ID_CU, "$id_cliente_unico"), 
															   computed(NOMBRE, "$nombre"), computed("apellidoPaterno", "$apellido_paterno"), 
															   computed("apellidoMaterno", "$apellido_materno"), computed("estatus", ESTATUS_BANCA), 
															   computed("fecha", "$fecha_cotizacion"), computed("hora", "$hora_cotizacion"), 
															   computed("fechaLimite", "$fecha_limite"), computed("vigencia", "$vigencia"),
															   computed("tipoOperacion", "$tipo_operacion"),
															   computed("descTipoOperacion", new Document(COND,
									 								   new Document("if", new Document("$eq", Arrays.asList("$tipo_operacion", "412")))
									 								   	    .append("then", "COMPRA").append("else", "VENTA")))))
													   .into(new ArrayList<Document>());	
			
			for (Document doc : reporteIncidencias) {
				doc.put("idCotizacion", doc.getObjectId("_id").toHexString());
				doc.remove("_id");
				
				Document cu = (Document) doc.get(ID_CU);
				String cuPais = cu.getString("pais");
				String cuCanal = cu.getString("canal");
				String cuSucursal = cu.getString(SUCURSAL);
				String cuFolio = cu.getString("folio");
				
				doc.put(ID_CU, cuPais+"-"+cuCanal+"-"+cuSucursal+"-"+cuFolio);
			}

		} 
		catch (MongoException e) {
			LogApp.excepcion(e, "Ocurrió un error al consultar el reporte de incidencias app");
		}
		
		return reporteIncidencias;
	}
	

	/**
	 * Actualiza una cotizacion de Compra o Venta
	 * @param icu			- Identificador del Cliente en Banca Digital
	 * @param idCotizacion  - Id de Cotizacion
	 * @param tipoOperacion - 412-Compra, 413-Venta
	 * @param estatusNuevo  - Estatus con el que se actualiza la cotizacion
	 * @return Response     - Resultado de la operacion
	 */
	public boolean actualizaEstatusCotizacion(String idCotizacion, String tipoOperacion, String estatusNuevo) {
						
		try {
									
			Document configEstatus = consultaDetalleEstatusCotizacion(estatusNuevo, tipoOperacion);
								
			ArrayList<Bson> listCampos = new ArrayList<>();
			
			listCampos.add(set(STATUS, estatusNuevo));
			listCampos.add(set(FECHA_ACTUALIZACION, DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETAEXT)));
			listCampos.add(set(ID_PRODUCTO, configEstatus.get(ID_PRODUCTO)));
			listCampos.add(set(DESCRIPCION_MOVIMIENTO, configEstatus.get(DESCRIPCION_MOVIMIENTO)));
			listCampos.add(set(CONCEPTO, configEstatus.get(CONCEPTO)));
			listCampos.add(set(DESCRIPCION_OPERACION, configEstatus.get(DESCRIPCION_OPERACION)));
			listCampos.add(set(DESCRIPCION_OPERACION2, configEstatus.get(DESCRIPCION_OPERACION2)));
			listCampos.add(set(BANDERA_LIBRE, configEstatus.get(BANDERA_LIBRE)));
			listCampos.add(set(STATUS_BANCA, configEstatus.get(STATUS_BANCA)));
						
			UpdateResult resultado = coleccionCotizaciones.updateOne(and(eq("_id", new ObjectId(idCotizacion)), 
																		 eq(TIPO_OPERACION,tipoOperacion)), 
																	 combine(listCampos));
			
			if(resultado.getModifiedCount() > 0) {				
				bitacora.registrar(AccionBitacora.EDITAR, "Se actualizó el estatus de la cotización: " 
														 + idCotizacion +"|"+estatusNuevo);
			}
			else {
				return false;
			}
			
		}
		catch (MongoException e){
			LogApp.excepcion(e, "ERROR al actualizar la cotizacion: "+ idCotizacion);
			return false;
		}
		
		return true;
		
	}
	
	
	private Document consultaDetalleEstatusCotizacion(String estatus, String tipoOperacion) {

		String descTipoOperacion = "";
		
		if(tipoOperacion.equals(Constant.COMPRA_ABONO)) {
			descTipoOperacion = "compra";
		}
		else {
			descTipoOperacion = "venta";
		}

		return coleccionCatEstatusCotizacion.aggregate(Arrays.asList(
														match(eq("_id", estatus)),
														project( fields(excludeId(), 
																		computed(ID_PRODUCTO, "$"+ descTipoOperacion +".id_producto"),
																		computed(DESCRIPCION_MOVIMIENTO, "$"+ descTipoOperacion +".descripcion_movimiento"),
																		computed(CONCEPTO, "$"+ descTipoOperacion +".concepto"),
																		computed(DESCRIPCION_OPERACION, "$"+ descTipoOperacion +".descripcion_operacion"),
																		computed(DESCRIPCION_OPERACION2, "$"+ descTipoOperacion +".descripcion_operacion2"),
																		computed(BANDERA_LIBRE, "$"+ descTipoOperacion +".bandera_libre"),
																		computed(STATUS_BANCA, "$"+ descTipoOperacion +".estatus_banca")
																	   )
														)
										            )).first();	
	}
	
}
