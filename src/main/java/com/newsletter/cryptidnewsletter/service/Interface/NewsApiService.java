package com.newsletter.cryptidnewsletter.service.Interface;


import com.newsletter.cryptidnewsletter.model.newsAPI.Article;
import com.newsletter.cryptidnewsletter.model.newsAPI.NewsApiResponseModel;
import com.newsletter.cryptidnewsletter.model.newsAPI.NewsForNewsLetter;

import java.util.Date;
import java.util.List;

public interface NewsApiService {
    public NewsApiResponseModel getNewsByDate(String cryptidName, Date startDate, Date endDate);

    public List<Article> getNews(String cryptidName);
    public List<NewsForNewsLetter> getLatestTopNews(String topicName, int numberOfNewsArticles);
}
