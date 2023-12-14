package com.itsoeh.vcruz.paselista;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itsoeh.vcruz.paselista.accesoADatos.AGrupo;
import com.itsoeh.vcruz.paselista.accesoADatos.Api;
import com.itsoeh.vcruz.paselista.accesoADatos.VolleySingleton;
import com.itsoeh.vcruz.paselista.modelo.MGrupo;
import com.itsoeh.vcruz.paselista.radapter.AdapterMaterias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link crearGrupo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class crearGrupo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnGuardar, btnModificar, btnEliminar;
    private EditText txtcve, txtnombreAsig, txtAula, txtCarrera, txtCreditos;
    private Spinner spDoc;
    private int seleccionable = -1;
    private NavController nav;

    private MGrupo llegado;

    List<materias> materiasList;

    RecyclerView recyclerView;


    public crearGrupo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment crearGrupo.
     */
    // TODO: Rename and change types and number of parameters
    public static crearGrupo newInstance(String param1, String param2) {
        crearGrupo fragment = new crearGrupo();
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
        View view = inflater.inflate(R.layout.fragment_crear_grupo, container, false);

        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nav = Navigation.findNavController(view);

        btnGuardar = view.findViewById(R.id.grp_btn_guardar);
        btnModificar=view.findViewById(R.id.grp_btn_modificar);
        btnEliminar=view.findViewById(R.id.grp_btn_elimiar);
        txtcve = view.findViewById(R.id.grp_txt_cve);
        txtnombreAsig = view.findViewById(R.id.grp_txt_nomMateria);
        txtCreditos=view.findViewById(R.id.grp_txt_cred);
        txtCarrera = view.findViewById(R.id.grp_txt_carrera);

        Bundle datos = this.getArguments();
        if(datos != null){
            llegado = new MGrupo();
            llegado.setCve(datos.getString("cve"));
            btnGuardar.setVisibility(view.GONE);
            btnModificar.setVisibility(view.VISIBLE);
            btnEliminar.setVisibility(view.VISIBLE);
            txtcve.setText(datos.getString("cve"));
            txtnombreAsig.setText(datos.getString("nom"));
            txtCreditos.setText(datos.getString("creditos"));
            txtCarrera.setText(datos.getString("carr"));
            
        }
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliceliminar();
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicmodificar();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clikGuardar();
            }
        });


    }

    private void cliceliminar() {
        AGrupo a = new AGrupo(this.getContext());
        a.eliminar(Integer.parseInt(llegado.getCve()));
        nav.navigate(R.id.listaClases);
    }

    private void clicmodificar() {
        AGrupo a = new AGrupo(this.getContext());
        llegado.setCve(txtcve.getText().toString());
        llegado.setNomAsignatura(txtnombreAsig.getText().toString());
        llegado.setCreditos(txtCreditos.getText().toString());
        llegado.setId_carrera(txtCarrera.getText().toString());
        a.actualizar(llegado);
        nav.navigate(R.id.listaClases);

    }


    private void clicGuardar() {
        AGrupo bd = new AGrupo(this.getContext());
        MGrupo u = new MGrupo();
        u.setCve(txtcve.getText().toString());
        u.setNomAsignatura(txtnombreAsig.getText().toString());
        u.setCreditos(txtCreditos.getText().toString());
        u.setId_carrera(txtCarrera.getText().toString());

        try {
            bd.guardar(u);
            Toast.makeText(this.getContext(), "El grupo se registró con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), "Falló el registro\n" + ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        nav.navigate(R.id.listaClases);
    }



    private void clikGuardar() {
        Context context = getContext();
        if (context == null) return;

        SharedPreferences sharedPref = context.getSharedPreferences("PreferenciasMiApp", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "defaultId");

        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.POST, Api.CREARMATERIA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            if (!respuesta.getBoolean("error")) {
                                Toast.makeText(context, "Grupo registrado con éxito", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, respuesta.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                            Log.e("JSONError", "Error parsing JSON", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error en la conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("VolleyError", "Error en la solicitud", error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("clave_unica", txtcve.getText().toString());
                parametros.put("nombre_materia", txtnombreAsig.getText().toString());
                parametros.put("creditos", txtCreditos.getText().toString());
                parametros.put("carrera", txtCarrera.getText().toString());
                parametros.put("id_docente", userId);
                return parametros;
            }

        };
        colaDeSolicitudes.add(solicitud);
        nav.navigate(R.id.listaClases);

    }

}