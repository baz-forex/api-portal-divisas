package com.baz.divisas.api.tef.competencias.services.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.tef.competencias.dao.CargaCompetenciaDao;
import com.baz.divisas.api.tef.competencias.dto.BusquedaCeCo;
import com.baz.divisas.api.tef.competencias.dto.CeCo;
import com.baz.divisas.api.tef.competencias.dto.Competencias;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CargaCompetenciasServiceImplTest {

	@InjectMocks
	CargaCompetenciasServiceImpl competenciasService;

	@Mock
	CargaCompetenciaDao competenciasDao;

	@Test
	void testConsultaCompetenciasFecha() {
		
		ResponseApi response = competenciasService.consultaCompetenciasFecha("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------

		when(competenciasDao.buscaCompetencias(isA(String.class))).thenReturn(null);
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------

		when(competenciasDao.buscaCompetencias(isA(String.class))).thenReturn(Arrays.asList());
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		Document competenciaSucursal1 = new Document("resultado", new Document("idSucursal", "1"));
		Document competenciaSucursal2 = new Document("resultado", new Document("idSucursal", "2"));
		
		when(competenciasDao.buscaCompetencias(isA(String.class))).thenReturn(Arrays.asList(competenciaSucursal1, competenciaSucursal2));
		when(competenciasDao.buscaSucursales(any())).thenReturn(null);
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------

		when(competenciasDao.buscaSucursales(any())).thenReturn(Arrays.asList());
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		Sucursal sucursal = new Sucursal();
		sucursal.setSucursal(0);
		
		Sucursal sucursal1 = new Sucursal();
		sucursal1.setSucursal(1);
		
		when(competenciasDao.buscaSucursales(any())).thenReturn(Arrays.asList(sucursal, sucursal1));
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------

		Document ceco = new Document("tipo", new Document("nombreCompetencia", "test"))
							 .append("idSucursal", "1")
							 .append("id", ObjectId.get().toHexString());
		
		when(competenciasDao.buscaSucursalesCeCo(any(), any())).thenReturn(Arrays.asList(ceco));
		when(competenciasDao.buscaSucursalesSinCeCo(any(), any())).thenReturn(Arrays.asList(ceco));
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------

		ceco.append("estado", "");
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------

		ceco.append("municipio", "");
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------

		ceco.append("ciudad", "");
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------

		ceco.append("estado", null);
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------

		ceco.append("estado", "");
		ceco.append("ciudad", null);
		
		response = competenciasService.consultaCompetenciasFecha("2000-01-01");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	@Test
	void testConsultaCompetenciasAgrupadas() {
		
		when(competenciasDao.buscaCompetenciaAgrupadas()).thenReturn(null);

		ResponseApi response = competenciasService.consultaCompetenciasAgrupadas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------

		when(competenciasDao.buscaCompetenciaAgrupadas()).thenReturn(Arrays.asList());

		response = competenciasService.consultaCompetenciasAgrupadas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------

		when(competenciasDao.buscaCompetenciaAgrupadas()).thenReturn(Arrays.asList(new Document()));

		response = competenciasService.consultaCompetenciasAgrupadas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testModelos() {
		
		CeCo ceco =  new  CeCo(1, "", "", "", null);
		ceco = new CeCo();
		ceco.setCiudad("");
		ceco.getCiudad();
		ceco.setCompetencias(Arrays.asList());
		ceco.getCompetencias();
		ceco.setEstado("");
		ceco.getEstado();
		ceco.setId("");
		ceco.getId();
		ceco.setIdSucursal(1);
		ceco.getIdSucursal();
		ceco.setMunicipio("");
		ceco.getMunicipio();
		
		BusquedaCeCo buscaCeCo = new BusquedaCeCo();
		buscaCeCo.setListaCeCo(Arrays.asList());
		buscaCeCo.getListaCeCo();
		buscaCeCo.setListaSinCeCo(Arrays.asList());
		buscaCeCo.getListaSinCeCo();

		
		Competencias competencia = new Competencias("", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		competencia = new Competencias();
		competencia.getCanadienseCompra();
		competencia.getCanadienseVenta();
		competencia.getDolarCompra();
		competencia.getDolarVenta();
		competencia.getEuroCompra();
		competencia.getEuroVenta();
		competencia.getNombreCompetencia();
		
		competencia.setCanadienseCompra(0.0);
		competencia.setCanadienseVenta(0.0);
		competencia.setDolarCompra(0.0);
		competencia.setDolarVenta(0.0);
		competencia.setEuroCompra(0.0);
		competencia.setEuroVenta(0.0);
		competencia.setNombreCompetencia("");
		
		ResponseApi response = new ResponseApi(EstatusResponse.OK);
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

}
