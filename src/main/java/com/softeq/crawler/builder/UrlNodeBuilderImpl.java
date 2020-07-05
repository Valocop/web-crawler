package com.softeq.crawler.builder;

import com.softeq.crawler.config.Config;
import com.softeq.crawler.entity.UrlNode;
import com.softeq.crawler.reader.UrlReader;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j
public class UrlNodeBuilderImpl implements UrlNodeBuilder {
    Set<String> uniqUrlSet = new HashSet<>();
    private final String rootUrl;
    private final int defaultHeight;
    private long pagesLimit;
    private final UrlReader urlElementsReader;

    public UrlNodeBuilderImpl(Config config, UrlReader reader) {
        this.rootUrl = config.getRootUrl();
        this.pagesLimit = config.getPagesLimit();
        this.defaultHeight = config.getHeight();
        this.urlElementsReader = reader;
    }

    @Override
    public UrlNode build() {
        UrlNode rootUrlNode = new UrlNode(rootUrl, 0);
        uniqUrlSet.add(rootUrl);
        buildChildUrlNodes(rootUrlNode);
        return rootUrlNode;
    }

    private void buildChildUrlNodes(UrlNode urlNode) {
        if (urlNode.getHeight() < defaultHeight) {
            try {
                List<String> urlList = urlElementsReader.readUrls(urlNode.getUrl());

                for (String url : urlList) {
                    if (pagesLimit > 1) {
                        if (!uniqUrlSet.contains(url)) {
                            UrlNode childUrlNode = new UrlNode(url, urlNode.getHeight() + 1);
                            urlNode.addChildUrlNode(childUrlNode);
                            uniqUrlSet.add(url);
                            pagesLimit--;
                            log.info("Link was added to UrlNode " + url);
                            buildChildUrlNodes(childUrlNode);
                        } else {
                            log.info("Link already exists " + url);
                        }
                    } else return;
                }
            } catch (IOException e) {
                log.warn("Failed to get document from url " + urlNode.getUrl());
            }
        }
    }
}
