package com.edstem.blogapp.service;

import com.edstem.blogapp.contract.request.ListPostRequest;
import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.ListPostResponse;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.contract.response.PostSummaryResponse;
import com.edstem.blogapp.model.post.Post;
import com.edstem.blogapp.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private ModelMapper modelMapper;
    private PostService postService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository, modelMapper);
    }

    @Test
    void testCreatePost() {

        PostRequest postRequest = createPostRequest();
        Post savedPost = createSavedPost(postRequest);
        PostResponse expectedPostResponse = createExpectedPostResponse(savedPost);

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);
        when(modelMapper.map(savedPost, PostResponse.class)).thenReturn(expectedPostResponse);


        PostResponse actualPostResponse = postService.createPost(postRequest);


        assertEquals(expectedPostResponse, actualPostResponse);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    private PostRequest createPostRequest() {
        return PostRequest.builder()
                .title("Test Title")
                .content("Test Content")
                .categories(Arrays.asList("Category1", "Category2"))
                .codeSnippet("Test Code Snippet")
                .build();
    }

    private Post createSavedPost(PostRequest postRequest) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return Post.builder()
                .id(1L)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .categories(postRequest.getCategories())
                .codeSnippet(postRequest.getCodeSnippet())
                .createdTime(currentDateTime)
                .build();
    }

    private PostResponse createExpectedPostResponse(Post savedPost) {
        return PostResponse.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .categories(savedPost.getCategories())
                .codeSnippet(savedPost.getCodeSnippet())
                .createdTime(savedPost.getCreatedTime())
                .build();
    }


    @Test
    void testUpdatePostById() {

        Long postId = 1L;
        PostRequest postRequest = createPostRequest();
        Post existingPost = createExistingPost(postId);
        Post updatedPost = createUpdatedPost(existingPost, postRequest);
        PostResponse expectedPostResponse = createExpectedPostResponse(updatedPost);

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);
        when(modelMapper.map(updatedPost, PostResponse.class)).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = postService.updatePostById(postId, postRequest);

        assertEquals(expectedPostResponse, actualPostResponse);
    }

    private Post createExistingPost(Long id) {
        return Post.builder()
                .id(id)
                .title("Existing Title")
                .content("Existing Content")
                .categories(Arrays.asList("Existing Category1", "Existing Category2"))
                .codeSnippet("Existing Code Snippet")
                .createdTime(LocalDateTime.now())
                .build();
    }

    private Post createUpdatedPost(Post existingPost, PostRequest postRequest) {
        return Post.builder()
                .id(existingPost.getId())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .categories(postRequest.getCategories())
                .codeSnippet(postRequest.getCodeSnippet())
                .createdTime(existingPost.getCreatedTime())
                .updatedTime(LocalDateTime.now())
                .build();
    }


    @Test
    void testDeletePostById() {
        Long postId = 1L;
        when(postRepository.existsById(postId)).thenReturn(true);

        postService.deletePostById(postId);

        verify(postRepository, times(1)).existsById(postId);
        verify(postRepository, times(1)).deleteById(postId);

    }

    @Test
    void testGetPostByCategory() {
        String category = "Category1";
        List<Post> posts = createPosts();
        List<PostResponse> expectedPostResponses = createPostResponses(posts);

        when(postRepository.findByCategoriesContaining(category)).thenReturn(posts);
        when(modelMapper.map(any(Post.class), eq(PostResponse.class))).thenAnswer(i -> createPostResponse((Post) i.getArguments()[0]));

        List<PostResponse> actualPostResponses = postService.getPostsByCategory(category);

        assertEquals(expectedPostResponses, actualPostResponses);
        verify(postRepository, times(1)).findByCategoriesContaining(category);

    }

    private List<Post> createPosts() {
        return Arrays.asList(
                Post.builder()
                        .id(1L)
                        .title("Post 1")
                        .content("Content 1")
                        .categories(Arrays.asList("Category1", "Category2"))
                        .codeSnippet("Code Snippet 1")
                        .createdTime(LocalDateTime.now())
                        .build(),
                Post.builder()
                        .id(2L)
                        .title("Post 2")
                        .content("Content 2")
                        .categories(Arrays.asList("Category1", "Category3"))
                        .codeSnippet("Code Snippet 2")
                        .createdTime(LocalDateTime.now())
                        .build()
        );
    }

    private List<PostResponse> createPostResponses(List<Post> posts) {
        return posts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .categories(post.getCategories())
                        .codeSnippet(post.getCodeSnippet())
                        .createdTime(post.getCreatedTime())
                        .build())
                .collect(Collectors.toList());
    }

    private PostResponse createPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .categories(post.getCategories())
                .codeSnippet(post.getCodeSnippet())
                .createdTime(post.getCreatedTime())
                .build();
    }

    @Test
    void testGetPostById() {
        Long postId = 1L;
        Post post = createPost(postId);
        PostResponse expectedPostResponse = createPostResponse(post);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(modelMapper.map(post, PostResponse.class)).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = postService.getPostById(postId);

        assertEquals(expectedPostResponse, actualPostResponse);
        verify(postRepository, times(1)).findById(postId);
        verify(modelMapper, times(1)).map(post, PostResponse.class);

    }

    private Post createPost(Long id) {
        return Post.builder()
                .id(id)
                .title("Post Title")
                .content("Post Content")
                .categories(Arrays.asList("Category1", "Category2"))
                .codeSnippet("Code Snippet")
                .createdTime(LocalDateTime.now())
                .build();
    }

    @Test
    void testSearchPosts() {
        String query = "Test Query";
        Sort sort = Sort.by(Sort.Direction.DESC, "title");
        List<Post> posts = createPosts();
        List<PostResponse> expectedPostResponses = createPostResponses(posts);

        when(postRepository.searchPosts(query, sort)).thenReturn(posts);
        when(modelMapper.map(any(Post.class), eq(PostResponse.class))).thenAnswer(i -> createPostResponse((Post) i.getArguments()[0]));

        List<PostResponse> actualPostResponses = postService.searchPosts(query, sort);

        assertEquals(expectedPostResponses, actualPostResponses);
        verify(postRepository, times(1)).searchPosts(query, sort);
        verify(modelMapper, times(posts.size())).map(any(Post.class), eq(PostResponse.class));
    }

    @Test
    void testGetListPostResponse() {
        ListPostRequest request = createListPostRequest();
        List<PostSummaryResponse> expectedPostResponses = createPostSummaryResponses();
        Long totalPosts = (long) expectedPostResponses.size();

        when(postRepository.findAll(any(Pageable.class))).thenReturn(createNewPosts());
        when(modelMapper.map(any(Post.class), eq(PostSummaryResponse.class)))
                .thenAnswer(i -> createPostSummaryResponse((Post) i.getArguments()[0]));
        when(postRepository.count()).thenReturn(totalPosts);

        List<ListPostResponse> actualListPostResponses = postService.getListPostResponse(request);

        assertEquals(1, actualListPostResponses.size());
        ListPostResponse actualListPostResponse = actualListPostResponses.get(0);

        assertEquals(totalPosts, actualListPostResponse.getTotalPosts());
    }

    private ListPostRequest createListPostRequest() {
        return ListPostRequest.builder()
                .pageNumber(0)
                .pageSize(10)
                .build();
    }

    private List<PostSummaryResponse> createPostSummaryResponses() {
        return Arrays.asList(
                PostSummaryResponse.builder()
                        .id(1L)
                        .title("Post 1")
                        .categories(Arrays.asList("Category1", "Category2"))
                        .build()
        );
    }

    private PostSummaryResponse createPostSummaryResponse(Post post) {
        return PostSummaryResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .categories(post.getCategories())
                .build();
    }

    private Page<Post> createNewPosts() {
        List<Post> posts = Arrays.asList(
                Post.builder()
                        .id(1L)
                        .title("Post 1")
                        .content("Content 1")
                        .categories(Arrays.asList("Category1", "Category2"))
                        .codeSnippet("Code Snippet 1")
                        .build()
        );
        return new PageImpl<>(posts);
    }


}
