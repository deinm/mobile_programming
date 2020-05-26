package edu.skku.map.week10_class;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.net.URI;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "Week10 FCM TAG";
    String title;
    String content;
    String body;

    public FirebaseMessagingService() {
    }

    @Override
    public void onNewToken(@NonNull String s){
        Log.d(TAG, "Refreshed token: " + s);
        super.onNewToken(s);

        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String token){
        Log.e(TAG, "here ! sendRegistrationToServer! token is " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From : "+remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0){
            Log.d(TAG, "Data : "+remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null){
            Log.d(TAG, "send Notification");
            title = remoteMessage.getData().get("title");
            content = remoteMessage.getData().get("content");
            body = remoteMessage.getNotification().getBody();

//            Log.d(TAG, "Message Notification Body: "+body);

            sendNotification(body);
        }
    }

    private void sendNotification (String messageBody){
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelID = "fcm default channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setVibrate(new long[]{1000, 1000})
                        .setLights(Color.BLUE, 1, 1)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelName = "NOTIFICATION";
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}
