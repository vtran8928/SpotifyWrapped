package com.example.spotifywrappedgroup5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrappedgroup5.databinding.SignupPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SignupPage extends Fragment {
    private SignupPageBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SignupPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        EditText signupName = view.findViewById(R.id.full_name);
        EditText signupEmail = view.findViewById(R.id.email);
        EditText signupPass = view.findViewById(R.id.password);
        EditText signupVerifyPass = view.findViewById(R.id.verify_password);
        Button signupButton = view.findViewById(R.id.submit_signup);

        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignupPage.this)
                        .navigate(R.id.action_SignUpPage_to_LandingPage);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = signupName.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String pass = signupPass.getText().toString().trim();
                String verifyPass = signupVerifyPass.getText().toString().trim();

                if (email.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                }
                if (name.isEmpty()) {
                    signupName.setError("Name cannot be empty");
                }

                if (pass.isEmpty()) {
                    signupPass.setError("Password cannot be empty");
                } else if (!pass.equals(verifyPass)) {
                    signupVerifyPass.setError("Passwords must match");
                } else {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Signup Successful", Toast.LENGTH_SHORT).show();

                                String uid = auth.getCurrentUser().getUid();
                                UserInformation userInformation = new UserInformation(uid, name, email);
                                reference.child(uid).setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            System.out.println("User Created");
                                        }
                                        else
                                        {
                                            System.out.println("User not created");
                                        }
                                    }
                                });


                                NavHostFragment.findNavController(SignupPage.this)
                                        .navigate(R.id.action_SignUpPage_to_LandingPage);
                            } else {
                                Toast.makeText(getActivity(), "Signup Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                System.out.println(task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}