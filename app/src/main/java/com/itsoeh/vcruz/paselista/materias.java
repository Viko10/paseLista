package com.itsoeh.vcruz.paselista;

public class materias {
    private String clavemat;
    private String nombreMat;
    private int creditos;
    private String carrera;
    private int idDocente;

    public materias(String clavemat, String nombreMat, int creditos, String carrera, int idDocente) {
        this.clavemat = clavemat;
        this.nombreMat = nombreMat;
        this.creditos = creditos;
        this.carrera = carrera;
        this.idDocente = idDocente;
    }

    public String getClavemat() {
        return clavemat;
    }

    public void setClavemat(String clavemat) {
        this.clavemat = clavemat;
    }

    public String getNombreMat() {
        return nombreMat;
    }

    public void setNombreMat(String nombreMat) {
        this.nombreMat = nombreMat;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    @Override
    public String toString() {
        return "materias{" +
                "clavemat='" + clavemat + '\'' +
                ", nombreMat='" + nombreMat + '\'' +
                ", creditos=" + creditos +
                ", carrera='" + carrera + '\'' +
                ", idDocente=" + idDocente +
                '}';
    }
}
