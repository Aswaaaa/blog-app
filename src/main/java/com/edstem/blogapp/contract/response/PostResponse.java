package com.edstem.blogapp.contract.response;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private long id;
    private String title;
    private String content;
    private List<String> categories;
    private LocalDate date;


}
