package com.baz.divisas.api.core.sucursales;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.validation.Valid;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqAsignarSucursales;
import com.baz.divisas.api.core.sucursales.dto.ReqFiltroCuestionarios;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginadoBitacora;
import com.baz.divisas.api.core.sucursales.dto.ReqPregRespSucursal;
import com.baz.divisas.api.core.sucursales.dto.ReqRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.service.CertificacionConsultasHistImpl;
import com.baz.divisas.api.core.sucursales.service.CertificacionConsultasImpl;
import com.baz.divisas.api.core.sucursales.service.CertificacionInsercionesImpl;
import com.baz.divisas.api.core.sucursales.service.CertificacionModificacionesImpl;
import com.baz.divisas.api.core.sucursales.service.CuestionariosDocx;
import com.baz.divisas.api.core.sucursales.service.CuestionariosXls;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase CertificacionController
 * 
 * @author 1096526
 *
 */
@RestController
@RequestMapping("/api/core/sucursales")
@Api(tags = "certificacion sucursales")
@Validated
public class CertificacionController {

	@Autowired
	private CertificacionInsercionesImpl certificacionInsercionesImpl;

	@Autowired
	private CertificacionConsultasImpl certificacionConsultasImpl;

	@Autowired
	private CertificacionConsultasHistImpl certificacionConsultasHistImpl;

	@Autowired
	private CertificacionModificacionesImpl certificacionModificacionesImpl;

	@Autowired
	private CertificacionDao certificacionDao;

	/**
	 * Constructor de la clase
	 */
	public CertificacionController() {
		super();
	}

	/**
	 * Servicio para consultar sucursales CeCo
	 * 
	 * @param reqPaginado datos del paginado
	 * 
	 * @return datos de la consulta
	 * 
	 */
	@PostMapping(value = "/consulta", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Consulta las sucursales del CeCo")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaSucursales(@RequestBody ReqPaginado reqPaginado) {

		// Consulta las sucursales activas del CeCo
		return certificacionConsultasImpl.consultaSucursales(reqPaginado);

	}

	/**
	 * Crea un nuevo cuestionario
	 *
	 * @param reqNuevoCuestionario the reqNuevoCuestionario
	 * 
	 * @return the response entity
	 */
	@PostMapping(value = "nuevo-cuestionario", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Crea un nuevo cuestionario")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi creaCuestionario(
			@RequestBody @Validated BeanCuestionarios reqNuevoCuestionario) {

		// Crea un nuevo cuestionario
		return certificacionInsercionesImpl.creaCuestionario(reqNuevoCuestionario);

	}

	/**
	 * Metodo de carga archivo de actualizacion masiva
	 * 
	 * @param file - Archivo de actualizacion masiva
	 * 
	 * @return ResponseApi
	 */
	@PostMapping(value = "carga-masiva")
	@ApiOperation(value = "Carga masiva")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseEntity<ResponseApi> servicioCargaMasiva(
			@RequestParam("file") MultipartFile file) {

		// Realiza la carga masiva de sucursales
		ResponseApi resultado = certificacionInsercionesImpl.servicioCargaMasiva(file);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Consulta en detalle los cuestionarios
	 *
	 * @param reqPaginado the reqPaginado
	 * 
	 * @return the response entity
	 */
	@PostMapping(value = "consulta-cuestionarios", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Consulta el detalle de los cuestionarios")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaCuestionarios(@RequestBody @Validated ReqPaginado reqPaginado) {

		// Consulta los cuestionarios
		return certificacionConsultasImpl.consultaCuestionarios(reqPaginado);

	}

	/**
	 * Consulta las preguntas de los cuestionarios
	 *
	 * @param id the id
	 * 
	 * @return the response entity
	 */
	@GetMapping(value = "consulta-preguntas/{id}", produces = "application/json")
	@ApiOperation(value = "Consulta las preguntas de los cuestionarios")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaPreguntas(@PathVariable String id) {

		// Consulta las preguntas de un cuestionario
		return certificacionConsultasImpl.consultaPreguntas(id);

	}

	/**
	 * Actualiza el contenido de un cuestionario
	 * 
	 * @param reqActualizaCuestionario - Solciitud con los datos a actualizar
	 * 
	 * @return objeto ResponseApi con la respuesdta del servicio
	 */
	@PutMapping(value = "actualiza-cuestionario", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "actualiza el contenido de un cuestionario")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi actualizaCuestionario(
			@RequestBody BeanCuestionarios reqActualizaCuestionario) {

		// Actualiza las respuestas de un cuestionario
		return certificacionModificacionesImpl.actualizaCuestionario(reqActualizaCuestionario);

	}

	/**
	 * Actualiza el estatus de un cuestionario (bloqueado/desbloqueado)
	 *
	 * @param id the id
	 * 
	 * @return the response entity
	 */
	@PatchMapping(value = "actualiza-estatus-cuestionario/{id}", produces = "application/json")
	@ApiOperation(value = "actualiza el estatus de un cuestionario")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi actualizaEstatusCuestionario(@PathVariable String id) {

		// Actualiza el estatus de un cuestionario
		return certificacionModificacionesImpl.actualizaEstatusCuestionario(id);

	}

	/**
	 * Asigna sucursales a un cuestionario
	 *
	 * @param reqAsignarSucursales the reqAsignarSucursales
	 * 
	 * @return the response entity
	 */
	@PatchMapping(value = "asigna-cuestionario", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "asigna sucursales a un cuestionario")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi asignarSucursales(@RequestBody ReqAsignarSucursales reqAsignarSucursales) {

		// Asigna un cuestionario a una sucursal
		return certificacionModificacionesImpl.asignarSucursales(reqAsignarSucursales);

	}

	/**
	 * Consulta respuestas de los cuestionarios
	 *
	 * @param reqPaginado the reqPaginado
	 * 
	 * @return the response entity
	 */
	@PostMapping(value = "consulta-respuestas", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "consulta las respuestas de los cuestionarios")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaRespuestas(@RequestBody ReqPaginado reqPaginado) {

		// Consulta las respuestas de un cuestionario
		return certificacionConsultasImpl.consultaRespuestas(reqPaginado);

	}

	/**
	 * Metodo para Certificar una sucursal
	 *
	 * @param sucursal the sucursal
	 * 
	 * @return the response entity
	 */
	@PatchMapping(value = "certificacion/{sucursal}", produces = "application/json")
	@ApiOperation(value = "certifica una sucursal")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi certificacion(@PathVariable String sucursal) {

		// Certifica una sucursal
		return certificacionModificacionesImpl.certificaSucursal(sucursal);

	}

	/**
	 * Consulta respuestas de los cuestionarios por sucursal
	 *
	 * @param reqRespuestasSucursal the reqRespuestasSucursal
	 * 
	 * @return the response entity
	 */
	@PostMapping(value = "consulta-respuestas-sucursal", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "consulta las respuestas de los cuestionarios por sucursal")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaRespuestasSucursal(
			@RequestBody ReqRespuestasSucursal reqRespuestasSucursal) {

		// Consulta las respuestas de un cuestionario
		return certificacionConsultasImpl.consultaRespuestasSucursal(reqRespuestasSucursal);

	}

	/**
	 * Consulta el detalle de las preguntas
	 *
	 * @param reqPaginado the reqPaginado
	 * 
	 * @return the response entity
	 */
	@PostMapping(value = "consulta-cuestionarios-historico", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "consulta los cuestionarios del historico")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaCuestionariosHistorico(@RequestBody ReqPaginado reqPaginado) {

		// Consulta los cuestionarios del historico
		return certificacionConsultasHistImpl.consultaCuestionariosHistorico(reqPaginado);

	}

	/**
	 * Consulta de cuestionarios
	 *
	 * @param id the id
	 * 
	 * @return the response entity
	 */
	@GetMapping(value = "consulta-preguntas-cuestionarios-historico/{id}", produces = "application/json")
	@ApiOperation(value = "consulta las preguntas de los cuestionarios históricos")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaCuestionariosHist(@PathVariable String id) {

		return certificacionConsultasHistImpl.consultaPregCuestionariosHist(id);

	}

	/**
	 * ListaSucursales asignadas a un cuestionario del histórico
	 *
	 * @param id the id
	 * 
	 * @return the response entity
	 */
	@PatchMapping(value = "asignadas-historico/{id}", produces = "application/json")
	@ApiOperation(value = "sucursales asignadas a un cuestionario")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi sucursalesAsignadasHist(@PathVariable String id) {

		// Consulta las sucursales asignadas a un cuestionario del historico
		return certificacionConsultasImpl.sucursalesAsignadasHist(id);

	}

	/**
	 * Metodo para consultar las preguntas y respuestas de un cuestionario por
	 * sucursal
	 * 
	 * @param reqPregRespSucursal - request de la solicitud
	 * 
	 * @return objeto ResponseApi con la respuesta del servicio
	 */
	@PostMapping(value = "consulta-preguntas-respuestas", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "consulta las preguntas y respuestas de un cuestionario por sucursal")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaPreguntasRespuestas(
			@RequestBody ReqPregRespSucursal reqPregRespSucursal) {

		// Consulta las preguntas y respuestas de un cuestionario
		return certificacionConsultasImpl.consultaPreguntasRespuestas(reqPregRespSucursal);

	}

	/**
	 * Consulta el detalle de las preguntas
	 *
	 * @param reqPaginadoBitacora the reqPaginadoBitacora
	 * 
	 * @return the response entity
	 */
	@PostMapping(value = "consulta-bitacora-cuestionario", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "consulta los cuestionarios del historico")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseApi consultaBitacoraCuestionario(
			@RequestBody ReqPaginadoBitacora reqPaginadoBitacora) {

		// Consulta la bitacora de un cuestioanrio
		return certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora);

	}

	/**
	 * Permite descargar los cuestionarios en un archivo docx
	 * 
	 * @param reqFiltroCuestionarios - request de la solicitud
	 * 
	 * @return objeto ResponseEntity con la respuesta del servicio
	 */
	@PostMapping(value = "descargar-cuestionario-docx", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ApiOperation(value = "descarga los cuestionarios del historico como archivo DOCX")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseEntity<byte[]> descargarCuestionariosDocx(
			@RequestBody ReqFiltroCuestionarios reqFiltroCuestionarios) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (XWPFDocument document = new CuestionariosDocx(certificacionDao)
				.generaCuestionarioDocx(reqFiltroCuestionarios)) {
			document.write(outputStream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// Preparar el archivo para descargar
		byte[] bytes = outputStream.toByteArray();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "Cuestionarios.docx");

		return new ResponseEntity<>(bytes, headers, 200);
	}

	/**
	 * Permite descargar los cuestionarios en un archivo docx
	 * 
	 * @param reqFiltroCuestionarios - request de la solicitud
	 * 
	 * @return objeto ResponseEntity con la respuesta del servicio
	 */
	@PostMapping(value = "descargar-cuestionario-xlsx", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ApiOperation(value = "descarga los cuestionarios del historico como archivo XLSX")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")
	})
	public ResponseEntity<Resource> descargarCuestionariosXlsx(
			@Valid @RequestBody ReqFiltroCuestionarios reqFiltroCuestionarios) {

		InputStreamResource file = new InputStreamResource(
				new CuestionariosXls(certificacionDao).generaExel(reqFiltroCuestionarios));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cuestionarios.xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

}
