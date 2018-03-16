package com.ritvi.kaajneeti.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ritvi.kaajneeti.MainActivity;
import com.ritvi.kaajneeti.R;

/**
 * Created by sunil on 18-08-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    DatabaseReference databaseReference;
    DatabaseReference chatdatabasereference;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        try {
//            Log.d(TagUtils.getTag(), "remote msg:-" + remoteMessage.getData().toString());
//            Log.d(TagUtils.getTag(), "success:-" + remoteMessage.getData().get("success"));
//            Log.d(TagUtils.getTag(), "message:-" + remoteMessage.getData().get("result"));
//            Log.d(TagUtils.getTag(), "type:-" + remoteMessage.getData().get("type"));
////            checkType(getApplicationContext(), remoteMessage.getData().get("type"), remoteMessage.getData().get("result"));
//        } catch (Exception e) {
//            Log.d(TAG, e.toString());
            try {
                Log.d(TAG, "From: " + remoteMessage.getFrom());
                Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
                sendLiveNot(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
            }
//        }
    }


    public void sendLiveNot(String messageBody,String title) {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}