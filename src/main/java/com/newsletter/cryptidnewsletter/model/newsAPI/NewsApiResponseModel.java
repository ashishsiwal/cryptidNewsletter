package com.newsletter.cryptidnewsletter.model.newsAPI;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsApiResponseModel {
    private String status;
    private int totalResults;
    private List<Article> articles;
}
