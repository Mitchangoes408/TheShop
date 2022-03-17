package com.main.theshop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import java.io.File;

public class PhotosFragment extends DialogFragment {
    private static final String ARG_PHOTO = "photoFile";
    private ImageView mPhotoView;

    public static PhotosFragment newInstance(File photoFile) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO, photoFile);
        PhotosFragment fragment = new PhotosFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        File photoFile = (File)getArguments().getSerializable(ARG_PHOTO);
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.photo_dialog, null);

        mPhotoView = (ImageView) view.findViewById(R.id.dialog_photo_view);

        if(photoFile == null)
            mPhotoView.setImageDrawable(null);
        else {
            Bitmap bm = PictureUtils.getScaledBitmap(
                    photoFile.getPath(), getActivity()
            );
            mPhotoView.setImageBitmap(bm);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Cut: ")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .create();
    }
}
