package com.newsletter.cryptidnewsletter.model.newsAPI;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsForNewsLetter {
    private String author;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
}
