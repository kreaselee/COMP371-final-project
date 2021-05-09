package com.example.finalproject;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class PaletteRepository {

    private PaletteDAO paletteDao;
    private LiveData<List<PaletteEntity>> allPalettes;

    public PaletteRepository(Application application) {
        PaletteRoomDatabase db = PaletteRoomDatabase.getInstance(application);
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
        new PaletteInsertion(paletteDao).execute(paletteEntity);
    }

    private static class PaletteInsertion extends AsyncTask<PaletteEntity, Void, Void> {
        private PaletteDAO paletteDAO;

        private PaletteInsertion(PaletteDAO paletteDAO) {
            this.paletteDAO = paletteDAO;
        }

        @Override
        protected Void doInBackground(PaletteEntity... paletteEntities) {
            paletteDAO.insertPalette(paletteEntities[0]);
            return null;
        }
    }

    public void updatePalette(PaletteEntity paletteEntity) {
        new PaletteUpdate(paletteDao).execute(paletteEntity);
    }

    private static class PaletteUpdate extends AsyncTask<PaletteEntity, Void, Void> {
        private PaletteDAO paletteDAO;

        private PaletteUpdate(PaletteDAO paletteDAO) {
            this.paletteDAO = paletteDAO;
        }

        @Override
        protected Void doInBackground(PaletteEntity... paletteEntities) {
            paletteDAO.updatePalette(
                    paletteEntities[0].name,
                    paletteEntities[0].color1,
                    paletteEntities[0].color2,
                    paletteEntities[0].color3,
                    paletteEntities[0].color4,
                    paletteEntities[0].color5,
                    paletteEntities[0].id);
            return null;
        }
    }

    public void deletePalette(PaletteEntity paletteEntity) {
        new PaletteDelete(paletteDao).execute(paletteEntity);
    }

    private static class PaletteDelete extends AsyncTask<PaletteEntity, Void, Void> {
        private PaletteDAO paletteDAO;

        private PaletteDelete(PaletteDAO paletteDAO) {
            this.paletteDAO = paletteDAO;
        }

        @Override
        protected Void doInBackground(PaletteEntity... paletteEntities) {
            paletteDAO.deletePalette(paletteEntities[0].id);
            return null;
        }
    }
}