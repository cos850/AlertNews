package com.alertnews.common;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class TemplateLoader {

    private final ResourceLoader resourceLoader;

    public TemplateLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String readTemplateToString(String fileName){
        return new String(readTemplateToBytes(fileName), StandardCharsets.UTF_8);
    }

    public byte[] readTemplateToBytes(String fileName){
        try {
            Resource resource = resourceLoader.getResource("template/" + fileName);
            return Files.readAllBytes(Paths.get(resource.getURI()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
