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
import com.example.ukarsha.hub.model.CampusListResult;
import com.example.ukarsha.hub.model.Companies;
import com.example.ukarsha.hub.model.UserResult;

import java.util.List;

/**
 * Created by Ukarsha on 24-Apr-19.
 */

public class CampusListAdapter extends RecyclerView.Adapter<CampusListAdapter.MyViewHolder> {
    Context context;
    private List<CampusListResult> getService;
    CampusListAdapter.OnItemClickListener listener;

    public CampusListAdapter(Context context, List<CampusListResult> getService, OnItemClickListener listener) {
        this.context = context;
        this.getService = getService;
        this.listener = listener;
    }

    @Override
    public CampusListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_campus1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CampusListAdapter.MyViewHolder holder, final int position) {
        final CampusListResult service = getService.get(position);
        holder.textCompanyName.setText(service.getName());
        holder.textPostDt.setText(service.getPosted_date());
        holder.textPosition.setText(service.getPosition());
        holder.textWebsite.setText(service.getRegistration_link());

        holder.bind(service, listener, String.valueOf(position));
    }

    /* @Override
     public int getItemCount() {
         return getService.size();
     }
 */
    @Override
    public int getItemCount() {
        if (getService == null) {
            return 0;
        }
        return getService.size();

    }


    public interface OnItemClickListener {
        void onItemClick(CampusListResult services, ImageView imageView, String pos);

        void onButtonClick(CampusListResult service, LinearLayout linearLayout, String s);

        void onButtonClick(int layoutPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textCompanyName, textPostDt, textPosition, textWebsite;
        ImageView image;
        CardView cardView;
        Button buttonDetails, buttonRepost;
        private LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);

            textCompanyName = itemView.findViewById(R.id.cmpnyName);
            textPostDt = itemView.findViewById(R.id.postDt);
            textPosition = itemView.findViewById(R.id.position);
            textWebsite = itemView.findViewById(R.id.website);
            image = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
            buttonDetails = itemView.findViewById(R.id.details);
            buttonRepost = itemView.findViewById(R.id.repost);
            linearLayout = itemView.findViewById(R.id.linear);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
               /* case R.id.repost:
                    listener.onButtonClick(this.getLayoutPosition());
                    break;*/

            }
        }

        public void bind(final CampusListResult service, final OnItemClickListener listener, final String s) {
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