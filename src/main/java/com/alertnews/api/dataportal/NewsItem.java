package com.alertnews.api.dataportal;

import com.alertnews.news.News;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NewsItem {
    private String newsTitl;        // 뉴스 타이틀
    private String kotraNewsUrl;    // 뉴스 URL
    private String regn;            // 지역
    private String cntntSumar;      // 요약
    private String newsBdt;         // 뉴스 데이터 (HTML)
    private String othbcDt;         // 등록일???
    private String bbstxSn;         // ???
    private String newsWrterNm;     // 작성자
    private String natn;            // 국가a

    public News toNewsEntity() {
        News news = new News();
        news.setTitle(newsTitl);
        news.setUrl(kotraNewsUrl);
        news.setRegion(regn);
        news.setSummary(cntntSumar);
        news.setContentHtml(newsBdt);
        news.setPublishDate(othbcDt);
        news.setWriter(newsWrterNm);
        news.setNation(natn);
        return news;
    }
}
