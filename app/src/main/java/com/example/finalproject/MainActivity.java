package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        loadFragment(new FragmentExplore(), R.id.fragmentContainerView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.page_1) {
                loadFragment(new FragmentExplore(), R.id.fragmentContainerView);
                return true;
            }
            else if (item.getItemId() == R.id.page_2) {
                loadFragment(new FragmentGenerate(), R.id.fragmentContainerView);
                return true;
            }
            else if (item.getItemId() == R.id.page_3) {
                loadFragment(new FragmentSaved(), R.id.fragmentContainerView);
                return true;
            }
            return false;
        });
    }

    public void loadFragment(Fragment fragment, int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }


}