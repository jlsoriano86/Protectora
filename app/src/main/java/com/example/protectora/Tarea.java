package com.example.protectora;

//Clase POJO con todos los atributos de las tareas
public class Tarea {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    private String id;
    private String description;



    public Tarea(String name, String id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;

    }

    @Override
    public String toString() {
        return name;
    }
}
