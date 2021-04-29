package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ScratchActivity extends AppCompatActivity {

    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;

    private Button button_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        color1 = findViewById(R.id.color1_scratch);
        color2 = findViewById(R.id.color2_scratch);
        color3 = findViewById(R.id.color3_scratch);
        color4 = findViewById(R.id.color4_scratch);
        color5 = findViewById(R.id.color5_scratch);

        button_cancel = findViewById(R.id.button_cancel_scratch);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
