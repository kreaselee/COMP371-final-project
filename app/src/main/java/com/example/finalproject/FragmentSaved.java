package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class FragmentSaved extends Fragment implements PaletteListAdapter.OnButtonListener {

    private PaletteViewModel paletteViewModel;
    private List<PaletteEntity> palettesList;
    private ColorValueConverter colorValueConverter = new ColorValueConverter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_saved);

        final PaletteListAdapter adapter = new PaletteListAdapter(new PaletteListAdapter.WordDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        paletteViewModel = new ViewModelProvider(getActivity()).get(PaletteViewModel.class);

        paletteViewModel.getAllPalettes().observe(getViewLifecycleOwner(), paletteEntities -> {
            palettesList = paletteEntities;
            adapter.submitList(paletteEntities);
        });

        return view;
    }

    @Override
    public void onButtonClick(int position, int viewId) {
        PaletteEntity paletteEntity = palettesList.get(position);
        switch (viewId) {
            case R.id.button_saved_delete:
                paletteViewModel.deletePalette(paletteEntity.id);
                // Log.d("message", "click successful " + position);
                break;
            case R.id.button_saved_view:
                Log.d("message", "click successful " + position);
                break;
            case R.id.button_saved_edit:
                if (paletteEntity != null) {
                    Intent intent = new Intent(getContext(), ScratchActivity.class);
                    intent.putExtra("name", paletteEntity.name);
                    intent.putExtra("color1RGB", colorValueConverter.hexToRGB(paletteEntity.color1));
                    intent.putExtra("color2RGB", colorValueConverter.hexToRGB(paletteEntity.color2));
                    intent.putExtra("color3RGB", colorValueConverter.hexToRGB(paletteEntity.color3));
                    intent.putExtra("color4RGB", colorValueConverter.hexToRGB(paletteEntity.color4));
                    intent.putExtra("color5RGB", colorValueConverter.hexToRGB(paletteEntity.color5));
                    intent.putExtra("id", paletteEntity.id);
                    // Log.d("name", paletteEntity.name);
                    // Log.d("color1", paletteEntity.color1);
                    startActivity(intent);
                }
                Log.d("message", "click successful " + position);
                break;
        }
    }
}
