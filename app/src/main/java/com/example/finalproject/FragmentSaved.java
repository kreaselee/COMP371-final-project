package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FragmentSaved extends Fragment {

    private PaletteViewModel paletteViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_saved);

        final PaletteListAdapter adapter = new PaletteListAdapter(new PaletteListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        paletteViewModel = new ViewModelProvider(getActivity()).get(PaletteViewModel.class);

        paletteViewModel.getAllPalettes().observe(getViewLifecycleOwner(), paletteEntities -> {
            adapter.submitList(paletteEntities);
        });

        return view;
    }
}
