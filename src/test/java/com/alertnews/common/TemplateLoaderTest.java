package com.alertnews.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

class TemplateLoaderTest {

    private TemplateLoader templateLoader = new TemplateLoader(new DefaultResourceLoader(ClassLoader.getSystemClassLoader()));

    @Test
    void getTemplateFile(){
        // given
        String templateFile = "email-template.html";

        // when
        String fileContents = templateLoader.readTemplateToString(templateFile);

        // then
        Assertions.assertThat(fileContents).isNotBlank();
    }

}