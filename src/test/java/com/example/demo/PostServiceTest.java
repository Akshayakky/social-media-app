package com.example.demo;

import com.example.demo.model.CreatePostRequest;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostService postService;

    @Test
    void createPost_shouldReturnSavedPost() {
        CreatePostRequest request = new CreatePostRequest();
        request.setCaption("Test Caption");

        Post post = new Post();
        post.setPostId(1L);
        post.setCaption(request.getCaption());

        when(postRepository.save(any(Post.class))).thenReturn(post);

        User user = userService.getUser(1L);

        Post createdPost = postService.createPost(request, user);

        assertEquals("Test Caption", createdPost.getCaption());
        assertEquals(1L, createdPost.getPostId());
        verify(postRepository, times(1)).save(any(Post.class));
    }
}
