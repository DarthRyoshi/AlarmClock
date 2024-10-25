package com.example.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TimePicker timePicker;
    Button setAlarmButton;
    Button viewAlarmsButton;
    Button loginButton;
    AlarmManager alarmManager;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        viewAlarmsButton = findViewById(R.id.viewAlarmsButton);
        loginButton = findViewById(R.id.topRightButton);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        dbHelper = new DatabaseHelper(this);

        NotificationHelper.createNotificationChannel(this);

        setAlarmButton.setOnClickListener(view -> setAlarm());
        viewAlarmsButton.setOnClickListener(view -> viewAlarmsActivity());
        loginButton.setOnClickListener(view -> openLoginActivity());
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);

        int alarmId = new Random().nextInt(10000);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("ALARM_ID", alarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Guardar la alarma en la base de datos
        dbHelper.addAlarm(calendar.getTime().toString());

        Toast.makeText(this, "Alarma configurada con ID: " + alarmId, Toast.LENGTH_SHORT).show();
    }

    private void viewAlarmsActivity() {
        Intent intent = new Intent(MainActivity.this, ViewAlarmsActivity.class);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
