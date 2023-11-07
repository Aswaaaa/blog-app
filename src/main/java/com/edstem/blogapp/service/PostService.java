package com.edstem.blogapp.service;

import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.exception.EntityNotFoundException;
import com.edstem.blogapp.model.Post;
import com.edstem.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostResponse createPost(PostRequest request) {
        Post post = modelMapper.map(request, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostResponse.class);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public PostResponse updatePostById(Long id, PostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post ",id));
        modelMapper.map(request, post);
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost,PostResponse.class);
    }

    public void deletePostById(Long id)  {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post ", id);
        }
        postRepository.deleteById(id);
    }

    public List<PostResponse> getPostsByCategory(String category) {
        List<Post> posts = postRepository.findAll().stream()
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
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post" + id));

        return modelMapper.map(post, PostResponse.class);
    }
}
