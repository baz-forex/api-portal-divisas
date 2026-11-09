package com.baz.divisas.api.tef.tarjetas.dto;

import java.util.List;

import com.baz.divisas.api.apigee.dto.ResultadoApi;

/**
 * clase RespTarjetasMasivas
 */
public class RespTarjetasMasivas extends ResultadoApi {
  private List<String> correctos;
  private List<String> incorrectos;

  /**
   * COntructor
   */
  public RespTarjetasMasivas() {
    //Init
  }

  public List<String> getCorrectos() {
    return correctos;
  }

  public void setCorrectos(List<String> correctos) {
    this.correctos = correctos;
  }

  public List<String> getIncorrectos() {
    return incorrectos;
  }

  public void setIncorrectos(List<String> incorrectos) {
    this.incorrectos = incorrectos;
  }

  @Override
  public String toString() {
    return "RespTarjetasMasivas{" +
        "correctos=" + correctos +
        ", incorrectos=" + incorrectos +
        '}';
  }
}
