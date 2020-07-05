package com.softeq.crawler.reader;

import java.io.IOException;
import java.util.List;

public interface UrlReader {
    List<String> readUrls(String url) throws IOException;

    String readTextFromUrl(String url) throws IOException;
}
