package com.alertnews.news;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NewsRepositoryTest {

    @Autowired
    public NewsRepository repository;

    private News news;

    @BeforeEach
    void setup(){
        this.news = new News();
        news.setTitle("미국뉴스001");
        news.setUrl("http://sample.com/미국뉴스001");
        news.setWriter("홍길동");
        news.setRegion("북미");
        news.setNation("미국");
        news.setSummary("뉴스 001 엄청난 미국 소식 001!");
        news.setPublishDate("2024.01.03");
        news.setContentHtml("<h1>미국뉴스001</h1><p>대충 엄청난 소식 !!!</p>".getBytes());
    }

    @Test
    void 뉴스_저장_테스트(){
        // when
        News result = repository.save(news);

        // then
        checkSameNews(result);
    }

    @Test
    void 뉴스_ID_검색_테스트(){
        // given
        News saveObj = repository.save(news);

        // when
        News findObj = repository.findById(saveObj.getId()).orElseThrow();

        // then
        checkSameNews(findObj);
    }

    @Test
    void 뉴스_삭제_테스트(){
        // given
        News saveObj = repository.save(news);

        // when
        repository.deleteById(saveObj.getId());

        // then
        assertThat(repository.existsById(saveObj.getId())).isFalse();
    }

    private void checkSameNews(News testObj){
        assertThat(testObj).isNotNull();
        assertThat(testObj.getTitle()).isEqualTo(news.getTitle());
        assertThat(testObj.getRegion()).isEqualTo(news.getRegion());
        assertThat(testObj.getSummary()).isEqualTo(news.getSummary());
        assertThat(testObj.getNation()).isEqualTo(news.getNation());
        assertThat(testObj.getUrl()).isEqualTo(news.getUrl());
        assertThat(testObj.getWriter()).isEqualTo(news.getWriter());
        assertThat(testObj.getContentHtml()).isEqualTo(news.getContentHtml());
        assertThat(testObj.getPublishDate()).isEqualTo(news.getPublishDate());
    }

}