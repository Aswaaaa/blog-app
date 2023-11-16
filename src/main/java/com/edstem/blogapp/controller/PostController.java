package com.edstem.blogapp.controller;

import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.model.Post;
import com.edstem.blogapp.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

@RestController
@RequestMapping("/blog/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public PostResponse createPost(@Valid @RequestBody PostRequest request) {
        return this.postService.createPost(request);
    }

    @PostMapping("/pageable")
    public Page<PostResponse> getPostsByPageable(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
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

    @PostMapping("/search")
    public Page<PostResponse> searchAndPaginate(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id,ASC") String sort) {

        Page<PostResponse> postsPage = postService.searchAndPaginate(query, page, size, sort);

        return new PageImpl<>(postsPage.getContent(), PageRequest.of(page, size), postsPage.getTotalElements());
    }


}
