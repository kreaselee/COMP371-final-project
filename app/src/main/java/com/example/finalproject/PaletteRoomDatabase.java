package com.example.finalproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PaletteEntity.class}, version = 1, exportSchema = false)
public abstract class PaletteRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS=4;
    public static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile PaletteRoomDatabase INSTANCE;
    public abstract PaletteDAO paletteDao();

    public static PaletteRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PaletteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PaletteRoomDatabase.class, "palette_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
