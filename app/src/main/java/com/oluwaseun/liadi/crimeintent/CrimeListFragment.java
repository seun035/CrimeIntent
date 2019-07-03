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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeListRecyclerView;
    private List<Crime> mCrimes;

    private CrimeListAdapter mCrimeListAdapter;

    private boolean mSubtitleFlag = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCrimes = CrimeLab.getInstance(getActivity()).getCrimes();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeListRecyclerView = v.findViewById(R.id.crime_list_recycle);
        //mCrimes = CrimeLab.getInstance(getActivity()).getCrimeList();
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
            case R.id.new_crime :
                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);
                getActivity().startActivity(CrimePagerActivity.newCrimeActivityIntent(getActivity(),crime.getId()));
                return true;
            case R.id.show_subtitle :
                mSubtitleFlag = !mSubtitleFlag;
                getActivity().invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        updateToolbar(menu);
    }

    public static Fragment newFragment(){
        return new CrimeListFragment();
    }

    private void setSubtitle(){
        String subtitle = mSubtitleFlag ? getString(R.string.subtitle_format,mCrimes.size()) : null;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateToolbar(Menu menu){
        if(mSubtitleFlag){
            setSubtitle();
            menu.findItem(R.id.show_subtitle).setTitle(R.string.hide_subtitle);
        }
        else{
            setSubtitle();
            menu.findItem(R.id.show_subtitle).setTitle(R.string.show_subtitle);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}