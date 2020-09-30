package com.pk.wifireminderalarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    private static final int NOTIFICATION_ID = 0;
    NotificationManager mNotifyManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Triggered");
        deliverNotification(context);
    }

    public void deliverNotification(Context context) {

        mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle("Its 12 Am")
                .setContentText("Turn off data. Save it for tomorrow")
                .setSmallIcon(R.drawable.ic_network_check)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}
