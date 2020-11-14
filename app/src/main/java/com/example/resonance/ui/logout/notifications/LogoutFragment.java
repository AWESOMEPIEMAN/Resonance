package com.example.resonance.ui.logout.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.resonance.LogIn;
import com.example.resonance.MainActivity;
import com.example.resonance.R;
import com.google.android.gms.common.SignInButton;

public class LogoutFragment extends Fragment {
    private LogoutViewModel logoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logoutViewModel =
                new ViewModelProvider(this).get(LogoutViewModel.class);
        View v = inflater.inflate(R.layout.fragment_logout, container, false);

        Button b = (Button) v.findViewById(R.id.log_out_button);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LogIn.class);
                startActivity(intent);
            }
        });

        return v;
    }

}