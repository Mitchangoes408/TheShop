package com.main.theshop.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.main.theshop.models.Cuts;
import com.main.theshop.R;
import com.main.theshop.models.Shop;

import java.util.UUID;


public class CutDialogFragment extends DialogFragment {
    private EditText cutDetails;
    private TextView cutType;
    private Spinner cutSpinner;
    private String newDetails;
    private String newType;

    private Cuts cut;

    private static final String ARG_CUT = "cut";

    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_DETAILS = "details";
    private static final String EXTRA_DIALOG = "com.main.theshop.dialog";

    private void sendResult(int resultCode, UUID cutId) {
        if(getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DIALOG, cutId);
        intent.putExtra(EXTRA_TYPE, newType);
        intent.putExtra(EXTRA_DETAILS, newDetails);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    public static CutDialogFragment newInstance(Cuts cut) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUT, cut.getmId());
        CutDialogFragment fragment = new CutDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        UUID cutId = (UUID)getArguments().getSerializable(ARG_CUT);
        cut = Shop.get(getActivity()).getCut(cutId);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.edit_cut_dialog, null);

        cutType = (TextView) v.findViewById(R.id.appt_text);

        cutSpinner = (Spinner) v.findViewById(R.id.cut_type_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.cut_style, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cutSpinner.setAdapter(adapter);

        cutDetails = (EditText) v.findViewById(R.id.appointment_details);
        cutDetails.setText(cut.getCutDetails());
        newDetails = cut.getCutDetails();
        cutDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newDetails = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Edit Cut Details")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("Details", "onClick: " + cut.getCutDetails());
                                Log.d("Type", "onClick: " + cut.getCutType());

                                newType = cutSpinner.getSelectedItem().toString();

                                sendResult(Activity.RESULT_OK, cutId);
                            }
                        })
                .create();

    }

}
