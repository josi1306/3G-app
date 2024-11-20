package com.example.a3gracing.ui.repuesto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a3gracing.databinding.FragmentRepuestosBinding;

public class RepuestoFragment extends Fragment {

private FragmentRepuestosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        RepuestoViewModel repuestoViewModel =
                new ViewModelProvider(this).get(RepuestoViewModel.class);

    binding = FragmentRepuestosBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textRepuestos;
        repuestoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}