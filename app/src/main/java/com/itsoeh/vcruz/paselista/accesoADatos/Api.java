package com.itsoeh.vcruz.paselista.accesoADatos;
import android.content.SharedPreferences;

public interface Api {


    ///String URL = "http://192.168.137.125/wsasissmart/Api.php";

    String URL = "https://asissmart.000webhostapp.com//wsasissmart/Api.php";
    String GUARDAR = URL+ "?apicall=guardardocente";
    String LOGIN = URL+ "?apicall=iniciarsesiondocente";
    String CREARMATERIA = URL+ "?apicall=crearmateria";
    String LISTARMATERIA = URL+ "?apicall=listarmateria";

    String LISTARALUMNOS = URL+ "?apicall=consultaralumnos";

    String REGASISTENCIA = URL+ "?apicall=registrarasistencia";



}
