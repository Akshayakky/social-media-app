package com.example.demo.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long userId;
    private String commentText;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
