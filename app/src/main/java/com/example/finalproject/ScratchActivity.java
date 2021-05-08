package com.example.finalproject;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.Random;

public class ScratchActivity extends AppCompatActivity {

    private ConstraintLayout scratchLayout;

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

        // find views by id
        scratchLayout = findViewById(R.id.scratchLayout);

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
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> color1RGB = getRGBValues(color1);
                ArrayList<Integer> color2RGB = getRGBValues(color2);
                ArrayList<Integer> color3RGB = getRGBValues(color3);
                ArrayList<Integer> color4RGB = getRGBValues(color4);
                ArrayList<Integer> color5RGB = getRGBValues(color5);

                Intent intent = new Intent(ScratchActivity.this, NameActivity.class);
                intent.putExtra("color1RGB", color1RGB);
                intent.putExtra("color2RGB", color2RGB);
                intent.putExtra("color3RGB", color3RGB);
                intent.putExtra("color4RGB", color4RGB);
                intent.putExtra("color5RGB", color5RGB);
                startActivity(intent);
                finish();
            }
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

        /*
        currentView = color1;

        // get colors of current color imageView (i.e., first view)
        // set values and colors of the rest of the views accordingly
        currentColorRGB = getRGBValues(currentView);
        int valR = currentColorRGB.get(0);
        int valG = currentColorRGB.get(1);
        int valB = currentColorRGB.get(2);

        redValue = valR;
        greenValue = valG;
        blueValue = valB;

        // set hex value
        String hex = String.format("%02x%02x%02x", valR, valG, valB).toUpperCase();
        textViewHexVal.setText(hex);

        // set seekBar progress values
        seekBarR.setProgress(valR);
        seekBarG.setProgress(valG);
        seekBarB.setProgress(valB);

        // set RGB values
        textViewRVal.setText(Integer.toString(valR));
        textViewGVal.setText(Integer.toString(valG));
        textViewBVal.setText(Integer.toString(valB));

         */

        // when imageView is clicked on, set it be the current color imageView
        final View.OnClickListener colorClickListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        // create random palette to set as the default
        Random rnd = new Random();
        // get two random colors and set as first and last colors
        // int c1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        // int c5 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

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

    // method to get rgb values
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Integer> getRGBValues(ImageView view) {
        // get color of color imageView
        ColorDrawable drawable = (ColorDrawable) view.getBackground();
        int color = drawable.getColor();

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

    // method to select color imageView
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void selectView(ImageView view) {
        // set color imageView to current view
        currentView = view;

        // get colors of current color imageView (i.e., first view)
        // set values and colors of the rest of the views accordingly
        currentColorRGB = getRGBValues(currentView);
        int valR = currentColorRGB.get(0);
        int valG = currentColorRGB.get(1);
        int valB = currentColorRGB.get(2);

        redValue = valR;
        greenValue = valG;
        blueValue = valB;

        // set hex value
        String hex = String.format("%02x%02x%02x", valR, valG, valB).toUpperCase();
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
