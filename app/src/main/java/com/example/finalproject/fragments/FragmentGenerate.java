package com.example.finalproject.fragments;

import android.animation.ArgbEvaluator;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
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

import com.example.finalproject.ColorValueConverter;
import com.example.finalproject.activities.ImageActivity;
import com.example.finalproject.activities.NameActivity;
import com.example.finalproject.R;
import com.example.finalproject.activities.ScratchActivity;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class FragmentGenerate extends Fragment {
    private ColorValueConverter colorValueConverter;

    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;

    private Button button_random;
    private Button button_image;
    private Button button_scratch;
    private Button button_save;

    private static final String api_url="http://colormind.io/api/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate, container, false);
        colorValueConverter = new ColorValueConverter();

        color1 = view.findViewById(R.id.color1_generate);
        color2 = view.findViewById(R.id.color2_generate);
        color3 = view.findViewById(R.id.color3_generate);
        color4 = view.findViewById(R.id.color4_generate);
        color5 = view.findViewById(R.id.color5_generate);
        button_random = view.findViewById(R.id.button_random);
        button_scratch = view.findViewById(R.id.button_scratch);
        button_image = view.findViewById(R.id.button_image);
        button_save = view.findViewById(R.id.button_generate_save);

        button_random.setOnClickListener(v -> generatePalette());

        button_scratch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ScratchActivity.class);
            startActivity(intent);
        });

        button_image.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ImageActivity.class);
            startActivity(intent);
        });

        button_save.setOnClickListener(v -> {
            ArrayList<ImageView> views = new ArrayList<>();
            views.add(color1);
            views.add(color2);
            views.add(color3);
            views.add(color4);
            views.add(color5);

            Intent intent = new Intent(getActivity(), NameActivity.class);
            for (int i = 0; i < views.size(); i++) {
                ColorDrawable drawable = (ColorDrawable) views.get(i).getBackground();
                int color = drawable.getColor();
                Log.d("color", Integer.toString(color));
                int num = i+1;

                ArrayList<Integer> colorRGB = colorValueConverter.intToRGB(color);
                intent.putExtra("color" + num + "RGB", colorRGB);
            }
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generatePalette();
    }

    public void generatePalette() {
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

                ArrayList<ImageView> views = new ArrayList<>();
                views.add(color1);
                views.add(color2);
                views.add(color3);
                views.add(color4);
                views.add(color5);

                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    JSONArray result = json.getJSONArray("result");

                    for (int i = 0; i < result.length(); i++) {
                        JSONArray values = result.getJSONArray(i);
                        int r = values.getInt(0);
                        int g = values.getInt(1);
                        int b = values.getInt(2);
                        Paint paint = new Paint();
                        paint.setColor(Color.rgb(r, g, b));

                        views.get(i).setBackgroundColor(Color.rgb(r, g, b));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // create random palette to set as the default
                Random rnd = new Random();
                // get two random colors and set as first and last colors
                int c1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                int c5 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
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
        });

    }

}
