package com.example.a3gracing.ui.registro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a3gracing.databinding.FragmentRegistroBinding;

public class registroFragment extends Fragment {

private FragmentRegistroBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        registroViewModel registroViewModel =
                new ViewModelProvider(this).get(registroViewModel.class);

    binding = FragmentRegistroBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textregistro;
        registroViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}