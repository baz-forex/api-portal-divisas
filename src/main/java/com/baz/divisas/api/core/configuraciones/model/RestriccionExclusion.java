package com.baz.divisas.api.core.configuraciones.model;

import com.google.gson.annotations.SerializedName;

public class RestriccionExclusion {
    
    @SerializedName("identificacion")
    private Identificacion identificacion = new Identificacion();
    
    

    /**
     * @param identificacion
     */
    public RestriccionExclusion(Identificacion identificacion) {
        super();
        this.identificacion = identificacion;
    }

    /**
     * 
     */
    public RestriccionExclusion() {
        super();
    }

    /**
     * @return the identificacion
     */
    public Identificacion getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }
    
    

}
