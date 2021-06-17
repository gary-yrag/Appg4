package com.example.inicio.model;

public class muestra {
    private String _id;
    private String id;
    private String hash;
    private String oracion;
    private String hash_padre;
    private String hash_hijo;
    private String nick_hijo;
    private String porcentaje_riesgo;
    private String tiempo_creado;

    public String get_Id() {
        return _id;
    }

    public void set_Id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getOracion() {
        return oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public String getHash_padre() {
        return hash_padre;
    }

    public void setHash_padre(String hash_padre) {
        this.hash_padre = hash_padre;
    }

    public String getHash_hijo() {
        return hash_hijo;
    }

    public void setHash_hijo(String hash_hijo) {
        this.hash_hijo = hash_hijo;
    }

    public String getNick_hijo() {
        return nick_hijo;
    }

    public void setNick_hijo(String nick_hijo) {
        this.nick_hijo = nick_hijo;
    }

    public String getPorcentaje_riesgo() {
        return porcentaje_riesgo;
    }

    public void setPorcentaje_riesgo(String porcentaje_riesgo) {
        this.porcentaje_riesgo = porcentaje_riesgo;
    }

    public String getTiempo_creado() {
        return tiempo_creado;
    }

    public void setTiempo_creado(String tiempo_creado) {
        this.tiempo_creado = tiempo_creado;
    }
}
