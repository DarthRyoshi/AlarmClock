package com.example.alarmclock;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ViewAlarmsActivity extends AppCompatActivity {
    private ListView alarmsListView;
    private List<String> alarmsList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Button backToMainButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alarms);

        alarmsListView = findViewById(R.id.alarmsListView);
        backToMainButton = findViewById(R.id.backToMainButton);
        dbHelper = new DatabaseHelper(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alarmsList);
        alarmsListView.setAdapter(adapter);

        loadAlarms();

        backToMainButton.setOnClickListener(view -> finish());
    }

    private void loadAlarms() {
        Cursor cursor = dbHelper.getAlarms();
        alarmsList.clear();

        // Verifica si el cursor es nulo o no contiene registros
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int timeIndex = cursor.getColumnIndex("time");
                // Verifica si timeIndex es válido
                if (timeIndex != -1) {
                    String time = cursor.getString(timeIndex);
                    alarmsList.add("Hora: " + time);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            // Manejar el caso donde no hay alarmas
            alarmsList.add("No hay alarmas.");
        }

        adapter.notifyDataSetChanged();
    }
}
