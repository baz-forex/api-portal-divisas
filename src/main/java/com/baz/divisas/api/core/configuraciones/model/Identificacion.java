package com.baz.divisas.api.core.configuraciones.model;

import com.google.gson.annotations.SerializedName;

public class Identificacion {
    
    @SerializedName("primaria")
    private String primaria = "0";
    @SerializedName("adicional")
    private String adicional = "0" ;
    
    
    /**
     * 
     */
    public Identificacion() {
        super();
    }
    /**
     * @param primaria
     * @param adicional
     */
    public Identificacion(String primaria, String adicional) {
        super();
        this.primaria = primaria;
        this.adicional = adicional;
    }
    /**
     * @return the primaria
     */
    public String getPrimaria() {
        return primaria;
    }
    /**
     * @param primaria the primaria to set
     */
    public void setPrimaria(String primaria) {
        this.primaria = primaria;
    }
    /**
     * @return the adicional
     */
    public String getAdicional() {
        return adicional;
    }
    /**
     * @param adicional the adicional to set
     */
    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }
    
}
