package com.edstem.blogapp.contract.request;

import jdk.jfr.Category;

import java.time.LocalDateTime;
import java.util.List;

public interface PostSummaryRequest {
    Long getId();
    String getTitle();
    LocalDateTime getCreatedTime();
    LocalDateTime getUpdatedTime();
    List<String> getCategories();

}
