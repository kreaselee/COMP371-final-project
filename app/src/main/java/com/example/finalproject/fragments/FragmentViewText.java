package com.example.finalproject.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.ColorValueConverter;
import com.example.finalproject.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentViewText extends Fragment {
    private TextView heading;
    private TextView heading2;
    private TextView text;
    private ImageView imageView;
    private ColorValueConverter colorValueConverter = new ColorValueConverter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_text, container, false);

        // set views by id
        heading = view.findViewById(R.id.textView_view_heading);
        heading2 = view.findViewById(R.id.textView_view_heading2);
        text = view.findViewById(R.id.textView_view_text);
        imageView = view.findViewById(R.id.imageView_view);

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

        // set views accordingly
        heading.setText(name);
        heading.setTextColor(Color.rgb(color1RGB.get(0), color1RGB.get(1), color1RGB.get(2)));

        String hexValues = "#" + color1Hex + " #" + color2Hex + " #" + color3Hex + " #" + color4Hex + " #" + color5Hex;
        heading2.setText(hexValues);
        heading2.setTextColor(Color.rgb(color2RGB.get(0), color2RGB.get(1), color2RGB.get(2)));

        text.setTextColor(Color.rgb(color3RGB.get(0), color3RGB.get(1), color3RGB.get(2)));
        imageView.setBackgroundColor(Color.rgb(color4RGB.get(0), color4RGB.get(1), color4RGB.get(2)));
        view.setBackgroundColor(Color.rgb(color5RGB.get(0), color5RGB.get(1), color5RGB.get(2)));

        return view;
    }
}
