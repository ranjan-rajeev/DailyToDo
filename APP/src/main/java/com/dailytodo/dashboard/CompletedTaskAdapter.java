package com.dailytodo.dashboard;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dailytodo.R;
import com.dailytodo.model.TaskEntity;

import java.util.List;


public class CompletedTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    DashboardFragment mContext;
    List<TaskEntity> taskEntityList;


    public CompletedTaskAdapter(DashboardFragment context, List<TaskEntity> list) {
        mContext = context;
        taskEntityList = list;
    }

    public class DashboardItemHolder extends RecyclerView.ViewHolder {
        ImageView ivTick, ivDelete;
        TextView tvTitle;

        public DashboardItemHolder(View view) {
            super(view);
            ivTick = (ImageView) view.findViewById(R.id.ivTick);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            ivDelete = view.findViewById(R.id.ivDelete);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_completed_task, parent, false);
        return new DashboardItemHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DashboardItemHolder) {
            final TaskEntity taskEntity = taskEntityList.get(position);
            ((DashboardItemHolder) holder).tvTitle.setPaintFlags(((DashboardItemHolder) holder).tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ((DashboardItemHolder) holder).tvTitle.setText("" + taskEntity.getName());
            ((DashboardItemHolder) holder).ivTick.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    taskEntity.setStatus(false);
                    ((DashboardFragment) mContext).updateTask(taskEntity.getTaskId(), taskEntity);
                    return false;
                }
            });
            ((DashboardItemHolder) holder).ivDelete.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    taskEntity.setStatus(false);
                    ((DashboardFragment) mContext).deleteTask(taskEntity.getTaskId(), taskEntity);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskEntityList.size();
    }


}