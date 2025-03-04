package org.fer.java.jdb.model;

public class Area
{
    private int id_area;
    private String nombre_area;
    private String descripcion;

    // Getters y Setters
    public int getId_area() { return id_area; }
    public void setId_area(int id_area) { this.id_area = id_area; }

    public String getNombre_area() { return nombre_area; }
    public void setNombre_area(String nombre_area) { this.nombre_area = nombre_area; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "ID: " + id_area + " | Nombre: " + nombre_area + " | Descripción: " + descripcion;
    }
}
