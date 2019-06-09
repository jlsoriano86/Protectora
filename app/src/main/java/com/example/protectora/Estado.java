package com.example.protectora;

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
