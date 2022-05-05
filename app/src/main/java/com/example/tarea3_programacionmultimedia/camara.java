package com.example.tarea3_programacionmultimedia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class camara extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    Bitmap imagenMalandrin;
    ImageView imageView2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        imageView2 = (ImageView) findViewById(R.id.imageView2);

        //Sacar Foto
        Button botonFoto = (Button) findViewById(R.id.button);
        botonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }
                else{
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE);
                }
            }
        });

        //Enviar a la pulisia
        Button botonEnviar = (Button) findViewById(R.id.button2);
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }

                //Creamos el correo y lo asignamos
                EditText correo = (EditText) findViewById(R.id.editTextTextEmailAddress);
                String[] TO = {correo.getText().toString()};

                //Pasamos valores al intent
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{correo.getText().toString()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "AUXILIO");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Esta persona me esta atacando, por favor ayuda");
                File savedImage = saveToExternalStorage(imagenMalandrin);
                //File f = new File(String.valueOf(loadImageFromStorage(saveToInternalStorage(imagenMalandrin))));
                Uri imageUri = FileProvider.getUriForFile(camara.this, "com.example.tarea3_programacionmultimedia.provider", savedImage);
                emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                emailIntent.setType("image/png");

                try {
                    startActivity(Intent.createChooser(emailIntent,"Share you on the jobing"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(camara.this,"There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            imagenMalandrin = (Bitmap) data.getExtras().get("data");
            imageView2.setImageBitmap(imagenMalandrin);

        }
    }

    private File saveToExternalStorage(Bitmap image) {
        File extStorFile = getExternalFilesDir(null);
        File malandrinFile = new File(extStorFile, "malandrin.png");
        try (FileOutputStream out = new FileOutputStream(malandrinFile)){
            imagenMalandrin.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (Exception e) {
            Toast.makeText(this, "mal", Toast.LENGTH_SHORT).show();
        }
        return malandrinFile;
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory,"malandrin.png");

        try (FileOutputStream out = new FileOutputStream(mypath)) {
        imagenMalandrin.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            Toast.makeText(this, "mal", Toast.LENGTH_SHORT).show();
        }

/*        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return directory.getAbsolutePath();
    }

    private File loadImageFromStorage(String path)
    {
        File f = new File(path, "malandrin.png");

        return f;

    }

}