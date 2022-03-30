package com.main.theshop;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class UserProfileActivity extends SingleFragmentActivity{
    private static final String EXTRA_USER_ID = "userId";

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        UUID userId = UUID.fromString(intent.getSerializableExtra(EXTRA_USER_ID).toString());

        return new UserProfile(userId);
    }
}
