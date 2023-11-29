package com.edstem.blogapp.service;

import com.edstem.blogapp.contract.request.ListPostRequest;
import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.ListPostResponse;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.contract.response.PostSummaryResponse;
import com.edstem.blogapp.exception.EntityNotFoundException;
import com.edstem.blogapp.model.post.Post;
import com.edstem.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostResponse createPost(PostRequest request) {
        Post post =
                Post.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .categories(request.getCategories())
                        .codeSnippet(request.getCodeSnippet())
                        .createdTime(LocalDateTime.now())
                        .build();
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostResponse.class);
    }

    public PostResponse updatePostById(Long id, PostRequest request) {
        Post post =
                postRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Post ", id));

        modelMapper.map(request, post);
        post.setUpdatedTime(LocalDateTime.now());
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostResponse.class);
    }

    public void deletePostById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post ", id);
        }
        postRepository.deleteById(id);
    }

    public List<PostResponse> getPostsByCategory(String category) {
        String lowerCaseCategory = category.toLowerCase();

        List<Post> posts =
                postRepository.findByCategoriesContaining(category).stream()
                        .filter(
                                post ->
                                        post.getCategories().stream()
                                                .map(String::toLowerCase)
                                                .toList()
                                                .contains(lowerCaseCategory))
                        .toList();

        if (posts.isEmpty()) {
            throw new EntityNotFoundException("No posts found for category: " + category);
        }

        return posts.stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id) {
        Post post =
                postRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Post" + id));

        return modelMapper.map(post, PostResponse.class);
    }

    public List<PostResponse> searchPosts(String query, Sort sort) {
        List<Post> responses = postRepository.searchPosts(query, sort);

        if (responses.isEmpty()) {
            throw new EntityNotFoundException("No posts found for the given query: " + query);
        }

        return responses.stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
    }

    public List<ListPostResponse> getListPostResponse(ListPostRequest request) {
        List<PostSummaryResponse> posts = listPosts(request);
        Long totalPosts = countPosts();

        return List.of(ListPostResponse.builder()
                .posts(posts)
                .totalPosts(totalPosts)
                .build());
    }

    private List<PostSummaryResponse> listPosts(ListPostRequest request) {
        Pageable page = PageRequest.of(request.getPageNumber(),
                request.getPageSize(), Sort.by(Sort.Direction.DESC, "updatedTime", "createdTime"));

        List<PostSummaryResponse> posts =
                postRepository.findAll(page).stream()
                        .map(post -> modelMapper.map(post, PostSummaryResponse.class))
                        .collect(Collectors.toList());

        return posts;
    }

    private Long countPosts() {
        return postRepository.count();
    }
}
