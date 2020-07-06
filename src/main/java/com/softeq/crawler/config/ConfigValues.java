package com.softeq.crawler.config;

import java.util.Arrays;
import java.util.Optional;

public enum ConfigValues {
    URL, TAGS, PATH, FILE_NAME, TOP_FILE_NAME, HEIGHT, PAGES_LIMIT, TOP_LIMIT;

    public static Optional<ConfigValues> valueOfIgnoreCase(String of) {
        return Arrays.stream(ConfigValues.values())
                .filter(configValues -> configValues.name().equalsIgnoreCase(of))
                .findFirst();
    }
}
