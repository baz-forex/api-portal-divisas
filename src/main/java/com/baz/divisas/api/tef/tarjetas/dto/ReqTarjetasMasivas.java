package com.baz.divisas.api.tef.tarjetas.dto;

import java.util.List;

/**
 * The Class ReqTarjetasMasivas.
 */
public class ReqTarjetasMasivas {

    private String sucursal;
    private String terminal;
    private List<String> solicitudes;

    /**
     * COnstructor
     */
    public ReqTarjetasMasivas() {
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public List<String> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<String> solicitudes) {
        this.solicitudes = solicitudes;
    }

    @Override
    public String toString() {
        return "ReqTarjetasMasivas{" +
                "sucursal='" + sucursal + '\'' +
                ", terminal='" + terminal + '\'' +
                ", solicitudes=" + solicitudes +
                '}';
    }
}
