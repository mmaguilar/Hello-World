package com.example.helloworld;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity { //every activity extends from this


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //everything in layout will be set to this second_activity
        setContentView(R.layout.activity_second);
        //manifest file, keeps track of components of app
        //have to update manifest file

    }
}
