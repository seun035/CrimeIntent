package com.oluwaseun.liadi.crimeintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.oluwaseun.liadi.crimeintent.database.CrimeBaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.oluwaseun.liadi.crimeintent.database.CrimeDbSchema.*;

class CrimeLab {
    private static CrimeLab ourInstance;

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
    }

    public Crime getCrime(UUID id){

       Cursor cursor = db.query(CrimeTable.NAME,null, CrimeTable.Cols.UUID +" =?",
               new String[]{id.toString()},null,null,null);

       int uuidIndex = cursor.getColumnIndex(CrimeTable.Cols.UUID);
       int titleIndex = cursor.getColumnIndex(CrimeTable.Cols.TITLE);
       int dateIndex = cursor.getColumnIndex(CrimeTable.Cols.DATE);
       int solvedIndex = cursor.getColumnIndex(CrimeTable.Cols.SOLVED);
       try {
           if (cursor.getCount() == 0){
               return  null;
           }
           cursor.moveToFirst();
           Crime crime = new Crime(UUID.fromString(cursor.getString(uuidIndex)));
           crime.setDate(new Date(cursor.getLong(dateIndex)));
           crime.setTitle(cursor.getString(titleIndex));
           crime.setSolved(cursor.getInt(solvedIndex) == 1 ? true : false );
           return crime;
       }finally {
           db.close();
       }

    }

    public List<Crime> getCrimes(){
        Cursor cursor = db.query(CrimeTable.NAME,null,null,null,null,null,null);
        int uuidIndex = cursor.getColumnIndex(CrimeTable.Cols.UUID);
        int titleIndex = cursor.getColumnIndex(CrimeTable.Cols.TITLE);
        int dateIndex = cursor.getColumnIndex(CrimeTable.Cols.DATE);
        int solvedIndex = cursor.getColumnIndex(CrimeTable.Cols.SOLVED);

        try{

            if (cursor.getCount() == 0){
                return new ArrayList<>();
            }
            List<Crime> crimes = new ArrayList<>();
            boolean index = cursor.moveToFirst();
            while (index){
                Crime crime = new Crime(UUID.fromString(cursor.getString(uuidIndex)));
                crime.setDate(new Date(cursor.getLong(dateIndex)));
                crime.setTitle(cursor.getString(titleIndex));
                crime.setSolved(cursor.getInt(solvedIndex) == 1 ? true : false );
                crimes.add(crime);
                index = cursor.moveToNext();
            }
            return crimes;
        }finally {
            db.close();
        }

    }

    public void addCrime(Crime crime){
        ContentValues values = createContentValues(crime);
        db.insert(CrimeTable.NAME,null,values);
    }

    public void updateCrime(Crime crime) {
        ContentValues values = createContentValues(crime);
        db.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + "= ?", new String[]{crime.getId().toString()});
    }


    private ContentValues createContentValues(Crime crime){

        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID,crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE,crime.getTitle());
        contentValues.put(CrimeTable.Cols.DATE,crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED,crime.isSolved() ? 1 : 0);
        return contentValues;
    }
}
