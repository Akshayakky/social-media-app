package com.example.demo.controller;

import com.example.demo.dto.CommentRequest;
import com.example.demo.model.Comment;
import com.example.demo.model.CreatePostRequest;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.FeedService;
import com.example.demo.service.GroupService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SocialController {

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    GroupService groupService;

    @GetMapping("feed/{userId}")
    public ResponseEntity<Page<Post>> getUserFeed(@PathVariable Long userId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<Post> feed = feedService.getUserFeed(userId, page, size);
        return ResponseEntity.ok(feed);
    }

    @PostMapping("posts/create")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request) {

        User user = userService.getUser(request.getUserId());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Post createdPost = postService.createPost(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("groups/{groupId}/posts")
    public ResponseEntity<List<Post>> getPosts(@PathVariable Long groupId) {
        List<Post> posts = groupService.posts(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PostMapping("/posts/{postId}/like/{userId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @PathVariable Long userId) {

        User user = userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        postService.likePost(postId, user);
        return ResponseEntity.ok("Post liked successfully");
    }

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<Comment> commentOnPost(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        User user = userService.getUser(commentRequest.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Comment comment = postService.commentOnPost(postId, user, commentRequest.getCommentText());
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

}

