package com.dailytodo.dashboard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import com.dailytodo.model.TaskEntity;

import java.util.List;


public class PendingTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    DashboardFragment mContext;
    List<TaskEntity> taskEntityList;


    public PendingTaskAdapter(DashboardFragment context, List<TaskEntity> list) {
        mContext = context;
        taskEntityList = list;
    }

    public class DashboardItemHolder extends RecyclerView.ViewHolder {
        ImageView ivNotification;
        TextView tvTitle;
        RadioButton rb;
        CardView cvMAin;

        public DashboardItemHolder(View view) {
            super(view);
            ivNotification = (ImageView) view.findViewById(R.id.ivNotification);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            rb = (RadioButton) view.findViewById(R.id.rb);
            cvMAin = (CardView) view.findViewById(R.id.cvMAin);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_pending_task, parent, false);
        return new DashboardItemHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DashboardItemHolder) {
            final TaskEntity taskEntity = taskEntityList.get(position);
            ((DashboardItemHolder) holder).tvTitle.setText("" + taskEntity.getName());

            ((DashboardItemHolder) holder).cvMAin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DashboardFragment) mContext).redirectToDetails(taskEntity);
                }
            });

            ((DashboardItemHolder) holder).rb.setOnTouchListener(new View.OnTouchListener() {
                @Override

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    taskEntity.setStatus(true);
                    ((DashboardFragment) mContext).updateTask(taskEntity.getTaskId(), taskEntity);
                    return false;
                }
            });
            ((DashboardItemHolder) holder).ivNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DashboardFragment) mContext).startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 1000);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskEntityList.size();
    }


}