package com.example.a3gracing.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a3gracing.R;
import com.example.a3gracing.databinding.FragmentSlideshowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Referencias a los EditText y el botón
        EditText emailEditText = root.findViewById(R.id.editTextTextEmailAddress);
        EditText passwordEditText = root.findViewById(R.id.editTextTextPassword);
        Button ingresarButton = root.findViewById(R.id.ingresar);

        // Acción del botón "Ingresar"
        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    // Si los campos están vacíos, mostrar un mensaje
                    Toast.makeText(getContext(), "Por favor, ingresa un correo y una contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    // Intentar iniciar sesión con Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), task -> {
                                if (task.isSuccessful()) {
                                    // Login exitoso, navegar al HomeFragment
                                    Toast.makeText(getActivity(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                    NavController navController = NavHostFragment.findNavController(SlideshowFragment.this);
                                    navController.navigate(R.id.nav_home);
                                } else {
                                    // Si el login falla, mostrar un mensaje de error
                                    String error = task.getException() instanceof FirebaseAuthInvalidCredentialsException ?
                                            "Usuario o contraseña incorrectos" :
                                            "Error en la autenticación, intentalo nuevamente";
                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Acción del botón "Registrarse" (aún no implementado, pero añadido por ahora)
        View registrarse = root.findViewById(R.id.registrar);
        registrarse.setOnClickListener(v -> {
            // Aquí puedes navegar a otro fragmento para registro si fuera necesario
            NavController navController = NavHostFragment.findNavController(SlideshowFragment.this);
            NavOptions navOptions = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(navController.getGraph().getStartDestinationId(), false)
                    .build();
            //navController.navigate(R.id.nav_register, null, navOptions);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
