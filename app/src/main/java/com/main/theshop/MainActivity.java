package com.main.theshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button submitButton, cancelButton, registerButton;
    private TextView titleText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        submitButton = (Button)findViewById(R.id.login_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        registerButton = (Button)findViewById(R.id.register_button);
        titleText = (TextView)findViewById(R.id.login_screen);



    }


}