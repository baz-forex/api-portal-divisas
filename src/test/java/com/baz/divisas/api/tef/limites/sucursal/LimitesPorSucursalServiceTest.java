package com.baz.divisas.api.tef.limites.sucursal;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.limites.dao.LimitesPorSucursalDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.baz.divisas.api.tef.limites.services.impl.LimitesPorSucursalService;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.api.tef.sucursales.utils.ArchivosExcel;
import com.baz.divisas.general.dto.ResponseApi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class LimitesPorSucursalServiceTest {
    @InjectMocks
    private LimitesPorSucursalService limitesPorSucursalService;

    @Mock
    private LimitesPorSucursalDao limitesPorSucursalDao;
    @Mock
    private BitacoraServiceImpl bitacoraService;

    @Mock
    private SucursalesDao sucursalesDao;
    
    private MultipartFile fileMock;
	ArchivosExcel lectorExcel;
    @Test
    void testConsultaLimitesSucursal() {

        when(limitesPorSucursalDao.findLimitesSucursal()).thenReturn(Arrays.asList());
        
        ResponseApi response = limitesPorSucursalService.consultaLimitesSucursal();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorSucursalDao.findLimitesSucursal()).thenReturn(Arrays.asList(new LimitesPorSucursalDto()));
        response = limitesPorSucursalService.consultaLimitesSucursal();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());


    }

    @Test
    void testActualizaLimiteSucursal() {

    	when(limitesPorSucursalDao.findLimitesSucursalId(isA(LimitesPorSucursalDto.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorSucursalService.actualizaLimiteSucursal(new LimitesPorSucursalDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        when(limitesPorSucursalDao.findLimitesSucursalId(isA(LimitesPorSucursalDto.class))).thenReturn(Arrays.asList(new LimitesPorSucursalDto()));

        response = limitesPorSucursalService.actualizaLimiteSucursal(new LimitesPorSucursalDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

    }
    
    @Test
    void testActualizaEstatusLimiteSucursal() {

    	when(limitesPorSucursalDao.findLimitesSucursalId(isA(LimitesPorSucursalDto.class))).thenReturn(Arrays.asList());

        ResponseApi response = limitesPorSucursalService.actualizaEstatusLimiteSucursal(new LimitesPorSucursalDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        
        // -------------------------------
        
        when(limitesPorSucursalDao.findLimitesSucursalId(isA(LimitesPorSucursalDto.class))).thenReturn(Arrays.asList(new LimitesPorSucursalDto()));

        response = limitesPorSucursalService.actualizaEstatusLimiteSucursal(new LimitesPorSucursalDto());
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testConsultaLimitesSucursalOlp() {

        when(limitesPorSucursalDao.findLimitesSucursalOlp()).thenReturn(Arrays.asList());
        
        ResponseApi response = limitesPorSucursalService.consultaLimitesSucursalOlp();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

        // --------------------------------------
        
        when(limitesPorSucursalDao.findLimitesSucursalOlp()).thenReturn(Arrays.asList(new LimitesPorSucursalDto()));
        response = limitesPorSucursalService.consultaLimitesSucursalOlp();
        assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());


    }
    
	@Test
	public void testCargaArchivoLimites() {

		
		byte[] excelFileContent = null;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("SampleSheet");

			// Create headers
			Row headerRow = sheet.createRow(0);
			String[] headers = { "sucursal", "divisa", "tipo de operación", "límite diario cliente",
					"límite diario usuario", "límite mensual cliente", "límite mensual usuario","límite diario cliente extranjero",
					"límite diario usuario extranjero", "límite mensual cliente extranjero", "límite mensual usuario extranjero"
			};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Add sample data
			String[][] sampleData = {{"0172", "USD", "410",
				"3000.0", "150.0", "3000.0", "150.0","3000.0", "150.0", "3000.0", "150.0"
					}, {"0172", "CAD", "411",
						"3000.0", "150.0", "3000.0", "150.0","3000.0", "150.0", "3000.0", "150.0"
					}
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
		ResponseApi result = limitesPorSucursalService.cargaArchivoLimites(fileMock);

		assertNotNull(result);
		
		excelFileContent = null;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("SampleSheet");

			// Create headers
			Row headerRow = sheet.createRow(0);
			String[] headers = { "sucursal", "divisa", "tipo de operación", "límite diario cliente",
					"límite diario usuario", "límite mensual cliente", "límite mensual usuario","límite diario cliente extranjero",
					"límite diario usuario extranjero",  "límite mensual usuario extranjero"
			};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Add sample data
			String[][] sampleData = { {"USD", "410",
				"3000.0", "150.0", "3000.0", "150.0","3000.0", "150.0", "3000.0", "150.0"
					}, {"0172", "CAD", "411",
						"3000.0", "150.0", "3000.0", "150.0","3000.0", "150.0", "3000.0", "150.0"
					}
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
		result = limitesPorSucursalService.cargaArchivoLimites(fileMock);

		assertNotNull(result);
	}
	
	@Test
	public void testDescargaLimitesSucursales() {
		when(sucursalesDao.consulta()).thenReturn(null);
		ByteArrayInputStream response = limitesPorSucursalService.descargaLimitesSucursales("USD");
		assertThat(response).isNull();
		
		List<Sucursal> sucursales = new ArrayList<Sucursal>();
		Sucursal sucursal = new Sucursal();
		sucursal.setId("0172");
				
		Limites limitesUsd = new Limites();
		limitesUsd.setCodigoDivisa("USD");
		limitesUsd.setIdTipoOperacion("410");		
		MontosLimite montoDiario = new MontosLimite(0.0,0.0,0.0,0.0);
		MontosLimite montoMensual = new MontosLimite(0.0,0.0,0.0,0.0);
		limitesUsd.setDiario(montoDiario);
		limitesUsd.setMensual(montoMensual);
		
		Limites limitesCad = new Limites();
		limitesCad.setCodigoDivisa("CAD");
		limitesCad.setIdTipoOperacion("410");	
		limitesCad.setDiario(montoDiario);
		limitesCad.setMensual(montoMensual);
		
		Limites limitesEur = new Limites();
		limitesEur.setCodigoDivisa("EUR");
		limitesEur.setIdTipoOperacion("410");		
		limitesEur.setDiario(montoDiario);
		limitesEur.setMensual(montoMensual);
		
		sucursal.setLimites(Arrays.asList(limitesUsd,limitesEur,limitesCad));
		
		sucursales.add(sucursal);
		when(sucursalesDao.consulta()).thenReturn(sucursales);
		response = limitesPorSucursalService.descargaLimitesSucursales("USD");
		assertThat(response).isNotNull();
	}
}