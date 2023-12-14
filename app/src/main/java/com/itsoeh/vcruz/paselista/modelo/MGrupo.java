package com.itsoeh.vcruz.paselista.modelo;

public class MGrupo {
    private  String cve;
    private String nomAsignatura;
    private String creditos ;
    private String id_carrera;

    public MGrupo() {
    }

    public MGrupo(String cve, String nomAsignatura, String creditos, String id_carrera) {
        this.cve = cve;
        this.nomAsignatura = nomAsignatura;
        this.creditos = creditos;
        this.id_carrera = id_carrera;
    }

    public String getCve() {
        return cve;
    }

    public void setCve(String cve) {
        this.cve = cve;
    }

    public String getNomAsignatura() {
        return nomAsignatura;
    }

    public void setNomAsignatura(String nomAsignatura) {
        this.nomAsignatura = nomAsignatura;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String aula) {
        this.creditos = creditos;
    }

    public String getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(String id_carrera) {
        this.id_carrera = id_carrera;
    }

    @Override
    public String toString() {
        return "MGrupo{" +
                "cve=" + cve +
                ", nomAsignatura='" + nomAsignatura + '\'' +
                ", creditos ='" + creditos + '\'' +
                ", id_carrera='" + id_carrera + '\'' +
                '}';
    }
}


