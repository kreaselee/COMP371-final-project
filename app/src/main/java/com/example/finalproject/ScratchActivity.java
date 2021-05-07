package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private SeekBar seekBarR;
    private SeekBar seekBarB;
    private SeekBar seekBarG;
    private int redValue = 0;
    private int greenValue = 0;
    private int blueValue = 0;

    private Button button_cancel;

    private static final String api_url="http://colormind.io/api/";
    private static AsyncHttpClient client = new AsyncHttpClient();

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

        final View.OnClickListener colorClickListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int viewId = v.getId();
                        switch (viewId) {
                            case R.id.color1_scratch:
                                currentView = color1;
                                break;
                            case R.id.color2_scratch:
                                currentView = color2;
                                break;
                            case R.id.color3_scratch:
                                currentView = color3;
                                break;
                            case R.id.color4_scratch:
                                currentView = color4;
                                break;
                            case R.id.color5_scratch:
                                currentView = color5;
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

        ArrayList<Integer> rgbValues = getColorValues(currentView);
        seekBarR.setProgress(rgbValues.get(0));
        seekBarG.setProgress(rgbValues.get(1));
        seekBarB.setProgress(rgbValues.get(2));

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
                        currentColor = Color.rgb(redValue, greenValue, blueValue);

                        int width = currentView.getWidth();
                        int height = currentView.getHeight();

                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        currentView.setImageBitmap(bitmap);
                        Canvas canvas = new Canvas(bitmap);

                        int left = 0;
                        int top = 0;
                        int right = bitmap.getWidth();
                        int bottom = bitmap.getHeight();

                        Paint paint = new Paint();
                        paint.setColor(currentColor);

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

    public void setDefaultColors() {
        // add header to client
        client.addHeader("Accept", "application/json");
        RequestParams params = new RequestParams();
        params.put("model", "default");

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("model", "default");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(this, api_url, entity, "application/json", new AsyncHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                int width = scratchLayout.getWidth();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int height = Math.round(200 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                Log.d("info", Integer.toString(height));
                int sectionWidth = width / 5;

                ArrayList<ImageView> views = new ArrayList<>();
                views.add(color1);
                views.add(color2);
                views.add(color3);
                views.add(color4);
                views.add(color5);

                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    JSONArray result = json.getJSONArray("result");

                    // Log.i("values", result.get(0).toString());

                    for (int i = 0; i < result.length(); i++) {
                        Bitmap bitmap = Bitmap.createBitmap(sectionWidth, height, Bitmap.Config.ARGB_8888);
                        views.get(i).setImageBitmap(bitmap);
                        Canvas canvas = new Canvas(bitmap);

                        JSONArray values = result.getJSONArray(i);
                        int r = values.getInt(0);
                        int g = values.getInt(1);
                        int b = values.getInt(2);
                        Paint paint = new Paint();
                        paint.setColor(Color.rgb(r, g, b));

                        int left = 0;
                        int top = 0;
                        int right = bitmap.getWidth();
                        int bottom = bitmap.getHeight();

                        // int color = paletteRand.get(i);

                        // Paint paint = new Paint();
                        // paint.setColor(color);

                        Rect rect = new Rect(left, top, right, bottom);
                        canvas.drawRect(rect, paint);
                    }

                    // Log.i("palette", palette.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public ArrayList<Integer> getColorValues(ImageView view) {
        /*
        Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
        int x = bitmap.getWidth()/2;
        int y = bitmap.getHeight()/2;
        int pixel = bitmap.getPixel(x,y);

        int red = Color.red(pixel);
        int green = Color.green(pixel);
        int blue = Color.blue(pixel);

        ArrayList<Integer> rgbValues = new ArrayList<>();
        rgbValues.add(red);
        rgbValues.add(green);
        rgbValues.add(blue);

         */
        ArrayList<Integer> rgbValues = new ArrayList<>();
        return rgbValues;
    }
}
