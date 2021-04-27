package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class FragmentGenerate extends Fragment {
    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;

    private Button button_random;

    private static final String api_url="http://colormind.io/api/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate, container, false);

        color1 = view.findViewById(R.id.color1_generate);
        color2 = view.findViewById(R.id.color2_generate);
        color3 = view.findViewById(R.id.color3_generate);
        color4 = view.findViewById(R.id.color4_generate);
        color5 = view.findViewById(R.id.color5_generate);
        button_random = view.findViewById(R.id.button_random);

        button_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawSomething(v);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        client.post(getContext(), api_url, entity, "application/json", new AsyncHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                int width = color1.getWidth();
                int height = color1.getHeight();
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
                        Bitmap bitmap;
                        Canvas canvas;

                        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        views.get(i).setImageBitmap(bitmap);
                        canvas = new Canvas(bitmap);

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

    public void drawSomething(View view) {

        /*
        Paint pA = new Paint();
        int vA = R.color.purple_200;
        pA.setColor(0xffa500);

        Paint pB = new Paint();
        int vB = R.color.purple_500;
        pB.setColor(Color.rgb(255, 165, 0));

        Paint pC = new Paint();
        int vC = R.color.purple_700;
        pC.setColor(ResourcesCompat.getColor(getResources(), vC, null));

        Paint pD = new Paint();
        int vD = R.color.teal_200;
        pD.setColor(ResourcesCompat.getColor(getResources(), vD, null));

        Paint pE = new Paint();
        int vE = R.color.teal_700;
        pE.setColor(ResourcesCompat.getColor(getResources(), vE, null));

        ArrayList<Paint> paints = new ArrayList<>();
        paints.add(pA);
        paints.add(pB);
        paints.add(pC);
        paints.add(pD);
        paints.add(pE);

        int width = imageView.getWidth();
        int height = imageView.getHeight();
        int sectionWidth = width / 5;

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        imageView.setImageBitmap(bitmap);
        canvas = new Canvas(bitmap);

        for (int i = 0; i < paints.size(); i++) {

            int left = sectionWidth*i;
            int top = 0;
            int right = sectionWidth*(i+1);
            int bottom = height;

            // int color = paletteRand.get(i);

            // Paint paint = new Paint();
            // paint.setColor(color);

            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawRect(rect, paints.get(i));
        }
        */


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

        client.post(getContext(), api_url, entity, "application/json", new AsyncHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                int width = color1.getWidth();
                int height = color1.getHeight();
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
                        Bitmap bitmap;
                        Canvas canvas;

                        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        views.get(i).setImageBitmap(bitmap);
                        canvas = new Canvas(bitmap);

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
}
