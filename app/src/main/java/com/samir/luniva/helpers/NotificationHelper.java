package com.samir.luniva.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.samir.luniva.R;
import com.samir.luniva.activities.ClinicActivity;
import com.samir.luniva.activities.MainActivity;
import com.samir.luniva.constants.AppConstants;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.samir.luniva.constants.AppConstants.NOTIFICATION_REQUEST;

public class NotificationHelper {

//    Notification Builder
//    Notification Channel
//    Notification Manager

    public static void displayNotification(Context context, String title, String body){

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST /* Request code */, intent,
                FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, AppConstants.NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel channel = new NotificationChannel(AppConstants.NOTIFICATION_CHANNEL_ID, AppConstants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(AppConstants.NOTIFICATION_CHANNEL_DESCRIPTION);
            channel.setSound(defaultSoundUri, attributes);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] {500, 500, 500, 500, 500});
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
            mNotificationManager.createNotificationChannel(channel);
            mNotificationManager.notify(NOTIFICATION_REQUEST, notificationBuilder.build());

        } else {

            NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
            mNotificationManager.notify(AppConstants.NOTIFICATION_REQUEST, notificationBuilder.build());
        }

    }
}
