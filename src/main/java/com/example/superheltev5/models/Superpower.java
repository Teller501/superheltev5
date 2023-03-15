package com.example.superheltev5.models;

public class Superpower {
    private int id;
    private String name;

    public Superpower( int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Superpower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
