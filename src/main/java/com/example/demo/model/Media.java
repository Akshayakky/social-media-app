package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mediaId;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
    private String mediaUrl;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    enum MediaType {
        IMAGE("IMAGE"),
        VIDEO("VIDEO");

        private final String type;

        MediaType(String type) {
            this.type = type;
        }

        @JsonValue
        public String getType() {
            return type;
        }

        @JsonCreator
        public static MediaType fromValue(String value) {
            for (MediaType type : MediaType.values()) {
                if (type.type.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}
