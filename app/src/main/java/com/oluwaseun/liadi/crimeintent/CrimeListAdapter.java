package com.oluwaseun.liadi.crimeintent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.CrimeViewHolder> {

    private Context mContext;
    private List<Crime> mCrimeList;

    public CrimeListAdapter(Context context, List<Crime> crimeList) {
        mContext = context;
        mCrimeList = crimeList;
    }

    @NonNull
    @Override
    public CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_crime, parent, false);
        return new CrimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeViewHolder holder, int position) {
        Crime crime = mCrimeList.get(position);
        holder.bindData(crime);
    }

    @Override
    public int getItemCount() {
        return mCrimeList.size();
    }

    class CrimeViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitleTextView;

        private TextView mDateTextView;

        private Crime mCrime;

        public CrimeViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.list_item_title);
            mDateTextView = itemView.findViewById(R.id.list_item_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(CrimePagerActivity.newCrimeActivityIntent(mContext,mCrime.getId()));
                }
            });
        }

        public void bindData(Crime crime){

            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }
    }
}
