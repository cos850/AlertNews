package com.alertnews.api;

import java.util.Map;

public interface ApiClient<R> {
    public R get();
    public R getWithParameter(Map<String, String> params);
}
