package com.beebapcay.galleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimatedBottomBar animatedBottomBar = findViewById(R.id.nav_bottomBar);

        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.picturesFragment, true);
            fragmentManager = getSupportFragmentManager();
            PicturesFragment picturesFragment = new PicturesFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, picturesFragment).commit();
        }


        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.picturesFragment:
                        fragment = new PicturesFragment();
                        break;
                    case R.id.albumsFragment:
                        fragment = new AlbumsFragment();
                        break;
                    case R.id.videosFragment:
                        fragment = new VideosFragment();
                        break;
                    case R.id.privacyFragment:
                        fragment = new PrivacyFragment();
                        break;
                }
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    Log.e(TAG, "Cant create Fragment on Navigation Bottom Bar");
                }
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });


//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
//        NavController navController = (NavController) Navigation.findNavController(this, R.id.fragment);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}