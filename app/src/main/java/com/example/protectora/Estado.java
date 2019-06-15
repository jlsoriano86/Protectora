package com.example.protectora;
//Esta clase se utiliza para cargar los estados de los animales
public class Estado {
    private String name;
    private String value;

    public Estado(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
