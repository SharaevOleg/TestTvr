package com.tvrtest.tverskoi2.model;

public class Profession {

    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = formatString(name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private String formatString(String string) {
        string = string.trim();
        string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        return string;
    }
}
