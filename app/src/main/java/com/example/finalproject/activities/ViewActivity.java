package com.example.finalproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalproject.R;
import com.example.finalproject.fragments.FragmentExplore;
import com.example.finalproject.fragments.FragmentViewInfo;
import com.example.finalproject.fragments.FragmentViewText;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private Button button_exit;
    private Button button_change;
    private int currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        button_exit = findViewById(R.id.button_view_exit);
        button_change = findViewById(R.id.button_view_change);

        Intent intentSent = getIntent();
        String name = intentSent.getStringExtra("name");
        ArrayList<Integer> color1RGB = intentSent.getIntegerArrayListExtra("color1RGB");
        ArrayList<Integer> color2RGB = intentSent.getIntegerArrayListExtra("color2RGB");
        ArrayList<Integer> color3RGB = intentSent.getIntegerArrayListExtra("color3RGB");
        ArrayList<Integer> color4RGB = intentSent.getIntegerArrayListExtra("color4RGB");
        ArrayList<Integer> color5RGB = intentSent.getIntegerArrayListExtra("color5RGB");

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putIntegerArrayList("color1RGB", color1RGB);
        bundle.putIntegerArrayList("color2RGB", color2RGB);
        bundle.putIntegerArrayList("color3RGB", color3RGB);
        bundle.putIntegerArrayList("color4RGB", color4RGB);
        bundle.putIntegerArrayList("color5RGB", color5RGB);

        FragmentViewInfo fragmentViewInfo = new FragmentViewInfo();
        fragmentViewInfo.setArguments(bundle);
        loadFragment(fragmentViewInfo, R.id.fragmentContainerView_view);
        currentFragment = 0;

        FragmentViewText fragmentViewText = new FragmentViewText();
        fragmentViewText.setArguments(bundle);

        button_exit.setOnClickListener(v -> finish());

        button_change.setOnClickListener(v -> {
            if (currentFragment == 0) {
                loadFragment(fragmentViewText, R.id.fragmentContainerView_view);
                currentFragment = 1;
                button_change.setText(R.string.button_change_info);
            }
            else {
                loadFragment(fragmentViewInfo, R.id.fragmentContainerView_view);
                currentFragment = 0;
                button_change.setText(R.string.button_change_text);
            }
        });
    }

    public void loadFragment(Fragment fragment, int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }

}

