package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Post createPost(CreatePostRequest postRequest, User user) {

        if (postRequest.getGroupId() == null) {
            throw new IllegalArgumentException("Group ID cannot be null.");
        }

        Optional<Group> groupOptional = groupRepository.findById(postRequest.getGroupId());

        if (groupOptional.isEmpty()) {
            throw new IllegalArgumentException("Group not found for the provided Group ID.");
        }

        Group group = groupOptional.get();

        if (!group.getUsers().contains(user)) {
            throw new IllegalArgumentException("User is not part of the group.");
        }

        Post post = new Post();
        post.setUser(user);
        post.setCaption(postRequest.getCaption());
        post.setMedia(postRequest.getMedia());
        post.setCreatedAt(new Date());
        post.setGroup(group);

        group.getPosts().add(post);
        groupRepository.save(group);

        return postRepository.save(post);
    }

    public void likePost(Long postId, User user) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new EntityNotFoundException("Post not found");
        }

        post.get().getLikedBy().add(user);
        postRepository.save(post.get());
    }

    public Comment commentOnPost(Long postId, User user, String commentText) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new EntityNotFoundException("Post not found");
        }

        Comment comment = new Comment();
        comment.setTime(new Date());
        comment.setUser(user);
        comment.setContent(commentText);
        return commentRepository.save(comment);
    }
}
