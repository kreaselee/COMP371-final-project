package com.example.finalproject;

import android.animation.ArgbEvaluator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import codes.side.andcolorpicker.model.IntegerHSLColor;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

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

    private EditText editTextHex;
    private EditText editTextR;
    private EditText editTextG;
    private EditText editTextB;

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

        editTextHex = findViewById(R.id.editTextHex);

        editTextHex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextR = findViewById(R.id.editTextR);
        editTextG = findViewById(R.id.editTextG);
        editTextB = findViewById(R.id.editTextB);

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
        editTextHex.setText(hex);

        redValue = valR;
        greenValue = valG;
        blueValue = valB;

        seekBarR.setProgress(valR);
        seekBarG.setProgress(valG);
        seekBarB.setProgress(valB);

        editTextR.setText(Integer.toString(valR));
        editTextG.setText(Integer.toString(valG));
        editTextB.setText(Integer.toString(valB));

        final SeekBar.OnSeekBarChangeListener seekBarChangeListener =
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int viewId = seekBar.getId();
                        switch (viewId) {
                            case R.id.seekBarR:
                                redValue = progress;
                                editTextR.setText(Integer.toString(progress));
                                break;
                            case R.id.seekBarG:
                                greenValue = progress;
                                editTextG.setText(Integer.toString(progress));
                                break;
                            case R.id.seekBarB:
                                blueValue = progress;
                                editTextB.setText(Integer.toString(progress));
                                break;
                        }
                        currentColor = Color.rgb(redValue, greenValue, blueValue);
                        currentView.setBackgroundColor(currentColor);

                        String hex = String.format("%02x%02x%02x", redValue, greenValue, blueValue).toUpperCase();
                        editTextHex.setText(hex);
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

        editTextR.setText(currentColorRGB.get(0).toString());
        editTextG.setText(currentColorRGB.get(1).toString());
        editTextB.setText(currentColorRGB.get(2).toString());
    }
}
