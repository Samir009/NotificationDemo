package com.samir.luniva.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.samir.luniva.R;
import com.samir.luniva.databinding.ActivitySignupBinding;
import com.samir.luniva.helpers.ShowToast;
import com.samir.luniva.utils.Utilities;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignupBinding binding;

//    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        firebaseAuth = Utilities.firebaseAuth();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

//        updateUI(currentUser);

        binding.signUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.sign_up:
                showProgressBar();

                if(binding.emailTxt.getText().toString().isEmpty() || binding.passwordTxt.getText().toString().isEmpty()){
                    ShowToast.withLongMessage("Email or password is empty");
                    hideProgressBar();

                } else {
                    if(!binding.passwordTxt.getText().toString().equals(binding.rePasswordTxt.getText().toString())
                            || binding.passwordTxt.getText().toString().length()<=5){
                        ShowToast.withLongMessage("password not matched or less than 6 character");
                        hideProgressBar();
                    }
                    else {

                                        sendUserData();

//                        ShowToast.withLongMessage("hurrey, all credentials are fine :)");
//                        firebaseAuth.createUserWithEmailAndPassword(binding.emailTxt.getText().toString(), binding.passwordTxt.getText().toString())
//                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if(task.isSuccessful()){
//                                            hideProgressBar();
//                                            ShowToast.withLongMessage("Signup successful :)");
//                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                        } else {
//                                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
//                                                ShowToast.withLongMessage("Users is already registered");
//                                            } else {
//                                                ShowToast.withLongMessage(task.getException().toString());
//                                            }
//                                        }
//                                    }
//                                });
                    }
                }
                break;
        }
    }

    private void showProgressBar(){
        binding.signUpProgressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        binding.signUpProgressbar.setVisibility(View.GONE);
    }

    protected void sendUserData(){
        ;



        Intent intent = new Intent(getApplicationContext(), AuthenticateUserActivity.class);
        intent.putExtra("email", binding.emailTxt.getText().toString());
        intent.putExtra("contact", binding.numebrTxt.getText().toString());
        intent.putExtra("password", binding.passwordTxt.getText().toString());

        Log.e( "sendUserData: ",  binding.numebrTxt.getText().toString());
        startActivity(intent);

        finish();
    }
}
