package com.example.a3gracing.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a3gracing.databinding.FragmentHomeBinding;
import com.example.a3gracing.ui.gallery.GalleryFragment;
import com.example.a3gracing.R;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Configuramos el click del view2
        View view2 = root.findViewById(R.id.view2);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegamos usando NavController
                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(R.id.action_homeFragment_to_galleryFragment);
            }
        });

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void navigateToGallery(View view) {
        try {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.text_gallery, new GalleryFragment()); // Reemplaza con tu fragmento GalleryFragment
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            androidx.media3.common.util.Log.e("HomeFragment", "Error al navegar a GalleryFragment", e);
        }
    }
}