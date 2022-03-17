package com.main.theshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class ApptPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Appointments> mAppts;
    private static final String EXTRA_APPT_ID = "com.main.theshop.appt_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_pager);

        mViewPager = (ViewPager)findViewById(R.id.appt_view_pager);
        mAppts = Shop.get(this).getAppts();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Appointments appointments = mAppts.get(position);
                return ApptsFragment.newInstance(appointments.getApptUUID());
            }

            @Override
            public int getCount() {
                return mAppts.size();
            }
        });

        UUID apptId = (UUID)getIntent().getSerializableExtra(EXTRA_APPT_ID);

        for(int i = 0; i < mAppts.size(); i++) {
            if(mAppts.get(i).getApptUUID().equals(apptId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID apptId) {
        Intent intent = new Intent(packageContext,
                ApptPagerActivity.class);
        Log.d("NEW APPT ", "newIntent newAppt UUID: " + apptId);
        intent.putExtra(EXTRA_APPT_ID, apptId);
        return intent;
    }
}
