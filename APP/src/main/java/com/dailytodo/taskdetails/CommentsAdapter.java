package com.dailytodo.taskdetails;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dailytodo.R;
import com.dailytodo.dashboard.DashboardFragment;
import com.dailytodo.model.CommentsEntity;
import com.dailytodo.model.TaskEntity;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<CommentsEntity> commentsEntities;


    public CommentsAdapter(Context context, List<CommentsEntity> list) {
        mContext = context;
        commentsEntities = list;
    }

    public class DashboardItemHolder extends RecyclerView.ViewHolder {
        ImageView ivNotification;
        TextView tvTitle;
        RadioButton rb;
        CardView cvMAin;

        public DashboardItemHolder(View view) {
            super(view);
            //ivNotification = (ImageView) view.findViewById(R.id.ivNotification);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            //rb = (RadioButton) view.findViewById(R.id.rb);
            //cvMAin = (CardView) view.findViewById(R.id.cvMAin);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_comment, parent, false);
        return new DashboardItemHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DashboardItemHolder) {
            final CommentsEntity commentsEntity = commentsEntities.get(position);
            ((DashboardItemHolder) holder).tvTitle.setText("" + commentsEntity.getComment());
        }
    }

    @Override
    public int getItemCount() {
        return commentsEntities.size();
    }


}