package com.example.protectora;

public class Tarea {
    private Long idTarea;
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

    public Tarea(Long idTarea, String nombreTarea, String descripcionTarea) {
        this.idTarea = idTarea;
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;

    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long id) {
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
