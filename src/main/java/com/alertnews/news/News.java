package com.alertnews.news;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Table(name = "NEWS")
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private String region;
    private String summary;
    private String publishDate;
    private String writer;
    private String nation;

    @Lob
    @Column(columnDefinition = "CLOB")
    private byte[] contentHtml;

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
    public void setContentHtml(byte[] contentHtml) {
        this.contentHtml = contentHtml;
    }
}
