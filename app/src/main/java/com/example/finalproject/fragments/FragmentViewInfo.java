package com.example.finalproject.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.ColorValueConverter;
import com.example.finalproject.R;

import java.util.ArrayList;

public class FragmentViewInfo extends Fragment {
    private TextView hexText1;
    private TextView hexText2;
    private TextView hexText3;
    private TextView hexText4;
    private TextView hexText5;

    private TextView rgbText1;
    private TextView rgbText2;
    private TextView rgbText3;
    private TextView rgbText4;
    private TextView rgbText5;

    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;

    private ColorValueConverter colorValueConverter = new ColorValueConverter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_info, container, false);

        // set views by id
        hexText1 = view.findViewById(R.id.textView_view_color1_hex);
        hexText2 = view.findViewById(R.id.textView_view_color2_hex);
        hexText3 = view.findViewById(R.id.textView_view_color3_hex);
        hexText4 = view.findViewById(R.id.textView_view_color4_hex);
        hexText5 = view.findViewById(R.id.textView_view_color5_hex);

        rgbText1 = view.findViewById(R.id.textView_view_color1_rgb);
        rgbText2 = view.findViewById(R.id.textView_view_color2_rgb);
        rgbText3 = view.findViewById(R.id.textView_view_color3_rgb);
        rgbText4 = view.findViewById(R.id.textView_view_color4_rgb);
        rgbText5 = view.findViewById(R.id.textView_view_color5_rgb);

        color1 = view.findViewById(R.id.color1_view);
        color2 = view.findViewById(R.id.color2_view);
        color3 = view.findViewById(R.id.color3_view);
        color4 = view.findViewById(R.id.color4_view);
        color5 = view.findViewById(R.id.color5_view);

        // get arguments
        String name = getArguments().getString("name");
        ArrayList<Integer> color1RGB = getArguments().getIntegerArrayList("color1RGB");
        ArrayList<Integer> color2RGB = getArguments().getIntegerArrayList("color2RGB");
        ArrayList<Integer> color3RGB = getArguments().getIntegerArrayList("color3RGB");
        ArrayList<Integer> color4RGB = getArguments().getIntegerArrayList("color4RGB");
        ArrayList<Integer> color5RGB = getArguments().getIntegerArrayList("color5RGB");

        // get hex values
        String color1Hex = colorValueConverter.RGBToHex(color1RGB.get(0), color1RGB.get(1), color1RGB.get(2));
        String color2Hex = colorValueConverter.RGBToHex(color2RGB.get(0), color2RGB.get(1), color2RGB.get(2));
        String color3Hex = colorValueConverter.RGBToHex(color3RGB.get(0), color3RGB.get(1), color3RGB.get(2));
        String color4Hex = colorValueConverter.RGBToHex(color4RGB.get(0), color4RGB.get(1), color4RGB.get(2));
        String color5Hex = colorValueConverter.RGBToHex(color5RGB.get(0), color5RGB.get(1), color5RGB.get(2));

        // set text and imageView backgrounds
        setViews(hexText1, rgbText1, color1, color1Hex, color1RGB);
        setViews(hexText2, rgbText2, color2, color2Hex, color2RGB);
        setViews(hexText3, rgbText3, color3, color3Hex, color3RGB);
        setViews(hexText4, rgbText4, color4, color4Hex, color4RGB);
        setViews(hexText5, rgbText5, color5, color5Hex, color5RGB);

        return view;
    }

    public void setViews(TextView hexTextView, TextView rgbTextView, ImageView colorView,
                         String hex, ArrayList<Integer> rgb) {
        hexTextView.setText(hex);
        String rgbString = "R" + rgb.get(0) + " G" + rgb.get(1) + " B" + rgb.get(2);
        rgbTextView.setText(rgbString);
        colorView.setBackgroundColor(Color.rgb(rgb.get(0), rgb.get(1), rgb.get(2)));

        // change text color depending on background
        if ((rgb.get(0)*0.299 + rgb.get(1)*0.587 + rgb.get(2)*0.114) > 149) {
            hexTextView.setTextColor(Color.BLACK);
            rgbTextView.setTextColor(Color.BLACK);
        }
        else {
            hexTextView.setTextColor(Color.WHITE);
            rgbTextView.setTextColor(Color.WHITE);
        }
    }
}
