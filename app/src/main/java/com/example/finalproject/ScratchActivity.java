package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class ScratchActivity extends AppCompatActivity {

    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;
    private ImageView currentColor;

    private SeekBar seekBarR;
    private SeekBar seekBarB;
    private SeekBar seekBarG;
    private int redValue = 0;
    private int greenValue = 0;
    private int blueValue = 0;

    private Button button_cancel;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        color1 = findViewById(R.id.color1_scratch);
        color2 = findViewById(R.id.color2_scratch);
        color3 = findViewById(R.id.color3_scratch);
        color4 = findViewById(R.id.color4_scratch);
        color5 = findViewById(R.id.color5_scratch);

        currentColor = color1;

        final View.OnClickListener colorClickListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int viewId = v.getId();
                        switch (viewId) {
                            case R.id.color1_scratch:
                                currentColor = color1;
                                break;
                            case R.id.color2_scratch:
                                currentColor = color2;
                                break;
                            case R.id.color3_scratch:
                                currentColor = color3;
                                break;
                            case R.id.color4_scratch:
                                currentColor = color4;
                                break;
                            case R.id.color5_scratch:
                                currentColor = color5;
                                break;
                        }
                    }
                };

        color1.setOnClickListener(colorClickListener);
        color2.setOnClickListener(colorClickListener);
        color3.setOnClickListener(colorClickListener);
        color4.setOnClickListener(colorClickListener);
        color5.setOnClickListener(colorClickListener);

        SeekBar seekBarR = findViewById(R.id.seekBarR);
        SeekBar seekBarG = findViewById(R.id.seekBarG);
        SeekBar seekBarB = findViewById(R.id.seekBarB);

        int width = currentColor.getWidth();
        int height = currentColor.getHeight();

        final SeekBar.OnSeekBarChangeListener seekBarChangeListener =
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int viewId = seekBar.getId();
                        switch (viewId) {
                            case R.id.seekBarR:
                                redValue = progress;
                                break;
                            case R.id.seekBarG:
                                greenValue = progress;
                                break;
                            case R.id.seekBarB:
                                blueValue = progress;
                                break;
                        }
                        int color = Color.rgb(redValue, greenValue, blueValue);

                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        currentColor.setImageBitmap(bitmap);
                        Canvas canvas = new Canvas(bitmap);

                        Paint paint = new Paint();
                        paint.setColor(color);

                        int left = 0;
                        int top = 0;
                        int right = bitmap.getWidth();
                        int bottom = bitmap.getHeight();

                        Rect rect = new Rect(left, top, right, bottom);
                        canvas.drawRect(rect, paint);


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

    public void loadFragment(Fragment fragment, int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }
}
