package com.samir.luniva.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.samir.luniva.R;
import com.samir.luniva.constants.AppConstants;
import com.samir.luniva.helpers.AppActivity;
import com.samir.luniva.helpers.NotificationHelper;
import com.samir.luniva.helpers.ShowToast;
import com.samir.luniva.models.Fcm_User_Device_Token;
import com.samir.luniva.utils.Utilities;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.Random;

import fcm.androidtoandroid.FirebasePush;
import fcm.androidtoandroid.connection.PushNotificationTask;
import fcm.androidtoandroid.model.Notification;

public class SendNotificationActivity extends AppActivity {

    //    initiate firebase
    CollectionReference colUserDeviceReg;
    //    db.collection("registered_user");
    DocumentReference docUserDeviceReg;
    Fcm_User_Device_Token fcm_user_device_token;
    private String users_token = "";
    private String user_id;

    private Button send_notification, send_notification_to_all, local_noti_btn;

    private EditText noti_id, noti_title, noti_body;
//    private EditText noti_grp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        initializeView();
        initializeListeners();

        ShowToast.withLongMessage("your id is: " + AppConstants.USER_ID);

    }

    @Override
    protected void initializeView() {
        send_notification_to_all = findViewById(R.id.send_noti_to_all_btn);
        send_notification = findViewById(R.id.send_noti_btn);
        local_noti_btn = findViewById(R.id.local_noti_btn);
        noti_id = findViewById(R.id.notification_to_id);
//        noti_grp = findViewById(R.id.notification_to_grp);
        noti_title = findViewById(R.id.noti_title_txt);
        noti_body = findViewById(R.id.noti_descri_txt);

    }

    @Override
    protected void initializeListeners() {

        //sends notificaitons to devices without using firebase console
        FirebasePush firebasePush = new FirebasePush("AIzaSyAui-TQgY0-S7yUtAQwDeIzZByDDepxQUk");
        firebasePush.setAsyncResponse(new PushNotificationTask.AsyncResponse() {
            @Override
            public void onFinishPush(@NotNull String ouput) {
                Log.e("OUTPUT", ouput);
            }
        });

//        find the token according to user id
        noti_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                user_id = noti_id.getText().toString();
                getTokenFromId(noti_id.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        send_notification_to_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePush.setNotification(new Notification(noti_title.getText().toString(), noti_body.getText().toString()));
                firebasePush.sendToTopic(AppConstants.FCM_TOPIC);
            }
        });

        send_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String notiId = noti_id.getText().toString();

                if(notiId.isEmpty()){
                    ShowToast.withLongMessage("Insert Id");

                } else {

                    if(users_token.isEmpty()){
                        Log.e( "onClick: ", "On notification button clicked: token token is empty" );
                    } else {
                        Log.e( "onClick: ", "Individual notification button clicked: token token is " + users_token);
                        firebasePush.setNotification(new Notification(noti_title.getText().toString(), noti_body.getText().toString()));
                        firebasePush.sendToToken(users_token);
                    }

                }
// Send to topic
//        firebasePush.sendToTopic("news");

// or send to token
//        firebasePush.sendToToken("cyaqeJZhSH4:APA91bFfvJesBs6vQGE0VZEqEKkMo3TgRJGu8ph5_NB64DmLoKLJz15-S663XwQauWkm5R6VHf5d7e0fzS-adPfWXLcpv2ApaWBGfpR7fKULsagZHU4Lyg2vRO4rsRFvzM9JezDq2snJ");
// or send to user segment
//                JSONArray jsonArray = new JSONArray();
//                jsonArray.put("firebaseTokenId1");
//                jsonArray.put("firebaseTokenId2");
//                jsonArray.put("firebaseTokenId3");
//                firebasePush.sendToGroup(jsonArray);

            }
        });

        local_noti_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationHelper.displayNotification(getApplicationContext(), noti_title.getText().toString(), noti_body.getText().toString());
            }
        });

    }
    private void getTokenFromId(String userid) {

        Log.e( "getTokenFromId: ", userid);

        colUserDeviceReg = Utilities.firebaseFirestore().collection("registered_user_devices");
        docUserDeviceReg = colUserDeviceReg.document();

        colUserDeviceReg.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        fcm_user_device_token = documentSnapshot.toObject(Fcm_User_Device_Token.class);

                        if(fcm_user_device_token.getUser_id().equals(userid)){

                            users_token = fcm_user_device_token.getToken();
                            ShowToast.withLongMessage(fcm_user_device_token.getToken());
                            break;

                        } else {
                            ShowToast.withLongMessage("Wrong Id");
                        }
                    }

                } else {
                    ShowToast.withLongMessage("No data found");
                }
            }
        });
    }
}
