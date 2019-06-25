package com.oluwaseun.liadi.crimeintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeListRecyclerView;
    private List<Crime> mCrimes;

    private CrimeListAdapter mCrimeListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeListRecyclerView = v.findViewById(R.id.crime_list_recycle);
        mCrimes = CrimeLab.getInstance().getCrimeList();
        mCrimeListAdapter = new CrimeListAdapter(getActivity() , mCrimes);
        mCrimeListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCrimeListRecyclerView.setAdapter( mCrimeListAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCrimeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.
        }
        return super.onOptionsItemSelected(item);
    }

    public static Fragment newFragment(){
        return new CrimeListFragment();
    }
}
