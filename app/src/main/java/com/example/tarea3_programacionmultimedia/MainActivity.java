package com.example.tarea3_programacionmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button Llamada, Sms, Mail, Ajustes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Llamada = findViewById(R.id.BtnLlamada);
        Sms = findViewById(R.id.BtnSms);
        Mail = findViewById(R.id.BtnMail);
        Ajustes = findViewById(R.id.BtnAjustes);

        Sms.setOnClickListener(view -> {
            Intent intent = new Intent(this, SmsActivity.class);
            startActivity(intent);
        });

        Llamada.setOnClickListener(view -> {
            Intent intent = new Intent(this, SmsActivity.class);
            startActivity(intent);
        });

        Mail.setOnClickListener(view -> {
            Intent intent = new Intent(this, SmsActivity.class);
            startActivity(intent);
        });

        Ajustes.setOnClickListener(view -> {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        });
    }
}