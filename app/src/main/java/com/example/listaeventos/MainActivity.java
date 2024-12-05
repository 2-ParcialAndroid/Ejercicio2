package com.example.listaeventos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ArrayList<Event> eventList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        ListView lvEvents = findViewById(R.id.lv_events);
        Button btnAddEvent = findViewById(R.id.btn_add_event);

        eventList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        lvEvents.setAdapter(adapter);

        btnAddEvent.setOnClickListener(v -> startActivity(new Intent(this, EventFormActivity.class)));

        loadEvents();
    }
    private void addSampleEvent() {
        DatabaseReference sampleRef = FirebaseDatabase.getInstance().getReference("events");
        String eventId = sampleRef.push().getKey();
        Event sampleEvent = new Event("Sample Event", "This is a sample event", "Sample Location", 10.0, "2024-12-31");
        sampleRef.child(eventId).setValue(sampleEvent)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Sample event added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error adding sample event", Toast.LENGTH_SHORT).show());
    }

    private void loadEvents() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                ArrayList<String> eventNames = new ArrayList<>();

                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    eventList.add(event);
                    eventNames.add(event.getName() + " - " + event.getDescription());
                }

                adapter.clear();
                adapter.addAll(eventNames);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error al cargar eventos", Toast.LENGTH_SHORT).show();
            }

        });
    }
}