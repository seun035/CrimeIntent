package com.oluwaseun.liadi.crimeintent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class CrimeFragment extends Fragment {
    private Button mDateButton;
    private EditText mTitleEditText;
    private CheckBox mSolvedCheckBox;
    private Button mSendReportButton;
    public static final String CRIME_ID = "com.oluwaseun.liadi.crimeintent.crime.id";
    public static final int REQUEST_CODE = 10;
    private static final String TAG = "CrimeFragment";

    private Crime mCrime;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mCrime = new Crime();
        UUID crimeId = (UUID)getArguments().getSerializable(CRIME_ID);
        if ( crimeId != null){
            mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime,container,false);
        mDateButton = v.findViewById(R.id.crime_date);
        mTitleEditText = v.findViewById(R.id.crime_title);
        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSendReportButton = v.findViewById(R.id.send_report);

        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTitleEditText.setText(mCrime.getTitle());
        mDateButton.setText(mCrime.getDate().toString());
        mSolvedCheckBox.setChecked(mCrime.isSolved());

        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved( isChecked);
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = DatePickerFragment.newFragment(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_CODE);
                dialog.show(getActivity().getSupportFragmentManager(),"dateDialog");

            }
        });

        mSendReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailReport = new Intent(Intent.ACTION_SEND);
                emailReport.putExtra(Intent.EXTRA_SUBJECT , getString(R.string.crime_report_subject));
                emailReport.putExtra(Intent.EXTRA_TEXT, generateReport());
                emailReport.setType("message/rfc822");
                getActivity().startActivity(emailReport);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (REQUEST_CODE == requestCode){
                Date date = (Date)data.getSerializableExtra("data4");
                Log.d(TAG, "onActivityResult: "+date);
                mCrime.setDate(date);
                mDateButton.setText(mCrime.getDate().toString());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getInstance(getActivity()).updateCrime(mCrime);
    }

    public static Fragment newFragment(UUID crimeId)
    {

        Bundle arg = new Bundle();
        arg.putSerializable(CRIME_ID, crimeId);
        Fragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(arg);
        return crimeFragment;
    }

    private String generateReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        }
        else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        DateFormat dateFormat = new SimpleDateFormat("EEE, MM dd");
        String dateString = dateFormat.format(mCrime.getDate());

        String suspect = mCrime.getSuspect();

        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        }
        else {
            suspect = getString(R.string.crime_report_suspect);
        }

        String report = getString(R.string.crime_report,mCrime.getTitle(),dateString, solvedString, suspect);
        return  report;
    }
}
