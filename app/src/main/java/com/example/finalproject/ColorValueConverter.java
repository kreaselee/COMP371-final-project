package com.example.finalproject;

import android.graphics.Color;

import java.util.ArrayList;

public class ColorValueConverter {

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return String hex code
     */
    public String RGBToHex(int r, int g, int b) {
        String hex = String.format("%02x%02x%02x", r, g, b).toUpperCase();
        return hex;
    }

    /**
     *
     * @param color
     * @return ArrayList of rgb values
     */
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
