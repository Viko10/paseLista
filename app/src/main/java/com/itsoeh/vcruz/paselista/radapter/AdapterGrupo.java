package com.itsoeh.vcruz.paselista.radapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.vcruz.paselista.R;
import com.itsoeh.vcruz.paselista.accesoADatos.ADocenteRegistro;
import com.itsoeh.vcruz.paselista.modelo.MGrupo;


import java.util.ArrayList;
import java.util.HashMap;

public class AdapterGrupo extends RecyclerView.Adapter<AdapterGrupo.ViewHolderGrupo> {
    private ArrayList<MGrupo> lista;

    private ArrayList<HashMap<String, String>> listaGrupos;


    public AdapterGrupo(ArrayList<MGrupo> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderGrupo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grupos,parent,false);
        return new ViewHolderGrupo(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGrupo.ViewHolderGrupo holder, int position) {
        holder.setDatos(lista.get(position));
        holder.imgVerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verlista(v, position);
            }
        });
        holder.imagedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEditar(v, position);
            }
        });

    }

    private void clicEditar(View v, int position) {
        NavController nav = Navigation.findNavController(v);
        Bundle datos = new Bundle();
        MGrupo u = lista.get(position);
        String valorInt = u.getCve();
        String valorString = String.valueOf(valorInt);
        datos.putString("cve", valorString);
        //datos.putString("cve", u.getCve());
        datos.putString("nom", u.getNomAsignatura());
        datos.putString("aula", u.getCreditos());
        datos.putString("carr", u.getId_carrera());
        nav.navigate(R.id.crearGrupo,datos);
    }

    private void verlista(View v, int position) {
        NavController nav = Navigation.findNavController(v);
        Bundle datos = new Bundle();

        nav.navigate(R.id.asistencias, datos);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void filtro(ArrayList<MGrupo> filtro) {
        this.lista=filtro;
        notifyDataSetChanged();
    }

    public class ViewHolderGrupo extends RecyclerView.ViewHolder {
        private TextView cvgrupo, nombreGrupo, creditos, carrera;
        private ImageView imvGenero;
        private CardView crvCont;
        private Bitmap userm, userw;
        private ImageView imginf, imgVerlist, imagedit;
        private MGrupo m;
        public ViewHolderGrupo(@NonNull View itemView) {
            super(itemView);
            imagedit=itemView.findViewById(R.id.itemgroup_edt);
            imginf=itemView.findViewById(R.id.itemgrop_info);
            imgVerlist=itemView.findViewById(R.id.itemest_verList);
            cvgrupo=itemView.findViewById(R.id.itemgrup_txt_cv_materia);
            nombreGrupo=itemView.findViewById(R.id.itemgrup_txt_nombreMat);
            creditos=itemView.findViewById(R.id.itemgroup_creditos);
            carrera=itemView.findViewById(R.id.itemgroup_txt_carrera);

            imginf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicVer();
                }
            });

        }

        private void clicVer() {
            AlertDialog.Builder aviso=new AlertDialog.Builder(itemView.getContext());
            aviso.setTitle("Dato consultado")
                    .setMessage(this.m.toString())
                    .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

        public void setDatos(MGrupo m) {
            this.m=m;
            if(m!=null){

                cvgrupo.setText(String.valueOf(m.getCve()));
                nombreGrupo.setText(String.valueOf(m.getNomAsignatura()));
                creditos.setText(String.valueOf(m.getCreditos()));
                carrera.setText(String.valueOf(m.getId_carrera()));

            }
        }
    }

}
