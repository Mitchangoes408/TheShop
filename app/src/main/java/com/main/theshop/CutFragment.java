package com.main.theshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/** CUT FRAGMENT NOTES
 *      ADD A MENU OPTION TO EDIT THE CUT DETAILS
 *      FIGURE OUT THE GIF DISPLAY
 *
 */

public class CutFragment extends Fragment {
    //request codes:
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_EDIT = 2;

    private Cuts mCut;

    //view fields
    private ImageView cutImage;
    private TextView cutText;

    private File mCutPhotoFile;

    private static final String ARG_CUT_ID = "cut_id";
    private static final String DIALOG_EDIT = "DialogEdit";

    private static final String EXTRA_DIALOG = "com.main.theshop.dialog";
    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_DETAILS = "details";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID cutId = (UUID)getArguments().getSerializable(ARG_CUT_ID);
        mCut = Shop.get(getActivity()).getCut(cutId);
        mCutPhotoFile = Shop.get(getActivity()).getPhotoFile(mCut);

        setHasOptionsMenu(true);
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

        if(requestCode == REQUEST_EDIT) {
            if(data.getSerializableExtra(EXTRA_TYPE) != null || data.getSerializableExtra(EXTRA_DETAILS) != null) {
                mCut.setCutType(data.getSerializableExtra(EXTRA_TYPE).toString());
                mCut.setCutDetails(data.getSerializableExtra(EXTRA_DETAILS).toString());

                StringBuilder stringBuilder = new StringBuilder("Cut Type: " + mCut.getCutType());
                stringBuilder.append("\nAdditional Requests: " + mCut.getCutDetails());
                cutText.setText(stringBuilder);
            }
            Shop.get(getActivity()).updateCut(mCut);
        }
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

        //PROGRAMMATICALLY SET IMAGE HEIGHT BASED ON SCREEN DIMENSIONS
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenHeight = displayMetrics.heightPixels + getNavigationBarHeight();
        Log.d("Screen Height", "MaxHeight = " + (screenHeight));
        Log.d("Screen Height", "SetMaxHeight = " + (3 * (screenHeight/5)));
        cutImage.setMaxHeight(3 * (screenHeight / 5));

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
        StringBuilder stringBuilder = new StringBuilder("Cut Type: " + mCut.getCutType());
        stringBuilder.append("\nAdditional Requests: " + mCut.getCutDetails());
        cutText.setText(stringBuilder);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.image_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.edit_details:
                /** DISPLAY A EDITTEXT TO CHANGE THE CUT DETAILS
                 *      MAYBE CHANGE TEXTVIEW => EDITTEXT
                 *      ADD A WAY TO CHANGE THE CURRENT ITEM IMAGE
                 *          EITHER THROUGH BUTTON OR LONG PRESS
                 **/
                FragmentManager fragmentManager = getFragmentManager();
                CutDialogFragment dialogFragment = CutDialogFragment.newInstance(mCut);
                dialogFragment.setTargetFragment(CutFragment.this, REQUEST_EDIT);
                dialogFragment.show(fragmentManager, DIALOG_EDIT);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }



    public static CutFragment newInstance(UUID cutId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUT_ID, cutId);
        CutFragment fragment = new CutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int getNavigationBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            ((Activity)getContext()).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }


}
