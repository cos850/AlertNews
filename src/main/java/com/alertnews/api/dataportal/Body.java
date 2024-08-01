package com.alertnews.api.dataportal;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Body {
    private int totalCnt;
    private int pageNo;
    private int numOfRows;
    private List<NewsItem> itemList;
}
