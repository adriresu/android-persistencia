package com.example.tarea3_programacionmultimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {

    String Telefono;
    TextView Latitud, Longitud;
    Location Localizacion;
    LocationManager Localizador;
    Button SendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            SetSMS();
        }
        SharedPreferences Prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        Telefono = String.valueOf(Prefs.getInt("Telf", 1));
        Latitud = findViewById(R.id.TextLatitud);
        Latitud = findViewById(R.id.TextLongitud);
        SendSMS = findViewById(R.id.ButtonSend);
        SendSMS.setOnClickListener(view -> {
            SmsManager Sms = SmsManager.getDefault();
            String TextSMS = "Latitud: " + Latitud + " Longitud: " + Longitud;
            Sms.sendTextMessage(String.valueOf(Telefono), null, TextSMS, null, null);
        });
    }

    public void SetSMS() {
        Localizador = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean IsGpsActivated = Localizador.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!IsGpsActivated){
            Intent SettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(SettingIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        Localizacion = Localizador.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Latitud.setText(String.valueOf(Localizacion.getLatitude()));
        Longitud.setText(String.valueOf(Localizacion.getLongitude()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                SetSMS();
            }
        }
    }
}