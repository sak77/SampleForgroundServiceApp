package com.test.sampleforgroundserviceapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("My Sample Music Player")
                    .setTicker("My Sample Music Player")
                    .setContentText("My Music")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(Bitmap.createScaledBitmap(icon,128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(R.drawable.ic_play_arrow_black_24dp, "Play", pplayIntent)
                    .addAction(R.drawable.ic_skip_previous_black_24dp, "Previous", ppreviousIntent)
                    .addAction(R.drawable.ic_skip_next_black_24dp, "Next", pnextIntent).build();
            /*
            Note that calling startForeground method does not put the service in the started state itself, even though the name sounds like it.
            You must always call startService(Intent) first to tell the system it should keep the service running, and then use this method to tell it to keep it running harder.
            * */
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,notification);
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i(LOG_TAG, "Clicked Previous");
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i(LOG_TAG, "Clicked Play");
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Next");
        }else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            stopForeground(true);
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
