package com.baz.divisas.api.tef.tarjetas.dto;

/**
 * clase RespTarjetasMasivas
 */
public class RespTarjetasMasivasGeneral {

  private String mensaje;
  private String folio;
  private RespTarjetasMasivas resultado;

  /**
   * Constructor
   */
  public RespTarjetasMasivasGeneral() {
    //Init
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getFolio() {
    return folio;
  }

  public void setFolio(String folio) {
    this.folio = folio;
  }

  public RespTarjetasMasivas getResultado() {
    return resultado;
  }

  public void setResultado(RespTarjetasMasivas resultado) {
    this.resultado = resultado;
  }

  @Override
  public String toString() {
    return "RespTarjetasMasivasGeneral{" +
        "mensaje='" + mensaje + '\'' +
        ", folio='" + folio + '\'' +
        ", resultado=" + resultado +
        '}';
  }
}
