package com.softeq.crawler.service;

import com.softeq.crawler.dao.StatisticsCsvRepository;
import com.softeq.crawler.entity.Statistics;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Log4j
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsCsvRepository repository;

    public StatisticsServiceImpl(StatisticsCsvRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(List<Statistics> statisticsList, String fileName) throws IOException {
        this.repository.saveToFile(statisticsList, fileName);
    }

    @Override
    public void sortedSave(List<Statistics> statisticsList, int limit, String fileName) throws IOException {
        List<Statistics> statistics = new ArrayList<>(statisticsList);
        Comparator<Statistics> comparingLong = Comparator.comparingLong(stats -> {
            Collection<Long> values = stats.getTagStatistics().values();
            return values.stream()
                    .reduce(0L, Long::sum);
        });
        statistics.sort(comparingLong.reversed());
        if (statistics.size() >= limit) {
            this.repository.saveToFile(statistics.subList(0, limit), fileName);
        } else {
            this.repository.saveToFile(statistics, fileName);
        }
    }
}
