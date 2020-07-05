package com.softeq.crawler;

import com.softeq.crawler.dao.StatisticsCsvRepository;
import com.softeq.crawler.dao.StatisticsCsvRepositoryImpl;
import com.softeq.crawler.entity.Statistics;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class StatisticsCsvRepositoryTest {

    @Test
    public void shouldWriteCsvFileWithStatistics() throws IOException {
        String fileName = "test" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("-HH-mm-ss")) + ".csv";
        String path = Paths.get("src", "test", "resources", fileName).toString();
        List<Statistics> statisticsList = getTestData();
        StatisticsCsvRepository repository = new StatisticsCsvRepositoryImpl();
        repository.saveToFile(statisticsList, path);
        File file = new File(path);
        Assert.assertTrue(file.isFile());
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionByWriteToIncorrectFile() throws IOException {
        String fileName = Paths.get("").toString();
        List<Statistics> statisticsList = getTestData();
        StatisticsCsvRepository repository = new StatisticsCsvRepositoryImpl();
        repository.saveToFile(statisticsList, fileName);
    }

    private List<Statistics> getTestData() {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        Map<String, Long> statsOne = new LinkedHashMap<>();
        statsOne.put(tags.get(0), 120L);
        statsOne.put(tags.get(1), 0L);
        statsOne.put(tags.get(2), 40L);
        statsOne.put(tags.get(3), 0L);
        Statistics oneStats = new Statistics("https://www.baeldung.com/apache-commons-csv", statsOne, tags);
        Map<String, Long> statsTwo = new LinkedHashMap<>();
        statsTwo.put(tags.get(0), 10L);
        statsTwo.put(tags.get(1), 0L);
        statsTwo.put(tags.get(2), 4L);
        statsTwo.put(tags.get(3), 0L);
        Statistics twoStats = new Statistics("https://stackoverflow.com/questions/36289043", statsTwo, tags);
        return Arrays.asList(oneStats, twoStats);
    }
}
