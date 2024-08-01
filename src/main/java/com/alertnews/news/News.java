package com.alertnews.news;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class News {
    @Id
    private Long id;
    private String title;
    private String url;
    private String region;
    private String summary;
    private String publishDate;
    private String writer;
    private String nation;
    private String contentHtml;

    public News() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }
}
