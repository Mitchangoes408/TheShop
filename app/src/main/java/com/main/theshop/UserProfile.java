package com.main.theshop;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserProfile extends Fragment {
    private ImageView mImageView;
    private TextView mTextView;
    private RecyclerView mCutsRecycler;
    private CutsAdapter mAdapter;
    DataBaseHelper myDb;

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

        mTextView = (TextView) view.findViewById(R.id.profile_text);
        mImageView = (ImageView)view.findViewById(R.id.profile_image);

        mCutsRecycler = (RecyclerView)view.findViewById(R.id.cuts_recycler_view);
        mCutsRecycler.setLayoutManager(
                new GridLayoutManager(getActivity(), 3));

        updateUI();



        return view;

    }



    public void updateUI() {
        Shop theShop = Shop.get(getActivity());
        List<Cuts> cuts = theShop.getCuts();
        if(mAdapter == null) {
            mAdapter = new CutsAdapter(cuts);
            mCutsRecycler.setAdapter(mAdapter);

        }
        else
            mAdapter.notifyDataSetChanged();
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

                Intent intent = CutPagerActivity.newIntent(getActivity(), newCut.getmId());
                startActivity(intent);
                return true;

            case R.id.edit_profile:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private class CutsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mCutsView;
        private TextView mCutsText;
        private Cuts mCut;

        public CutsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.profile_cut_item, parent, false));
            mCutsView = (ImageView)itemView.findViewById(R.id.cut_image);
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

    private class CutsAdapter extends  RecyclerView.Adapter<CutsHolder> {
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




}
