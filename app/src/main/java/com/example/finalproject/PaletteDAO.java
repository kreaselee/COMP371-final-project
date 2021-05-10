package com.example.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
interface PaletteDAO {
    /**
     * get all PaletteEntities by id
     * @return List<Palette>
     */
    @Query("SELECT * FROM palette_table ORDER BY id DESC")
    LiveData<List<PaletteEntity>> getAllPalettes();

    /**
     * get a PaletteEntity by id
     * @param id
     * @return
     */
    @Query ("SELECT * FROM palette_table WHERE id=:id")
    LiveData<PaletteEntity> selectPalette(int id);

    /**
     * insert a PaletteEntity into table
     * @param paletteEntity
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPalette(PaletteEntity paletteEntity);

    /**
     * get PaletteEntity by id and delete from table
     * @param id
     */
    @Query("DELETE FROM palette_table WHERE id=:id")
    void deletePalette(int id);

    /**
     * get PaletteEntity by id and update
     * @param name
     * @param color1
     * @param color2
     * @param color3
     * @param color4
     * @param color5
     * @param id
     */
    @Query("UPDATE palette_table SET name=:name, color1=:color1, color2=:color2, color3=:color3" +
            ", color4=:color4, color5=:color5 WHERE id=:id")
    void updatePalette(String name, String color1, String color2, String color3, String color4, String color5, int id);
}
