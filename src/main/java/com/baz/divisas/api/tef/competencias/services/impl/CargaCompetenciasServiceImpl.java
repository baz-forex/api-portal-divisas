package com.baz.divisas.api.tef.competencias.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.tef.competencias.dao.CargaCompetenciaDao;
import com.baz.divisas.api.tef.competencias.dto.BusquedaCeCo;
import com.baz.divisas.api.tef.competencias.dto.CeCo;
import com.baz.divisas.api.tef.competencias.dto.Competencias;
import com.baz.divisas.api.tef.competencias.services.ICargaCompetenciasService;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;


/**
 * The Class CargaCompetenciasServiceImpl.
 *
 * @author 1023825
 */
@Service
public class CargaCompetenciasServiceImpl implements ICargaCompetenciasService{

	private CargaCompetenciaDao competenciasDao;

	/**
	 * Constructor de la clase
	 *
	 * @param competenciasDao - CargaCompetenciaDao
	 */
	@Autowired
	public CargaCompetenciasServiceImpl(CargaCompetenciaDao competenciasDao) {
		super();
		this.competenciasDao = competenciasDao;
	}

	/**
	 * Consulta competencias fecha sucursal.
	 *
	 * @param fecha the fecha
	 * @return the response api
	 */
	@Override
	public ResponseApi consultaCompetenciasFecha(String fecha) {

		if (validarFecha(fecha)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("El formato de fecha es incorrecto tiene que ser yyyy-MM-dd"));
		}

		List<Document> buscaCompetencias = competenciasDao.buscaCompetencias(fecha);

		if (null == buscaCompetencias || buscaCompetencias.isEmpty()) {

			return new ResponseApi(EstatusResponse.SIN_RESULTADOS);
		}

		ResponseApi resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, "Sin resultado");

		List<Document> competenciasConCECO = new ArrayList<>();

		List<Document> competenciasSinCECO = new ArrayList<>();

		List<Integer> lista = new ArrayList<>();

		List<CeCo> listResponseCeCo = new ArrayList<>();

		BusquedaCeCo resultadoCeCo = new BusquedaCeCo();
		
		List<Sucursal> listaCeCo = competenciasDao.buscaSucursales(buscaCompetencias);

		if (listaCeCo != null && !listaCeCo.isEmpty()) {

			for (Sucursal sucursal : listaCeCo) {

				lista.add(sucursal.getSucursal());

			}

			for (Document listaBusquedaComp : buscaCompetencias) {
				Document resultadoComp = (Document) listaBusquedaComp.get("resultado");
				if (lista.contains(Integer.valueOf(resultadoComp.get("idSucursal").toString()))) {
					competenciasConCECO.add(resultadoComp);
				} else {
					competenciasSinCECO.add(resultadoComp);
				}

			}

			List<Document> listaCeCoParse = competenciasDao.buscaSucursalesCeCo(lista, fecha);

			for (Integer idSucursal : lista) {
				List<Competencias> listCompetencia = new ArrayList<>();
				CeCo responseCeCo = new CeCo();

				for (Document document : listaCeCoParse) {
					Document tipo = (Document) document.get("tipo");
					if (idSucursal.equals(Integer.valueOf(document.get("idSucursal").toString()))) {


						responseCeCo.setIdSucursal(Integer.valueOf(document.get("idSucursal").toString()));
						ObjectId id = new ObjectId(document.get("id").toString());
						document.get("id");
						document.put("id", id.toHexString());

						responseCeCo.setId(document.get("id").toString());


						if (null != document.get("estado") && null != document.get("municipio")
								&& null != document.get("ciudad")) {	

							responseCeCo.setEstado(document.get("estado").toString());
							responseCeCo.setCiudad(document.get("ciudad").toString());
							responseCeCo.setMunicipio(document.get("municipio").toString());

						} 
						else if (null == document.get("estado") && null != document.get("municipio")
								&& null != document.get("ciudad")) {

							responseCeCo.setCiudad(document.get("ciudad").toString());
							responseCeCo.setMunicipio(document.get("municipio").toString());

						} 
						else if (null != document.get("estado") && null != document.get("municipio")
								&& null == document.get("ciudad")) {

							responseCeCo.setEstado(document.get("estado").toString());
							responseCeCo.setMunicipio(document.get("municipio").toString());

						}

						if (null != tipo) {

							Competencias competenciaCeCo = new Competencias();

							competenciaCeCo.setNombreCompetencia(tipo.get("nombreCompetencia").toString());

							if (null != tipo.get("dolarCompra")) {
								competenciaCeCo.setDolarCompra(Double.valueOf(tipo.get("dolarCompra").toString()));
							}
							if (null != tipo.get("dolarVenta")) {
								competenciaCeCo.setDolarVenta(Double.valueOf(tipo.get("dolarVenta").toString()));

							}
							if (null != tipo.get("euroCompra")) {
								competenciaCeCo.setEuroCompra(Double.valueOf(tipo.get("euroCompra").toString()));
							}
							if (null != tipo.get("euroVenta")) {
								competenciaCeCo.setEuroVenta(Double.valueOf(tipo.get("euroVenta").toString()));
							}
							if (null != tipo.get("canadienseCompra")) {
								competenciaCeCo
								.setCanadienseCompra(Double.valueOf(tipo.get("canadienseCompra").toString()));
							}
							if (null != tipo.get("canadienseVenta")) {
								competenciaCeCo
								.setCanadienseVenta(Double.valueOf(tipo.get("canadienseVenta").toString()));
							}

							if (idSucursal.equals(Integer.valueOf(document.get("idSucursal").toString()))) {

								listCompetencia.add(competenciaCeCo);
							}

						}

					}

					responseCeCo.setCompetencias(listCompetencia);
				}
				listResponseCeCo.add(responseCeCo);
			}

			List<Document> listaSinCeCoParse = competenciasDao.buscaSucursalesSinCeCo(competenciasSinCECO, fecha);

			for (Document document : listaSinCeCoParse) {
				ObjectId id = new ObjectId(document.get("id").toString());
				document.get("id");
				document.put("id", id.toHexString());
			}

			resultadoCeCo.setListaCeCo(listResponseCeCo);
			resultadoCeCo.setListaSinCeCo(listaSinCeCoParse);

			resultado = new ResponseApi(EstatusResponse.OK, resultadoCeCo);

		}

		return resultado;
	}

	@Override
	public ResponseApi consultaCompetenciasAgrupadas() {

		List<Document> busquedaAgrupada = competenciasDao.buscaCompetenciaAgrupadas();

		if (null == busquedaAgrupada || busquedaAgrupada.isEmpty()) {
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS);
		}


		return new ResponseApi(EstatusResponse.OK,busquedaAgrupada);

	}

	private static boolean validarFecha(String fecha) {
		boolean correcto = false;

		try {
			//Formato de fecha (año-mes-dia)
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			formatoFecha.setLenient(false);
			//Comprobación de la fecha
			formatoFecha.parse(fecha);
			correcto = false;
		} 
		catch (ParseException e) {
			//Si la fecha no es correcta, pasará por aquí
			correcto = true;
		}

		return correcto;
	}


}
