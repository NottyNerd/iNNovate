package io.innovate.innovate.Util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;

import io.innovate.innovate.MainActivity;
import io.innovate.innovate.R;
import io.innovate.innovate.Splash;

/**
 * Created by NottyNerd on 06/02/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Map data;
    String msg ="";
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        data = remoteMessage.getData();

        // Check if message contains a data payload.
        if (data.size() > 0) {
            Log.d(TAG, "Message data payload: " + data);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                msg = ""+ data.get("optional");
                Toast toast = Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
                toast.show();
                sendNotification(msg);

            }
        });


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.d("current task :", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClass().getSimpleName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        if(componentInfo.getPackageName().equalsIgnoreCase("io.innovate.innovate")){
            if(!TextUtils.isEmpty(messageBody)){
                new DialogUtility(this).showDialog(messageBody,"Alert!");
            }
        }
        else {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("push", messageBody);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle() .bigText(messageBody) .setBigContentTitle("Alert!");


            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.toolbar_icon)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setStyle(style)
                    .setVibrate(new long[]{1000, 1000})
                    .setLights(0xFF001042, 300, 1000);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        }
    }

    private void showNotification(String msg){
        //Creating a notification
        android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.toolbar_icon);
        Intent intent = new Intent(this, Splash.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        //builder.set
        builder.setVibrate(new long[]{1000, 1000})
                .setLights(0xFF001042, 300, 1000);
        builder.setContentTitle(getResources().getString(R.string.app_name));
        builder.setContentText(msg);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
