package com.beebapcay.galleryapp.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.beebapcay.galleryapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomNavigationView mBottomNavigationView;
    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Set Navigate of NavigationBottomView
        mBottomNavigationView = findViewById(R.id.view_bottom_nav);
        mNavController = Navigation.findNavController(this, R.id.view_dest_container);
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);


    }
}