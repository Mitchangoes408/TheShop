package com.main.theshop;

import androidx.fragment.app.Fragment;

public class UserProfileActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new UserProfile();
    }
}
