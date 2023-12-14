package com.itsoeh.vcruz.paselista;

import static com.itsoeh.vcruz.paselista.R.id.login_new_account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.vcruz.paselista.accesoADatos.ADocenteRegistro;
import com.itsoeh.vcruz.paselista.accesoADatos.Api;
import com.itsoeh.vcruz.paselista.accesoADatos.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    private Button btnEntrada, btnRegistro, btnNewCount;
    private EditText txtCorreo, txtContra;
    private TextView olvidasteContraseña;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnEntrada = findViewById(R.id.login_btn_entrar);
        txtCorreo = findViewById(R.id.login_txt_usuario);
        txtContra = findViewById(R.id.login_txt_pass);
        btnNewCount = findViewById(login_new_account);
        olvidasteContraseña = findViewById(R.id.login_olvidascontra);

        olvidasteContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               clicRecuperarpass();
            }
        });

        btnNewCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicRegistrar();
            }
        });
        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEntrar();
            }
        });
    }

    private void clicRecuperarpass() {
        Intent vamosARegistro=new Intent(this, recuperacontra.class);
        startActivity(vamosARegistro);
    }

    private void clicRegistrar() {
        Intent vamosARegistro=new Intent(this, registro.class);
        startActivity(vamosARegistro);
    }
    private void mostrarProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.setCancelable(false); // Evitar que el usuario pueda cancelarlo manualmente
        progressDialog.show();
    }

    private void ocultarProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }



   private boolean esCorreoValido(String correo) {
       return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches();
   }

  private void clicEntrar() {

      String correo = txtCorreo.getText().toString();
      String contra = txtContra.getText().toString();

      // Validaciones
      if (!esCorreoValido(correo)) {
          txtCorreo.setError("Correo electrónico no válido");
          return;
      }
      if (correo.isEmpty() || contra.isEmpty()) {
          Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
          return;
      }

      // Mostrar ProgressDialog
      mostrarProgressDialog();


      StringRequest solicitud = new StringRequest(Request.Method.POST, Api.LOGIN,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      try {
                          JSONObject respuesta = new JSONObject(response);
                          if (!respuesta.getBoolean("error")) {
                              // Obtener primero el objeto 'contenido'
                              JSONObject contenido = respuesta.getJSONObject("contenido");

                              // Luego obtener el 'id' del objeto 'contenido'
                              String userId = contenido.getString("id");
                              String nombre = contenido.getString("nom");
                              String apellidoPaterno = contenido.getString("app");
                              String apellidoMaterno = contenido.getString("apm");
                              String email = contenido.getString("email");
                              String matricula = contenido.getString("mat");

                              // Almacenar el ID del usuario en SharedPreferences
                              //String userId = respuesta.getString("id"); // Asegúrate de que este es el nombre correcto de la clave
                              SharedPreferences sharedPref = getSharedPreferences("PreferenciasMiApp", Context.MODE_PRIVATE);
                              SharedPreferences.Editor editor = sharedPref.edit();
                              editor.putString("userId", userId);
                              editor.putString("nombre", nombre);
                              editor.putString("apellidoPaterno", apellidoPaterno);
                              editor.putString("apellidoMaterno", apellidoMaterno);
                              editor.putString("email", email);
                              editor.putString("matricula", matricula);
                              editor.apply();

                              // Inicio de sesión exitoso
                              Toast.makeText(login.this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();
                              Intent brinco = new Intent(login.this, MainActivity2.class);
                              startActivity(brinco);
                          } else {
                              // Error en inicio de sesión
                              AlertDialog.Builder aviso = new AlertDialog.Builder(login.this);
                              aviso.setTitle("Error al ingresar")
                                      .setMessage(respuesta.getString("message"))
                                      .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {
                                              dialog.dismiss();
                                          }
                                      }).show();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              // Ocultar el ProgressDialog
              ocultarProgressDialog();
              Toast.makeText(login.this, "Error en la conexión!!", Toast.LENGTH_SHORT).show();
          }
      }) {
          @Override
          protected Map<String, String> getParams() {
              Map<String, String> parametros = new HashMap<String, String>();
              parametros.put("email", correo);
              parametros.put("contra", contra);
              return parametros;
          }
      };

      VolleySingleton.getInstance(this).addToRequestQueue(solicitud);
  }


}