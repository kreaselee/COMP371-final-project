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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    private DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        palettes = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView_explore);

        database = FirebaseDatabase.getInstance().getReference();

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int count = 0;

                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    // Log.d("item id ", item_snapshot.getKey());
                    // Log.d("item id ", item_snapshot.child("color1").getValue().toString());
                    // Log.d("item desc", item_snapshot.child("item_desc").getValue().toString());

                    count++;
                    ArrayList<String> colorsHex = new ArrayList<>();

                    ArrayList<String> colors = new ArrayList<>();
                    colors.add(snapshot.child("color1").getValue().toString());
                    colors.add(snapshot.child("color2").getValue().toString());
                    colors.add(snapshot.child("color3").getValue().toString());
                    colors.add(snapshot.child("color4").getValue().toString());
                    colors.add(snapshot.child("color5").getValue().toString());

                    for (int i = 0; i < colors.size(); i++) {
                        String string = colors.get(i);
                        /*
                        ArrayList<Integer> rgb = new ArrayList<>();
                        int r = Integer.valueOf(string.substring(0,2), 16);
                        int g = Integer.valueOf(string.substring(2,4), 16);
                        int b = Integer.valueOf(string.substring(4,6), 16);
                        rgb.add(r);
                        rgb.add(g);
                        rgb.add(b);

                         */
                        colorsHex.add(string);
                    }

                    Palette palette = new Palette(snapshot.child("name").getValue().toString(), colorsHex);
                    palettes.add(palette);

                    if (count == dataSnapshot.getChildrenCount()) {
                        int width = recyclerView.getWidth();
                        // create and attach adapter
                        PaletteExploreAdapter adapter = new PaletteExploreAdapter(palettes, width);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setHasFixedSize(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(getTag(), "loadPost:onCancelled", databaseError.toException());
            }
        });

        return view;
    }
}
