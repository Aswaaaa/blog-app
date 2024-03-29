package com.edstem.blogapp.controller;

import com.edstem.blogapp.contract.request.ListPostRequest;
import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.ListPostResponse;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public PostResponse createPost(@Valid @RequestBody PostRequest request) {
        return postService.createPost(request);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public PostResponse updatePostById(
            @Valid @PathVariable Long id, @RequestBody PostRequest request) {
        return postService.updatePostById(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
    }

    @GetMapping("/categories/{category}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public List<PostResponse> getPostsByCategory(@PathVariable String category) {
        return postService.getPostsByCategory(category);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public PostResponse getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public List<PostResponse> searchPosts(@RequestParam("query") String query) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return postService.searchPosts(query, sort);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public List<ListPostResponse> listPosts(@RequestBody ListPostRequest request) {
        return postService.getListPostResponse(request);
    }
}
