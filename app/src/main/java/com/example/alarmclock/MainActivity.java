package com.example.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    Button loginButton; // Botón de login
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        viewAlarmsButton = findViewById(R.id.viewAlarmsButton);
        loginButton = findViewById(R.id.topRightButton); // Botón de login
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Crear el canal de notificación si es necesario
        NotificationHelper.createNotificationChannel(this);

        // Configurar el botón para establecer la alarma
        setAlarmButton.setOnClickListener(view -> setAlarm());

        // Configurar el botón para ver las alarmas
        viewAlarmsButton.setOnClickListener(view -> viewAlarmsActivity());

        // Configurar el botón de login para abrir LoginActivity
        loginButton.setOnClickListener(view -> openLoginActivity());
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);

        // Generar un ID único para cada alarma
        int alarmId = new Random().nextInt(10000);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("ALARM_ID", alarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Guardar la alarma en SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AlarmPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(alarmId), calendar.getTime().toString()); // Guardar ID y hora de la alarma
        editor.apply();

        Toast.makeText(this, "Alarm set with ID: " + alarmId, Toast.LENGTH_SHORT).show();
    }

    // Método para abrir la actividad de ver alarmas
    private void viewAlarmsActivity() {
        Intent intent = new Intent(MainActivity.this, ViewAlarmsActivity.class);
        startActivity(intent);
    }

    // Método para abrir la actividad de login
    private void openLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

