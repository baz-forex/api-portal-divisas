package com.baz.divisas.api.tef.sucursales.model;

import java.time.LocalDateTime;
import java.util.List;

import com.baz.divisas.api.tef.general.model.Limites;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Sucursal.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sucursal{

	/** The id. */
	@SerializedName("_id")
	private String id;
	
	/** The descripcion. */
	private String descripcion;
	
	/** The sucursal. */
	private Integer sucursal;
	
	/** The nom CC. */
	private String nomCC;
	
	/** The tipo canal. */
	private String tipoCanal;
	
	/** The nom canal. */
	private String nomCanal;
	
	/** The nom pais. */
	private String nomPais;
	
	/** The nom estado. */
	private String nomEstado;
	
	/** The id entidad. */
	private Integer idEntidad;
	
	/** The nom entidad. */
	private String nomEntidad;	
	
	/** The nom municipio. */
	private String nomMunicipio;
	
	/** The nom calle. */
	private String nomCalle;
	
	/** The nom colonia. */
	private String nomColonia;
	
	/** The nom localidad. */
	private String nomLocalidad;
	
	/** The id estatus. */
	private Integer idEstatus;
	
	/** The nom estatus. */
	private String nomEstatus;
	
	/** The nom tipo operacion. */
	private String nomTipoOperacion;
	
	/** The codigo postal. */
	private String codigoPostal;
	
	/** The latitud. */
	private String latitud;
	
	/** The longitud. */
	private String longitud;
	
	/** The asignada. */
	private boolean asignada;
	
	/** The descripcion region. */
	private String descripcionRegion;
	
	/** The limites. */
	private List<Limites> limites;
	
	/** The divisas. */
	private List<Divisas> divisas;
	
	/** The tc olp. */
	private Divisas tcOlp;
	
	/** The fecha modificacion. */
	private String fechaModificacion;
	
	/** The fecha ultimo cambio. */
	private String fechaUltimoCambio;	
	
	/** The region sucursal archivo. */
	private String regionSucursalArchivo;
	
	/** The nombre sucursal archivo. */
	private String nombreSucursalArchivo;
	
	/** The tipo sucursal archivo. */
	private String tipoSucursalArchivo;
	
	/** The municipio sucursal archivo. */
	private String municipioSucursalArchivo;
	
	/** The estado sucursal archivo. */
	private String estadoSucursalArchivo;
	
	/** The calle sucursal archivo. */
	private String calleSucursalArchivo;
	
	/** The numero sucursal archivo. */
	private String numeroSucursalArchivo;
	
	/** The cp sucursal archivo. */
	private String cpSucursalArchivo;
	
	/** The numero region archivo. */
	private String numeroRegionArchivo;
	
	/** The fecha actualizacion. */
	private LocalDateTime fechaActualizacion;
	
	/** The id numero economico. */
	private String idNumeroEconomico;
	
	/** The fecha ultimo cambio OLP. */
	private String fechaUltimoCambioOLP;
	/**
	 * Constructor de la clase.
	 */
	public Sucursal() {
		super();
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the sucursal.
	 *
	 * @return the sucursal
	 */
	public Integer getSucursal() {
		return sucursal;
	}

	/**
	 * Sets the sucursal.
	 *
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(Integer sucursal) {
		this.sucursal = sucursal;
	}

	/**
	 * Gets the nom CC.
	 *
	 * @return the nomCC
	 */
	public String getNomCC() {
		return nomCC;
	}
	
	/**
	 * Sets the nom CC.
	 *
	 * @param nomCC the nomCC to set
	 */
	public void setNomCC(String nomCC) {
		this.nomCC = nomCC;
	}
	
	/**
	 * Gets the tipo canal.
	 *
	 * @return the tipoCanal
	 */
	public String getTipoCanal() {
		return tipoCanal;
	}
	
	/**
	 * Sets the tipo canal.
	 *
	 * @param tipoCanal the tipoCanal to set
	 */
	public void setTipoCanal(String tipoCanal) {
		this.tipoCanal = tipoCanal;
	}
	
	/**
	 * Gets the nom canal.
	 *
	 * @return the nomCanal
	 */
	public String getNomCanal() {
		return nomCanal;
	}
	
	/**
	 * Sets the nom canal.
	 *
	 * @param nomCanal the nomCanal to set
	 */
	public void setNomCanal(String nomCanal) {
		this.nomCanal = nomCanal;
	}
	
	/**
	 * Gets the nom pais.
	 *
	 * @return the nomPais
	 */
	public String getNomPais() {
		return nomPais;
	}
	
	/**
	 * Sets the nom pais.
	 *
	 * @param nomPais the nomPais to set
	 */
	public void setNomPais(String nomPais) {
		this.nomPais = nomPais;
	}
	
	/**
	 * Gets the nom estado.
	 *
	 * @return the nomEstado
	 */
	public String getNomEstado() {
		return nomEstado;
	}
	
	/**
	 * Sets the nom estado.
	 *
	 * @param nomEstado the nomEstado to set
	 */
	public void setNomEstado(String nomEstado) {
		this.nomEstado = nomEstado;
	}
	
	/**
	 * Gets the id entidad.
	 *
	 * @return the idEntidad
	 */
	public Integer getIdEntidad() {
		return idEntidad;
	}
	
	/**
	 * Sets the id entidad.
	 *
	 * @param idEntidad the idEntidad to set
	 */
	public void setIdEntidad(Integer idEntidad) {
		this.idEntidad = idEntidad;
	}
	
	/**
	 * Gets the nom entidad.
	 *
	 * @return the nomEntidad
	 */
	public String getNomEntidad() {
		return nomEntidad;
	}
	
	/**
	 * Sets the nom entidad.
	 *
	 * @param nomEntidad the nomEntidad to set
	 */
	public void setNomEntidad(String nomEntidad) {
		this.nomEntidad = nomEntidad;
	}
	
	/**
	 * Gets the nom municipio.
	 *
	 * @return the nomMunicipio
	 */
	public String getNomMunicipio() {
		return nomMunicipio;
	}
	
	/**
	 * Sets the nom municipio.
	 *
	 * @param nomMunicipio the nomMunicipio to set
	 */
	public void setNomMunicipio(String nomMunicipio) {
		this.nomMunicipio = nomMunicipio;
	}
	
	/**
	 * Gets the nom calle.
	 *
	 * @return the nomCalle
	 */
	public String getNomCalle() {
		return nomCalle;
	}
	
	/**
	 * Sets the nom calle.
	 *
	 * @param nomCalle the nomCalle to set
	 */
	public void setNomCalle(String nomCalle) {
		this.nomCalle = nomCalle;
	}
	
	/**
	 * Gets the nom colonia.
	 *
	 * @return the nomColonia
	 */
	public String getNomColonia() {
		return nomColonia;
	}
	
	/**
	 * Sets the nom colonia.
	 *
	 * @param nomColonia the nomColonia to set
	 */
	public void setNomColonia(String nomColonia) {
		this.nomColonia = nomColonia;
	}
	
	/**
	 * Gets the nom localidad.
	 *
	 * @return the nomLocalidad
	 */
	public String getNomLocalidad() {
		return nomLocalidad;
	}
	
	/**
	 * Sets the nom localidad.
	 *
	 * @param nomLocalidad the nomLocalidad to set
	 */
	public void setNomLocalidad(String nomLocalidad) {
		this.nomLocalidad = nomLocalidad;
	}
	
	/**
	 * Gets the id estatus.
	 *
	 * @return the idEstatus
	 */
	public Integer getIdEstatus() {
		return idEstatus;
	}
	
	/**
	 * Sets the id estatus.
	 *
	 * @param idEstatus the idEstatus to set
	 */
	public void setIdEstatus(Integer idEstatus) {
		this.idEstatus = idEstatus;
	}
	
	/**
	 * Gets the nom estatus.
	 *
	 * @return the nomEstatus
	 */
	public String getNomEstatus() {
		return nomEstatus;
	}
	
	/**
	 * Sets the nom estatus.
	 *
	 * @param nomEstatus the nomEstatus to set
	 */
	public void setNomEstatus(String nomEstatus) {
		this.nomEstatus = nomEstatus;
	}
	
	/**
	 * Gets the nom tipo operacion.
	 *
	 * @return the nomTipoOperacion
	 */
	public String getNomTipoOperacion() {
		return nomTipoOperacion;
	}
	
	/**
	 * Sets the nom tipo operacion.
	 *
	 * @param nomTipoOperacion the nomTipoOperacion to set
	 */
	public void setNomTipoOperacion(String nomTipoOperacion) {
		this.nomTipoOperacion = nomTipoOperacion;
	}
	
	/**
	 * Gets the codigo postal.
	 *
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	/**
	 * Sets the codigo postal.
	 *
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	/**
	 * Gets the latitud.
	 *
	 * @return the latitud
	 */
	public String getLatitud() {
		return latitud;
	}
	
	/**
	 * Sets the latitud.
	 *
	 * @param latitud the latitud to set
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	
	/**
	 * Gets the longitud.
	 *
	 * @return the longitud
	 */
	public String getLongitud() {
		return longitud;
	}
	
	/**
	 * Sets the longitud.
	 *
	 * @param longitud the longitud to set
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	
	/**
	 * Checks if is asignada.
	 *
	 * @return the asignada
	 */
	public boolean isAsignada() {
		return asignada;
	}
	
	/**
	 * Sets the asignada.
	 *
	 * @param asignada the asignada to set
	 */
	public void setAsignada(boolean asignada) {
		this.asignada = asignada;
	}
	
	/**
	 * Gets the descripcion region.
	 *
	 * @return the descripcionRegion
	 */
	public String getDescripcionRegion() {
		return descripcionRegion;
	}
	
	/**
	 * Sets the descripcion region.
	 *
	 * @param descripcionRegion the descripcionRegion to set
	 */
	public void setDescripcionRegion(String descripcionRegion) {
		this.descripcionRegion = descripcionRegion;
	}
	
	/**
	 * Gets the limites.
	 *
	 * @return the limites
	 */
	public List<Limites> getLimites() {
		return limites;
	}
	
	/**
	 * Sets the limites.
	 *
	 * @param limites the limites to set
	 */
	public void setLimites(List<Limites> limites) {
		this.limites = limites;
	}
	
	/**
	 * Gets the divisas.
	 *
	 * @return the divisas
	 */
	public List<Divisas> getDivisas() {
		return divisas;
	}
	
	/**
	 * Sets the divisas.
	 *
	 * @param divisas the divisas to set
	 */
	public void setDivisas(List<Divisas> divisas) {
		this.divisas = divisas;
	}

	/**
	 * Gets the fecha modificacion.
	 *
	 * @return the fecha modificacion
	 */
	public String getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * Sets the fecha modificacion.
	 *
	 * @param fechaModificacion the new fecha modificacion
	 */
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * Gets the fecha ultimo cambio.
	 *
	 * @return the fecha ultimo cambio
	 */
	public String getFechaUltimoCambio() {
		return fechaUltimoCambio;
	}

	/**
	 * Sets the fecha ultimo cambio.
	 *
	 * @param fechaUltimoCambio the new fecha ultimo cambio
	 */
	public void setFechaUltimoCambio(String fechaUltimoCambio) {
		this.fechaUltimoCambio = fechaUltimoCambio;
	}

	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Gets the region sucursal archivo.
	 *
	 * @return the region sucursal archivo
	 */
	public String getRegionSucursalArchivo() {
		return regionSucursalArchivo;
	}

	/**
	 * Sets the region sucursal archivo.
	 *
	 * @param regionSucursalArchivo the new region sucursal archivo
	 */
	public void setRegionSucursalArchivo(String regionSucursalArchivo) {
		this.regionSucursalArchivo = regionSucursalArchivo;
	}

	/**
	 * Gets the nombre sucursal archivo.
	 *
	 * @return the nombre sucursal archivo
	 */
	public String getNombreSucursalArchivo() {
		return nombreSucursalArchivo;
	}

	/**
	 * Sets the nombre sucursal archivo.
	 *
	 * @param nombreSucursalArchivo the new nombre sucursal archivo
	 */
	public void setNombreSucursalArchivo(String nombreSucursalArchivo) {
		this.nombreSucursalArchivo = nombreSucursalArchivo;
	}

	/**
	 * Gets the tipo sucursal archivo.
	 *
	 * @return the tipo sucursal archivo
	 */
	public String getTipoSucursalArchivo() {
		return tipoSucursalArchivo;
	}

	/**
	 * Sets the tipo sucursal archivo.
	 *
	 * @param tipoSucursalArchivo the new tipo sucursal archivo
	 */
	public void setTipoSucursalArchivo(String tipoSucursalArchivo) {
		this.tipoSucursalArchivo = tipoSucursalArchivo;
	}

	/**
	 * Gets the municipio sucursal archivo.
	 *
	 * @return the municipio sucursal archivo
	 */
	public String getMunicipioSucursalArchivo() {
		return municipioSucursalArchivo;
	}

	/**
	 * Sets the municipio sucursal archivo.
	 *
	 * @param municipioSucursalArchivo the new municipio sucursal archivo
	 */
	public void setMunicipioSucursalArchivo(String municipioSucursalArchivo) {
		this.municipioSucursalArchivo = municipioSucursalArchivo;
	}

	/**
	 * Gets the estado sucursal archivo.
	 *
	 * @return the estado sucursal archivo
	 */
	public String getEstadoSucursalArchivo() {
		return estadoSucursalArchivo;
	}

	/**
	 * Sets the estado sucursal archivo.
	 *
	 * @param estadoSucursalArchivo the new estado sucursal archivo
	 */
	public void setEstadoSucursalArchivo(String estadoSucursalArchivo) {
		this.estadoSucursalArchivo = estadoSucursalArchivo;
	}

	/**
	 * Gets the calle sucursal archivo.
	 *
	 * @return the calle sucursal archivo
	 */
	public String getCalleSucursalArchivo() {
		return calleSucursalArchivo;
	}

	/**
	 * Sets the calle sucursal archivo.
	 *
	 * @param calleSucursalArchivo the new calle sucursal archivo
	 */
	public void setCalleSucursalArchivo(String calleSucursalArchivo) {
		this.calleSucursalArchivo = calleSucursalArchivo;
	}

	/**
	 * Gets the numero sucursal archivo.
	 *
	 * @return the numero sucursal archivo
	 */
	public String getNumeroSucursalArchivo() {
		return numeroSucursalArchivo;
	}

	/**
	 * Sets the numero sucursal archivo.
	 *
	 * @param numeroSucursalArchivo the new numero sucursal archivo
	 */
	public void setNumeroSucursalArchivo(String numeroSucursalArchivo) {
		this.numeroSucursalArchivo = numeroSucursalArchivo;
	}

	/**
	 * Gets the cp sucursal archivo.
	 *
	 * @return the cp sucursal archivo
	 */
	public String getCpSucursalArchivo() {
		return cpSucursalArchivo;
	}

	/**
	 * Sets the cp sucursal archivo.
	 *
	 * @param cpSucursalArchivo the new cp sucursal archivo
	 */
	public void setCpSucursalArchivo(String cpSucursalArchivo) {
		this.cpSucursalArchivo = cpSucursalArchivo;
	}

	/**
	 * Gets the numero region archivo.
	 *
	 * @return the numero region archivo
	 */
	public String getNumeroRegionArchivo() {
		return numeroRegionArchivo;
	}

	/**
	 * Sets the numero region archivo.
	 *
	 * @param numeroRegionArchivo the new numero region archivo
	 */
	public void setNumeroRegionArchivo(String numeroRegionArchivo) {
		this.numeroRegionArchivo = numeroRegionArchivo;
	}

	/**
	 * Gets the tc olp.
	 *
	 * @return the tc olp
	 */
	public Divisas getTcOlp() {
		return tcOlp;
	}

	/**
	 * Sets the tc olp.
	 *
	 * @param tcOlp the new tc olp
	 */
	public void setTcOlp(Divisas tcOlp) {
		this.tcOlp = tcOlp;
	}

	/**
	 * Gets the fecha actualizacion.
	 *
	 * @return the fecha actualizacion
	 */
	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * Sets the fecha actualizacion.
	 *
	 * @param fechaActualizacion the new fecha actualizacion
	 */
	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * Gets the id numero economico.
	 *
	 * @return the id numero economico
	 */
	public String getIdNumeroEconomico() {
		return idNumeroEconomico;
	}

	/**
	 * Sets the id numero economico.
	 *
	 * @param idNumeroEconomico the new id numero economico
	 */
	public void setIdNumeroEconomico(String idNumeroEconomico) {
		this.idNumeroEconomico = idNumeroEconomico;
	}

	/**
	 * Gets the fecha ultimo cambio OLP.
	 *
	 * @return the fecha ultimo cambio OLP
	 */
	public String getFechaUltimoCambioOLP() {
		return fechaUltimoCambioOLP;
	}

	/**
	 * Sets the fecha ultimo cambio OLP.
	 *
	 * @param fechaUltimoCambioOLP the new fecha ultimo cambio OLP
	 */
	public void setFechaUltimoCambioOLP(String fechaUltimoCambioOLP) {
		this.fechaUltimoCambioOLP = fechaUltimoCambioOLP;
	}
	
	
}
