package com.example.finalproject;

import android.graphics.Color;

import java.util.ArrayList;

public class ColorConverter {

    public String RGBToHex(ArrayList<Integer> rgb) {
        String hex = String.format("%02x%02x%02x", rgb.get(0), rgb.get(1), rgb.get(2)).toUpperCase();
        return hex;
    }

    public ArrayList<Integer> intToRGB(int color) {
        // get rgb values of the color
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // add to arrayList and return arrayList
        ArrayList<Integer> rgbValues = new ArrayList<>();
        rgbValues.add(red);
        rgbValues.add(green);
        rgbValues.add(blue);

        return rgbValues;
    }
}
