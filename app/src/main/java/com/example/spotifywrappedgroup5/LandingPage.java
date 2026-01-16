package com.example.spotifywrappedgroup5;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrappedgroup5.databinding.LandingPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LandingPage extends Fragment {
    private LandingPageBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = LandingPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        EditText loginEmail = view.findViewById(R.id.userNameInput);
        EditText loginPass = view.findViewById(R.id.passwordInput);
        Button loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //temporary code to bypass loginpage
//                NavHostFragment.findNavController(LandingPage.this)
//                        .navigate(R.id.action_LandingPage_to_SpotifySummary);

                String email = loginEmail.getText().toString().trim();
                String pass = loginPass.getText().toString().trim();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(LandingPage.this)
                                        .navigate(R.id.action_LandingPage_to_SpotifySummary);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        loginPass.setError("Password cannot be empty");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Email cannot be empty");
                } else {
                    loginEmail.setError("Please enter valid email");
                }
            }
        });
        view.findViewById(R.id.singup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LandingPage.this)
                        .navigate(R.id.action_LandingPage_to_SignUpPage);
            }
        });

    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
