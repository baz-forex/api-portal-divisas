package com.baz.divisas.api.tef.sucursales.dto;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.tef.sucursales.model.Divisa;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReqActSucursalTest {

	@Test
	public void testRequest() {
		ReqActSucursal  req = new ReqActSucursal();
		req.setDivisas(new ArrayList<Divisa>());
		req.getDivisas();
		req.setCalleSucursalArchivo("");
		req.getCalleSucursalArchivo();
		req.setCpSucursalArchivo("");
		req.getCpSucursalArchivo();
		req.setEstadoSucursalArchivo("");
		req.getEstadoSucursalArchivo();
		req.setIdsucursal("");
		req.getIdsucursal();
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
		req.setTcOlp(new Divisa());
		req.getTcOlp();
		req.setTipoSucursalArchivo("");
		req.getTipoSucursalArchivo();
	}
}
