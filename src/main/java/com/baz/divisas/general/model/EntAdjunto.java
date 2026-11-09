package com.baz.divisas.general.model;

import java.io.InputStream;

public class EntAdjunto {

	private InputStream archivo;
	private String nombre;
	
	public EntAdjunto(InputStream archivo,String nombre){
		this.archivo = archivo;
		this.nombre = nombre;
	}

	public InputStream getArchivo() {
		return archivo;
	}

	public String getNombre() {
		return nombre; 
	}
	
}
