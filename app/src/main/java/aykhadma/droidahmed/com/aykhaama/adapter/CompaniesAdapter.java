package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnContactAction;
import aykhadma.droidahmed.com.aykhaama.model.CompaniesModel;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 7/19/2016.
 */
public class CompaniesAdapter extends RecyclerView
        .Adapter<CompaniesAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<CompaniesModel> mDataset;
    OnContactAction onContactAction;
     Activity context;
     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView TvCompanyLocation,TvCompanyEmail,tvCompanyName,TvCompanyPhone,TvCompanyWebsite;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgCompanies);
            TvCompanyLocation = (TextView) itemView.findViewById(R.id.TvCompanyLocation);
            TvCompanyEmail = (TextView) itemView.findViewById(R.id.TvCompanyEmail);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            TvCompanyPhone = (TextView) itemView.findViewById(R.id.TvCompanyPhone);
            TvCompanyWebsite = (TextView) itemView.findViewById(R.id.TvCompanyWebsite);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public CompaniesAdapter(ArrayList<CompaniesModel> myDataset, Activity context, OnContactAction onContactAction) {
        this.mDataset = myDataset;
        this.context = context;
        this.onContactAction = onContactAction;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
       holder.TvCompanyEmail.setText(mDataset.get(position).getEmail());
        holder.tvCompanyName.setText(mDataset.get(position).getName());
        holder.TvCompanyWebsite.setText(mDataset.get(position).getWebsite());
        holder.TvCompanyPhone.setText(mDataset.get(position).getPhone());
        if(!mDataset.get(position).getPicture().equals("")) {
             Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" +
                    mDataset.get(position).getPicture() + "&width="+ 150+"&height="+150).into(holder.img);
        }
        holder.TvCompanyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactAction.onEmailClick(position);
            }
        });

        holder.TvCompanyWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactAction.onWebsiteClick(position);

            }
        });

        holder.TvCompanyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onContactAction.onPhoneClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
