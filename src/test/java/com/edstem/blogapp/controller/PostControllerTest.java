package com.edstem.blogapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.edstem.blogapp.contract.request.ListPostRequest;
import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.ListPostResponse;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private PostService postService;

    @Test
    @WithMockUser(authorities = "admin:create")
    void testCreatePost() throws Exception {
        PostRequest request = createPostRequest();
        PostResponse expectedResponse = createExpectedResponse();

        when(postService.createPost(any(PostRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/blog/post/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    private PostRequest createPostRequest() {

        List<String> categories = Arrays.asList("Test Category");
        return PostRequest.builder()
                .title("Test Title")
                .content("Test Content")
                .categories(categories)
                .codeSnippet("Test code")
                .build();
    }

    private PostResponse createExpectedResponse() {

        List<String> categories = Arrays.asList("Test Category");
        return PostResponse.builder()
                .id(2L)
                .title("Test Title")
                .content("Test Content")
                .categories(categories)
                .codeSnippet("Test code")
                .build();
    }

    @Test
    @WithMockUser(authorities = "admin:update")
    void testUpdatePostById() throws Exception {
        Long id = 1L;

        PostRequest updateRequest = createUpdateRequest();

        PostResponse expectedResponse = createExpectedResponse();

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

    private PostRequest createUpdateRequest() {
        List<String> categories = Arrays.asList("Test Category");
        return PostRequest.builder()
                .title("Test Title")
                .content("Test Content")
                .categories(categories)
                .codeSnippet("Test code")
                .build();
    }

    @Test
    @WithMockUser(authorities = "admin:delete")
    void testDeletePostById() throws Exception {
        Long id = 1L;

        doNothing().when(postService).deletePostById(id);
        mockMvc.perform(delete("/blog/post/" + id)).andDo(print()).andExpect(status().isOk());
        verify(postService).deletePostById(id);
    }

    @Test
    @WithMockUser(authorities = {"admin:read", "user:read"})
    void testGetPostsByCategory() throws Exception {
        String category = "TestCategory";
        List<PostResponse> expectedResponses = Arrays.asList(createExpectedResponse());

        when(postService.getPostsByCategory(anyString())).thenReturn(expectedResponses);

        mockMvc.perform(
                        get("/blog/post/categories/" + category)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().json(new ObjectMapper().writeValueAsString(expectedResponses)));
    }

    @Test
    @WithMockUser(authorities = {"admin:read", "user:read"})
    void testGetPostById() throws Exception {
        Long id = 1L;
        String category = "Test Category";

        PostResponse expectedResponse = createExpectedResponse();

        when(postService.getPostById(id)).thenReturn(expectedResponse);

        mockMvc.perform(get("/blog/post/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    @WithMockUser(authorities = {"admin:read", "user:read"})
    void testSearchPosts() throws Exception {
        String query = "test";
        List<PostResponse> expectedResponses = Arrays.asList(createExpectedResponse());

        when(postService.searchPosts(anyString(), any(Sort.class))).thenReturn(expectedResponses);

        mockMvc.perform(
                        get("/blog/post/search")
                                .param("query", query)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().json(new ObjectMapper().writeValueAsString(expectedResponses)));
    }

    @Test
    @WithMockUser(authorities = {"admin:read", "user:read"})
    public void testListPosts() throws Exception {
        ListPostRequest request = new ListPostRequest();
        List<ListPostResponse> expectedResponses = Arrays.asList(new ListPostResponse());

        when(postService.getListPostResponse(any(ListPostRequest.class)))
                .thenReturn(expectedResponses);

        mockMvc.perform(
                        post("/blog/post/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(
                        content().json(new ObjectMapper().writeValueAsString(expectedResponses)));
    }
}
