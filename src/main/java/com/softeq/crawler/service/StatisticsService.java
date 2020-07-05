package com.softeq.crawler.service;

import com.softeq.crawler.entity.Statistics;

import java.io.IOException;
import java.util.List;

public interface StatisticsService {
    void save(List<Statistics> statisticsList, String fileName) throws IOException;

    void sortedSave(List<Statistics> statisticsList, int limit, String fileName) throws IOException;
}
