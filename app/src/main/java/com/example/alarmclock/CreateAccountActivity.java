package com.example.alarmclock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button createAccountButton;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        createAccountButton = findViewById(R.id.createAccountButton);

        dbHelper = new DatabaseHelper(this);

        createAccountButton.setOnClickListener(view -> createAccount());
    }

    private void createAccount() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar el usuario en la base de datos
        dbHelper.addUser(username, password);
        Toast.makeText(this, "Cuenta creada exitosamente.", Toast.LENGTH_SHORT).show();

        // Volver a la actividad de login
        finish();
    }
}
