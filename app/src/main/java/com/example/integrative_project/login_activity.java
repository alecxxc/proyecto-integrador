package com.example.integrative_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login_activity extends AppCompatActivity {
private EditText pt_nombre, et_contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pt_nombre = (EditText) findViewById(R.id.pt_name);
        et_contraseña = (EditText) findViewById(R.id.et_password);
    }

    public void Ingresar (View view){
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
    }
}