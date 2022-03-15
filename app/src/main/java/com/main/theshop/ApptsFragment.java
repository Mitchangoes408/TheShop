package com.main.theshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

public class ApptsFragment extends Fragment {
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "DialogDate";

    private Appointments appointment;

    private TextView apptTypeText;
    private Spinner apptSpinner;
    private Button datePickerButton;
    private Button submitApptBtn;

    private static final String ARG_APPT_ID = "appt_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID apptID = (UUID)getArguments().getSerializable(ARG_APPT_ID);
        appointment = Shop.get(getActivity()).getAppt(apptID);

        //get photofile maybe?
            //for attaching cut pictures

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

        if(requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            appointment.setScheduledDate(date);
            datePickerButton.setText(appointment.getScheduledDate().toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.appointment_form,
                container,
                false
        );

        apptTypeText= (TextView) v.findViewById(R.id.appt_text);

        apptSpinner = (Spinner)v.findViewById(R.id.cut_type_menu);
        //SPINNER DETAILS
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.cut_style, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        apptSpinner.setAdapter(adapter);

        datePickerButton = (Button) v.findViewById(R.id.date_picker_button);
        //DATE PICKER DETAILS
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();

                DatePickerFragment dateFragment = DatePickerFragment.newInstance(appointment.getScheduledDate());
                dateFragment.setTargetFragment(ApptsFragment.this, REQUEST_DATE);
                dateFragment.show(fm, DIALOG_DATE);
            }
        });

        submitApptBtn = (Button) v.findViewById(R.id.schedule_button);
        //SUBMIT BUTTON DETAILS
        /*
        submitApptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** FINISH ADDING APPOINTMENT DETAILS TO THE DATABASE AND RETURN TO HOME
                 *      MAYBE ADD A TOAST TO LET USER KNOW THAT THE APPOINTMENT IS SCHEDULED *
            }
        });
        */




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
