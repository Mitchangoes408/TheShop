package com.main.theshop;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserProfile extends Fragment {
    private RecyclerView mCutsRecycler;
    private CutsAdapter mAdapter;

    @Override
    public void onSaveInstance(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.profile, container, false);

        mCutsRecycler = (RecyclerView)view.findViewById(R.id.cuts_recycler_view);
        mCutsRecycler.setLayoutManager(new GridLayoutManager(getActivity()));


        return view;

    }



    private class CutsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mCutsView;
        private Cuts mCut;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.profile_cut_item, parent, false));
            mCutsView = (ImageView)itemView.findViewById(R.id.cut_image);
            itemView.setOnClickListener(this);
        }

        //NEEDS WORK FOR BINDING DATA
        public void bind(Cuts cut) {
            mCut = cut;
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
