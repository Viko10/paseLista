package com.itsoeh.vcruz.paselista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.itsoeh.vcruz.paselista.modelo.MDocenteRegistro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {
    private Button btnGuardar;
    private TextView regresarLogin, txtAceptar, txtfecnac;

    private EditText txtNombre, txtApp, txtApm, txtEmail, txtmat, txtContra, txtContra2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        regresarLogin = findViewById(R.id.txt_Regresar_login);
        btnGuardar = findViewById(R.id.reg_btn_guardarDatos);


        txtNombre=findViewById(R.id.reg_txt_Nombre);
        txtApp=findViewById(R.id.reg_txt_App);
        txtApm=findViewById(R.id.reg_txt_Apm);
        txtEmail=findViewById(R.id.reg_txt_email);
        txtmat= findViewById(R.id.reg_txt_mat);
        txtfecnac=findViewById(R.id.reg_txt_fecnac);
        txtContra=findViewById(R.id.reg_txt_pass);
        txtContra2=findViewById(R.id.reg_txt_pass2);

        txtfecnac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        registro.this,
                        R.style.CustomDatePickerDialog,// Usar el tema personalizado
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String fechaFormateada = year + "-" + (month + 1) + "-" + dayOfMonth;
                                txtfecnac.setText(fechaFormateada);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCuenta();
            }
        });


        regresarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicRegresar();
            }
        });

    }



    private boolean esCorreoValido(String correo) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    private void guardarCuenta() {

        // Obtén los valores de los campos de texto
        String nombre = txtNombre.getText().toString();
        String app = txtApp.getText().toString();
        String apm = txtApm.getText().toString();
        String email = txtEmail.getText().toString();
        String fecnac = txtfecnac.getText().toString();
        String contra = txtContra.getText().toString();
        String contra2 = txtContra2.getText().toString();

        if (!esCorreoValido(email)) {
            txtEmail.setError("Correo electrónico no válido");
            return;
        }

        // Verifica si alguno de los campos está vacío
        if (nombre.isEmpty() || app.isEmpty() || apm.isEmpty() || email.isEmpty() ||
                fecnac.isEmpty() || contra.isEmpty() || contra2.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return; // Detiene la ejecución adicional del método
        }

        // Verifica si las contraseñas coinciden
        if (!contra.equals(contra2)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return; // Detiene la ejecución adicional del método
        }
        ADocenteRegistro g = new ADocenteRegistro(this);
        MDocenteRegistro x = new MDocenteRegistro();
        x.setNombre(txtNombre.getText().toString());
        x.setApp(txtApp.getText().toString());
        x.setApm(txtApm.getText().toString());
        x.setEmail(txtEmail.getText().toString());
        x.setFecnac(txtfecnac.getText().toString());
        x.setContra(txtContra.getText().toString());

        try {
            g.Guardar(x);
            AlertDialog.Builder builder = new AlertDialog.Builder(registro.this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.activity_ventanaguardar_exito, null);
            builder.setView(view);
            final AlertDialog dialog = builder.create();
            dialog.show();
            TextView txt = view.findViewById(R.id.txtexito);
            txt.setText("Tu información se Guardo Con Exito");
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            Button btnAceptar = view.findViewById(R.id.btnAceptar_exi);
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seGuardo();
                    Toast.makeText(getApplicationContext(), "Tu cuenta se Creo con exito", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            clicGuardarNube();
        }catch (Exception ex){
            Toast.makeText(this, "Error al guardar la información", Toast.LENGTH_SHORT).show();
        }


    }

    private void seGuardo() {
        Intent vamosARegistro=new Intent(this, login.class);
        startActivity(vamosARegistro);
    }

    private void clicRegresar() {
        Intent vamosARegistro=new Intent(this, login.class);
        startActivity(vamosARegistro);
    }
    private void clicGuardarNube() {
        RequestQueue colaDeSolicitudes  = VolleySingleton.getInstance(this).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.POST, Api.GUARDAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            //txtSalida.setText(respuesta.getString("contenido"));
                        } catch (JSONException e) {
                            Toast.makeText(registro.this,
                                    e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mostrar un Toast con el mensaje de error
                Toast.makeText(registro.this, "ERROR--->" + error.getMessage(), Toast.LENGTH_SHORT).show();

                // Imprimir el error en Logcat
                Log.e("RegistroError", "Error en Volley: ", error);

                // Opcionalmente, puedes imprimir la pila de llamadas si está disponible
                if (error.getCause() != null) {
                    error.getCause().printStackTrace();
                }
            }

        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("mat",txtmat.getText().toString());
                parametros.put("nom",txtNombre.getText().toString());
                parametros.put("app",txtApp.getText().toString());
                parametros.put("apm",txtApm.getText().toString());
                parametros.put("email",txtEmail.getText().toString());
                parametros.put("fecnac",txtfecnac.getText().toString());
                parametros.put("contra",txtContra.getText().toString());
                return parametros;
            }
        };
        colaDeSolicitudes.add(solicitud);

    }
}