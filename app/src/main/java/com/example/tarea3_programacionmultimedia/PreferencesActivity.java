package com.example.tarea3_programacionmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {

    TextView Pass, NumTelf, NumMens, Mail;
    Button Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Pass = findViewById(R.id.PrefPass);
        NumTelf = findViewById(R.id.PrefPhone);
        NumMens = findViewById(R.id.PrefMensajes);
        Mail = findViewById(R.id.PrefMail);

        Save = findViewById(R.id.SavePrefs);
        Save.setOnClickListener(view -> {
            try{
                SharedPreferences Prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor EditarPrefs = Prefs.edit();
                EditarPrefs.putInt("Pass", Integer.parseInt(Pass.getText().toString()));
                EditarPrefs.putInt("Telf", Integer.parseInt(Pass.getText().toString()));
                EditarPrefs.putInt("Sms", Integer.parseInt(Pass.getText().toString()));
                EditarPrefs.putString("Pass", Mail.getText().toString());
                EditarPrefs.apply();
                Toast.makeText(this, "Preferencias guardadas correctamente.", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex){
                Toast.makeText(this, "No se han guardado las preferencias:\n" + ex, Toast.LENGTH_SHORT).show();
            }
        });
    }
}