package com.baz.divisas.api.catalogos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.catalogos.model.CatRangoEdadesCampanias;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.catalogos.services.CatalogosServiceImpl;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CatalogosServiceImplTest {

	@InjectMocks
	private CatalogosServiceImpl catalogosService;
	
	@Mock
	private CatalogosDao catalogosDao;
	@Mock
	private RegionesDao regionesDao;
	@Mock
	private SegmentosDao segmentosDao;
	@Mock
	private BitacoraServiceImpl bitacora;
	
	@Test
	void testGetCatPaisNacionalidad() {
		
		List<CatPaisNacionalidad> paisesNacionalidades = new ArrayList<>();
		
		when(catalogosDao.findCatPaisNacionalidad()).thenReturn(paisesNacionalidades);
		ResponseApi response = catalogosService.getCatPaisNacionalidad();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
			
		////////////////////
		paisesNacionalidades.add(new CatPaisNacionalidad());

		response = catalogosService.getCatPaisNacionalidad();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatEstados() {
		
		List<Catalogo> estados = new ArrayList<>();
		
		when(catalogosDao.findCatEstados()).thenReturn(estados);
		ResponseApi response = catalogosService.getCatEstados();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
				
		////////////////////
		estados.add(new Catalogo());

		response = catalogosService.getCatEstados();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
		
	@Test
	void testGetCatDivisas() {
		
		List<Catalogo> divisas = new ArrayList<>();
		
		when(catalogosDao.findCatDivisas()).thenReturn(divisas);
		ResponseApi response = catalogosService.getCatDivisas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
				
		////////////////////
		divisas.add(new Catalogo());

		response = catalogosService.getCatDivisas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatIdentificaciones() {
		
		List<Catalogo> identificaciones = new ArrayList<>();
		
		when(catalogosDao.findCatIdentificaciones()).thenReturn(identificaciones);
		ResponseApi response = catalogosService.getCatIdentificaciones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
				
		////////////////////
		identificaciones.add(new Catalogo());

		response = catalogosService.getCatIdentificaciones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testActualizaEstatusDivisa() {
			
		when(catalogosDao.actualizarEstatusCatDivisas("", true)).thenReturn(false);
		ResponseApi response = catalogosService.actualizaEstatusDivisa(new Catalogo("", "", true));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		
		when(catalogosDao.actualizarEstatusCatDivisas("", true)).thenReturn(true);
		response = catalogosService.actualizaEstatusDivisa(new Catalogo("", "", true));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testCreaDivisa() {
			
		when(catalogosDao.findCatDivisasId("")).thenReturn(Arrays.asList(new Catalogo()));
		ResponseApi response = catalogosService.creaDivisa(new Catalogo("", ""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());

		////////////////

		when(catalogosDao.findCatDivisasId("")).thenReturn(Arrays.asList());
		when(bitacora.registrar(isA(AccionBitacora.class), isA(String.class), any(), any())).thenReturn("");
		
		InsertOneResult insertResult = InsertOneResult.acknowledged(null);
		when(catalogosDao.insertDivisa(isA(Document.class))).thenReturn(insertResult);

		response = catalogosService.creaDivisa(new Catalogo("", ""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		////////////////
		insertResult = InsertOneResult.acknowledged(new BsonObjectId());
		when(catalogosDao.insertDivisa(isA(Document.class))).thenReturn(insertResult);
		
		UpdateResult updateResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(regionesDao.addDivisa(isA(Divisa.class))).thenReturn(updateResult);
		
		when(catalogosDao.resetLimitesRegion(isA(String.class))).thenReturn(null);
		
		response = catalogosService.creaDivisa(new Catalogo("", ""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		////////////////
		updateResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(regionesDao.addDivisa(isA(Divisa.class))).thenReturn(updateResult);
		
		UpdateResult updateSegmentoResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(segmentosDao.addDivisa(isA(Divisa.class))).thenReturn(updateSegmentoResult);
				
		response = catalogosService.creaDivisa(new Catalogo("", ""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		////////////////	
		updateSegmentoResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(segmentosDao.addDivisa(isA(Divisa.class))).thenReturn(updateSegmentoResult);
				
		response = catalogosService.creaDivisa(new Catalogo("", ""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatEstatusCotizacion() {
		
		List<Catalogo> estatus = new ArrayList<>();
		
		when(catalogosDao.findCatEstatusCotizacion()).thenReturn(estatus);
		ResponseApi response = catalogosService.getCatEstatusCotizacion();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		////////////////////
		estatus.add(new Catalogo());

		response = catalogosService.getCatEstatusCotizacion();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatTipoOperacion() {
		
		List<Catalogo> tipoOperacion = new ArrayList<>();
		
		when(catalogosDao.findCatTipoOperacion()).thenReturn(tipoOperacion);
		ResponseApi response = catalogosService.getCatTipoOperacion();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		////////////////////
		tipoOperacion.add(new Catalogo());

		response = catalogosService.getCatTipoOperacion();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatTipoCliente() {
		
		List<Catalogo> tipoCliente = new ArrayList<>();
		
		when(catalogosDao.findCatTipoCliente()).thenReturn(tipoCliente);
		ResponseApi response = catalogosService.getCatTipoCliente();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		////////////////////
		tipoCliente.add(new Catalogo());

		response = catalogosService.getCatTipoCliente();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatPerfilClienteCampanias() {
		
		List<Catalogo> perfilCliente = new ArrayList<>();
		
		when(catalogosDao.findCatPerfilClienteCampanias()).thenReturn(perfilCliente);
		ResponseApi response = catalogosService.getCatPerfilClienteCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		////////////////////
		perfilCliente.add(new Catalogo());

		response = catalogosService.getCatPerfilClienteCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatTipoLimiteCampanias() {
		
		List<Catalogo> tipoLimite = new ArrayList<>();
		
		when(catalogosDao.findCatTipoLimiteCampanias()).thenReturn(tipoLimite);
		ResponseApi response = catalogosService.getCatTipoLimiteCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		////////////////////
		tipoLimite.add(new Catalogo());

		response = catalogosService.getCatTipoLimiteCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatTipoAcumuladoCampanias() {
		
		List<Catalogo> tipoAcumulado = new ArrayList<>();
		
		when(catalogosDao.findCatTipoAcumuladoCampanias()).thenReturn(tipoAcumulado);
		ResponseApi response = catalogosService.getCatTipoAcumuladoCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		////////////////////
		tipoAcumulado.add(new Catalogo());

		response = catalogosService.getCatTipoAcumuladoCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatEstatusCampanias() {
		
		List<Catalogo> estatus = new ArrayList<>();
		
		when(catalogosDao.findCatEstatusCampanias()).thenReturn(estatus);
		ResponseApi response = catalogosService.getCatEstatusCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		////////////////////
		estatus.add(new Catalogo());

		response = catalogosService.getCatEstatusCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatRangoEdadesCampanias() {
		
		List<CatRangoEdadesCampanias> rangoEdades = new ArrayList<>();
		
		when(catalogosDao.findCatRangoEdadesCampanias()).thenReturn(rangoEdades);
		ResponseApi response = catalogosService.getCatRangoEdadesCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		////////////////////
		CatRangoEdadesCampanias rango = new CatRangoEdadesCampanias();
		rango.setmaximo(0);
		rango.getmaximo();
		rango.setminimo(0);
		rango.getMinimo();
		
		rangoEdades.add(rango);

		response = catalogosService.getCatRangoEdadesCampanias();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
}
