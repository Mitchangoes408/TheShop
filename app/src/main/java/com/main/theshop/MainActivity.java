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


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open up a form to create new user


                //create user
                User newUser = new User(username.getText().toString(), password.getText().toString(), true);

                //add user to database
            }
        });

    }


}