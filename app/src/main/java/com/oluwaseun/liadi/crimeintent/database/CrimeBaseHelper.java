package com.oluwaseun.liadi.crimeintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    //private int version = 1;
    public static final int VERSION = 1;
    private static final String DATBASE_NAME = "crime.db";
    public CrimeBaseHelper(@Nullable Context context) {
        super(context, DATBASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+ CrimeDbSchema.CrimeTable.NAME+" ( "+CrimeDbSchema.CrimeTable.Cols.UUID + ","
                +CrimeDbSchema.CrimeTable.Cols.TITLE+ ","+CrimeDbSchema.CrimeTable.Cols.DATE + ","+CrimeDbSchema.CrimeTable.Cols.SOLVED +")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
