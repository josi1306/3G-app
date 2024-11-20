package com.example.a3gracing.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a3gracing.R;
import com.example.a3gracing.databinding.FragmentSlideshowBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Referencias a los elementos del layout
        EditText emailEditText = root.findViewById(R.id.editTextTextEmailAddress);
        EditText passwordEditText = root.findViewById(R.id.editTextTextPassword);
        Button loginButton = root.findViewById(R.id.ingresar);

        // Configurar el botón de login
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                iniciarSesion(email, password);
            }
        });

        return root;
    }

    private void iniciarSesion(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        // Redirigir al usuario a otra actividad o fragment si es necesario
                    } else {
                        Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseAuth", "Error al iniciar sesión", task.getException());
                    }
                });
    }
    

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
