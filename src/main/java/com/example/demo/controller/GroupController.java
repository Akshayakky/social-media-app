package com.example.demo.controller;

import com.example.demo.dto.GroupRequest;
import com.example.demo.model.CreatePostRequest;
import com.example.demo.model.Group;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.GroupService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social/v1/groups")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    GroupService groupService;

    /**
     * Create a new group.
     * @param request GroupRequest containing the group name
     * @return ResponseEntity with created group
     */
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequest request) {
        logger.info("Creating new group with name: {}", request.getGroupName());
        Group group = groupService.createGroup(request.getGroupName());
        logger.info("Group created successfully with ID: {}", group.getGroupId());
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    /**
     * Create a new post in a group.
     * @param request CreatePostRequest containing post details
     * @param groupId The group ID where the post should be created
     * @return ResponseEntity with created post or error response
     */
    @PostMapping("/{groupId}/posts")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request, @PathVariable Long groupId) {
        logger.info("User with ID {} is creating a post in group with ID {}", request.getUserId(), groupId);

        User user;
        try {
            user = userService.getUser(request.getUserId());
        } catch (RuntimeException e) {
            logger.error("User with ID {} not found", request.getUserId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            Post createdPost = postService.createPost(request, user, groupId);
            logger.info("Post created successfully with ID: {}", createdPost.getPostId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to create post in group {} due to: {}", groupId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get all posts from a specific group.
     * @param groupId The group ID to fetch posts from
     * @return ResponseEntity with the list of posts or 404 if no posts are found
     */
    @GetMapping("/{groupId}/posts")
    public ResponseEntity<List<Post>> getPosts(@PathVariable Long groupId) {
        logger.info("Fetching posts for group with ID {}", groupId);
        List<Post> posts = groupService.posts(groupId);

        if (posts.isEmpty()) {
            logger.warn("No posts found for group with ID {}", groupId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Found {} posts for group with ID {}", posts.size(), groupId);
        return ResponseEntity.ok(posts);
    }
}
