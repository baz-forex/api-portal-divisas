package com.baz.divisas.api.tef.parametria.services;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.apigee.dto.ResultadoApi;
import com.baz.divisas.api.apigee.dto.ResultadoLlaves;
import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.apigee.model.ApiLlaves;
import com.baz.divisas.api.tef.parametria.dto.AsignacionParametro;
import com.baz.divisas.api.tef.parametria.dto.ConsultaAsignaciones;
import com.baz.divisas.api.tef.parametria.dto.ConsultaParametros;
import com.baz.divisas.api.tef.parametria.dto.Paginacion;
import com.baz.divisas.api.tef.parametria.dto.Parametro;
import com.baz.divisas.api.tef.parametria.dto.ReqActAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqArchivo;
import com.baz.divisas.api.tef.parametria.dto.ReqAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaAsignaciones;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaParametros;
import com.baz.divisas.api.tef.parametria.dto.ReqDetalleParametro;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminacionAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ResConsultaAsignaciones;
import com.baz.divisas.api.tef.parametria.dto.ResConsultaParametros;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParametriaSucursalesImplTest {

	@InjectMocks
	private ParametriaSucursalesImpl parametriaImpl;
	
	@Mock
	private ParametriaService parametriaService;
	
	@Test
	public void testConsultaParametros() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.consultaParametros(new ReqBusquedaParametros());
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);
		
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(null);
		
		parametriaImpl.consultaParametros(new ReqBusquedaParametros());
		
		ResultadoLlaves resLlaves = new ResultadoLlaves();
		resLlaves.setAccesoPrivado("123123123123");
		resLlaves.setAccesoPublico("123123123123");
		resLlaves.setIdAcceso("123123123");
		ApiLlaves llaves = new ApiLlaves();
		llaves.setResultado(resLlaves);
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		
		ReqBusquedaParametros req = new ReqBusquedaParametros();
		req.setParametro("");
		parametriaImpl.consultaParametros(req);
		
		ConsultaParametros resultado = new ConsultaParametros();
		resultado.setPaginacion(new Paginacion());
		Parametro parametro = new Parametro();
		parametro.setDescripcion("1231231");
		parametro.setParametro("121313");
		List<Parametro> listaParametro = new ArrayList<>();
		listaParametro.add(parametro);
		resultado.setParametros(listaParametro);
		ResConsultaParametros resConsulta = new ResConsultaParametros();
		resConsulta.setResultado(resultado);
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		when(parametriaService.consultaParametros(isA(ReqBusquedaParametros.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.consultaParametros(req);
		
		req.setParametro("sdasdasdas");
		parametriaImpl.consultaParametros(req);
		
	}
	
	@Test
	public void testRegistraParametro() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.registraParametro(new ReqBusquedaParametros(),"");
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);
		
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(null);
		
		parametriaImpl.registraParametro(new ReqBusquedaParametros(),"");
		
		ResultadoLlaves resLlaves = new ResultadoLlaves();
		resLlaves.setAccesoPrivado("123123123123");
		resLlaves.setAccesoPublico("123123123123");
		resLlaves.setIdAcceso("123123123");
		ApiLlaves llaves = new ApiLlaves();
		llaves.setResultado(resLlaves);
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		
		ReqBusquedaParametros req = new ReqBusquedaParametros();
		req.setParametro("sdasdasdas");
				
		ResultadoApi resConsulta = new ResultadoApi();
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		when(parametriaService.registraParametros(isA(ReqDetalleParametro.class),isA(String.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.registraParametro(req,"");
				
	}
	
	@Test
	public void testActualizaParametro() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.actualizaParametro(new ReqBusquedaParametros(),"");
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);
		
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(null);
		
		parametriaImpl.actualizaParametro(new ReqBusquedaParametros(),"");
		
		ResultadoLlaves resLlaves = new ResultadoLlaves();
		resLlaves.setAccesoPrivado("123123123123");
		resLlaves.setAccesoPublico("123123123123");
		resLlaves.setIdAcceso("123123123");
		ApiLlaves llaves = new ApiLlaves();
		llaves.setResultado(resLlaves);
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		
		ReqBusquedaParametros req = new ReqBusquedaParametros();
		req.setParametro("sdasdasdas");
				
		ResultadoApi resConsulta = new ResultadoApi();
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		when(parametriaService.actualizaParametros(isA(ReqDetalleParametro.class),isA(String.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.actualizaParametro(req,"");
				
	}
	
	@Test
	public void testRegistraParametrosMasivos() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.registraParametrosMasivos(new ReqArchivo(),"");
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);
		
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(null);
		
		parametriaImpl.registraParametrosMasivos(new ReqArchivo(),"");
		
		ResultadoLlaves resLlaves = new ResultadoLlaves();
		resLlaves.setAccesoPrivado("123123123123");
		resLlaves.setAccesoPublico("123123123123");
		resLlaves.setIdAcceso("123123123");
		resLlaves.setAccesoSimetrico("12312323");
		resLlaves.setCodigoAutentificacionHash("123123123");
		ApiLlaves llaves = new ApiLlaves();
		llaves.setResultado(resLlaves);
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		
		ReqArchivo req = new ReqArchivo();
		req.setArchivo("sdasdasdas");
				
		ResultadoApi resConsulta = new ResultadoApi();
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		when(parametriaService.registraParametrosMasivos(isA(ReqArchivo.class),isA(String.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.registraParametrosMasivos(req,"");
				
	}
	
	@Test
	public void testBuscaParametrosAsignados() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.buscaParametrosAsignados(new ReqBusquedaAsignaciones());
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);
		
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(null);
		
		parametriaImpl.buscaParametrosAsignados(new ReqBusquedaAsignaciones());
		
		ResultadoLlaves resLlaves = new ResultadoLlaves();
		resLlaves.setAccesoPrivado("123123123123");
		resLlaves.setAccesoPublico("123123123123");
		resLlaves.setIdAcceso("123123123");
		resLlaves.setAccesoSimetrico("12312323");
		resLlaves.setCodigoAutentificacionHash("123123123");
		ApiLlaves llaves = new ApiLlaves();
		llaves.setResultado(resLlaves);
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		
		ReqBusquedaAsignaciones req = new ReqBusquedaAsignaciones();
		req.setParametro("sdasdasdas");
		req.setAsignacion("123123123");		
		
		ConsultaAsignaciones resultado = new ConsultaAsignaciones();
		resultado.setPaginacion(new Paginacion());
		AsignacionParametro asignacion = new AsignacionParametro();
		asignacion.setDescripcion("1231231");
		asignacion.setParametro("121313");
		List<AsignacionParametro> listaAsignacion = new ArrayList<>();
		listaAsignacion.add(asignacion);
		resultado.setAsignaciones(listaAsignacion);
		ResConsultaAsignaciones resConsulta = new ResConsultaAsignaciones();
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		resConsulta.setResultado(resultado);
		
		when(parametriaService.consultaAsignacionesParametros(isA(ReqBusquedaAsignaciones.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.buscaParametrosAsignados(req);
		
		when(parametriaService.consultaAsignacionesParametros(isA(ReqBusquedaAsignaciones.class),isA(String.class),isA(String.class))).thenReturn(null);
		parametriaImpl.buscaParametrosAsignados(req);
				
	}
	
	@Test
	public void testCrearAsignacionParametro() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.crearAsignacionParametro(new ReqAsignacion(),"");
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);
		
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(null);
		
		parametriaImpl.crearAsignacionParametro(new ReqAsignacion(),"");
		
		ResultadoLlaves resLlaves = new ResultadoLlaves();
		resLlaves.setAccesoPrivado("123123123123");
		resLlaves.setAccesoPublico("123123123123");
		resLlaves.setIdAcceso("123123123");
		ApiLlaves llaves = new ApiLlaves();
		llaves.setResultado(resLlaves);
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		
		ReqAsignacion req = new ReqAsignacion();
		req.setParametro("sdasdasdas");
				
		ResultadoApi resConsulta = new ResultadoApi();
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		when(parametriaService.crearAsignacionParametro(isA(ReqAsignacion.class),isA(String.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.crearAsignacionParametro(req,"");
				
	}
	
	@Test
	public void testActualizaAsignacionParametro() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.actualizaAsignacionParametro(new ReqActAsignacion(),"");
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);
		
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(null);
		
		parametriaImpl.actualizaAsignacionParametro(new ReqActAsignacion(),"");
		
		ResultadoLlaves resLlaves = new ResultadoLlaves();
		resLlaves.setAccesoPrivado("123123123123");
		resLlaves.setAccesoPublico("123123123123");
		resLlaves.setIdAcceso("123123123");
		ApiLlaves llaves = new ApiLlaves();
		llaves.setResultado(resLlaves);
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		
		ReqActAsignacion req = new ReqActAsignacion();
		req.setParametro("sdasdasdas");
				
		ResultadoApi resConsulta = new ResultadoApi();
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		when(parametriaService.actualizaAsignacionParametro(isA(ReqActAsignacion.class),isA(String.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.actualizaAsignacionParametro(req,"");
				
	}
	
	@Test
	public void testEliminaAsignacionParametro() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.eliminaAsignacionParametro(new ReqEliminacionAsignacion(),"");
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);		
						
		ReqEliminacionAsignacion req = new ReqEliminacionAsignacion();
				
		ResultadoApi resConsulta = new ResultadoApi();
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		when(parametriaService.eliminaAsignacionParametro(isA(ReqEliminacionAsignacion.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.eliminaAsignacionParametro(req,"");
				
	}
	
	@Test
	public void testRegistraAsignacionesParametrosMasivos() {
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(null);
		parametriaImpl.registraAsignacionesParametrosMasivos(new ReqArchivo(),"");
		
		TokenApigee restmd = new TokenApigee();
		restmd.setCodigo("1");
		restmd.setAccess_token("1");
		when(parametriaService.getTokenApiAWSInterno()).thenReturn(restmd);
		
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(null);
		
		parametriaImpl.registraAsignacionesParametrosMasivos(new ReqArchivo(),"");
		
		ResultadoLlaves resLlaves = new ResultadoLlaves();
		resLlaves.setAccesoPrivado("123123123123");
		resLlaves.setAccesoPublico("123123123123");
		resLlaves.setIdAcceso("123123123");
		resLlaves.setAccesoSimetrico("12312323");
		resLlaves.setCodigoAutentificacionHash("123123123");
		ApiLlaves llaves = new ApiLlaves();
		llaves.setResultado(resLlaves);
		when(parametriaService.getLlavesDivisas(isA(String.class))).thenReturn(llaves);
		
		ReqArchivo req = new ReqArchivo();
		req.setArchivo("sdasdasdas");
				
		ResultadoApi resConsulta = new ResultadoApi();
		resConsulta.setCodigo("123");
		resConsulta.setMensaje("123123");
		when(parametriaService.registraAsignacionesParametrosMasivos(isA(ReqArchivo.class),isA(String.class),isA(String.class),isA(String.class))).thenReturn(resConsulta);
		parametriaImpl.registraAsignacionesParametrosMasivos(req,"");
		
		parametriaImpl= new ParametriaSucursalesImpl();
	}
	
}
