package com.baz.divisas.api.tef.sucursales.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.tef.general.model.SucursalTC;
import com.baz.divisas.api.tef.general.model.TC;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.sucursales.dto.ReqActSucursal;
import com.baz.divisas.api.tef.sucursales.model.Divisa;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SucursalesDaoTest {

	@InjectMocks 
	private SucursalesDao sucursalesDao;

	@Mock 
	private MongoCollection<Sucursal> mockCollectionSucursales;
	@Mock
	private FindIterable<Sucursal> mockFindIterableSucursales;
	@Mock
	private AggregateIterable<Sucursal> mockAggIterableSucursales;
	@Mock
	private UpdateResult mockUpdateResult;
	@Mock
	private PromoEspacioServiceImpl promoEspacioService;

	
	@Test
	public void testFindAsignadas() {

		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		List<Sucursal> resultado = sucursalesDao.findAsignadas(true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testFindSucursalesSegmentosRegion() {

		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<Document>());

		List<Document> resultado = sucursalesDao.findSucursalesSegmentosRegion();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testFindList() {

		when(mockCollectionSucursales.find(isA(Bson.class))).thenReturn(mockFindIterableSucursales);
		when(mockFindIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		List<Sucursal> resultado = sucursalesDao.findList(Arrays.asList());
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testUpdateSucursalesAsignadas() {

		when(mockCollectionSucursales.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);
		UpdateResult resultado = sucursalesDao.updateSucursalesAsignadas(Arrays.asList("0172"), true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testFindTCSucursales() {

		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		List<Sucursal> resultado = sucursalesDao.findTCSucursales();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testCrearDivisas() {
		when(mockCollectionSucursales.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);
		ReqActSucursal req = new ReqActSucursal();
		
		List<Divisa> divisas = new ArrayList<>();
		Divisa n = new Divisa();
		n.setIdDivisa("USD");
		
		TC tc = new TC();		
		tc.setCompra(15.3);
		tc.setVenta(-0.05);
		
		TC spread = new TC();
		spread.setCompra(15.3);
		spread.setVenta(-0.05);
		
		n.setSpread(spread);
		n.setTc(tc);		
		divisas.add(n);
		
		req.setDivisas(divisas);
		req.setIdsucursal("0172");
		
		sucursalesDao.crearDivisas(req);
	}
	
	@Test
	public void testFindTCSucursalesOlp() {

		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		List<Sucursal> resultado = sucursalesDao.findTCSucursalesOlp();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testUpdateSucursalTCSpreadSpot() {
		UpdateResult update = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(mockCollectionSucursales.find(isA(Bson.class))).thenReturn(mockFindIterableSucursales);
		when(mockFindIterableSucursales.first()).thenReturn(new Sucursal());
		when(mockCollectionSucursales.updateOne(isA(Bson.class), isA(Bson.class) ,isA(UpdateOptions.class))).thenReturn(update);
		UpdateResult resultado = sucursalesDao.updateSucursalTCSpreadSpot("0172","USD","410",210.1,213.2);
		assertThat(resultado).isNotNull();
		
		when(mockCollectionSucursales.find(isA(Bson.class))).thenReturn(mockFindIterableSucursales);
		when(mockFindIterableSucursales.first()).thenReturn(null);
		when(mockCollectionSucursales.updateOne(isA(Bson.class), isA(Bson.class) ,isA(UpdateOptions.class))).thenReturn(update);
		resultado = sucursalesDao.updateSucursalTCSpreadSpot("0172","USD","410",210.1,213.2);
		assertThat(resultado).isNull();
		
		resultado = sucursalesDao.updateSucursalTCSpreadSpot("0172","USD","411",210.1,213.2);
		assertThat(resultado).isNull();
	}
	
	@Test
	public void testUpdateSucursalTCSpreadSpotOlp() {
		when(mockCollectionSucursales.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);
		UpdateResult resultado = sucursalesDao.updateSucursalTCSpreadSpotOlp("0172","USD","410",210.1,213.2);
		assertThat(resultado).isNull();
	}
	
	@Test
	public void testUpdateSucursal() {
		ReqActSucursal req = new ReqActSucursal();
		when(mockCollectionSucursales.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);
		UpdateResult resultado = sucursalesDao.updateSucursal(req);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testConsulta() {

		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		List<Sucursal> resultado = sucursalesDao.consulta();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testConsultaSucursalesTC() {
		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<SucursalTC>());

		List<SucursalTC> resultado = sucursalesDao.consultaSucursalesTC("USD");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testConsultaSucursal() {
		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.first()).thenReturn(new Sucursal());

		Sucursal resultado = sucursalesDao.consultaSucursal("0172");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testConsultaSucursalOlp() {
		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.first()).thenReturn(new Sucursal());

		boolean resultado = sucursalesDao.consultaSucursalOlp("0172");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testConsultaSucursalesOlp() {
		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<SucursalTC>());

		List<SucursalTC> resultado = sucursalesDao.consultaSucursalesOlp("OLP");
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testConsultaOlp() {

		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		Iterable<Sucursal> resultado = sucursalesDao.consultaOlp();
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testFindListOlp() {
		// Primer find devuelve vacio -> ejecuta el segundo find (fallback)
		when(mockCollectionSucursales.find(isA(Bson.class))).thenReturn(mockFindIterableSucursales);
		when(mockFindIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		List<Sucursal> resultado = sucursalesDao.findListOlp(Arrays.asList());
		assertThat(resultado).isNotNull();
	}

	@Test
	public void testFindListOlpConResultado() {
		// Primer find devuelve una sucursal (lista no vacia) -> NO ejecuta el segundo find
		Sucursal s = new Sucursal();
		s.setId("0172");
		when(mockCollectionSucursales.find(isA(Bson.class))).thenReturn(mockFindIterableSucursales);
		when(mockFindIterableSucursales.into(any())).thenReturn(new ArrayList<>(Arrays.asList(s)));

		List<Sucursal> resultado = sucursalesDao.findListOlp(Arrays.asList("0172"));
		assertThat(resultado).isNotEmpty();
	}
	
	@Test
	public void testUpdateSucursalesAsignadasOlp() {

		when(mockCollectionSucursales.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);
		UpdateResult resultado = sucursalesDao.updateSucursalesAsignadasOlp(Arrays.asList("0172"), true);
		assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testCrearDivisasOlp() {
		when(mockCollectionSucursales.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(mockUpdateResult);
		sucursalesDao.crearDivisasOlp("0172");
	}

	// ======================= Casos faltantes para 100% =======================

	@Test
	public void testFindAsignadasFalse() {
		// asignada = false -> retorna lista vacia sin consultar BD
		List<Sucursal> resultado = sucursalesDao.findAsignadas(false);
		assertThat(resultado).isEmpty();
	}

	@Test
	public void testCrearDivisasSinDivisas() {
		// req sin divisas -> no ejecuta updateOne (lista vacia)
		ReqActSucursal req = new ReqActSucursal();
		req.setIdsucursal("0172");
		req.setDivisas(new ArrayList<>());
		sucursalesDao.crearDivisas(req);
	}

	@Test
	public void testConsultaSucursalOlpNoExiste() {
		// first() retorna null -> existe = false
		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.first()).thenReturn(null);

		boolean resultado = sucursalesDao.consultaSucursalOlp("9999");
		assertThat(resultado).isFalse();
	}

	// ---------- updateSucursalTC (boolean) ----------

	private ReqActSucursal reqConDivisa(String idDivisa, double compra, double venta) {
		ReqActSucursal req = new ReqActSucursal();
		req.setIdsucursal("0172");

		Divisa divisa = new Divisa();
		divisa.setIdDivisa(idDivisa);

		TC tc = new TC();
		tc.setCompra(compra);
		tc.setVenta(venta);
		divisa.setTc(tc);

		TC spread = new TC();
		spread.setCompra(0.10);
		spread.setVenta(0.20);
		divisa.setSpread(spread);

		List<Divisa> divisas = new ArrayList<>();
		divisas.add(divisa);
		req.setDivisas(divisas);
		return req;
	}

	private Sucursal sucursalConDivisaExistente(String idDivisa, double compraSpot, double ventaSpot) {
		Sucursal sucursal = new Sucursal();
		com.baz.divisas.api.tef.sucursales.model.Divisas d = new com.baz.divisas.api.tef.sucursales.model.Divisas();
		d.setId(idDivisa);
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion t410 =
				new com.baz.divisas.api.tef.sucursales.model.TipoOperacion("410", compraSpot, 0.0);
		com.baz.divisas.api.tef.sucursales.model.TipoOperacion t411 =
				new com.baz.divisas.api.tef.sucursales.model.TipoOperacion("411", ventaSpot, 0.0);
		d.setTiposOperacion(Arrays.asList(t410, t411));
		sucursal.setDivisas(new ArrayList<>(Arrays.asList(d)));
		return sucursal;
	}

	@Test
	public void testUpdateSucursalTCDivisaNueva() {
		// divisa no existe en la sucursal previa -> rama de alta
		when(mockCollectionSucursales.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class)))
				.thenReturn(mockUpdateResult);

		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		Sucursal previa = new Sucursal();
		previa.setDivisas(new ArrayList<>());

		boolean resultado = sucursalesDao.updateSucursalTC(req, previa);
		assertThat(resultado).isTrue();
	}

	@Test
	public void testUpdateSucursalTCDivisaExistenteValida() {
		// divisa existe, diferencias dentro del limite -> rama de actualizacion
		when(mockCollectionSucursales.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class)))
				.thenReturn(mockUpdateResult);

		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		Sucursal previa = sucursalConDivisaExistente("USD", 18.52, 19.48);

		boolean resultado = sucursalesDao.updateSucursalTC(req, previa);
		assertThat(resultado).isTrue();
	}

	@Test
	public void testUpdateSucursalTCDiferenciaCompraExcedida() {
		// diferencia de compra > 5.0 -> retorna false
		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		Sucursal previa = sucursalConDivisaExistente("USD", 99.00, 19.48);

		boolean resultado = sucursalesDao.updateSucursalTC(req, previa);
		assertThat(resultado).isFalse();
	}

	@Test
	public void testUpdateSucursalTCDiferenciaVentaExcedida() {
		// diferencia de venta > 5.0 -> retorna false
		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		Sucursal previa = sucursalConDivisaExistente("USD", 18.52, 99.00);

		boolean resultado = sucursalesDao.updateSucursalTC(req, previa);
		assertThat(resultado).isFalse();
	}

	// ---------- construirUpdateTC (WriteModel) ----------

	@Test
	public void testConstruirUpdateTCDivisaNueva() {
		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		Sucursal previa = new Sucursal();
		previa.setDivisas(new ArrayList<>());

		com.mongodb.client.model.WriteModel<Sucursal> op = sucursalesDao.construirUpdateTC(req, previa);
		assertThat(op).isNotNull();
	}

	@Test
	public void testConstruirUpdateTCValida() {
		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		Sucursal previa = sucursalConDivisaExistente("USD", 18.52, 19.48);

		com.mongodb.client.model.WriteModel<Sucursal> op = sucursalesDao.construirUpdateTC(req, previa);
		assertThat(op).isNotNull();
	}

	@Test
	public void testConstruirUpdateTCDiferenciaCompraExcedida() {
		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		Sucursal previa = sucursalConDivisaExistente("USD", 99.00, 19.48);

		com.mongodb.client.model.WriteModel<Sucursal> op = sucursalesDao.construirUpdateTC(req, previa);
		assertThat(op).isNull();
	}

	@Test
	public void testConstruirUpdateTCDiferenciaVentaExcedida() {
		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		Sucursal previa = sucursalConDivisaExistente("USD", 18.52, 99.00);

		com.mongodb.client.model.WriteModel<Sucursal> op = sucursalesDao.construirUpdateTC(req, previa);
		assertThat(op).isNull();
	}

	// ---------- construirCrearDivisas ----------

	@Test
	public void testConstruirCrearDivisasConDatos() {
		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		com.mongodb.client.model.WriteModel<Sucursal> op = sucursalesDao.construirCrearDivisas(req);
		assertThat(op).isNotNull();
	}

	@Test
	public void testConstruirCrearDivisasVacio() {
		ReqActSucursal req = new ReqActSucursal();
		req.setIdsucursal("0172");
		req.setDivisas(new ArrayList<>());
		com.mongodb.client.model.WriteModel<Sucursal> op = sucursalesDao.construirCrearDivisas(req);
		assertThat(op).isNull();
	}

	// ---------- ejecutarBulkSucursales ----------

	@Test
	public void testEjecutarBulkSucursalesConDatos() {
		when(mockCollectionSucursales.bulkWrite(any(), isA(com.mongodb.client.model.BulkWriteOptions.class)))
				.thenReturn(null);

		ReqActSucursal req = reqConDivisa("USD", 18.50, 19.50);
		List<com.mongodb.client.model.WriteModel<Sucursal>> ops = new ArrayList<>();
		ops.add(sucursalesDao.construirCrearDivisas(req));

		sucursalesDao.ejecutarBulkSucursales(ops);
	}

	@Test
	public void testEjecutarBulkSucursalesVacioYNulo() {
		// lista vacia y null -> no ejecuta bulkWrite (return temprano)
		sucursalesDao.ejecutarBulkSucursales(new ArrayList<>());
		sucursalesDao.ejecutarBulkSucursales(null);
	}

	// ---------- consultaSucursales (batch) ----------

	@Test
	public void testConsultaSucursalesConDatos() {
		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<Sucursal>());

		List<Sucursal> resultado = sucursalesDao.consultaSucursales(Arrays.asList("0172"));
		assertThat(resultado).isNotNull();
	}

	@Test
	public void testConsultaSucursalesVacioYNulo() {
		assertThat(sucursalesDao.consultaSucursales(new ArrayList<>())).isEmpty();
		assertThat(sucursalesDao.consultaSucursales(null)).isEmpty();
	}

	// ---------- consultaSucursalesConOlp (batch) ----------

	@Test
	public void testConsultaSucursalesConOlpConDatos() {
		Sucursal s = new Sucursal();
		s.setId("0172");
		when(mockCollectionSucursales.aggregate(any())).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.allowDiskUse(true)).thenReturn(mockAggIterableSucursales);
		when(mockAggIterableSucursales.into(any())).thenReturn(new ArrayList<>(Arrays.asList(s)));

		java.util.Set<String> resultado = sucursalesDao.consultaSucursalesConOlp(Arrays.asList("0172"));
		assertThat(resultado).contains("0172");
	}

	@Test
	public void testConsultaSucursalesConOlpVacioYNulo() {
		assertThat(sucursalesDao.consultaSucursalesConOlp(new ArrayList<>())).isEmpty();
		assertThat(sucursalesDao.consultaSucursalesConOlp(null)).isEmpty();
	}

	// ---------- construirUpdateOlp ----------

	private ReqActSucursal reqOlp() {
		ReqActSucursal req = new ReqActSucursal();
		req.setIdsucursal("0172");

		Divisa olp = new Divisa();
		olp.setIdDivisa("OLP");
		TC tc = new TC();
		tc.setCompra(1200.0);
		tc.setVenta(1300.0);
		olp.setTc(tc);
		TC spread = new TC();
		spread.setCompra(1.0);
		spread.setVenta(2.0);
		olp.setSpread(spread);

		req.setTcOlp(olp);
		return req;
	}

	@Test
	public void testConstruirUpdateOlpNoExiste() {
		// existeOlp = false -> upsert que crea tcOlp completo
		com.mongodb.client.model.WriteModel<Sucursal> op = sucursalesDao.construirUpdateOlp(reqOlp(), false);
		assertThat(op).isNotNull();
	}

	@Test
	public void testConstruirUpdateOlpExiste() {
		// existeOlp = true -> updateOne con arrayFilters
		com.mongodb.client.model.WriteModel<Sucursal> op = sucursalesDao.construirUpdateOlp(reqOlp(), true);
		assertThat(op).isNotNull();
	}
}

