package com.alertnews.email;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class EmailDTO {
    private String to;
    private String subject;
    private String content;
}
