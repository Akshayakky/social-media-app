package com.example.demo.controller;

import com.example.demo.dto.UserRequest;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.FeedService;
import com.example.demo.service.UserService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    @GetMapping("/{userId}/feed")
    public ResponseEntity<Page<Post>> getUserFeed(@PathVariable @NotNull Long userId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching feed for user with ID {}", userId);
        try {
            Page<Post> feed = feedService.getUserFeed(userId, page, size);
            if (feed.isEmpty()) {
                logger.info("No posts found for user with ID {}", userId);
                return ResponseEntity.ok(feed);  // Returning empty feed with 200 OK
            }
            return ResponseEntity.ok(feed);
        } catch (Exception e) {
            logger.error("Error fetching feed for user with ID {}", userId, e);
            return ResponseEntity.status(500).build();  // Internal Server Error
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        logger.info("Creating user with name {}", request.getFirstName());
        try {
            User user = userService.createUser(request);
            return ResponseEntity.status(201).body(user);  // 201 Created
        } catch (Exception e) {
            logger.error("Error creating user with name {}", request.getFirstName(), e);
            return ResponseEntity.status(400).build();  // Bad Request in case of validation error
        }
    }
}
