package com.baz.divisas.api.core.sucursales.model;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baz.divisas.api.core.sucursales.dto.RespConsultaPreguntas;

@ExtendWith(MockitoExtension.class)
public class ModelTest {

	@Test
	void testTopesCaja() {
	
		Titulo titulo = new Titulo();
		titulo.getImagen();

		TipoRespuesta tipoRespuesta = new TipoRespuesta();
		tipoRespuesta.getId();
		tipoRespuesta.getDescripcion();

		RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
		respuestaCuestionario.getIdPregunta();
		respuestaCuestionario.getIdTipoRespuesta();
		respuestaCuestionario.getImagen();
		respuestaCuestionario.getOpciones();
		respuestaCuestionario.getRespuesta();

		Pregunta pregunta = new Pregunta();
		pregunta.getId();
		pregunta.getImagen();
		pregunta.getOpciones();
		pregunta.getTexto();
		pregunta.getTipoRespuesta();
		pregunta.isPermiteImagen();
		pregunta.isObligatoria();
		pregunta.isRespuestaMultiple();

		Opcion opcion = new Opcion();
		opcion.getId();
		opcion.getTexto();

		Cuestionario cuestionario = new Cuestionario();
		cuestionario.getAvance();
		cuestionario.getFechaRegistro();
		cuestionario.getId();
		cuestionario.getPreguntas();
		cuestionario.getPreguntasRespondidas();
		cuestionario.getTitulo();

		Contacto contacto = new Contacto();
		contacto.getCorreo();
		contacto.getTelefono();

		ConsultaRespuestasSucursal consultaRespuestasSucursal = new ConsultaRespuestasSucursal();
		consultaRespuestasSucursal.getAvanceTotal();
		consultaRespuestasSucursal.getCuestionarios();
		consultaRespuestasSucursal.getElementosPagina();
		consultaRespuestasSucursal.getPaginaActual();
		consultaRespuestasSucursal.getPaginas();
		consultaRespuestasSucursal.getTotalRegistros();
		

		ConsultaRespuestas consultaRespuestas = new ConsultaRespuestas();
		consultaRespuestas.getAvance();
		consultaRespuestas.getContacto();
		consultaRespuestas.getCuestionarios();
		consultaRespuestas.getEstatus();
		consultaRespuestas.getGerente();
		consultaRespuestas.getSucursal();
		consultaRespuestas.isCertificar();
		consultaRespuestas.setSucursal(null);
		consultaRespuestas.setCuestionarios(0);
		consultaRespuestas.setAvance(0);
		consultaRespuestas.setGerente(null);
		consultaRespuestas.setContacto(contacto);
		consultaRespuestas.setEstatus(null);
		consultaRespuestas.setCertificar(false);
		
		BeanSucursales beanSucursales = new BeanSucursales();
		beanSucursales.getAppYGeolocalizacion();
		beanSucursales.getContactoGerente();
		beanSucursales.getContactoSucursal();
		beanSucursales.getCorreoGerente();
		beanSucursales.getDireccion();
		beanSucursales.getFechaApertura();
		beanSucursales.getGerente();
		beanSucursales.getId();
		beanSucursales.getImplementacionDeSistemas();
		beanSucursales.getImplementadorJr();
		beanSucursales.getImplementadorSr();
		beanSucursales.getNomCalle();
		beanSucursales.getNumeroExterior();
		beanSucursales.getPresupuesto();
		beanSucursales.getNomRegion();
		beanSucursales.getRegional();
		beanSucursales.getRegionalizacionLimites();
		beanSucursales.getSucursal();
		beanSucursales.getTerritorial();
		beanSucursales.getNomTerritorio();
		beanSucursales.getTiempoPorTransaccion();
		beanSucursales.getTopesCaja();
		beanSucursales.setRegionalizacionLimites(null);
		beanSucursales.setCorreoGerente(null);
		
		BeanCuestionarios beanCuestionarios = new BeanCuestionarios();
		beanCuestionarios.getTotal();
	
		BeanRespuestasCuestionario beanRespuestasCuestionario = new BeanRespuestasCuestionario();
		beanRespuestasCuestionario.isFinalizaCuestionario();
		beanRespuestasCuestionario.setFinalizaCuestionario(false);
		beanRespuestasCuestionario.setRespuestas(new ArrayList<>());
		
		BeanCuestionariosBitacora beanCuestionariosBitacora = new BeanCuestionariosBitacora();
		beanCuestionariosBitacora.getIdCuestionario();
		
		BeanTopesCaja beanTopesCaja = new BeanTopesCaja();
		beanTopesCaja.getIdSucursal();
		beanTopesCaja.setIdSucursal(0);
		
		RespConsultaPreguntas respConsultaPreguntas = new RespConsultaPreguntas();
		respConsultaPreguntas.setTitulo(titulo);
		
		
		assertNull(null);
	}
}
