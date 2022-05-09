package com.example.tarea3_programacionmultimedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;

public class VibracionActivity extends AppCompatActivity {

    Vibrator vibratorService;

    Button btnVibracion1;
    Button btnVibracion2;
    Button btnVibracion3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibracion);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        vibratorService = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btnVibracion1 = (Button) findViewById(R.id.btnVibracion1);
        btnVibracion2 = (Button) findViewById(R.id.btnVibracion2);
        btnVibracion3 = (Button) findViewById(R.id.btnVibracion3);


        btnVibracion1.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibratorService.vibrate(new long[] {300, 400}, 1);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                vibratorService.cancel();
            }
            else {
                vibratorService.vibrate(10000);
            }
        });

        btnVibracion2.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibratorService.vibrate(new long[] {200, 300}, 1);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                vibratorService.cancel();
            }
            else {
                vibratorService.vibrate(9000);
            }
        });

        btnVibracion3.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibratorService.vibrate(new long[] {100, 200}, 1);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                vibratorService.cancel();
            }
            else {
                vibratorService.vibrate(8000);
            }
        });
    }
}