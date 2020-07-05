package com.softeq.crawler.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Config {
    private String rootUrl;
    private int height;
    private long pagesLimit;
    private List<String> tags;
    private String path;
    private String fileName;
    private String topFileName;
    private int topLimit;
}
