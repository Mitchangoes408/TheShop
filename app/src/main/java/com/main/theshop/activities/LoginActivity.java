package com.main.theshop.activities;

import androidx.fragment.app.Fragment;

import com.main.theshop.fragments.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}