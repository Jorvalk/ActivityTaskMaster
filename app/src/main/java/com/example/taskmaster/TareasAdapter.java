package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder> {
    private List<Tarea> tareas;
    private Context context;
    private DatabaseHelper dbHelper;

    public TareasAdapter(List<Tarea> tareas, Context context) {
        this.tareas = tareas;
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        holder.textViewTitulo.setText(tarea.getTitulo()); // Mostrar el título de la tarea en el TextView
        final int currentPosition = position;

        // Asignar el estado del checkbox de acuerdo a la tarea
        holder.checkBoxRealizada.setChecked(tarea.isRealizada());

        // Al cambiar el estado del checkbox, actualizamos el estado de la tarea
        holder.checkBoxRealizada.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarea.setRealizada(isChecked);
            dbHelper.updateTarea(tarea); // Guardamos el estado actualizado en la base de datos
        });

        // Listener para el botón de eliminar
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteTarea(tarea.getId());
                tareas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, tareas.size());
            }
        });

        // Listener para el clic en la tarea
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para abrir la actividad de edición de tareas
                Intent intent = new Intent(context, Tareas.class);

                // Pasar los datos de la tarea al intent
                intent.putExtra("tareaId", tarea.getId());
                intent.putExtra("tareaTitulo", tarea.getTitulo());
                intent.putExtra("tareaContenido", tarea.getContenido());

                // Iniciar la actividad
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        CheckBox checkBoxRealizada;
        Button buttonDelete;

        public TareaViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            checkBoxRealizada = itemView.findViewById(R.id.checkBoxRealizada);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}