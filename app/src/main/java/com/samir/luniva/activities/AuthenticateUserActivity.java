package com.samir.luniva.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.samir.luniva.R;
import com.samir.luniva.databinding.ActivityAuthenticateUserBinding;
import com.samir.luniva.helpers.ShowToast;
import com.samir.luniva.utils.Utilities;

import java.util.concurrent.TimeUnit;

public class AuthenticateUserActivity extends AppCompatActivity {

    ActivityAuthenticateUserBinding binding;

    private String email;
    private String contact;
    private String password;

    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthenticateUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        contact = bundle.getString("contact");
        password = bundle.getString("password");

        Log.e( "onCreate: ",email + contact + password );

        sendVerificationCode(contact);

        binding.authenticateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(verificationCode);
            }
        });

    }

    //    send otp verification code to the given number
    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, 120, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
//                auto insert otp code here
                Log.e( "OTP code: ", code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.e("onVerificationFailed: ", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

//        verifies the code that has been sent to user
    private void verifyCode(String code){

        showProgressBar();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);

        registerUser(credential.getSmsCode());
    }

    private void registerUser(String otp){
        (Utilities.firebaseAuth().createUserWithEmailAndPassword(email,password))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            Log.e("onComplete: ","phone number verified." );

                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                ShowToast.withLongMessage("Users is already registered");
                            } else {
                                ShowToast.withLongMessage(task.getException().toString());
                            }
                        }
                    }
                });
    }

//    private void signInWithCredential(PhoneAuthCredential credential) {
//        Utilities.firebaseAuth().signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
////                            register user
//                            (Utilities.firebaseAuth().createUserWithEmailAndPassword(email,password))
//                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if(task.isSuccessful()){
//                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                                                Log.e("onComplete: ","phone number verified." );
//
//                                            } else {
//                                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
//                                                ShowToast.withLongMessage("Users is already registered");
//                                            } else {
//                                                ShowToast.withLongMessage(task.getException().toString());
//                                            }
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
//    }

    private void showProgressBar(){
        binding.numberAuthProgressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        binding.numberAuthProgressbar.setVisibility(View.GONE);
    }

}
