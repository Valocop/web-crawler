package com.softeq.crawler.controller;

import com.softeq.crawler.config.Config;
import com.softeq.crawler.service.StatisticsService;
import lombok.extern.log4j.Log4j;

@Log4j
public class CrawlerController {
    private StatisticsService service;

    public CrawlerController(StatisticsService service) {
        this.service = service;
    }

    public void execute(Config config) {
        if (config != null) {

        } else {
            log.warn("Config is null");
        }
    }
}
