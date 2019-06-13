package com.example.protectora;

public class Tarea {
    private String idTarea;
    private String nombreTarea;
    private String descripcionTarea;




    public Tarea() {
        this.idTarea = null;
        this.nombreTarea = "";
        this.descripcionTarea = "";
    }

    public Tarea(String nombreTarea, String descripcionTarea) {
        this.idTarea = null;
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;

    }

    public Tarea(String idTarea, String nombreTarea, String descripcionTarea) {
        this.idTarea = idTarea;
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;

    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String id) {
        this.idTarea = idTarea;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) { this.nombreTarea = nombreTarea; }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) { this.descripcionTarea = descripcionTarea; }





    @Override
    public String toString() {
        return "Tarea {" +
                "idTarea=" + idTarea +
                ", nombreTarea='" + nombreTarea + '\'' +
                ", descripcionTarea='" + descripcionTarea + '\'' +
                '}';
    }
}
