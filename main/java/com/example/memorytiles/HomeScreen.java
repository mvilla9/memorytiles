package com.example.memorytiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

    }

    public void activateGameScreen(View v) {
        Intent intent = new Intent(HomeScreen.this, MainActivity.class);
        startActivity(intent);
    }
}
