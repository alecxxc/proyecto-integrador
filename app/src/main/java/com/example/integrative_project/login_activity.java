package com.example.integrative_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    public void evaluarUsuario(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        String u = pt_nombre.getText().toString();
        String p = et_contraseña.getText().toString();

        if (u.length() == 0 || p.length() == 0) {
            Toast.makeText(this, "Completa el campo de usuario y contraseña", Toast.LENGTH_SHORT).show();
        } else {
            // Verificar si el usuario y la contraseña existen en la base de datos
            String query = "SELECT * FROM usuarios WHERE user = ? AND password = ?";
            Cursor cursor = bd.rawQuery(query, new String[]{u, p});

            if (cursor.moveToFirst()) {
                // Usuario y contraseña coinciden
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                Intent i = new Intent (this, MainActivity.class);
                startActivity(i);
            } else {
                // Usuario o contraseña incorrectos
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        }

        bd.close();
    }


    public void registro (View v){
        Intent i = new Intent (this, registro_activity.class);
        startActivity(i);
    }
}