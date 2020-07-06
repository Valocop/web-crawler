package com.softeq.crawler.controller;

import com.softeq.crawler.builder.UrlNodeBuilder;
import com.softeq.crawler.builder.UrlNodeBuilderImpl;
import com.softeq.crawler.config.Config;
import com.softeq.crawler.creator.ConfigCreator;
import com.softeq.crawler.creator.Creator;
import com.softeq.crawler.dao.StatisticsCsvRepository;
import com.softeq.crawler.dao.StatisticsCsvRepositoryImpl;
import com.softeq.crawler.entity.Statistics;
import com.softeq.crawler.entity.UrlNode;
import com.softeq.crawler.reader.UrlReader;
import com.softeq.crawler.reader.UrlReaderImpl;
import com.softeq.crawler.scanner.StatisticsScanner;
import com.softeq.crawler.scanner.StatisticsScannerImpl;
import com.softeq.crawler.service.StatisticsService;
import com.softeq.crawler.service.StatisticsServiceImpl;
import com.softeq.crawler.validator.ConfigValidator;
import com.softeq.crawler.validator.ResultValidator;
import com.softeq.crawler.validator.Validator;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Log4j
public class CrawlerController {
    private StatisticsService service;

    public CrawlerController(StatisticsService service) {
        this.service = service;
    }

    public void execute(List<String> params) {
        Creator<Config> configCreator = new ConfigCreator();
        try {
            Config config = configCreator.create(params);
            UrlReader reader = new UrlReaderImpl();
            Validator<Config> configValidator = new ConfigValidator(reader);
            ResultValidator resultValidator = configValidator.validate(config);
            if (resultValidator.isValid()) {
                UrlNodeBuilder nodeBuilder = new UrlNodeBuilderImpl(config, reader);
                UrlNode urlNode = nodeBuilder.build();
                StatisticsScanner scanner = new StatisticsScannerImpl();
                List<Statistics> statisticsList = scanner.scan(urlNode, config.getTags(), reader);
                String filePath = Path.of(config.getPath(), config.getFileName()).toString();
                String topFilePath = Path.of(config.getPath(), config.getTopFileName()).toString();
                service.save(statisticsList, filePath);
                service.sortedSave(statisticsList, config.getTopLimit(), topFilePath);
            } else {
                Object[] exceptions = resultValidator.getExceptionMap().entrySet().toArray();
                log.warn("Config is not valid " + Arrays.toString(exceptions));
            }
        } catch (IOException e) {
            log.warn("Failed to create config", e);
        }
    }

    public static void main(String[] args) {
        StatisticsCsvRepository repository = new StatisticsCsvRepositoryImpl();
        StatisticsService service = new StatisticsServiceImpl(repository);
        CrawlerController controller = new CrawlerController(service);
        controller.execute(Arrays.asList(args));
    }
}
