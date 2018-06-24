package com.dailytodo.model;

/**
 * Created by Rajeev Ranjan on 24-Jun-18.
 */

public class CommentsEntity {
    String taskId;
    String commentId;
    String comment;

    public CommentsEntity(String taskId, String commentId, String comment) {
        this.taskId = taskId;
        this.commentId = commentId;
        this.comment = comment;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public CommentsEntity() {
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
