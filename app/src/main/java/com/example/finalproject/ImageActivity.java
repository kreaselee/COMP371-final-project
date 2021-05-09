package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import org.json.JSONArray;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {

    private ColorValueConverter colorValueConverter;
    private static final int RESULT_LOAD_IMAGE = 1;

    private ImageView uploadedImage;
    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;

    private Button button_choose;
    private Button button_save;
    private Button button_cancel;

    private Boolean imageUploaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        colorValueConverter = new ColorValueConverter();

        uploadedImage = findViewById(R.id.imageView_uploaded);
        color1 = findViewById(R.id.color1_image);
        color2 = findViewById(R.id.color2_image);
        color3 = findViewById(R.id.color3_image);
        color4 = findViewById(R.id.color4_image);
        color5 = findViewById(R.id.color5_image);

        button_choose = findViewById(R.id.button_choose_image);
        button_save = findViewById(R.id.button_save_image_palette);
        button_cancel = findViewById(R.id.button_cancel_image_palette);

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Uri selectedImage = intent.getData();
                        uploadedImage.setImageURI(selectedImage);
                        imageUploaded = true;

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

                        for (int i = 0; i < colors.size(); i++) {
                            views.get(i).setBackgroundColor(colors.get(i));
                        }
                    }
                });

        button_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startForResult.launch(galleryIntent);
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (imageUploaded == false) {
                    Toast.makeText(ImageActivity.this, "Upload an image", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(ImageActivity.this, NameActivity.class);

                    ArrayList<ImageView> views = new ArrayList<>();
                    views.add(color1);
                    views.add(color2);
                    views.add(color3);
                    views.add(color4);
                    views.add(color5);

                    for (int i = 0; i < views.size(); i++) {
                        ColorDrawable drawable = (ColorDrawable) views.get(i).getBackground();
                        int color = drawable.getColor();
                        int num = i+1;

                        ArrayList<Integer> colorRGB = colorValueConverter.intToRGB(color);
                        intent.putExtra("color" + num + "RGB", colorRGB);
                    }
                    /*
                    ArrayList<Integer> color1RGB = getRGBValues(color1);
                    ArrayList<Integer> color2RGB = getRGBValues(color2);
                    ArrayList<Integer> color3RGB = getRGBValues(color3);
                    ArrayList<Integer> color4RGB = getRGBValues(color4);
                    ArrayList<Integer> color5RGB = getRGBValues(color5);

                    Intent intent = new Intent(ImageActivity.this, NameActivity.class);
                    intent.putExtra("color1RGB", color1RGB);
                    intent.putExtra("color2RGB", color2RGB);
                    intent.putExtra("color3RGB", color3RGB);
                    intent.putExtra("color4RGB", color4RGB);
                    intent.putExtra("color5RGB", color5RGB);

                     */
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /*
    // method to get rgb values
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Integer> getRGBValues(ImageView view) {
        // get color of color imageView
        ColorDrawable drawable = (ColorDrawable) view.getBackground();
        int color = drawable.getColor();

        // get rgb values of the color
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // add to arrayList and return arrayList
        ArrayList<Integer> rgbValues = new ArrayList<>();
        rgbValues.add(red);
        rgbValues.add(green);
        rgbValues.add(blue);

        return rgbValues;
    }

     */
}
