package com.softeq.crawler.scanner;

import com.softeq.crawler.entity.Statistics;
import com.softeq.crawler.entity.UrlNode;
import com.softeq.crawler.reader.UrlReader;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j
public class StatisticsScannerImpl implements StatisticsScanner {

    @Override
    public List<Statistics> scan(UrlNode urlNode, List<String> tags, UrlReader reader) {
        List<Statistics> statisticsList = new ArrayList<>();
        urlNodeTreeScan(urlNode, statisticsList, tags, reader);
        return statisticsList;
    }

    private void urlNodeTreeScan(UrlNode urlNode, List<Statistics> statisticsList, List<String> tags,
                                 UrlReader reader) {
        try {
            String text = reader.readTextFromUrl(urlNode.getUrl());
            Map<String, Long> tagsStats = tagsScan(text, tags);
            Statistics statistics = new Statistics(urlNode.getUrl(), tagsStats, tags);
            statisticsList.add(statistics);
            log.info("Url was scanned and added to statistics " + urlNode.getUrl());
            urlNode.getChildNodes().forEach(childNode -> urlNodeTreeScan(childNode, statisticsList, tags, reader));
        } catch (IOException e) {
            log.warn("Failed to scan url " + urlNode.getUrl(), e);
        }
    }

    private Map<String, Long> tagsScan(String text, List<String> params) {
        Map<String, Long> statsMap = new LinkedHashMap<>();
        params.forEach(tag -> {
            long count = StringUtils.countMatches(text, tag);
            statsMap.put(tag, count);
        });
        return statsMap;
    }
}
