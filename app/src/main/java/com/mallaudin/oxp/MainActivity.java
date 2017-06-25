package com.mallaudin.oxp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.github.allaudin.annotations.OxyViews;

import io.github.allaudin.oxygeroid.MainActivityViews;

@OxyViews(value = "activity_main")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityViews.newInstance(this);

    } // onCreate

    @Override
    public void onClick(View v) {
        
    }
} // MainActivity
