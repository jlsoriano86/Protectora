package com.example.protectora;

public class Tarea {
    private Long idTarea;
    private String nombreTarea;
    private String descripcionTarea;
    private Integer fechaInicio;
    private Integer fechaFin;



    public Tarea() {
        this.idTarea = null;
        this.nombreTarea = "";
        this.descripcionTarea = "";
        this.fechaInicio = 0;
        this.fechaFin = 0;

    }

    public Tarea(String nombreTarea, String descripcionTarea, Integer fechaInicio, Integer fechaFin) {
        this.idTarea = null;
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Tarea(Long idTarea, String nombreTarea, String descripcionTarea, Integer fechaInicio, Integer fechaFin) {
        this.idTarea = idTarea;
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public Integer getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Integer fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Integer fechaFin) {
        this.fechaFin = fechaFin;
    }



    @Override
    public String toString() {
        return "Tarea {" +
                "idTarea=" + idTarea +
                ", nombreTarea='" + nombreTarea + '\'' +
                ", descripcionTarea='" + descripcionTarea + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin + '\'' +
                '}';
    }
}
