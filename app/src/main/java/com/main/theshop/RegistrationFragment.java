package com.main.theshop;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class RegistrationFragment extends Fragment {
    private EditText usernameInput,
            passwordInput,
            fullNameInput,
            emailInput,
            phoneNumber;
    private Switch accountType;
    private Button submitBtn;

    private String username,
            password,
            fullName,
            email,
            phone,
            acctType;

    private User user;

    private static final String ARG_REG = "registration";

    private static final String EXTRA_USERNAME = "username";
    private static final String EXTRA_PASSWORD = "password";
    private static final String EXTRA_FULLNAME = "fullname";
    private static final String EXTRA_EMAIL = "email";
    private static final String EXTRA_PHONE = "phone";
    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_USER_ID = "userId";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.registration_form,
                container,
                false
        );

        usernameInput = (EditText) v.findViewById(R.id.username_register);
        usernameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordInput = (EditText) v.findViewById(R.id.password_register);
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fullNameInput = (EditText) v.findViewById(R.id.fullname_register);
        fullNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fullName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailInput = (EditText) v.findViewById(R.id.email_register);
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phoneNumber = (EditText) v.findViewById(R.id.phone_register);
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phone = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /** MAYBE CHANGE SWITCH TO ANOTHER USER SELECTION TYPE **/
        accountType = (Switch) v.findViewById(R.id.account_type_switch);



        submitBtn = (Button) v.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** CHECK FOR DUPLICATES **/
                if(Shop.get(getActivity()).checkUser(username)) {
                    Toast errorToast = Toast.makeText(getContext(), "Username already used.", Toast.LENGTH_LONG);
                    errorToast.show();
                }
                else {
                    user = new User(username, password, fullName, email, phone, acctType);
                    Shop.get(getActivity()).addUser(user);
                    Log.d("RegistrationFragment", "onSubmit: currUserId = " + user.getId().toString());
                    Shop.get(getActivity()).setCurrUser(user.getId());

                    Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                    startActivity(intent);
                }
            }
        });


        return v;
    }



    public static RegistrationFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
