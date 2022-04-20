package com.main.theshop.models;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.main.theshop.R;

public class HomePage extends AppCompatActivity {
    private TextView appointments;
    private Button scheduleApt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        appointments = (TextView) findViewById(R.id.appointment_section);
        scheduleApt = (Button) findViewById(R.id.schedule_button);

        scheduleApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
