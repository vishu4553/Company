package com.example.ukarsha.hub.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.model.JobPosting1;

import java.util.List;

/**
 * Created by Ukarsha on 24-Apr-19.
 */

public class JobPostListAdapter extends RecyclerView.Adapter<JobPostListAdapter.MyViewHolder> {
    Context context;
    private List<JobPosting1> getService;
    JobPostListAdapter.OnItemClickListener listener;

    public JobPostListAdapter(Context context, List<JobPosting1> getService, OnItemClickListener listener) {
        this.context = context;
        this.getService = getService;
        this.listener = listener;
    }

    @Override
    public JobPostListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_jobpst1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobPostListAdapter.MyViewHolder holder, final int position) {
        final JobPosting1 service = getService.get(position);
        holder.textCompanyName.setText(service.getName());
        holder.textPostDt.setText(service.getPosted_date());
        holder.textJobTit.setText(service.getJob_title());
        holder.textTechnology.setText(service.getTechnology());
        holder.textVacancy.setText(service.getVacancies());
        //holder.textType.setText(service.getType());

        holder.bind(service, listener, String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return getService.size();
    }

    public interface OnItemClickListener {
        void onItemClick(JobPosting1 services, ImageView imageView, String pos);

        void onButtonClick(JobPosting1 service, LinearLayout linearLayout, String s);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textCompanyName, textPostDt, textJobTit, textTechnology, textVacancy, textType;
        ImageView image;
        CardView cardView;
        Button buttonDetails, buttonRepost;
        private LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);

            textCompanyName = itemView.findViewById(R.id.cmpnyName);
            textPostDt = itemView.findViewById(R.id.postDt);
            textJobTit = itemView.findViewById(R.id.jobTit);
            textTechnology = itemView.findViewById(R.id.tech);
            textVacancy = itemView.findViewById(R.id.vacancy);
            //textType = itemView.findViewById(R.id.type);
            image = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
            buttonDetails = itemView.findViewById(R.id.details);
            buttonRepost = itemView.findViewById(R.id.repost);
            linearLayout = itemView.findViewById(R.id.linear);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }

        public void bind(final JobPosting1 service, final OnItemClickListener listener, final String s) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(service, image, s);
                }
            });

            buttonDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonClick(service, linearLayout, s);
                }
            });

            buttonRepost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonClick(service, linearLayout, s);
                }
            });

        }
    }
}