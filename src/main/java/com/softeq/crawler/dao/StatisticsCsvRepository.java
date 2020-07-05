package com.softeq.crawler.dao;

import com.softeq.crawler.entity.Statistics;

import java.io.IOException;
import java.util.List;

public interface StatisticsCsvRepository {
    void saveToFile(List<Statistics> statisticsList, String filename) throws IOException;
}
