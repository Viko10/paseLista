package com.itsoeh.vcruz.paselista.radapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.vcruz.paselista.R;
import com.itsoeh.vcruz.paselista.alumnos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterAlumno  extends RecyclerView.Adapter<AdapterAlumno.ViewHolder> {

    private Context context;
    private  List<alumnos> alumnosList;

    private List<alumnos> alumnosListFull;


    public void updateData(ArrayList<alumnos> nuevosDatos) {
        alumnosList.clear();
        alumnosList.addAll(nuevosDatos);
        notifyDataSetChanged();
    }

    public  List<alumnos> getAlumnosList() {
        return alumnosList;
    }

    public AdapterAlumno(Context context, List<alumnos> alumnosList) {
        this.context = context;
        this.alumnosList = alumnosList;
        this.alumnosListFull = new ArrayList<>(alumnosList);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreAlumn, app, apm;
        RadioButton radioButton;
        RadioButton radioButtonAsistio, radioButtonFalto, radioButtonRetardo, radioButtonJustificante;

        RadioGroup radioGroupAsistencia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreAlumn = itemView.findViewById(R.id.itemalumno_nombre);
            app = itemView.findViewById(R.id.itemalumno_APP);
            apm = itemView.findViewById(R.id.itemalumno_APM);
            radioButton = itemView.findViewById(R.id.radioButtonAsistio);
            radioButtonAsistio = itemView.findViewById(R.id.radioButtonAsistio);
            radioButtonFalto = itemView.findViewById(R.id.radioButtonFalta);
            radioButtonRetardo = itemView.findViewById(R.id.radioButtonRetardo);
            radioButtonJustificante = itemView.findViewById(R.id.radioButtonJustificante);

            radioGroupAsistencia = itemView.findViewById(R.id.radioGroupAsistencia);


        }

    }

    @NonNull
    @Override
    public AdapterAlumno.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemalumno, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAlumno.ViewHolder holder, int position) {
        alumnos alumno = alumnosList.get(position);
        if (alumno != null) {
            holder.nombreAlumn.setText(alumno.getNombreAlumno());
            holder.app.setText(alumno.getApp());
            holder.apm.setText(alumno.getApm());

            String asistencia = alumno.getAsistencia();
            if ("A".equals(asistencia)) {
                holder.radioButtonAsistio.setChecked(true);
            } else {
                holder.radioButtonAsistio.setChecked(false);
                // Desmarca los otros RadioButtons si es necesario
                // (dependiendo de tu lógica de diseño)
            }

            // Establecer el estado de los botones de radio según la asistencia del alumno
            holder.radioButtonAsistio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alumno.setAsistencia("A");
                    notifyDataSetChanged(); // Actualiza la interfaz cuando se marca el RadioButton
                    Log.d("AdapterAlumno", "Asistencia de " + alumno.getNombreAlumno() + ": " + alumno.getAsistencia());
                }
            });

            holder.radioButtonFalto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alumno.setAsistencia("F");
                    Log.d("AdapterAlumno", "Asistencia de " + alumno.getNombreAlumno() + ": " + alumno.getAsistencia());
                }
            });

            holder.radioButtonRetardo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alumno.setAsistencia("R");
                    Log.d("AdapterAlumno", "Asistencia de " + alumno.getNombreAlumno() + ": " + alumno.getAsistencia());
                }
            });

            holder.radioButtonJustificante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alumno.setAsistencia("J");
                    Log.d("AdapterAlumno", "Asistencia de " + alumno.getNombreAlumno() + ": " + alumno.getAsistencia());
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return alumnosList.size();
    }

    public void filter(String text) {
        alumnosList.clear();
        if (text.isEmpty()) {
            alumnosList.addAll(alumnosListFull);
        } else {
            text = text.toLowerCase();
            for (alumnos item : alumnosListFull) {
                if (item.getNombreAlumno().toLowerCase().contains(text)) {
                    alumnosList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


    public void marcarTodosAsistieron(boolean asistieron) {
        for (alumnos alumno : alumnosList) {
            alumno.setAsistio(asistieron);
        }
        notifyDataSetChanged();
    }

    public void marcarTodasAsistencias(boolean asistio) {
        String opcionAsistencia = asistio ? "A" : ""; // Si asistio es verdadero, marcar "Asistio" ("A")
        for (alumnos alumno : alumnosList) {
            alumno.setAsistencia(opcionAsistencia);
        }
        notifyDataSetChanged();
    }





}
