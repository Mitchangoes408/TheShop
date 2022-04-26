package com.main.theshop.models;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.theshop.R;
import com.main.theshop.activities.ApptPagerActivity;
import com.main.theshop.activities.CutPagerActivity;
import com.main.theshop.fragments.ApptDialogFragment;
import com.main.theshop.utils.PictureUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserProfile extends Fragment {
    // FRAGMENT
    private static final int REQUEST_DIALOG = 0;
    private static final int REQUEST_PHOTO = 1;

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_APPT = "DialogAppt";
    private static final String EXTRA_DIALOG = "com.main.theshop.dialog";

    private ImageView mFavImage;
    private TextView mFavDetails;
    private RecyclerView mCutsRecycler;
    private RecyclerView mApptRecycler;
    private CutsAdapter mAdapter;
    private ApptAdapter mApptAdapter;
    private Cuts currFav;

    private File mCutPhotoFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("UserProfile", "onCreate: currUserId = " + Shop.get(getActivity()).getCurrUserId().toString());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.profile, container, false);


        mFavDetails = (TextView) view.findViewById(R.id.profile_text);
        mFavImage = (ImageView)view.findViewById(R.id.profile_image);

        //SCREEN MATH FOR PROFILE IMAGE
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels + getNavigationBarHeight();
        Log.d("Screen Height", "SetMaxHeight = " + ((screenHeight/5)));
        mFavImage.setMaxHeight((screenHeight / 5));


         //SEARCH THROUGH THE CUTS DB AND FIND THE FAVORITED CUT AND LOAD IT
         //      STORE THE CURRENT FAVORITE CUT UUID FOR QUICK ACCESS ON NEW FAVORITE ITEM
        if(currFav != null){
            mCutPhotoFile = Shop.get(getActivity()).getPhotoFile(currFav);
            StringBuilder stringBuilder = new StringBuilder("Cut Type: " + currFav.getCutType());
            stringBuilder.append("\nAdditional Requests: " + currFav.getCutDetails());
            mFavDetails.setText(stringBuilder);
        }

        if(mCutPhotoFile == null || !mCutPhotoFile.exists()) {
            mFavImage.setImageDrawable(null);
        }
        else {
            Bitmap bm = PictureUtils.getScaledBitmap(
                    mCutPhotoFile.getPath(), getActivity()
            );
            mFavImage.setImageBitmap(bm);

        }


        mApptRecycler = (RecyclerView)view.findViewById(R.id.appt_recycler);
        mApptRecycler.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        mCutsRecycler = (RecyclerView)view.findViewById(R.id.cuts_recycler_view);

        mCutsRecycler.setLayoutManager(
                new GridLayoutManager(getActivity(), 3));
        //ATTEMPT FOR ONLONGCLICK
        registerForContextMenu(mCutsRecycler);

        updateUI();

        return view;
    }

    public void updateUI() {
        Shop theShop = Shop.get(getActivity());
        List<Cuts> cuts = theShop.getCuts();
        Collections.reverse(cuts);      //THIS IS DONE SO RECYCLER LOADS BY MOST RECENT
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
            mAdapter.setCuts(cuts);
            mAdapter.notifyDataSetChanged();
            mApptAdapter.setAppts(appointments);
            mApptAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
            return;

        if(requestCode == REQUEST_DIALOG) {
            //DELETE THE APPOINTMENT FROM THE ARRAYLIST THEN UPDATE UI
            Shop theShop = Shop.get(getActivity());
            Log.d("DIALOG", "onActivityResult: Deleted Appt ID: " + data.getSerializableExtra(EXTRA_DIALOG).toString());
            Log.d("DIALOG", "onActivityResult: Active Appts " + theShop.getAppts().size());
            updateUI();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    /**     MENU METHODS    **/
    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);

    }

    /** APP MENU OPTION ACTIONS **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.new_cut:
                Cuts newCut = new Cuts();
                Shop.get(getActivity()).addCut(newCut);

                //TAKE THE PICTURE TO ADD TO THE RECYCLERVIEW
                final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mCutPhotoFile = Shop.get(getActivity()).getPhotoFile(newCut);

                Uri uri = FileProvider.getUriForFile(Objects.requireNonNull(getActivity()),
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
                return true;

            case R.id.edit_profile:


                return true;

            case R.id.new_appointment:

                //using ApptPagerActivity
                Appointments newAppt = new Appointments();
                Shop.get(getActivity()).addAppt(newAppt);
                Log.d("NEW APPT ", "newAppt UUID: " + newAppt.getApptUUID());

                Intent apptIntent = ApptPagerActivity.newIntent(getActivity(), newAppt.getApptUUID());
                startActivity(apptIntent);

                return true;

            case R.id.sign_out:
                Shop.get(getActivity()).setCurrUser(null);
                getActivity().finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** LONG PRESS ON CUT ITEM ACTIONS **/
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Shop theShop = Shop.get(getActivity());
        List<Cuts> cuts = theShop.getCuts();
        Collections.reverse(cuts);
        Cuts cut = cuts.get(mAdapter.getPosition());

        switch(item.getItemId()) {
            case R.id.delete_item:
                //GET ITEM INFO, DELETE ITEM AND REFRESH
                Shop.get(getActivity()).deleteCut(cut);

                //REFRESH SCREEN
                updateUI();
                Toast.makeText(getContext(), "Deleted item", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.favorite_item:
                //ADD FAVORITE MARKER TO CUT
                cut.setFavorite("true");
                //REMOVE FAVORITE MARKER FROM CURRENT FAVORITE ITEM
                if(currFav != null) {       //CHECK FOR WHEN NO CURRENT FAVORITE IS SET
                    currFav.setFavorite("false");
                    Shop.get(getActivity()).updateCut(currFav);
                }
                currFav = cut;

                //REPLACE THE PROFILE IMAGE AND DESCRIPTION WITH SELECTION
                mCutPhotoFile = Shop.get(getActivity()).getPhotoFile(cut);

                if(mCutPhotoFile == null || !mCutPhotoFile.exists()) {
                    mFavImage.setImageDrawable(null);
                }
                else {
                    Bitmap bm = PictureUtils.getScaledBitmap(
                            mCutPhotoFile.getPath(), getActivity()
                    );
                    mFavImage.setImageBitmap(bm);
                    StringBuilder stringBuilder1 = new StringBuilder("Cut Type: " + currFav.getCutType());
                    stringBuilder1.append("\nAdditional Requests: " + currFav.getCutDetails());
                    mFavDetails.setText(stringBuilder1);
                }

                Shop.get(getActivity()).updateCut(cut);


            default:
                return super.onContextItemSelected(item);
        }
    }

    /** CUTS HELPER CLASSES FOR RECYCLERVIEW  **/
    /** NOTES FOR CUTS RECYCLER
     *      EVERNTUALLY FIND A WAY TO ADD A GIF OF THE PROFILE
     */

    private class CutsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        private ImageView mCutsImage;
        private TextView mCutsText;
        private Cuts mCut;

        public CutsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.profile_cut_item, parent, false));
            mCutsImage = (ImageView)itemView.findViewById(R.id.cut_image);

            /** WORK WITHOUT THE CUT DESCRIPTION FOR NOW    **/
            mCutsText = (TextView)itemView.findViewById(R.id.cut_text);


            itemView.setOnClickListener(this);

            /** LONG PRESS TO BRING UP A DELETE OPTION **/
            itemView.setOnCreateContextMenuListener(this);
        }

        //NEEDS WORK FOR BINDING DATA
        public void bind(Cuts cut) {
            mCut = cut;

            mCutPhotoFile = Shop.get(getActivity()).getPhotoFile(mCut);

            if(mCutPhotoFile == null || !mCutPhotoFile.exists()) {
                mCutsImage.setImageDrawable(null);
                mCutsText.setText(mCut.getmId().toString());
            }
            else {
                Bitmap bm = PictureUtils.getScaledBitmap(
                        mCutPhotoFile.getPath(), getActivity()
                );
                mCutsImage.setImageBitmap(bm);
            }
            mCutsText.setText(mCut.getmId().toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = CutPagerActivity.newIntent(
                    getActivity(), mCut.getmId());
            startActivity(intent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.context_menu, contextMenu);
        }
    }

    private class CutsAdapter extends RecyclerView.Adapter<CutsHolder> {
        List<Cuts> mCuts;
        private int position;

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
            Log.d("CONTEXT MENU", "Cut Position: " + position);
            holder.bind(cut);

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setPosition(holder.getPosition());
                    Log.d("CONTEXT MENU", "Long Position: " + holder.getPosition());
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCuts.size();
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setCuts(List<Cuts> cuts) {
            mCuts = cuts;
        }
    }




    /** APPOINTMENT HELPER CLASSES FOR RECYCLERVIEW **/

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
                POP UP MENU FOR EITHER COMPLETING OR CANCELLING APPOINTMENT
                Completing = remove from ApptDatabase, then proceed to add photo to
                            profile RecyclerView
                Cancelling = remove from ApptDatabase
             */

            FragmentManager fm = getFragmentManager();

            ApptDialogFragment apptDialog = ApptDialogFragment.newInstance(mAppointment);
            apptDialog.setTargetFragment(UserProfile.this, REQUEST_DIALOG);
            apptDialog.show(fm, DIALOG_APPT);

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
