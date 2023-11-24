// package com.edstem.blogapp.service;
//
// import com.edstem.blogapp.contract.request.PostRequest;
// import com.edstem.blogapp.contract.response.PostResponse;
// import com.edstem.blogapp.model.post.Post;
// import com.edstem.blogapp.repository.PostRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;
// import org.modelmapper.ModelMapper;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
//
// import java.time.LocalDate;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
//
// public class PostServiceTest {
//    private PostRepository postRepository;
//    private ModelMapper modelMapper;
//    private PostService postService;
//
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//        postRepository = Mockito.mock(PostRepository.class);
//        modelMapper = new ModelMapper();
//        postService = new PostService(postRepository, modelMapper);
//    }
//
//    @Test
//    void testCreatePost() {
//        List<String> categories = Arrays.asList("Test Category");
//
//        PostRequest request =
//                new PostRequest(
//                        "Test Title", "Test Content", categories, "Test code", LocalDate.now());
//        Post post = new Post();
//        Post savedPost = new Post();
//        PostResponse expectedResponse = new PostResponse();
//
//        when(postRepository.save(any(Post.class))).thenReturn(savedPost);
//
//        PostResponse actualResponse = postService.createPost(request);
//
//        assertEquals(expectedResponse, actualResponse);
//    }
//
//    @Test
//    void testGetAllPosts() {
//        Post post1 = new Post();
//        Post post2 = new Post();
//        List<Post> posts = Arrays.asList(post1, post2);
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Post> page = new PageImpl<>(posts, pageable, posts.size());
//
//        when(postRepository.findAll(pageable)).thenReturn(page);
//
//        Page<Post> actualResponses = postService.getAllPosts(pageable);
//
//        assertEquals(posts.size(), actualResponses.getContent().size());
//    }
//
//    @Test
//    void testUpdatePostById() {
//        Long id = 1L;
//        List<String> categories = Arrays.asList("Test Category");
//
//        Post existingPost =
//                new Post(
//                        id, "Test Title", "Test Content", categories, "Test code",
// LocalDate.now());
//        PostRequest request =
//                new PostRequest(
//                        "Updated Title",
//                        "Updated Content",
//                        categories,
//                        "Updated code",
//                        LocalDate.now());
//        Post updatedPost =
//                new Post(
//                        id, "Test Title", "Test Content", categories, "Test code",
// LocalDate.now());
//        PostResponse expectedResponse = modelMapper.map(updatedPost, PostResponse.class);
//        when(postRepository.findById(id)).thenReturn(Optional.of(existingPost));
//        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);
//
//        PostResponse actualResponse = postService.updatePostById(id, request);
//
//        assertEquals(expectedResponse, actualResponse);
//    }
//
//    @Test
//    void testDeletePostById() {
//        Long id = 1L;
//        List<String> categories = Arrays.asList("Test Category");
//
//        Post existingPost =
//                new Post(
//                        id, "Test Title", "Test Content", categories, "Test code",
// LocalDate.now());
//
//        when(postRepository.existsById(id)).thenReturn(true);
//        postService.deletePostById(id);
//    }
//
//    @Test
//    void testGetPostByCategory() {
//        List<String> categories = Arrays.asList("Test Category1", "Test Category2");
//        String category = "Test Category1"; // Choose one category for testing
//
//        Post post1 =
//                new Post(
//                        1L,
//                        "Test Title1",
//                        "Test Content1",
//                        categories,
//                        "Test code1",
//                        LocalDate.now());
//        Post post2 =
//                new Post(
//                        2L,
//                        "Test Title2",
//                        "Test Content2",
//                        categories,
//                        "Test code2",
//                        LocalDate.now());
//
//        List<Post> posts = Arrays.asList(post1, post2);
//
//        List<PostResponse> expectedResponse =
//                posts.stream()
//                        .map(post -> modelMapper.map(post, PostResponse.class))
//                        .collect(Collectors.toList());
//
//        when(postRepository.findAll()).thenReturn(posts);
//
//        List<PostResponse> actualResponse = postService.getPostsByCategory(category);
//        assertEquals(expectedResponse, actualResponse);
//    }
//
//    @Test
//    void testGetPostById() {
//        Long id = 1L;
//        List<String> categories = Arrays.asList("Test Category");
//
//        Post post =
//                new Post(
//                        id,
//                        "Test Title1",
//                        "Test Content1",
//                        categories,
//                        "Test code1",
//                        LocalDate.now());
//
//        PostResponse expectedResponse = modelMapper.map(post, PostResponse.class);
//
//        when(postRepository.findById(id)).thenReturn(Optional.of(post));
//
//        PostResponse actualResponse = postService.getPostById(id);
//
//        assertEquals(expectedResponse, actualResponse);
//    }
// }
