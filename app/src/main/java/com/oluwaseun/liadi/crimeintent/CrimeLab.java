package com.oluwaseun.liadi.crimeintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.oluwaseun.liadi.crimeintent.database.CrimeBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class CrimeLab {
    private static CrimeLab ourInstance;

    private List<Crime> mCrimeList;
    private Context mContext;

    private SQLiteDatabase db;
    static CrimeLab getInstance(Context context) {
        if (ourInstance == null){
           ourInstance = new CrimeLab(context);
        }
        return ourInstance;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        db = new CrimeBaseHelper(mContext).getWritableDatabase();
        mCrimeList = new ArrayList<>();
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
