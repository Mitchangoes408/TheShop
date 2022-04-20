package com.main.theshop.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.main.theshop.models.Appointments;
import com.main.theshop.models.Cuts;
import com.main.theshop.R;
import com.main.theshop.models.Shop;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ApptDialogFragment extends DialogFragment {
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    private TextView apptDate;
    private TextView apptType;
    private TextView apptDetails;
    private Button completeAppt;
    private Button cancelAppt;
    //private Button photoButton;
    private Cuts cut;
    private File mCutPhotoFile;

    private static final String ARG_APPT = "appointment";
    private static final String EXTRA_DIALOG = "com.main.theshop.dialog";

    private void sendResult(int resultCode, UUID apptId) {
        if(getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DIALOG, apptId);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    public static ApptDialogFragment newInstance(Appointments appointment) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_APPT, appointment.getApptUUID());
        ApptDialogFragment fragment = new ApptDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)
            return;

        if(requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.main.theshop.fileprovider",
                    mCutPhotoFile);

            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        UUID apptId = (UUID)getArguments().getSerializable(ARG_APPT);
        Log.d("PHOTO", "onCreate UUID " + apptId.toString());

        //cut = Shop.get(getActivity()).getCut(apptId);
        //mCutPhotoFile = Shop.get(getActivity()).getPhotoFile(cut);
        /** BECAUSE OF ABOVE, APPT_UUID MUST MATCH THE CUT_UUID **/

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.appt_dialog, null);

        /** Appointment Date Display **/
        apptDate = (TextView) v.findViewById(R.id.date_dialog_text);
        apptDate.setText(Shop.get(getActivity()).getAppt(apptId).getScheduledDate().toString());

        /** Appointment Type Display **/
        apptType = (TextView) v.findViewById(R.id.type_dialog_text);
        apptType.setText(Shop.get(getActivity()).getAppt(apptId).getCutType());

        /** Appointment Detail Display **/
        apptDetails = (TextView) v.findViewById(R.id.appt_dialog_details);
        StringBuilder stringBuilder = new StringBuilder("Additional details: ");
        stringBuilder.append(Shop.get(getActivity()).getAppt(apptId).getApptDetails());
        apptDetails.setText(stringBuilder);

        completeAppt = (Button) v.findViewById(R.id.complete_appt_btn);

        //Image Capture Intent
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        completeAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** CREATE NEW CUT, TAKE PICTURE AND ADD TO DB
                 *    BASICALLY MOVE THE "+" MENU OPTION TO THIS BUTTON **/
                Log.d("Photos", "onClick: Enter ");

                //CREATE THE CUT
                Cuts newCut = new Cuts(apptId);
                Log.d("PHOTO", "newCut UUID: " + newCut.getmId().toString());

                //ADD ALL THE DETAILS
                newCut.setCutDetails(Shop.get(getActivity()).getAppt(apptId).getApptDetails());
                newCut.setCutType(Shop.get(getActivity()).getAppt(apptId).getCutType());
                newCut.setCompletedDate(new Date());
                Shop.get(getActivity()).addCut(newCut);

                //TAKE PICTURE

                mCutPhotoFile = Shop.get(getActivity()).getPhotoFile(newCut);
                Log.d("PHOTO", "Photo File: " + mCutPhotoFile.toString());

                Uri uri = FileProvider.getUriForFile(getActivity(),
                          "com.main.theshop.fileprovider",
                          mCutPhotoFile);

                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities =
                          getActivity().getPackageManager().queryIntentActivities(
                                  captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                for(ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                              uri,
                              Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);


                /**   ADD A WAY TO ADD CUT DETAILS AFTER PHOTO IS TAKEN **/


                //DELETE THE APPT
                Shop.get(getActivity()).deleteAppt(apptId);
                sendResult(Activity.RESULT_OK, apptId);
                getDialog().dismiss();

                Log.d("Appointment Dialog", "Appointment " + apptId + " completed.");

            }
        });

        cancelAppt = (Button) v.findViewById(R.id.cancel_appt_btn);
        cancelAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** DELETE APPOINTMENT FROM DATABASE **/
                Shop.get(getActivity()).deleteAppt(apptId);

                //USED TO
                sendResult(Activity.RESULT_OK, apptId);
                getDialog().dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle("Appointment")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    sendResult(Activity.RESULT_OK, apptId);
                                }
                            })
                    .create();
    }


}
