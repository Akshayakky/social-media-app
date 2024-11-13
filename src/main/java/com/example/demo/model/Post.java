package com.example.demo.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postId;
    List<Integer> media;
    Date createdAt;
    List<Integer> likes;
    List<Integer> comments;
    int ownerId;
    String text;
}
