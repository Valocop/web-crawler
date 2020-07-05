package com.softeq.crawler;

import com.softeq.crawler.dao.StatisticsCsvRepository;
import com.softeq.crawler.entity.Statistics;
import com.softeq.crawler.service.StatisticsService;
import com.softeq.crawler.service.StatisticsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {
    private StatisticsCsvRepository repository;
    private StatisticsService service;

    @Captor
    private ArgumentCaptor<List<Statistics>> listStatisticsCaptor;
    @Captor
    private ArgumentCaptor<String> fileNameCaptor;

    @Before
    public void init() {
        repository = mock(StatisticsCsvRepository.class);
        service = new StatisticsServiceImpl(repository);
    }

    @Test
    public void shouldSortAndSaveAllStatisticsList() throws IOException {
        String path = getFileName();
        List<Statistics> statisticsList = getTestData();
        service.sortedSave(statisticsList, statisticsList.size(), path);
        List<Statistics> sortedStatistics = sortByCountTags(statisticsList);

        verify(repository).saveToFile(listStatisticsCaptor.capture(), fileNameCaptor.capture());
        verify(repository, times(1)).saveToFile(any(), any());
        assertThat(fileNameCaptor.getValue(), is(path));
        assertThat(listStatisticsCaptor.getValue().size(), is(sortedStatistics.size()));
        assertThat(listStatisticsCaptor.getValue().get(0), is(sortedStatistics.get(0)));
        assertThat(listStatisticsCaptor.getValue().get(1), is(sortedStatistics.get(1)));
        assertThat(listStatisticsCaptor.getValue().get(2), is(sortedStatistics.get(2)));
        assertThat(listStatisticsCaptor.getValue().get(3), is(sortedStatistics.get(3)));
    }

    @Test
    public void shouldSortAndSaveStatisticsListWithLimit() throws IOException {
        String path = getFileName();
        List<Statistics> statisticsList = getTestData();
        service.sortedSave(statisticsList, 2, path);
        List<Statistics> sortedStatistics = sortByCountTags(statisticsList);

        verify(repository).saveToFile(listStatisticsCaptor.capture(), fileNameCaptor.capture());
        verify(repository, times(1)).saveToFile(any(), any());
        assertThat(fileNameCaptor.getValue(), is(path));
        assertThat(listStatisticsCaptor.getValue().size(), is(2));
        assertThat(listStatisticsCaptor.getValue().get(0), is(sortedStatistics.get(0)));
        assertThat(listStatisticsCaptor.getValue().get(1), is(sortedStatistics.get(1)));
    }

    private String getFileName() {
        String fileName = "test" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("-HH-mm-ss")) + ".csv";
        return Paths.get("src", "test", "resources", fileName).toString();
    }

    private List<Statistics> sortByCountTags(List<Statistics> statisticsList) {
        List<Statistics> sortedStatistics = new ArrayList<>(statisticsList);
        Comparator<Statistics> comparingLong = Comparator.comparingLong(stats -> {
            Collection<Long> values = stats.getTagStatistics().values();
            return values.stream()
                    .reduce(0L, Long::sum);
        });
        sortedStatistics.sort(comparingLong.reversed());
        return sortedStatistics;
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
        Statistics twoStats = new Statistics("https://stackoverflow.com/questions", statsTwo, tags);
        Map<String, Long> statsThree = new LinkedHashMap<>();
        statsThree.put(tags.get(0), 0L);
        statsThree.put(tags.get(1), 0L);
        statsThree.put(tags.get(2), 4L);
        statsThree.put(tags.get(3), 0L);
        Statistics threeStats = new Statistics("https://stackoverflow.com/questions/36289043", statsThree, tags);
        Map<String, Long> statsFour = new LinkedHashMap<>();
        statsFour.put(tags.get(0), 0L);
        statsFour.put(tags.get(1), 0L);
        statsFour.put(tags.get(2), 1L);
        statsFour.put(tags.get(3), 0L);
        Statistics fourStats = new Statistics("https://habr.com/ru/post/444982/", statsFour, tags);
        return Arrays.asList(fourStats, threeStats, twoStats, oneStats);
    }
}
