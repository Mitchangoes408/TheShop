package com.main.theshop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.UUID;

public class ApptDialogFragment extends DialogFragment {
    private TextView apptDate;
    private Button completeAppt;
    private Button cancelAppt;

    private static final String ARG_APPT = "appointment";

    public static ApptDialogFragment newInstance(Appointments appointment) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_APPT, appointment.getApptUUID());
        ApptDialogFragment fragment = new ApptDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        UUID apptId = (UUID)getArguments().getSerializable(ARG_APPT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.appt_dialog, null);

      apptDate = (TextView) v.findViewById(R.id.date_dialog_text);
      apptDate.setText(Shop.get(getActivity()).getAppt(apptId).getScheduledDate().toString());

      completeAppt = (Button) v.findViewById(R.id.complete_appt_btn);
      completeAppt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              /** CREATE NEW CUT, TAKE PICTURE AND ADD TO DB
               *    BASICALLY MOVE THE "+" MENU OPTION TO THIS BUTTON **/
          }
      });

      cancelAppt = (Button) v.findViewById(R.id.cancel_appt_btn);
      cancelAppt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              /** DELETE APPOINTMENT FROM DATABASE **/
              Shop.get(getActivity()).deleteAppt(apptId);
          }
      });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Appointment")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                            }
                        })
                .create();
    }
}
