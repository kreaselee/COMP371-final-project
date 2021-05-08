package com.example.finalproject;

import java.util.ArrayList;

public class Palette {

    // instance variables
    private String name;
    private ArrayList<String> colors;

    public Palette(String name, ArrayList<String> colors) {
        this.name = name;
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }
}
