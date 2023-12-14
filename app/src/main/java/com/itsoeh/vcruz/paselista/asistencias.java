package com.itsoeh.vcruz.paselista;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.vcruz.paselista.accesoADatos.Api;
import com.itsoeh.vcruz.paselista.accesoADatos.VolleySingleton;
import com.itsoeh.vcruz.paselista.modelo.MGrupo;
import com.itsoeh.vcruz.paselista.radapter.AdapterAlumno;
import com.itsoeh.vcruz.paselista.radapter.AdapterGrupo;
import com.itsoeh.vcruz.paselista.radapter.AdapterMaterias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link asistencias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class asistencias extends Fragment {

    private EditText txtBuscar;
    private  TextView nomAis,  creditosAsis, carreraAis;
    private ImageView imgAsisReg;
    private NavController nav;
    private RecyclerView recLista;

    private ArrayList<MGrupo> lista;
    private AdapterGrupo x;

    private CheckBox selccionarTodo;



    private ArrayList<alumnos> alumnosList;
    private AdapterAlumno adapterAlumno;

    private Button btn1, btn2, btn3 ,btn4, btnFecha;

    private Button btnGuardarAsistencia;
    private TextView txtFecha, txtR1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @Nullable
    private Bundle savedInstanceState;

    public asistencias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment asistencias.
     */
    // TODO: Rename and change types and number of parameters
    public static asistencias newInstance(String param1, String param2) {
        asistencias fragment = new asistencias();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asistencias, container, false);
        
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnFecha = view.findViewById(R.id.exam_btn_fecha);
        FloatingActionButton btnGuardarAsistencia = view.findViewById(R.id.btnGuardarAsis);

        nomAis = view.findViewById(R.id.nombreasistencia);
        creditosAsis = view.findViewById(R.id.creditosasistencia);
        carreraAis = view.findViewById(R.id.carreraasistencia);
        selccionarTodo = view.findViewById(R.id.selccinatodo);


        // Obtener la clave única del Bundle
        if (getArguments() != null) {
            String claveUnica = getArguments().getString("claveUnica", "defaultClaveUnica");
            String nombreMateria = getArguments().getString("nombreMateria", "Nombre por defecto");
            String creditosMateria = getArguments().getString("creditosMateria", "0");
            String carreraMateria = getArguments().getString("carreraMateria", "Carrera por defecto");

            // Guardar la clave única en SharedPreferences
            SharedPreferences sharedPref = getContext().getSharedPreferences("PreferenciasMiApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("claveUnicaMateria", claveUnica);
            editor.apply();

            // Cargar alumnos
            loadAlumnos();

            nomAis.setText(nombreMateria);
            creditosAsis.setText(creditosMateria);
            carreraAis.setText(carreraMateria);

        }



        recLista = view.findViewById(R.id.listAlumnos);
        if (recLista != null) {
            recLista.setHasFixedSize(true);
            recLista.setLayoutManager(new LinearLayoutManager(getContext()));
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing); // Puedes definir el valor en dimens.xml
            recLista.addItemDecoration(new SpacingItemDecoration(spacingInPixels));
            // Otros ajustes de RecyclerView
        }

       // recLista.setHasFixedSize(true);



        alumnosList = new ArrayList<>();
       // loadAlumnos();

        nav = Navigation.findNavController(view);
        imgAsisReg = view.findViewById(R.id.asisten_reg);
        txtBuscar = view.findViewById(R.id.lisgrup_txtBusca);


        selccionarTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(adapterAlumno != null) {
                    adapterAlumno.marcarTodasAsistencias(isChecked);
                }
            }
        });


        btnGuardarAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarAsistencia();
            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicFecha();
            }
        });

        imgAsisReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickver();
            }
        });

        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Llamada al método de filtrado en el adaptador
                if (adapterAlumno != null) {
                    adapterAlumno.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario implementar
            }
        });
}

    private void guardarAsistencia() {
        String fechaSeleccionada = btnFecha.getText().toString();
        if (fechaSeleccionada.isEmpty() || fechaSeleccionada.equals("Seleccionar Fecha")) {
            Toast.makeText(getContext(), "Selecciona una fecha primero.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adapterAlumno == null) {
            Toast.makeText(getContext(), "No se han cargado los alumnos aún.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<alumnos> listaAlumnos = adapterAlumno.getAlumnosList();
        boolean faltanAsistencias = false;

        for (alumnos alumno : listaAlumnos) {
            String asistencia = alumno.getAsistencia();
            if (asistencia == null || asistencia.isEmpty()) {
                faltanAsistencias = true;
                break;
            }
        }

        if (faltanAsistencias) {
            Toast.makeText(getContext(), "Verifica que todos los alumnos tengan su estatus de asistencia seleccionado.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPref = getContext().getSharedPreferences("PreferenciasMiApp", Context.MODE_PRIVATE);
        String claveUnicaMateria = sharedPref.getString("claveUnicaMateria", "defaultClaveUnica");

        for (alumnos alumno : listaAlumnos) {
            String asistencia = alumno.getAsistencia();
            if (asistencia != null && !asistencia.isEmpty()) {
                enviarAsistenciaAlServidor(claveUnicaMateria, alumno.getId(), fechaSeleccionada, asistencia);
                Log.d("AdapterAlumno", ", Alumno: " + alumno.getNombreAlumno() + ", Asistencia: " + alumno.getAsistencia());

                // Limpiar RadioButton después de guardar
                alumno.setAsistencia(""); // Limpiar el estatus de asistencia
                alumno.setChecked(false); // Desmarcar visualmente el checkbox
            }
        }

        // Limpiar la interfaz después de guardar
        btnFecha.setText("Seleccionar Fecha");
        if (selccionarTodo != null) {
            selccionarTodo.setChecked(false);
        }

        // Mostrar mensaje de éxito después de guardar
        Toast.makeText(getContext(), "La información se guardó exitosamente.", Toast.LENGTH_SHORT).show();
        clickCrear();


    }

    private void clickCrear() {
        nav.navigate(R.id.listaClases);
    }


    private void enviarAsistenciaAlServidor(String claveUnica, int idEstudiante, String fecha, String asistencia) {
        Context context = getContext();
        if (context == null) return;

        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.POST, Api.REGASISTENCIA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejo de la respuesta del servidor
                        // Aquí puedes incluir la lógica para manejar la respuesta del servidor después de enviar la asistencia
                        Log.d("AsistenciaEnviada", "La asistencia se ha enviado con éxito");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores en la solicitud al servidor
                        Toast.makeText(context, "Error al enviar la asistencia: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ErrorEnvioAsistencia", "Error al enviar la asistencia", error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("clave_unica", claveUnica);
                params.put("id_estudiante", String.valueOf(idEstudiante));
                params.put("fecha", fecha);
                params.put("asistencia", asistencia);
                return params;
            }
        };

        colaDeSolicitudes.add(solicitud);


    }




/*
    private boolean isSending = false; // Agregar un indicador de envío

    private void enviarAsistenciaAlServidor(String claveUnica, int idEstudiante, String fecha, String asistencia) {
        if (isSending) {
            Toast.makeText(getContext(), "Ya se está enviando la asistencia.", Toast.LENGTH_SHORT).show();
            return;
        }
        isSending = true;

        Context context = getContext();
        if (context == null) return;


        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.POST, Api.REGASISTENCIA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        isSending = false;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                // Asistencia registrada correctamente
                                Toast.makeText(getContext(), "Asistencia registrada con éxito", Toast.LENGTH_SHORT).show();
                            } else {
                                // Error al registrar asistencia
                                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Verifica tu seleccion o Fecha", Toast.LENGTH_SHORT).show();
                            Log.e("JSONErrorAsistencia", "Error parsing JSON: " + response, e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isSending = false;
                        Toast.makeText(context, "Error en la conexión al registrar asistencia: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("VolleyErrorAsistencia", "Error en la solicitud al registrar asistencia", error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("clave_unica", claveUnica);
                params.put("id_estudiante", String.valueOf(idEstudiante));
                params.put("fecha", fecha);
                params.put("asistencia", asistencia.toString());
                Log.d("Asistencia", ", valor: " + asistencia);
                return params;
            }
        };

        colaDeSolicitudes.add(solicitud);
    }
*/



    private void loadAlumnos() {
        Context context = getContext();
        if (context == null) return;

        SharedPreferences sharedPref = context.getSharedPreferences("PreferenciasMiApp", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "defaultId");
        String claveUnica = sharedPref.getString("claveUnicaMateria", "defaultClaveUnica");
        String nombreMateria = sharedPref.getString("nombreMateria", "Nombre por defecto");
        String creditosMateria = sharedPref.getString("creditosMateria", "0");
        String carreraMateria = sharedPref.getString("carreraMateria", "Carrera por defecto");


        Log.d("ClaveUnica", claveUnica);

        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.POST, Api.LISTARALUMNOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                JSONArray array = jsonObject.getJSONArray("contenido");
                                ArrayList<alumnos> alumnosList = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject alumno = array.getJSONObject(i);
                                    alumnosList.add(new alumnos(
                                            alumno.getInt("id"),
                                            alumno.getString("nom"),
                                            alumno.getString("app"),
                                            alumno.getString("apm"),
                                            alumno.getString("email"),
                                            alumno.getString("mat"),
                                            alumno.getString("contra")
                                    ));
                                }
                                // Actualiza el adaptador de tu RecyclerView aquí
                                adapterAlumno = new AdapterAlumno(getContext(), alumnosList);
                                recLista.setAdapter(adapterAlumno);
                            } else {
                                // Manejo de error
                                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                            Log.e("JSONError", "Error parsing JSON: " + response, e);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error en la conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("VolleyError", "Error en la solicitud", error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("clave_unica", claveUnica);
                return params;
            }
        };

        colaDeSolicitudes.add(solicitud);
    }



    private void clickver() {
        nav.navigate(R.id.asisReg);
    }

    private void clicFecha() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        //Mostrar el dialogo de selección de fecha
        DatePickerDialog cuadro = new DatePickerDialog(this.getContext(), R.style.CustomDatePickerDialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                btnFecha.setText(anio + "-" + (mes + 1 ) + "-" + dia);
            }
        }, year, month, dayOfMonth);
        cuadro.show();

    }
    }