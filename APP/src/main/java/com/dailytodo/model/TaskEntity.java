package com.dailytodo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rajeev Ranjan on 24-Jun-18.
 */

public class TaskEntity implements Parcelable {
    String userId;
    String taskId;
    String name;
    String desc;
    boolean status;
    long timeInMilliSec;


    public TaskEntity(String userId, String taskId, String name, String desc, boolean status, long timeInMilliSec) {
        this.userId = userId;
        this.taskId = taskId;
        this.name = name;
        this.desc = desc;
        this.status = status;
        this.timeInMilliSec = timeInMilliSec;
    }

    public TaskEntity() {

        this.status = false;

    }

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userID) {
        this.userId = userID;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTimeInMilliSec() {
        return timeInMilliSec;
    }

    public void setTimeInMilliSec(long timeInMilliSec) {
        this.timeInMilliSec = timeInMilliSec;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.taskId);
        dest.writeString(this.name);
        dest.writeString(this.desc);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeLong(this.timeInMilliSec);
    }

    protected TaskEntity(Parcel in) {
        this.userId = in.readString();
        this.taskId = in.readString();
        this.name = in.readString();
        this.desc = in.readString();
        this.status = in.readByte() != 0;
        this.timeInMilliSec = in.readLong();
    }

    public static final Parcelable.Creator<TaskEntity> CREATOR = new Parcelable.Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel source) {
            return new TaskEntity(source);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };
}
