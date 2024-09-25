package com.example.taskmaster;

public class Tarea {
    private int id;
    private String titulo;
    private String contenido;
    private boolean realizada;

    // Constructor con ID
    public Tarea(int id, String titulo, String contenido, boolean realizada) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.realizada = realizada;
    }

    // Constructor sin ID
    public Tarea(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.realizada = false;
    }

    // Getters
    public int getId() {
        return id; // Devuelve el ID
    }

    public String getTitulo() {
        return titulo; // Devuelve el título
    }

    public String getContenido() {
        return contenido; // Devuelve el contenido
    }

    public boolean isRealizada() {
        return realizada; // Devuelve el estado de realizada
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo; // Establece el título
    }

    public void setContenido(String contenido) {
        this.contenido = contenido; // Establece el contenido
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada; // Establece el estado de realizada
    }

    // Sobrescribir el método toString para facilitar la impresión de los objetos
    @Override
    public String toString() {
        return "Tarea{id=" + id + ", titulo='" + titulo + "', contenido='" + contenido + "', realizada=" + realizada + '}';
    }
}
