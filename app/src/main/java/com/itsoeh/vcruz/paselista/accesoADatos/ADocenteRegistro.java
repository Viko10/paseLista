package com.itsoeh.vcruz.paselista.accesoADatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.itsoeh.vcruz.paselista.modelo.MDocenteRegistro;

public class ADocenteRegistro extends  AccesoADatos{
    public ADocenteRegistro(@Nullable Context context) {
        super(context);
    }

    public void Guardar(MDocenteRegistro x){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DOCENTES VALUES (NULL," +
                "'" + x.getNombre() + "'," +
                "'" + x.getApp() + "'," +
                "'" + x.getApm() + "'," +
                "'" + x.getEmail() + "'," +
                "'" + x.getFecnac() + "'," +
                "'" + x.getContra() + "')"
        );
    }

    public String[] buscarContrasenia(String email){
        String datos[]=new String[2];
        SQLiteDatabase db= getReadableDatabase();
        Cursor reg=db.rawQuery("SELECT NOMBRE, PASS FROM DOCENTES WHERE CORREO='" + email + "'", null);
        if (reg.getCount()!=0){
            reg.moveToNext();
            datos[0]=reg.getString(0);
            datos[1]= reg.getString(1);
            return datos;
        }else
            return null;
    }

}
