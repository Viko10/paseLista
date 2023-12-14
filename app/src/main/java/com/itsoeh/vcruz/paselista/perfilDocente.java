package com.itsoeh.vcruz.paselista;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link perfilDocente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class perfilDocente extends Fragment {


    private TextView nombreDocenteTextView;
    private TextView apellidosDocenteTextView;
    private TextView matriculaDocenteTextView;
    private TextView emailDocenteTextView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public perfilDocente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment perfilDocente.
     */
    // TODO: Rename and change types and number of parameters
    public static perfilDocente newInstance(String param1, String param2) {
        perfilDocente fragment = new perfilDocente();
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
        View view = inflater.inflate(R.layout.fragment_perfil_docente, container, false);

        // Obtener referencias a los TextViews en el layout del fragmento
        nombreDocenteTextView = view.findViewById(R.id.textViewNombre);
       /// apellidosDocenteTextView = view.findViewById(R.id.te);
        matriculaDocenteTextView = view.findViewById(R.id.textViewMatricula);
        emailDocenteTextView = view.findViewById(R.id.textViewEmail);

        // Recuperar los datos del docente almacenados en SharedPreferences
        SharedPreferences sharedPref = requireContext().getSharedPreferences("PreferenciasMiApp", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "defaultUserId");
        String nombre = sharedPref.getString("nombre", "Nombre por defecto");
        String apellidoPaterno = sharedPref.getString("apellidoPaterno", "Apellido paterno por defecto");
        String apellidoMaterno = sharedPref.getString("apellidoMaterno", "Apellido materno por defecto");
        String email = sharedPref.getString("email", "Email por defecto");
        String matricula = sharedPref.getString("matricula", "Matrícula por defecto");

        // Mostrar los datos del docente en los TextViews correspondientes
        nombreDocenteTextView.setText(nombre + " " + apellidoPaterno + " " + apellidoMaterno );
        //apellidosDocenteTextView.setText();
        matriculaDocenteTextView.setText(matricula);
        emailDocenteTextView.setText(email);

        return view;
    }

}
  /*  @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



}*/