package com.mallaudin.oxp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mallaudin.annotations.ResourcePackage;
import com.mallaudin.annotations.ViewFactory;
import com.mallaudin.oxygeroid.MainActivityViews;

@ResourcePackage("com.mallaudin.oxp")
@ViewFactory(value = "activity_main")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private MainActivityViews views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        views = MainActivityViews.newInstance(this);

        views.test.setText("some text");
    } // onCreate

    @Override
    public void onClick(View v) {
        
    }
} // MainActivity
