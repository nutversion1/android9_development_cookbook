package com.example.fragmentbackstack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    Button mButtonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        mButtonNext = findViewById(R.id.buttonNext);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new FragmentTwo());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                mButtonNext.setVisibility(View.INVISIBLE);

                /*
                Toast.makeText(getApplicationContext(),
                        ""+getSupportFragmentManager().getBackStackEntryCount(),
                        Toast.LENGTH_SHORT).show();*/

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });



        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, new FragmentOne());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        /*
        Toast.makeText(getApplicationContext(),
                ""+getSupportFragmentManager().getBackStackEntryCount(),
                Toast.LENGTH_SHORT).show();*/

        if(getSupportFragmentManager().getBackStackEntryCount() >= 2){
            super.onBackPressed();
            mButtonNext.setVisibility(View.VISIBLE);
        }else{
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }else{
            return super.onOptionsItemSelected(menuItem);
        }

    }

    @Override
    public void onBackStackChanged() {
        /*
        Toast.makeText(getApplicationContext(),
                "onBackStackChanged",
                Toast.LENGTH_SHORT).show();*/

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if(fragment instanceof FragmentOne){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }else if(fragment instanceof FragmentTwo){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}