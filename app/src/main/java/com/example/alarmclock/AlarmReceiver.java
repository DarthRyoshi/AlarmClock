package com.example.alarmclock;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context, intent);
    }

    private void showNotification(Context context, Intent intent) {
        String alarmTime = intent.getStringExtra("ALARM_TIME");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alarmClockChannel")
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Alarma")
                .setContentText("Alarma programada para " + alarmTime + " está sonando!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
