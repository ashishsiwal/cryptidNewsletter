package com.newsletter.cryptidnewsletter.service.Impl;

import com.newsletter.cryptidnewsletter.model.newsAPI.Article;
import com.newsletter.cryptidnewsletter.model.newsAPI.NewsApiResponseModel;
import com.newsletter.cryptidnewsletter.model.newsAPI.NewsForNewsLetter;
import com.newsletter.cryptidnewsletter.service.Interface.NewsApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static util.ServiceConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsApiServiceImpl implements NewsApiService {

    private final RestTemplate restTemplate;

    @Value("${news.api.baseurl}")
    private String newsApiBaseUrl;

    @Value("${news.api.key}")
    private String newsApiKey;

    @Override
    public List<NewsForNewsLetter> getLatestTopNews(String topicName, int numberOfNewsArticles) {
        List<Article> newsApiResponse = getNews(topicName);
        List<NewsForNewsLetter> newsForNewsLettersList = new ArrayList<>();

        int numberOfArticlesToFetch = Math.min(newsApiResponse.size(), GENERAL_NUMBER_OF_ARTICLES);

        for(int i = 0; i <= numberOfArticlesToFetch; i++){
            Article article = newsApiResponse.get(i);
            NewsForNewsLetter newsForNewsLetter = NewsForNewsLetter.builder()
                    .author(article.getAuthor() != null ? article.getAuthor() : "HIDDEN_AUTHOR")
                    .title(article.getTitle() != null ? article.getTitle() : "About " + topicName)
                    .description(article.getDescription() != null ? article.getDescription() : "No Description")
                    .url(article.getUrl())
                    .imageUrl(article.getUrlToImage())
                    .build();
            newsForNewsLettersList.add(newsForNewsLetter);
        }

            return newsForNewsLettersList;
    }

    @Override
    public List<Article> getNews(String topicName) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(newsApiBaseUrl + NEWS_ENDPOINT_EVERYTHING)
                .queryParam(KEYWORD_OR_PHRASE, topicName)
                .queryParam(LANGUAGE, ENGLISH)
                .queryParam(SORT_BY, PUBLISHED_AT);

        String urlWithQueryParams = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add(NEWS_API_KEY, newsApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<NewsApiResponseModel> newsApiResponse = restTemplate.exchange(
                urlWithQueryParams,
                HttpMethod.GET,
                entity,
                NewsApiResponseModel.class);
        if(newsApiResponse.getStatusCode().is2xxSuccessful()){
            if (Objects.requireNonNull(newsApiResponse.getBody()).getArticles().size() == 0) {
                throw new RuntimeException("No news found for topic : " + topicName + " from NEWS API");
            }
            return newsApiResponse.getBody().getArticles();
        } else {
            log.info("Got error response from NEWS API : " + newsApiResponse.getBody());
            throw new RuntimeException("Got error response from NEWS API" + newsApiResponse.getBody());
        }
    }

    @Override
    public NewsApiResponseModel getNewsByDate(String cryptidName, Date startDate, Date endDate) {
        return null;
    }
}
