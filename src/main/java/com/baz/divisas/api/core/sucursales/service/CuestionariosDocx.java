package com.baz.divisas.api.core.sucursales.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.bson.Document;

import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqFiltroCuestionarios;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.client.AggregateIterable;

public class CuestionariosDocx {

	private final CertificacionDao certificacionDao;

	/**
	 * Constructor para Pruebas Unitarias
	 * 
	 * @param certificacionDao - Mock
	 */
	public CuestionariosDocx(CertificacionDao certificacionDao) {
		this.certificacionDao = certificacionDao;
	}

	/**
	 * Metodo para generar documento .docx con los Cuestionarios
	 * 
	 * @return objeto XWPFDocument con los cuestionarios obtenidos
	 */
	public XWPFDocument generaCuestionarioDocx(ReqFiltroCuestionarios reqFiltroCuestionarios) {

		try (FileOutputStream out = new FileOutputStream("ReporteCuestionario.docx")) {
			XWPFDocument document = new XWPFDocument();
			// Crear un párrafo para el título
			XWPFParagraph titleParagraph = document.createParagraph();
			titleParagraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun titleRun = titleParagraph.createRun();
			titleRun.setText(
					"Cuestionarios de la Sucursal " + reqFiltroCuestionarios.getSucursal());
			titleRun.setBold(true);
			titleRun.setFontSize(16);

			AggregateIterable<Document> cuestionarios = certificacionDao
					.getCuestionariosFiltroHis(reqFiltroCuestionarios);

			for (Document cuestionario : cuestionarios) {
				titleParagraph = document.createParagraph();
				titleParagraph.setAlignment(ParagraphAlignment.LEFT);
				titleRun = titleParagraph.createRun();
				titleRun.setText("Fecha: " + cuestionario.getString("fechaRegistro"));
				titleRun.setBold(false);
				titleRun.setFontSize(14);

				titleParagraph = document.createParagraph();
				titleParagraph.setAlignment(ParagraphAlignment.LEFT);
				titleRun = titleParagraph.createRun();

				Document titulo = cuestionario.get("titulo", Document.class);

				titleRun.setText("Título:  " + titulo.getString("texto"));
				titleRun.setBold(true);
				titleRun.setFontSize(14);

				List<Document> respuesta = cuestionario.getList("respuestas", Document.class);

				int contador = 0;

				for (Document pregunta : cuestionario.getList("preguntas", Document.class)) {
					titleParagraph = document.createParagraph();
					titleParagraph.setAlignment(ParagraphAlignment.LEFT);
					titleRun = titleParagraph.createRun();
					titleRun.setText(
							"Pregunta " + (contador + 1) + ": " + pregunta.getString("texto"));

					titleParagraph = document.createParagraph();
					titleParagraph.setAlignment(ParagraphAlignment.LEFT);
					titleRun.setBold(false);
					titleRun.setFontSize(14);
					titleRun = titleParagraph.createRun();

					String idPregunta = pregunta.get("tipoRespuesta", Document.class)
							.getString("_id");

					if ((Integer.parseInt(idPregunta) == 2)
							|| (Integer.parseInt(idPregunta) == 4)) {
						String resp = "";

						for (Document opcion : respuesta.get(contador).getList("opciones",
								Document.class)) {
							resp += opcion.getString("texto") + "   ";
						}

						titleRun.setText("Respuesta: " + resp);
					}
					else {
						titleRun.setText(
								"Respuesta: " + respuesta.get(contador).getString("respuesta"));
					}

					if ((Integer.parseInt(idPregunta) == 3
							&& "0".equals(respuesta.get(contador).getString("respuesta")))) {
						titleRun.setText(
								"   Motivo: " + respuesta.get(contador).getString("motivo"));
					}

					titleRun.setBold(false);
					titleRun.setFontSize(14);
					contador++;
				}

				titleParagraph = document.createParagraph();
				titleParagraph.setAlignment(ParagraphAlignment.LEFT);
				titleRun = titleParagraph.createRun();
				titleRun.setText(
						"**********************************************************************"
								+ "**********************************************************************"
								+ "**********************************************************************"
								+ "******************************");
			}

			document.write(out);
			return document;
		}
		catch (IOException e) {
			LogApp.mensaje("Error al generar el reporte: " + e.getMessage());

		}

		return null;
	}
}
