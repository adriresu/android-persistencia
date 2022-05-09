package com.example.tarea3_programacionmultimedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {

    TextView Pass, NumTelf, NumMens, Mail, User;
    Button Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Pass = findViewById(R.id.PrefPass);
        NumTelf = findViewById(R.id.PrefPhone);
        NumMens = findViewById(R.id.PrefMensajes);
        Mail = findViewById(R.id.PrefMail);
        User = findViewById(R.id.PrefUser);

        SharedPreferences Prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        Pass.setText(Prefs.getString("Pass", ""));
        NumTelf.setText(Prefs.getString("Telf", ""));
        NumMens.setText(Prefs.getString("Sms", ""));
        Mail.setText(Prefs.getString("Mail", ""));
        User.setText(Prefs.getString("User", ""));

        Save = findViewById(R.id.SavePrefs);
        Save.setOnClickListener(view -> {
            try{
                SharedPreferences.Editor EditarPrefs = Prefs.edit();
                EditarPrefs.putString("Pass", Pass.getText().toString());
                EditarPrefs.putString("Telf", NumTelf.getText().toString());
                EditarPrefs.putString("Sms", NumMens.getText().toString());
                EditarPrefs.putString("Mail", Mail.getText().toString());
                EditarPrefs.putString("User", Mail.getText().toString());
                EditarPrefs.apply();

                Toast.makeText(this, "Preferencias guardadas correctamente.", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex){
                Toast.makeText(this, "No se han guardado las preferencias:\n" + ex, Toast.LENGTH_SHORT).show();
            }
        });
    }
}