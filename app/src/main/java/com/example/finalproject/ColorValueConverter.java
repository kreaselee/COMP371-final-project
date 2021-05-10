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
        String hex = "";
        if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
            hex = String.format("%02x%02x%02x", r, g, b).toUpperCase();
        }
        return hex;
    }

    public ArrayList<Integer> hexToRGB(String hex) {
        String string = hex;
        ArrayList<Integer> rgb = new ArrayList<>();

        if (hex.length() == 6) {
            int r = Integer.valueOf(string.substring(0,2), 16);
            int g = Integer.valueOf(string.substring(2,4), 16);
            int b = Integer.valueOf(string.substring(4,6), 16);

            rgb.add(r);
            rgb.add(g);
            rgb.add(b);
        }
        else {
            rgb.add(0);
            rgb.add(0);
            rgb.add(0);
        }

        return rgb;
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
