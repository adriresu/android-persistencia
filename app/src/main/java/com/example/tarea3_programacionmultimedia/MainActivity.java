package com.example.tarea3_programacionmultimedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button Llamada, Sms, Mail, Ajustes, Whatsappp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Llamada = findViewById(R.id.BtnLlamada);
        Sms = findViewById(R.id.BtnSms);
        Mail = findViewById(R.id.BtnMail);
        Ajustes = findViewById(R.id.BtnAjustes);
        Whatsappp = findViewById(R.id.btnWhatsapp);


        Sms.setOnClickListener(view -> {
            Intent intent = new Intent(this, SmsActivity.class);
            startActivity(intent);
        });

        Llamada.setOnClickListener(view -> {
            Intent intent = new Intent(this, llamada.class);
            startActivity(intent);
        });

        Mail.setOnClickListener(view -> {
            Intent intent = new Intent(this, camara.class);
            startActivity(intent);
        });

        Ajustes.setOnClickListener(view -> {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        });

        Whatsappp.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage("com.whatsapp");
            intent.putExtra(Intent.EXTRA_TEXT, "Estoy en peligro! Ayúdame!!");

            if (intent.resolveActivity(getPackageManager()) == null) {
                // Whatsapp no está instalado
                Toast.makeText(this, "Whatsapp no está instalado en el dispositivo", Toast.LENGTH_SHORT).show();
                return;
            }

            startActivity(intent);
        });
    }
}