package com.example.finalproject.activities;

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

        loadFragment(new FragmentViewInfo(), R.id.fragmentContainerView_view);
        currentFragment = 0;

        button_exit.setOnClickListener(v -> finish());

        button_change.setOnClickListener(v -> {
            if (currentFragment == 0) {
                loadFragment(new FragmentViewText(), R.id.fragmentContainerView_view);
                currentFragment = 1;
            }
            else {
                loadFragment(new FragmentViewInfo(), R.id.fragmentContainerView_view);
                currentFragment = 0;
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

