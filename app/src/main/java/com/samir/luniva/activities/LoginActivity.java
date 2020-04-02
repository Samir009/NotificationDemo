package com.samir.luniva.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.samir.luniva.R;
import com.samir.luniva.databinding.ActivityLoginBinding;
import com.samir.luniva.helpers.ShowToast;
import com.samir.luniva.utils.Utilities;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = Utilities.firebaseAuth();

        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signInWithEmailAndPassword(binding.emailTxt.getText().toString(), binding.passwordTxt.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                } else {
                                    ShowToast.withLongMessage("Invalid credentials");
                                }

                            }
                        });
            }
        });

        binding.signUpHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });

    }
    private void showProgressBar(){
        binding.signInProgressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        binding.signInProgressbar.setVisibility(View.GONE);
    }

}
