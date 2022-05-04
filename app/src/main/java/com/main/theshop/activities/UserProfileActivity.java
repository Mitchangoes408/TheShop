package com.main.theshop.activities;

import androidx.fragment.app.Fragment;

import com.main.theshop.fragments.UserProfile;

public class UserProfileActivity extends SingleFragmentActivity {
    //private static final String EXTRA_USER_ID = "userId";

    @Override
    protected Fragment createFragment() {
        return new UserProfile();
    }
}
