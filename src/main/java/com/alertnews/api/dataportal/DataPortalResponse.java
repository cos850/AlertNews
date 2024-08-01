package com.alertnews.api.dataportal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DataPortalResponse {
    private Header header;
    private Body body;
}
