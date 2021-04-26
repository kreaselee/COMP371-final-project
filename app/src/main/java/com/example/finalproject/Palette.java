package com.example.finalproject;

import java.util.ArrayList;

public class Palette {

    // instance variables
    private String name;
    private ArrayList<ArrayList<Integer>> colors;

    public Palette(String name, ArrayList<ArrayList<Integer>> colors) {
        this.name = name;
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ArrayList<Integer>> getColors() {
        return colors;
    }

    public void setColors(ArrayList<ArrayList<Integer>> colors) {
        this.colors = colors;
    }
}
