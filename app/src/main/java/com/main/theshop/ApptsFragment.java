package com.main.theshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.net.IDN;
import java.util.UUID;

public class ApptsFragment extends Fragment {
    private static final int REQUEST_DATE = 0;
    private Appointments appointment;

    //View Fields
    private DatePicker datePicker;

    private static final String ARG_APPT_ID = "appt_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID apptID = (UUID)getArguments().getSerializable(ARG_APPT_ID);
        appointment = Shop.get(getActivity()).getAppt(apptID);

    }

    @Override
    public void onPause() {
        super.onPause();
        Shop.get(getActivity()).updateAppt(appointment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
            return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_appt,
                container,
                false
        );

        datePicker = (DatePicker)v.findViewById(R.id.date_picker);

        return v;
    }

    public static ApptsFragment newInstance(UUID apptID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_APPT_ID, apptID);
        ApptsFragment fragment = new ApptsFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
