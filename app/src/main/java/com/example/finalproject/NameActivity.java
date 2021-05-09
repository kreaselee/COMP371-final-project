package com.example.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NameActivity extends AppCompatActivity {
    private ColorValueConverter colorValueConverter;

    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;

    private TextView color1Hex;
    private TextView color2Hex;
    private TextView color3Hex;
    private TextView color4Hex;
    private TextView color5Hex;

    private EditText paletteName;

    private Button button_back;
    private Button button_save;

    private DatabaseReference database;
    private String key = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        colorValueConverter = new ColorValueConverter();

        color1 = findViewById(R.id.color1_name);
        color2 = findViewById(R.id.color2_name);
        color3 = findViewById(R.id.color3_name);
        color4 = findViewById(R.id.color4_name);
        color5 = findViewById(R.id.color5_name);

        color1Hex = findViewById(R.id.textView_color1_name_hex);
        color2Hex = findViewById(R.id.textView_color2_name_hex);
        color3Hex = findViewById(R.id.textView_color3_name_hex);
        color4Hex = findViewById(R.id.textView_color4_name_hex);
        color5Hex = findViewById(R.id.textView_color5_name_hex);

        paletteName = findViewById(R.id.editText_name);

        button_back = findViewById(R.id.button_name_edit);
        button_save = findViewById(R.id.button_name_save);

        database = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        ArrayList<Integer> color1RGB = intent.getIntegerArrayListExtra("color1RGB");
        ArrayList<Integer> color2RGB = intent.getIntegerArrayListExtra("color2RGB");
        ArrayList<Integer> color3RGB = intent.getIntegerArrayListExtra("color3RGB");
        ArrayList<Integer> color4RGB = intent.getIntegerArrayListExtra("color4RGB");
        ArrayList<Integer> color5RGB = intent.getIntegerArrayListExtra("color5RGB");

        setViews(color1, color1Hex, color1RGB);
        setViews(color2, color2Hex, color2RGB);
        setViews(color3, color3Hex, color3RGB);
        setViews(color4, color4Hex, color4RGB);
        setViews(color5, color5Hex, color5RGB);

        button_back.setOnClickListener(v -> {
            Intent intent1 = new Intent(NameActivity.this, ScratchActivity.class);
            intent1.putExtra("color1RGB", color1RGB);
            intent1.putExtra("color2RGB", color2RGB);
            intent1.putExtra("color3RGB", color3RGB);
            intent1.putExtra("color4RGB", color4RGB);
            intent1.putExtra("color5RGB", color5RGB);
            startActivity(intent1);
            finish();
        });

        button_save.setOnClickListener(v -> {
            if (paletteName.getText().toString().replace(" ", "").matches("")) {
                Toast.makeText(NameActivity.this, "Enter palette name", Toast.LENGTH_SHORT).show();
            }
            else {
                Query dbByKey = database.orderByKey();
                dbByKey.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int num = 1;
                        String keyTest = "";

                        for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            // Log.d("item id", snapshot.getKey());
                            keyTest = "palette" + num;
                            if (keyTest.matches(snapshot.getKey())) {
                                num++;
                            }

                        }
                        key = "palette" + num;
                        Log.d("item id", key);

                        ArrayList<String> colors = new ArrayList<>();
                        ArrayList<ArrayList<Integer>> rgbValues = new ArrayList<>();
                        rgbValues.add(color1RGB);
                        rgbValues.add(color2RGB);
                        rgbValues.add(color3RGB);
                        rgbValues.add(color4RGB);
                        rgbValues.add(color5RGB);

                        for (int i = 0; i < rgbValues.size(); i++) {
                            int r = rgbValues.get(i).get(0);
                            int g = rgbValues.get(i).get(1);
                            int b = rgbValues.get(i).get(2);
                            String colorHex = colorValueConverter.RGBToHex(r,g,b);
                            colors.add(colorHex);
                        }

                        writeNewPalette(paletteName.getText().toString(), colors);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("loadPost:onCancelled", String.valueOf(databaseError.toException()));
                    }
                });
            }
        });
    }

    private void setViews(ImageView imageView, TextView textView, ArrayList<Integer> rgb) {
        imageView.setBackgroundColor(Color.rgb(rgb.get(0), rgb.get(1), rgb.get(2)));

        String hex = colorValueConverter.RGBToHex(rgb.get(0), rgb.get(1), rgb.get(2));
        textView.setText(hex);
    }

    private void writeNewPalette(String name, ArrayList<String> colors) {
        // Palette user = new Palette(name, colors);
        database.child(key).child("name").setValue(name);
        database.child(key).child("color1").setValue(colors.get(0));
        database.child(key).child("color2").setValue(colors.get(1));
        database.child(key).child("color3").setValue(colors.get(2));
        database.child(key).child("color4").setValue(colors.get(3));
        database.child(key).child("color5").setValue(colors.get(4));
    }

}
