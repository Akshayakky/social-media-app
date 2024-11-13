package com.example.demo.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

//@Entity
//@Table(name = "Media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mediaId;
    MediaType mediaType;
    String mediaUrl;

    enum MediaType {
        IMAGE,
        VIDEO
    }
}
