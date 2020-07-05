package com.softeq.crawler.dao;

import com.softeq.crawler.entity.Statistics;
import lombok.extern.log4j.Log4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
public class StatisticsCsvRepositoryImpl implements StatisticsCsvRepository {

    @Override
    public void saveToFile(List<Statistics> statisticsList, String filename) throws IOException {
        String[] header = new String[]{""};
        if (!statisticsList.isEmpty()) {
            Statistics statistics = statisticsList.get(0);
            List<String> tags = new ArrayList<>(statistics.getTags());
            tags.add(0, "url");
            header = tags.toArray(String[]::new);
        }
        FileWriter out = new FileWriter(filename);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(header))) {

            List<List<String>> rowsList = statisticsList.stream()
                    .map(statistics -> {
                        List<String> list = new ArrayList<>();
                        list.add(statistics.getUrl());
                        List<String> valuesList = statistics.getTagStatistics().values().stream()
                                .map(String::valueOf)
                                .collect(Collectors.toList());
                        list.addAll(valuesList);
                        return list;
                    })
                    .collect(Collectors.toList());
            printer.printRecords(rowsList);
            log.info("Statistics was added to file " + filename);
        }
    }
}
