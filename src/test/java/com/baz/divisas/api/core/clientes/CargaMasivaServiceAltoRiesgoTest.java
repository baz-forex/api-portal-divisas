package com.baz.divisas.api.core.clientes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.clientes.dao.ClientesDao;
import com.baz.divisas.api.core.clientes.services.CargaMasivaServiceAltoRiesgo;
import com.baz.divisas.api.tef.sucursales.utils.ArchivosExcel;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CargaMasivaServiceAltoRiesgoTest {

	@InjectMocks
	private CargaMasivaServiceAltoRiesgo cargaMasivaService;
	
	@Mock
	private ClientesDao clientesDao;
	
	@Mock
	private BufferedReader br;
	
	private MultipartFile fileMock;
	ArchivosExcel lectorExcel;
	
	@Test
	void testCargaArchivoActualizacionMasiva() {
		
		MockMultipartFile file = new MockMultipartFile("Test", IOUtils.EMPTY_BYTE_ARRAY);
		ResponseApi response = cargaMasivaService.registraClientesAltoRiesgo(file, "123123", "sdadasd");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		file = new MockMultipartFile("test", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test\nTest".getBytes());
		response = cargaMasivaService.registraClientesAltoRiesgo(file, "123123", "sdadasd");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		
		byte[] excelFileContent = null;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("SampleSheet");

			// Create headers
			Row headerRow = sheet.createRow(0);
			String[] headers = { "cliente", "nombre", "apellido paterno", "apellido materno","fecha de nacimiento"};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Add sample data
			String[][] sampleData = {{"123213", "Israel", "Chavez", "", "1988-08-27"}, {"12321213", "Juan", "Chavez", "Lopez", "1988-08-27"}
			};

			for (int rowIdx = 0; rowIdx < sampleData.length; rowIdx++) {
				Row dataRow = sheet.createRow(rowIdx + 1);
				for (int colIdx = 0; colIdx < sampleData[rowIdx].length; colIdx++) {
					Cell cell = dataRow.createCell(colIdx);
					cell.setCellValue(sampleData[rowIdx][colIdx]);
				}
			}

			// Write the workbook content to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			excelFileContent = outputStream.toByteArray();
		}
		catch (IOException e) {

		}

		fileMock = new MockMultipartFile("prueba", "prueba.xlsx", "text/plain", excelFileContent);
		ResponseApi result = cargaMasivaService.registraClientesAltoRiesgo(fileMock, "123","123123");

		assertNotNull(result);
	}
}
