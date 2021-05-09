package com.example.finalproject;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class PaletteRepository {

    private PaletteDAO paletteDao;
    private LiveData<List<PaletteEntity>> allPalettes;
    private PaletteRoomDatabase db;

    public PaletteRepository(Application application) {
        db = PaletteRoomDatabase.getInstance(application);
        paletteDao = db.paletteDao();
        allPalettes = paletteDao.getAllPalettes();
    }

    public LiveData<List<PaletteEntity>> getAllPalettes() {
        return allPalettes;
    }

    public LiveData<PaletteEntity> selectPalette(int id) {
        return paletteDao.selectPalette(id);
    }

    public void insertPalette(PaletteEntity paletteEntity) {
        db.databaseWriteExecutor.execute(()-> paletteDao.insertPalette(paletteEntity));
    }

    public void updatePalette(String name, String color1, String color2, String color3, String color4, String color5, int id) {
        db.databaseWriteExecutor.execute(()-> paletteDao.updatePalette(name, color1, color2, color3, color4, color5, id));
    }

    public void deletePalette(int id){
        db.databaseWriteExecutor.execute(()-> paletteDao.deletePalette(id));
    }

}