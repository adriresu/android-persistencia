package com.example.tarea3_programacionmultimedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    Button botonLogin;
    Button botonRegister;
    EditText usuario;
    EditText contraseña;
    private static String Modo = "privado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences Prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button botonLogin = (Button) findViewById(R.id.btnLogin);
        Button botonRegister = (Button) findViewById(R.id.btnRegister);
        EditText txtUser = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText txtPass = (EditText) findViewById(R.id.editTextTextPersonName2);


        botonLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                SharedPreferences Prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor EditarPrefs = Prefs.edit();

                //Prefs.getString("Pass", "");
                String passwordCheck = Prefs.getString("Pass", "");
                String userCheck = Prefs.getString("User", "");
                String passwordInput = txtPass.getText().toString();
                String userInput = txtUser.getText().toString();


                if (userCheck.equals(userInput)) {

                    if (passwordCheck.equals(passwordInput)) {
                        Toast.makeText(Login.this, "Correcto, Iniciando Sesion", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        botonRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Boolean flag = true;
                if ( txtUser.getText().toString().length() == 0 || txtPass.getText().toString().length() == 0){
                    flag = false;
                    Toast.makeText(Login.this, "Complete con el nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
                }
                if ( txtPass.getText().toString() == null || txtPass.getText().toString() == ""){
                    flag = false;
                }
                if (txtUser.getText().toString().length() < 3){
                    Toast.makeText(Login.this, "El nombre de usuario debe de ser de 3 caracteres o mas", Toast.LENGTH_SHORT).show();
                    flag = false;
                }


                if (flag){
                    Modo = "privado";
                    SharedPreferences Prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = Prefs.edit();
                    editor.putString("User", txtUser.getText().toString());
                    editor.putString("Pass", txtPass.getText().toString());
                    editor.apply();
                    Toast.makeText(Login.this, "REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });
    }

}