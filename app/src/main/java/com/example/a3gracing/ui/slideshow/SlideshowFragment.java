package com.example.a3gracing.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a3gracing.R;
import com.example.a3gracing.databinding.FragmentSlideshowBinding;
import com.example.a3gracing.ui.home.HomeFragment;

public class SlideshowFragment extends Fragment {

private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

    binding = FragmentSlideshowBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        
        View ingresar = root.findViewById(R.id.button2);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = NavHostFragment.findNavController(SlideshowFragment.this);
                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(navController.getGraph().getStartDestinationId(), false)
                        .build();

                navController.navigate(R.id.nav_home, null, navOptions);
            }
        });

        View registrarse = root.findViewById(R.id.button3);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = NavHostFragment.findNavController(SlideshowFragment.this);
                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(navController.getGraph().getStartDestinationId(), false)
                        .build();

                navController.navigate(R.id.nav_home, null, navOptions);
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