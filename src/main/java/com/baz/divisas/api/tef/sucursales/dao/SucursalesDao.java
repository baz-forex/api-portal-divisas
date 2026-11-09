package com.baz.divisas.api.tef.sucursales.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Updates.set;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.general.model.SucursalTC;
import com.baz.divisas.api.tef.sucursales.dto.ReqActSucursal;
import com.baz.divisas.api.tef.sucursales.model.Divisa;
import com.baz.divisas.api.tef.sucursales.model.Divisas;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.api.tef.sucursales.model.TipoOperacion;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.util.DateUtils;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;

/**
 * SucursalesDao.
 */
@Repository
public class SucursalesDao {

	/** The factory BD. */
	private FactoryMongoDB factoryBD;

	/** The terminal financiero BD. */
	private MongoDatabase terminalFinancieroBD;

	/** The colleccion regiones. */
	private MongoCollection<Document> colleccionRegiones;

	/** The coleccion sucursales. */
	private MongoCollection<Sucursal> coleccionSucursales;
	
	/** The coleccion sucursales. */
	private MongoCollection<SucursalTC> coleccionSucursalesTC;

	/** The Constant TIPOOPERACION. */
	private static final String TIPOOPERACION = "$$limite.idTipoOperacion";

	/** The Constant ID. */
	private static final String ID = "_id";
	
	/** The Constant ASIGNADA. */
	private static final String ASIGNADA = "asignada";
	/** The Constant ASIGNADA. */
	private static final String ASIGNADAOLP = "asignadaOLP";
	
	/** The Constant ESTATUS. */
	private static final String ESTATUS = "nomEstatus";
	
	/** The Constant DIVISAS_ID. */
	private static final String DIVISAS_ID = "divisas._id";
	
	/** The Constant OLP_ID. */
	private static final String OLP_ID = "tcOlp._id";
	
	/** The Constant DIVISA_ID. */
	private static final String DIVISA_ID = "divisa._id";	
	
	/** The Constant DIVISAS_TIPOOPEID. */
	private static final String DIVISAS_TIPOOPEID = "divisas.tiposOperacion.idTipoOperacion";	
	
	/** The Constant DIVISAS_TIPOOPEID. */
	private static final String OLP_TIPOOPEID = "tcOlp.tiposOperacion.idTipoOperacion";
	
	/** The Constant TIPOSOPE_ID. */
	private static final String TIPOSOPE_ID = "tiposOperaciones.idTipoOperacion";		
	
	/** The Constant DIVISA_TCSPOT. */
	private static final String DIVISA_TCSPOT = "divisas.$[divisa].tiposOperacion.$[tiposOperaciones].tipoCambioSpot";	
	
	/** The Constant DIVISA_TCSPOT. */
	private static final String OLP_TCSPOT = "tcOlp.tiposOperacion.$[tiposOperaciones].tipoCambioSpot";
	
	/** The Constant DIVISA_TCSPREAD. */
	private static final String DIVISA_TCSPREAD = "divisas.$[divisa].tiposOperacion.$[tiposOperaciones].tipoCambioSpread";
	
	/** The Constant OLP_TCSPREAD. */
	private static final String OLP_TCSPREAD = "tcOlp.tiposOperacion.$[tiposOperaciones].tipoCambioSpread";
	
	/**
	 * condtructor de la clase.
	 */
	public SucursalesDao() {

		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryBD = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryBD.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());

		colleccionRegiones = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccion());

		coleccionSucursales = terminalFinancieroBD.getCollection(PropBD.getSucursales(), Sucursal.class)
				.withCodecRegistry(pojoCodecRegistry);
		
		coleccionSucursalesTC = terminalFinancieroBD.getCollection(PropBD.getSucursales(), SucursalTC.class)
				.withCodecRegistry(pojoCodecRegistry);
		
	}

	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion        - Mock Collection
	 * @param coleccionReg     - Mock Collection
	 * @param coleccionSucursalesTC the coleccion sucursales TC
	 */
	public SucursalesDao(MongoCollection<Sucursal> coleccion, MongoCollection<Document> coleccionReg,
			MongoCollection<SucursalTC> coleccionSucursalesTC) {
		super();
		this.coleccionSucursales = coleccion;
		this.colleccionRegiones = coleccionReg;		
		this.coleccionSucursalesTC = coleccionSucursalesTC;
	}

	/**
	 * Busqueda de sucursales asignadas.
	 *
	 * @param asignada - true/false
	 * @return Sucursales
	 */
	public List<Sucursal> findAsignadas(boolean asignada) {

		if (asignada) {
			return coleccionSucursales.aggregate(Arrays.asList(
					match(and(eq(ESTATUS, "ACTIVO"))),
					project(fields(
							include("_id", "nomCC", "nomMunicipio", "nomEstado", ESTATUS, "tipoCanal", "nomLocalidad",
									"latitud", "longitud", "divisas", ASIGNADA),
							computed("limites",
									new Document("$filter",
											new Document("input", "$limites").append("cond", new Document("$or",
													Arrays.asList(new Document("$eq", Arrays.asList(TIPOOPERACION, "410")),
															new Document("$eq", Arrays.asList(TIPOOPERACION, "411"))))).append("as", "limite"))))))).allowDiskUse(true)
					.into(new ArrayList<Sucursal>());
		}
		else {
			return Collections.emptyList();
		}
	}

	/**
	 * Busqueda sucursales de los segmentos en las regiones.
	 *
	 * @return List
	 */
	public List<Document> findSucursalesSegmentosRegion() {

		return colleccionRegiones
				.aggregate(Arrays.asList(match(eq("_id", new Document("$exists", true))),
						lookup("catSegmentos", "segmentos", "_id", "segmentos"), unwind("$segmentos"),
						unwind("$segmentos.sucursales"),
						group("$_id", Accumulators.first("descripcion", "$descripcion"),
								Accumulators.push("sucursalesSegmentos", "$segmentos.sucursales"))))
				.allowDiskUse(true).into(new ArrayList<Document>());
	}

	/**
	 * Metodo de consulta de una lista de sucursales.
	 *
	 * @param idSucursales - Lista de sucursales
	 * @return sucursales
	 */
	public List<Sucursal> findList(List<String> idSucursales) {

		return coleccionSucursales.find(and(in(ID, idSucursales), eq(ESTATUS, "ACTIVO"), eq(ASIGNADA, false)))
				.into(new ArrayList<Sucursal>());

	}

	/**
	 * Metodo de actualización de sucursales Asigando/Desasignado.
	 *
	 * @param idsSucursales the ids sucursales
	 * @param asignado      - true/false
	 * @return UpdateResult
	 */
	public UpdateResult updateSucursalesAsignadas(Collection<String> idsSucursales,
			boolean asignado) {

		UpdateResult result = coleccionSucursales.updateMany(in(ID, idsSucursales),
				set(ASIGNADA, asignado));

		return result;
	}

	/**
	 * Metodo de consulta de una lista de sucursales.
	 *
	 * @return sucursales
	 */
	public List<Sucursal> findTCSucursales() {

		return coleccionSucursales
				.aggregate(
						Arrays.asList(match(and(eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),
								eq("divisas", new Document("$exists", true).append("$not", new Document("$size", 0))
										.append("$ne", null)))),
								project(new Document("idSucursal", "$_id").append("fechaModificacion","$fechaUltimoCambio")
										.append("divisas", 1)),
								sort(Sorts.ascending(ID)
										)))
				.allowDiskUse(true).into(new ArrayList<Sucursal>());

	}
	
	/**
	 * Metodo de consulta de una lista de sucursales.
	 *
	 * @return sucursales
	 */
	public List<Sucursal> findTCSucursalesOlp() {

		return coleccionSucursales
				.aggregate(
						Arrays.asList(match(and(eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),
								eq("tcOlp", new Document("$exists", true)))),
								project(new Document("idSucursal", "$_id").append("fechaModificacion","$fechaUltimoCambio").append("fechaUltimoCambioOLP","$fechaUltimoCambioOLP")
										.append("tcOlp", 1)),
								sort(Sorts.ascending(ID)
										)))
				.allowDiskUse(true).into(new ArrayList<Sucursal>());

	}
	
	/**
	 * Update sucursal.
	 *
	 * @param sucursal the sucursal
	 * @param idDivisa the id divisa
	 * @param tipoOperacion the tipo operacion
	 * @param tcSpot the tc spot
	 * @param tcSpread the tc spread
	 * @return the update result
	 */
	public UpdateResult updateSucursalTCSpreadSpot(String sucursal,String idDivisa, 
			String tipoOperacion, Double tcSpot , Double tcSpread) {
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		Sucursal tipoCambioSucursal = coleccionSucursales.find(and(eq(ID, sucursal),
				eq(DIVISAS_ID, idDivisa),
				eq(DIVISAS_TIPOOPEID, tipoOperacion))).first();
		
		if(null != tipoCambioSucursal) {

			//Se actualiza el tipo de cambio
			result = coleccionSucursales.updateOne(
					and(eq(ID, sucursal),
							eq(DIVISAS_ID, idDivisa),
							eq(DIVISAS_TIPOOPEID, tipoOperacion)),
					combine(set(DIVISA_TCSPOT, tcSpot),
							set(DIVISA_TCSPREAD,tcSpread),
							set("fechaUltimoCambio",DateUtils.getDateFormat("YYYY-MM-dd HH:mm:ss"))),
					new UpdateOptions().arrayFilters(Arrays.asList(
							eq(DIVISA_ID, idDivisa), eq(TIPOSOPE_ID, tipoOperacion))));

		}
		else {
			TipoOperacion t410 = new TipoOperacion("410",0.0, 0.0);
			TipoOperacion t411 = new TipoOperacion("411",0.0, 0.0);
			TipoOperacion t412 = new TipoOperacion("412",0.0, 0.0);
			TipoOperacion t413 = new TipoOperacion("413",0.0, 0.0);

			if(tipoOperacion.equals("410")) {
				t410.setTipoCambioSpot(tcSpot);
				t410.setTipoCambioSpread(tcSpread);
			}
			else {
				t411.setTipoCambioSpot(tcSpot);
				t411.setTipoCambioSpread(tcSpread);
			}


			Divisas divisa = new Divisas();
			divisa.setId(idDivisa);
			divisa.setTiposOperacion(Arrays.asList(t410, t411, t412, t413));

			result = coleccionSucursales.updateOne(
					and(eq(ID, sucursal)),
					push("divisas",divisa));
		}		
		
		return result;
	}
	

	/**
	 * Update sucursal.
	 *
	 * @param sucursal the sucursal
	 * @param idDivisa the id divisa
	 * @param tipoOperacion the tipo operacion
	 * @param tcSpot the tc spot
	 * @param tcSpread the tc spread
	 * @return the update result
	 */
	public boolean updateSucursalTC(ReqActSucursal sucursal,Sucursal sucursalPrevia) {
		List<Divisas> divisas = new ArrayList<>();
	
		for(Divisa divisa : sucursal.getDivisas()) {
			Double compraAnterior = 0.0d;
			Double ventaAnterior = 0.0d;

			String divisaRegistrar = divisa.getIdDivisa(); 

			Divisas divisaExistente = sucursalPrevia.getDivisas().stream().filter( divisaNueva -> divisaRegistrar.equals(divisaNueva.getId())).findFirst().orElse(null);
			if(null == divisaExistente) {

				TipoOperacion t410 = new TipoOperacion("410",divisa.getTc().getCompra(),divisa.getSpread().getCompra());
				TipoOperacion t411 = new TipoOperacion("411",divisa.getTc().getVenta(),divisa.getSpread().getVenta());
				TipoOperacion t412 = new TipoOperacion("412",0.0, 0.0);
				TipoOperacion t413 = new TipoOperacion("413",0.0, 0.0);

				Divisas divisaNoExistente = new Divisas();
				divisaNoExistente.setId(divisaRegistrar);
				divisaNoExistente.setTiposOperacion(Arrays.asList(t410, t411, t412, t413));
				divisas.add(divisaNoExistente);
			}
			else {
				for(TipoOperacion tipoOperacion : divisaExistente.getTiposOperacion()) {

					if(tipoOperacion.getIdTipoOperacion().equals("410")){
						compraAnterior = tipoOperacion.getTipoCambioSpot();
					}

					if(tipoOperacion.getIdTipoOperacion().equals("411")){
						ventaAnterior = tipoOperacion.getTipoCambioSpot();
					}

				}
				BigDecimal diferenciaCompra = new BigDecimal(
								compraAnterior - divisa.getTc().getCompra()).setScale(2, RoundingMode.DOWN);


				if (diferenciaCompra.abs().compareTo(BigDecimal.valueOf(5.0)) > 0) {
					return false;
				}

				BigDecimal diferenciaVenta = new BigDecimal(
								ventaAnterior - divisa.getTc().getVenta()).setScale(2, RoundingMode.DOWN);

				if (diferenciaVenta.abs().compareTo(BigDecimal.valueOf(5.0)) > 0) {
					return false;
				}
				
				TipoOperacion t410 = new TipoOperacion("410",divisa.getTc().getCompra(),divisa.getSpread().getCompra());
				TipoOperacion t411 = new TipoOperacion("411",divisa.getTc().getVenta(),divisa.getSpread().getVenta());
				TipoOperacion t412 = new TipoOperacion("412",0.0, 0.0);
				TipoOperacion t413 = new TipoOperacion("413",0.0, 0.0);

				Divisas divisaActualizar = new Divisas();
				divisaActualizar.setId(divisaRegistrar);
				divisaActualizar.setTiposOperacion(Arrays.asList(t410, t411, t412, t413));
				divisas.add(divisaActualizar);

			}
		}

		coleccionSucursales.updateOne(eq(ID, sucursal.getIdsucursal()), 
				combine(
				set("divisas", divisas),
				set("fechaUltimoCambio",DateUtils.getDateFormat("YYYY-MM-dd HH:mm:ss"))),
				new UpdateOptions().upsert(true));
		
		return true;
	}

	/**
	 * Construye la operacion de actualizacion de TC + metadata de archivo para bulkWrite.
	 *
	 * Aplica la misma validacion que {@link #updateSucursalTC(ReqActSucursal, Sucursal)}:
	 * si la diferencia de compra o venta supera 5.0 devuelve {@code null} (indica error,
	 * la sucursal no se debe actualizar). En caso valido, combina en UNA sola operacion
	 * la actualizacion de divisas + fechaUltimoCambio + los campos de archivo
	 * (fusiona lo que antes eran dos updateOne).
	 *
	 * @param sucursal the sucursal
	 * @param sucursalPrevia the sucursal previa
	 * @return el WriteModel a ejecutar en bulk, o {@code null} si la validacion falla
	 */
	public WriteModel<Sucursal> construirUpdateTC(ReqActSucursal sucursal, Sucursal sucursalPrevia) {
		List<Divisas> divisas = new ArrayList<>();

		for (Divisa divisa : sucursal.getDivisas()) {
			Double compraAnterior = 0.0d;
			Double ventaAnterior = 0.0d;

			String divisaRegistrar = divisa.getIdDivisa();

			Divisas divisaExistente = sucursalPrevia.getDivisas().stream()
					.filter(divisaNueva -> divisaRegistrar.equals(divisaNueva.getId())).findFirst().orElse(null);

			if (null == divisaExistente) {
				divisas.add(construirDivisas(divisaRegistrar, divisa));
			}
			else {
				for (TipoOperacion tipoOperacion : divisaExistente.getTiposOperacion()) {
					if (tipoOperacion.getIdTipoOperacion().equals("410")) {
						compraAnterior = tipoOperacion.getTipoCambioSpot();
					}
					if (tipoOperacion.getIdTipoOperacion().equals("411")) {
						ventaAnterior = tipoOperacion.getTipoCambioSpot();
					}
				}

				BigDecimal diferenciaCompra = new BigDecimal(
						compraAnterior - divisa.getTc().getCompra()).setScale(2, RoundingMode.DOWN);
				if (diferenciaCompra.abs().compareTo(BigDecimal.valueOf(5.0)) > 0) {
					return null;
				}

				BigDecimal diferenciaVenta = new BigDecimal(
						ventaAnterior - divisa.getTc().getVenta()).setScale(2, RoundingMode.DOWN);
				if (diferenciaVenta.abs().compareTo(BigDecimal.valueOf(5.0)) > 0) {
					return null;
				}

				divisas.add(construirDivisas(divisaRegistrar, divisa));
			}
		}

		// Se fusiona la actualizacion de TC y los campos de archivo en una sola operacion
		Bson update = combine(
				set("divisas", divisas),
				set("fechaUltimoCambio", DateUtils.getDateFormat("YYYY-MM-dd HH:mm:ss")),
				set("regionSucursalArchivo", sucursal.getRegionSucursalArchivo()),
				set("nombreSucursalArchivo", sucursal.getNombreSucursalArchivo()),
				set("tipoSucursalArchivo", sucursal.getTipoSucursalArchivo()),
				set("municipioSucursalArchivo", sucursal.getMunicipioSucursalArchivo()),
				set("estadoSucursalArchivo", sucursal.getEstadoSucursalArchivo()),
				set("calleSucursalArchivo", sucursal.getCalleSucursalArchivo()),
				set("numeroSucursalArchivo", sucursal.getNumeroSucursalArchivo()),
				set("cpSucursalArchivo", sucursal.getCpSucursalArchivo()),
				set("numeroRegionArchivo", sucursal.getNumeroRegionArchivo()));

		return new UpdateOneModel<>(eq(ID, sucursal.getIdsucursal()), update,
				new UpdateOptions().upsert(true));
	}

	/**
	 * Construye la operacion de creacion de divisas (upsert) para bulkWrite.
	 *
	 * Equivalente a {@link #crearDivisas(ReqActSucursal)} pero devolviendo el WriteModel
	 * en lugar de ejecutar el updateOne.
	 *
	 * @param sucursal the sucursal
	 * @return el WriteModel a ejecutar en bulk, o {@code null} si no hay divisas
	 */
	public WriteModel<Sucursal> construirCrearDivisas(ReqActSucursal sucursal) {
		List<Divisas> divisas = new ArrayList<>();

		for (Divisa divisa : sucursal.getDivisas()) {
			divisas.add(construirDivisas(divisa.getIdDivisa(), divisa));
		}

		if (divisas.isEmpty()) {
			return null;
		}

		Bson update = combine(
				set("divisas", divisas),
				setOnInsert(ID, sucursal.getIdsucursal()),
				setOnInsert("asignada", false),
				setOnInsert("idEstatus", 1),
				setOnInsert("latitud", null),
				setOnInsert("longitud", null),
				setOnInsert("nomEstatus", "ACTIVO"),
				setOnInsert("nomPais", "MEXICO"),
				setOnInsert("sucursal", Integer.valueOf(sucursal.getIdsucursal())),
				setOnInsert("fechaActualizacion", LocalDateTime.now()),
				setOnInsert("idNumeroEconomico", sucursal.getIdsucursal()));

		return new UpdateOneModel<>(eq(ID, sucursal.getIdsucursal()), update,
				new UpdateOptions().upsert(true));
	}

	/**
	 * Construye el objeto Divisas con los 4 tipos de operacion estandar.
	 *
	 * @param idDivisa the id divisa
	 * @param divisa the divisa origen
	 * @return the divisas
	 */
	private static Divisas construirDivisas(String idDivisa, Divisa divisa) {
		TipoOperacion t410 = new TipoOperacion("410", divisa.getTc().getCompra(), divisa.getSpread().getCompra());
		TipoOperacion t411 = new TipoOperacion("411", divisa.getTc().getVenta(), divisa.getSpread().getVenta());
		TipoOperacion t412 = new TipoOperacion("412", 0.0, 0.0);
		TipoOperacion t413 = new TipoOperacion("413", 0.0, 0.0);

		Divisas d = new Divisas();
		d.setId(idDivisa);
		d.setTiposOperacion(Arrays.asList(t410, t411, t412, t413));
		return d;
	}

	/**
	 * Ejecuta un lote de operaciones de escritura en una sola llamada a BD.
	 *
	 * Usa bulkWrite no ordenado: MongoDB aplica las operaciones en paralelo del lado
	 * del servidor y no se detiene ante un error individual. Reduce N round-trips a 1.
	 *
	 * @param operaciones the operaciones
	 */
	public void ejecutarBulkSucursales(List<WriteModel<Sucursal>> operaciones) {
		if (null == operaciones || operaciones.isEmpty()) {
			return;
		}
		coleccionSucursales.bulkWrite(operaciones, new BulkWriteOptions().ordered(false));
	}
	
	/**
	 * Update sucursal OLP.
	 *
	 * @param sucursal the sucursal
	 * @param idDivisa the id divisa
	 * @param tipoOperacion the tipo operacion
	 * @param tcSpot the tc spot
	 * @param tcSpread the tc spread
	 * @return the update result
	 */
	public UpdateResult updateSucursalTCSpreadSpotOlp(String sucursal,String idDivisa, 
			String tipoOperacion, Double tcSpot , Double tcSpread) {

		return coleccionSucursales.updateOne(
				and(eq(ID, sucursal),
						eq(OLP_ID, idDivisa),
						eq(OLP_TIPOOPEID, tipoOperacion)),
				combine(set(OLP_TCSPOT, tcSpot),
						set(OLP_TCSPREAD,tcSpread),
						set("fechaUltimoCambioOLP",DateUtils.getDateFormat("YYYY-MM-dd HH:mm:ss"))),
				new UpdateOptions().arrayFilters(Arrays.asList(eq(TIPOSOPE_ID, tipoOperacion))));

	}
	
	
	/**
	 * Update sucursal.
	 *
	 * @param req the req
	 * @return the update result
	 */
	public UpdateResult updateSucursal(ReqActSucursal req) {
		return coleccionSucursales.updateOne(eq(ID, req.getIdsucursal()),
				combine(set("regionSucursalArchivo", req.getRegionSucursalArchivo()),
						set("nombreSucursalArchivo",req.getNombreSucursalArchivo() ),
						set("tipoSucursalArchivo",req.getTipoSucursalArchivo() ),
						set("municipioSucursalArchivo",req.getMunicipioSucursalArchivo() ),
						set("estadoSucursalArchivo", req.getEstadoSucursalArchivo()),
						set("calleSucursalArchivo", req.getCalleSucursalArchivo()),
						set("numeroSucursalArchivo", req.getNumeroSucursalArchivo()),
						set("cpSucursalArchivo", req.getCpSucursalArchivo()),
						set("numeroRegionArchivo", req.getNumeroRegionArchivo())						
						)
				);		
	}

	/**
	 * Consulta.
	 *
	 * @return the iterable
	 */
	public List<Sucursal> consulta() {

		return coleccionSucursales
				.aggregate(
						Arrays.asList(match(and(eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),
								eq("divisas",
										new Document("$exists", true).append("$not", new Document("$size", 0))
												.append("$ne", null))))))
				.allowDiskUse(true).into(new ArrayList<Sucursal>());

	}
	
	/**
	 * Consulta.
	 *
	 * @param idDivisa the id divisa
	 * @return the iterable
	 */
	public List<SucursalTC> consultaSucursalesTC(String idDivisa) {
		return coleccionSucursalesTC
				.aggregate(
						Arrays.asList(
								match(and(eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),eq("divisas", new Document("$exists", true).append("$not", new Document("$size", 0)).append("$ne", null)))),
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
				.allowDiskUse(true).into(new ArrayList<SucursalTC>());
	}
	
	/**
	 * Crear divisas.
	 *
	 * @param sucursal the sucursal
	 */
	public void crearDivisas(ReqActSucursal sucursal) {
		List<Divisas> divisas = new ArrayList<>();
		
		for(Divisa divisa : sucursal.getDivisas()) {
			
			TipoOperacion t410 = new TipoOperacion("410",divisa.getTc().getCompra(),divisa.getSpread().getCompra());
			TipoOperacion t411 = new TipoOperacion("411",divisa.getTc().getVenta(),divisa.getSpread().getVenta());
			TipoOperacion t412 = new TipoOperacion("412",0.0, 0.0);
			TipoOperacion t413 = new TipoOperacion("413",0.0, 0.0);

			Divisas divisaNoExistente = new Divisas();
			divisaNoExistente.setId(divisa.getIdDivisa());
			divisaNoExistente.setTiposOperacion(Arrays.asList(t410, t411, t412, t413));
			divisas.add(divisaNoExistente);
		}
		
		if(!divisas.isEmpty() && divisas.size() > 0) { 
			coleccionSucursales.updateOne(eq(ID, sucursal.getIdsucursal()), combine(
					set("divisas", divisas),
					setOnInsert(ID, sucursal.getIdsucursal()), 
					setOnInsert("asignada", false),
					setOnInsert("idEstatus", 1),
					setOnInsert("latitud", null),
					setOnInsert("longitud", null),
					setOnInsert("nomEstatus", "ACTIVO"),
					setOnInsert("nomPais", "MEXICO"),
					setOnInsert("sucursal", Integer.valueOf(sucursal.getIdsucursal())),
					setOnInsert("fechaActualizacion", LocalDateTime.now()),
					setOnInsert("idNumeroEconomico", sucursal.getIdsucursal())
					),
					new UpdateOptions().upsert(true));
		}
	}

	/**
	 * Consulta.
	 *
	 * @param sucursal the sucursal
	 * @return the iterable
	 */
	public Sucursal consultaSucursal(String sucursal) {
		
		return coleccionSucursales
				.aggregate(
						Arrays.asList(match(and(eq(ID,sucursal),eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),
								eq("divisas", new Document("$exists", true).append("$not", new Document("$size", 0))
										.append("$ne", null))))))
				.allowDiskUse(true).first();
	}

	/**
	 * Consulta un lote de sucursales en una sola llamada a BD.
	 *
	 * Aplica el mismo filtro que {@link #consultaSucursal(String)} pero para
	 * multiples ids, evitando el problema de consultas N+1.
	 *
	 * @param sucursales the ids de sucursales
	 * @return the list de sucursales que cumplen el filtro
	 */
	public List<Sucursal> consultaSucursales(Collection<String> sucursales) {

		if (null == sucursales || sucursales.isEmpty()) {
			return new ArrayList<>();
		}

		return coleccionSucursales
				.aggregate(
						Arrays.asList(match(and(in(ID, sucursales), eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),
								eq("divisas", new Document("$exists", true).append("$not", new Document("$size", 0))
										.append("$ne", null))))))
				.allowDiskUse(true).into(new ArrayList<>());
	}

	/**
	 * Consulta.
	 *
	 * @param sucursal the sucursal
	 * @return the iterable
	 */
	public boolean consultaSucursalOlp(String sucursal) {
		
		boolean existe = false;
		
		Sucursal sucuDoc = coleccionSucursales
				.aggregate(
						Arrays.asList(match(and(eq(ID,sucursal),eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),
								eq("tcOlp", new Document("$exists", true))))))
				.allowDiskUse(true).first();
		
		if(null != sucuDoc) {
			existe = true;
		}
		return existe;
	}
	/**
	 * Consulta sucursales OLP.
	 *
	 * @param idDivisa the id divisa
	 * @return the list
	 */
	public List<SucursalTC> consultaSucursalesOlp(String idDivisa) {
		return coleccionSucursalesTC
				.aggregate(
						Arrays.asList(
								match(and(eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),eq("tcOlp", new Document("$exists", true)))),
								project (
										fields(	
												computed("divisaCompra",new Document("$filter",
														new Document("input", "$tcOlp.tiposOperacion").append("as", "tipo").append("cond", new Document("$eq",Arrays.asList("$$tipo.idTipoOperacion" ,"410")))
													)											
										),
												computed("divisaVenta",new Document("$filter",
														new Document("input", "$tcOlp.tiposOperacion").append("as", "tipo").append("cond", new Document("$eq",Arrays.asList("$$tipo.idTipoOperacion" ,"411")))
													)
								))),
								unwind("$divisaCompra"),
								unwind("$divisaVenta")))
				.allowDiskUse(true).into(new ArrayList<SucursalTC>());
	}

	/**
	 * Consulta OLP.
	 *
	 * @return the iterable
	 */
	public Iterable<Sucursal> consultaOlp() {
		return coleccionSucursales
				.aggregate(
						Arrays.asList(match(and(eq("idEstatus", 1), eq(ESTATUS, "ACTIVO"),
								eq("tcOlp", new Document("$exists", true))))))
				.allowDiskUse(true).into(new ArrayList<Sucursal>());
	}

	/**
	 * Crear divisas OLP.
	 *
	 * @param sucursal the sucursal
	 */
	public void crearDivisasOlp(String sucursal) {
		TipoOperacion t410 = new TipoOperacion("410",0.0, 0.0);
		TipoOperacion t411 = new TipoOperacion("411",0.0, 0.0);

		Divisas divisaOLP = new Divisas();
		divisaOLP.setId("OLP");
		divisaOLP.setTiposOperacion(Arrays.asList(t410, t411));	
		
		coleccionSucursales.updateOne(eq(ID, sucursal),
				set("tcOlp", divisaOLP));
		
	}

	/**
	 * Consulta en lote que sucursales ya tienen tcOlp.
	 *
	 * Reemplaza las N llamadas a {@link #consultaSucursalOlp(String)} por una sola,
	 * evitando el problema de consultas N+1.
	 *
	 * @param sucursales the ids de sucursales
	 * @return el conjunto de ids que ya tienen tcOlp (busqueda O(1))
	 */
	public java.util.Set<String> consultaSucursalesConOlp(Collection<String> sucursales) {

		if (null == sucursales || sucursales.isEmpty()) {
			return new java.util.HashSet<>();
		}

		List<Sucursal> docs = coleccionSucursales
				.aggregate(Arrays.asList(match(and(in(ID, sucursales), eq("idEstatus", 1),
						eq(ESTATUS, "ACTIVO"), eq("tcOlp", new Document("$exists", true))))))
				.allowDiskUse(true).into(new ArrayList<Sucursal>());

		java.util.Set<String> ids = new java.util.HashSet<>();
		for (Sucursal s : docs) {
			ids.add(s.getId());
		}
		return ids;
	}

	/**
	 * Construye la operacion de actualizacion de TC OLP (compra + venta) para bulkWrite.
	 *
	 * Fusiona en una sola operacion lo que antes eran hasta 3 escrituras:
	 * - Si la sucursal NO tiene tcOlp: upsert que crea el tcOlp completo con compra y venta.
	 * - Si ya tiene tcOlp: updateOne que actualiza los elementos 410 y 411 con dos arrayFilters.
	 *
	 * @param sucursal the sucursal
	 * @param existeOlp indica si la sucursal ya tiene tcOlp
	 * @return el WriteModel a ejecutar en bulk
	 */
	public WriteModel<Sucursal> construirUpdateOlp(ReqActSucursal sucursal, boolean existeOlp) {

		Double compraSpot = sucursal.getTcOlp().getTc().getCompra();
		Double compraSpread = sucursal.getTcOlp().getSpread().getCompra();
		Double ventaSpot = sucursal.getTcOlp().getTc().getVenta();
		Double ventaSpread = sucursal.getTcOlp().getSpread().getVenta();
		String fecha = DateUtils.getDateFormat("YYYY-MM-dd HH:mm:ss");

		if (!existeOlp) {
			// La sucursal no tiene tcOlp: se crea completo en una sola operacion (upsert)
			TipoOperacion t410 = new TipoOperacion("410", compraSpot, compraSpread);
			TipoOperacion t411 = new TipoOperacion("411", ventaSpot, ventaSpread);

			Divisas divisaOLP = new Divisas();
			divisaOLP.setId(sucursal.getTcOlp().getIdDivisa());
			divisaOLP.setTiposOperacion(Arrays.asList(t410, t411));

			Bson updateNuevo = combine(
					set("tcOlp", divisaOLP),
					set("fechaUltimoCambioOLP", fecha));

			return new UpdateOneModel<>(eq(ID, sucursal.getIdsucursal()), updateNuevo,
					new UpdateOptions().upsert(true));
		}

		// La sucursal ya tiene tcOlp: se actualizan compra (410) y venta (411) en una sola operacion
		Bson update = combine(
				set("tcOlp.tiposOperacion.$[compra].tipoCambioSpot", compraSpot),
				set("tcOlp.tiposOperacion.$[compra].tipoCambioSpread", compraSpread),
				set("tcOlp.tiposOperacion.$[venta].tipoCambioSpot", ventaSpot),
				set("tcOlp.tiposOperacion.$[venta].tipoCambioSpread", ventaSpread),
				set("fechaUltimoCambioOLP", fecha));

		UpdateOptions opciones = new UpdateOptions().arrayFilters(Arrays.asList(
				eq("compra.idTipoOperacion", "410"),
				eq("venta.idTipoOperacion", "411")));

		return new UpdateOneModel<>(
				and(eq(ID, sucursal.getIdsucursal()), eq(OLP_ID, sucursal.getTcOlp().getIdDivisa())),
				update, opciones);
	}

	/**
	 * Find list olp.
	 *
	 * @param idSucursales the id sucursales
	 * @return the list
	 */
	public List<Sucursal> findListOlp(List<String> idSucursales) {
		
		List<Sucursal> sucursalesOlp =  coleccionSucursales.find(and(in(ID, idSucursales), eq(ESTATUS, "ACTIVO"), eq(ASIGNADAOLP, false)))
				.into(new ArrayList<Sucursal>());
		
		if(sucursalesOlp.isEmpty()) {
			sucursalesOlp =  coleccionSucursales.find(and(in(ID, idSucursales), eq(ESTATUS, "ACTIVO")))
					.into(new ArrayList<Sucursal>());
		}
		
		return sucursalesOlp;
	}

	/**
	 * Update sucursales asignadas olp.
	 *
	 * @param idsSucursales the ids sucursales
	 * @param asignado the asignado
	 * @return the update result
	 */
	public UpdateResult updateSucursalesAsignadasOlp(Collection<String> idsSucursales,
			boolean asignado) {

		UpdateResult result = coleccionSucursales.updateMany(in(ID, idsSucursales),
			 set(ASIGNADAOLP, asignado));

		return result;
		
	}
}
