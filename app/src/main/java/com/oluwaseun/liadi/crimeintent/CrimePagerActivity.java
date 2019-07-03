package com.oluwaseun.liadi.crimeintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    public static final String CRIME_ID_EXTRA = "com.oluwaseun.liadi.crimeintent.crime.position.extra";
    private UUID mCrimeId;
    private ViewPager mCrimeViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mCrimeViewPager = findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.getInstance(this).getCrimes();
        setCrimePosition();
        FragmentManager fm = getSupportFragmentManager();
        mCrimeViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                UUID crimeId = mCrimes.get(position).getId();
                Fragment fragment = CrimeFragment.newFragment(crimeId);
                return fragment;
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        mCrimeViewPager.setCurrentItem(getCrimePostion(mCrimeId));

    }

    public void setCrimePosition(){
      mCrimeId  = (UUID) getIntent().getSerializableExtra(CRIME_ID_EXTRA);
    }

    public static Intent newCrimeActivityIntent(Context context , UUID crimeId){
        Intent intent = new Intent(context , CrimePagerActivity.class);
        intent.putExtra(CRIME_ID_EXTRA, crimeId);
        return intent;
    }

    private int getCrimePostion(UUID id){
        for (int i = 0 ; i < mCrimes.size() ; ++i){
            if (mCrimes.get(i).getId().equals(id)){
                return  i;
            }
        }
        return 0;
    }
}
