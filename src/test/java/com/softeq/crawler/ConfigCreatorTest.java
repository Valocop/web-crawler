package com.softeq.crawler;

import com.softeq.crawler.config.Config;
import com.softeq.crawler.creator.ConfigCreator;
import com.softeq.crawler.creator.Creator;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnit4.class)
public class ConfigCreatorTest {

    @Test
    public void shouldCreateConfigWithUrlAndTagsParams() throws IOException {
        Creator<Config> configCreator = new ConfigCreator();
        Properties properties = getProperties();
        List<String> params = new ArrayList<>();
        String url = "https://en.wikipedia.org/wiki/Elon_Musk";
        params.add("url=" + url);
        String tags = "Tesla,Musk,Gigafactory,Elon Musk";
        List<String> tagsList = Arrays.asList(tags.split(","));
        params.add("tags=" + tags);
        Config config = configCreator.create(params);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getRootUrl(), url);
        Assert.assertEquals(config.getTags(), tagsList);
        Assert.assertEquals(config.getPath(), getPath(properties));
        Assert.assertEquals(config.getFileName(), properties.getProperty("file_name"));
        Assert.assertEquals(config.getTopFileName(), properties.getProperty("top_file_name"));
        Assert.assertThat(config.getHeight(), is(Integer.parseInt(properties.getProperty("height"))));
        Assert.assertThat(config.getPagesLimit(), is(Long.parseLong(properties.getProperty("pages_limit"))));
        Assert.assertThat(config.getTopLimit(), is(Integer.parseInt(properties.getProperty("top_limit"))));
    }

    @Test
    public void shouldCreateConfigWithAllParams() throws IOException {
        Creator<Config> configCreator = new ConfigCreator();
        List<String> params = new ArrayList<>();
        String url = "https://en.wikipedia.org/wiki/Elon_Musk";
        String tags = "Tesla,Musk,Gigafactory,Elon Musk";
        String path = Path.of("D", "Epam", "gitHub", "app.properties").toString();
        List<String> tagsList = Arrays.asList(tags.split(","));
        String fileName = "file.txt";
        String topFileName = "topFile.txt";
        int height = 30;
        long pagesLimit = 100;
        int topLimit = 150;
        params.add("url=" + url);
        params.add("tags=" + tags);
        params.add("path=" + path);
        params.add("file_name=" + fileName);
        params.add("top_file_name=" + topFileName);
        params.add("height=" + height);
        params.add("pages_limit=" + pagesLimit);
        params.add("top_limit=" + topLimit);
        Config config = configCreator.create(params);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getRootUrl(), url);
        Assert.assertEquals(config.getTags(), tagsList);
        Assert.assertEquals(config.getPath(), path);
        Assert.assertEquals(config.getFileName(), fileName);
        Assert.assertEquals(config.getTopFileName(), topFileName);
        Assert.assertEquals(config.getHeight(), height);
        Assert.assertEquals(config.getPagesLimit(), pagesLimit);
        Assert.assertEquals(config.getTopLimit(), topLimit);
    }

    @Test
    public void shouldCreateEmptyConfigWithNullParams() throws IOException {
        Creator<Config> configCreator = new ConfigCreator();
        Config config = configCreator.create(null);
        Assert.assertNull(config);
    }

    @Test
    public void shouldCreateEmptyConfigWithEmptyParams() throws IOException {
        Creator<Config> configCreator = new ConfigCreator();
        List<String> params = new ArrayList<>();
        Config config = configCreator.create(params);
        Assert.assertNull(config);
    }

    private String getPath(Properties properties) {
        String[] paths = properties.getProperty("path").split(",");
        String[] addPath = Arrays.copyOfRange(paths, 1, paths.length);
        return Path.of(paths[0], addPath).toString();
    }

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        Path appProp = Path.of("src", "main", "resources", "app.properties");
        try (InputStream is = new FileInputStream(appProp.toString())) {
            properties.load(is);
        }
        return properties;
    }
}
