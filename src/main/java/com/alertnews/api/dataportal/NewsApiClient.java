package com.alertnews.api.dataportal;

import com.alertnews.api.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NewsApiClient implements ApiClient<DataPortalResponse> {

    private final WebClient apiClient;
    private final String endPoint = "https://apis.data.go.kr/B410001/kotra_overseasMarketNews/ovseaMrktNews/ovseaMrktNews";
    private static final DateTimeFormatter API_SEARCH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public NewsApiClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        apiClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString())
                // 테스트 코드이므로 서비스 요청 성공 후 삭제
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> {
                            System.out.println("Request: {" + clientRequest.method() + "} {" + clientRequest.url() + "}");
                            clientRequest.headers().forEach((name, values) ->
                                    values.forEach(value -> System.out.println("{" + name + "} : {" + value + "}")));
                            return Mono.just(clientRequest);
                        }
                ))
                .build();
    }

    @Override
    public DataPortalResponse get() {
        throw new UnsupportedOperationException("파라메터 없는 get 요청 불가");
    }

    @Override
    public DataPortalResponse getWithParameter(Map<String, String> params) {
        HashMap<String, Object> response = apiClient.get()
                .uri(endPoint + parametersToString(params))
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();

        return convertResponse(response);
    }

    public static Map<String, String> makeParams(int numOfRows, LocalDate searchDate){
        return Map.of(
                "serviceKey", "XKy3rvCY4QvPmC6Xpy%2FvxGrJUDnQrG6vfKAGqyqLmlFsH3A%2BerZtn8GxrKTQdsqCuR6uG5yXR65rcaJkReB8Kw%3D%3D",
                "numOfRows", String.valueOf(numOfRows),
                "pageNo", "1",
                "search1", "%EB%AF%B8%EA%B5%AD",
                "search7", searchDate.format(API_SEARCH_DATE_FORMATTER)
        );
    }

    public DataPortalResponse convertResponse(HashMap<String, Object> response) {
        Map<String, Object> responseData = (Map<String, Object>) response.get("response");
        DataPortalResponse dataPortalResponse = new DataPortalResponse();

        dataPortalResponse.setHeader(convertHeader((Map<String, Object>) responseData.get("header")));
        dataPortalResponse.setBody(convertBody((Map<String, Object>) responseData.get("body")));

        return dataPortalResponse;
    }

    private Header convertHeader(Map responseHeader) {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.convertValue(responseHeader, Header.class);
    }

    private Body convertBody(Map<String, Object> responseBody){
        Body body = new Body();
        ObjectMapper mapper = new ObjectMapper();

        body.setPageNo(Integer.valueOf((String) responseBody.get("pageNo")));
        body.setNumOfRows(Integer.valueOf((String) responseBody.get("numOfRows")));
        body.setTotalCnt(Integer.valueOf((String) responseBody.get("totalCnt")));

        List<NewsItem> newsItemList = ((List<Map>)((Map<String, HashMap>) responseBody.get("itemList")).get("item"))
                .stream()
                .map(map-> convertNewsItem(map))
                .collect(Collectors.toList());

        body.setItemList(newsItemList);
        return body;
    }

    private NewsItem convertNewsItem(Map<String, String> responseItem){
        NewsItem newsItem = new NewsItem();
        newsItem.setNewsTitl(responseItem.get("newsTitl"));
        newsItem.setNewsWrterNm(responseItem.get("newsWrterNm"));
        newsItem.setNewsBdt(responseItem.get("newsBdt"));
        newsItem.setKotraNewsUrl(responseItem.get("kotraNewsUrl"));
        newsItem.setNatn(responseItem.get("natn"));
        newsItem.setOthbcDt(responseItem.get("othbcDt"));
        newsItem.setCntntSumar(responseItem.get("cntnSumar"));
        return newsItem;
    }

    private String parametersToString(Map<String, String> parameters) {
        String stringParam = "?";
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            stringParam += param.getKey() + "=" + param.getValue() + "&";
        }

        stringParam = stringParam.substring(0, stringParam.length() - 1);
        return stringParam;
    }
}
