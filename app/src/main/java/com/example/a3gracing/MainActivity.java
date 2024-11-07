package com.example.a3gracing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.a3gracing.databinding.ActivityRegistrarBinding;
import com.example.a3gracing.ui.gallery.GalleryFragment;
import com.example.a3gracing.ui.registro.registroFragment;
import com.example.a3gracing.ui.slideshow.SlideshowFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a3gracing.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        Button irRegistrar = findViewById(R.id.registrar);
        irRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar a otra actividad
                Intent intent = new Intent(MainActivity.this, registroFragment.class);
                startActivity(intent);

                // O para navegar a un fragmento:
                // SlideshowFragment slideshowFragment = new SlideshowFragment();
                // getSupportFragmentManager().beginTransaction()
                //     .replace(R.id.fragment_container, slideshowFragment)
                //     .addToBackStack(null)
                //     .commit();
            }
        });

        // Configuración de navegación y otros elementos...
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}