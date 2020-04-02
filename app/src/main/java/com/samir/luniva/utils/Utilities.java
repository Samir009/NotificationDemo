package com.samir.luniva.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import static android.content.Context.MODE_PRIVATE;
import static com.samir.luniva.constants.AppConstants.FCM_TOKEN;
import static com.samir.luniva.constants.AppConstants.FCM_TOKEN_PREF;

/**
 * created by SAMIR SHRESTHA on 2/18/2020  at 1:56 PM
 */

public class Utilities {

    public static FirebaseAuth firebaseAuth(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }

    public static FirebaseFirestore firebaseFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db;
    }

    public static void saveFcmToken(Context context, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FCM_TOKEN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FCM_TOKEN, token);
        editor.apply();
    }

    public static String getSavedFcmToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FCM_TOKEN_PREF, MODE_PRIVATE);
        String fcmToken = sharedPreferences.getString(FCM_TOKEN, "Token is empty");
        return fcmToken;
    }
}
