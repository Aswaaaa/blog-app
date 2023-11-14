package com.edstem.blogapp.contract.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Column(columnDefinition = "text")
    private String content;

    private List<String> categories;

    @Column(columnDefinition = "text")
    private String codeSnippet;

    @FutureOrPresent private LocalDate date;
}
