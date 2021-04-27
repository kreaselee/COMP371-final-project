package com.example.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import org.json.JSONArray;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    private ImageView uploadedImage;
    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;

    private Button button_choose;
    private Button button_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        uploadedImage = findViewById(R.id.imageView_uploaded);
        color1 = findViewById(R.id.color1_image);
        color2 = findViewById(R.id.color2_image);
        color3 = findViewById(R.id.color3_image);
        color4 = findViewById(R.id.color4_image);
        color5 = findViewById(R.id.color5_image);

        button_choose = findViewById(R.id.button_choose_image);
        button_save = findViewById(R.id.button_save_image_palette);

        button_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uploadedImage.setImageURI(selectedImage);

            uploadedImage.invalidate();
            BitmapDrawable drawable = (BitmapDrawable) uploadedImage.getDrawable();
            Bitmap bitmap_image = drawable.getBitmap();

            Palette.Builder builder = new Palette.Builder(bitmap_image);
            Palette palette = builder.generate();

            ArrayList<ImageView> views = new ArrayList<>();
            views.add(color1);
            views.add(color2);
            views.add(color3);
            views.add(color4);
            views.add(color5);

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(palette.getDominantColor(4737096));
            colors.add(palette.getDarkVibrantColor(15263976));
            colors.add(palette.getDarkMutedColor(11053224));
            colors.add(palette.getMutedColor(14474460));
            colors.add(palette.getLightMutedColor(16316664));

            int width = color1.getWidth();
            int height = color1.getHeight();
            Log.d("info", Integer.toString(height));
            int sectionWidth = width / 5;

            for (int i = 0; i < colors.size(); i++) {
                Bitmap bitmap;
                Canvas canvas;

                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                views.get(i).setImageBitmap(bitmap);
                canvas = new Canvas(bitmap);

                Paint paint = new Paint();
                paint.setColor(colors.get(i));

                int left = 0;
                int top = 0;
                int right = bitmap.getWidth();
                int bottom = bitmap.getHeight();

                // int color = paletteRand.get(i);

                // Paint paint = new Paint();
                // paint.setColor(color);

                Rect rect = new Rect(left, top, right, bottom);
                canvas.drawRect(rect, paint);
            }
        }
    }
}
