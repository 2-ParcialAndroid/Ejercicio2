package com.example.listaeventos;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventFormActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);

        databaseReference = FirebaseDatabase.getInstance().getReference("events");

        EditText etName = findViewById(R.id.et_event_name);
        EditText etDescription = findViewById(R.id.et_event_description);
        EditText etLocation = findViewById(R.id.et_event_location);
        EditText etPrice = findViewById(R.id.et_event_price);
        EditText etDate = findViewById(R.id.et_event_date);

        findViewById(R.id.btn_save_event).setOnClickListener(v -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            String location = etLocation.getText().toString();
            double price = Double.parseDouble(etPrice.getText().toString());
            String date = etDate.getText().toString();

            String eventId = databaseReference.push().getKey(); // Genera un ID único para el evento
            Event event = new Event(name, description, location, price, date);
            databaseReference.child(eventId).setValue(event)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar evento", Toast.LENGTH_SHORT).show());
        });

        findViewById(R.id.btn_cancel).setOnClickListener(v -> finish());
    }
}
