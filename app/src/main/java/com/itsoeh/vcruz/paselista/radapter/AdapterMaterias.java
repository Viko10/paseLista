package com.itsoeh.vcruz.paselista.radapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.vcruz.paselista.R;
import com.itsoeh.vcruz.paselista.materias;

import java.util.ArrayList;
import java.util.List;

public class AdapterMaterias  extends RecyclerView.Adapter<AdapterMaterias.ViewHolder> {



    private Context context;
    private List<materias> materiasList;

    private List<materias> materiasListFull; // Lista para mantener una copia de la lista original


    public AdapterMaterias(Context context, List<materias> materiasList) {
        this.context = context;
        this.materiasList = materiasList;
        materiasListFull = new ArrayList<>(materiasList);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView clave, nombremat,creditos, carrera;

        TextView nomAis,creditosAsis, carreraAis;

        private ImageView imginf, imgVerlist, imagedit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clave = itemView.findViewById(R.id.itemgrup_txt_cv_materia);
            nombremat = itemView.findViewById(R.id.itemgrup_txt_nombreMat);
            creditos = itemView.findViewById(R.id.itemgroup_creditos);
            carrera = itemView.findViewById(R.id.itemgroup_txt_carrera);

            imgVerlist=itemView.findViewById(R.id.itemest_verList);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_grupos, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterMaterias.ViewHolder holder, int position) {
    materias materia = materiasList.get(position);

    holder.clave.setText(materia.getClavemat());
    holder.nombremat.setText(materia.getNombreMat());
    holder.creditos.setText(String.valueOf(materia.getCreditos()));
    holder.carrera.setText(materia.getCarrera());


        holder.imgVerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verlista(v, position);
            }
        });


    }

    private void verlista(View v, int position) {
        NavController nav = Navigation.findNavController(v);
        Bundle bundle = new Bundle();

        // Obtener la clave única de la materia seleccionada
        String claveUnicaSeleccionada = materiasList.get(position).getClavemat();
        bundle.putString("claveUnica", claveUnicaSeleccionada);

        String materiaSeleccionada = materiasList.get(position).getNombreMat();
        bundle.putString("nombreMateria", materiaSeleccionada);

        String creditosSeleccionados = String.valueOf(materiasList.get(position).getCreditos());
        bundle.putString("creditosMateria", creditosSeleccionados);

        String carreraSeleccionada = materiasList.get(position).getCarrera();
        bundle.putString("carreraMateria", carreraSeleccionada);


        nav.navigate(R.id.asistencias, bundle);
    }


    @Override
    public int getItemCount() {
        return materiasList.size();
    }

    public void filter(String text) {
        materiasList.clear();
        if (text.isEmpty()) {
            materiasList.addAll(materiasListFull);
        } else {
            text = text.toLowerCase();
            for (materias item : materiasListFull) {
                if (item.getNombreMat().toLowerCase().contains(text)) {
                    materiasList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


}
