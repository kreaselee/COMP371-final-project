package com.example.finalproject;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class ScratchActivity extends AppCompatActivity {

    private ColorValueConverter colorValueConverter;

    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;
    private ImageView displayColor;
    private ImageView currentView;
    private int currentColor;
    private ArrayList<Integer> currentColorRGB;

    private TextView textViewHexVal;
    private TextView textViewRVal;
    private TextView textViewGVal;
    private TextView textViewBVal;

    private SeekBar seekBarR;
    private SeekBar seekBarB;
    private SeekBar seekBarG;
    private int redValue = 0;
    private int greenValue = 0;
    private int blueValue = 0;

    private Button button_cancel;
    private Button button_save;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);
        colorValueConverter = new ColorValueConverter();

        // find views by id
        color1 = findViewById(R.id.color1_scratch);
        color2 = findViewById(R.id.color2_scratch);
        color3 = findViewById(R.id.color3_scratch);
        color4 = findViewById(R.id.color4_scratch);
        color5 = findViewById(R.id.color5_scratch);
        displayColor = findViewById(R.id.currentColor_scratch);

        textViewHexVal = findViewById(R.id.textViewHex_value);
        textViewRVal = findViewById(R.id.textViewR_value);
        textViewGVal = findViewById(R.id.textViewG_value);
        textViewBVal = findViewById(R.id.textViewB_value);

        seekBarR = findViewById(R.id.seekBarR);
        seekBarG = findViewById(R.id.seekBarG);
        seekBarB = findViewById(R.id.seekBarB);

        button_cancel = findViewById(R.id.button_cancel_scratch);
        button_save = findViewById(R.id.button_save_scratch);

        // when cancel button is clicked, exit activity
        button_cancel.setOnClickListener(v -> finish());

        button_save.setOnClickListener(v -> {
            ArrayList<ImageView> views = new ArrayList<>();
            views.add(color1);
            views.add(color2);
            views.add(color3);
            views.add(color4);
            views.add(color5);

            Intent intent = new Intent(ScratchActivity.this, NameActivity.class);
            for (int i = 0; i < views.size(); i++) {
                ColorDrawable drawable = (ColorDrawable) views.get(i).getBackground();
                int color = drawable.getColor();
                int num = i+1;

                ArrayList<Integer> colorRGB = colorValueConverter.intToRGB(color);
                intent.putExtra("color" + num + "RGB", colorRGB);
            }
            startActivity(intent);
            finish();
        });

        Intent intent = getIntent();

        if (intent.getExtras() == null) {
            // set default colors and select first color imageView
            setDefaultColors();
        }
        else {
            ArrayList<Integer> c1RGB = intent.getIntegerArrayListExtra("color1RGB");
            ArrayList<Integer> c2RGB = intent.getIntegerArrayListExtra("color2RGB");
            ArrayList<Integer> c3RGB = intent.getIntegerArrayListExtra("color3RGB");
            ArrayList<Integer> c4RGB = intent.getIntegerArrayListExtra("color4RGB");
            ArrayList<Integer> c5RGB = intent.getIntegerArrayListExtra("color5RGB");

            // set color imageViews accordingly
            color1.setBackgroundColor(Color.rgb(c1RGB.get(0), c1RGB.get(1), c1RGB.get(2)));
            color2.setBackgroundColor(Color.rgb(c2RGB.get(0), c2RGB.get(1), c2RGB.get(2)));
            color3.setBackgroundColor(Color.rgb(c3RGB.get(0), c3RGB.get(1), c3RGB.get(2)));
            color4.setBackgroundColor(Color.rgb(c4RGB.get(0), c4RGB.get(1), c4RGB.get(2)));
            color5.setBackgroundColor(Color.rgb(c5RGB.get(0), c5RGB.get(1), c5RGB.get(2)));
        }
        selectView(color1);

        // when imageView is clicked on, set it be the current color imageView
        final View.OnClickListener colorClickListener =
                v -> {
                    int viewId = v.getId();
                    switch (viewId) {
                        case R.id.color1_scratch:
                            selectView(color1);
                            break;
                        case R.id.color2_scratch:
                            selectView(color2);
                            break;
                        case R.id.color3_scratch:
                            selectView(color3);
                            break;
                        case R.id.color4_scratch:
                            selectView(color4);
                            break;
                        case R.id.color5_scratch:
                            selectView(color5);
                            break;
                    }
                };

        color1.setOnClickListener(colorClickListener);
        color2.setOnClickListener(colorClickListener);
        color3.setOnClickListener(colorClickListener);
        color4.setOnClickListener(colorClickListener);
        color5.setOnClickListener(colorClickListener);

        // change color and view values according the seekBar changes
        final SeekBar.OnSeekBarChangeListener seekBarChangeListener =
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int viewId = seekBar.getId();
                        switch (viewId) {
                            case R.id.seekBarR:
                                redValue = progress;
                                textViewRVal.setText(Integer.toString(progress));
                                break;
                            case R.id.seekBarG:
                                greenValue = progress;
                                textViewGVal.setText(Integer.toString(progress));
                                break;
                            case R.id.seekBarB:
                                blueValue = progress;
                                textViewBVal.setText(Integer.toString(progress));
                                break;
                        }
                        currentColor = Color.rgb(redValue, greenValue, blueValue);
                        currentView.setBackgroundColor(currentColor);
                        displayColor.setBackgroundColor(currentColor);

                        String hex = String.format("%02x%02x%02x", redValue, greenValue, blueValue).toUpperCase();
                        textViewHexVal.setText(hex);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                };

        seekBarR.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarG.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarB.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    public void setDefaultColors() {
        // grayscale palette
        int c1 = Color.argb(255, 60, 60, 60);
        int c5 = Color.argb(255, 255, 255, 255);
        // get colors in between two end colors
        int c2 = (Integer) new ArgbEvaluator().evaluate((float)1/4, c1, c5);
        int c3 = (Integer) new ArgbEvaluator().evaluate((float)2/4, c1, c5);
        int c4 = (Integer) new ArgbEvaluator().evaluate((float)3/4, c1, c5);

        // set color imageViews accordingly
        color1.setBackgroundColor(c1);
        color2.setBackgroundColor(c2);
        color3.setBackgroundColor(c3);
        color4.setBackgroundColor(c4);
        color5.setBackgroundColor(c5);
    }

    // method to select color imageView
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void selectView(ImageView view) {
        // set color imageView to current view
        currentView = view;

        // get colors of current color imageView (i.e., first view)
        // set values and colors of the rest of the views accordingly
        ColorDrawable drawable = (ColorDrawable) currentView.getBackground();
        int color = drawable.getColor();
        currentColorRGB = colorValueConverter.intToRGB(color);
        int valR = currentColorRGB.get(0);
        int valG = currentColorRGB.get(1);
        int valB = currentColorRGB.get(2);

        redValue = valR;
        greenValue = valG;
        blueValue = valB;

        // set hex value
        String hex = colorValueConverter.RGBToHex(valR, valG, valB);
        textViewHexVal.setText(hex);

        // display the color of the current color imageView
        displayColor.setBackgroundColor(Color.rgb(valR, valG, valB));

        // set seekBar progress values
        seekBarR.setProgress(valR);
        seekBarG.setProgress(valG);
        seekBarB.setProgress(valB);

        // set rgb values
        textViewRVal.setText(Integer.toString(valR));
        textViewGVal.setText(Integer.toString(valG));
        textViewBVal.setText(Integer.toString(valB));
    }
}
