package com.example.finalproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class PaletteViewModel extends AndroidViewModel {

    private PaletteRepository paletteRepository;
    private LiveData<List<PaletteEntity>> allPalettes;

    public PaletteViewModel(@NonNull Application application) {
        super(application);
        paletteRepository = new PaletteRepository(application);
        allPalettes = paletteRepository.getAllPalettes();
    }

    public LiveData<List<PaletteEntity>> getAllPalettes() {
        return allPalettes;
    }

    public LiveData<PaletteEntity> selectPalette(int id) {
        return paletteRepository.selectPalette(id);
    }

    public void insertPalette(PaletteEntity paletteEntity) {
        paletteRepository.insertPalette(paletteEntity);
    }

    public void deletePalette(int id) {
        paletteRepository.deletePalette(id);
    }

    public void updatePalette(String name, String color1, String color2, String color3, String color4, String color5, int id) {
        paletteRepository.updatePalette(name, color1, color2, color3, color4, color5, id);
    }
}

/*
class PaletteViewModelFactory implements ViewModelProvider.Factory {
    private PaletteRepository repository;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PaletteViewModel.class)) {
            return modelClass.cast(new PaletteViewModel(repository));
        }
        throw new IllegalArgumentException("Unknown view model class");
    }
}
 */