package ru.mirea.volkovmm.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        TextView textViewResult = findViewById(R.id.textViewResult);

        Intent intent = getIntent();


        String receivedTime = intent.getStringExtra("time_key");


        int listNumber = 11;
        int square = listNumber * listNumber;


        String textToDisplay = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ ЧИСЛО "
                + square + ", а текущее время " + receivedTime;

        textViewResult.setText(textToDisplay);
    }
}