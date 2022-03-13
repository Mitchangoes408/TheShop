package com.main.theshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserProfile extends Fragment {
    private static final int REQUEST_DATE = 0;

    private static final String DIALOG_DATE ="DialogDate";
    private ImageView mProfileImage;
    private TextView mProfileDescription;
    private TextView mAppointments;
    private RecyclerView mCutsRecycler;
    private RecyclerView mApptRecycler;
    private CutsAdapter mAdapter;
    private ApptAdapter mApptAdapter;
    private Appointments appointment;
    CutBaseHelper cutDb;
    ApptBaseHelper apptDb;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        //myDb = new DataBaseHelper(this.getContext());
        View view = inflater.inflate(
                R.layout.profile, container, false);

        mProfileDescription = (TextView) view.findViewById(R.id.profile_text);
        mProfileImage = (ImageView)view.findViewById(R.id.profile_image);
        //mAppointments = (TextView)view.findViewById(R.id.upcoming_appointments);



        mApptRecycler = (RecyclerView)view.findViewById(R.id.appt_recycler);
        mApptRecycler.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        mCutsRecycler = (RecyclerView)view.findViewById(R.id.cuts_recycler_view);
        mCutsRecycler.setLayoutManager(
                new GridLayoutManager(getActivity(), 3));

        updateUI();

        return view;
    }

    public void updateUI() {
        Shop theShop = Shop.get(getActivity());
        List<Cuts> cuts = theShop.getCuts();
        List<Appointments> appointments = theShop.getAppts();
        if(mAdapter == null) {
            mAdapter = new CutsAdapter(cuts);
            mCutsRecycler.setAdapter(mAdapter);
        }
        if(mApptAdapter == null) {
            mApptAdapter = new ApptAdapter(appointments);
            mApptRecycler.setAdapter(mApptAdapter);
        }
        else {
            mAdapter.notifyDataSetChanged();
            mApptAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.new_cut:
                Cuts newCut = new Cuts();
                Shop.get(getActivity()).addCut(newCut);

                Intent cutIntent = CutPagerActivity.newIntent(getActivity(), newCut.getmId());
                startActivity(cutIntent);
                return true;

            case R.id.edit_profile:

                return true;

            case R.id.new_appointment:

                //using ApptPagerActivity
                Appointments newAppt = new Appointments();
                Shop.get(getActivity()).addAppt(newAppt);

                Intent apptIntent = ApptPagerActivity.newIntent(getActivity(), newAppt.getApptUUID());
                startActivity(apptIntent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //CUTS HELPER CLASSES FOR RECYCLERVIEW

    private class CutsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mCutsImage;
        private TextView mCutsText;
        private Cuts mCut;

        public CutsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.profile_cut_item, parent, false));
            mCutsImage = (ImageView)itemView.findViewById(R.id.cut_image);
            mCutsText = (TextView)itemView.findViewById(R.id.cut_text);
            itemView.setOnClickListener(this);
        }

        //NEEDS WORK FOR BINDING DATA
        public void bind(Cuts cut) {
            mCut = cut;
            mCutsText.setText(mCut.getmId().toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = CutPagerActivity.newIntent(
                    getActivity(), mCut.getmId());
            startActivity(intent);
        }
    }

    private class CutsAdapter extends RecyclerView.Adapter<CutsHolder> {
        List<Cuts> mCuts;

        public CutsAdapter(List<Cuts> cuts) {
            mCuts = cuts;
        }

        @Override
        public CutsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CutsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CutsHolder holder, int position) {
            Cuts cut = mCuts.get(position);
            holder.bind(cut);
        }

        @Override
        public int getItemCount() {
            return mCuts.size();
        }

        public void setCuts(List<Cuts> cuts) {
            mCuts = cuts;
        }
    }

    //APPOINTMENT HELPER CLASSES FOR RECYCLERVIEW

    private class ApptHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mApptsText;
        private Appointments mAppointment;

        public ApptHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.appointments_item, parent, false));
            mApptsText = (TextView)itemView.findViewById(R.id.upcoming_appointments);
            itemView.setOnClickListener(this);
        }

        public void bind(Appointments appointment) {
            mAppointment = appointment;
            mApptsText.setText(mAppointment.getScheduledDate().toString());
        }

        @Override
        public void onClick(View view) {
            /**
             ADD POP UP MENU FOR EITHER COMPLETING OR CANCELLING APPOINTMENT
                Completing = remove from ApptDatabase, then proceed to add photo to
                            profile RecyclerView
                Cancelling = remove from ApptDatabase
             */
        }
    }

    private class ApptAdapter extends RecyclerView.Adapter<ApptHolder> {
        List<Appointments> mAppointments;

        public ApptAdapter(List<Appointments> appointments) {
            mAppointments = appointments;
        }

        @Override
        public ApptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ApptHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ApptHolder holder, int position) {
            Appointments appointment = mAppointments.get(position);
            holder.bind(appointment);
        }

        @Override
        public int getItemCount() {
            return mAppointments.size();
        }

        public void setAppts(List<Appointments> appointments) {
            mAppointments = appointments;
        }
    }




}
