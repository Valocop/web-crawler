package com.softeq.crawler.creator;

import com.softeq.crawler.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ConfigCreator implements Creator<Config> {
    private static final String APP_PROPERTIES = "app.properties";

    @Override
    public Optional<Config> create(List<String> params) throws IOException {
        Properties properties = new Properties();
        try (InputStream is = ConfigCreator.class.getResourceAsStream(APP_PROPERTIES)) {
            properties.load(is);
        }
        if (params == null || params.size() < 2) {
            return Optional.empty();
        } else {
            Config config = Config.builder()
                    .rootUrl(params.get(0))
                    .tags(Arrays.asList(params.get(1).split(",")))
                    .build();
            setFilePath(params, properties, config);
            setFileName(params, properties, config);
            setTopFileName(params, properties, config);
            setHeight(params, properties, config);
            setPagesLimit(params, properties, config);
            setTopLimit(params, properties, config);
            return Optional.of(config);
        }
    }

    private void setTopLimit(List<String> params, Properties properties, Config config) {
        if (params.size() >= 8) {
            int topLimit = Integer.parseInt(params.get(7));
            config.setTopLimit(topLimit);
        } else {
            int topLimit = Integer.parseInt(properties.getProperty("topLimit"));
            config.setTopLimit(topLimit);
        }
    }

    private void setPagesLimit(List<String> params, Properties properties, Config config) {
        long pagesLimit;
        if (params.size() >= 7) {
            pagesLimit = Long.parseLong(params.get(6));
        } else {
            pagesLimit = Long.parseLong(properties.getProperty("pagesLimit"));
        }
        config.setPagesLimit(pagesLimit);
    }

    private void setHeight(List<String> params, Properties properties, Config config) {
        int height;
        if (params.size() >= 6) {
            height = Integer.parseInt(params.get(5));
        } else {
            height = Integer.parseInt(properties.getProperty("height"));
        }
        config.setHeight(height);
    }

    private void setTopFileName(List<String> params, Properties properties, Config config) {
        if (params.size() >= 5) {
            config.setTopFileName(params.get(4));
        } else {
            config.setTopFileName(properties.getProperty("topFileName"));
        }
    }

    private void setFileName(List<String> params, Properties properties, Config config) {
        if (params.size() >= 4) {
            config.setFileName(params.get(3));
        } else {
            config.setFileName(properties.getProperty("fileName"));
        }
    }

    private void setFilePath(List<String> params, Properties properties, Config config) {
        if (params.size() >= 3) {
            config.setPath(params.get(2));
        } else {
            String[] paths = properties.getProperty("path").split(",");
            String[] addPath = Arrays.copyOfRange(paths, 1, paths.length - 1);
            Path filePath = Path.of(paths[0], addPath);
            config.setPath(filePath.toString());
        }
    }
}
