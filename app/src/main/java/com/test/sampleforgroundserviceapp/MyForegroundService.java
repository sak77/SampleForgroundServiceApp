package com.test.sampleforgroundserviceapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by saket.shriwas on 3/15/2018.
 */

public class MyForegroundService extends Service {
    private static final String LOG_TAG = "MyForegroundService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //By default service is started in foreground
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Intent previousIntent = new Intent(this, MyForegroundService.class);
            previousIntent.setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent playIntent = new Intent(this, MyForegroundService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, MyForegroundService.class);
            nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Drawable d = getResources().getDrawable(R.drawable.ic_all_out_black_24dp);
            d.isAutoMirrored();
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.sample);

            /*
            Starting Android O (API 26) all notifications are part of Notification channels or
            categories. The channel allows the user to have more granular control over permissions
            that apply to notifications that come on that channel.

            Channels can also be grouped together into NotificationChannelGroups.
            So you can define the properties of channels and also of the notification that
            show up on the channel.

            For more info, refer -
            https://developer.android.com/training/notify-user/channels
             */
            String channel_id = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel_id = "com.test.sampleforegroundserviceapp";
                NotificationChannel channel = new NotificationChannel(channel_id,
                        "My Background service", NotificationManager.IMPORTANCE_NONE);
                channel.enableLights(true);
                channel.setLightColor(Color.BLUE);
                channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                NotificationManager manager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
            } else {
                //before API 26, channel id is not used....
            }
            Icon playIcon = Icon.createWithResource(getBaseContext(),
                    R.drawable.ic_play_arrow_black_24dp);
            Icon skipPrevIcon = Icon.createWithResource(getBaseContext(),
                    R.drawable.ic_skip_previous_black_24dp);
            Icon skipNextIcon = Icon.createWithResource(getBaseContext(),
                    R.drawable.ic_skip_next_black_24dp);

            Notification notification = new Notification.Builder(this, channel_id)
                    .setContentTitle("Sample music player")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setLargeIcon(Icon.createWithResource(getBaseContext(), R.drawable.sample))
                    .setContentIntent(pendingIntent)
                    .setOngoing(
                            true)   //notification cannot be swiped by the user...also it tells
                    // the user that
                    //some task is on-going in the background.
                    .addAction(
                            new Notification.Action.Builder(playIcon, "Play", pplayIntent).build())
                    .addAction(new Notification.Action.Builder(skipPrevIcon, "Previous",
                            ppreviousIntent).build())
                    .addAction(new Notification.Action.Builder(skipNextIcon, "Next",
                            pnextIntent).build())
                    .build();

            /*
            Note that calling startForeground method does not put the service in the started
            state itself, even though the name sounds like it.
            You must always call startService(Intent) first to tell the system it should keep the
             service running, and then use this method to tell it to keep it running harder.
            * */
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i(LOG_TAG, "Clicked Previous");
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i(LOG_TAG, "Clicked Play");
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Next");
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            //stopForeground only stops the foreground behavior of the service. But the service
            // continues to run.
            stopForeground(true);
            //stopSelf will stop the background service as well...
            stopSelf();
        }
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }
}
