package com.example.finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FragmentExplore extends Fragment {

    private ArrayList<Palette> palettes;
    private RecyclerView recyclerView;

    private static final String api_url="https://www.colourlovers.com/api/palettes?";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        palettes = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView_explore);

        String numResults = "numResults=20";
        String format = "format=json";
        String new_url = api_url + numResults + "&" + format;

        // send a get request to the api url
        client.get(new_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                try {
                    JSONArray json = new JSONArray(new String(responseBody));
                    // Log.d("info", json.getJSONObject(0).toString());
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject paletteJson = json.getJSONObject(i);
                        String name = "Palette #" + (i+1);
                        JSONArray colors = paletteJson.getJSONArray("colors");
                        ArrayList<ArrayList<Integer>> colorsRGB = new ArrayList<>();

                        Log.d("info", colors.toString());


                        for (int j = 0; j < colors.length(); j++) {
                            String string = colors.getString(j);
                            ArrayList<Integer> rgb = new ArrayList<>();
                            int r = Integer.valueOf(string.substring(0,2), 16);
                            int g = Integer.valueOf(string.substring(2,4), 16);
                            int b = Integer.valueOf(string.substring(4,6), 16);
                            rgb.add(r);
                            rgb.add(g);
                            rgb.add(b);
                            colorsRGB.add(rgb);
                        }

                        Palette palette = new Palette(name, colorsRGB);
                        palettes.add(palette);

                        int width = recyclerView.getWidth();
                        // create and attach adapter
                        PaletteExploreAdapter adapter = new PaletteExploreAdapter(palettes, width);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setHasFixedSize(true);

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });

        return view;
    }
}
