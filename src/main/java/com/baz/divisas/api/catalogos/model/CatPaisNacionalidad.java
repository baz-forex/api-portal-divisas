package com.baz.divisas.api.catalogos.model;

import com.baz.divisas.api.core.configuraciones.model.RestriccionExclusion;
import com.google.gson.annotations.SerializedName;

/**
 * Clase Catalogo Pais Nacionalidad
 * @author B813196
 *
 */
public class CatPaisNacionalidad extends Catalogo {

	@SerializedName("restriccion_pais")
	private boolean restriccionPais;	
	
	@SerializedName("restriccion_nacionalidad")
	private boolean restriccionNacionalidad;
	
	@SerializedName("restriccion_exclusion")
	private RestriccionExclusion restriccionExclusion = new RestriccionExclusion() ;
	
	/**
	 * Constructor de la clase
	 */
	public CatPaisNacionalidad() {
		super();
	}
	
	public boolean isRestriccionPais() {
		return restriccionPais;
	}
	public void setRestriccionPais(boolean restriccionPais) {
		this.restriccionPais = restriccionPais;
	}
	public boolean isRestriccionNacionalidad() {
		return restriccionNacionalidad;
	}
	public void setRestriccionNacionalidad(boolean restriccionNacionalidad) {
		this.restriccionNacionalidad = restriccionNacionalidad;
	}

    /**
     * @return the restriccionExclusion
     */
    public RestriccionExclusion getRestriccionExclusion() {
        return restriccionExclusion;
    }

    /**
     * @param restriccionExclusion the restriccionExclusion to set
     */
    public void setRestriccionExclusion(RestriccionExclusion restriccionExclusion) {
        this.restriccionExclusion = restriccionExclusion;
    }
	
	
}
