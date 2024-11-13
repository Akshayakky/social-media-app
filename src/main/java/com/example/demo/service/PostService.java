package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Post createPost(CreatePostRequest postRequest, User user, Long groupId) {

        if (groupId == null) {
            logger.error("Group ID cannot be null.");
            throw new IllegalArgumentException("Group ID cannot be null.");
        }

        logger.info("Creating post for user {} in group {}", user.getUserId(), groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if (groupOptional.isEmpty()) {
            logger.error("Group not found for ID {}", groupId);
            throw new IllegalArgumentException("Group not found for the provided Group ID.");
        }

        Group group = groupOptional.get();

        if (!group.getUsers().contains(user)) {
            logger.error("User {} is not part of the group {}", user.getUserId(), groupId);
            throw new IllegalArgumentException("User is not part of the group.");
        }

        Post post = new Post();
        post.setUser(user);
        post.setCaption(postRequest.getCaption());
        post.setMedia(postRequest.getMedia());
        post.setCreatedAt(new Date());
        post.setGroup(group);

        // Save post and update group
        group.getPosts().add(post);
        groupRepository.save(group);
        logger.info("Post created successfully for user {} in group {}", user.getUserId(), groupId);

        Post savedPost = postRepository.save(post);
        logger.info("Post saved with ID {}", savedPost.getPostId());
        return savedPost;
    }

    public void likePost(Long postId, User user) {
        logger.info("User {} is liking post with ID {}", user.getUserId(), postId);
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            logger.error("Post with ID {} not found", postId);
            throw new EntityNotFoundException("Post not found");
        }

        post.get().getLikedBy().add(user);
        postRepository.save(post.get());
        logger.info("Post with ID {} liked by user {}", postId, user.getUserId());
    }

    public Comment commentOnPost(Long postId, User user, String commentText) {
        logger.info("User {} is commenting on post with ID {}", user.getUserId(), postId);
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            logger.error("Post with ID {} not found", postId);
            throw new EntityNotFoundException("Post not found");
        }

        Comment comment = new Comment();
        comment.setTime(new Date());
        comment.setUser(user);
        comment.setContent(commentText);
        Comment savedComment = commentRepository.save(comment);
        logger.info("User {} commented on post with ID {}. Comment ID: {}", user.getUserId(), postId, savedComment.getCommentId());
        return savedComment;
    }
}
