package com.oluwaseun.liadi.crimeintent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class CrimeLab {
    private static CrimeLab ourInstance;

    private List<Crime> mCrimeList;
    static CrimeLab getInstance() {
        if (ourInstance == null){
           ourInstance = new CrimeLab();
        }
        return ourInstance;
    }

    private CrimeLab() {
        mCrimeList = new ArrayList<>();
        for (int i = 0 ; i < 100 ; ++i){
            Crime crime = new Crime();
            crime.setTitle("Crime #"+i);
            if (i%2 == 0){
                crime.setSolved(true);
            }
            mCrimeList.add(crime);
        }
    }

    public List<Crime> getCrimeList() {
        return mCrimeList;
    }

    public Crime getCrime(UUID id){
        for (int i = 0 ; i < mCrimeList.size() ; ++i){
            if (mCrimeList.get(i).getId().equals(id)){
                return  mCrimeList.get(i);
            }
        }
        return null;
    }

    public int getCrimePostion(UUID id){
        for (int i = 0 ; i < mCrimeList.size() ; ++i){
            if (mCrimeList.get(i).getId().equals(id)){
                return  i;
            }
        }
        return 0;
    }
}
