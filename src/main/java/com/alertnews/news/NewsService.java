package com.alertnews.news;

import com.alertnews.api.ApiClient;
import com.alertnews.api.dataportal.DataPortalResponse;
import com.alertnews.api.dataportal.NewsApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final ApiClient<DataPortalResponse> newsApiClient;

    @Autowired
    public NewsService(NewsRepository newsRepository, ApiClient<DataPortalResponse> newsApiClient) {
        this.newsRepository = newsRepository;
        this.newsApiClient = newsApiClient;
    }

    public List<News> fetchYesterdayNews() {
        Map<String, String> params = NewsApiClient.makeParams(100, LocalDate.now().minusDays(1));

        DataPortalResponse response = newsApiClient.getWithParameter(params);
        List<News> newsEntities = response.getBody().getItemList().stream()
                .map(newsItem -> newsItem.toNewsEntity())
                .collect(Collectors.toList());

        return (List<News>) newsRepository.saveAll(newsEntities);
    }

    public News searchNews(Long id) {
        return newsRepository.findById(id).orElseThrow();
    }

}