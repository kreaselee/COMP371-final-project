package com.example.finalproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PaletteEntity.class}, version = 1, exportSchema = false)
public abstract class PaletteRoomDatabase extends RoomDatabase {

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
