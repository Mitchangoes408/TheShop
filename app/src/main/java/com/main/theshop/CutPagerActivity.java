package com.main.theshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class CutPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Cuts> mCuts;
    private static final String EXTRA_CUT_ID =
            "android.theshop.cut_id";

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_cut_pager);

        mViewPager = (ViewPager)findViewById(R.id.cut_view_pager);
        mCuts = Shop.get(this).getCuts();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Cuts cuts = mCuts.get(position);
                return CutFragment.newInstance(cuts.getmId());
            }

            @Override
            public int getCount() {
                return mCuts.size();
            }
        });

        UUID cutId = (UUID)getIntent().getSerializableExtra(EXTRA_CUT_ID);

        for(int i = 0; i < mCuts.size(); i++) {
            if(mCuts.get(i).getmId().equals(cutId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(
            Context packageContext, UUID cutId) {
        Intent intent = new Intent(packageContext,
                CutPagerActivity.class);
        intent.putExtra(EXTRA_CUT_ID, cutId);
        return intent;
    }

}
