package com.example.demo.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long groupId;
    List<Integer> posts;
    List<Integer> users;

}
