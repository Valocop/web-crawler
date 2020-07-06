package com.softeq.crawler.creator;

import com.softeq.crawler.config.Config;
import com.softeq.crawler.config.ConfigValues;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.softeq.crawler.config.ConfigValues.valueOfIgnoreCase;

public class ConfigCreator implements Creator<Config> {
    private static final String APP_PROPERTIES = "app.properties";

    @Override
    public Config create(List<String> params) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        } else {
            Config config = new Config();
            Properties properties = new Properties();
            Path appProp = Path.of("src", "main", "resources", APP_PROPERTIES);
            try (InputStream is = new FileInputStream(appProp.toString())) {
                properties.load(is);
            }
            Map<String, String> paramsMap = params.stream()
                    .collect(Collectors.toMap(s -> s.contains("=") ? s.split("=")[0].trim() : s,
                            s -> s.contains("=") ? s.split("=")[1].trim() : s));
            setInitValues(config, paramsMap);
            setDefaultValues(config, properties, paramsMap);
            return config;
        }
    }

    private void setDefaultValues(Config config, Properties properties, Map<String, String> paramsMap) {
        Set<ConfigValues> initConfigValues = paramsMap.keySet().stream()
                .map(key -> valueOfIgnoreCase(key).orElse(null))
                .collect(Collectors.toSet());
        Set<ConfigValues> defaultConfigValues = new HashSet<>(Arrays.asList(ConfigValues.values()));
        defaultConfigValues.removeAll(initConfigValues);
        defaultConfigValues.forEach(configValue -> {
            switch (configValue) {
                case PATH:
                    setDefaultFilePath(properties, config);
                    break;
                case FILE_NAME:
                    config.setFileName(properties.getProperty("file_name"));
                    break;
                case TOP_FILE_NAME:
                    config.setTopFileName(properties.getProperty("top_file_name"));
                    break;
                case HEIGHT:
                    String height = properties.getProperty("height");
                    config.setHeight(Integer.parseInt(height));
                    break;
                case PAGES_LIMIT:
                    String pagesLimit = properties.getProperty("pages_limit");
                    config.setPagesLimit(Long.parseLong(pagesLimit));
                    break;
                case TOP_LIMIT:
                    String topLimit = properties.getProperty("top_limit");
                    config.setTopLimit(Integer.parseInt(topLimit));
                    break;
            }
        });
    }

    private void setInitValues(Config config, Map<String, String> paramsMap) {
        paramsMap.forEach((key, value) -> {
            Optional<ConfigValues> optionalValue = valueOfIgnoreCase(key);
            if (optionalValue.isPresent()) {
                switch (optionalValue.get()) {
                    case URL:
                        config.setRootUrl(value);
                        break;
                    case PATH:
                        config.setPath(value);
                        break;
                    case FILE_NAME:
                        config.setFileName(value);
                        break;
                    case TOP_FILE_NAME:
                        config.setTopFileName(value);
                        break;
                    case TAGS:
                        setTags(value, config);
                        break;
                    case HEIGHT:
                        config.setHeight(Integer.parseInt(value));
                        break;
                    case TOP_LIMIT:
                        config.setTopLimit(Integer.parseInt(value));
                        break;
                    case PAGES_LIMIT:
                        config.setPagesLimit(Long.parseLong(value));
                        break;
                }
            }
        });
    }

    public void setDefaultFilePath(Properties properties, Config config) {
        String[] paths = properties.getProperty("path").split(",");
        String[] addPath = Arrays.copyOfRange(paths, 1, paths.length);
        Path filePath = Path.of(paths[0], addPath);
        config.setPath(filePath.toString());
    }

    private void setTags(String value, Config config) {
        List<String> tags = Arrays.asList(value.split(","));
        config.setTags(tags);
    }
}
