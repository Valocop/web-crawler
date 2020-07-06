package com.softeq.crawler;

import com.softeq.crawler.config.Config;
import com.softeq.crawler.reader.UrlReader;
import com.softeq.crawler.validator.ConfigValidator;
import com.softeq.crawler.validator.ResultValidator;
import com.softeq.crawler.validator.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ConfigValidatorTest {
    private UrlReader urlReader;
    private Validator<Config> validator;

    @Before
    public void init() {
        urlReader = mock(UrlReader.class);
        validator = new ConfigValidator(urlReader);
    }

    @Test
    public void shouldValidateValidConfig() throws IOException {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        String path = Paths.get("src", "test", "resources").toString();
        Config config = Config.builder()
                .rootUrl("https://www.baeldung.com/apache-commons-csv")
                .tags(tags)
                .path(path)
                .fileName("fileName.txt")
                .topFileName("topFileName.txt")
                .pagesLimit(1000)
                .height(10)
                .topLimit(5)
                .build();

        when(urlReader.readUrls(any())).thenReturn(any());

        ResultValidator resultValidator = validator.validate(config);
        Assert.assertTrue(resultValidator.isValid());
    }

    @Test
    public void shouldValidateConfigWithNotValidUrl() throws IOException {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        String path = Paths.get("src", "test", "resources").toString();
        Config config = Config.builder()
                .rootUrl("https://www.baeldung.com/apache-commons-csv")
                .tags(tags)
                .path(path)
                .fileName("fileName.txt")
                .topFileName("topFileName.txt")
                .pagesLimit(1000)
                .height(10)
                .topLimit(5)
                .build();

        when(urlReader.readUrls(any())).thenThrow(IOException.class);

        ResultValidator resultValidator = validator.validate(config);
        Assert.assertFalse(resultValidator.isValid());
        Assert.assertEquals(resultValidator.getExceptionMap().size(), 1);
        Assert.assertTrue(resultValidator.getExceptionMap().containsKey("Url"));
    }

    @Test
    public void shouldValidateConfigWithNotValidTags() throws IOException {
        List<String> tags = new ArrayList<>();
        String path = Paths.get("src", "test", "resources").toString();
        Config config = Config.builder()
                .rootUrl("https://www.baeldung.com/apache-commons-csv")
                .tags(tags)
                .path(path)
                .fileName("fileName.txt")
                .topFileName("topFileName.txt")
                .pagesLimit(1000)
                .height(10)
                .topLimit(5)
                .build();

        when(urlReader.readUrls(any())).thenReturn(any());

        ResultValidator resultValidator = validator.validate(config);
        Assert.assertFalse(resultValidator.isValid());
        Assert.assertEquals(resultValidator.getExceptionMap().size(), 1);
        Assert.assertTrue(resultValidator.getExceptionMap().containsKey("Tags"));
    }

    @Test
    public void shouldValidateConfigWithNotValidFilePath() throws IOException {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        String path = "Path";
        Config config = Config.builder()
                .rootUrl("https://www.baeldung.com/apache-commons-csv")
                .tags(tags)
                .path(path)
                .fileName("fileName.txt")
                .topFileName("topFileName.txt")
                .pagesLimit(1000)
                .height(10)
                .topLimit(5)
                .build();

        when(urlReader.readUrls(any())).thenReturn(any());

        ResultValidator resultValidator = validator.validate(config);
        Assert.assertFalse(resultValidator.isValid());
        Assert.assertEquals(resultValidator.getExceptionMap().size(), 1);
        Assert.assertTrue(resultValidator.getExceptionMap().containsKey("Path"));
    }

    @Test
    public void shouldValidateConfigWithNotValidFileName() throws IOException {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        String path = Paths.get("src", "test", "resources").toString();
        Config config = Config.builder()
                .rootUrl("https://www.baeldung.com/apache-commons-csv")
                .tags(tags)
                .path(path)
                .fileName("")
                .topFileName("topFileName.txt")
                .pagesLimit(1000)
                .height(10)
                .topLimit(5)
                .build();

        when(urlReader.readUrls(any())).thenReturn(any());

        ResultValidator resultValidator = validator.validate(config);
        Assert.assertFalse(resultValidator.isValid());
        Assert.assertEquals(resultValidator.getExceptionMap().size(), 1);
        Assert.assertTrue(resultValidator.getExceptionMap().containsKey("FileName"));
    }

    @Test
    public void shouldValidateConfigWithNotValidTopFileName() throws IOException {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        String path = Paths.get("src", "test", "resources").toString();
        Config config = Config.builder()
                .rootUrl("https://www.baeldung.com/apache-commons-csv")
                .tags(tags)
                .path(path)
                .fileName("fileName.txt")
                .topFileName("")
                .pagesLimit(1000)
                .height(10)
                .topLimit(5)
                .build();

        when(urlReader.readUrls(any())).thenReturn(any());

        ResultValidator resultValidator = validator.validate(config);
        Assert.assertFalse(resultValidator.isValid());
        Assert.assertEquals(resultValidator.getExceptionMap().size(), 1);
        Assert.assertTrue(resultValidator.getExceptionMap().containsKey("FileName"));
    }

    @Test
    public void shouldValidateConfigWithNotValidHeight() throws IOException {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        String path = Paths.get("src", "test", "resources").toString();
        Config config = Config.builder()
                .rootUrl("https://www.baeldung.com/apache-commons-csv")
                .tags(tags)
                .path(path)
                .fileName("fileName.txt")
                .topFileName("topFileName.txt")
                .pagesLimit(1000)
                .height(-1)
                .topLimit(5)
                .build();

        when(urlReader.readUrls(any())).thenReturn(any());

        ResultValidator resultValidator = validator.validate(config);
        Assert.assertFalse(resultValidator.isValid());
        Assert.assertEquals(resultValidator.getExceptionMap().size(), 1);
        Assert.assertTrue(resultValidator.getExceptionMap().containsKey("Height"));
    }

    @Test
    public void shouldValidateConfigWithNotValidPagesLimitAndTopLimit() throws IOException {
        List<String> tags = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk");
        String path = Paths.get("src", "test", "resources").toString();
        Config config = Config.builder()
                .rootUrl("https://www.baeldung.com/apache-commons-csv")
                .tags(tags)
                .path(path)
                .fileName("fileName.txt")
                .topFileName("topFileName.txt")
                .pagesLimit(0)
                .height(10)
                .topLimit(0)
                .build();

        when(urlReader.readUrls(any())).thenReturn(any());

        ResultValidator resultValidator = validator.validate(config);
        Assert.assertFalse(resultValidator.isValid());
        Assert.assertEquals(resultValidator.getExceptionMap().size(), 2);
        Assert.assertTrue(resultValidator.getExceptionMap().containsKey("TopLimit"));
        Assert.assertTrue(resultValidator.getExceptionMap().containsKey("PagesLimit"));
    }
}
