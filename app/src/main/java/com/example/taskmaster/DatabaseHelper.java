package com.example.taskmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + DatabaseContract.TABLE_TAREAS + "(" +
                DatabaseContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.COLUMN_TITULO + " TEXT, " +
                DatabaseContract.COLUMN_CONTENIDO + " TEXT, " +
                DatabaseContract.COLUMN_REALIZADA + " INTEGER DEFAULT 0)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TAREAS);
        onCreate(db);
    }

    // Agregar una nueva tarea
    public void addTarea(Tarea tarea) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.COLUMN_TITULO, tarea.getTitulo());
        values.put(DatabaseContract.COLUMN_CONTENIDO, tarea.getContenido());
        values.put(DatabaseContract.COLUMN_REALIZADA, tarea.isRealizada() ? 1 : 0);

        // Insertar la tarea en la base de datos
        db.insert(DatabaseContract.TABLE_TAREAS, null, values);
        db.close();
    }


    // Obtener todas las tareas
    public List<Tarea> getAllTareas() {
        List<Tarea> tareas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.TABLE_TAREAS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Tarea tarea = new Tarea(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_TITULO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_CONTENIDO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_REALIZADA)) == 1
                );

                tareas.add(tarea);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tareas;
    }

    public Tarea getTareaById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.TABLE_TAREAS,
                null,
                DatabaseContract.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //Obtener los datos de la tarea
                Tarea tarea = new Tarea(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_TITULO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_CONTENIDO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_REALIZADA)) == 1
                );
                cursor.close(); // Cerrar el cursor
                return tarea; // Retornar la tarea
            }
            cursor.close(); // Cerrar el cursor si no se encuentra la tarea
        }
        return null; // Retornar null si no se encuentra la tarea
    }

    // Actualizar una tarea
    public void updateTarea(Tarea tarea) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.COLUMN_TITULO, tarea.getTitulo());
        values.put(DatabaseContract.COLUMN_CONTENIDO, tarea.getContenido());
        values.put(DatabaseContract.COLUMN_REALIZADA, tarea.isRealizada() ? 1 : 0);

        db.update(DatabaseContract.TABLE_TAREAS, values, DatabaseContract.COLUMN_ID + " = ?", new String[]{String.valueOf(tarea.getId())});
        db.close();
    }

    // Eliminar una tarea
    public void deleteTarea(int tareaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tareas", "id = ?", new String[]{String.valueOf(tareaId)});
        db.close();
    }

}