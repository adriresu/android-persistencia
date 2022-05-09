package com.example.tarea3_programacionmultimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        List<String> missingPermissions = new ArrayList<String>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(Manifest.permission.SEND_SMS);
        }

        String[] missingPermissionsArray = new String[missingPermissions.size()];
        missingPermissionsArray = missingPermissions.toArray(missingPermissionsArray);

        if (missingPermissionsArray.length > 0) {
            ActivityCompat.requestPermissions(this, missingPermissionsArray, 102);
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No tienes los permisos necesarios", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences Prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        Telefono = String.valueOf(Prefs.getString("Sms", ""));
        Latitud = findViewById(R.id.TextLatitud);
        Longitud = findViewById(R.id.TextLongitud);
        SendSMS = findViewById(R.id.ButtonSend);

        try {
            Localizador = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Localizador.requestLocationUpdates(LocationManager.FUSED_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    Localizacion = location;
                    Latitud.setText(String.valueOf(location.getLatitude()));
                    Longitud.setText(String.valueOf(location.getLongitude()));
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "No se ha podido obtener la localización", Toast.LENGTH_SHORT).show();
        }

        SendSMS.setOnClickListener(view -> {
            Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            SmsManager smsManager = SmsManager.getDefault();

            Localizacion = Localizador.getLastKnownLocation(LocationManager.FUSED_PROVIDER);
            try {
                smsManager.sendTextMessage(Telefono, null, "Ubicación del usuario: Longitud: " + String.valueOf(Localizacion.getLongitude()) + ", Latitud: " + String.valueOf(Localizacion.getLatitude()) + ", Altitud: " + String.valueOf(Localizacion.getAltitude()), pi, null);
                Toast.makeText(this, "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show();
            }
        });
    }
}