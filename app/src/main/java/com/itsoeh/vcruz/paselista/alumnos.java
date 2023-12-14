package com.itsoeh.vcruz.paselista;

public class alumnos {

    private boolean asistio;
    private int id;
    private String nombreAlumno;
    private String app;
    private String apm;
    private String email;
    private String mat;
    private String contra;

    private String asistencia;

    private boolean isChecked;



    public alumnos(int id, String nombreAlumno, String app, String apm, String email, String mat, String contra) {
        this.id = id;
        this.nombreAlumno = nombreAlumno;
        this.app = app;
        this.apm = apm;
        this.email = email;
        this.mat = mat;
        this.contra = contra;
        isChecked = false;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }



    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public String getAsistencia() {
        return this.asistencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apm) {
        this.apm = apm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    @Override
    public String toString() {
        return "alumnos{" +
                "id=" + id +
                ", nombreAlumno='" + nombreAlumno + '\'' +
                ", app='" + app + '\'' +
                ", apm='" + apm + '\'' +
                ", email='" + email + '\'' +
                ", mat='" + mat + '\'' +
                ", contra='" + contra + '\'' +
                '}';
    }
}
