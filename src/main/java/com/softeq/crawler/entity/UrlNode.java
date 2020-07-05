package com.softeq.crawler.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UrlNode {
    private final List<UrlNode> childNodes = new ArrayList<>();
    private String url;
    private int height;

    public UrlNode(String url, int height) {
        this.url = url;
        this.height = height;
    }

    public void addChildUrlNode(UrlNode urlNode) {
        this.childNodes.add(urlNode);
    }
}
