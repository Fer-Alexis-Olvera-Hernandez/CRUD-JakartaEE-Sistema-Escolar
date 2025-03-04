package org.fer.java.jdb.model;

public class Docente {
    private int id_docente;
    private int id_persona;
    private int id_area;
    private String titulo_academico;
    private String especialidad;
    private String tipo_contrato;

    // Getters y Setters
    public int getId_docente() { return id_docente; }
    public void setId_docente(int id_docente) { this.id_docente = id_docente; }

    public int getId_persona() { return id_persona; }
    public void setId_persona(int id_persona) { this.id_persona = id_persona; }

    public int getId_area() { return id_area; }
    public void setId_area(int id_area) { this.id_area = id_area; }

    public String getTitulo_academico() { return titulo_academico; }
    public void setTitulo_academico(String titulo_academico) { this.titulo_academico = titulo_academico; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTipo_contrato() { return tipo_contrato; }
    public void setTipo_contrato(String tipo_contrato) { this.tipo_contrato = tipo_contrato; }

    @Override
    public String toString() {
        return "ID: " + id_docente +
                " | Persona ID: " + id_persona +
                " | Área ID: " + id_area +
                " | Título Académico: " + titulo_academico +
                " | Especialidad: " + especialidad +
                " | Tipo de Contrato: " + tipo_contrato;
    }
}
