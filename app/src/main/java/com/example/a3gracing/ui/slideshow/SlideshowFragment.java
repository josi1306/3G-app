package com.example.a3gracing.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a3gracing.R;
import com.example.a3gracing.databinding.FragmentSlideshowBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.GoogleAuthProvider;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    // Launcher para Google Sign-In
    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(Exception.class);
                        if (account != null) {
                            firebaseAuthWithGoogle(account);
                        }
                    } catch (Exception e) {
                        Log.w("SlideshowFragment", "Google sign in failed", e);
                    }
                }
            });

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

        // Configurar Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        // Referencias a los EditText y botones
        EditText emailEditText = root.findViewById(R.id.Email);
        EditText passwordEditText = root.findViewById(R.id.Contraseña);
        Button ingresarButton = root.findViewById(R.id.ingresar);
        View googleSignInButton = root.findViewById(R.id.imageView3);

        // Acción del botón "Ingresar"
        ingresarButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, ingresa un correo y una contraseña", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                NavController navController = NavHostFragment.findNavController(SlideshowFragment.this);
                                navController.navigate(R.id.nav_home);
                            } else {
                                String error = task.getException() instanceof FirebaseAuthInvalidCredentialsException ?
                                        "Usuario o contraseña incorrectos" :
                                        "Error en la autenticación, intentalo nuevamente";
                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Acción para iniciar sesión con Google
        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

        // Acción del botón "Registrar"
        View registrarse = root.findViewById(R.id.registrar);
        registrarse.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(SlideshowFragment.this);
            NavOptions navOptions = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(navController.getGraph().getStartDestinationId(), false)
                    .build();
            navController.navigate(R.id.nav_registro, null, navOptions);
        });

        return root;
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        String email = mAuth.getCurrentUser().getEmail();
                        if (email != null) {
                            checkUserRegistration(email);
                        }
                    } else {
                        Toast.makeText(getContext(), "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUserRegistration(String email) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null &&
                            task.getResult().getSignInMethods() != null &&
                            task.getResult().getSignInMethods().isEmpty()) {
                        NavController navController = NavHostFragment.findNavController(SlideshowFragment.this);
                        Bundle args = new Bundle();
                        args.putString("email", email);
                        navController.navigate(R.id.nav_registro, args);
                    } else {
                        NavController navController = NavHostFragment.findNavController(SlideshowFragment.this);
                        navController.navigate(R.id.nav_home);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
