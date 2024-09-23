package com.example.alarmclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewAlarmsActivity extends AppCompatActivity {
    private ListView alarmsListView;
    private List<String> alarmsList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Button backToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alarms);

        alarmsListView = findViewById(R.id.alarmsListView);
        backToMainButton = findViewById(R.id.backToMainButton);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alarmsList);
        alarmsListView.setAdapter(adapter);

        // Cargar y mostrar las alarmas guardadas
        loadAlarms();

        // Acción para volver a MainActivity
        backToMainButton.setOnClickListener(view -> finish());
    }

    private void loadAlarms() {
        SharedPreferences sharedPreferences = getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE);
        Map<String, ?> allAlarms = sharedPreferences.getAll(); // Obtener todas las alarmas

        alarmsList.clear(); // Limpiar la lista antes de cargar
        for (Map.Entry<String, ?> entry : allAlarms.entrySet()) {
            alarmsList.add("Alarm ID: " + entry.getKey() + ", Time: " + entry.getValue().toString()); // Agregar alarmas a la lista
        }

        // Log para verificar el contenido de SharedPreferences
        Log.d("ViewAlarmsActivity", "Alarms: " + allAlarms.toString());

        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }
}
