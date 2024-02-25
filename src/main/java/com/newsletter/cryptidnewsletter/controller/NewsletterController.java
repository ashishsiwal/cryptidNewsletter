package com.newsletter.cryptidnewsletter.controller;

import com.newsletter.cryptidnewsletter.model.newsAPI.NewsApiResponseModel;
import com.newsletter.cryptidnewsletter.model.newsAPI.NewsForNewsLetter;
import com.newsletter.cryptidnewsletter.service.Interface.NewsApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("newsletter")
@RequiredArgsConstructor
public class NewsletterController {
    private final NewsApiService newsApiService;

    @GetMapping("/cryptid")
    public ResponseEntity<List<NewsForNewsLetter>> dogmanNewsletter(@RequestParam("topic") String topic) {
        List<NewsForNewsLetter> newsApiResponseModel = newsApiService.getLatestTopNews(topic, 5);
        return ResponseEntity.ok(newsApiResponseModel);
    }
}
