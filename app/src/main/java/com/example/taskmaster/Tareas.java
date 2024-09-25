package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Tareas extends AppCompatActivity {

    EditText editTitulo;
    EditText editContenido;
    DatabaseHelper dbHelper;
    int tareaId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tareas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTitulo = findViewById(R.id.editTitulo);
        editContenido = findViewById(R.id.editContenido);
        dbHelper = new DatabaseHelper(this);

        // Obtener datos del Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("tareaId")) {
            tareaId = intent.getIntExtra("tareaId", -1); // Obtener el ID de la tarea

            String tareaTitulo = intent.getStringExtra("tareaTitulo");
            String tareaContenido = intent.getStringExtra("tareaContenido");

            // Establecer los datos en los EditText
            editTitulo.setText(tareaTitulo);
            editContenido.setText(tareaContenido);
        }

        Button buttonKeep = findViewById(R.id.buttonKeep);
        buttonKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = editTitulo.getText().toString();
                String contenido = editContenido.getText().toString();

                if (tareaId == -1) {
                    // Crear una nueva tarea si no existe
                    Tarea nuevaTarea = new Tarea(titulo, contenido); // Usar el constructor sin ID
                    dbHelper.addTarea(nuevaTarea); // Agregar la tarea a la base de datos
                    Toast.makeText(Tareas.this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Si hay tareaId, significa que estamos actualizando una tarea existente
                    Tarea tarea = new Tarea(tareaId, titulo, contenido, false); // Usar el constructor con ID
                    dbHelper.updateTarea(tarea);
                    Toast.makeText(Tareas.this, "Tarea actualizada correctamente", Toast.LENGTH_SHORT).show();
                }

                // Regresar a la vista de tareas
                Intent intent = new Intent(Tareas.this, ViewTareas.class);
                startActivity(intent);
            }
        });



        Button buttonBackT = findViewById(R.id.buttonBackT);
        buttonBackT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tareas.this, ViewTareas.class);
                startActivity(intent);
            }
        });
    }
}