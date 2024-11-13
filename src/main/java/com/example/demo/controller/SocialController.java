package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.FeedService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        feedService
    }

    @PostMapping("users")
    public ResponseEntity<User> getFeed(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }
}
