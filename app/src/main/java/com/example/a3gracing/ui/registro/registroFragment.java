package com.example.a3gracing.ui.registro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a3gracing.R;
import com.example.a3gracing.databinding.FragmentRegistroBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class registroFragment extends Fragment {

    private FragmentRegistroBinding binding;
    private FirebaseAuth mAuth; // FirebaseAuth instance

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registroViewModel registroViewModel =
                new ViewModelProvider(this).get(registroViewModel.class);

        binding = FragmentRegistroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Referencias a los EditText
        EditText emailEditText = root.findViewById(R.id.Email);
        EditText passwordEditText = root.findViewById(R.id.Contraseña);
        EditText confirmPasswordEditText = root.findViewById(R.id.confirmar_contraseña);

        // Obtener el correo del argumento, si existe
        if (getArguments() != null) {
            String prefilledEmail = getArguments().getString("email");
            if (prefilledEmail != null && !prefilledEmail.isEmpty()) {
                emailEditText.setText(prefilledEmail);
                emailEditText.setEnabled(false); // Desactivar edición si viene desde Google
            }
        }

        // Botón de registro
        View registrarButton = root.findViewById(R.id.registrar);
        registrarButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // Validación de campos
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                // Validar si las contraseñas coinciden
                Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                // Validar la longitud de la contraseña
                Toast.makeText(getContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            } else {
                // Crear usuario con Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                // Registro exitoso, navegar al HomeFragment
                                Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                                NavController navController = NavHostFragment.findNavController(registroFragment.this);
                                navController.navigate(R.id.nav_home);
                            } else {
                                // Manejo de errores en el registro
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getContext(), "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
