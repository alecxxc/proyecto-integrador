package com.example.integrative_project;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing widgets
        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Setting default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new InicioFragmento()).commit();
        }

        // Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Getting MenuItem id in a variable
                int id = menuItem.getItemId();

                // If else if statement for fragment
                if (id == R.id.bottom_nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new InicioFragmento()).commit();
                } else if (id == R.id.bottom_nav_account) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new EventosFragmento()).commit();
                } else if (id == R.id.bottom_nav_setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new PopularFragmento()).commit();
                }
                return true;
            }
        });
    }
}