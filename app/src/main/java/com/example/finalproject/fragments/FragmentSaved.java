package com.example.finalproject.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.ColorValueConverter;
import com.example.finalproject.PaletteEntity;
import com.example.finalproject.PaletteViewModel;
import com.example.finalproject.R;
import com.example.finalproject.activities.ScratchActivity;
import com.example.finalproject.activities.ViewActivity;

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
                // Log.d("message", "click successful " + position);
                Intent viewIntent = new Intent(getContext(), ViewActivity.class);
                viewIntent.putExtra("name", paletteEntity.name);
                viewIntent.putExtra("color1RGB", colorValueConverter.hexToRGB(paletteEntity.color1));
                viewIntent.putExtra("color2RGB", colorValueConverter.hexToRGB(paletteEntity.color2));
                viewIntent.putExtra("color3RGB", colorValueConverter.hexToRGB(paletteEntity.color3));
                viewIntent.putExtra("color4RGB", colorValueConverter.hexToRGB(paletteEntity.color4));
                viewIntent.putExtra("color5RGB", colorValueConverter.hexToRGB(paletteEntity.color5));
                viewIntent.putExtra("id", paletteEntity.id);
                // Log.d("name", paletteEntity.name);
                // Log.d("color1", paletteEntity.color1);
                startActivity(viewIntent);
                break;
            case R.id.button_saved_edit:
                if (paletteEntity != null) {
                    Intent scratchIntent = new Intent(getContext(), ScratchActivity.class);
                    scratchIntent.putExtra("name", paletteEntity.name);
                    scratchIntent.putExtra("color1RGB", colorValueConverter.hexToRGB(paletteEntity.color1));
                    scratchIntent.putExtra("color2RGB", colorValueConverter.hexToRGB(paletteEntity.color2));
                    scratchIntent.putExtra("color3RGB", colorValueConverter.hexToRGB(paletteEntity.color3));
                    scratchIntent.putExtra("color4RGB", colorValueConverter.hexToRGB(paletteEntity.color4));
                    scratchIntent.putExtra("color5RGB", colorValueConverter.hexToRGB(paletteEntity.color5));
                    scratchIntent.putExtra("id", paletteEntity.id);
                    // Log.d("name", paletteEntity.name);
                    // Log.d("color1", paletteEntity.color1);
                    startActivity(scratchIntent);
                }
                Log.d("message", "click successful " + position);
                break;
            case R.id.button_saved_share:
                String body = "Palette Name: " + paletteEntity.name
                        + "\nHex Codes: #" + paletteEntity.color1
                        + ", #" + paletteEntity.color2
                        + ", #" + paletteEntity.color3
                        + ", #" + paletteEntity.color4
                        + ", #" + paletteEntity.color5;

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Color Palette");
                intent.putExtra(Intent.EXTRA_TEXT, body);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
        }
    }
}
