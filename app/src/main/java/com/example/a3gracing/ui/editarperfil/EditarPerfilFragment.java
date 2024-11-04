package com.example.a3gracing.ui.editarperfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a3gracing.databinding.FragmentEditarPerfilBinding;

public class EditarPerfilFragment extends Fragment {

private FragmentEditarPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        EditarPerfilViewModel editarPerfilViewModel =
                new ViewModelProvider(this).get(EditarPerfilViewModel.class);

    binding = FragmentEditarPerfilBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textEditarPerfil;
        editarPerfilViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}