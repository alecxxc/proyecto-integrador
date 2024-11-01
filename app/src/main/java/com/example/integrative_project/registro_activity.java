package com.example.integrative_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registro_activity extends AppCompatActivity {
private EditText et_nombre, et_contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_contraseña = (EditText) findViewById(R.id.et_contraseña);
    }

    public void registro (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String u = et_nombre.getText().toString();
        String p = et_contraseña.getText().toString();

        if (u.length() == 0 || p.length() == 0){
            Toast.makeText(this, "Completa el campo de usuario y contraseña", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues registro = new ContentValues();
            registro.put("user", u);
            registro.put("password", p);
            bd.insert("usuarios", null, registro);
            bd.close();
            Toast.makeText(this, "Se cargaron los datos de registro", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}