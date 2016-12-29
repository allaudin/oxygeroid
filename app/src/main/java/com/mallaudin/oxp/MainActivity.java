package com.mallaudin.oxp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mallaudin.annotations.ResourcePackage;
import com.mallaudin.annotations.ViewFactory;

@ResourcePackage("com.mallaudin.oxp")
@ViewFactory("activity_main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    } // onCreate

} // MainActivity
