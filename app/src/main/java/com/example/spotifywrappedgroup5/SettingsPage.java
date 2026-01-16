package com.example.spotifywrappedgroup5;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotifywrappedgroup5.databinding.LandingPageBinding;
import com.example.spotifywrappedgroup5.databinding.SettingsPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsPage extends Fragment {

    private SettingsPageBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = SettingsPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        Button update_email_button = view.findViewById(R.id.update_email);
        Button update_pass_button = view.findViewById(R.id.update_pass);
        Button delete_account_button = view.findViewById(R.id.delete_account);

        update_email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(getContext()).inflate(R.layout.update_email_popup, null);
                final EditText currEmail = popup.findViewById(R.id.currEmail);
                final EditText currPass = popup.findViewById(R.id.currPass);
                final EditText newEmail = popup.findViewById(R.id.newEmail);
                AlertDialog dialog = new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Update Email")
                        .setView(popup)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = auth.getCurrentUser();
                                // Get auth credentials from the user for re-authentication
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(currEmail.getText().toString().trim(), currPass.getText().toString().trim()); // Current Login Credentials \\
                                // Prompt the user to re-provide their sign-in credentials
                                user.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                        Log.d("TAG", "User re-authenticated.");
                                                        //Now change your email address \\
                                                        //----------------Code for Changing Email Address----------\\
                                                        FirebaseUser user = auth.getCurrentUser();
                                                        user.verifyBeforeUpdateEmail(newEmail.getText().toString().trim())
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Toast.makeText(getActivity(), "Check new email for verification", Toast.LENGTH_SHORT).show();
                                                                            Log.d("TAG", "User email address updated.");
                                                                        } else {
                                                                            Toast.makeText(getActivity(), "Update Failed, please try again", Toast.LENGTH_SHORT).show();
                                                                            Log.d("TAG", "User email not updated.");
                                                                        }
                                                                    }
                                                                });
                                                } else {
                                                    Toast.makeText(getActivity(), "Update, Failed, please try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        update_pass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(getContext()).inflate(R.layout.update_pass_popup, null);
                final EditText currEmail = popup.findViewById(R.id.currEmail);
                final EditText currPass = popup.findViewById(R.id.currPass);
                final EditText newPass = popup.findViewById(R.id.newPass);
                AlertDialog dialog = new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Update Pass")
                        .setView(popup)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = auth.getCurrentUser();
                                // Get auth credentials from the user for re-authentication
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(currEmail.getText().toString().trim(), currPass.getText().toString().trim()); // Current Login Credentials \\
                                // Prompt the user to re-provide their sign-in credentials
                                user.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAG", "User re-authenticated.");
                                                    //Now change your email address \\
                                                    //----------------Code for Changing Email Address----------\\
                                                    FirebaseUser user = auth.getCurrentUser();
                                                    user.updatePassword(newPass.getText().toString().trim())
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(getActivity(), "Updated Password", Toast.LENGTH_SHORT).show();
                                                                        Log.d("TAG", "User password address updated.");
                                                                    } else {
                                                                        Toast.makeText(getActivity(), "Update Failed, please try again", Toast.LENGTH_SHORT).show();
                                                                        Log.d("TAG", "User password not updated.");
                                                                    }
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(getActivity(), "Update Failed, please try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        delete_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(getContext()).inflate(R.layout.delete_account_popup, null);
                final EditText currEmail = popup.findViewById(R.id.currEmail);
                final EditText currPass = popup.findViewById(R.id.currPass);

                AlertDialog dialog = new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Update Pass")
                        .setMessage("Are you sure you want to delete your account? Input credentials to continue")
                        .setView(popup)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = auth.getCurrentUser();
                                // Get auth credentials from the user for re-authentication
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(currEmail.getText().toString().trim(), currPass.getText().toString().trim()); // Current Login Credentials \\
                                // Prompt the user to re-provide their sign-in credentials
                                user.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAG", "User re-authenticated.");
                                                    //Now change your email address \\
                                                    //----------------Code for Changing Email Address----------\\
                                                    FirebaseUser user = auth.getCurrentUser();
                                                    user.delete()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(getActivity(), "Account Deleted", Toast.LENGTH_SHORT).show();
                                                                        NavHostFragment.findNavController(SettingsPage.this)
                                                                                .navigate(R.id.action_settingsPage_to_LandingPage);
                                                                        Log.d("TAG", "User deleted.");
                                                                    } else {
                                                                        Toast.makeText(getActivity(), "Deletion Failed, please try again", Toast.LENGTH_SHORT).show();
                                                                        Log.d("TAG", "User not deleted.");
                                                                    }
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(getActivity(), "Deletion Failed, please try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });
    }
}