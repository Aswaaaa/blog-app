package com.edstem.blogapp.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private long id;
    private String title;
    private String content;
    private List<String> categories;
    private String codeSnippet;
    private LocalDateTime createdTime;
}
