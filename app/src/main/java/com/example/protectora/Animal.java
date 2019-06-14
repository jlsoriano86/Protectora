package com.example.protectora;

public class Animal {
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getState_desc() {
        return state_desc;
    }

    public void setState_desc(String state_desc) {
        this.state_desc = state_desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String id;
    private String birth;
    private String type;
    private String state;
    private String state_desc;
    private String img;


    public Animal(String name, String id, String birth, String type, String state, String state_desc, String img) {
        this.name = name;
        this.id = id;
        this.birth = birth;
        this.type = type;
        this.state = state;
        this.state_desc = state_desc;
        this.img = img;
    }

    @Override
    public String toString() {
        return name;
    }
}
