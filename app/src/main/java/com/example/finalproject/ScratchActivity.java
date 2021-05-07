package com.example.finalproject;

import android.animation.ArgbEvaluator;
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

    private static final String api_url="http://colormind.io/api/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        scratchLayout = findViewById(R.id.scratchLayout);

        color1 = findViewById(R.id.color1_scratch);
        color2 = findViewById(R.id.color2_scratch);
        color3 = findViewById(R.id.color3_scratch);
        color4 = findViewById(R.id.color4_scratch);
        color5 = findViewById(R.id.color5_scratch);

        setDefaultColors();
        currentView = color1;

        textViewHexVal = findViewById(R.id.textViewHex_value);

        textViewRVal = findViewById(R.id.textViewR_value);
        textViewGVal = findViewById(R.id.textViewG_value);
        textViewBVal = findViewById(R.id.textViewB_value);

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

        seekBarR = findViewById(R.id.seekBarR);
        seekBarG = findViewById(R.id.seekBarG);
        seekBarB = findViewById(R.id.seekBarB);

        currentColorRGB = getColorValues(currentView);
        int valR = currentColorRGB.get(0);
        int valG = currentColorRGB.get(1);
        int valB = currentColorRGB.get(2);
        // Log.d("rgb", currentColorRGB.toString());

        String hex = String.format("%02x%02x%02x", valR, valG, valB).toUpperCase();
        textViewHexVal.setText(hex);

        redValue = valR;
        greenValue = valG;
        blueValue = valB;

        seekBarR.setProgress(valR);
        seekBarG.setProgress(valG);
        seekBarB.setProgress(valB);

        textViewRVal.setText(Integer.toString(valR));
        textViewGVal.setText(Integer.toString(valG));
        textViewBVal.setText(Integer.toString(valB));

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

        button_cancel = findViewById(R.id.button_cancel_scratch);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setDefaultColors() {
        Random rnd = new Random();
        int c1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        int c5 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Log.d("color", new ArgbEvaluator().evaluate(25, c1, c5).toString());
        int c2 = (Integer) new ArgbEvaluator().evaluate((float)1/4, c1, c5);
        int c3 = (Integer) new ArgbEvaluator().evaluate((float)2/4, c1, c5);
        int c4 = (Integer) new ArgbEvaluator().evaluate((float)3/4, c1, c5);

        color1.setBackgroundColor(c1);
        color2.setBackgroundColor(c2);
        color3.setBackgroundColor(c3);
        color4.setBackgroundColor(c4);
        color5.setBackgroundColor(c5);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Integer> getColorValues(ImageView view) {

        ColorDrawable drawable = (ColorDrawable) view.getBackground();
        int color = drawable.getColor();

        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        ArrayList<Integer> rgbValues = new ArrayList<>();
        rgbValues.add(red);
        rgbValues.add(green);
        rgbValues.add(blue);

        // ArrayList<Integer> rgbValues = new ArrayList<>();
        return rgbValues;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void selectView(ImageView view) {
        currentView = view;
        currentColorRGB = getColorValues(currentView);
        Log.d("rgb", currentColorRGB.toString());

        seekBarR.setProgress(currentColorRGB.get(0));
        seekBarG.setProgress(currentColorRGB.get(1));
        seekBarB.setProgress(currentColorRGB.get(2));

        textViewRVal.setText(currentColorRGB.get(0).toString());
        textViewGVal.setText(currentColorRGB.get(1).toString());
        textViewBVal.setText(currentColorRGB.get(2).toString());
    }
}
