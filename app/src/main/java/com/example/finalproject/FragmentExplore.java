package com.example.finalproject;

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

import java.util.ArrayList;

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
                        colorsHex.add(string);
                    }

                    Palette palette = new Palette(snapshot.child("name").getValue().toString(), colorsHex);
                    palettes.add(palette);

                    if (count == dataSnapshot.getChildrenCount()) {
                        // create and attach adapter
                        PaletteExploreAdapter adapter = new PaletteExploreAdapter(palettes);
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
