package com.main.theshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class CutFragment extends Fragment {
    //request codes:
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    private Cuts mCut;

    private File mCutPhotoFile;

    private static final String ARG_CUT_ID = "cut_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID cutId = (UUID)getArguments().getSerializable(ARG_CUT_ID);
        mCut = Shop.get(getActivity()).getCut(cutId);
        mCutPhotoFile = Shop.get(getActivity()).getPhotoFile(mCut);
    }

    @Override
    public void onPause() {
        super.onPause();
        Shop.get(getActivity()).updateCut(mCut);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
            return;


    }

    /*****************************
     IMAGE POPUP SCREEN ONCE IMAGE IN PROFILE IS CLICKED
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

    }

     */

    public static CutFragment newInstance(UUID cutId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUT_ID, cutId);
        CutFragment fragment = new CutFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
