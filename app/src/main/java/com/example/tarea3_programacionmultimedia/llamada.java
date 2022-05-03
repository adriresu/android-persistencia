package com.example.tarea3_programacionmultimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class llamada extends AppCompatActivity {
    private static final int REQUEST_PHONE_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamada);

        ImageView llamada = (ImageView) findViewById(R.id.imageView);
        llamada.setOnClickListener(new View.OnClickListener() {
            EditText numTelf = (EditText) findViewById(R.id.editTextPhone);

            @Override
            public void onClick(View v) {
                //Gestion de llamada
                if (TextUtils.isEmpty(numTelf.getText().toString())){
                    Toast.makeText(llamada.this, "Por favor, escriba un numero", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if (ContextCompat.checkSelfPermission(llamada.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(llamada.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                            }
                            else{
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + Uri.encode(numTelf.getText().toString())));
                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(callIntent);
                            }
                        }


                    }
                    catch(Exception ex){
                        Toast.makeText(llamada.this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}