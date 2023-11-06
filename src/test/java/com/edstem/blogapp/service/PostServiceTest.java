package com.edstem.blogapp.service;

import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.model.Post;
import com.edstem.blogapp.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class PostServiceTest {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private PostService postService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        postRepository = Mockito.mock(PostRepository.class);
        modelMapper = new ModelMapper();
        postService = new PostService(postRepository, modelMapper);
    }

    @Test
    void testCreatePost() {
        List<String> categories = Arrays.asList("Test Category");

        PostRequest request = new PostRequest("Test Title", "Test Content",categories , LocalDate.now());
        Post post = new Post();
        Post savedPost = new Post();
        PostResponse expectedResponse = new PostResponse();


        when(postRepository.save(any(Post.class))).thenReturn(savedPost);


        PostResponse actualResponse = postService.createPost(request);

        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void testGetAllPosts() {
        Post post1 = new Post();
        Post post2 = new Post();
        List<Post> posts = Arrays.asList(post1, post2);

        when(postRepository.findAll()).thenReturn(posts);

        List<PostResponse> expectedResponses = Arrays.asList(new PostResponse(), new PostResponse());

        List<Post> actualResponses = postService.getAllPosts();

        assertEquals(expectedResponses.size(), actualResponses.size());
    }

    @Test
    void testUpdatePostById() {
        Long id = 1L;
        List<String> categories = Arrays.asList("Test Category");



        Post existingPost = new Post(id, "Test Title", "Test Content", categories, LocalDate.now());
        PostRequest request = new PostRequest("Updated Title", "Updated Content", categories, LocalDate.now());
        Post updatedPost = new Post(id, "Test Title", "Test Content", categories, LocalDate.now());
        PostResponse expectedResponse = modelMapper.map(updatedPost, PostResponse.class);
        when(postRepository.findById(id)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        PostResponse actualResponse = postService.updatePostById(id, request);

        assertEquals(expectedResponse, actualResponse);

    }
    @Test
    void testDeletePostById() {
        Long id = 1L;
        List<String> categories = Arrays.asList("Test Category");

        Post existingPost = new Post(id, "Test Title", "Test Content", categories, LocalDate.now());

        when(postRepository.existsById(id)).thenReturn(true);
        postService.deletePostById(id);

    }
    @Test
    void testGetPostByCategory() {
        List<String> categories = Arrays.asList("Test Category1", "Test Category2");
        String category = "Test Category1"; // Choose one category for testing

        Post post1 = new Post(1L, "Test Title1", "Test Content1", categories, LocalDate.now());
        Post post2 = new Post(2L, "Test Title2", "Test Content2", categories, LocalDate.now());

        List<Post> posts = Arrays.asList(post1, post2);

        List<PostResponse> expectedResponse = posts.stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());

        when(postRepository.findAll()).thenReturn(posts);

        List<PostResponse> actualResponse = postService.getPostsByCategory(category);
        assertEquals(expectedResponse, actualResponse);
    }


}
