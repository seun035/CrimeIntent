package com.oluwaseun.liadi.crimeintent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class DatePickerFragment extends DialogFragment {
    public static final String TAG = "DialogFragment";

    private static final String CRIME_DATE = "com.oluwaseun.liadi.crimeintent.crime.date";

    private DatePicker mDatePicker;

    private Date mCrimeDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mCrimeDate = (Date) getArguments().getSerializable(CRIME_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCrimeDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker,null,false);
        mDatePicker = view.findViewById(R.id.date_picker);
        mDatePicker.init(year, month, day,null);

       return new AlertDialog.Builder(getActivity()).setTitle("Pick a date")
               .setView(view)
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               int year = mDatePicker.getYear();
               int month = mDatePicker.getMonth();
               int day = mDatePicker.getDayOfMonth();
               Date date = new GregorianCalendar(year,month,day).getTime();
               sendResult(RESULT_OK,date);



           }
       }).create();
    }

    public static DialogFragment newFragment(Date date)
    {

        Bundle arg = new Bundle();
        arg.putSerializable(CRIME_DATE, date);
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(arg);
        return datePickerFragment;
    }

    private void sendResult(int resultCode, Date date){
        if(getTargetFragment() == null){
           return;
        }
        Intent intent = new Intent();
        intent.putExtra("data4",date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
