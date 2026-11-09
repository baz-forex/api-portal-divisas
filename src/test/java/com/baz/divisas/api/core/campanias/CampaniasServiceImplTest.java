package com.baz.divisas.api.core.campanias;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.core.beneficios.dao.CampaniasDao;
import com.baz.divisas.api.core.beneficios.dto.ReqAltaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEliminaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusCampania;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqTipoCampania;
import com.baz.divisas.api.core.beneficios.model.BeanCampania;
import com.baz.divisas.api.core.beneficios.model.BeanCatalogo;
import com.baz.divisas.api.core.beneficios.model.BeanEntrega;
import com.baz.divisas.api.core.beneficios.model.BeanMensaje;
import com.baz.divisas.api.core.beneficios.model.BeanMensajeConDetalle;
import com.baz.divisas.api.core.beneficios.model.BeanMensajeDetallado;
import com.baz.divisas.api.core.beneficios.model.BeanPremio;
import com.baz.divisas.api.core.beneficios.model.BeanPresupuestos;
import com.baz.divisas.api.core.beneficios.model.BeanRangoEdades;
import com.baz.divisas.api.core.beneficios.model.BeanSegmentoSucursal;
import com.baz.divisas.api.core.beneficios.service.CampaniasServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class CampaniasServiceImplTest {
	@InjectMocks
	private CampaniasServiceImpl campaniasServiceImpl;
	@Mock
	private CampaniasDao dao;
	@Test
	void testCrear() {
		doNothing().when(dao).insert(isA(BeanCampania.class));
		BeanCampania req = new BeanCampania();
		req.setPremios(null);
		req.setFechaInicio("Pepe pecas pica papas con un pico");
		ResponseApi res = campaniasServiceImpl.crear(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		req.setFechaInicio(LocalDateTime.now().minusDays(1L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
		res = campaniasServiceImpl.crear(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		req.setFechaInicio(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
		res = campaniasServiceImpl.crear(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		ArrayList<BeanPremio> ls = new ArrayList<>();
		ls.add(new BeanPremio());
		ls.add(new BeanPremio());
		req.setPremios(ls);
		res = campaniasServiceImpl.crear(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		BeanPremio bp = new BeanPremio();
		bp.setActividadEconomica(new ArrayList<>());
		ls.add(bp);
		req.setPremios(ls);
		res = campaniasServiceImpl.crear(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		req.setAcumulado(true);
		req.setAfectaInventario(true);
		req.setEstatus("Vigente");
		req.setFechaFin("");
		req.setFechaInicio("");
		req.setFechaRegistro("");
		req.setId("");
		req.setNombre("");
		req.setPermanente(true);
		req.setPresupuestos(new BeanPresupuestos());
		req.setPremios(new ArrayList<>());
		req.setPrioridad(0);
		req.setUnica(true);
		
		req.getAcumulado();
		req.getAfectaInventario();
		req.getEstatus();
		req.getFechaFin();
		req.getFechaInicio();
		req.getFechaRegistro();
		req.getId();
		req.getNombre();
		req.getPermanente();
		req.getPresupuestos();
		req.getPremios();
		req.getPrioridad();
		req.getUnica();
		
		BeanPresupuestos bpre = new BeanPresupuestos();
		bpre.setEstatus(true);
		bpre.setIdDivisa("USD");
		bpre.setLimitePresupuesto(0);
		bpre.setPresupuestoActual(0);
		
		bpre.getEstatus();
		bpre.getIdDivisa();
		bpre.getLimitePresupuesto();
		bpre.getPresupuestoActual();
		
		BeanPremio pre = new BeanPremio();
		pre.setActividadEconomica(new ArrayList<>());
		pre.setDescripcion("");
		pre.setDescripcionTipoBeneficio("");
		pre.setDias(new ArrayList<>());
		pre.setEntrega(new BeanEntrega());
		pre.setEstatus(true);
		pre.setGenero(new ArrayList<>());
		pre.setHoraFin("");
		pre.setHoraInicio("");
		pre.setId("");
		pre.setMensajeCajero(new BeanMensaje());
		pre.setMensajeTicket(new BeanMensajeConDetalle());
		pre.setMesesAntiguedad(0);
		pre.setMoneda("USD");
		pre.setPerfilCliente(new ArrayList<>());
		pre.setPrioridadPremio(0);
		pre.setRangoEdades(new ArrayList<>());
		pre.setRangoFinal(0);
		pre.setRangoInicial(0);
		pre.setSegmentoSucursal(new BeanSegmentoSucursal());
		pre.setTipoBeneficio(0);
		pre.setTipoCliente(new ArrayList<>());
		pre.setTipoOperacion(new ArrayList<>());
		pre.setValor(0D);
		
		pre.getActividadEconomica();
		pre.getDescripcion();
		pre.getDescripcionTipoBeneficio();
		pre.getDias();
		pre.getEntrega();
		pre.getEstatus();
		pre.getGenero();
		pre.getHoraFin();
		pre.getHoraInicio();
		pre.getId();
		pre.getMensajeCajero();
		pre.getMensajeTicket();
		pre.getMesesAntiguedad();
		pre.getMoneda();
		pre.getPerfilCliente();
		pre.getPrioridadPremio();
		pre.getRangoEdades();
		pre.getRangoFinal();
		pre.getRangoInicial();
		pre.getSegmentoSucursal();
		pre.getTipoBeneficio();
		pre.getTipoCliente();
		pre.getTipoOperacion();
		pre.getValor();
		
		BeanCatalogo cat = new BeanCatalogo();
		cat.setId("");
		cat.setDescripcion("");
		
		cat.getId();
		cat.getDescripcion();
		
		BeanEntrega be = new BeanEntrega();
		be.setCantidadPeriodoAcumulado(0);
		be.setConLimite(true);
		be.setDescripcion("");
		be.setIntervaloLandingPage(0);
		be.setIntervaloVentanilla(0);
		be.setLandingPage(true);
		be.setLimiteEntregas(0);
		be.setMaximoOperaciones(0);
		be.setMonto(0);
		be.setMontoLandingPage(0);
		be.setOperacionesAcumuladas(0);
		be.setPeriodoAcumulado("");
		be.setPeriodoEntregas("");
		be.setRangoMontoFin(0);
		be.setRangoMontoInicio(0);
		be.setTipo(0);
		be.setTipoAcumulado("");
		
		be.getCantidadPeriodoAcumulado();
		be.isConLimite();
		be.getDescripcion();
		be.getIntervaloLandingPage();
		be.getIntervaloVentanilla();
		be.getLandingPage();
		be.getLimiteEntregas();
		be.getMaximoOperaciones();
		be.getMonto();
		be.getMontoLandingPage();
		be.getOperacionesAcumuladas();
		be.getPeriodoAcumulado();
		be.getPeriodoEntregas();
		be.getRangoMontoFin();
		be.getRangoMontoInicio();
		be.getTipo();
		be.getTipoAcumulado();
		
		BeanMensaje m = new BeanMensaje();
		m.setMensaje("");
		m.setTitulo("");
		m.getMensaje();
		m.getTitulo();
		
		BeanMensajeConDetalle md = new BeanMensajeConDetalle();
		md.setMensaje(new BeanMensajeDetallado());
		md.setTitulo(new BeanMensajeDetallado());
		
		BeanMensajeDetallado mdd = new BeanMensajeDetallado();
		mdd.setNegritas("");
		mdd.setTamanio("");
		mdd.setTexto("");
		mdd.getNegritas();
		mdd.getTamanio();
		mdd.getTexto();
		
		BeanRangoEdades re = new BeanRangoEdades();
		re.setDescripcion("");
		re.setId("");
		re.setMaximo(0);
		re.setMinimo(0);
		re.getDescripcion();
		re.getId();
		re.getMaximo();
		re.getMinimo();
		
		BeanSegmentoSucursal s = new BeanSegmentoSucursal();
		s.setIds(new ArrayList<>());
		s.setTipo("");
		s.getIds();
		s.getTipo();
	}
	@Test
	void testObtener() {
		doNothing().when(dao).actualizaCampaniasFueraVigencia();
		when(dao.consultar()).thenReturn(null);
		ResponseApi res = campaniasServiceImpl.obtener();
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizar() {
		doNothing().when(dao).actualizar(isA(BeanCampania.class));
		BeanCampania req = new BeanCampania();
		req.setFechaInicio(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
		ResponseApi res = campaniasServiceImpl.actualizar(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testEliminar() {
		doNothing().when(dao).eliminarCampania(isA(String.class));
		
		when(dao.consultaUnica(isA(String.class))).thenReturn(null);
		ResponseApi res = campaniasServiceImpl.eliminar("");
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		BeanCampania c = new BeanCampania();
		c.setEstatus("Creada");
		when(dao.consultaUnica(isA(String.class))).thenReturn(c);
		res = campaniasServiceImpl.eliminar("");
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		c = new BeanCampania();
		c.setEstatus("Vigente");
		when(dao.consultaUnica(isA(String.class))).thenReturn(c);
		res = campaniasServiceImpl.eliminar("");
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		c = new BeanCampania();
		c.setEstatus("Pausada");
		when(dao.consultaUnica(isA(String.class))).thenReturn(c);
		res = campaniasServiceImpl.eliminar("");
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizarTipo() {
		doNothing().when(dao).actualizarTipo(isA(ReqTipoCampania.class));
		ResponseApi res = campaniasServiceImpl.actualizarTipo(new ReqTipoCampania());
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizarEstatus() {
		doNothing().when(dao).actualizarEstatus(isA(ReqEstatusCampania.class));
		ResponseApi res = campaniasServiceImpl.actualizarEstatus(new ReqEstatusCampania());
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testCrearPremio() {
		doNothing().when(dao).insertPremio(isA(String.class),isA(BeanPremio.class));
		ReqAltaPremio req = new ReqAltaPremio();
		req.setIdCampania("");
		req.setPremios(null);
		ResponseApi res = campaniasServiceImpl.crearPremio(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		req.setPremios(new ArrayList<>());
		res = campaniasServiceImpl.crearPremio(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		ArrayList<BeanPremio> ls = new ArrayList<>();
		ls.add(new BeanPremio());
		req.setPremios(ls);
		res = campaniasServiceImpl.crearPremio(req);
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizarPremios() {
		doNothing().when(dao).actualizarPremio(isA(ReqAltaPremio.class));
		ResponseApi res = campaniasServiceImpl.actualizarPremio(new ReqAltaPremio());
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testEliminarPremio() {
		doNothing().when(dao).eliminarPremio(isA(ReqEliminaPremio.class));
		ResponseApi res = campaniasServiceImpl.eliminarPremio(new ReqEliminaPremio());
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizarEstatusPremio() {
		doNothing().when(dao).actualizarEstatusPremio(isA(ReqEstatusPremio.class));
		ResponseApi res = campaniasServiceImpl.actualizarEstatusPremio(new ReqEstatusPremio());
		assertThat(res.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
}
