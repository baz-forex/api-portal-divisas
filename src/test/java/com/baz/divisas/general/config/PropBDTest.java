package com.baz.divisas.general.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PropBDTest {
	

	@Test
	public void valid() {
		PropBD.getAuthBD();
		PropBD.getAuthClientesApiDivColeccion();
		PropBD.getBitacoraColeccion();
		PropBD.getBitacoraCuestionarios();
		PropBD.getBitacoraNotificacionesMW();
		PropBD.getCajaAnclada();
		PropBD.getCajaPrincipal();
		PropBD.getCajero();
		PropBD.getCatMonedasColeccion();
		PropBD.getCatSegmentoLealtad();
		PropBD.getCatSpot();
		PropBD.getCatSpreads();
		PropBD.getCatTipoOperacionColeccion();
		PropBD.getCatUbicacionSucursales();
		PropBD.getCatValoresRentabilidad();
		PropBD.getCompetenciasPromoEspacio();
		PropBD.getCotizacionesApp();
		PropBD.getCuestionarios();
		PropBD.getCuestionariosHistorico();
		PropBD.getDivisa();
		PropBD.getIncidenciasBCDColeccion();
		PropBD.getIncidenciasBD();
		PropBD.getLimitesClienteColeccion();
		PropBD.getLlavesClienteColeccion();
		PropBD.getLlavesColeccion();
		PropBD.getMiddlewareBD();
		PropBD.getModulosColeccion();
		PropBD.getMonitoreoBD();
		PropBD.getNegociosMW();
		PropBD.getNotificacionesMW();
		PropBD.getOperacionesMW();
		PropBD.getParametriaCotizacionesMW();
		PropBD.getParametriaGeneral();
		PropBD.getParametriaRascadito();
		PropBD.getRegionesColeccion();
		PropBD.getRegionesColeccionOlp();
		PropBD.getRespuestasCuestionarios();
		PropBD.getScimGruposColeccion();
		PropBD.getSegmentosColeccion();
		PropBD.getSegmentosColeccionOlp();
		PropBD.getSeguros();
		PropBD.getSucursales();
		PropBD.getTerminalFinancieroBD();
		PropBD.getTopeCaja();
		PropBD.getUriMongoDB();
		PropBD.getUsuariosColeccion();
		PropBD.getScimUsuariosColeccion();
		PropBD.getCatSegmentoRentabilidad();
	}
}
