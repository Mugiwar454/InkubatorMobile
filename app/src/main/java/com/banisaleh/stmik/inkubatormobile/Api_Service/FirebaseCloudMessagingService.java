package com.banisaleh.stmik.inkubatormobile.Api_Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.banisaleh.stmik.inkubatormobile.NotifActivity;
import com.banisaleh.stmik.inkubatormobile.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {
    View view;
    SharedPreferences sharedPreferences;
    public static final String mypreferenced = "myprefs";
    String dateString;
    private SimpleDateFormat dateFormter;
    String im;
    private long time;
    Context context;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("message"));

    }

    private void showNotification(String message){
        Intent i = new Intent(this, NotifActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.
                FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Inkubator Mobile")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
}
