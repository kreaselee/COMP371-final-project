package com.example.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="palette_table")
public class PaletteEntity {
    @PrimaryKey(autoGenerate=true)
    public int id;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="color1")
    public String color1;

    @ColumnInfo(name="color2")
    public String color2;

    @ColumnInfo(name="color3")
    public String color3;

    @ColumnInfo(name="color4")
    public String color4;

    @ColumnInfo(name="color5")
    public String color5;

    public PaletteEntity(String name, String color1, String color2, String color3, String color4, String color5, int id) {
        this.name = name;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.color5 = color5;
        if (id != -1) {
            this.id = id;
        }
    }
}