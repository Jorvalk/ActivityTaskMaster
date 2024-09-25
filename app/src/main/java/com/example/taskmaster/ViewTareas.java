package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewTareas extends AppCompatActivity {
    private List<Tarea> tareas;
    private RecyclerView recyclerView;
    private TareasAdapter adapter;
    private DatabaseHelper dbHelper;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);

        dbHelper = new DatabaseHelper(this);

        // Inicializar la lista de tareas
        tareas = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tareas = dbHelper.getAllTareas();
        adapter = new TareasAdapter(tareas, this);
        recyclerView.setAdapter(adapter);

        // Cargar las tareas desde la base de datos
        cargarTareas();

        // Botones
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTareas.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad para agregar una nueva tarea
                Intent intent = new Intent(ViewTareas.this, Tareas.class);
                // No pasamos ningún ID, ya que estamos creando una tarea nueva
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Obtener las tareas de la base de datos SQLite
        tareas = dbHelper.getAllTareas();
        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }



    private void cargarTareas() {
        tareas.clear(); // Limpiar la lista actual
        tareas.addAll(dbHelper.getAllTareas()); // Obtener tareas de la base de datos
        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }
}
