package com.example.protectora;

public class Animales {
    private String name;
    private String id;
    private String birth;
    private String type;
    private String state;
    private String img;


    public Animales(String name, String id, String birth, String type, String state, String img) {
        this.name = name;
        this.id = id;
        this.birth = birth;
        this.type = type;
        this.state = state;
        this.img = img;
    }

    public String getData() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
