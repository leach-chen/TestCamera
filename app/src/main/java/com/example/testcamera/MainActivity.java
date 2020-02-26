package com.example.testcamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goCamera1(View v)
    {
        Intent intent = new Intent(this,Camera1Activity.class);
        startActivity(intent);
    }


    public void goCamera2(View v)
    {
        Intent intent = new Intent(this,Camera2Activity.class);
        startActivity(intent);
    }

    public void goCamera3(View v)
    {
        Intent intent = new Intent(this,Camera3Activity.class);
        startActivity(intent);
    }

}
