package org.fer.java.jdb.model;

public class Directivo
{
    private int id_directivo;
    private int id_persona;
    private int id_area;
    private String cargo;
    private String nivel_jerarquico;
    private String fecha_inicio_cargo;

    // Getters y Setters
    public int getId_directivo() { return id_directivo; }
    public void setId_directivo(int id_directivo) { this.id_directivo = id_directivo; }

    public int getId_persona() { return id_persona; }
    public void setId_persona(int id_persona) { this.id_persona = id_persona; }

    public int getId_area() { return id_area; }
    public void setId_area(int id_area) { this.id_area = id_area; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getNivel_jerarquico() { return nivel_jerarquico; }
    public void setNivel_jerarquico(String nivel_jerarquico) { this.nivel_jerarquico = nivel_jerarquico; }

    public String getFecha_inicio_cargo() { return fecha_inicio_cargo; }
    public void setFecha_inicio_cargo(String fecha_inicio_cargo) { this.fecha_inicio_cargo = fecha_inicio_cargo; }

    @Override
    public String toString() {
        return "ID: " + id_directivo +
                " | Persona ID: " + id_persona +
                " | Área ID: " + id_area +
                " | Cargo: " + cargo +
                " | Nivel: " + nivel_jerarquico +
                " | Fecha Inicio: " + fecha_inicio_cargo;
    }
}
