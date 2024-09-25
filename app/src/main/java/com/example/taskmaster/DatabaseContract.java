package com.example.taskmaster;

public class DatabaseContract {
    public static final String DATABASE_NAME = "tareas_db";
    public static final int DATABASE_VERSION = 1;

    // Definir constantes para la tabla de tareas
    public static final String TABLE_TAREAS = "tareas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_CONTENIDO = "contenido";
    public static final String COLUMN_REALIZADA = "realizada";

    private DatabaseContract() {}
}