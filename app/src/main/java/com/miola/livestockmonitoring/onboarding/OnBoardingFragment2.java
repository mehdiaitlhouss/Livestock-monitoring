package com.miola.livestockmonitoring.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.miola.livestockmonitoring.R;
import com.miola.livestockmonitoring.SignIn;

public class OnBoardingFragment2 extends Fragment {

    TextView textViewSkip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_on_bording_2, container, false);

        textViewSkip = root.findViewById(R.id.textView2);

        textViewSkip.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SignIn.class));
        });

        return root;
    }
}
