package com.itsoeh.vcruz.paselista.accesoADatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccesoADatos  extends SQLiteOpenHelper {
    public AccesoADatos(@Nullable Context context) {
        super(context, "PaseLista", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DOCENTES ("+
                "IDDOCENTES INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "NOMBRE TEXT,"+
                "APP TEXT,"+
                "APM TEXT,"+
                "CORREO TEXT,"+
                "FECNAC TEXT,"+
                "PASS TEXT"+
                ");"
        );


/*
        db.execSQL("CREATE TABLE Carreras (" +
                "carrera_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_carrera TEXT NOT NULL" +
                ");");



/*
        db.execSQL("CREATE TABLE Grupos (" +
                "cve_grupo INTEGER PRIMARY KEY," +
                "nombre_materia TEXT NOT NULL," +
                "aula TEXT NOT NULL," +
                "carrera_id TEXT" +
                ");"
        );
*/
        //"FOREIGN KEY (carrera_id) REFERENCES Carreras(carrera_id)" +

/*
        db.execSQL("CREATE TABLE Maestros (" +
                "maestro_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "app TEXT NOT NULL," +
                "apm TEXT NOT NULL," +
                "correo_user TEXT NOT NULL," +
                "fecnac DATE NOT NULL," +
                "pass TEXT NOT NULL," +
                "grupo_id INTEGER," +
                "FOREIGN KEY (grupo_id) REFERENCES Grupos(cve_grupo)" +
                ");");


        db.execSQL("CREATE TABLE Estudiantes (" +
                "estudiante_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matricula TEXT," +
                "nombre_estudiante TEXT NOT NULL," +
                "app TEXT NOT NULL," +
                "apm TEXT NOT NULL," +
                "correo_user TEXT NOT NULL," +
                "fecnac DATE NOT NULL," +
                "pass TEXT NOT NULL," +
                "grupo_id INTEGER," +
                "FOREIGN KEY (grupo_id) REFERENCES Grupos(cve_grupo)" +
                ");");

        db.execSQL("CREATE TABLE Asistencia (" +
                "asistencia_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "estudiante_id INTEGER," +
                "fecha_asistencia DATE," +
                "estado_asistencia TEXT NOT NULL," +
                "FOREIGN KEY (estudiante_id) REFERENCES Estudiantes(estudiante_id)" +
                ");");

        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
