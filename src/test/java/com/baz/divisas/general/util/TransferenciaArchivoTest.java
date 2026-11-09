package com.baz.divisas.general.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.*;
public class TransferenciaArchivoTest {

	@InjectMocks
	private TransferenciaArchivo transferencia;
	@TempDir
	private Path tempDir;
	
	@Test
	public void testCopyInputStreamToFile() throws IOException {
		// 1. Setup: Create input stream with data
		String content = "Hello World";
		InputStream inputStream = new ByteArrayInputStream(content.getBytes());
		File outputFile = tempDir.resolve("output.txt").toFile();

		// 2. Execute the method
		TransferenciaArchivo.copyInputStreamToFile(inputStream, outputFile);

		// 3. Verify
		assertTrue(outputFile.exists(), "File should be created");
		String outputContent = Files.readString(outputFile.toPath());
		assertTrue(content.equals(outputContent), "Content should match");		
	}
	
    @Test
    void testSetArchivoSMB_Success() {
        // Arrange
    	String content = "Hello World";
		InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        String smbPath = "smb://user:password@server/share/file.txt";
       
        // Act
        transferencia = new TransferenciaArchivo();
        transferencia.setArchivoSMB(inputStream,smbPath, smbPath);


    }
	
}
