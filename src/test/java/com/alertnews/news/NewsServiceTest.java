package com.alertnews.news;

import com.alertnews.api.ApiClient;
import com.alertnews.api.dataportal.Body;
import com.alertnews.api.dataportal.DataPortalResponse;
import com.alertnews.api.dataportal.Header;
import com.alertnews.api.dataportal.NewsItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doReturn;

class NewsServiceTest {

    private NewsService newsService;

    @Mock
    private NewsRepository repository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        ApiClient newsApiClient = new TestNewsApiClient();
        this.newsService = new NewsService(repository, newsApiClient);
    }

    @Test
    void 뉴스_적재_테스트() {
        // given
        News news = new News();
        news.setId(0L);
        List<News> testData = List.of(news);
        doReturn(testData).when(repository).saveAll(testData);

        // when
        List<News> fetchData = newsService.fetchNewsForDate(LocalDate.now().minusDays(1));

        // then
        Assertions.assertThat(fetchData.size()).isEqualTo(1);
        Assertions.assertThat(fetchData.get(0).getId()).isEqualTo(0L);
    }


    static class TestNewsApiClient implements ApiClient<DataPortalResponse> {

        @Override
        public DataPortalResponse get() {
            throw new UnsupportedOperationException("Not Supported");
        }

        @Override
        public DataPortalResponse getWithParameter(Map params) {
            DataPortalResponse response = new DataPortalResponse();
            Header header = new Header();
            Body body = new Body();
            header.setResultCode("00");
            body.setItemList(List.of(new NewsItem()));

            response.setHeader(header);
            response.setBody(body);

            return response;
        }
    }

}