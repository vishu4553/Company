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
import com.example.ukarsha.hub.model.IndustrialListModel;
import com.example.ukarsha.hub.model.IndustrialVisit1;

import java.util.List;

/**
 * Created by Ukarsha on 24-Apr-19.
 */

public class IndustryListAdapter extends RecyclerView.Adapter<IndustryListAdapter.MyViewHolder> {
    Context context;
    private List<IndustrialVisit1> getService;
    IndustryListAdapter.OnItemClickListener listener;

    public IndustryListAdapter(Context context, List<IndustrialVisit1> getService, OnItemClickListener listener) {
        this.context = context;
        this.getService = getService;
        this.listener = listener;
    }

    @Override
    public IndustryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_industrial1, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(IndustryListAdapter.MyViewHolder holder, final int position) {
        final IndustrialVisit1 service = getService.get(position);
        holder.textCompanyName.setText(service.getName());
        holder.textPostDt.setText(service.getPosted_date());
        holder.textVisitDt.setText(service.getVisit_date());
        //holder.textTraInfo.setText(service.getTraining_info());

        holder.bind(service, listener, String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return getService.size();
    }

    public interface OnItemClickListener {
        void onItemClick(IndustrialVisit1 services, ImageView imageView, String pos);

        void onButtonClick(IndustrialVisit1 service, LinearLayout linearLayout, String s);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textCompanyName, textPostDt, textVisitDt, textTraInfo;
        ImageView image;
        CardView cardView;
        Button buttonDetails, buttonRepost;
        private LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);

            textCompanyName = itemView.findViewById(R.id.cmpnyName);
            textPostDt = itemView.findViewById(R.id.postDt);
            textVisitDt = itemView.findViewById(R.id.visittDt);
          //  textTraInfo = itemView.findViewById(R.id.traInfo);
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

        public void bind(final IndustrialVisit1 service, final OnItemClickListener listener, final String s) {
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