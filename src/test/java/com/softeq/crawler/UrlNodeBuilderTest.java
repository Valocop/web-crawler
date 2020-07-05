package com.softeq.crawler;

import com.softeq.crawler.builder.UrlNodeBuilder;
import com.softeq.crawler.builder.UrlNodeBuilderImpl;
import com.softeq.crawler.config.Config;
import com.softeq.crawler.entity.UrlNode;
import com.softeq.crawler.reader.UrlReader;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlNodeBuilderTest {
    private UrlReader urlReader;

    @Before
    public void init() {
        urlReader = mock(UrlReader.class);
    }

    @Test
    public void shouldBuildUrlNode() throws IOException {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        Config config = Config.builder()
                .rootUrl(getTestUrl())
                .height(2)
                .pagesLimit(10)
                .tags(tags)
                .build();
        UrlNodeBuilder builder = new UrlNodeBuilderImpl(config, urlReader);

        when(urlReader.readUrls(any())).thenReturn(getRandomUrlList(10));

        UrlNode urlNode = builder.build();
        Assert.assertNotNull(urlNode);
        Assert.assertFalse(urlNode.getChildNodes().isEmpty());
    }

    private String getTestUrl() {
        return "https://en.wikipedia.org/wiki/Elon_Musk";
    }

    private List<String> getRandomUrlList(int size) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            stringList.add("https://" + RandomStringUtils.random(10, true, false));
        }
        return stringList;
    }
}
