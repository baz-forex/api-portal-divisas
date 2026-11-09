package com.baz.divisas.api.catalogos.model;
/**
 * Clase CatRangoEdadesCampanias 
 * @author B86960
 * rvalencia
 */
public class CatRangoEdadesCampanias extends Catalogo {
    private int minimo;
    private int maximo;
    /**
	 * Constructor de la clase
	 */
	public CatRangoEdadesCampanias() {
		super();
	}
    /**
     * Minimo Edad
     * @return minimo
     */
    public int getMinimo() {
        return minimo;
    }
    /**
     * Minimo Edad
     * @param minimo
     */
    public void setminimo(int minimo) {
        this.minimo = minimo;
    }
    /**
     * Maximo Edad
     * @return maximo
     */
    public int getmaximo() {
        return maximo;
    }
    /**
     * Maximo Edad
     * @param maximo
     */
    public void setmaximo(int maximo) {
        this.maximo = maximo;
    }
    
}
