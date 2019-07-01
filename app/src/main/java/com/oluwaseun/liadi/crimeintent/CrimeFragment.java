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


    public static Fragment newFragment(UUID crimeId)
    {

        Bundle arg = new Bundle();
        arg.putSerializable(CRIME_ID, crimeId);
        Fragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(arg);
        return crimeFragment;
    }
}
