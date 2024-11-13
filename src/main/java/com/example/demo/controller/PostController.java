package com.example.demo.controller;

import com.example.demo.dto.CommentRequest;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social/v1/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    /**
     * Like a post by a user.
     * @param postId The ID of the post to be liked
     * @param userId The ID of the user liking the post
     * @return ResponseEntity with status and message
     */
    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @PathVariable Long userId) {

        logger.info("User with ID {} is liking the post with ID {}", userId, postId);

        User user;
        try {
            user = userService.getUser(userId);
        } catch (RuntimeException e) {
            logger.error("User with ID {} not found", userId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        try {
            postService.likePost(postId, user);
            logger.info("Post with ID {} liked successfully by user with ID {}", postId, userId);
            return ResponseEntity.ok("Post liked successfully");
        } catch (Exception e) {
            logger.error("Failed to like post with ID {}: {}", postId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to like the post");
        }
    }

    /**
     * Comment on a post by a user.
     * @param postId The ID of the post to be commented on
     * @param commentRequest Request containing the user ID and comment text
     * @return ResponseEntity with created comment or error message
     */
    @PostMapping("/{postId}/comment")
    public ResponseEntity<Comment> commentOnPost(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {

        logger.info("User with ID {} is commenting on post with ID {}", commentRequest.getUserId(), postId);

        User user;
        try {
            user = userService.getUser(commentRequest.getUserId());
        } catch (RuntimeException e) {
            logger.error("User with ID {} not found", commentRequest.getUserId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            Comment comment = postService.commentOnPost(postId, user, commentRequest.getCommentText());
            logger.info("Comment added successfully to post with ID {} by user with ID {}", postId, commentRequest.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (Exception e) {
            logger.error("Failed to add comment on post with ID {}: {}", postId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
