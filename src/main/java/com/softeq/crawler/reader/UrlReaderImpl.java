package com.softeq.crawler.reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UrlReaderImpl implements UrlReader {
    private static final String CSS_QUERY = "a[href~=^((?!#).)*$]";
    private static final String ATTRIBUTE_KEY = "abs:href";

    @Override
    public List<String> readUrls(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements linkElement = document.select(CSS_QUERY);
        return linkElement.stream()
                .map(element -> {
                    return element.attr(ATTRIBUTE_KEY).trim();
                })
                .collect(Collectors.toList());
    }

    @Override
    public String readTextFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return document.text();
    }
}
