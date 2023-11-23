package com.edstem.blogapp.service;

import com.edstem.blogapp.contract.request.ListPostRequest;
import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.request.PostSummaryRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.exception.EntityNotFoundException;
import com.edstem.blogapp.model.post.Post;
import com.edstem.blogapp.model.post.PostStatus;
import com.edstem.blogapp.repository.PostRepository;
import com.edstem.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
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
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostResponse createPost(PostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .categories(request.getCategories())
                .codeSnippet(request.getCodeSnippet())
                .createdTime(LocalDateTime.now())
                .status(PostStatus.ACTIVE)
                .build();
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostResponse.class);
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

    public List<PostSummaryRequest> listPosts(ListPostRequest request) {
        Pageable page = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by("id").descending());

        log.info("Request {} for list posts is ", request);
        List<PostSummaryRequest> posts = postRepository.findAllByStatusNot(PostStatus.INACTIVE, page)
                .stream()
                .map(post -> modelMapper.map(post, PostSummaryRequest.class))
                .collect(Collectors.toList());

        log.info("Fetched {} posts", posts.size());
        return posts;
    }

    public Long postsCount(ListPostRequest request) {
        log.info("Request {} for list post is ", request);
         Long postCount = postRepository.countByStatusNot(PostStatus.INACTIVE);

        log.info("Fetched all posts - count {}", postCount);
        return postCount;

    }
}
