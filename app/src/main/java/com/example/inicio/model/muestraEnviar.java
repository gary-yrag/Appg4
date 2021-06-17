package com.example.inicio.model;

public class muestraEnviar {
    private String id;
    private String Hash;
    private String estado;
    private String tiempo_estadoactual;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTiempo_estadoactual() {
        return tiempo_estadoactual;
    }

    public void setTiempo_estadoactual(String tiempo_estadoactual) {
        this.tiempo_estadoactual = tiempo_estadoactual;
    }
}
