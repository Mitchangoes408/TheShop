package com.main.theshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button loginButton, cancelButton, registerButton;
    private TextView titleText;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginButton = (Button)findViewById(R.id.login_button);
        //cancelButton = (Button)findViewById(R.id.cancel_button);
        registerButton = (Button)findViewById(R.id.register_button);
        titleText = (TextView) findViewById(R.id.login_screen);
        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verify with database that login information is valid then bring to HOMEPAGE

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create user
                //pop up form for: USERNAME, PASSWORD, NAME, PHONE, EMAIL


                //add user to database
            }
        });

    }


}