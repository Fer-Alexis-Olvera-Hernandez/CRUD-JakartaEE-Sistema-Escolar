package org.fer.java.jdb.model;



public class Operativo {
    private int id_operativo;
    private int id_persona;
    private int id_area;
    private String cargo;
    private String horario;
    private double sueldo;

    // Getters y Setters
    public int getId_operativo() { return id_operativo; }
    public void setId_operativo(int id_operativo) { this.id_operativo = id_operativo; }

    public int getId_persona() { return id_persona; }
    public void setId_persona(int id_persona) { this.id_persona = id_persona; }

    public int getId_area() { return id_area; }
    public void setId_area(int id_area) { this.id_area = id_area; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public double getSueldo() { return sueldo; }
    public void setSueldo(double sueldo) { this.sueldo = sueldo; }

    @Override
    public String toString() {
        return "ID: " + id_operativo +
                " | Persona ID: " + id_persona +
                " | Área ID: " + id_area +
                " | Cargo: " + cargo +
                " | Horario: " + horario +
                " | Sueldo: $" + sueldo;
    }
}
