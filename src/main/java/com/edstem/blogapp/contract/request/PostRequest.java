package com.edstem.blogapp.contract.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Column(columnDefinition = "text")
    private String content;

    private List<String> categories;

    @Column(columnDefinition = "text")
    private String codeSnippet;

    @FutureOrPresent
    private LocalDateTime createdTime;

}
