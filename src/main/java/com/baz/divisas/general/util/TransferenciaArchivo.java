package com.baz.divisas.general.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

/**
 * The Class TransferenciaArchivo.
 */
@Service
public class TransferenciaArchivo {
	
	/** The Constant DEFAULT_BUFFER_SIZE. */
	private static final int DEFAULT_BUFFER_SIZE = 8192;

	/**
	 * Sets the archivo SMB.
	 *
	 * @param archivo       the archivo
	 * @param carpeta       the carpeta
	 * @param nombreArchivo the nombre archivo
	 */
	public void setArchivoSMB(InputStream archivo, String carpeta, String nombreArchivo) {

		try {
			File file = new File(carpeta + nombreArchivo);

			copyInputStreamToFile(archivo, file);
		} 
		catch (IOException e) {

			LogApp.excepcion(e, "No se a podido depositar el archivo");
		}
	}

	/**
	 * Copy input stream to file.
	 *
	 * @param inputStream the input stream
	 * @param file        the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {

		try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
			int read;
			byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}

	}

}
