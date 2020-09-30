package com.pk.wifireminderalarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    AppCompatToggleButton alertToggle;
    NotificationManager mNotifyManager;
    AlarmManager mAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertToggle = findViewById(R.id.toggle_alert);

        // Get handle to System Alarm service
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Use calendar object to set alarm time
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Create intent to launch broadcast class
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        // Check if Pending intent is already, if yes show the toggle as enabled
        boolean alarmUp = (PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_NO_CREATE) != null);
        alertToggle.setChecked(alarmUp);

        // Create a PendingIntent, Alarm manager will use this when its called
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alertToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Set Repeating Alarm at 12:00AM with a days interval, ie: Everyday
                    mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                } else {
                    mAlarmManager.cancel(pendingIntent);
                    mNotifyManager.cancelAll();
                }
            }
        });

        createNotificationChannel();
    }

    public void createNotificationChannel() {

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                "Wifi Reminder", NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Notification from wifi reminder");

        mNotifyManager.createNotificationChannel(notificationChannel);
    }
}