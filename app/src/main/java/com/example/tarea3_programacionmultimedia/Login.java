package com.example.tarea3_programacionmultimedia;

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

        Button botonLogin = (Button) findViewById(R.id.btnLogin);
        Button botonRegister = (Button) findViewById(R.id.btnRegister);
        EditText txtUser = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText txtPass = (EditText) findViewById(R.id.editTextTextPersonName2);

        botonLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                SharedPreferences preferencias = getSharedPreferences("Preferencias", Modo.equals("privado") ? Context.MODE_PRIVATE : Context.MODE_WORLD_WRITEABLE);
                String password = preferencias.getString(txtUser.getText().toString(), "");
                String password2 = txtPass.getText().toString();
                if (password.equals(password2) ){
                    Toast.makeText(Login.this, "Correcto, Iniciando Sesion", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        botonRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                ArrayList<String> errores = new ArrayList<String>();
                Boolean flag = true;
                if ( txtUser.getText().toString().length() == 0 || txtPass.getText().toString().length() == 0){
                    flag = false;
                    errores.add("Complete con el nombre de usuario y contraseña");
                }
                if ( txtPass.getText().toString() == null || txtPass.getText().toString() == ""){
                    flag = false;
                }
                if (txtPass.getText().toString().length() < 8){
                    errores.add("La contraseña debe de ser de 3 caracteres o mas");
                    flag = false;
                }
                if (txtUser.getText().toString().length() < 3){
                    errores.add("El nombre de usuario debe de ser de 3 caracteres o mas");
                    flag = false;
                }


                if (flag){
                    Modo = "privado";
                    SharedPreferences preferencias = getSharedPreferences("Preferencias", Modo.equals("privado") ? Context.MODE_PRIVATE : Context.MODE_WORLD_WRITEABLE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(txtUser.getText().toString(), txtPass.getText().toString());
                    editor.apply();
                    Toast.makeText(Login.this, "REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }
                else{
                    for(String e : errores){
                        TextView txtErrores = (TextView) findViewById(R.id.textView3);
                        txtErrores.setText(txtErrores.getText() + "\n" + e);
                    }
                }
            }
        });
    }

}