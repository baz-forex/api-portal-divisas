package com.baz.divisas.api.tef.sucursales.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SucursalReporteTest {
	@Test
	public void testRequest() {
		SucursalReporte  req = new SucursalReporte();
		req.setId("");
		req.getId();
		req.setFechaUltimoCambio("");
		req.getFechaUltimoCambio();
		
		req.setTcSucursalCompraUsd(12.2);
		req.getTcSucursalCompraUsd();
		req.setTcSucursalCompraCad(12.2);
		req.getTcSucursalCompraCad();
		req.setTcSucursalCompraEur(12.2);
		req.getTcSucursalCompraEur();
		req.setTcSucursalCompraOlp(12.2);
		req.getTcSucursalCompraOlp();
		
		req.setTcSucursalVentaUsd(12.2);
		req.getTcSucursalVentaUsd();
		req.setTcSucursalVentaCad(12.2);
		req.getTcSucursalVentaCad();
		req.setTcSucursalVentaEur(12.2);
		req.getTcSucursalVentaEur();
		req.setTcSucursalVentaOlp(12.2);
		req.getTcSucursalVentaOlp();
		
		req.setEstrategiaCompraUsd(0.21);
		req.getEstrategiaCompraUsd();
		req.setEstrategiaCompraCad(0.21);
		req.getEstrategiaCompraCad();
		req.setEstrategiaCompraEur(0.21);
		req.getEstrategiaCompraEur();
		req.setEstrategiaCompraOlp(0.21);
		req.getEstrategiaCompraOlp();
		
		req.setEstrategiaVentaUsd(0.21);
		req.getEstrategiaVentaUsd();
		req.setEstrategiaVentaCad(0.21);
		req.getEstrategiaVentaCad();
		req.setEstrategiaVentaEur(0.21);
		req.getEstrategiaVentaEur();
		req.setEstrategiaVentaOlp(0.21);
		req.getEstrategiaVentaOlp();
		
		req.setDivisaUsd("");
		req.getDivisaUsd();
		req.setDivisaCad("");
		req.getDivisaCad();
		req.setDivisaEur("");
		req.getDivisaEur();
		req.setDivisaOlp("");
		req.getDivisaOlp();
		req.setCalleSucursalArchivo("");
		req.getCalleSucursalArchivo();
		req.setCpSucursalArchivo("");
		req.getCpSucursalArchivo();
		req.setEstadoSucursalArchivo("");
		req.getEstadoSucursalArchivo();
		req.setMunicipioSucursalArchivo("");
		req.getMunicipioSucursalArchivo();
		req.setNombreSucursalArchivo("");
		req.getNombreSucursalArchivo();
		req.setNumeroRegionArchivo("");
		req.getNumeroRegionArchivo();
		req.setNumeroSucursalArchivo("");
		req.getNumeroSucursalArchivo();
		req.setRegionSucursalArchivo("");
		req.getRegionSucursalArchivo();
		req.setTipoSucursalArchivo("");
		req.getTipoSucursalArchivo();
	}
	
}
