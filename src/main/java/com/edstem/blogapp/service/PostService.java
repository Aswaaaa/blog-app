package com.edstem.blogapp.service;

import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.request.PostSummaryRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.exception.EntityNotFoundException;
import com.edstem.blogapp.model.Post;
import com.edstem.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostResponse createPost(PostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .categories(request.getCategories())
                .codeSnippet(request.getCodeSnippet())
                .createdTime(LocalDateTime.now())
                .build();
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostResponse.class);
    }


    public Page<PostResponse> getPostsByPageable(Pageable pageable) {
        Pageable sortedByTimestampDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdTime").descending());
//        Page<Post> postLists = postRepository.findAll(sortedByTimestampDesc);
        Page<PostSummaryRequest> postSummaries = postRepository.findPostsSummary(pageable);
        return postSummaries.map(post -> modelMapper.map(post, PostResponse.class));
    }


    public PostResponse updatePostById(Long id, PostRequest request) {
        Post post =
                postRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Post ", id));

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
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
        List<Post> posts =
                postRepository.findAll().stream()
                        .filter(post -> post.getCategories().contains(category))
                        .collect(Collectors.toList());


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
        Sort defaultSort = Sort.by(Sort.Direction.DESC, "id");
        List<Post> responses = postRepository.searchPosts(query, sort);

        if (responses.isEmpty()) {
            throw new EntityNotFoundException("No posts found for the given query: " + query);
        }

        return responses.stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
    }

}
