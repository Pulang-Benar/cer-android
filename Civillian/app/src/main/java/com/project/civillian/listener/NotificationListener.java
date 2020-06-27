package com.project.civillian.listener;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.civillian.R;
import com.project.civillian.activity.MainActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NotificationListener extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println(TAG+" From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            System.out.println(TAG+" Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            System.out.println(TAG+" Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData());
    }

    private void sendNotification(String title, String body, Map<String, String> data) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("latitude", data.get("latitude"));
        intent.putExtra("longitude", data.get("longitude"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String lokasiKejadian = getAddress(data.get("latitude"), data.get("longitude"));
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_twitter)
                        .setColor(getResources().getColor(R.color.colorAccent))
                        .setContentTitle(title)
                        .setContentText(lokasiKejadian)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private String getAddress(String latitude, String longitude){
        String alamatLengkap = "";
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(getDouble(latitude), getDouble(longitude), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!addresses.isEmpty()){
            alamatLengkap = addresses.get(0).getAddressLine(0); //setLatestFormattedAddress
        }
        return alamatLengkap;
    }

    private Double getDouble(String d){
        if(d != null) return Double.valueOf(d);
        else return 0d;
    }

}