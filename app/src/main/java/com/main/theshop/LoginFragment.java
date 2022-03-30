package com.main.theshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class LoginFragment extends Fragment {
    private Button loginButton, cancelButton, registerButton;
    private TextView titleText;
    private EditText username, password;

    private String usernameText, passwordText;

    private static final String EXTRA_USER_ID = "userId";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.login,
                container,
                false);

        loginButton = (Button) view.findViewById(R.id.login_button);
        //cancelButton = (Button)findViewById(R.id.cancel_button);
        registerButton = (Button) view.findViewById(R.id.register_button);
        titleText = (TextView) view.findViewById(R.id.login_screen);
        username = (EditText) view.findViewById(R.id.username_input);
        password = (EditText) view.findViewById(R.id.password_input);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameText = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordText = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verify with database that login information is valid then bring to HOMEPAGE
                /**
                 *  TAKE THE USERNAME AND PASSWORD
                 *  CHECK IT AGAINST THE DATABASE
                 *  IF BOTH MATCH, RETRIEVE THE UUID
                 *  FIGURE OUT A WAY TO PASS THAT OR SET IT FOR THE HOMESCREEN
                 */
                User user = Shop.get(getActivity()).getUser(usernameText, passwordText);

                if(user != null) {
                    Intent intent = new Intent(getContext(),
                            UserProfileActivity.class);
                    Log.d("Login Screen", "Starting UserProfileActivity");
                    intent.putExtra(EXTRA_USER_ID, user.getId());
                    Log.d("Login Screen", "UserId = " + user.getId());
                    startActivity(intent);

                }
                /** DISPLAY ERROR DIALOG/TOAST IF CREDENTIALS DONT MATCH **/

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open up a form to create new user
                //
                // create user
                ////pop up form for: USERNAME, PASSWORD, NAME, PHONE, EMAIL

                RegistrationFragment registrationFragment = RegistrationFragment.newInstance();
                FragmentManager fragmentManager = getFragmentManager();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container, registrationFragment)
                        .commit();
            }
        });


        return view;
    }
}
