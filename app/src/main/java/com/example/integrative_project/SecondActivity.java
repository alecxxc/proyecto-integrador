package com.example.integrative_project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.integrative_project.databinding.ActivitySecondBinding;
import com.example.integrative_project.R;


//prueba commit
public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Remplazodefragmento(new Inicio());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.inicio:
                    Remplazodefragmento(new Inicio());
                    break;
                case R.id.eventos:
                    Remplazodefragmento(new Eventos());
                    break;
                case R.id.cursos:
                    Remplazodefragmento(new Cursos());
                    break;

            }
            return true;
        });
    }

    private void Remplazodefragmento(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.pantalla, fragment);
        fragmentTransaction.commit();
    }
}
