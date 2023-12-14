package com.itsoeh.vcruz.paselista;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itsoeh.vcruz.paselista.accesoADatos.AGrupo;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link listaClases#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listaClases extends Fragment {
    private EditText txtBuscar;
    private FloatingActionButton btnCrear;
    private NavController nav;
    private RecyclerView recLista;

    private ArrayList<MGrupo> lista;
    private AdapterGrupo x;

    private ArrayList<materias> materiasList;
    private AdapterMaterias adapterMaterias;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public listaClases() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listaClases.
     */
    // TODO: Rename and change types and number of parameters
    public static listaClases newInstance(String param1, String param2) {
        listaClases fragment = new listaClases();
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
        return inflater.inflate(R.layout.fragment_lista_clases, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recLista = view.findViewById(R.id.list_grup_lista);

        recLista.setHasFixedSize(true);
        recLista.setLayoutManager(new LinearLayoutManager(getContext()));


        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing); // Puedes definir el valor en dimens.xml
        recLista.addItemDecoration(new SpacingItemDecoration(spacingInPixels));


        materiasList = new ArrayList<>();
        loadMaterias();

        nav = Navigation.findNavController(view);
        btnCrear = view.findViewById(R.id.btn_crear_grupo);
        txtBuscar = view.findViewById(R.id.lisgrup_txtBusca);
        recLista = view.findViewById(R.id.list_grup_lista);
        AGrupo u = new AGrupo(view.getContext());
        lista = u.listar();
        x = new AdapterGrupo(lista);
        recLista.setAdapter(x);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCrear();
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
                if (adapterMaterias != null) {
                    adapterMaterias.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario implementar
            }
        });
    }

    private void filtrar(String t){
        ArrayList<MGrupo> listaFiltrada  = new ArrayList<MGrupo>();
        for(MGrupo e:lista){
            if(e.getNomAsignatura().toLowerCase().contains(t.toLowerCase()))
                listaFiltrada.add(e);
        }
        x.filtro(listaFiltrada);
    }

    private void clickCrear() {
        nav.navigate(R.id.crearGrupo);
    }

    private void loadMaterias() {
        Context context = getContext();
        if (context == null) return;

        // Obtener el ID del docente que ha iniciado sesión desde las preferencias
        SharedPreferences sharedPref = context.getSharedPreferences("PreferenciasMiApp", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "defaultId");

        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.GET, Api.LISTARMATERIA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                JSONArray array = jsonObject.getJSONArray("contenido");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject materias = array.getJSONObject(i);

                                    // Obtener el ID del docente asociado a la materia
                                    int idDocente = materias.getInt("id_docente");

                                    // Verificar si el ID del docente coincide con el ID de sesión
                                    if (idDocente == Integer.parseInt(userId)){
                                        // Agregar la materia solo si pertenece al docente en sesión
                                        materiasList.add(new materias(
                                                materias.getString("clave_unica"),
                                                materias.getString("nombre_materia"),
                                                materias.getInt("creditos"),
                                                materias.getString("carrera"),
                                                idDocente
                                        ));
                                    }
                                }
                                adapterMaterias = new AdapterMaterias(getContext(), materiasList);
                                recLista.setAdapter(adapterMaterias);
                            } else {
                                // Manejar el caso de error
                                Toast.makeText(getContext(), jsonObject.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                            Log.e("JSONError", "Error parsing JSON", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error en la conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("VolleyError", "Error en la solicitud", error);
                    }
                });

        colaDeSolicitudes.add(solicitud);
        // Agregar una navegación si es necesario
        // nav.navigate(R.id.destinoSiguiente);
    }






}