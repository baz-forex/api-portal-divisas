package com.baz.divisas.api.core.beneficios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import com.baz.divisas.api.core.beneficios.dto.ReqEstatusSeguro;
import com.baz.divisas.api.core.beneficios.model.BeanMensaje;
import com.baz.divisas.api.core.beneficios.model.BeanMensajeConDetalle;
import com.baz.divisas.api.core.beneficios.model.BeanMensajeDetallado;
import com.baz.divisas.api.core.beneficios.model.PremioSeguro;
import com.baz.divisas.api.core.beneficios.model.SegmentoSucursal;
import com.baz.divisas.api.core.beneficios.model.Seguro;
import com.baz.divisas.api.core.beneficios.service.SegurosServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class SegurosControllerTest {
	
	@InjectMocks
	private SegurosController segurosController;
	@Mock
	private SegurosServiceImpl segurosService;
	
	@Test
	void testConsulta() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(segurosService.consultaSeguros()).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = segurosController.consulta();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testConsultaPorEstatus() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(segurosService.consultaSegurosPorEstatus(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = segurosController.consultaPorEstatus("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaPorId() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(segurosService.consultaSegurosPorId(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = segurosController.consultaPorId("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCrear() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(segurosService.crear(isA(Seguro.class))).thenReturn(respApi);
		
		Seguro seguro = new Seguro();
		seguro.setEstatus("");
		seguro.getEstatus();
		seguro.setFechaRegistro("");
		seguro.getFechaRegistro();
		seguro.setId("");
		seguro.getId();
		seguro.setNombre("");
		seguro.getNombre();
		
		PremioSeguro premio = new PremioSeguro();
		premio.setDias(Arrays.asList());
		premio.getDias();
		premio.setEstatus("");
		premio.getEstatus();
		premio.setHoraFin("");
		premio.getHoraFin();
		premio.setHoraInicio("");
		premio.getHoraInicio();
		premio.setId("");
		premio.getId();
		
		BeanMensaje msj = new BeanMensaje();
		msj.setMensaje("");
		msj.getMensaje();
		msj.setTitulo("");
		msj.getTitulo();
		
		premio.setMensajeCajero(msj);
		premio.getMensajeCajero();
		
		BeanMensajeDetallado detalle = new BeanMensajeDetallado();
		detalle.setNegritas("");
		detalle.getNegritas();
		detalle.setTamanio("");
		detalle.getTamanio();
		detalle.setTexto("");
		detalle.getTexto();
		
		BeanMensajeConDetalle msjDetalle = new BeanMensajeConDetalle();
		msjDetalle.setMensaje(detalle);
		msjDetalle.getMensaje();
		msjDetalle.setTitulo(detalle);
		msjDetalle.getTitulo();
		
		premio.setMensajeTicket(msjDetalle);
		premio.getMensajeTicket();
		premio.setMoneda("");
		premio.getMoneda();
		premio.setMontoMinimo(0);
		premio.getMontoMinimo();
		premio.setNombre("");
		premio.getNombre();		
		premio.setRegiones(new SegmentoSucursal());
		premio.getRegiones();
		premio.setTipoCliente(Arrays.asList());
		premio.getTipoCliente();
		premio.setTipoOperacion(Arrays.asList());
		premio.getTipoOperacion();		
		
		seguro.setPremiosSeguros(Arrays.asList(premio));
		
		ResponseEntity<ResponseApi> response = segurosController.crear(seguro);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testActualizar() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(segurosService.actualizar(isA(Seguro.class))).thenReturn(respApi);
				
		ResponseEntity<ResponseApi> response = segurosController.actualizar(new Seguro());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaEstatus() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(segurosService.actualizarEstatus(isA(ReqEstatusSeguro.class))).thenReturn(respApi);
				
		ReqEstatusSeguro request = new ReqEstatusSeguro();
		request.setEstatus("");
		request.getEstatus();
		request.setId("");
		request.getId();
		
		ResponseEntity<ResponseApi> response = segurosController.actualizaEstatus(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
}
