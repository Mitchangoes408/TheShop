package com.main.theshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class CutFragment extends Fragment {
    //request codes:
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    private Cuts mCut;

    //view fields
    private ImageView cutImage;
    private TextView cutText;

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
        TEST WITH: ADD BUTTON PRESSED
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_cut,
                container,
                false);

        cutImage = (ImageView)v.findViewById(R.id.cut_image);
        if(mCutPhotoFile == null || !mCutPhotoFile.exists()) {
            cutImage.setImageDrawable(null);
        }
        else {
            Bitmap bm = PictureUtils.getScaledBitmap(
                    mCutPhotoFile.getPath(), getActivity()
            );
            cutImage.setImageBitmap(bm);
        }
        cutText = (TextView)v.findViewById(R.id.cut_description);

        return v;
    }



    public static CutFragment newInstance(UUID cutId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUT_ID, cutId);
        CutFragment fragment = new CutFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
