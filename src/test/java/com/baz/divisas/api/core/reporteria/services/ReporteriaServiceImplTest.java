package com.baz.divisas.api.core.reporteria.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.core.reporteria.dao.BeneficiosDao;
import com.baz.divisas.api.core.reporteria.dao.IncidenciasDao;
import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.api.core.reporteria.dao.OperacionesDao;
import com.baz.divisas.api.core.reporteria.dto.ReqBusquedaIncidencias;
import com.baz.divisas.api.core.reporteria.dto.ReqReporteOperaciones;
import com.baz.divisas.api.core.reporteria.dto.ReqReporteRascadito;
import com.baz.divisas.api.core.reporteria.model.ClienteGanador;
import com.baz.divisas.api.core.reporteria.model.ClienteIncidencia;
import com.baz.divisas.api.core.reporteria.model.DatosBusqueda;
import com.baz.divisas.api.core.reporteria.model.GanadorCostalSanta;
import com.baz.divisas.api.core.reporteria.model.GanadorGolDeOro;
import com.baz.divisas.api.core.reporteria.model.GanadorPromoDigital;
import com.baz.divisas.api.core.reporteria.model.GanadorVeranoMundialista;
import com.baz.divisas.api.core.reporteria.model.InventarioCostalSanta;
import com.baz.divisas.api.core.reporteria.model.Incidencia;
import com.baz.divisas.api.core.reporteria.model.InventarioMundialVerde;
import com.baz.divisas.api.core.reporteria.model.InventarioVeranoMundialista;
import com.baz.divisas.api.core.reporteria.model.ListaNegra;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReporteriaServiceImplTest {


	@InjectMocks
	private ReporteriaServiceImpl reporteriaService;	
	@Mock
	private IncidenciasDao incidenciasDao;	
	@Mock
	private OperacionesDao operacionesDao;
	@Mock
	private BeneficiosDao beneficiosDao;	
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	@Mock
	private MongoCursor<Document> mockCursor;
	
	@Test
	void testGetAllIncidencia() {
		
		ReqBusquedaIncidencias reqBusquedaIncidencias = new ReqBusquedaIncidencias();
		reqBusquedaIncidencias.setFechaFinal("");
		reqBusquedaIncidencias.setFechaInicial("");
		reqBusquedaIncidencias.setTipoIncidencia("");
		reqBusquedaIncidencias.getFechaFinal();
		reqBusquedaIncidencias.getFechaInicial();
		reqBusquedaIncidencias.getTipoIncidencia();
		
		List<Incidencia> listaIncidencias = new ArrayList<Incidencia>();
		
		when(incidenciasDao.consulta(isA(ReqBusquedaIncidencias.class), isA(boolean.class))).thenReturn(listaIncidencias);
		ResponseApi response = reporteriaService.getIncidenciasImpl(reqBusquedaIncidencias);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//////////////////
		listaIncidencias.add( new Incidencia());
		
		when(incidenciasDao.consulta(isA(ReqBusquedaIncidencias.class), isA(boolean.class))).thenReturn(listaIncidencias);
		response = reporteriaService.getIncidenciasImpl(reqBusquedaIncidencias);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	@Test
	void testGetIncidencia() {
				
		when(incidenciasDao.consultaPorId(isA(String.class))).thenReturn(null);
		ResponseApi response = reporteriaService.getIncidenciaImpl("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//////////////////		
		when(incidenciasDao.consultaPorId(isA(String.class))).thenReturn(new Incidencia());
		response = reporteriaService.getIncidenciaImpl("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}	
	
	@Test
	void testDescargaIncidenciasImpl() {
				
		List<Incidencia> listaIncidencias = new ArrayList<Incidencia>();
		ReqBusquedaIncidencias request = new ReqBusquedaIncidencias();
		request.setTipoIncidencia("LISTA NEGRA");
		
		when(incidenciasDao.consulta(isA(ReqBusquedaIncidencias.class), isA(boolean.class))).thenReturn(listaIncidencias);
		ByteArrayInputStream response = reporteriaService.descargaIncidenciasImpl(request);
		assertThat(response).isNotNull();
		
		//////////////////			
		DatosBusqueda db = new DatosBusqueda();
		db.getApellidoMaterno();
		db.getApellidoPaterno();
		db.getFechaNacimiento();
		db.getNombre();
		db.setApellidoMaterno("");
		db.setApellidoPaterno("");
		db.setFechaNacimiento("");
		db.setNombre("");
		
		Incidencia incidencia = new Incidencia();
		incidencia.setId("");
		incidencia.setFechaIncidencia("");
		incidencia.setSucursal("");
		incidencia.setTipoIncidencia("LISTA NEGRA");
		incidencia.setResultados(new ArrayList<>());
		incidencia.setDatosBusqueda(db);
		incidencia.getId();
		incidencia.getFechaIncidencia();
		incidencia.getSucursal();
		incidencia.getTipoIncidencia();
		incidencia.getResultados();
		incidencia.getDatosBusqueda();
		
		listaIncidencias.add(incidencia);
		
		response = reporteriaService.descargaIncidenciasImpl(request);
		assertThat(response).isNotNull();
		
		//////////////////			
		ListaNegra listaNegra = new ListaNegra();
		listaNegra.setDescripcion("");
		listaNegra.setId("");
		listaNegra.getDescripcion();
		listaNegra.getId();
				
		ClienteIncidencia clienteIncidencia = new ClienteIncidencia();
		clienteIncidencia.setApellidos("");
		clienteIncidencia.setFechaNacimiento("");
		clienteIncidencia.setNombres("");
		clienteIncidencia.setPorcentajeCoincidencia("");
		clienteIncidencia.setPrecision("");
		clienteIncidencia.setRutaVisor("");
		clienteIncidencia.setTipoLista(listaNegra);
		clienteIncidencia.getApellidos();
		clienteIncidencia.getFechaNacimiento();
		clienteIncidencia.getNombres();
		clienteIncidencia.getPorcentajeCoincidencia();
		clienteIncidencia.getPrecision();
		clienteIncidencia.getRutaVisor();
		clienteIncidencia.getTipoLista();
		
		incidencia.setResultados(Arrays.asList(clienteIncidencia));
		
		response = reporteriaService.descargaIncidenciasImpl(request);
		assertThat(response).isNotNull();
		
	}

	@Test
	void testGetOperacionesImpl() {
	
		ReqReporteOperaciones request = new ReqReporteOperaciones();

		when(operacionesDao.count(isA(Document.class))).thenReturn(0L);
		
		ResponseApi response = reporteriaService.getOperacionesImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());	
		
		/////////////
		request.setDivisa("");
		request.getDivisa();

		request.setFechaFinal("");
		request.getFechaFinal();
		request.setFechaInicial("");
		request.getFechaInicial();
		request.setIdCliente("");
		request.getIdCliente();
		request.setSucursal("");
		request.getSucursal();
		request.setTipoOperacion(Arrays.asList());
		request.getTipoOperacion();
		request.setAplicacionOrigen("");
		request.getAplicacionOrigen();
				
		when(operacionesDao.count(isA(Document.class))).thenReturn(1L);
		when(operacionesDao.consulta(isA(Document.class), isA(boolean.class))).thenReturn(mockAggIterable);
		when(mockAggIterable.iterator()).thenReturn(mockCursor);
		when(mockCursor.hasNext()).thenReturn(false);

		response = reporteriaService.getOperacionesImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		/////////////
		request.setFechaInicial("1");

		response = reporteriaService.getOperacionesImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		/////////////
		request.setFechaFinal("1");
		request.setDivisa("1");
		request.setIdCliente("1");
		request.setSucursal("1");
		request.setAplicacionOrigen("1");
		request.setEstatus(Arrays.asList("Test"));
		
		response = reporteriaService.getOperacionesImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	
		/////////////
		when(mockCursor.hasNext()).thenReturn(true);
		response = reporteriaService.getOperacionesImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaOperacionesImpl() {
		
		ReqReporteOperaciones request = new ReqReporteOperaciones();
		
		request.isFechasRequeridas();
		
		request.setFechaInicial("");
		request.isFechasRequeridas();
		
		request.setFechaInicial("2023-04-01");
		request.isFechasRequeridas();

		request.setFechaFinal("");
		request.isFechasRequeridas();
		
		request.setFechaFinal("2023-04-01");
		request.isFechasRequeridas();
		
		request.setIdCliente("");
		request.isFechasRequeridas();

		request.setIdCliente("1");
		request.isFechasRequeridas();
		request.setEstatus(Arrays.asList());


		List<Document> listOperaciones = new ArrayList<>();
		when(operacionesDao.consulta(isA(Document.class), isA(boolean.class))).thenReturn(listOperaciones);
		
		ByteArrayInputStream response = reporteriaService.descargaOperacionesImpl(request);
		assertThat(response).isNotNull();
		
		/////////////
		Document docOperacion = new Document("tipo_operacion", "413");
		docOperacion.put("tipo_identificacion", "1");
		docOperacion.put("fecha_operacion", "");
		docOperacion.put("monto_divisa", "0.0");
		docOperacion.put("monto_mxn", "0.0");
		docOperacion.put("tipo_cambio", "0.0");
		docOperacion.put("tipo_cambio_sucursal", "0.0");
		

		listOperaciones.add(docOperacion);
		
		response = reporteriaService.descargaOperacionesImpl(request);
		assertThat(response).isNotNull();
		
		/////////////
		docOperacion.put("premio_beneficio", "");
		docOperacion.put("tipo_operacion", "412");
		docOperacion.put("tipo_identificacion", "2");

		response = reporteriaService.descargaOperacionesImpl(request);
		assertThat(response).isNotNull();
		
		/////////////
		docOperacion.put("tipo_operacion", "411");
		docOperacion.put("tipo_identificacion", "37");

		response = reporteriaService.descargaOperacionesImpl(request);
		assertThat(response).isNotNull();
		
		/////////////
		docOperacion.put("tipo_operacion", "410");
		docOperacion.put("tipo_identificacion", "38");

		response = reporteriaService.descargaOperacionesImpl(request);
		assertThat(response).isNotNull();
		
		/////////////
		docOperacion.put("tipo_operacion", "0");
		docOperacion.put("tipo_identificacion", "0");

		response = reporteriaService.descargaOperacionesImpl(request);
		assertThat(response).isNotNull();

	}
	
	@Test
	void testGetAcumuladoOperacionesImpl() {
	
		ReqReporteOperaciones request = new ReqReporteOperaciones();
	
		when(operacionesDao.acumulado(isA(Document.class))).thenReturn(new Document());

		ResponseApi response = reporteriaService.getAcumuladoOperacionesImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void testDescargaGanadoresRascaditoImpl() {
		
		ReqReporteRascadito request = new ReqReporteRascadito();
		request.setFechaInicial("");
		request.getFechaInicial();
		request.setFechaFinal("");
		request.getFechaFinal();
		
		
		GanadorPromoDigital ganador = new GanadorPromoDigital();
		ganador.setBeneficio(0);
		ganador.getBeneficio();
		ganador.setCodigo("");
		ganador.getCodigo();
		ganador.setCodigoDivisa("");
		ganador.getCodigoDivisa();
		ganador.setEstatus("");
		ganador.getEstatus();
		ganador.setFechaActualizacion("");
		ganador.getFechaActualizacion();
		ganador.setFechaRegistro("");
		ganador.getFechaRegistro();
		ganador.setId("");
		ganador.getId();
		ganador.setMontoMinimoOperacion(0);
		ganador.getMontoMinimoOperacion();
		ganador.setNombreCampania("");
		ganador.getNombreCampania();
		ganador.setOrigen("");
		ganador.getOrigen();
		ganador.setVigencia("");
		ganador.getVigencia();
		
		List<GanadorPromoDigital> listGanadores = Arrays.asList(ganador);
		
		when(beneficiosDao.consultaGanadoresPromoDigital(isA(Document.class))).thenReturn(listGanadores);
		
		ByteArrayInputStream response = reporteriaService.descargaGanadoresRascaditoImpl(request);
		assertThat(response).isNotNull();
		
		///////////////////
		
		ClienteGanador cliente = new ClienteGanador();
		cliente.setNumeroTelefono("");
		cliente.getNumeroTelefono();
		cliente.setCorreoElectronico("");
		cliente.getCorreoElectronico();
		
		ganador.setCliente(cliente);
		ganador.getCliente();
		
		response = reporteriaService.descargaGanadoresRascaditoImpl(request);
		assertThat(response).isNotNull();

	}
	
	@Test
	void testDescargaGanadoresGolDeOroImpl() {
		
		GanadorGolDeOro ganador = new GanadorGolDeOro();
		ganador.setAcumuladoBanca("");
		ganador.setCaja("");
		ganador.setCajero("");
		ganador.setCanalOperado("");
		ganador.setClienteUnico("");
		ganador.setFechaNacimiento("");
		ganador.setFechaOperacion("");
		ganador.setGolesAcumulados("");
		ganador.setGolesEntregados("1");
		ganador.setIdCliente("");
		ganador.setMontoDivisa(0d);
		ganador.setMontoOperado(0d);
		ganador.setNombreCompleto("");
		ganador.setNumeroAutorizacion("");
		ganador.setNumeroOperacion("");
		ganador.setStatus("");
		ganador.setSucursal("");
		ganador.setTipoFlujo("");
		ganador.setTipoOperacion("");

		List<GanadorGolDeOro> listGanadores = Arrays.asList(ganador);
		List<String> empleadosBloqueados = Arrays.asList();
		when(beneficiosDao.consultaGanadoresGolDeOro(isA(Document.class))).thenReturn(listGanadores);
		when(beneficiosDao.consultaEmpleadosBloqueados()).thenReturn(empleadosBloqueados);
		
		ByteArrayInputStream response = reporteriaService.descargaGanadoresGolDeOroImpl();
		assertThat(response).isNotNull();

	}
	
	@Test
	public void testDescargaInventarioMundialVerdeImpl() {
		
		InventarioMundialVerde inventario = new InventarioMundialVerde();
		inventario.setId("0172");
		inventario.setStock(0);
		inventario.setStockEntregado(0);
		inventario.setStockRestante(0);

		List<InventarioMundialVerde> listInventario = Arrays.asList(inventario);
		when(beneficiosDao.consultaInventarioMundialVerde()).thenReturn(listInventario);
		
		ByteArrayInputStream response = reporteriaService.descargaInventarioMundialVerdeImpl();
		assertThat(response).isNotNull();

	}
	
	@Test
	public void testDescargaInventarioVeranoMundialistaImpl() {
		
		InventarioVeranoMundialista inventario = new InventarioVeranoMundialista();
		inventario.setId("0172");
		inventario.setStock1(0);
		inventario.setStockEntregado1(0);
		inventario.setStockRestante1(0);
		
		inventario.setStock2(0);
		inventario.setStockEntregado2(0);
		inventario.setStockRestante2(0);
		
		inventario.setStock3(0);
		inventario.setStockEntregado3(0);
		inventario.setStockRestante3(0);
		
		inventario.setStock4(0);
		inventario.setStockEntregado4(0);
		inventario.setStockRestante4(0);

		List<InventarioVeranoMundialista> listInventario = Arrays.asList(inventario);
		when(beneficiosDao.consultaInventarioVeranoMundialista()).thenReturn(listInventario);
		
		ByteArrayInputStream response = reporteriaService.descargaInventarioVeranoMundialistaImpl();
		assertThat(response).isNotNull();

	}
	
	
	@Test
	void testdescargaGanadoresVeranoMundialista() {
		
		GanadorVeranoMundialista ganador = new GanadorVeranoMundialista();
		
		ganador.setCajero("");
		
		ganador.setFechaNacimiento("");
		ganador.setFechaOperacion("");
		ganador.setNombreCompleto("");
		ganador.setNumeroAutorizacion("");
		ganador.setNumeroOperacion("");
		ganador.setSucursal("");
		ganador.setSku("");

		List<GanadorVeranoMundialista> listGanadores = Arrays.asList(ganador);
		when(beneficiosDao.consultaGanadoresVeranoMundialista(isA(Document.class))).thenReturn(listGanadores);
		
		ByteArrayInputStream response = reporteriaService.descargaGanadoresVeranoMundialista();
		assertThat(response).isNotNull();

	}
	
	@Test
	void testDescargaCuponesMasSaludImpl() {
		
		CuponMasSalud cupon = new CuponMasSalud();
		cupon.setId("DIV2600001");
		cupon.setTipo("1");
		cupon.setEstado("entregado");
		cupon.setDivisa("USD");
		cupon.setFechaEntrega("2026-07-15");
		cupon.setHoraEntrega("12:18:09");
		cupon.setIdCliente("123456");
		cupon.setMonto("249");
		cupon.setNombreCliente("Juan Perez Lopez");
		cupon.setSucursal("2244");
		
		List<CuponMasSalud> listCupones = Arrays.asList(cupon);
		when(beneficiosDao.consultaCuponesMasSalud()).thenReturn(listCupones);
		
		ByteArrayInputStream response = reporteriaService.descargaCuponesMasSaludImpl();
		assertThat(response).isNotNull();
		
		///////////////////
		// Lista vacía
		when(beneficiosDao.consultaCuponesMasSalud()).thenReturn(new ArrayList<>());
		
		response = reporteriaService.descargaCuponesMasSaludImpl();
		assertThat(response).isNotNull();
	}
	
	@Test
	void testDescargaGanadoresCostalSanta() {
		
		GanadorCostalSanta ganador = new GanadorCostalSanta();
		ganador.setNombreCompleto("Juan Perez Lopez");
		ganador.getNombreCompleto();
		ganador.setSucursal("2244");
		ganador.getSucursal();
		ganador.setNumeroOperacion("123456");
		ganador.getNumeroOperacion();
		ganador.setFechaOperacion("2026-12-01");
		ganador.getFechaOperacion();
		ganador.setMontoDivisa("100.0");
		ganador.getMontoDivisa();
		ganador.setRegion("Norte");
		ganador.getRegion();
		ganador.setCorreo("juan@test.com");
		ganador.getCorreo();
		
		List<GanadorCostalSanta> listGanadores = Arrays.asList(ganador);
		when(beneficiosDao.consultaGanadoresCostalSanta(isA(Document.class))).thenReturn(listGanadores);
		
		ByteArrayInputStream response = reporteriaService.descargaGanadoresCostalSanta();
		assertThat(response).isNotNull();
		
		///////////////////
		// Lista vacía
		when(beneficiosDao.consultaGanadoresCostalSanta(isA(Document.class))).thenReturn(new ArrayList<>());
		
		response = reporteriaService.descargaGanadoresCostalSanta();
		assertThat(response).isNotNull();
	}
	
	@Test
	void testDescargaInventarioCostalSanta() {
		
		InventarioCostalSanta inventario = new InventarioCostalSanta();
		inventario.setId("Norte");
		inventario.getId();
		inventario.setStock(100);
		inventario.getStock();
		inventario.setStockEntregado(40);
		inventario.getStockEntregado();
		inventario.setStockRestante(60);
		inventario.getStockRestante();
		inventario.setFechaUltimaEntrega("2026-12-01");
		inventario.getFechaUltimaEntrega();
		
		List<InventarioCostalSanta> listInventario = Arrays.asList(inventario);
		when(beneficiosDao.consultaInventarioCostalSanta()).thenReturn(listInventario);
		
		ByteArrayInputStream response = reporteriaService.descargaInventarioCostalSanta();
		assertThat(response).isNotNull();
		
		///////////////////
		// Lista vacía
		when(beneficiosDao.consultaInventarioCostalSanta()).thenReturn(new ArrayList<>());
		
		response = reporteriaService.descargaInventarioCostalSanta();
		assertThat(response).isNotNull();
	}

}
