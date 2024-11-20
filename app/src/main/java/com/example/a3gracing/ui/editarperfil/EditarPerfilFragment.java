package com.example.a3gracing.ui.editarperfil;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a3gracing.R;
import com.example.a3gracing.databinding.FragmentEditarPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditarPerfilFragment extends Fragment {

    private FragmentEditarPerfilBinding binding;
    private FirebaseAuth mAuth;
    private boolean isPasswordVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View binding
        binding = FragmentEditarPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        // Load user data
        loadUserData();

        // Show/Hide password functionality
        binding.imageButton.setOnClickListener(v -> togglePasswordVisibility());

        // Save data functionality
        binding.button.setOnClickListener(v -> saveUserData());

        return root;
    }

    private void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            binding.Email.setText(user.getEmail());
            if (user.getDisplayName() != null) {
                binding.nombre.setText(user.getDisplayName());
            }
            binding.Contrasena.setText("********"); // Placeholder for the password
        } else {
            Toast.makeText(getContext(), "No se encontró información del usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.Contrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.imageButton.setImageResource(R.drawable.ic_visibility_off); // Replace with your icon
        } else {
            binding.Contrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            binding.imageButton.setImageResource(R.drawable.ic_visibility); // Replace with your icon
        }
        isPasswordVisible = !isPasswordVisible;
        binding.Contrasena.setSelection(binding.Contrasena.getText().length());
    }

    private void saveUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = binding.Email.getText().toString().trim();
        String password = binding.Contrasena.getText().toString().trim();
        String name = binding.nombre.getText().toString().trim();

        // Update email
        user.updateEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Email actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al actualizar el email", Toast.LENGTH_SHORT).show();
            }
        });

        // Update password
        if (!password.equals("********")) { // Only update if password was modified
            user.updatePassword(password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Update name
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Nombre actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al actualizar el nombre", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
