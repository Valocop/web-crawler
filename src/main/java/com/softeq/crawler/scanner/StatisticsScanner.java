package com.softeq.crawler.scanner;

import com.softeq.crawler.entity.Statistics;
import com.softeq.crawler.entity.UrlNode;
import com.softeq.crawler.reader.UrlReader;

import java.util.List;

public interface StatisticsScanner {
    List<Statistics> scan(UrlNode urlNode, List<String> tags, UrlReader reader);
}
