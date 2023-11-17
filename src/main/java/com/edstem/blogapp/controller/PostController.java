package com.edstem.blogapp.controller;

import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.model.Post;
import com.edstem.blogapp.repository.PostRepository;
import com.edstem.blogapp.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/blog/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @PostMapping("/create")
    public PostResponse createPost(@Valid @RequestBody PostRequest request) {
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

    @PostMapping("/pageable")
    public Page<PostResponse> getPostsByPageable(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getPostsByPageable(pageable);
    }

    @PutMapping("/update/{id}")
    public PostResponse updatePostById(
            @Valid @PathVariable Long id, @RequestBody PostRequest request) {
        return postService.updatePostById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
    }

    @GetMapping("/categories/{category}")
    public List<PostResponse> getPostsByCategory(@PathVariable String category) {
        return postService.getPostsByCategory(category);
    }

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/search")
    public List<PostResponse> searchPosts(@RequestParam("query")String query) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return postService.searchPosts(query,sort);

    }


}
