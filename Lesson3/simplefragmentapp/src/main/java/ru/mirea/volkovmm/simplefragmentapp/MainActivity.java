package ru.mirea.volkovmm.simplefragmentapp;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private Fragment firstFragment, secondFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        fragmentManager = getSupportFragmentManager();


        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, firstFragment)
                    .commit();
        }
    }

    public void onClickFirst(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, firstFragment)
                .commit();
    }

    public void onClickSecond(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, secondFragment)
                .commit();
    }
}