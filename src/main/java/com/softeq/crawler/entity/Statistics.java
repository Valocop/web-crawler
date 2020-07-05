package com.softeq.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Statistics {
    private String url;
    private Map<String, Long> tagStatistics;
    private List<String> tags;
}
