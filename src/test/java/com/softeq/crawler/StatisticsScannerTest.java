package com.softeq.crawler;

import com.softeq.crawler.entity.Statistics;
import com.softeq.crawler.entity.UrlNode;
import com.softeq.crawler.reader.UrlReader;
import com.softeq.crawler.scanner.StatisticsScanner;
import com.softeq.crawler.scanner.StatisticsScannerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsScannerTest {
    private UrlReader urlReader;

    @Before
    public void init() {
        urlReader = mock(UrlReader.class);
    }

    @Test
    public void shouldScanUrlNodeTree() throws IOException {
        UrlNode urlNode = new UrlNode("https://en.wikipedia.org/wiki/Elon_Musk", 2);
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        StatisticsScanner scanner = new StatisticsScannerImpl();

        when(urlReader.readTextFromUrl(any())).thenReturn(getTestText());

        List<Statistics> statisticsList = scanner.scan(urlNode, tags, urlReader);
        Assert.assertNotNull(statisticsList);
        Map<String, Long> tagStatistics = statisticsList.get(0).getTagStatistics();
        Collection<Long> values = tagStatistics.values();
        values.forEach(aLong -> Assert.assertTrue(aLong > 0));
    }



    private String getTestText() throws IOException {
        String path = Paths.get("src", "test", "resources", "Elon Musk - Wikipedia.html").toString();
        return Files.readString(Paths.get(path));
    }
}
