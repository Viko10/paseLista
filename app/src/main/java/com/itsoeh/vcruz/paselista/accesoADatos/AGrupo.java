package com.itsoeh.vcruz.paselista.accesoADatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.itsoeh.vcruz.paselista.modelo.MGrupo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AGrupo extends AccesoADatos{

    public AGrupo(@Nullable Context context) {super(context);}

    public void guardar(MGrupo x) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO GRUPOS VALUES ( " +
                "'" + x.getCve() + "'," +
                "'" + x.getNomAsignatura() + "'," +
                "'" + x.getCreditos() + "'," +
                "'" + x.getId_carrera() + "')"
        );
    }

    public ArrayList<MGrupo> listar(){
        ArrayList<MGrupo> lista=new ArrayList<MGrupo>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor reg=db.rawQuery("SELECT * FROM GRUPOS ",null);
        while(reg.moveToNext()){
            MGrupo x=new MGrupo();
            x.setCve(reg.getString(0));
            x.setNomAsignatura(reg.getString(1));
            x.setCreditos(reg.getString(2));
            x.setId_carrera(reg.getString(3));
            lista.add(x);
        }
        return lista;
    }




    public void actualizar(MGrupo x){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE GRUPOS SET nomasignatura=?, aula=?, id_carrera = ? WHERE cve = ?";
        db.execSQL(sql, new Object[]{
                x.getNomAsignatura(),
                x.getCreditos(),
                x.getId_carrera(),
                x.getCve()  // Debes colocar el valor de cve al final
        });
        db.close();
    }

    /*public void actualizar(MGrupo x){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE GRUPOS SET nomasignatura=?, aula=?, id_carrera = ? WHERE cve = ?";
        db.execSQL(sql, new Object[]{
                x.getCve(),
                x.getNomAsignatura(),
                x.getAula(),
                x.getId_carrera()
        });
        db.close();
    }*/

    public void  eliminar (int cve){
        SQLiteDatabase db =  this.getWritableDatabase();
        String sql = "DELETE FROM GRUPOS WHERE CVE = ?";
        db.execSQL(sql, new Object[]{cve});

        ///cierra la base de datos
        db.close();
    }


    public String getGrupo(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor reg = db.rawQuery("SELECT NOMASIGNATURA FROM GRUPOS "+
                "WHERE IDGRUPO=" +id, null);
        if(reg.getCount() != 0){
            reg.moveToNext();
            return reg.getString(0);
        }
        else
            return null;
    }
}
