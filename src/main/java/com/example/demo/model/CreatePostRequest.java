package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class CreatePostRequest {
    private String caption;
    private Long userId;
    private List<Media> media;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}
