package com.alertnews.news;

import com.alertnews.api.ApiClient;
import com.alertnews.api.dataportal.DataPortalResponse;
import com.alertnews.api.dataportal.NewsApiClient;
import com.alertnews.api.dataportal.NewsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsService {

    public static final DateTimeFormatter SEARCH_PUBLISH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final NewsRepository newsRepository;
    private final ApiClient<DataPortalResponse> newsApiClient;

    @Autowired
    public NewsService(NewsRepository newsRepository, ApiClient<DataPortalResponse> newsApiClient) {
        this.newsRepository = newsRepository;
        this.newsApiClient = newsApiClient;
    }

    public List<News> fetchNewsForDate(LocalDate date) {
        Map<String, String> params = NewsApiClient.makeParams(100, date);

        DataPortalResponse response = newsApiClient.getWithParameter(params);
        List<News> newsEntities = response.getBody().getItemList().stream()
                .map(NewsItem::toNewsEntity)
                .collect(Collectors.toList());

        return (List<News>) newsRepository.saveAll(newsEntities);
    }

    public News searchNews(Long id) {
        return newsRepository.findById(id).orElseThrow();
    }

    public News getNewsFirst(LocalDate publishDate, String nation) {
        return newsRepository.findFirstByPublishDateAndNation(publishDate.format(SEARCH_PUBLISH_DATE_FORMATTER), nation).orElseThrow();
    }

}