package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.FeedService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
//TODO - add this in app level
public class SocialController {

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    @GetMapping("feed/{userId}")
    public ResponseEntity<User> getFeed(@PathVariable String userId) {
        User user = new User();
        user.setFirstName("Ajshay");
        return ResponseEntity.ok(user);
    }
}
