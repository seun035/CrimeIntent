package com.oluwaseun.liadi.crimeintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.oluwaseun.liadi.crimeintent.database.CrimeDbSchema.*;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    //private int version = 1;
    public static final int VERSION = 1;
    private static final String DATABASE_NAME = "crime.db";
    public CrimeBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+ CrimeTable.NAME+" ( _id integer primary key autoincrement, "+ CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE+ ","+ CrimeTable.Cols.DATE + ","+ CrimeTable.Cols.SOLVED +")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
