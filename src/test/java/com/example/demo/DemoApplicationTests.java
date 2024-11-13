package com.example.demo;

import com.example.demo.model.CreatePostRequest;
import com.example.demo.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

//	@Test
//	void createPost_shouldReturnCreatedPost_whenValidRequest() throws Exception {
//		CreatePostRequest createPostRequest = new CreatePostRequest();
//		createPostRequest.setCaption("My First Post");
//		createPostRequest.setUserId(1L);
//		createPostRequest.setMedia( /* Mock media list */ );
//
//		Post savedPost = new Post();
//		savedPost.setCaption(createPostRequest.getCaption());
//
//		when(postService.createPost(createPostRequest)).thenReturn(savedPost);
//
//		mockMvc.perform(post("/api/v1/create")
//						.contentType("application/json")
//						.content("{\"caption\":\"My First Post\",\"userId\":1}")
//				)
//				.andExpect(status().isCreated())
//				.andExpect(jsonPath("$.postId").value(1L))
//				.andExpect(jsonPath("$.caption").value("My First Post"));
//
//		verify(postService, times(1)).createPost(createPostRequest);
//	}
}
