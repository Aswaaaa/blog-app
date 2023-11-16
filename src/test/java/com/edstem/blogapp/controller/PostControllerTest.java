package com.edstem.blogapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.model.Post;
import com.edstem.blogapp.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private PostService postService;
    private List<String> categories;

    @Autowired private ModelMapper modelMapper;

    @Test
    void testCreatePost() throws Exception {
        List<String> categories = Arrays.asList("Test Category");

        PostRequest request =
                new PostRequest("Test Title", "Test Content", categories, "Test code", null);
        PostResponse expectedResponse =
                new PostResponse(2L, "Test Title", "Test Content", categories, "Test code", null);

        when(postService.createPost(any(PostRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/blog/post/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testGetAllPosts() throws Exception {

        List<String> categories = Arrays.asList("Test Category");
        List<Post> posts = new ArrayList<>();

        posts.add(new Post(1L, "Test Title", "Test Content", categories, "Test code", null));
        posts.add(new Post(2L, "Test Title", "Test Content", categories, "Test code", null));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> page = new PageImpl<>(posts, pageable, posts.size());

        when(postService.getAllPosts(pageable)).thenReturn(page);

        mockMvc.perform(post("/blog/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pageable)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(page.getContent())));
    }

    @Test
    void testUpdatePostById() throws Exception {
        Long id = 1L;
        List<String> categories = Arrays.asList("Test Category");

        PostRequest updateRequest =
                new PostRequest(
                        "Updated Title", "Updated Content", categories, "Updated code", null);

        PostResponse expectedResponse =
                new PostResponse(id, "Test Title", "Test Content", categories, "Test code", null);

        when(postService.updatePostById(eq(id), any(updateRequest.getClass())))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        put("/blog/post/update/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testDeletePostById() throws Exception {
        Long id = 1L;

        doNothing().when(postService).deletePostById(id);

        mockMvc.perform(delete("/blog/post/" + id)).andDo(print()).andExpect(status().isOk());

        verify(postService).deletePostById(id);
    }

    @Test
    void testGetPostsByCategory() throws Exception {
        String category = "Test Category";

        List<PostResponse> expectedResponse =
                Arrays.asList(
                        new PostResponse(
                                1L,
                                "Test Title 1",
                                "Test Content 1",
                                categories,
                                "Test code 1",
                                null),
                        new PostResponse(
                                2L,
                                "Test Title 2",
                                "Test Content 2",
                                categories,
                                "Test code 2",
                                null));

        when(postService.getPostsByCategory(eq(category))).thenReturn(expectedResponse);

        mockMvc.perform(
                        get("/blog/post/categories/" + category)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));

        verify(postService).getPostsByCategory(category);
    }

    @Test
    void testGetPostById() throws Exception {
        Long id = 1L;
        String category = "Test Category";

        PostResponse postResponse =
                new PostResponse(
                        1L, "Test Title 1", "Test Content 1", categories, "Test code 1", null);

        when(postService.getPostById(id)).thenReturn(postResponse);

        mockMvc.perform(get("/blog/post/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(postResponse)));
    }
}
