package com.edstem.blogapp.contract.response;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDate date;
}
